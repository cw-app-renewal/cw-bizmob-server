/*
 * DownloadController.java
 * Copyright 2010, MOBILE C&C LTD. All rights reserved.
 */

package com.mcnc.smart.hybrid.server.web.control;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mcnc.common.util.IOUtil;
import com.mcnc.portal.auth.dao.EnvDao;
import com.mcnc.portal.auth.model.Env;
import com.mcnc.smart.common.Smart;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LogType;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.helper.IOHelper;
import com.mcnc.smart.hybrid.server.web.dao.ErrorMessageDao;
import com.mcnc.smart.hybrid.server.web.exception.HttpDownloadException;
import com.mcnc.smart.hybrid.server.web.exception.SessionTimeoutException;
import com.mcnc.smart.hybrid.server.web.model.AbstractFile;
import com.mcnc.smart.hybrid.server.web.service.LocalFileDownloadService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


@Controller
@RequestMapping("ios")
public class IosPlistController {
    private static ILogger logger       = LoggerService.getLogger(IosPlistController.class);

    /**
     * 처음부터 받기
     */
    public static final int         MODE_STREAM  = 1;
    /**
     * <strike>나누어 받기 (사용 안함)</strike>
     */
    public static final int         MODE_PARTIAL = 2;
    /**
     * 이어 받기
     */
    public static final int         MODE_RESUME  = 3;
    public static final int         BUFFER_SIZE  = 8192;                                             // 8kb

    @Autowired
    LocalFileDownloadService localDWSvc;
    @Autowired
    ErrorMessageDao     errorMessageDao;

    @Autowired
    ServletContext servletContext;
    @Autowired 
    CacheManager cacheManager;
    
    @Autowired
   	EnvDao endDao;
    
    @Autowired
    MessageSourceAccessor updatePathSourceAccessor;
    
