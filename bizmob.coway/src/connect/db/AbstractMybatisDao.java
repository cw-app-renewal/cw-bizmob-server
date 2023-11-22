package connect.db;

import org.mybatis.spring.SqlSessionTemplate;

public abstract class AbstractMybatisDao {

	private SqlSessionTemplate cowayMmsSessionTemplate;
	
	//mybatis namespace
	public static final String MMS_NAMESPACE = "coway.mms";

	public SqlSessionTemplate getCowayMmsSessionTemplate() {
		return cowayMmsSessionTemplate;
	}

	public void setCowayMmsSessionTemplate(
			SqlSessionTemplate cowayMmsSessionTemplate) {
		this.cowayMmsSessionTemplate = cowayMmsSessionTemplate;
	}






}
