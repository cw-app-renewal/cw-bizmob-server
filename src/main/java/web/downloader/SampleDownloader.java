/**
 * SampleDownloader.java
 * Copyright 2010, MOBILE C&C LTD. All rights reserved.
 */
package web.downloader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mcnc.smart.downloader.Attachment;
import com.mcnc.smart.downloader.URLDownloader;
import com.mcnc.smart.hybrid.server.web.control.DownloadController;
import com.mcnc.smart.hybrid.server.web.io.AbstractDownloader;
import com.mcnc.smart.hybrid.server.web.io.Downloader;

/**
 * 샘플 다운로드. http://{context_root}/download/sample 로 매핑된다.
 * @author 
 */
@Component
public class SampleDownloader extends AbstractDownloader implements Downloader {
    private static final Logger logger = LoggerFactory.getLogger(SampleDownloader.class);
    
    public SampleDownloader() {
        // set buffer size 16kb of memory
        super(DownloadController.BUFFER_SIZE * 2);
    }
    
    /**
     * {@inheritDoc}
     * @param target {@inheritDoc}
     * @param uid {@inheritDoc}
     * @param params {@inheritDoc}
     * @throws Exception {@inheritDoc}
     */
    public void download(String target, String uid, Map<String, Object> params) throws Exception {
        logger.info(String.format("========Start downloading from %s : %s ", target, uid));

        int mode = 0;
        int fileStartPos = Integer.parseInt(params.get("index").toString());
        HttpServletRequest request = (HttpServletRequest) params.get("HttpServletRequest");
        HttpServletResponse response = (HttpServletResponse) params.get("HttpServletResponse");
        String fileName = String.valueOf(params.get("file_name"));
        String fileUrl = request.getParameter("file_url");
        String fileType = getMimeType(request, fileName);
        ByteArrayInputStream in = null;
        
        try {
            mode = Integer.parseInt(params.get("mode").toString());
        } catch(Exception e) {
            mode = 0;
        }
        
        logger.debug("uid : " + uid);
        logger.debug("mode : " + mode);
        logger.debug("file name : " + fileName);
        logger.debug("file url : " + fileUrl);

        try {
        	//TODO URL에서 파일을 다운로드할 경우의 샘플
            URLDownloader urlDownloader = new URLDownloader();
            Attachment attch = urlDownloader.download(fileName, fileUrl);
            in = new ByteArrayInputStream(attch.getBytes());

            send(response, fileName, fileType, in, attch.getSize(), fileStartPos);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                in = null;
            }
        }

        logger.info("Sucess downloading from " + target + "=============");
    }
}
