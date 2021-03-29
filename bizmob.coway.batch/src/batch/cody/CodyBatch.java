package batch.cody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import connector.sqlite.cody.dao.CodyBomDao;
import connector.sqlite.cody.dao.CodyComDao;
import connector.sqlite.cody.dao.data.CodyCommonCodeDO;
import connector.sqlite.cody.dao.data.CodyProductDO;

public class CodyBatch {

	private ILogger logger = LoggerService.getLogger(CodyBatch.class);
	
	@Autowired
	SAPAdapter sapAdapter;
	
	@Autowired
	CodyComDao codyComDao;
	@Autowired
	CodyBomDao codyBomDao;
		
	private List<CodyCommonCodeDO> commonCodeList = null;
	private List<CodyProductDO> productList = null;
	
	public boolean createDatabase() {
		
		try {
			
			logger.debug("------------------ Cody Rfc Sap Connection Start -----------------------");
			executeRfc();
			logger.debug("------------------ Cody Rfc Sap Connection End -----------------------");
			
			//logger.debug("------------------ Cody Rfc Sap Code RD300 Connection Start -----------------------");
			//executeRfcRd300();
			//logger.debug("------------------ Cody Rfc Sap Code RD300  Connection End -----------------------");
			
			logger.debug("------------------ Cody CommonCode Table Creation Start ------------------------------");
			int count = insertCommonCodeTable();
			logger.debug("------------------ Cody CommonCode Table Creation End :: count = " + count + " ------------------------------");
			
			logger.debug("------------------ Cody Product Table Creation Start ------------------------------");
			count = insertProductTable();
			logger.debug("------------------ Cody Product Table Creation End :: count = " + count + " ------------------------------");
			
			codyBomDao.createRD007_ExportTable();
			codyBomDao.createRD007_O_ITAB1Table();
			codyBomDao.createRD007_O_ITAB2Table();
			logger.debug("------------------ Cody RD007 Table Creation End ------------------------------");
				
		} catch (Exception e) {
			logger.error("", e);
		}	
		return true;
	}
	
	
	public boolean executeRfc() {
		
		try {		
			sapAdapter.execute("ZPDA_TRAN_SP_CODY_CODE_DIS", null, new SapMapper());
	
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}

		return true;
	}
	
	
	
	public int insertCommonCodeTable() throws DataAccessException  {
		
		int insertCount = 0;
		
		TransactionStatus status = codyComDao.getCodyComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			codyComDao.deleteCommonCodeData();
			
			for(CodyCommonCodeDO code : commonCodeList) {
				
				insertCount += codyComDao.insertCommonCodeData(code);				
			}

			codyComDao.getCodyComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			codyComDao.getCodyComTransactionManager().rollback(status);
			throw e;
		}
		
		return insertCount;
	}
	
	
	public int insertProductTable() throws DataAccessException {
		
		int insertCount = 0;
		
		TransactionStatus status = codyBomDao.getCodyComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			codyBomDao.deleteProductData();
			
			for(CodyProductDO product : productList) {
				
				insertCount += codyBomDao.insertProductData(product);				
			}

			codyBomDao.getCodyComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			codyBomDao.getCodyComTransactionManager().rollback(status);
			throw e;
		}
		
		return insertCount;
	}
	
	
	
	class SapMapper extends AbstractSapMapper {

		@Override
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object param) throws AdapterException {
			
			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) 	throws AdapterException {
		
			JCoTable productTable = function.getTableParameterList().getTable("O_ITAB1");
			productList = convertSapTableToObjectList(productTable, CodyProductDO.class);
			logger.debug("product sap record count = " + productList.size());
			
			JCoTable commonCodeTable = function.getTableParameterList().getTable("O_ITAB2");
			commonCodeList = convertSapTableToObjectList(commonCodeTable, CodyCommonCodeDO.class);
			logger.debug("common Code sap record count = " + commonCodeList.size());
		
			return null;
		}

		@Override
		public void verifySapResult(JCoFunction function) throws AdapterException {
			
			JCoParameterList paramList = function.getExportParameterList();
			JCoStructure struct = paramList.getStructure("E_RETURN");
			String type = struct.getString("TYPE");
			if(type.equals("T") != true) {
				throw new AdapterException("", struct.getString("MESSAGE"));
			}
		}
	}
	
}