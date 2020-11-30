package common.ftp;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class DownloadDO {

	private static final String ISO_8859_1_ENCODING 	= "iso-8859-1";
	private static final String UTF_8_ENCODING 			= "utf-8";
	
	private String isThumbnail 	= "";
	private String jobDate 		= "";
	private String jobType 		= "";
	private String jobSeq 		= "";
	private String imgSeq 		= "";
	private String orderNo 		= "";		
	private String procId 		= "";

	public DownloadDO(HttpServletRequest request) throws UnsupportedEncodingException {

		this.isThumbnail 	= iso88591ToUtf8(request.getParameter("thumbnail"));
		this.jobDate 		= iso88591ToUtf8(request.getParameter("job_dt"));
		this.jobType 		= iso88591ToUtf8(request.getParameter("job_tp"));
		this.jobSeq 		= iso88591ToUtf8(request.getParameter("job_seq"));
		this.imgSeq 		= iso88591ToUtf8(request.getParameter("img_seq"));
		this.orderNo 		= iso88591ToUtf8(request.getParameter("order_no")); //유니폼 번호가 orderno로 넘어올 예정
		this.procId 		= iso88591ToUtf8(request.getParameter("proc_id"));
		
	}

	public String getIsThumbnail() {
		return isThumbnail;
	}

	public void setIsThumbnail(String isThumbnail) {
		this.isThumbnail = isThumbnail;
	}

	public String getJobDate() {
		return jobDate;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobSeq() {
		return jobSeq;
	}

	public void setJobSeq(String jobSeq) {
		this.jobSeq = jobSeq;
	}

	public String getImgSeq() {
		return imgSeq;
	}

	public void setImgSeq(String imgSeq) {
		this.imgSeq = imgSeq;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	@Override
	public String toString() {
		return "DownloadDO [isThumbnail=" + isThumbnail + ", jobDate=" + jobDate + ", jobType=" + jobType + ", jobSeq="
				+ jobSeq + ", imgSeq=" + imgSeq + ", orderNo=" + orderNo + ", procId=" + procId + "]";
	}
	
	private String iso88591ToUtf8(String changeString) throws UnsupportedEncodingException {
		
		String result = "";
		
		if ( (changeString != null) && (changeString.length() > 0) ) {
			
			result = new String(changeString.getBytes(ISO_8859_1_ENCODING), UTF_8_ENCODING);
		}
		
		return result;
	}
}
