package adapter.ftp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.server.web.dao.LocalFileStorageAccessor;

import adapter.common.SapCommonMapperException;
import adapter.model.UPLOAD.UPLOADRequest;
import adapter.model.UPLOAD.UPLOADRequest_Body;
import adapter.model.UPLOAD.UPLOADRequest_Body_img_list;
import adapter.model.header.CowayCommonHeader;
import common.ResponseUtil;
import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import common.ftp.CowayFtpFileType;
import common.util.FileAttachmentService;

@Adapter(trcode = { "CGR110" })
public class CGR110_ADT_ImageUpload extends AbstractTemplateAdapter implements IAdapterJob {

	private static final Logger logger = LoggerFactory.getLogger(CGR110_ADT_ImageUpload.class);
	
	@Autowired private LocalFileStorageAccessor uploadStorageAccessor;
	@Autowired private SAPAdapter sapAdapter;
	@Autowired private DBAdapter dbAdapter;
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		UPLOADRequest 						request 			= new UPLOADRequest(obj);
		CowayCommonHeader 					reqHeader 			= request.getHeader();
		UPLOADRequest_Body 					reqBody 			= request.getBody();
		String								trCode				= reqHeader.getTrcode();
		
		List<UPLOADRequest_Body_img_list> 	reqImgList 			= reqBody.getImg_list();
		String 								procID 				= reqBody.getI_PROC_ID();
		
		List<Map<String, Object>> 			workImgMapList 		= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> 			customerImgMapList 	= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> 			memoImgMapList 		= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> 			resImgNameList 		= new ArrayList<Map<String,Object>>();
		
