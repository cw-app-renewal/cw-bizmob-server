package adapter.ftp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;

import adapter.common.SapCommonMapperException;
import adapter.model.UPLOAD.UPLOADRequest;
import adapter.model.UPLOAD.UPLOADRequest_Body;
import adapter.model.UPLOAD.UPLOADRequest_Body_img_list;
import adapter.model.header.CowayCommonHeader;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.exception.AdapterException;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.server.web.dao.LocalFileStorageAccessor;

import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import common.ftp.CowayFtpFileType;
import connect.ftp.FtpClientService;

@Adapter(trcode = { "CGR110" })
public class CGR110_ADT_ImageUpload extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGR110_ADT_ImageUpload.class);
	
	@Autowired
	private FtpClientService ftpClientService;

	@Autowired
	private LocalFileStorageAccessor uploadStorageAccessor;

	@Autowired
	private SAPAdapter sapAdapter;

	@Autowired
	private DBAdapter dbAdapter;
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		String errCode = "ADAP0000";
		
		try {		
			//request 
			UPLOADRequest request = new UPLOADRequest(obj);
			CowayCommonHeader reqHeader = request.getHeader();
			//request body
			UPLOADRequest_Body reqBody = request.getBody();
			List<UPLOADRequest_Body_img_list> reqImgList = reqBody.getImg_list();
			logger.debug(reqImgList.toString());
			String procID = reqBody.getI_PROC_ID();
			
			//List<CowayImageWorkTypeDO> workImgList = new ArrayList<CowayImageWorkTypeDO>();
			//List<CowayImageCustomerTypeDO> customerImgList = new ArrayList<CowayImageCustomerTypeDO>();
			List<Map<String, Object>> workImgMapList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> customerImgMapList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> memoImgMapList = new ArrayList<Map<String,Object>>();
			
			// response : img_name list
			List<Map<String, Object>> resImgNameList = new ArrayList<Map<String,Object>>();
			
			for(UPLOADRequest_Body_img_list img : reqImgList) {
				
				logger.debug("upload img info = " + img.toString());
				String imgType = img.getIMG_TYPE();
				String jobDate = img.getJOB_DT();
				String jobType = img.getJOB_TP();
				String orderNo = img.getORDER_NO();
				String jobSeq = img.getJOB_SEQ();
				String imgSeq = img.getIMG_SEQ();
				String imgUid = img.getIMG_UID();
				String fileCa = img.getFILECA();
				
				String filePath = CowayFtpFilePath.getCowayFtpFilePath(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID);
				String fileName = CowayFtpFileName.getCowayFtpFileName(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID, fileCa);
				logger.debug("upload img ftp path = " + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName);
				
				Map<String, Object> imgNameMap = new HashMap<String, Object>();
				imgNameMap.put("IMG_UID", imgUid);
				imgNameMap.put("IMG_NAME", fileName);
				resImgNameList.add(imgNameMap);
				
				//ftp
				ftpClientService.uploadFile(filePath, fileName, getUploadImgData(imgUid));
				logger.debug("== upload img end ========");
				
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
				Map<String, Object> customerImgObj = new HashMap<String, Object>();
				customerImgObj.put("I_PROC_ID", procID);
				customerImgObj.put("I_ITAB1", customerImgMapList);
				
				JsonNode jsonNode = AdapterUtil.ConvertJsonNode(customerImgObj); 
				logger.debug(jsonNode.toString());
				//execute
				logger.info("*************** Insert Customer Image RFC NAME = " + "ZSMT_IF_SP_CSDR_WR201");
				Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_WR201", AdapterUtil.ConvertJsonNode(customerImgObj), new SapCommonMapperException("CGR110", dbAdapter));
				logger.debug("*************** ZSMT_IF_SP_CSDR_WR201 RFC Return = " + resMap.toString());
			}
			
			if(memoImgMapList.size() > 0) {			
				//영업노트 메모 이미지 SAP 등록
				Map<String, Object> memoImgObj = new HashMap<String, Object>();
				memoImgObj.put("I_PROC_ID", procID);
				memoImgObj.put("I_ITAB1", memoImgMapList);
				
				logger.debug("memo img upload sap import data = " + memoImgObj.toString());
				
				//execute
				logger.info("*************** Insert Memo Image RFC NAME = " + "ZSMT_IF_SP_CODY_WR202");
				Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CODY_WR202", AdapterUtil.ConvertJsonNode(memoImgObj), new SapCommonMapperException("CGR110", dbAdapter));
				logger.debug("*************** RFC Return = " + resMap.toString());
			}
			
			if(workImgMapList.size() > 0) {
				//작업 이미지 SAP 등록
				Map<String, Object> workImgObj = new HashMap<String, Object>();
				workImgObj.put("I_PROC_ID", procID);
				workImgObj.put("I_ITAB1", workImgMapList);
				
				//execute
				logger.info("*************** Insert Work Image RFC NAME = " + "ZSMT_IF_SP_CSDR_WR202");
				Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_WR202", AdapterUtil.ConvertJsonNode(workImgObj), new SapCommonMapperException("CGR110", dbAdapter));
				logger.debug("*************** RFC Return = " + resMap.toString());
			}
								
			//response
			JsonNodeFactory factory = JsonNodeFactory.instance;
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			//response body node
			ObjectNode resBodyNode = new ObjectNode(factory);
			
			//response root node
			ObjectNode resRootNode = new ObjectNode(factory);

			//정상 처리는 TYPE을 "T"로 설정
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("TYPE", "T");
			returnMap.put("MESSAGE", "");
			resBodyNode.put("E_RETURN", AdapterUtil.ConvertJsonNode(returnMap));
			// 파일명 return
			resBodyNode.put("O_ITAB", AdapterUtil.ConvertJsonNode(resImgNameList));
			
			resRootNode.put(Codes._JSON_MESSAGE_HEADER, reqHeader.toJsonNode());
			resRootNode.put(Codes._JSON_MESSAGE_BODY, resBodyNode);

			return makeResponse(resObj, resRootNode);
		
		} catch (AdapterException e) {
			logger.error("AdapterException = " + e.getErrorCode() + ":: " + e.getErrorMessage(), e);
			return makeFailReesponse(e.getErrorCode(), e.getErrorMessage());

		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(errCode, e.getLocalizedMessage());
		}
	}

	
	/**
	 * 
	 */
	private byte[] getUploadImgData(String img_uid) throws Exception {
			
		// LocalFileStorageAccessor Get Instance
        //LocalFileStorageAccessor storage = LocalFileStorageAccessor.getInstance();	 
        // Temp Repository File Load
		//logger.debug("upload img file name = " + uploadStorageAccessor.getFileName(img_uid));
		
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
	
	/**
	 * 
	 */
}
