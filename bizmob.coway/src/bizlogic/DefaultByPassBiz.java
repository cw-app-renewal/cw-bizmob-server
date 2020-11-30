
package bizlogic;

import java.util.HashMap;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.common.util.JsonUtil;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.json.SimpleJsonResponse;
import com.mcnc.smart.hybrid.common.server.AbstractJsonAdaptor;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;
import com.mcnc.smart.hybrid.common.server.MessageObject;
import com.mcnc.smart.hybrid.common.server.MessageObject.TYPE;
import com.mcnc.smart.hybrid.common.server.MessageProcessor;
import com.mcnc.smart.hybrid.server.core.AbstractMessageProcessor;


public class DefaultByPassBiz extends AbstractMessageProcessor implements MessageProcessor {
	
    private ILogger   logger = LoggerService.getLogger(DefaultByPassBiz.class);
    
    @Autowired
    AbstractJsonAdaptor adapterDispatcher;

    public MessageObject process(String id, MessageObject session, MessageObject request) {
        String errorCode = null;
        JsonAdaptorObject reqObj = null;
        JsonAdaptorObject resObj = null;
        MessageObject theObj = null;

        // Adaptor dispatcher 얻기
        try {
            // JSON 값이 정상으로 전달 되었는 지 확인
            if (request.containsKey(MessageObject.TYPE.JSON)) {
                // Adaptor Dispatcher에 전달할 Object 생성
                reqObj = buildAdpatorObject(session, null, request);
            } else {
                errorCode = id + _ERROR_PREFIX + _EMPTY_REQUEST;
            }
        } catch (Exception e) {
            errorCode = id + _ERROR_PREFIX + _SYSTEM_EXCEPTION;
        } catch (Throwable t) {
            errorCode = id + _ERROR_PREFIX + _SYSTEM_EXCEPTION;
        } finally {
            theObj = new MessageObject();
        }

        if (errorCode != null) {
            SimpleJsonResponse res = new SimpleJsonResponse(false, errorCode, null, null);

            theObj.put(MessageObject.TYPE.ERROR, res);
            
        } else {
            try {
                // Adaptor Dispatcher 실행
                resObj = adapterDispatcher.dispatch(reqObj);
                
                logger.debug("Success adapter");
                logger.debug("Response Object set:" + resObj.keySet());

                // 반환할 객체 생성
                theObj = buildResultObject(id, resObj);
                
                //logger.debug("Response Data : " + theObj.toString());
            } catch (Exception e) {
                SimpleJsonResponse res = new SimpleJsonResponse(false, id + _ERROR_PREFIX + _SYSTEM_EXCEPTION, e.getMessage(), null);

                theObj.put(MessageObject.TYPE.ERROR, res);
                
                logger.warn("Failed to execute the adapter : ", e);
            }
        }

        return theObj;
    }
    
    
	/**
	 * Adapter에서 처리할 수 있는 data 형식으로 만든다.
	 * @param meta 세션 정보를 저장한 {@link com.mcnc.smart.hybrid.common.server.MessageObject} 객체
	 * @param server Business Logic 정보를 저장한 {@link com.mcnc.smart.hybrid.common.server.MessageObject} 객체
	 * @param request client의 요청 정보를 저장한 {@link com.mcnc.smart.hybrid.common.server.MessageObject} 객체
	 * @return Adapter에서 처리할 수 있는 data 형식인 {@link com.mcnc.smart.hybrid.common.server.JsonAdaptorObject} 객체
	 */
//	protected JsonAdaptorObject buildAdpatorObject(MessageObject meta, MessageObject server, MessageObject request) {
//		JsonAdaptorObject obj = new JsonAdaptorObject();
//
//		if (meta != null && meta.containsKey(MessageObject.TYPE.JSON)) {
//			obj.put(JsonAdaptorObject.TYPE.META, (JsonNode) meta.get(MessageObject.TYPE.JSON));
//		}
//
//		if (server != null && server.containsKey(MessageObject.TYPE.JSON)) {
//			obj.put(JsonAdaptorObject.TYPE.SERVER, (JsonNode) server.get(MessageObject.TYPE.JSON));
//		}
//
//		if (request != null && request.containsKey(MessageObject.TYPE.JSON)) {
//			obj.put(JsonAdaptorObject.TYPE.REQUEST, (JsonNode) request.get(MessageObject.TYPE.JSON));
//		}
//
//		return obj;
//	}

