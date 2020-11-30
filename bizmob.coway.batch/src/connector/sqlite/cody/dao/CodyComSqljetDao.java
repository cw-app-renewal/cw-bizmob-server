package connector.sqlite.cody.dao;

import java.io.File;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import connector.sqlite.cody.dao.data.CodyCommonCodeDO;


@Deprecated
public class CodyComSqljetDao {

	
	public SqlJetDb createSqlDb(String _dbName) throws SqlJetException {
		
		File dbFile = new File(_dbName);
		dbFile.delete();
		
		SqlJetDb db = SqlJetDb.open(dbFile, true);
		
		return db;
	}
	
	public boolean createSqlTable(SqlJetDb db, String sql, String index) throws SqlJetException {
		db.beginTransaction(SqlJetTransactionMode.WRITE);
		db.createTable(sql);
		//db.createIndex(index);
		
		db.commit();
		
		return true;
	}
	
	public boolean insertSql(ISqlJetTable table, CodyCommonCodeDO code) throws SqlJetException {
		
		table.insert(code.getOTH_CLASS(), code.getOTH_CD(), code.getOTH_CD_NM());
		
		return true;
	}
	
}
