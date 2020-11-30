package adapter.push;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;

@Deprecated
public class PushNotificator {

	private ILogger logger = LoggerService.getLogger(PushNotificator.class);

	@Autowired
	PushService pushService;
	
	private Timer timer;
	
	
	public void activate() {
		long	pushSearchFirstDelayTime	= SmartConfig.getLong( "bizmob.push.first.delay.time" );
		long	pushSearchPeriodTime		= SmartConfig.getLong( "bizmob.push.period.time" );
		timer	= new Timer();
		timer.schedule( new PushTask(), pushSearchFirstDelayTime, pushSearchPeriodTime );
		logger.info( "PushNotificator is activated :: " + 
				"pushSearchFirstDelayTime=" + pushSearchFirstDelayTime + ", " +
				"pushSearchPeriodTime=" + pushSearchPeriodTime );
	}
	
	public void deactivate() {
		if ( timer != null ) {
			timer.cancel();
			timer = null;
			logger.info( "PushNotificator is deactivated" );
		}
	}
	
	
	class PushTask extends TimerTask {

		private long prevSearchTime	= Calendar.getInstance().getTimeInMillis();
		
		@Override
		public void run() {
			String	url	= SmartConfig.getString( "bizmob.push.server.url" );
			Calendar	prevCal	= Calendar.getInstance();
			prevCal.setTimeInMillis( prevSearchTime );
			Calendar	currCal		= Calendar.getInstance();
			SimpleDateFormat	sdf	= new SimpleDateFormat( "yyyyMMddHHmmss" );
			String		prevTime	= sdf.format( prevCal.getTime() );
			String		currTime	= sdf.format( currCal.getTime() );
			try {
				String	enabled	= SmartConfig.getString( "bizmob.push.enabled", "true" );
				logger.info( "check new push notification :: " + "previousTime=" + prevTime + ", currentTime=" + currTime );
				if ( Boolean.valueOf( enabled ) ) {
					pushService.pushNewNotifications( url, prevTime, currTime );
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("", e);
			} catch (Exception e) {
				logger.error("", e);
			}
			this.prevSearchTime	= currCal.getTimeInMillis(); 
			
		}
		
	}
}
