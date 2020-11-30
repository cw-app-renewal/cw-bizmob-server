package batch.cody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;


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
import connector.sqlite.cody.dao.CodyComSqljetDao;
import connector.sqlite.cody.dao.data.CodyCommonCodeDO;
import connector.sqlite.cody.dao.data.CodyProductDO;

@Deprecated
public class CodyBatchSqlJetTest {

	private ILogger logger = LoggerService.getLogger(CodyBatchSqlJetTest.class);
	
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
			
			logger.debug("------------------ Cody CommonCode Table Creation Start ------------------------------");
			int count = insertCommonCodeTable();
			logger.debug("------------------ Cody CommonCode Table Creation End :: count = " + count + " ------------------------------");
			
			logger.debug("------------------ Cody Product Table Creation Start ------------------------------");
			count = insertProductTable();
			logger.debug("------------------ Cody Product Table Creation End :: count = " + count + " ------------------------------");
			
		} catch (Exception e) {
			logger.error("", e);
		}	
		return true;
	}
	
	
	public boolean executeRfc() {
		
		try {		
			sapAdapter.execute("ZPDA_TRAN_SP_CODY_CODE_DIS", null, new SapMapper());
	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public int insertCommonCodeTable() {
		
		try {
			CodyComSqljetDao dao = new CodyComSqljetDao();
			
			SqlJetDb db = dao.createSqlDb("D:/cody_com.db");
			
			dao.createSqlTable(db, "create table common_code (oth_class text, oth_cd text, oth_cd_nm text) ", "");
			
			db.beginTransaction(SqlJetTransactionMode.WRITE);
			try {
				ISqlJetTable table = db.getTable("common_code");
				
				for(CodyCommonCodeDO code : commonCodeList) {
					dao.insertSql(table, code);
				}
				
			} finally {
				db.commit();
			}
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	public int insertProductTable() {
		
		int insertCount = 0;
		
		TransactionStatus status = codyBomDao.getCodyComTransactionManager().getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			codyBomDao.deleteProductData();
			
			for(CodyProductDO product : productList) {
				
				insertCount += codyBomDao.insertProductData(product);				
			}

			codyBomDao.getCodyComTransactionManager().commit(status);
		} catch (Exception e) {
			codyBomDao.getCodyComTransactionManager().rollback(status);
			return 0;
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
			
			JCoTable commonCodeTable = function.getTableParameterList().getTable("O_ITAB2");
			commonCodeList = convertSapTableToObjectList(commonCodeTable, CodyCommonCodeDO.class);
		
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
