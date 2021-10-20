package adapter.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.sample.TrCode.TrCodeRequest;
import adapter.model.sample.TrCode.TrCodeRequest_Body;
import adapter.model.sample.TrCode.TrCodeResponse;
import adapter.model.sample.TrCode.TrCodeResponse_Body;

//@Adapter(trcode = { "TRCODE" })
@Deprecated
public class TrCode_Sample extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(TrCode_Sample.class);

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
			return makeFailResponse(errCode, e.getMessage());
		}
	}
	
	
}
