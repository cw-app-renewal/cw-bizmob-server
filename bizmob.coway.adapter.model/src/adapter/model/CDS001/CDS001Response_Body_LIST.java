package adapter.model.CDS001;


import org.codehaus.jackson.JsonNode;

/**
 * 
 */
public class CDS001Response_Body_LIST {
	/**
	 * OTH_CD
	 */
	private String OTH_CD;
	/**
	 * DB_FLAG
	 */
	private String DB_FLAG;
	/**
	 * OTH_CD_NM
	 */
	private String OTH_CD_NM;
	/**
	 * OTH_CLASS
	 */
	private String OTH_CLASS;

	public CDS001Response_Body_LIST() {
	}

	public CDS001Response_Body_LIST(JsonNode jsonNode) {
		this.OTH_CD = jsonNode.path("OTH_CD").getTextValue();
		this.DB_FLAG = jsonNode.path("DB_FLAG").getTextValue();
		this.OTH_CD_NM = jsonNode.path("OTH_CD_NM").getTextValue();
		this.OTH_CLASS = jsonNode.path("OTH_CLASS").getTextValue();
	}

	public String getOTH_CD() {
		return this.OTH_CD;
	}

	public void setOTH_CD(String OTH_CD) {
		this.OTH_CD = OTH_CD;
	}

	public String getDB_FLAG() {
		return this.DB_FLAG;
	}

	public void setDB_FLAG(String DB_FLAG) {
		this.DB_FLAG = DB_FLAG;
	}

	public String getOTH_CD_NM() {
		return this.OTH_CD_NM;
	}

	public void setOTH_CD_NM(String OTH_CD_NM) {
		this.OTH_CD_NM = OTH_CD_NM;
	}

	public String getOTH_CLASS() {
		return this.OTH_CLASS;
	}

	public void setOTH_CLASS(String OTH_CLASS) {
		this.OTH_CLASS = OTH_CLASS;
	}
}