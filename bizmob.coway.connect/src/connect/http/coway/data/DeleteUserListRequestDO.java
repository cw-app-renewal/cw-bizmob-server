package connect.http.coway.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class DeleteUserListRequestDO {

	private String rowIdNoArray01 = "";			//삭제할 사용자ID #01
	private String rowIdNoArray02 = "";			//삭제할 사용자ID #02
	private String rowIdNoArray03 = "";			//삭제할 사용자ID #03
	private String rowIdNoArray04 = "";			//삭제할 사용자ID #04
	private String rowIdNoArray05 = "";			//삭제할 사용자ID #05

	private List<String> rowIdArray = null;

	public String getRowIdNoArray01() {
		return rowIdNoArray01;
	}
	public void setRowIdNoArray01(String rowIdNoArray01) {
		this.rowIdNoArray01 = rowIdNoArray01;
	}
	public String getRowIdNoArray02() {
		return rowIdNoArray02;
	}
	public void setRowIdNoArray02(String rowIdNoArray02) {
		this.rowIdNoArray02 = rowIdNoArray02;
	}
	public String getRowIdNoArray03() {
		return rowIdNoArray03;
	}
	public void setRowIdNoArray03(String rowIdNoArray03) {
		this.rowIdNoArray03 = rowIdNoArray03;
	}
	public String getRowIdNoArray04() {
		return rowIdNoArray04;
	}
	public void setRowIdNoArray04(String rowIdNoArray04) {
		this.rowIdNoArray04 = rowIdNoArray04;
	}
	public String getRowIdNoArray05() {
		return rowIdNoArray05;
	}
	public void setRowIdNoArray05(String rowIdNoArray05) {
		this.rowIdNoArray05 = rowIdNoArray05;
	}
	public List<String> getRowIdArray() {
		return rowIdArray;
	}
	public void setRowIdArray(List<String> rowIdArray) {
		this.rowIdArray = rowIdArray;
	}

	public JsonNode toJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(this);
	}
	@Override
	public String toString() {
		return "DeleteUserListRequestDO [rowIdNoArray01=" + rowIdNoArray01
				+ ", rowIdNoArray02=" + rowIdNoArray02 + ", rowIdNoArray03="
				+ rowIdNoArray03 + ", rowIdNoArray04=" + rowIdNoArray04
				+ ", rowIdNoArray05=" + rowIdNoArray05 + ", rowIdArray="
				+ rowIdArray + "]";
	}
	
	public ArrayList<NameValuePair> getDeleteUserListNameValueLimitList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("rowIdArray", this.rowIdNoArray01));
		qparams.add(new BasicNameValuePair("rowIdArray", this.rowIdNoArray02));
		qparams.add(new BasicNameValuePair("rowIdArray", this.rowIdNoArray03));
		qparams.add(new BasicNameValuePair("rowIdArray", this.rowIdNoArray04));
		qparams.add(new BasicNameValuePair("rowIdArray", this.rowIdNoArray05));
		
		return qparams;
	}	
	
	public ArrayList<NameValuePair> getDeleteUserListNameValueList() {
		
		ArrayList< NameValuePair > qparams = new ArrayList<NameValuePair>();
		
		for(String rowId : this.rowIdArray) {
			qparams.add(new BasicNameValuePair("rowIdArray", rowId));
		}
				
		return qparams;
	}	
}
