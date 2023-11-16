package adapter.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.model.CDS000.CDS000Request;
import adapter.model.CDS000.CDS000Request_Body;
import adapter.model.CDS000.CDS000Response;
import adapter.model.CDS000.CDS000Response_Body;
import adapter.model.header.CowayCommonHeader;
//@Adapter(trcode = { "CDS000" })
@Deprecated
public class CDS000_ADT_WorkTableVerChecker extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CDS000_ADT_WorkTableVerChecker.class);
	
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String errCode = "ADAP0000";
		
		try {
			//request
			CDS000Request request = new CDS000Request(obj);
			CowayCommonHeader reqHeader = request.getHeader();
			CDS000Request_Body reqBody = request.getBody();
			
			//request body data
			String syncDate = reqBody.getSYNC_DATE();
			String syncTime = reqBody.getSYNC_TIME();
			
			String schemaUpdate = "";
			
			if(syncDate == null || syncDate.equals("") == true) {
				//sync date가 null인 경우는 전체 테이블 스키마 업데이트
				schemaUpdate = "Y";
			} else {
				//스키마 생성 일자와 sync 일자를 비교 확인
				//스키마 생성 일자가 더 큰 경우 스키마 업데이트
				schemaUpdate = "N";
			}
			
			//response body data
			CDS000Response_Body resBody = new CDS000Response_Body();
			resBody.setNEW_SCHEMA(schemaUpdate);
			
			//response body
			CDS000Response response = new CDS000Response();
			response.setHeader(reqHeader);
			response.setBody(resBody);

			//return
			return makeResponse(obj, response.toJsonNode());
		
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailResponse(errCode, e.getLocalizedMessage());
		}
	}

}
