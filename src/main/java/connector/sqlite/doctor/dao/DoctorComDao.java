package connector.sqlite.doctor.dao;

import org.springframework.dao.DataAccessException;

import connector.sqlite.AbstractSqliteSessionTemplate;
import connector.sqlite.doctor.dao.data.DoctorBBSImgDO;
import connector.sqlite.doctor.dao.data.DoctorCommonCodeDO;
import connector.sqlite.doctor.dao.data.DoctorQcBBSDO;
import connector.sqlite.doctor.dao.data.DoctorQtCodeDO;




public class DoctorComDao extends AbstractSqliteSessionTemplate {

	public int createCommonCodeTable() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_COM_NAMESPACE + ".createComnonCodeTable");
	}
	
	public int createQtCodeTable() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_COM_NAMESPACE + ".createQtCodeTable");
	}	
	
	public int createQcBBSTable() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_COM_NAMESPACE + ".createQcBBSTable");
	}	

	public int createBBSImgTable() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().update(DOCTOR_COM_NAMESPACE + ".createBBSImgTable");
	}	

	public int insertCommonCodeData(DoctorCommonCodeDO code) throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().insert(DOCTOR_COM_NAMESPACE + ".insertCommonCodeData", code);
	}
	
	public int insertQtCodeData(DoctorQtCodeDO code) throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().insert(DOCTOR_COM_NAMESPACE + ".insertQtCodeData", code);
	}
	
	public int insertQcBBSData(DoctorQcBBSDO code) throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().insert(DOCTOR_COM_NAMESPACE + ".insertQcBBSData", code);
	}
	
	public int insertBBSImgData(DoctorBBSImgDO code) throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().insert(DOCTOR_COM_NAMESPACE + ".insertBBSImgData", code);
	}
	
	public int deleteCommonCodeData() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().delete(DOCTOR_COM_NAMESPACE + ".deleteCommonCodeData");
	}
	
	public int deleteQtCodeData() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().delete(DOCTOR_COM_NAMESPACE + ".deleteQtCodeData");
	}

	public int deleteQcBBSData() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().delete(DOCTOR_COM_NAMESPACE + ".deleteQcBBSData");
	}

	public int deleteBBSImgData() throws DataAccessException {
		
		return (int) getDoctorComSessionTemplate().delete(DOCTOR_COM_NAMESPACE + ".deleteBBSImgData");
	}
}
