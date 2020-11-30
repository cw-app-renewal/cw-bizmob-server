package connector.sqlite.cody.dao;

import org.springframework.dao.DataAccessException;

import connector.sqlite.AbstractSqliteSessionTemplate;
import connector.sqlite.cody.dao.data.CodyProductDO;


public class CodyBomDao extends AbstractSqliteSessionTemplate  {

	public int createProductTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".createProductTable");
	}
	
	public int dropProductTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".dropProductTable");
	}
	
	public int deleteProductData() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().delete(CODY_BOM_NAMESPACE + ".deleteProductData");
	}
	
	public int insertProductData(CodyProductDO product) throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().insert(CODY_BOM_NAMESPACE + ".insertProductData", product);
	}
	
	public int updateProductData(CodyProductDO product) throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".updateProductData", product);			
	}
	
	public int createRD007_O_ITAB1Table() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".createRD007_O_ITAB1Table");
	}
	public int createRD007_O_ITAB2Table() throws DataAccessException {
	
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".createRD007_O_ITAB2Table");
	}
	public int createRD007_ExportTable() throws DataAccessException {
	
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".createRD007_ExportTable");
	}
	
}
