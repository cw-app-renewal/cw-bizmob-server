package adapter.ftp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import adapter.common.SapCommonMapper;
import adapter.common.SapCommonMapperException;
import adapter.common.SapCommonResponse;
import adapter.model.DELETE.DELETERequest;
import adapter.model.DELETE.DELETERequest_Body;
import adapter.model.DELETE.DELETERequest_Body_img_list;
import adapter.model.DELETE.DELETEResponse;
import adapter.model.DELETE.DELETEResponse_Body;
import adapter.model.header.CowayCommonHeader;

import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.bizmob.adapter.DBAdapter;
import com.mcnc.bizmob.adapter.SAPAdapter;
import com.mcnc.bizmob.adapter.util.AdapterUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import common.ftp.CowayFtpFileName;
import common.ftp.CowayFtpFilePath;
import common.ftp.CowayFtpFileType;
import common.ftp.CowayImageCustomerTypeDO;
import common.ftp.CowayImageCustomerTypeListDO;
import common.ftp.CowayImageWorkTypeDO;
import common.ftp.CowayImageWorkTypeListDO;

import connect.ftp.FtpClientService;

@Adapter(trcode = { "CGR111" })
public class CGR111_ADT_ImageDelete extends AbstractTemplateAdapter implements IAdapterJob {

	private ILogger logger = LoggerService.getLogger(CGR111_ADT_ImageDelete.class);
	
	@Autowired
	private FtpClientService ftpClientService;

	@Autowired
	private SAPAdapter sapAdapter;

	@Autowired
	private DBAdapter dbAdapter;
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		String errCode = "ADAP0000";
		
		try {
			//request 
			DELETERequest request = new DELETERequest(obj);
			
			CowayCommonHeader reqHeader = request.getHeader();
			//request body
			DELETERequest_Body reqBody = request.getBody();
			//logger.debug("delete image body :: " + reqBody.toString());
			List<DELETERequest_Body_img_list> reqImgList = reqBody.getImg_list();
			String procID = reqBody.getI_PROC_ID();
			
			//List<CowayImageWorkTypeDO> workImgList = new ArrayList<CowayImageWorkTypeDO>();
			//List<CowayImageCustomerTypeDO> customerImgList = new ArrayList<CowayImageCustomerTypeDO>();
			List<Map<String, Object>> workImgMapList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> customerImgMapList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> memoImgMapList = new ArrayList<Map<String,Object>>();
			
			for(DELETERequest_Body_img_list img : reqImgList) {
				
				logger.debug("delete img info = " + img.toString());				
				String imgType = img.getImg_type();
				String jobDate = img.getJob_dt();
				String jobType = img.getJob_tp();
				String orderNo = img.getOrder_no();
				String jobSeq = img.getJob_seq();
				String imgSeq = img.getImg_seq();
				String fileCa = img.getFILECA();
				
				String filePath = CowayFtpFilePath.getCowayFtpFilePath(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID);
				String fileName = CowayFtpFileName.getCowayFtpFileName(imgType, jobDate, jobType, orderNo, jobSeq, imgSeq, procID, fileCa);
				logger.debug("delete img ftp path = " + filePath + CowayFtpFilePath._FOLDER_SEPARATOR + fileName);		
				
				//ftp
				ftpClientService.deleteFile(filePath, fileName);
				
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
				
				//execute
				logger.info("*************** Delete Image RFC NAME = " + "ZSMT_IF_SP_CSDR_WR201");
				Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_WR201", AdapterUtil.ConvertJsonNode(customerImgObj), new SapCommonMapperException("CGR111", dbAdapter));
				logger.debug("*************** ZSMT_IF_SP_CSDR_WR201 RFC Return = " + resMap.toString());
			}
			
			if(memoImgMapList.size() > 0) {			
				//영업노트 메모 이미지 SAP 등록
				Map<String, Object> memoImgObj = new HashMap<String, Object>();
				memoImgObj.put("I_PROC_ID", procID);
				memoImgObj.put("I_ITAB1", memoImgMapList);
				
				//execute
				logger.info("*************** Delete Image RFC NAME = " + "ZSMT_IF_SP_CODY_WR202");
				Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CODY_WR202", AdapterUtil.ConvertJsonNode(memoImgObj), new SapCommonMapperException("CGR110", dbAdapter));
				logger.debug("*************** RFC Return = " + resMap.toString());
			}
			
			if(workImgMapList.size() > 0) {
				//작업 사진 이미지 삭제
				Map<String, Object> workImgObj = new HashMap<String, Object>();
				workImgObj.put("I_PROC_ID", procID);
				workImgObj.put("I_ITAB1", workImgMapList);
				
				//execute
				logger.info("*************** Delete Image RFC NAME = " + "ZSMT_IF_SP_CSDR_WR202");
				Map<String, Object> resMap = (Map<String, Object>) sapAdapter.execute("ZSMT_IF_SP_CSDR_WR202", AdapterUtil.ConvertJsonNode(workImgObj), new SapCommonMapperException("CGR111", dbAdapter));
				logger.debug("*************** RFC Return = " + resMap.toString());
			}
			
			//response body
			/*DELETEResponse_Body resBody = new DELETEResponse_Body();
			resBody.setResult(true);
			
			DELETEResponse response = new DELETEResponse();
			response.setHeader(reqHeader);
			response.setBody(resBody);
			
			//
			return makeResponse(obj, response.toJsonNode());*/
			
			JsonAdaptorObject resObj = new JsonAdaptorObject();
			
			SapCommonResponse response = new SapCommonResponse(reqHeader);
			
			return makeResponse(resObj, response.getSapCommonResponse());
			
		} catch (Exception e) {
			logger.error("Exception :: ", e);
			return makeFailReesponse(errCode, e.getLocalizedMessage());
		}
	}

	
}
