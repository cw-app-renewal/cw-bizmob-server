package adapter.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.CDS002.CDS002Request;
import adapter.model.CDS002.CDS002Request_Body;
import adapter.model.CDS002.CDS002Response;
import adapter.model.CDS002.CDS002Response_Body;
import adapter.model.header.CowayCommonHeader;
//@Adapter(trcode = { "CDS002" })
@Deprecated
public class CDS002_ADT_BomTableVerChecker extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CDS002_ADT_BomTableVerChecker.class);
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String errCode = "ADAP0000";
		
		try {
			//request
			CDS002Request request = new CDS002Request(obj);
			CowayCommonHeader reqHeader = request.getHeader();
			CDS002Request_Body reqBody = request.getBody();
			
			//request body data
			String syncDate = reqBody.getSYNC_DATE();
			String syncTime = reqBody.getSYNC_TIME();
			
			//response body data
			CDS002Response_Body resBody = new CDS002Response_Body();
			resBody.setFULL_SYNC("Y");
			
			//response 
			CDS002Response response = new CDS002Response();
			response.setHeader(reqHeader);
			response.setBody(resBody);
			
			return makeResponse(obj, response.toJsonNode());
			
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailResponse(errCode, e.getLocalizedMessage());
		}
	}

}