		StringBuffer						logBuffer			= new StringBuffer();
		long 								start 				= System.currentTimeMillis();
		String[]							rfcNames			= {"ZSMT_IF_SP_CSDR_WR201", "ZSMT_IF_SP_CODY_WR202", "ZSMT_IF_SP_CSDR_WR202" };
		try {
			
			for(UPLOADRequest_Body_img_list img : reqImgList) {
				
				logBuffer.append("upload img info = " + img.toString());
				String imgType 		= img.getIMG_TYPE();
				String jobDate 		= img.getJOB_DT();
				String jobType 		= img.getJOB_TP();
				String orderNo 		= img.getORDER_NO();
				String jobSeq 		= img.getJOB_SEQ();
				String imgSeq 		= img.getIMG_SEQ();
				String imgUid 		= img.getIMG_UID();
				String fileCa 		= img.getFILECA();
				
				String filePath 	= CowayFtpFilePath.getCowayFtpFilePath(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID);
				String fileName 	= CowayFtpFileName.getCowayFtpFileName(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID, fileCa);
				logBuffer.append("upload img ftp path = " + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName);
				
				Map<String, Object> imgNameMap = new HashMap<String, Object>();
				imgNameMap.put("IMG_UID", imgUid);
				imgNameMap.put("IMG_NAME", fileName);
				
				resImgNameList.add(imgNameMap);
				
				/*
				 * 2021-11-05, 이미지파일 업로드 변경 ftp -> api
				 */
				FileAttachmentService service = new FileAttachmentService();
				service.upload(filePath, fileName, getUploadImgData(imgUid), true);
//				ftpClientService.uploadFile(filePath, fileName, getUploadImgData(imgUid));
				
				
				logBuffer.append("== upload img end ========");
				
				if(CowayFtpFileType.getCowayImageTypeFlag(imgType) == CowayFtpFileType._IMG_FLAG_CUSTOMER) {
					
					//고객이미지 (설치 이미지, 주소 이미지)
					Map<String, Object> customerImgMap = new HashMap<String, Object>();
					customerImgMap.put("COMMAND", "C");
					customerImgMap.put("FILENAME", fileName);
					customerImgMap.put("GUBUN1", "C");
					customerImgMap.put("GUBUN2", CowayFtpFileType.getImgTypeName(imgType));
					customerImgMap.put("ORDER_NO", orderNo);
					customerImgMap.put("FILECA", fileCa);
					customerImgMapList.add(customerImgMap);
					
				} else if(CowayFtpFileType.getCowayImageTypeFlag(imgType) == CowayFtpFileType._IMG_FLAG_MEMO) {
					
					//영업노트 메모 이미지
					Map<String, Object> memoImgMap = new HashMap<String, Object>();
					memoImgMap.put("COMMAND", "C");
					memoImgMap.put("FILENAME", fileName);
					memoImgMap.put("GUBUN1", "M");
					memoImgMap.put("GUBUN2", CowayFtpFileType.getImgTypeName(imgType));
					memoImgMap.put("ORDER_NO", orderNo);
					memoImgMap.put("FILECA", fileCa);
					memoImgMapList.add(memoImgMap);
					
				} else {
					
					//작업 이미지
					Map<String, Object> workImgMap = new HashMap<String, Object>();
					workImgMap.put("COMMAND", "C");
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
				
				//고객 사진 이미지 SAP 등록
				Map<String, Object> 		customerImgObj 	= new HashMap<String, Object>();
				customerImgObj.put("I_PROC_ID", procID);
				customerImgObj.put("I_ITAB1", customerImgMapList);
				
				JsonNode 					reqNode 		= AdapterUtil.ConvertJsonNode(customerImgObj);
				SapCommonMapperException 	mapper 			= new SapCommonMapperException("CGR110", dbAdapter);
				sapAdapter.execute("ZSMT_IF_SP_CSDR_WR201", reqNode, mapper);
			}
			
			if(memoImgMapList.size() > 0) {			
				//영업노트 메모 이미지 SAP 등록
				Map<String, Object> 		memoImgObj 		= new HashMap<String, Object>();
				memoImgObj.put("I_PROC_ID", procID);
				memoImgObj.put("I_ITAB1", memoImgMapList);
				
				JsonNode 					reqNode 		= AdapterUtil.ConvertJsonNode(memoImgObj);
				SapCommonMapperException 	mapper 			= new SapCommonMapperException("CGR110", dbAdapter);
				sapAdapter.execute("ZSMT_IF_SP_CODY_WR202", reqNode, mapper);
			}
			
			if(workImgMapList.size() > 0) {
				
				//작업 이미지 SAP 등록
				Map<String, Object> workImgObj = new HashMap<String, Object>();
				workImgObj.put("I_PROC_ID", procID);
				workImgObj.put("I_ITAB1", workImgMapList);
				
				JsonNode 					reqNode 		= AdapterUtil.ConvertJsonNode(workImgObj);
				SapCommonMapperException 	mapper 			= new SapCommonMapperException("CGR110", dbAdapter);
				sapAdapter.execute("ZSMT_IF_SP_CSDR_WR202", reqNode, mapper);
			}
			
			long 	end 		= System.currentTimeMillis();
			
			//response
			JsonNodeFactory 	factory 			= JsonNodeFactory.instance;
			ObjectNode 			resBodyNode 		= new ObjectNode(factory);
			ObjectNode 			resRootNode 		= new ObjectNode(factory);

			//정상 처리는 TYPE을 "T"로 설정
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("TYPE", "T");
			returnMap.put("MESSAGE", "");
			resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
			
			// 파일명 return
			resBodyNode.put("O_ITAB", AdapterUtil.ConvertJsonNode(resImgNameList));
			
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeader.toJsonNode());
			resRootNode.put(Codes._JSON_MESSAGE_BODY, resBodyNode);

			return ResponseUtil.makeResponse(obj, resRootNode, trCode, (end - start), rfcNames.toString(), reqBody.toJsonNode(), this.getClass().getName());		
		
		} catch (AdapterException e) {
			logger.error("AdapterException = " + e.getErrorCode() + ":: " + e.getErrorMessage(), e);
			return ResponseUtil.makeFailResponse(obj, e.getErrorCode(), e.getErrorMessage(), trCode, reqBody.toJsonNode(), e, this.getClass().getName());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL0001", "요청처리에 실패하였습니다.", trCode, reqBody.toJsonNode(), e, this.getClass().getName());
		} finally {
			logger.debug(logBuffer.toString());
			logBuffer = null;
		}
	}
	
	private byte[] getUploadImgData(String img_uid) throws Exception {
			
        byte[] imgData = uploadStorageAccessor.load(img_uid);
        
    	//legacy 로 전송한 temp file 삭제
        String uploadTempDeleteFlag = SmartConfig.getString("upload.temp.image.delete.flag", "true");
        if(Boolean.parseBoolean(uploadTempDeleteFlag) == true) {
        	uploadStorageAccessor.remove(img_uid);
        }
        
        // upload된 이미지가 없는 경우 
        if( imgData == null){
        	throw new AdapterException("CGR110ADAP0001", "업로드된 이미지가 없습니다. 통신상태를 확인하시고 다시 시도하시기 바랍니다.");
        }
        
		return imgData;
	}
}
