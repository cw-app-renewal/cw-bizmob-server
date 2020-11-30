package adapter.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import adapter.model.ERR002.ERR002Request;
import adapter.model.ERR002.ERR002Request_Body;
import adapter.model.ERR002.ERR002Request_Body_crushList;
import adapter.model.ERR002.ERR002Response;
import adapter.model.ERR002.ERR002Response_Body;
import adapter.model.header.CowayCommonHeader;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

@Adapter(trcode = { "ERR002" })
public class ERR002_Adapter extends AbstractTemplateAdapter implements
		IAdapterJob {

	private static final ILogger logger = LoggerService
			.getLogger(ERR002_Adapter.class);
	@Autowired
	private DBAdapter dbAdapter;

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		//request
		ERR002Request request = new ERR002Request(obj);
		CowayCommonHeader reqHeader = request.getHeader();
		ERR002Request_Body reqBody = request.getBody();
		
		ERR002Response response = new ERR002Response();
		ERR002Response_Body resBody = new ERR002Response_Body();
		
		int insertCnt = saveCrushList(reqBody);

		reqHeader.setResult(true);
		resBody.setCrushListCnt(String.valueOf(reqBody.getCrushList().size()));
		resBody.setInsertListCnt(String.valueOf(insertCnt));
		
		
		//response
		JsonAdaptorObject resObj = new JsonAdaptorObject();
		response.setHeader(reqHeader);
		response.setBody(resBody);
		
		return makeResponse(resObj, response.toJsonNode());
	}
	
	// crush list 저장
	@SuppressWarnings("unchecked")
	private int saveCrushList(ERR002Request_Body reqBody) {
		int dbInsertCnt;
		List<ERR002Request_Body_crushList> crushList = (List<ERR002Request_Body_crushList>) reqBody.getCrushList();
		String user_id = reqBody.getUser_id();
		dbInsertCnt = 0;
		
		for ( ERR002Request_Body_crushList crushInfo : crushList ) {
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put("user_id", user_id);
			inputMap.put("crushInfo", crushInfo);
			try{
				int resultCnt = dbAdapter.insert("BizMOBDB", "CowayLogSqlMap.saveLog", inputMap);					
				dbInsertCnt += resultCnt;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dbInsertCnt;
	}
}
