package adapter.sample;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;

import adapter.model.sample.CR011.CR011Request;
import adapter.model.sample.CR011.CR011Request_Body;
import adapter.model.sample.CR011.CR011Response;
import adapter.model.sample.CR011.CR011Response_Body;
import adapter.model.sample.header.HWHeader;
//@Adapter(trcode = { "CR011" })
@Deprecated
public class CR011_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CR011_Adapter.class);
	
	@Autowired
	private SAPAdapter sapAdapter;

	private String trCode = "";

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		try {		
			//request data
			CR011Request request = new CR011Request(obj);
			HWHeader header = request.getHeader();
			trCode = header.getTrcode();
			CR011Request_Body reqBody = request.getBody();
			logger.debug(reqBody.toString());
						
			//execute
			CR011Response_Body resBody = (CR011Response_Body) sapAdapter.execute("ZPDA_IF_SP_CODY_CR011", reqBody, new SapMapper());
					
			//response data
			CR011Response response = new CR011Response();
			response.setHeader(header);
			response.setBody(resBody);
			
			return makeResponse(obj, response.toJsonNode());
		
		} catch (AdapterException e) {
			logger.error("AdapterException :: ", e);
			return makeFailResponse(e.getErrorCode(), e.getErrorMessage());
			
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailResponse(trCode + "IMPL0001", e.getMessage());
		}
	}

	class SapMapper extends AbstractSapMapper {
		@Override
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object obj)  throws AdapterException {
			
			//import data mapping
			CR011Request_Body body = (CR011Request_Body) obj;
			
			setParametersToSapImportParam(body, function);	
			
//			JCoParameterList paramList = function.getImportParameterList();
//			paramList.setValue("I_INVNR", body.getI_INVNR());
//			paramList.setValue("I_RSCH_DT", body.getI_SRCH_DT());
//			paramList.setValue("I_UEMPL_NO", body.getI_UEMPL_NO());
//			paramList.setValue("I_COM_CD", body.getI_COM_CD());

			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function)  throws AdapterException {
		
			//export data mapping
			String o_start_dt = function.getExportParameterList().getString("O_START_DT");
			
			CR011Response_Body body = new CR011Response_Body();
			body.setO_START_DT(o_start_dt);
						
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
