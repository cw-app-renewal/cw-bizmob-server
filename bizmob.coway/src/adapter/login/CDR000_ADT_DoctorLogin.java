package adapter.login;

import adapter.model.CDR000.CDR000Request;
import adapter.model.CDR000.CDR000Request_Body;
import adapter.model.CDR000.CDR000Response;
import adapter.model.CDR000.CDR000Response_Body;
import adapter.model.header.CowayCommonHeader;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

@Deprecated
public class CDR000_ADT_DoctorLogin extends AbstractTemplateAdapter implements IAdapterJob {

	/*
	 * 닥터용 인증 - 
	 * CGR000 사용
	 *  common_adapter = true
	 *  rfc_name = ZSMT_IF_SP_CSDR_RD002
	 */
	
	private ILogger logger = LoggerService.getLogger(CDR000_ADT_DoctorLogin.class);
			
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String errCode = "ADAP0000";
		
		try {
			//request
			CDR000Request request = new CDR000Request(obj);
			CowayCommonHeader reqHeader = request.getHeader();
			logger.debug("reqHeader = " + reqHeader.toString());
			CDR000Request_Body reqBody = request.getBody();
			logger.debug("reqBody = " + reqBody.toString());
			
			//request body data
			//String I_PERNR = reqBody.getI_PERNR();
			//String I_ATWRT = reqBody.getI_ATWRT();
			
			//response body data
			CDR000Response_Body resBody = new CDR000Response_Body();
			
			//response
			CDR000Response response = new CDR000Response();
			response.setHeader(reqHeader);
			response.setBody(resBody);
			
			//
			return makeResponse(obj, response.toJsonNode());
			
			
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(errCode, e.getLocalizedMessage());
		}
	}

}
