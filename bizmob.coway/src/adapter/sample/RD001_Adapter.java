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

import adapter.model.sample.RD001.RD001Request;
import adapter.model.sample.RD001.RD001Request_Body;
import adapter.model.sample.RD001.RD001Response;
import adapter.model.sample.RD001.RD001Response_Body;
//@Adapter(trcode = { "RD001" })
@Deprecated
public class RD001_Adapter extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(RD001_Adapter.class);
	
	@Autowired
	private SAPAdapter sapAdapter;
	
	private String trCode = "";
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {

		try {		
			RD001Request request = new RD001Request(obj);
			
			RD001Response_Body resBody = null;
			
			resBody = (RD001Response_Body) sapAdapter.execute("ZPDA_IF_SP_CSDR_RD001", request.getBody(), new SapMapper());
			
			RD001Response response = new RD001Response();
			response.setHeader(request.getHeader());
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
		public JCoFunction mappingRequestObjectToSapData(JCoFunction function, Object arg1) throws AdapterException {
			
			RD001Request_Body body = (RD001Request_Body)arg1;
			setParametersToSapImportParam(body, function);		
			//JCoParameterList paramList = function.getImportParameterList();
			//paramList.setValue("I_INVNR", body.getI_INVNR());
			
			return function;
		}
		
		@Override
		public Object mappingResponseSapDataToObject(JCoFunction function) throws AdapterException {
			
			JCoParameterList paramList = function.getExportParameterList();
			
			RD001Response_Body resBody = new RD001Response_Body();	
			resBody.setO_PERNR(paramList.getString("O_PERNR"));
			resBody.setO_ATWRT(paramList.getString("O_ATWRT"));
			resBody.setO_USER_TP(paramList.getString("O_USER_TP"));
			resBody.setO_SNAME(paramList.getString("O_SNAME"));
			resBody.setO_DEPT_CD(paramList.getString("O_DEPT_CD"));
			resBody.setO_DEPT_NM(paramList.getString("O_DEPT_NM"));
			
			return resBody;
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
