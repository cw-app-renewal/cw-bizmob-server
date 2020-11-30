package web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.common.json.SimpleJsonResponse;

import adapter.common.EncryptionUtil;

public class TimeCheckInterceptor extends HandlerInterceptorAdapter{

	private static final ILogger logger = LoggerService.getLogger(TimeCheckInterceptor.class);

	/**
	 * 최대 유효한 초(클라이언트에서 요청한 시간과 현재 서버시간의 차이)
	 */
	private int maxValidMinutes = 10;
	/**
	 * 시간 체크를 하지 않는 tr code
	 */
	private Set<String> uncheckTrCodes = new HashSet<String>();
	private Set<String> uncheckUrls = new HashSet<String>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		if(uncheckUrls.contains(url)){
			return super.preHandle(request, response, handler);
		}

		JsonNode rootNode = null;
		try {
			rootNode = JsonUtil.getObjectMapper().readTree(request.getParameter("message"));
			ObjectNode header = (ObjectNode)rootNode.path("header");
			String trCode = header.path("trcode").getTextValue();

			if( !uncheckTrCodes.contains(trCode) ){
				String decReqTime = EncryptionUtil.getDecryptAES256(header.path("request_time").getTextValue());
				if( !getDiffSecBetweenReqTimeAndNowTime(decReqTime)) {
				
					JsonNode 	resultNode 		= new SimpleJsonResponse(false, "TIME001", "Request time is invalid", null).toJson();
					ObjectNode 	resNode 		= JsonUtil.objectNode();
					resNode.put("header", resultNode);
					String 		responseMsg 	= resNode.toString();
					
					response.getOutputStream().write(responseMsg.getBytes("UTF-8"));
					
					logger.error("TIME001 :: Request time is invalid - " + decReqTime);
					return false;
				}
			}
			return super.preHandle(request, response, handler);
		} catch (Exception e) {
			logger.error("요청 데이터 파싱 실패\n요청 데이터 : " + rootNode, e);
			JsonNode 	resultNode 		= new SimpleJsonResponse(false, "TIME001", "Request time is invalid", null).toJson();
			ObjectNode 	resNode 		= JsonUtil.objectNode();
			resNode.put("result", resultNode);
			String 		responseMsg 	= resNode.toString();
			
			response.getOutputStream().write(responseMsg.getBytes("UTF-8"));
			
			return false;
		}

	}

	private boolean getDiffSecBetweenReqTimeAndNowTime(String decReqTime) throws ParseException {
		
		boolean result = false;
		
		SimpleDateFormat 	dateFormat 	= new SimpleDateFormat("yyyyMMddHHmmssSSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		Date 				reqTime 	= dateFormat.parse(decReqTime);

		Calendar 			calendar 	= Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Date 				nowTime 	= calendar.getTime();
		
		calendar.add(Calendar.MINUTE, maxValidMinutes*-1);
		Date 				preTime 	= calendar.getTime();
		
		calendar.setTime(nowTime);
		
		calendar.add(Calendar.MINUTE, maxValidMinutes);
		Date 				postTime 	= calendar.getTime();
		
		if( reqTime.after(preTime) && reqTime.before(postTime) ) {
			result = true;
		} else {
			result = false;
		}
		
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		String url = request.getRequestURI();

		if(uncheckUrls.contains(url)){

			super.postHandle(request, response, handler, modelAndView);

		} else {
			Map<String, Object> model = modelAndView.getModel();

			ObjectNode headerNode = (ObjectNode)model.get("header");
			String trCode = headerNode.path("trcode").getTextValue();

			if( !uncheckTrCodes.contains(trCode) ){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			    String time = dateFormat.format(new Date());

			    boolean result = headerNode.path("result").getBooleanValue();
			    
			    //response 타임을 암호화 한 데이터를 bizmob header에 추가
				ObjectNode responseTimeNode = JsonUtil.objectNode();
				responseTimeNode.put("result", result);
				responseTimeNode.put("time", time);
				
				String encResponseTime = EncryptionUtil.getEncryptAES256(JsonUtil.toJson(responseTimeNode));
				headerNode.put("response_time", encResponseTime);
			}

			super.postHandle(request, response, handler, modelAndView);
		}
	}

	

	public int getMaxValidMinutes() {
		return maxValidMinutes;
	}

	public void setMaxValidMinutes(int maxValidMinutes) {
		this.maxValidMinutes = maxValidMinutes;
	}

	public void setUncheckTrCodes(Set<String> uncheckTrCodes) {
		this.uncheckTrCodes = uncheckTrCodes;
	}

	public void setUncheckUrls(Set<String> uncheckUrls) {
		this.uncheckUrls = uncheckUrls;
	}
}