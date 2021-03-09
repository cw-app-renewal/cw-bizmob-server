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

import adapter.model.sample.CD001.CD001Request;
import adapter.model.sample.CD001.CD001Request_Body;
import adapter.model.sample.CD001.CD001Response;
import adapter.model.sample.CD001.CD001Response_Body;
import adapter.model.sample.header.HWHeader;

//@Adapter(trcode = { "CD001" })
@Deprecated
public class CD001_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CD001_Adapter.class);

	@Autowired
	private SAPAdapter sapAdapter;
	
	private String trCode = "";
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		try {		
			//request data
			CD001Request request = new CD001Request(obj);
			HWHeader header = request.getHeader();
			trCode = header.getTrcode();
			CD001Request_Body reqBody = request.getBody();
			
			String id = reqBody.getI_UEMPL_NO();
			String status = reqBody.getI_STATUS();
			logger.debug("id :: " + id);
			logger.debug("status :: " + status);
						
			//execute
			CD001Response_Body resBody = (CD001Response_Body) sapAdapter.execute("1ZPDA_IF_SP_CODY_CD001", reqBody, new SapMapper());
					
			//response data
			CD001Response response = new CD001Response();
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
			CD001Request_Body body = (CD001Request_Body) obj;
			
			setParametersToSapImportParam(body, function);		
//			JCoParameterList paramList = function.getImportParameterList();
//			paramList.setValue("I_UEMPL_NO", body.getI_UEMPL_NO());
//			paramList.setValue("I_STATUS", body.getI_STATUS());
			
			return function;			
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) throws AdapterException  {
		
			//export data mapping
			CD001Response_Body body = new CD001Response_Body();
			
			return body;		
		}

		@Override
		public void verifySapResult(JCoFunction function) throws AdapterException {

			//execute result verify
			JCoParameterList paramList = function.getExportParameterList();
			JCoStructure struct = paramList.getStructure("E_RETURN");
			String type = struct.getString("TYPE");
			if(type.equals("T") != true) {
				throw new AdapterException("ADAP9000", struct.getString("MESSAGE"));
			}		
		}
		
	}
	
	
	


}
