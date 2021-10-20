package common.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mcnc.smart.hybrid.common.storage.StorageAccessor;
import com.mcnc.smart.hybrid.server.web.dao.ErrorMessageDao;
import com.mcnc.smart.hybrid.server.web.exception.HttpDownloadException;
@Controller
@RequestMapping("cowayupload")
//@Deprecated
public class CowayUploadController {

	private static final Logger logger = LoggerFactory.getLogger(CowayUploadController.class);

	@Autowired
	ErrorMessageDao errorMessageDao;
	
	@Autowired
	StorageAccessor uploadStorageAccessor;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseStatus(HttpStatus.OK)
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("===== Coway Upload Controller Start [Save] >>>>> ");

		Enumeration headers = request.getHeaderNames();
		
		while (headers.hasMoreElements()) {
			String headerName = (String) headers.nextElement();
			String value = request.getHeader(headerName);
			logger.trace(String.format("Header Name : %s , Value : %s", headerName, value));
		}

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator fileNameIterator = multipartRequest.getFileNames();

			ArrayList<String> fileUIDList = new ArrayList<String>();

			while (fileNameIterator.hasNext()) {
				MultipartFile multiFile = multipartRequest.getFile((String) fileNameIterator.next());

				if (multiFile.getSize() > 0) {
					// 파일 저장 처리
					logger.trace(String.format("File Name : %s ", multiFile.getOriginalFilename()));
					logger.trace(String.format("UID : %s ", multiFile.getName()));

					Boolean result = uploadStorageAccessor.save(multiFile.getName(), multiFile.getOriginalFilename(),
							multiFile.getBytes());

					logger.trace(String.format("Flie Save Result : %s", result.toString()));

					if (result) {
						fileUIDList.add(multiFile.getName());
					} else {
						Iterator iList = fileUIDList.iterator();
						
						while (iList.hasNext()) {
							uploadStorageAccessor.remove(iList.next().toString());
						}
						
						throw new HttpDownloadException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMessageDao.getMessage(
								"HTTP_" + HttpServletResponse.SC_INTERNAL_SERVER_ERROR, new Object[] { "mode" }));
					}
				}
			}
		} finally {
			
		}

		logger.info("<<<<<  End Coway Upload Controller [Save] =====");
	}
}
