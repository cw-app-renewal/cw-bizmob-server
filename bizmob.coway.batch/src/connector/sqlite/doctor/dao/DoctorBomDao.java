package connector.sqlite.doctor.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import connector.sqlite.AbstractSqliteSessionTemplate;
import connector.sqlite.doctor.dao.data.DoctorProductDO;





public class DoctorBomDao extends AbstractSqliteSessionTemplate {

	public int createProductTable() throws DataAccessException {
				
		return (int) getDoctorComSessionTemplate().update(DOCTOR_BOM_NAMESPACE + ".createProductTable");
	}

	public int createRD008_ExportTable() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_BOM_NAMESPACE + ".createRD008_ExportTable");			
	}
	
	public int createRD008_OITAB1Table() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_BOM_NAMESPACE + ".createRD008_O_ITAB1Table");			
	}
	
	public int createRD008_OITAB2Table() throws DataAccessException {
			
		return (int) getDoctorComSessionTemplate().update(DOCTOR_BOM_NAMESPACE + ".createRD008_O_ITAB2Table");			
	}
	
	public int dropProductTable() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_BOM_NAMESPACE + ".dropProductTable");
	}
	
	public int deleteProductData() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().delete(DOCTOR_BOM_NAMESPACE + ".deleteProductData");
	}
	
	public int insertProductData(DoctorProductDO product) throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().insert(DOCTOR_BOM_NAMESPACE + ".insertProductData", product);
	}
	
	public int updateProductData(DoctorProductDO product) throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_BOM_NAMESPACE + ".updateProductData", product);			
	}
	
}

