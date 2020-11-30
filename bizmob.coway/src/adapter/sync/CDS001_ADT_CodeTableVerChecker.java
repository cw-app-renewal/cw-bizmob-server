package adapter.sync;


import adapter.model.CDS001.CDS001Request;
import adapter.model.CDS001.CDS001Request_Body;
import adapter.model.CDS001.CDS001Response;
import adapter.model.CDS001.CDS001Response_Body;
import adapter.model.header.CowayCommonHeader;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

//@Adapter(trcode = { "CDS001" })
@Deprecated
public class CDS001_ADT_CodeTableVerChecker extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CDS001_ADT_CodeTableVerChecker.class);
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String errCode = "ADAP0000";
		
		try {
			//request
			CDS001Request request = new CDS001Request(obj);
			CowayCommonHeader reqHeader = request.getHeader();
			CDS001Request_Body reqBody = request.getBody();
			
			//request body data
			String syncDate = reqBody.getSYNC_DATE();
			String syncTime = reqBody.getSYNC_TIME();
			
			
						
			//response body
			CDS001Response_Body resBody = new CDS001Response_Body();
			resBody.setFULL_SYNC("Y");
			
			//response
			CDS001Response response = new CDS001Response();
			response.setHeader(reqHeader);
			response.setBody(resBody);
			
			return makeResponse(obj, response.toJsonNode());
			
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(errCode, e.getLocalizedMessage());
		}
	}

}
