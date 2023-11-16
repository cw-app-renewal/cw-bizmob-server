package batch.doctor;

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

import connector.sqlite.doctor.dao.DoctorComDao;
import connector.sqlite.doctor.dao.data.DoctorBBSImgDO;
import connector.sqlite.doctor.dao.data.DoctorCommonCodeDO;
import connector.sqlite.doctor.dao.data.DoctorQcBBSDO;
import connector.sqlite.doctor.dao.data.DoctorQtCodeDO;

public class DoctorBatchCode {

	private static final Logger logger = LoggerFactory.getLogger(DoctorBatchCode.class);
	
	@Autowired
	SAPAdapter sapAdapter;
	
	@Autowired
	DoctorComDao doctorComDao;
	
	private List<DoctorQtCodeDO> qtCodeList = null;
	private List<DoctorCommonCodeDO> commonCodeList = null;
	
	private List<DoctorQcBBSDO> qcBBSList = null;
	private List<DoctorBBSImgDO> bbsImgList = null;
	
	
	public boolean createCodeDatabase() {
		
		try {
			
			logger.debug("------------------ Code Rfc Sap Connection Start -----------------------");
			executeRfc();
			logger.debug("------------------ Code Rfc Sap Connection End -----------------------");
			
			logger.debug("------------------ CommonCode Table Creation Start ------------------------------");
			int count = insertCommonCodeTable();
			logger.debug("------------------ CommonCode Table Creation End :: count = " + count + " ------------------------------");
			
			logger.debug("------------------ QtCode Table Creation Start ------------------------------");
			count = insertQtCodeTable();
			logger.debug("------------------ QtCode Table Creation End :: count = " + count + " ------------------------------");
			
			logger.debug("------------------ QcBBS Table Creation Start ------------------------------");
			count = insertQcBBSTable();
			logger.debug("------------------ QcBBS Table Creation End :: count = " + count + " ------------------------------");

			logger.debug("------------------ BbsImg Table Creation Start ------------------------------");
			count = insertBbsImgTable();
			logger.debug("------------------ BbsImg Table Creation End :: count = " + count + " ------------------------------");

			
		} catch (Exception e) {
			logger.error("", e);
		}	
		return true;
	}
	
	
	public boolean executeRfc() throws AdapterException {
		
		logger.debug(" Sap RFC ZPDA_TRAN_SP_CSDR_CODE_DIS Execute !!");
		sapAdapter.execute("ZPDA_TRAN_SP_CSDR_CODE_DIS", null, new SapMapper());
	
		logger.debug(" Sap RFC ZPDA_TRAN_CSDR_CODE_DIS3 Execute !!");
		sapAdapter.execute("ZPDA_TRAN_CSDR_CODE_DIS3", null, new SapMapperCodeDis3());

		return true;
	}
	
	public int insertCommonCodeTable() throws DataAccessException {
		
		int insertCount = 0;
		
		TransactionStatus status = doctorComDao.getDoctorComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			doctorComDao.deleteCommonCodeData();
			
			for(DoctorCommonCodeDO code : commonCodeList) {
				
				insertCount += doctorComDao.insertCommonCodeData(code);				
			}

			doctorComDao.getDoctorComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			doctorComDao.getDoctorComTransactionManager().rollback(status);
			throw e;			
		}
		
		return insertCount;
	}
	
	
	public int insertQtCodeTable() throws DataAccessException {
		int insertCount = 0;
		
		TransactionStatus status = doctorComDao.getDoctorComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
			doctorComDao.deleteQtCodeData();
			
			for(DoctorQtCodeDO code : qtCodeList) {
				
				insertCount += doctorComDao.insertQtCodeData(code);				
			}

			doctorComDao.getDoctorComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			doctorComDao.getDoctorComTransactionManager().rollback(status);
			throw e;
		}
		
		return insertCount;		
	}
	
	public int insertQcBBSTable() throws DataAccessException {
		int insertCount = 0;
		
		TransactionStatus status = doctorComDao.getDoctorComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
			doctorComDao.deleteQcBBSData();
			
			for(DoctorQcBBSDO code : qcBBSList) {
				
				insertCount += doctorComDao.insertQcBBSData(code);				
			}

			doctorComDao.getDoctorComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			doctorComDao.getDoctorComTransactionManager().rollback(status);
			throw e;
		}
		
		return insertCount;		
	}
	
	public int insertBbsImgTable() throws DataAccessException {
		int insertCount = 0;
		
		TransactionStatus status = doctorComDao.getDoctorComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
			doctorComDao.deleteBBSImgData();
			
			for(DoctorBBSImgDO code : bbsImgList) {
				
				insertCount += doctorComDao.insertBBSImgData(code);				
			}

			doctorComDao.getDoctorComTransactionManager().commit(status);
		} catch (DataAccessException e) {
			doctorComDao.getDoctorComTransactionManager().rollback(status);
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
		
			JCoTable qtCodeTable = function.getTableParameterList().getTable("O_ITAB1");
			qtCodeList = convertSapTableToObjectList(qtCodeTable, DoctorQtCodeDO.class);
			logger.debug(" qualtity code sap record count = " + qtCodeList.size());
			
			JCoTable commonCodeTable = function.getTableParameterList().getTable("O_ITAB3");
			commonCodeList = convertSapTableToObjectList(commonCodeTable, DoctorCommonCodeDO.class);
			logger.debug(" doctor common code sap record count = " + commonCodeList.size());
		
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
	
	class SapMapperCodeDis3 extends AbstractSapMapper {

		@Override
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object param) throws AdapterException {
			
			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) 	throws AdapterException {
		
			JCoTable qcBBSTable = function.getTableParameterList().getTable("O_ITAB1");
			qcBBSList = convertSapTableToObjectList(qcBBSTable, DoctorQcBBSDO.class);
			logger.debug(" qcBBS code sap record count = " + qcBBSList.size());
			
			JCoTable bbsImgTable = function.getTableParameterList().getTable("O_ITAB2");
			bbsImgList = convertSapTableToObjectList(bbsImgTable, DoctorBBSImgDO.class);
			logger.debug(" bbsImg code sap record count = " + bbsImgList.size());
		
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
