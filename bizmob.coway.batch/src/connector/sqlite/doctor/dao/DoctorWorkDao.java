package connector.sqlite.doctor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import connector.sqlite.AbstractSqliteSessionTemplate;
import connector.sqlite.doctor.dao.data.DoctorWorkTableList;



@Deprecated
public class DoctorWorkDao extends AbstractSqliteSessionTemplate {

//	public int createWorkTableList() throws DataAccessException {
//		
//		TransactionStatus status = getDoctorWorkTransactionManager().getTransaction(new DefaultTransactionDefinition());
//		
//		try {
//			
//			DoctorWorkTableList tableList = new DoctorWorkTableList();
//			
//			for(int i=0 ; i<tableList.getWorkTableCount() ; i++) {
//	
//				String createId = tableList.getWorkTableName(i);
//				
//				getDoctorWorkSessionTemplate().update(DOCTOR_WORK_NAMESPACE + "." + createId);
//				
//				System.out.println(i + " :: " + createId );
//			
//			}
//	
//			getDoctorWorkTransactionManager().commit(status);
//		} catch (Exception e) {
//			getDoctorWorkTransactionManager().rollback(status);
//		}
//		
//		
//		return 0;
//	}
//	
//	public List<String> selectWorkTableList() throws DataAccessException {
//		
//		return (List<String>) getDoctorWorkSessionTemplate().selectList(DOCTOR_WORK_NAMESPACE + ".selectTableList");
//	}
//	
//	public int dropWorkTable(String table_name) throws DataAccessException, SQLException {
//		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("table_name", table_name);
//		
//		return (int) getDoctorWorkSessionTemplate().delete(DOCTOR_WORK_NAMESPACE + ".dropTable", map);		
//	}
//	
//	public int dorpWorkTableList(List<String> tableList) throws DataAccessException, SQLException {
//		
//		int delCount = 0;
//		
//		for (String table : tableList) {
//			delCount += dropWorkTable(table);
//		}
//		
//		return delCount;
//	}
		
}
