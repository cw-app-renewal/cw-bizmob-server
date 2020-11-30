package connector.sqlite.doctor.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

import connector.sqlite.SqliteDataSource;
import connector.sqlite.doctor.mapper.DoctorBomMapper;


@Deprecated
public class DoctorBomMapperDao {
	
	private String dbName;
	
	
	
	public int createProductTable() throws SQLException {
		
		SqliteDataSource sds = new SqliteDataSource("D:/TEST.db", "tester");
		SqlSession session = sds.openSession();
		
		DoctorBomMapper mapper = session.getMapper(DoctorBomMapper.class);
		
		mapper.dropProductTable();
		
		mapper.createProductTable();
		
		mapper.createProductIndex();
		
		return 0;
		
	}

}
