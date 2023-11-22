package connector.sqlite;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.PlatformTransactionManager;

public class AbstractSqliteSessionTemplate {

	//private SqlSessionTemplate doctorBomSessionTemplate;
	private SqlSessionTemplate doctorComSessionTemplate;
	//private SqlSessionTemplate doctorWorkSessionTemplate;
	
	//private PlatformTransactionManager doctorBomTransactionManager;
	private PlatformTransactionManager doctorComTransactionManager;
	//private PlatformTransactionManager doctorWorkTransactionManager;

	//private SqlSessionTemplate codyBomSessionTemplate;
	private SqlSessionTemplate codyComSessionTemplate;
	///private SqlSessionTemplate codyWorkSessionTemplate;
	
	//private PlatformTransactionManager codyBomTransactionManager;
	private PlatformTransactionManager codyComTransactionManager;
	//private PlatformTransactionManager codyWorkTransactionManager;
	
	public static final String DOCTOR_BOM_NAMESPACE = "doctor.bom";
	public static final String DOCTOR_COM_NAMESPACE = "doctor.com";
	//public static final String DOCTOR_WORK_NAMESPACE = "doctor.work";
	
	public static final String CODY_BOM_NAMESPACE = "cody.bom";
	public static final String CODY_COM_NAMESPACE = "cody.com";
	//public static final String CODY_WORK_NAMESPACe = "cody.work";
	
	
	/*public SqlSessionTemplate getDoctorBomSessionTemplate() {
		return doctorBomSessionTemplate;
	}
	public void setDoctorBomSessionTemplate(
			SqlSessionTemplate doctorBomSessionTemplate) {
		this.doctorBomSessionTemplate = doctorBomSessionTemplate;
	}*/
	public SqlSessionTemplate getDoctorComSessionTemplate() {
		return doctorComSessionTemplate;
	}
	public void setDoctorComSessionTemplate(
			SqlSessionTemplate doctorComSessionTemplate) {
		this.doctorComSessionTemplate = doctorComSessionTemplate;
	}
	/*public SqlSessionTemplate getDoctorWorkSessionTemplate() {
		return doctorWorkSessionTemplate;
	}
	public void setDoctorWorkSessionTemplate(
			SqlSessionTemplate doctorWorkSessionTemplate) {
		this.doctorWorkSessionTemplate = doctorWorkSessionTemplate;
	}*/
	/*public PlatformTransactionManager getDoctorBomTransactionManager() {
		return doctorBomTransactionManager;
	}
	public void setDoctorBomTransactionManager(
			PlatformTransactionManager doctorBomTransactionManager) {
		this.doctorBomTransactionManager = doctorBomTransactionManager;
	}*/
	public PlatformTransactionManager getDoctorComTransactionManager() {
		return doctorComTransactionManager;
	}
	public void setDoctorComTransactionManager(
			PlatformTransactionManager doctorComTransactionManager) {
		this.doctorComTransactionManager = doctorComTransactionManager;
	}
	/*public PlatformTransactionManager getDoctorWorkTransactionManager() {
		return doctorWorkTransactionManager;
	}
	public void setDoctorWorkTransactionManager(
			PlatformTransactionManager doctorWorkTransactionManager) {
		this.doctorWorkTransactionManager = doctorWorkTransactionManager;
	}*/
	
	/*public SqlSessionTemplate getCodyBomSessionTemplate() {
		return codyBomSessionTemplate;
	}
	public void setCodyBomSessionTemplate(SqlSessionTemplate codyBomSessionTemplate) {
		this.codyBomSessionTemplate = codyBomSessionTemplate;
	}*/
	public SqlSessionTemplate getCodyComSessionTemplate() {
		return codyComSessionTemplate;
	}
	public void setCodyComSessionTemplate(SqlSessionTemplate codyComSessionTemplate) {
		this.codyComSessionTemplate = codyComSessionTemplate;
	}
	/*public SqlSessionTemplate getCodyWorkSessionTemplate() {
		return codyWorkSessionTemplate;
	}
	public void setCodyWorkSessionTemplate(
			SqlSessionTemplate codyWorkSessionTemplate) {
		this.codyWorkSessionTemplate = codyWorkSessionTemplate;
	}*/
	/*public PlatformTransactionManager getCodyBomTransactionManager() {
		return codyBomTransactionManager;
	}
	public void setCodyBomTransactionManager(
			PlatformTransactionManager codyBomTransactionManager) {
		this.codyBomTransactionManager = codyBomTransactionManager;
	}*/
	public PlatformTransactionManager getCodyComTransactionManager() {
		return codyComTransactionManager;
	}
	public void setCodyComTransactionManager(
			PlatformTransactionManager codyComTransactionManager) {
		this.codyComTransactionManager = codyComTransactionManager;
	}
	/*public PlatformTransactionManager getCodyWorkTransactionManager() {
		return codyWorkTransactionManager;
	}
	public void setCodyWorkTransactionManager(
			PlatformTransactionManager codyWorkTransactionManager) {
		this.codyWorkTransactionManager = codyWorkTransactionManager;
	}*/
	
	
}
