package adapter.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.common.util.JsonUtil;
import com.sap.conn.jco.*;
import com.sap.conn.jco.rt.json.JCoSerializationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SapCommonMapper extends AbstractSapMapper {
	
	private String trCode = "";
	private DBAdapter dbAdapter = null;

	private static final Logger logger = LoggerFactory.getLogger(SapCommonMapper.class);

	public SapCommonMapper(String trCode, DBAdapter dbAdapter) {
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
		
		JsonNode bodyNode = AdapterUtil.ConvertJsonNode(param);

		//import data
		JCoParameterList importParam = function.getImportParameterList();

		if (importParam != null) {
			try {
				function.getImportParameterList().fromJSON(bodyNode.toString());
			} catch (JCoSerializationException e) {
				logger.error("", e);

				JCoParameterFieldIterator jCoParameterFieldIterator = function.getImportParameterList().getParameterFieldIterator();
				while (jCoParameterFieldIterator.hasNextField()) {
					JCoParameterField field = jCoParameterFieldIterator.nextParameterField();
					field.setValue(JsonUtil.getValue(bodyNode, field.getName()));
				}
			}

			logger.info("getImportParameterList + " + function.getImportParameterList().toJSON());
		}

		//table data
		JCoParameterList tableParam = function.getTableParameterList();

		if (tableParam != null) {

			try {
				logger.info("bodyNode.toString() + " + bodyNode.toString());
				tableParam.fromJSON(bodyNode.toString());
			} catch (JCoSerializationException e) {
				logger.error("", e);

				JCoListMetaData jCoListMetaData = tableParam.getListMetaData();

				for (int i = 0; i < jCoListMetaData.getFieldCount(); i++) {

					if (jCoListMetaData.getName(i).startsWith("I_")) {

						ArrayNode arrayNode = (ArrayNode) bodyNode.get(jCoListMetaData.getName(i));

						if (arrayNode == null || arrayNode.equals("")) {
							//throw new AdapterException("IMPT0003", "입력 데이터(Table) 오류 : " + field.getName() + " 데이터를 확인해 주세요.");
							continue;
						}

						if (arrayNode.size() < 1) {
							continue;
						}

						JCoTable table = tableParam.getTable(i);
						table.deleteAllRows();

						table.firstRow();

						for (JsonNode node : arrayNode) {

							table.appendRow();

							JCoFieldIterator tableFieldIter = table.getFieldIterator();

							while(tableFieldIter.hasNextField()) {

								JCoField tableField = tableFieldIter.nextField();
								if (node.get(tableField.getName()) == null) {
									//throw new AdapterException("IMPT0004", "입력 데이터(Table Field) 오류 : " + tableFieldName + "데이터를 확인해 주세요.");
									continue;
								}
								table.setValue(tableField.getName(), JsonUtil.getValue(node, tableField.getName()));
							}
						}

					}
				}
			}

			logger.info("getTableParameterList + " + tableParam.toJSON());
		}

		return function;
	}

	@Override
	public Object mappingResponseSapDataToObject(JCoFunction function) 	throws AdapterException {
	
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new GsonBuilder().create();
		Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
		
		//export data
		JCoParameterList exportParam = function.getExportParameterList();
		//table data
		JCoParameterList tableParam = function.getTableParameterList();

		if (exportParam != null) {
			HashMap<String, Object> exportParamMap = gson.fromJson(exportParam.toJSON(), type);
			if (exportParamMap != null) {
				resMap.putAll(exportParamMap);
			}
		}

		if (tableParam != null) {
			HashMap<String, Object> tableParamMap = gson.fromJson(tableParam.toJSON(), type);
			if (tableParamMap != null) {
				resMap.putAll(tableParamMap);
			}
		}

		return resMap;
	}

	@Override
	public void verifySapResult(JCoFunction function) throws AdapterException {
		
//		JCoParameterList paramList = function.getExportParameterList();
//		JCoStructure struct = paramList.getStructure("E_RETURN");
//		String type = struct.getString("TYPE");
//		if(type.equals("T") != true) {
//			SapCommonException sapCommonException = new SapCommonException(trCode, struct.getString("MESSAGE"), dbAdapter);
//			throw new AdapterException(sapCommonException.getErrCode(), sapCommonException.getErrMsg());
//		}
	}

}
