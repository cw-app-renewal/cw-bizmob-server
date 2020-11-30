package adapter.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.common.util.JsonUtil;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class SapCommonMapper extends AbstractSapMapper {
	
	private String trCode = "";
	private DBAdapter dbAdapter = null;
	
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
		if(importParam != null) {
			JCoFieldIterator importIter = importParam.getFieldIterator();
			if(importIter != null) {
				while(importIter.hasNextField() == true) {
					
					JCoField field = importIter.nextField();
					String fieldName = field.getName();
					
					if(field.getType() == JCoMetaData.TYPE_STRUCTURE) {
						JsonNode objectNode = bodyNode.get(fieldName);
						
						JCoStructure structure = field.getStructure();
						JCoFieldIterator structIter = structure.getFieldIterator();
						while(structIter.hasNextField() == true) {
							JCoField structField = structIter.nextField();
							
							String structFieldName = structField.getName();
							if(objectNode.get(structFieldName) == null) {
								//throw new AdapterException("IMPT0001", "입력 데이터(Structure) 오류 : " + structFieldName + " 데이터를 확인해 주세요.");
								continue;
							} 
							Object structFieldValue = JsonUtil.getValue(objectNode, structFieldName);
							structure.setValue(structFieldName, structFieldValue);
						}
						
					} else {
						if(bodyNode.get(fieldName) == null) {
							//throw new AdapterException("IMPT0002", "입력 데이터(Object) 오류 : " + fieldName + " 데이터를 확인해 주세요.");
							continue;
						}
						Object value = JsonUtil.getValue(bodyNode, fieldName);
						function.getImportParameterList().setValue(fieldName, value);
					}
				}
			}
		}
		
		//table data
		JCoParameterList tableParam = function.getTableParameterList();
		if(tableParam != null) {
			JCoFieldIterator tableIter = tableParam.getFieldIterator();
			if(tableIter != null) {
				while(tableIter.hasNextField() == true) {
					JCoField field = tableIter.nextField();
					String tableName = field.getName();
					if(tableName.indexOf("I_") == 0) {			//import table의 경우, I_ 로 시작함
						JCoTable table = field.getTable();
						table.firstRow();
						
						ArrayNode arrayNode = (ArrayNode) bodyNode.get(field.getName());
						if(arrayNode == null || arrayNode.equals("") == true) {
							//throw new AdapterException("IMPT0003", "입력 데이터(Table) 오류 : " + field.getName() + " 데이터를 확인해 주세요.");
							continue;
						}
						for(JsonNode node : arrayNode) {
							
							table.appendRow();
							JCoFieldIterator tableFieldIter = table.getFieldIterator();
							while(tableFieldIter.hasNextField() == true) {
								
								JCoField tableField = tableFieldIter.nextField(); 
								String tableFieldName = tableField.getName();
								if(node.get(tableFieldName) == null) {
									//throw new AdapterException("IMPT0004", "입력 데이터(Table Field) 오류 : " + tableFieldName + "데이터를 확인해 주세요.");
									continue;
								}
								Object tableFieldValue = JsonUtil.getValue(node, tableFieldName);
								table.setValue(tableFieldName, tableFieldValue);
							}					
						}	
					}
				}
			}
		}	
		/*
		Iterator<String> iter = jsonNode.getFieldNames();
		while(iter.hasNext() == true) {
			
			String key = (String) iter.next();
	
			JsonNode node = jsonNode.get(key);		
			if(node.isArray() == true) {
				
				JCoTable table = function.getTableParameterList().getTable(key);
				table.firstRow();
				
				ArrayNode arrayNode = (ArrayNode) node.get(key);
				for(int i=0 ; i<arrayNode.size() ; i++) {
					table.appendRow();
					
					Iterator<String> arrayIter = arrayNode.get(i).getFieldNames();
					while(arrayIter.hasNext() == true) {
						String arrayKey = (String) arrayIter.next();
						Object arrayValue = JsonUtil.getValue(node, arrayKey);
						table.setValue(arrayKey, arrayValue);
					}
				}
									
			} else if(node.isObject() == true) {
				JCoStructure struct = function.getImportParameterList().getStructure(key);
				
				Iterator<String> objIter = node.getFieldNames();
				while(objIter.hasNext() == true) {
					String objKey = (String) objIter.next();
					Object objValue = JsonUtil.getValue(node, objKey);
					struct.setValue(objKey, objValue);
				}
				
			} else {
				Object value = JsonUtil.getValue(jsonNode, key);
				function.getImportParameterList().setValue(key, value.toString());
			}	        
		}
		*/
		return function;
	}

	@Override
	public Object mappingResponseSapDataToObject(JCoFunction function) 	throws AdapterException {
	
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		//export data
		JCoParameterList exportParam = function.getExportParameterList();
		if(exportParam != null) {
			JCoFieldIterator exportIter = exportParam.getFieldIterator();
			if(exportIter != null) {
				while(exportIter.hasNextField() == true) {
					JCoField field = exportIter.nextField();
					if(field.getType() == JCoMetaData.TYPE_STRUCTURE) {
						Map<String, Object> objectMap = new HashMap<String, Object>();
						
						JCoStructure structure = field.getStructure();
						JCoFieldIterator structIter = structure.getFieldIterator();
						while(structIter.hasNextField() == true) {
							JCoField structField = structIter.nextField();
							objectMap.put(structField.getName(), structField.getValue());
						}
						
						resMap.put(field.getName(), objectMap);
						
					} else {
						resMap.put(field.getName(), field.getValue());
					}
				}
			}
		}
		
		//table data
		JCoParameterList tableParam = function.getTableParameterList();
		if(tableParam != null) {
			JCoFieldIterator tableIter = function.getTableParameterList().getFieldIterator();
			if(tableIter != null) {
				while(tableIter.hasNextField() == true) {
					JCoField field = tableIter.nextField();
					String tableName = field.getName();
					if(tableName.indexOf("O_") == 0) {				//export table의 경우, O_ 로 시작함
						JCoTable table = field.getTable();
						int rowCount = table.getNumRows();
					
						List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
						
						for(int i=0 ; i<rowCount ; i++) {
							table.setRow(i);
							Map<String, Object> rowMap = new HashMap<String, Object>();
							
							for(JCoField tableField : table) {
								rowMap.put(tableField.getName(), tableField.getValue());
							}
							
							listMap.add(rowMap);
						}
						
						resMap.put(field.getName(), listMap);
					}
						
				}	
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
