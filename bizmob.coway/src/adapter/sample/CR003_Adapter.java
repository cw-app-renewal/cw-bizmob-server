package adapter.sample;


import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import adapter.model.sample.CR003.CR003Request;
import adapter.model.sample.CR003.CR003Request_Body;
import adapter.model.sample.CR003.CR003Response;
import adapter.model.sample.CR003.CR003Response_Body;
import adapter.model.sample.CR003.CR003Response_Body_O_ITAB;
import adapter.model.sample.header.HWHeader;

//@Adapter(trcode = { "CR003" })
@Deprecated
public class CR003_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CR003_Adapter.class);

	@Autowired
	private SAPAdapter sapAdapter;
	
	private String trCode = "";
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		try {			
			//request data
			CR003Request request = new CR003Request(obj);
			HWHeader header = request.getHeader();
			trCode = header.getTrcode();
			CR003Request_Body reqBody = request.getBody();
			
			String id = reqBody.getI_UEMPL_NO();
			logger.debug("id :: " + id);
						
			//execute
			CR003Response_Body resBody = (CR003Response_Body) sapAdapter.execute("ZPDA_IF_SP_CODY_CR003", reqBody, new SapMapper());
					
			//response data
			CR003Response response = new CR003Response();
			response.setHeader(header);
			response.setBody(resBody);
			
			return makeResponse(obj, response.toJsonNode());
		
		} catch (AdapterException e) {
			logger.error("AdapterException :: ", e);
			return makeFailReesponse(e.getErrorCode(), e.getErrorMessage());
			
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(trCode + "IMPL0001", e.getMessage());
		}
		
	}

	class SapMapper extends AbstractSapMapper {
		@Override
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object obj) throws AdapterException {
			
			//import data mapping
			CR003Request_Body body = (CR003Request_Body) obj;
			
			setParametersToSapImportParam(body, function);	
			
			//JCoParameterList paramList = function.getImportParameterList();
			//paramList.setValue("I_UEMPL_NO", body.getI_UEMPL_NO());

			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) throws AdapterException {
			
			//export data mapping
			JCoTable table = function.getTableParameterList().getTable("O_ITAB");
			
			CR003Response_Body body = new CR003Response_Body();
			body.setO_ITAB(convertSapTableToObjectList(table, CR003Response_Body_O_ITAB.class));

//			int rowCount = table.getNumRows();
//			logger.debug("table row count = " + rowCount);
//			
//			for(int i=0 ; i<rowCount ; i++) {
//				table.setRow(i);
//				
//				CR003Response_Body_o_itab list = new CR003Response_Body_o_itab();
//				list.setJob_dt(table.getString("JOB_DT"));
//				list.setJob_seq(table.getString("JOB_SEQ"));
//				list.setSchedule_text(table.getString("SCHEDULE_TEXT"));
//				list.setText_gb(table.getString("TEXT_GB"));
//				
//				body.getO_itab().add(list);
//			}
			
			return body;
		}

		@Override
		public void verifySapResult(JCoFunction function) throws AdapterException {
			
			//execute result confirm
			JCoParameterList paramList = function.getExportParameterList();
			JCoStructure struct = paramList.getStructure("E_RETURN");
			String type = struct.getString("TYPE");
			if(type.equals("T") != true) {
				throw new AdapterException("ADAP9000", struct.getString("MESSAGE"));
			}
			
		}
		
	}
	

}
