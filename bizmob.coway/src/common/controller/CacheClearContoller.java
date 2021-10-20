package common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mcnc.bizmob.adapter.sap.SapConnector;
import com.mcnc.common.util.JsonUtil;

import common.util.RFCParser;


@Controller
@RequestMapping("cache")
public class CacheClearContoller {
	
	@Autowired private SapConnector sapConnector;
	
	private static final Logger logger = LoggerFactory.getLogger(CacheClearContoller.class);
	
	@RequestMapping(value = "/select/{rfcName}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
	public void  getRfcMetaInfo(@PathVariable String rfcName, HttpServletResponse response) throws IOException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("call :: /select/" + rfcName).append("\n");;
		
		try {
			
			RFCParser 	parser 		= new RFCParser();
			ObjectNode 	resultNode 	= parser.parseFunction(rfcName, sapConnector);
			byte[] 		content 	= JsonUtil.toJson(resultNode, true).getBytes("UTF-8");
			response.getOutputStream().write(content);
			response.setContentType("application/json; charset=utf-8");

			sb.append("result :: " + resultNode.toString()).append("\n");;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response.sendError(500);
		} finally {
			logger.info(sb.toString());
		}
		
		
	}
	

	@RequestMapping(value = "/clear/{rfcName}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
	public void RfcCacheClear(@PathVariable String rfcName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("call :: /clear/" + rfcName).append("\n");
		
		try {
			RFCParser 	parser 			= new RFCParser();
			ObjectNode 	resultNode 		= parser.cacheClear(rfcName, false, sapConnector);
			byte[] 		content 		= JsonUtil.toJson(resultNode, true).getBytes("UTF-8");
			
			response.getOutputStream().write(content);
			response.setContentType("application/json; charset=utf-8");
			
			sb.append("result :: " + resultNode.toString()).append("\n");;
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
				response.sendError(500);
			} finally {
				logger.info(sb.toString());
			}
			
	}
	
	@RequestMapping(value = "/clear/all", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseStatus(HttpStatus.OK)
	public void RfcCacheClear(HttpServletRequest request, HttpServletResponse response) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("call :: /clear/all").append("\n");
		
		try {
			RFCParser 	parser 			= new RFCParser();
			ObjectNode 	resultNode 		= parser.cacheClear(null, true, sapConnector);
			byte[] 		content 		= JsonUtil.toJson(resultNode, true).getBytes("UTF-8");
			
			response.getOutputStream().write(content);
			response.setContentType("application/json; charset=utf-8");
			sb.append("result :: " + resultNode.toString()).append("\n");;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response.sendError(500);
		} finally {
			logger.info(sb.toString());
		}
			
	}
	
}
 