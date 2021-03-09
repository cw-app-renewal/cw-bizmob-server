package adapter.common;

import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;

public class SapCommonMapperException extends SapCommonMapper {
	
	private String trCode = "";
	private DBAdapter dbAdapter = null;
	
	public SapCommonMapperException(String trCode, DBAdapter dbAdapter) {
		super(trCode, dbAdapter);
		this.trCode = trCode;
		this.dbAdapter = dbAdapter;
	}
	
	public String getTrCode() {
		return trCode;
	}
	public void setTrCode(String trCode) {
		this.trCode = trCode;
	}
	
	public DBAdapter getDbAdapter() {
		return dbAdapter;
	}
	public void setDbAdapter(DBAdapter dbAdapter) {
		this.dbAdapter = dbAdapter;
	}
	
	@Override
	public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object param) throws AdapterException {
		
		super.mappingRequestObjectToSapData(function, param);
	
		return function;
	}

	@Override
	public Object mappingResponseSapDataToObject(JCoFunction function) 	throws AdapterException {
		
		return super.mappingResponseSapDataToObject(function);
	}

	@Override
	public void verifySapResult(JCoFunction function) throws AdapterException {
		
		JCoParameterList 	paramList 	= function.getExportParameterList();
		JCoStructure 		struct 		= paramList.getStructure("E_RETURN");
		String 				type 		= struct.getString("TYPE");
		if(type.equals("T") != true) {
			SapCommonException sapCommonException = new SapCommonException(trCode, struct.getString("MESSAGE"), dbAdapter);
			throw new AdapterException(sapCommonException.getErrCode(), sapCommonException.getErrMsg());
		}
	}

}
