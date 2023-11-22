package adapter;

import org.codehaus.jackson.JsonNode;

import com.mcnc.smart.hybrid.adapter.AdapterService;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.adapter.dispatcher.AbstractAdapterDispatcher;
import com.mcnc.smart.hybrid.adapter.loaders.IAdapterLoader;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.AbstractJsonAdaptor;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject.TYPE;

public class CowayLoaderAdapterDispatcher extends AbstractAdapterDispatcher implements AbstractJsonAdaptor {

	private AdapterService adapterService;
	
    /**
     * 생성자
     */
    public CowayLoaderAdapterDispatcher() {
        super();
        adapterService = new AdapterService(new CowayAnnotationBeanAdapterLoader());
    }

    /**
     * 생성자
     * @param loader Adapter Loader 객체
     */
    public CowayLoaderAdapterDispatcher(IAdapterLoader loader) {
        super();
        adapterService = new AdapterService(loader);
    }
 
    /**
     * 생성자
     * @param loader Adapter Loader 객체
     * @param groupId Adapter group ID
     */
    public CowayLoaderAdapterDispatcher(IAdapterLoader loader, String groupId) {
    	super(groupId);
    	adapterService = new AdapterService(loader);
    }
    
    public JsonAdaptorObject dispatch(JsonAdaptorObject request) {
       
		IAdapterJob adapterJob = null;
		
		boolean commonAdapter = getCommonAdapter(request);
		if(commonAdapter == true) {
			adapterJob = adapterService.getAdapter(getAdapterGroupId(), "COMMON");
		} else {
			String trcode = getTrcode(request);
			adapterJob = adapterService.getAdapter(getAdapterGroupId(), trcode);
		}
		
        JsonAdaptorObject resObj = adapterJob.onProcess(request);

        return resObj;
    }
	
	private boolean getCommonAdapter(JsonAdaptorObject request) {
		
		boolean commonAdapter = false;
		
		try {
			JsonNode rootNode = request.get(TYPE.REQUEST);
			JsonNode headerNode = rootNode.path(Codes._JSON_MESSAGE_HEADER);
			commonAdapter = headerNode.path("common_adapter").getBooleanValue();
		} catch (Exception e) {
			commonAdapter = false;
			
		}
		
		return commonAdapter;
	}
	

}
