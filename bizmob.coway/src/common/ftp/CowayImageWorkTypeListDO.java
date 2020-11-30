package common.ftp;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class CowayImageWorkTypeListDO {

	private String I_PROC_ID;
	private List<CowayImageWorkTypeDO> I_ITAB;
	
	public String getI_PROC_ID() {
		return I_PROC_ID;
	}
	public void setI_PROC_ID(String I_PROC_ID) {
		this.I_PROC_ID = I_PROC_ID;
	}
	public List<CowayImageWorkTypeDO> getI_ITAB() {
		return I_ITAB;
	}
	public void setI_ITAB(List<CowayImageWorkTypeDO> I_ITAB) {
		this.I_ITAB = I_ITAB;
	}
	
	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	@Override
	public String toString() {
		return "CowayImageWorkTypeListDO [I_PROC_ID=" + I_PROC_ID + ", I_ITAB="
				+ I_ITAB + "]";
	}
	
	
}
