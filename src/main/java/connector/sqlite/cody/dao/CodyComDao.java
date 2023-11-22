package connector.sqlite.cody.dao;

import org.springframework.dao.DataAccessException;

import connector.sqlite.AbstractSqliteSessionTemplate;
import connector.sqlite.cody.dao.data.CodyCommonCodeDO;

public class CodyComDao extends AbstractSqliteSessionTemplate {

	public int createCommonCodeTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_COM_NAMESPACE + ".createComnonCodeTable");
	}

	public int dropCommonCodeTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_COM_NAMESPACE + ".dropCommonCodeTable");
	}
	
	public int insertCommonCodeData(CodyCommonCodeDO code) throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().insert(CODY_COM_NAMESPACE + ".insertCommonCodeData", code);
	}
		
	public int deleteCommonCodeData() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().delete(CODY_COM_NAMESPACE + ".deleteCommonCodeData");
	}
	

}
