package connector.sqlite;

import java.sql.SQLException;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import connector.sqlite.doctor.mapper.DoctorBomMapper;


@Deprecated
public class SqliteDataSource {

	private static final String _SQLITE_JDBC_DRIVER_NAME = "org.sqlite.JDBC";
	private static final int _SQLITE_MAX_ACTIVE_CONNECTION = 1;
	
	private SqlSessionFactory factory;
	private Configuration configuration;
	private PooledDataSource dataSource;
	
	public SqliteDataSource(String _dbName, String _userId) {
		dataSource = new PooledDataSource(_SQLITE_JDBC_DRIVER_NAME, _dbName, "", "");
		dataSource.setPoolMaximumActiveConnections(_SQLITE_MAX_ACTIVE_CONNECTION);
		
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment(_userId, transactionFactory, dataSource);
		
		configuration = new Configuration(environment);
        //configuration.setLazyLoadingEnabled(false);
        //configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        configuration.addMapper(DoctorBomMapper.class);
        
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        factory = builder.build(configuration);		
			
	}
	
    public SqlSession openSession() throws SQLException {
        //Connection conn = dataSource.getConnection();
        //SqlSession session = factory.openSession(conn);
        SqlSession session = factory.openSession();
        return session;
    }
    
    public void closeSession(SqlSession session) {
        session.close();
    }   	
}