	/**
	 * Adapter의 처리 결과를 BizMOB 서버에서 처리할 수 있는 data 형식으로 만든다.
	 * @param trcode 전문 코드
	 * @param obj Adapter의 처리 결과를 저장한 {@link com.mcnc.smart.hybrid.common.server.JsonAdaptorObject} 객체
	 * @return Business Logic에서 처리할 수 있는 data 형식인 {@link com.mcnc.smart.hybrid.common.server.MessageObject} 객체
	 */
//	protected MessageObject buildResultObject(String trcode, JsonAdaptorObject obj) {
//		JsonNode root = null;
//		MessageObject value = new MessageObject();
//		SimpleJsonResponse errRes = null;
//
//		try {
//			if (obj == null) {
//				errRes = new SimpleJsonResponse(false, trcode + _ERROR_PREFIX + _NULL_RESPONSE, null, null);
//				value.put(MessageObject.TYPE.ERROR, errRes);
//				logger.debug("Empty response from adaptor!");
//
//				return value;
//			} else if (obj.containsKey(JsonAdaptorObject.TYPE.RESULT)) {
//				root = obj.get(JsonAdaptorObject.TYPE.RESULT);
//				logger.debug("Get the result from adapter!");
//				
//				if(isTraceable()) {
//				    logger.trace("Result from adaptor : " + root.toString());
//				}
//
//				if (root.findPath(Codes._JSON_MESSAGE_RESULT).getBooleanValue() != true) {
//					errRes = new SimpleJsonResponse(false, root.findPath(Codes._JSON_MESSAGE_ERRORCODE).getTextValue(), 
//							root.findPath(Codes._JSON_MESSAGE_ERRORTEXT).getTextValue(), 
//							root.findPath(Codes._JSON_MESSAGE_INFOTEXT).getTextValue());
//					value.put(MessageObject.TYPE.ERROR, errRes);
//
//					return value;
//				}
//			}
//
//			if (obj.containsKey(JsonAdaptorObject.TYPE.META)) {
//				JsonNode node = (JsonNode) obj.get(JsonAdaptorObject.TYPE.META);
//				Iterator<String> iter = node.getFieldNames();
//				HashMap<String, Object> map = new HashMap<String, Object>();
//
//				while (iter.hasNext()) {
//					String name = iter.next();
//					map.put(name, JsonUtil.getValue(node, name));
//				}
//
//				value.put(TYPE.SESSION, map);
//				logger.trace("Get the session access from adapter!");
//			}
//
//			if (obj.containsKey(JsonAdaptorObject.TYPE.RESPONSE)) {
//				root = obj.get(JsonAdaptorObject.TYPE.RESPONSE);
//
//				if (root.findPath(Codes._JSON_MESSAGE_HEADER) == null) {
//					errRes = new SimpleJsonResponse(false, trcode + _ERROR_PREFIX + _JSON_NO_HEADER, null, null);
//					value.put(MessageObject.TYPE.ERROR, errRes);
//					logger.debug("No response header message from adaptor!");
//				} else {
//					value.put(MessageObject.TYPE.JSON, root);
//				}
//			} else {
//				errRes = new SimpleJsonResponse(false, trcode + _ERROR_PREFIX + _EMPTY_RESPONSE, null, null);
//				value.put(MessageObject.TYPE.ERROR, errRes);
//				logger.debug("No response message from adaptor!");
//			}
//		} catch (Exception e) {
//			errRes = new SimpleJsonResponse(false, trcode + _ERROR_PREFIX + _SYSTEM_EXCEPTION, null, null);
//			value.put(MessageObject.TYPE.ERROR, errRes);
//			logger.debug(e.getMessage());
//		}
//
//		return value;
//	}    
}
