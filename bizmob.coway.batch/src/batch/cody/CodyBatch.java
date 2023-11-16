package batch.cody;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import connector.sqlite.cody.dao.CodyBomDao;
import connector.sqlite.cody.dao.CodyComDao;
import connector.sqlite.cody.dao.data.CodyCommonCodeDO;
import connector.sqlite.cody.dao.data.CodyMaterialDO;
import connector.sqlite.cody.dao.data.CodyProductDO;
import connector.sqlite.doctor.dao.DoctorComDao;
import connector.sqlite.doctor.dao.data.DoctorQtCodeDO;
public class CodyBatch {

	private static final Logger logger = LoggerFactory.getLogger(CodyBatch.class);
	
	@Autowired
	SAPAdapter sapAdapter;
	
	@Autowired
	CodyComDao codyComDao;
	@Autowired
	CodyBomDao codyBomDao;
		
	@Autowired
	DoctorComDao doctorComDao;
	
	private List<DoctorQtCodeDO> qtCodeList = null;
	private List<CodyCommonCodeDO> commonCodeList = null;
	private List<CodyProductDO> productList = null;
	private List<CodyMaterialDO> materialList = null;
	
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
			
			logger.debug("------------------ Cody Material Table Creation Start ------------------------------");
			count = insertMaterialTable();
			logger.debug("------------------ Cody Material Table Creation End :: count = " + count + " ------------------------------");
			
			logger.debug("------------------ QtCode Table Creation Start ------------------------------");
			count = insertQtCodeTable();
			logger.debug("------------------ QtCode Table Creation End :: count = " + count + " ------------------------------");
			
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
			sapAdapter.execute("ZPDA_TRAN_SP_CSDR_CODE_DIS", null, new QTSapMapper());
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

	public int insertMaterialTable() throws DataAccessException {
		
		int insertCount = 0;
		TransactionStatus status = codyBomDao.getCodyComTransactionManager().getTransaction(new DefaultTransactionDefinition());		
		try {
			codyBomDao.createMaterialTable();
			codyBomDao.deleteMaterialData();
			
			for(CodyMaterialDO material : materialList) {
				
				insertCount += codyBomDao.insertMaterialData(material);				
			}

			codyBomDao.getCodyComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			codyBomDao.getCodyComTransactionManager().rollback(status);
			throw e;
		}
		
		return insertCount;
	}
	
	public int insertQtCodeTable() throws Exception {
		int insertCount = 0;
		TransactionStatus status = null;
		try {				
			status = codyBomDao.getCodyComTransactionManager().getTransaction(new DefaultTransactionDefinition());
			codyBomDao.createQtCodeTable();
			codyBomDao.deleteQtCodeData();
			
			for(DoctorQtCodeDO code : qtCodeList) {
				insertCount += codyBomDao.insertQtCodeData(code);				
			}

			codyBomDao.getCodyComTransactionManager().commit(status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
		
			JCoTable materialTable = function.getTableParameterList().getTable("O_ITAB3");
			materialList = convertSapTableToObjectList(materialTable, CodyMaterialDO.class);
			logger.info("material Code sap record count = " + materialList.size());
			
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
	
	class QTSapMapper extends AbstractSapMapper {

		@Override
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object param) throws AdapterException {
			
			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) 	throws AdapterException {
		
			JCoTable qtCodeTable = function.getTableParameterList().getTable("O_ITAB1");
			qtCodeList = convertSapTableToObjectList(qtCodeTable, DoctorQtCodeDO.class);
			logger.info(" qualtity code sap record count = " + qtCodeList.size());
			
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
