package common.ftp;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class CowayImageCustomerTypeListDO {

	private String I_PROC_ID;
	private List<CowayImageCustomerTypeDO> I_ITAB;
	
	public String getI_PROC_ID() {
		return I_PROC_ID;
	}
	public void setI_PROC_ID(String I_PROC_ID) {
		this.I_PROC_ID = I_PROC_ID;
	}
	public List<CowayImageCustomerTypeDO> getI_ITAB() {
		return I_ITAB;
	}
	public void setI_ITAB(List<CowayImageCustomerTypeDO> I_ITAB) {
		this.I_ITAB = I_ITAB;
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	@Override
	public String toString() {
		return "CowayImageCustomerTypeListDO [I_PROC_ID=" + I_PROC_ID
				+ ", I_ITAB=" + I_ITAB + "]";
	}
	
	
	
}
