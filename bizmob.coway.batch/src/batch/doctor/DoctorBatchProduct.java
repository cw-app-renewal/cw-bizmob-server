package batch.doctor;

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

import connector.sqlite.doctor.dao.DoctorBomDao;
import connector.sqlite.doctor.dao.data.DoctorProductDO;


public class DoctorBatchProduct {

	private ILogger logger = LoggerService.getLogger(DoctorBatchProduct.class);

	@Autowired
	SAPAdapter sapAdapter;
	
	@Autowired
	DoctorBomDao doctorBomDao;
	
	private List<DoctorProductDO> prdtList1 = null;
	private List<DoctorProductDO> prdtList2 = null;

	public boolean createProductDatabase() {
		
		try {
			
			logger.debug("------------------ Code Rfc Sap Connection Start -----------------------");
			executeRfc();
			logger.debug("------------------ Code Rfc Sap Connection End -----------------------");

			logger.debug("------------------ Product Table Data Delete -----------------------------");
			doctorBomDao.deleteProductData();
		
			logger.debug("------------------ Product Table Creation Start ------------------------------");
			int count = insertProductCodeTable(prdtList1);
			logger.debug("------------------ Product Table Creation End :: count = " + count + " ------------------------------");
			
			logger.debug("------------------ Product Table Creation Start ------------------------------");
			count = insertProductCodeTable(prdtList2);
			logger.debug("------------------ Product Table Creation End :: count = " + count + " ------------------------------");
			
			doctorBomDao.createRD008_OITAB1Table();
			doctorBomDao.createRD008_OITAB2Table();
			doctorBomDao.createRD008_ExportTable();
			logger.debug("-------------------RD008_O_ITAB Table Created!!---------------------------------------------------------");
			
		} catch (Exception e) {
			logger.error("", e);
		}	
		return true;
	}
	
	public boolean executeRfc() {
		
		try {		
			sapAdapter.execute("ZPDA_TRAN_SP_CSDR_CODE_DIS2", null, new SapMapper());
	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public int insertProductCodeTable(List<DoctorProductDO> productList) throws DataAccessException {
		
		int insertCount = 0;
		
		TransactionStatus status = doctorBomDao.getDoctorComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
						
			for(DoctorProductDO product : productList) {
								
				insertCount += doctorBomDao.insertProductData(product);				
			}

			doctorBomDao.getDoctorComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			doctorBomDao.getDoctorComTransactionManager().rollback(status);
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
		
			JCoTable productTable1 = function.getTableParameterList().getTable("O_ITAB1_1");
			prdtList1 = convertSapTableToObjectList(productTable1, DoctorProductDO.class);
			logger.debug(" doctor product1 sap record count = " + prdtList1.size());
			
			JCoTable productTable2 = function.getTableParameterList().getTable("O_ITAB1_2");
			prdtList2 = convertSapTableToObjectList(productTable2, DoctorProductDO.class);
			logger.debug(" doctor product2 sap record count = " + prdtList2.size());
		
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
