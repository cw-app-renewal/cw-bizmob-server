package adapter.login;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

@Deprecated
public class CCR000_ADT_CodyLogin extends AbstractTemplateAdapter implements IAdapterJob {

	/*
	 * 코디용 인증 - 
	 * CGR000 사용
	 *  common_adapter = true
	 *  rfc_name = ZSMT_IF_SP_CODY_RD002
	 */
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
