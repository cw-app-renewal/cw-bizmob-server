package adapter.ftp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.SapCommonMapperException;
import adapter.common.SapCommonResponse;
import adapter.model.DELETE.DELETERequest;
import adapter.model.DELETE.DELETERequest_Body;
import adapter.model.DELETE.DELETERequest_Body_img_list;
import adapter.model.header.CowayCommonHeader;
import common.ResponseUtil;
import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import common.ftp.CowayFtpFileType;
import common.util.FileAttachmentService;

@Adapter(trcode = { "CGR111" })
public class CGR111_ADT_ImageDelete extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGR111_ADT_ImageDelete.class);
	
	@Autowired private SAPAdapter sapAdapter;
	@Autowired private DBAdapter dbAdapter;
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		DELETERequest 						request 			= new DELETERequest(obj);
		CowayCommonHeader 					reqHeader 			= request.getHeader();
		DELETERequest_Body 					reqBody 			= request.getBody();
		String								trCode				= reqHeader.getTrcode();
		
		List<DELETERequest_Body_img_list> 	reqImgList 			= reqBody.getImg_list();
		String 								procID 				= reqBody.getI_PROC_ID();
		
		List<Map<String, Object>> 			workImgMapList 		= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> 			customerImgMapList 	= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> 			memoImgMapList 		= new ArrayList<Map<String,Object>>();
		
		StringBuffer						logBuffer			= new StringBuffer();
		long 								start 				= System.currentTimeMillis();
		String[]							rfcNames			= {"ZSMT_IF_SP_CSDR_WR201", "ZSMT_IF_SP_CODY_WR202", "ZSMT_IF_SP_CSDR_WR202" };
		
		try {			
			for(DELETERequest_Body_img_list img : reqImgList) {
				
				logBuffer.append("delete img info = " + img.toString());				
				String imgType 		= img.getImg_type();
				String jobDate 		= img.getJob_dt();
				String jobType 		= img.getJob_tp();
				String orderNo 		= img.getOrder_no();
				String jobSeq 		= img.getJob_seq();
				String imgSeq 		= img.getImg_seq();
				String fileCa 		= img.getFILECA();
				
				String filePath 	= CowayFtpFilePath.getCowayFtpFilePath(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID);
				String fileName 	= CowayFtpFileName.getCowayFtpFileName(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID, fileCa);
				logBuffer.append("delete img ftp path = " + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName);		
				
//				ftpClientService.deleteFile(filePath, fileName);
				FileAttachmentService service = new FileAttachmentService();
				service.delete(filePath, fileName);
				
				logBuffer.append("== delete img end ========");
				
				if(CowayFtpFileType.getCowayImageTypeFlag(imgType) == 1) {
					
					//고객이미지
					Map<String, Object> customerImgMap = new HashMap<String, Object>();
					customerImgMap.put("COMMAND", "D");
					customerImgMap.put("FILENAME", fileName);
					customerImgMap.put("GUBUN1", "C");
					customerImgMap.put("GUBUN2", CowayFtpFileType.getImgTypeName(imgType));
					customerImgMap.put("ORDER_NO", orderNo);
					customerImgMap.put("FILECA", fileCa);
					customerImgMapList.add(customerImgMap);
					
				} else if(CowayFtpFileType.getCowayImageTypeFlag(imgType) == 3) {
					
					//영업노트 메모 이미지
					Map<String, Object> memoImgMap = new HashMap<String, Object>();
					memoImgMap.put("COMMAND", "D");
					memoImgMap.put("FILENAME", fileName);
					memoImgMap.put("GUBUN1", "M");
					memoImgMap.put("GUBUN2", CowayFtpFileType.getImgTypeName(imgType));
					memoImgMap.put("ORDER_NO", orderNo);
					memoImgMap.put("FILECA", fileCa);
					memoImgMapList.add(memoImgMap);	
					
				} else {
					//작업 이미지
					Map<String, Object> workImgMap = new HashMap<String, Object>();
					workImgMap.put("COMMAND", "D");
					workImgMap.put("FILENAME", fileName);
					workImgMap.put("GUBUN1", "W");
					workImgMap.put("GUBUN2", CowayFtpFileType.getImgTypeName(imgType));
					workImgMap.put("ORDER_NO", orderNo);
					workImgMap.put("JOB_DT", jobDate);
					workImgMap.put("JOB_TP", jobType);
					workImgMap.put("JOB_SEQ", jobSeq);
					workImgMap.put("FILECA", fileCa);
					workImgMapList.add(workImgMap);
				}	
			}

			if(customerImgMapList.size() > 0) {
				//고객 사진 이미지 삭제
				Map<String, Object> customerImgObj = new HashMap<String, Object>();
				customerImgObj.put("I_PROC_ID", procID);
				customerImgObj.put("I_ITAB1", customerImgMapList);
				
				JsonNode 					reqNode 		= AdapterUtil.ConvertJsonNode(customerImgObj);
				SapCommonMapperException 	mapper 			= new SapCommonMapperException("CGR111", dbAdapter);
				sapAdapter.execute("ZSMT_IF_SP_CSDR_WR201", reqNode, mapper);
			}
			
			if(memoImgMapList.size() > 0) {
				
				//영업노트 메모 이미지 SAP 등록
				Map<String, Object> memoImgObj = new HashMap<String, Object>();
				memoImgObj.put("I_PROC_ID", procID);
				memoImgObj.put("I_ITAB1", memoImgMapList);
				
				JsonNode 					reqNode 		= AdapterUtil.ConvertJsonNode(memoImgObj);
				SapCommonMapperException 	mapper 			= new SapCommonMapperException("CGR111", dbAdapter);
				sapAdapter.execute("ZSMT_IF_SP_CODY_WR202", reqNode, mapper);
			}
			
			if(workImgMapList.size() > 0) {
				//작업 사진 이미지 삭제
				Map<String, Object> workImgObj = new HashMap<String, Object>();
				workImgObj.put("I_PROC_ID", procID);
				workImgObj.put("I_ITAB1", workImgMapList);
				
				JsonNode 					reqNode 		= AdapterUtil.ConvertJsonNode(workImgObj);
				SapCommonMapperException 	mapper 			= new SapCommonMapperException("CGR111", dbAdapter);
				sapAdapter.execute("ZSMT_IF_SP_CSDR_WR202", reqNode, mapper);
			}
			
			long 				end 		= System.currentTimeMillis();
			SapCommonResponse 	response = new SapCommonResponse(reqHeader);
			return ResponseUtil.makeResponse(obj, response.getSapCommonResponse(), trCode, (end - start), rfcNames.toString(), reqBody.toJsonNode(), this.getClass().getName());		
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0001", "요청처리에 실패하였습니다.", trCode, reqBody.toJsonNode(), e, this.getClass().getName());
		} finally {
			logger.debug(logBuffer.toString());
			logBuffer = null;
		}
	}
}
