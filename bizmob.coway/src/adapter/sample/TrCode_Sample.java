package adapter.sample;

import adapter.model.sample.TrCode.TrCodeRequest;
import adapter.model.sample.TrCode.TrCodeRequest_Body;
import adapter.model.sample.TrCode.TrCodeResponse;
import adapter.model.sample.TrCode.TrCodeResponse_Body;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;


//@Adapter(trcode = { "TRCODE" })
@Deprecated
public class TrCode_Sample extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(TrCode_Sample.class);

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String errCode = "ERRORCODE";
		
		try {	
			// Request Data
			TrCodeRequest request = new TrCodeRequest(obj);
			TrCodeRequest_Body reqBody = request.getBody();
			String data = reqBody.getData();
			
			String result = "Hello !!";
			
			// Response Data
			TrCodeResponse_Body resBody = new TrCodeResponse_Body();
			resBody.setResult(result);
			
			TrCodeResponse response = new TrCodeResponse();
			response.setHeader(request.getHeader());
			response.setBody(resBody);
			
	        return makeResponse(obj, response.toJsonNode());
	        
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(errCode, e.getMessage());
		}
	}
	
	
}
