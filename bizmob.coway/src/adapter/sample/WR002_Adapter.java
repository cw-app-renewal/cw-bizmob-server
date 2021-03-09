package adapter.sample;

import java.util.List;

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
import com.sap.conn.jco.JCoTable;

import adapter.model.sample.WR002.WR002Request;
import adapter.model.sample.WR002.WR002Request_Body;
import adapter.model.sample.WR002.WR002Request_Body_I_ITAB;
import adapter.model.sample.WR002.WR002Response;
import adapter.model.sample.WR002.WR002Response_Body;
import adapter.model.sample.header.HWHeader;

//@Adapter(trcode = { "WR002" })
@Deprecated
public class WR002_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(WR002_Adapter.class);
	
	@Autowired
	private SAPAdapter sapAdapter;

	private String trCode = "";

	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		String trCode = "";
		
		try {			
			//request data
			WR002Request request = new WR002Request(obj);
			HWHeader header = request.getHeader();
			trCode = header.getTrcode();
			WR002Request_Body reqBody = request.getBody();
			logger.debug(reqBody.toString());
						
			//execute
			WR002Response_Body resBody = (WR002Response_Body) sapAdapter.execute("ZPDA_IF_SP_CODY_WR002", reqBody, new SapMapper());
					
			//response data
			WR002Response response = new WR002Response();
			response.setHeader(header);
			response.setBody(resBody);
			
			return makeResponse(obj, response.toJsonNode());
		
		} catch (AdapterException e) {
			logger.error("AdapterException :: ", e);
			return makeFailReesponse(trCode + e.getErrorCode(), e.getErrorMessage());
			
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(trCode + "IMPL0001", e.getMessage());
		}
	}

	class SapMapper extends AbstractSapMapper {
		@Override
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object obj) throws AdapterException {
			
			WR002Request_Body body = (WR002Request_Body) obj;
			
			JCoParameterList paramList = function.getImportParameterList();
			paramList.setValue("I_START_DT", body.getI_START_DT());
			paramList.setValue("I_PROC_ID", body.getI_PROC_ID());
			//setParametersToSapImportParam(body, function);
			
			List<WR002Request_Body_I_ITAB> list = body.getI_ITAB();
			
			JCoTable table = function.getTableParameterList().getTable("I_ITAB");
			
			convertObjectListToSapTable(list, table);
			
			return function;
		}

		@Override
		public Object mappingResponseSapDataToObject(JCoFunction arg0) throws AdapterException {
			// TODO Auto-generated method stub
			return null;
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