    /**
     * 어플리케이션/컨텐츠/메타 update 요청을 처리한다.
     * 요청 URI는 <code>{context_root}/ios/update</code>이다.
     * 파일은 HTTP 프로토콜로 전송한다.
     * 기본 설정은 <code>{SMART_HOME}/conf/server/updater.properties</code> 파일에 지정한다.
     * 
     * @param request 요청한 HttpRequest
     * @param response 전송할 HttpResponse
     * @param type 요청한 파일의 종류(어플리케이션/컨텐츠/메타)
     * @param fileName 파일 이름
     * @param osType 요청한 client의 OS 종류
     * @param mode 받기 모드
     * @param requestFile 요청 파일에 대한 객체
     * @throws Exception
     */
    @RequestMapping(value = "/update/{file_name}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
    public void update(HttpServletRequest request, HttpServletResponse response, @PathVariable("file_name") final String fileName, AbstractFile requestFile) throws Exception {
        
        Env 			env 				= endDao.selectEnvByPk("ADMIN_DATAFILE_DIR");
        String 			baseFolder 			= env.getEnvValue().replace("${SMART_HOME}", Smart.HOME_DIR);
        
        StringBuffer 	localPath 			= new StringBuffer(200);
        
        long			startEventTime 		= 0;
		long			endEventTime 		= 0;
		
        localPath.append(baseFolder).append(File.separator);
        localPath.append(updatePathSourceAccessor.getMessage("basePath", "Download")).append(File.separator);
        localPath.append(updatePathSourceAccessor.getMessage("iOS", "iOS")).append(File.separator);
        localPath.append(updatePathSourceAccessor.getMessage("app", "app"));

        startEventTime = System.currentTimeMillis();
        logger.info("===== Start::update() : " + fileName);
        
        if (isTraceable()) {
            logger.trace("  > target name : " + fileName);
            logger.trace("  > Local Path:" + localPath.toString());
        }

        requestFile = new AbstractFile(fileName + ".plist", localPath.toString());
        response.setHeader("file_name", fileName);
        response.setContentType("application/octet-stream; charset=utf-8");

        try {
            // ehcache 적용 코드
            Cache cache = cacheManager.getCache("bizmob_update");
            String cacheKey = String.format("%s_%s_%s", "update", "app", fileName);
            Element element = cache.get(cacheKey);

            if (element == null) {
                localDWSvc.downloadAtByteBuffer(requestFile);
                Element newElement = new Element(cacheKey, requestFile);
                cache.put(newElement);
                logger.trace(fileName + " will be cached as " + cacheKey + " in the bizmob_update.");
            } 
            
            else {
                requestFile = (AbstractFile) element.getObjectValue();
                logger.trace("Gotten from the bizmob_update cache with " + cacheKey + ".");
            }
            
            responseAsByteBuffer(response, requestFile);
        } finally {
            if(requestFile != null) {
                IOUtil.closeQuietly(requestFile.getInputStream());
                IOUtil.closeQuietly(requestFile.getInputChannel());
            }
        }

        logger.info("End update() : " + fileName + " =====");
        endEventTime = System.currentTimeMillis() - startEventTime;
        logger.trace("Update Time : " + endEventTime);
    }

    @ExceptionHandler(HttpDownloadException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    void handleHttpDownloadException(HttpDownloadException e, HttpServletResponse response) {
        logger.error("Download failed! Code: " + e.getHttpCode() + ", Cause: " + e.getMessage());
        response.setHeader("error_code", String.valueOf(e.getHttpCode()));
        response.setHeader("info_text", getMimeText(e.getMessage()));
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    void handleNumberFormatException(NumberFormatException e, HttpServletResponse response) {
        logger.error("Download failed! Code: " + HttpStatus.EXPECTATION_FAILED + ", Cause: " + e.getMessage());
        response.setHeader("error_code", HttpStatus.EXPECTATION_FAILED.toString());
        response.setHeader("info_text", getMimeText(errorMessageDao.getMessage("HTTP_" + HttpStatus.EXPECTATION_FAILED)));
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.METHOD_FAILURE)
    void handleIOException(IOException e, HttpServletResponse response) {
        logger.error("Download failed! Code: " + HttpStatus.METHOD_FAILURE + ", Cause: " + e.getMessage());
        response.setHeader("error_code", HttpStatus.METHOD_FAILURE.toString());
        response.setHeader("info_text", getMimeText(errorMessageDao.getMessage("HTTP_" + HttpStatus.METHOD_FAILURE)));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    void handleException(Exception e, HttpServletResponse response) {
        logger.error("Download failed! Code: " + HttpStatus.INTERNAL_SERVER_ERROR + ", Cause: " + e.getMessage());
        response.setHeader("error_code", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        response.setHeader("info_text", getMimeText(errorMessageDao.getMessage("HTTP_" + HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleFileNotFoundException(FileNotFoundException e, HttpServletResponse response) {
        logger.error("Download failed! Code: " + HttpStatus.NOT_FOUND + ", Cause: " + e.getMessage());
        response.setHeader("error_code", HttpStatus.NOT_FOUND.toString());
        response.setHeader("info_text", getMimeText(errorMessageDao.getMessage("HTTP_" + HttpStatus.NOT_FOUND)));
    }

    @ExceptionHandler(SessionTimeoutException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void handleSessionTimeoutException(SessionTimeoutException e, HttpServletResponse response) {
        logger.error("Download failed! Code: " + HttpStatus.UNAUTHORIZED + ", Cause: " + e.getMessage());
        response.setHeader("error_code", HttpStatus.UNAUTHORIZED.toString());
        response.setHeader("info_text", getMimeText(errorMessageDao.getMessage("HTTP_" + HttpStatus.UNAUTHORIZED)));
    }

    /**
     * Http Response stream을 이용해서 파일을 전송한다.
     * @param response HttpResponse 객체
     * @param file 전송할 파일 객체
     * @throws IOException IO Exception
     */
    protected void responseAsFileStream(HttpServletResponse response, AbstractFile file) throws IOException {
        long contentLength = 0;
        long fileSize = file.getFileSize();
        FileChannel fileCh = null;
        ReadableByteChannel byteCh = null;
        WritableByteChannel outCh = null;
        
        response.setHeader("file_size", String.valueOf(fileSize));
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName() + ";");

        try {
            fileCh = file.getInputChannel();
            
            if(fileCh != null) {
                contentLength = fileCh.size() - file.getFileStartPos();
                response.setHeader("Content-Length", "" + contentLength);
                
                outCh = Channels.newChannel(response.getOutputStream());
                fileCh.transferTo(file.getFileStartPos(), contentLength, outCh);
            }
            
            else {
                ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE);
                file.getInputStream().skip(file.getFileStartPos());
                byteCh = Channels.newChannel(file.getInputStream());
                
                while (byteCh.read(bb) != -1) {
                    bb = IOHelper.resizeBuffer(bb); //get new buffer for read
                }

                contentLength = bb.position();
                response.setHeader("Content-Length", "" + contentLength);
                bb.position( Integer.valueOf(String.valueOf(file.getFileStartPos())) );
                
                outCh = Channels.newChannel(response.getOutputStream());
                outCh.write(bb);
                bb.clear();
            }
        } finally {
            IOUtil.closeQuietly(fileCh);
            IOUtil.closeQuietly(byteCh);
            IOUtil.closeQuietly(outCh);
        } // end of try/catch
    }
    
    /**
     * Http Response stream을 이용해서 파일을 전송한다.
     * @param response HttpResponse 객체
     * @param file 전송할 파일 객체
     * @throws IOException IO Exception
     */
    protected void responseAsByteBuffer(HttpServletResponse response, AbstractFile file) throws IOException {
    	long contentLength = 0;
        long fileSize = file.getFileSize();
        FileChannel fileCh = null;
        ReadableByteChannel byteCh = null;
        WritableByteChannel outCh = null;
        
        response.setHeader("file_size", String.valueOf(fileSize));
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName() + ";");

        try {
        	ByteBuffer bb = ByteBuffer.allocateDirect(BUFFER_SIZE);
        	byte[] buffer = file.getFileByteArray();
   
        	bb = ByteBuffer.wrap(buffer);
        	bb.put(buffer);

        	contentLength = bb.position();
            response.setHeader("Content-Length", "" + contentLength);
            bb.position( Integer.valueOf(String.valueOf(file.getFileStartPos())) );
            
            outCh = Channels.newChannel(response.getOutputStream());
            outCh.write(bb);
            bb.clear();
        } finally {
            IOUtil.closeQuietly(fileCh);
            IOUtil.closeQuietly(byteCh);
            IOUtil.closeQuietly(outCh);
        } // end of try/catch
    }
    
    /**
     * 파일의 확장자를 바탕으로 MIME(Multipurpose Internet Mail Extensions) 타입을 알아 낸다.
     * @param request HttpRequest 객체
     * @param fileName MIME 확장자를 포함한 파일 이름
     * @return 파일에 대한 MIME 타입
     * @see <a href="http://ko.wikipedia.org/wiki/MIME">MIME type</a>
     */
    protected String getMimeType(HttpServletRequest request, String fileName) {
        String mimetype = request.getSession().getServletContext().getMimeType(fileName);

        if (mimetype == null || mimetype.length() == 0) {
            return "application/octet-stream;";
        }

        else {
            return mimetype;
        }
    }
    
    /**
     * HTTP 프로토콜로 전송하기 위한 data를 URL 인코딩한다.
     * @param str 인코딩할 data text
     * @return 인코딩한 data text
     */
    protected String getMimeText(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
    
    /**
     * 현재 TRACE 로그를 남길 수 있는 지 결정한다.
     * @return TRACE 로그를 남길 수 있으면 true
     */
    protected static boolean isTraceable() {
        return logger.getLoggingLevel().getLevel() >= LogType.TRACE.getLevel();
    }
}
