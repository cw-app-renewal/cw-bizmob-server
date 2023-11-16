package adapter.push;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.bizmob.adapter.DBAdapter;

import connect.exception.ConnectClientException;
import connect.http.coway.CowayCommonHttpClient;
@Deprecated
public class PushService {

	private static final Logger logger = LoggerFactory.getLogger(PushService.class);

	@Autowired
	private DBAdapter dbAdapter;
	
	@Autowired
	private CowayCommonHttpClient cowayCommonHttpClient;

	public void pushNewNotifications( String url, String previousSearchTime, String currentSearchTime ) throws Exception {
		
		JsonNodeFactory	jsonNodeFactory	= JsonNodeFactory.instance;
		
		//신규 push 내용을 조회
		List<PushNewNotificationDO> newNotifications = selectNewPushNotification(previousSearchTime, currentSearchTime);
			
		for ( PushNewNotificationDO newNotificationDO : newNotifications ) {
			ObjectNode	pushDataNode = jsonNodeFactory.objectNode();
			pushDataNode.put( "approval", 0 );
			pushDataNode.put( "message", "신규 메시지" );
			
			ObjectNode	pushMessageNode	= jsonNodeFactory.objectNode();
			pushMessageNode.put( "user_id",	"user_id" );
			pushMessageNode.put( "push_type",	"message" );
			pushMessageNode.put( "push_target",	"single" );
			pushMessageNode.put( "push_data", 	pushDataNode );
			
			logger.info( "acube approval notification :: " + 
					"url=" + url + ", " + 
					"pushMessageNode=" + pushMessageNode );
			try {
				cowayCommonHttpClient.pushNotification( url, pushMessageNode );
			} catch ( ConnectClientException e ) {
				logger.error( "error parameters :: " + "newNotificationDO=" + newNotificationDO );
			}
		}			

	}
	
	private List<PushNewNotificationDO> selectNewPushNotification(String previousSearchTime, String currentSearchTime) {
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("previousSearchTime", previousSearchTime);
		inputMap.put("currentSearchTime", currentSearchTime);
		
		return (List<PushNewNotificationDO>)dbAdapter.selectList("namespace", "sqlid", inputMap);
	}
	
	
	
}
