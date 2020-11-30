package adapter.sample;

import org.springframework.beans.factory.annotation.Autowired;

import adapter.model.sample.RD044.RD044Request;
import adapter.model.sample.RD044.RD044Request_Body;
import adapter.model.sample.RD044.RD044Response;
import adapter.model.sample.RD044.RD044Response_Body;
import adapter.model.sample.RD044.RD044Response_Body_O_ITAB;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.sap.AbstractSapMapper;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

//@Adapter(trcode = { "RD044" })
@Deprecated
public class RD044_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(RD044_Adapter.class);
	
	@Autowired
	private SAPAdapter sapAdapter;

	private String trCode = "";

	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		try {
			RD044Request request = new RD044Request(obj);
			
			RD044Response_Body resBody = null;
			
			resBody = (RD044Response_Body) sapAdapter.execute("ZPDA_IF_SP_CODY_RD044", request.getBody(), new SapMapper());
			
			RD044Response response = new RD044Response();
			response.setHeader(request.getHeader());
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
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object arg1) {
			
			RD044Request_Body body = (RD044Request_Body)arg1;
			setParametersToSapImportParam(body, function);	
			
//			JCoParameterList paramList = function.getImportParameterList();
//			paramList.setValue("I_INVNR", body.getI_INVNR());
//			paramList.setValue("I_UEMPL_NO", body.getI_UEMPL_NO());
//			paramList.setValue("I_STD_YM", body.getI_STD_YM());
		
			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) throws AdapterException {
				
			JCoTable dataTable = function.getTableParameterList().getTable("O_ITAB");
			
			RD044Response_Body body = new RD044Response_Body();
			body.setO_ITAB(convertSapTableToObjectList(dataTable, RD044Response_Body_O_ITAB.class));
			
			return body;
		}

		@Override
		public void verifySapResult(JCoFunction function) throws AdapterException {
			
			JCoParameterList paramList = function.getExportParameterList();
			JCoStructure struct = paramList.getStructure("E_RETURN");
			String type = struct.getString("TYPE");
			if(type.equals("T") != true) {
				throw new AdapterException("ADAP9000", struct.getString("MESSAGE"));
			}
		}
		
	}

}
