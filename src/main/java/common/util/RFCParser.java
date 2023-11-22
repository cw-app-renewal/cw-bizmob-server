package common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.SapConnector;
import com.mcnc.common.util.JsonUtil;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

public class RFCParser {
	
	private Logger logger = LoggerFactory.getLogger(RFCParser.class);
	
	public ObjectNode cacheClear(String rfcName, boolean isall, SapConnector sapConnector) {
		boolean 				resultFlag 			= false;
		JCoRepository 			repository 			= null;
		ObjectNode 				resNode 			= JsonUtil.getNodeFactory().objectNode();
		
		try {
			repository = sapConnector.getJcoDestination().getRepository();
			if(isall) {
				repository.clear();
			} else {
				repository.removeFunctionTemplateFromCache(rfcName);
			}
			
			resultFlag = true;
			
		} catch (JCoException e) {
			resNode.put("error", e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (AdapterException e) {
			resNode.put("error", e.getMessage());
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			resNode.put("error", e.getMessage());
			logger.error(e.getMessage(), e);
		}
		
		resNode.put("result", resultFlag);
		return resNode;
	}
	
	public ObjectNode parseFunction(String rfcName, SapConnector sapConnector) {
		JCoRepository 			repository 			= null;
		JCoFunction 			function			= null;
		
		ObjectNode 				resultNode 			= JsonUtil.objectNode();
		ObjectNode 				resNode 			= JsonUtil.objectNode();
		
		try {
			
			repository 		= sapConnector.getJcoDestination().getRepository();
			
			function 		= repository.getFunction(rfcName);
			
			JCoParameterList 	importParameterList 	= function.getImportParameterList();
			JCoParameterList 	exportParameterList 	= function.getExportParameterList();
			JCoParameterList 	tableParameterList 		= function.getTableParameterList();

			Map<String, Object> rfcMap 					= new HashMap<String, Object>();
			
			parseJCoParameterList(importParameterList, rfcMap, false, "IMPORT");
			parseJCoParameterList(exportParameterList, rfcMap, false, "OUTPUT");
			parseJCoParameterList(tableParameterList, rfcMap, true, "TABLE");
			
			
			resNode = JsonUtil.toObjectNode(rfcMap);
			resultNode.put(rfcName, resNode);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return resultNode;
	}
	
	private void parseJCoParameterList(JCoParameterList jcoParameterList, Map<String, Object> rfcMap, boolean isTable, String type) {
		
		if( jcoParameterList != null ) {
			if(isTable) {
				
				int count = jcoParameterList.getMetaData().getFieldCount();		
				Map<String, Object> 		tableMap = null;
				for(int i=0; i < count; i++) {
					
					String 						tableName 		= jcoParameterList.getMetaData().getName(i);
					JCoTable 					table 			= jcoParameterList.getTable(tableName);
					
					tableMap = new HashMap<String, Object>();
					
					JCoRecordMetaData recordMetaData = table.getRecordMetaData();
					tableMap.put("recName", recordMetaData.getName());
					int fieldCount = recordMetaData.getFieldCount();
					
					Map<String, Object> dataMap = new HashMap<String, Object>();
					for(int j=0; j < fieldCount; j++) {
						
						String 	fieldName 		= recordMetaData.getName(j);
						String 	fieldType 		= recordMetaData.getTypeAsString(j);
						int 	fieldLength 	= recordMetaData.getLength(j);
						String 	description 	= recordMetaData.getDescription(j);
						
						dataMap.put(fieldName, fieldType + "(" + fieldLength + "," + description + ")");
						
					}
					List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
					listMap.add(dataMap);
					tableMap.put(tableName, listMap);
				}
				
				rfcMap.put("TABLE", tableMap);
				
			} else {
				JCoMetaData 			metaData 		= jcoParameterList.getMetaData();
				String 					metaName 		= metaData.getName();
				Map<String, Object> 	metaMap 		= new HashMap<String, Object>();
				
				int 					count 			= metaData.getFieldCount();
				
				for(int i=0; i < count; i++) {
					String 	fieldName 		= metaData.getName(i);
					String 	fieldType 		= metaData.getTypeAsString(i);
					int 	fieldLength 	= metaData.getLength(i);
					String description 		= metaData.getDescription(i);
					
					metaMap.put(fieldName, fieldType + "(" + fieldLength + "," + description + ")");
				}
				rfcMap.put(metaName, metaMap);
			}
		} else {
			rfcMap.put(type, "null");
		}
	}
}
