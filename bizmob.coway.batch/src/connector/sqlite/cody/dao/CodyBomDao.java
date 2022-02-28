package connector.sqlite.cody.dao;

import org.springframework.dao.DataAccessException;

import connector.sqlite.AbstractSqliteSessionTemplate;
import connector.sqlite.cody.dao.data.CodyMaterialDO;
import connector.sqlite.cody.dao.data.CodyProductDO;
import connector.sqlite.doctor.dao.data.DoctorQtCodeDO;


public class CodyBomDao extends AbstractSqliteSessionTemplate  {

	public int createProductTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".createProductTable");
	}
	
	public int createMaterialTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".createMaterialTable");
	}

	public int dropProductTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".dropProductTable");
	}
	
	public int dropMaterialTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".dropMaterialTable");
	}
	
	public int deleteProductData() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().delete(CODY_BOM_NAMESPACE + ".deleteProductData");
	}
	
	public int deleteMaterialData() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().delete(CODY_BOM_NAMESPACE + ".deleteMaterialData");
	}

	public int insertProductData(CodyProductDO product) throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().insert(CODY_BOM_NAMESPACE + ".insertProductData", product);
	}
	
	public int insertMaterialData(CodyMaterialDO material) throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().insert(CODY_BOM_NAMESPACE + ".insertMaterialData", material);
	}

	public int updateProductData(CodyProductDO product) throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".updateProductData", product);			
	}
	
	public int updateProductData(CodyMaterialDO material) throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".updateMaterialData", material);			
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

	public int deleteQtCodeData() throws Exception {
			
		return (int) getCodyComSessionTemplate().delete(CODY_BOM_NAMESPACE + ".deleteQtCodeData");
	}

	public int insertQtCodeData(DoctorQtCodeDO code) throws Exception {
		
		return (int) getCodyComSessionTemplate().insert(CODY_BOM_NAMESPACE + ".insertQtCodeData", code);
	}
	
	public int createQtCodeTable() throws DataAccessException {
		
		return (int) getCodyComSessionTemplate().update(CODY_BOM_NAMESPACE + ".createQtCodeTable");
	}	
	
}
