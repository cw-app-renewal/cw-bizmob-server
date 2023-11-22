package common.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LogType;

public class BizmobLogger implements ILogger {

	private String clazzName;
	private transient Logger logger;
	private static String FQCN = BizmobLogger.class.getName() + ".";
	private static boolean useLog4JLogging = false;

	public BizmobLogger() {
		//org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		logger = Logger.getRootLogger();
//		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
	}

	public BizmobLogger(Class<?> clazz) {
		//org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		this.clazzName = clazz.getName();
		logger = Logger.getLogger(clazzName);
//		if(useLog4JLogging == false) {
//			org.apache.ibatis.logging.LogFactory.useLog4JLogging();
//			useLog4JLogging = true;
//		}
	}
	
	@Override
	public void debug(String msg) {
		logger.log(FQCN, Level.DEBUG, msg, null);		
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger.log(FQCN, Level.DEBUG, msg, t);	
	}

	@Override
	public void debug(long arg0, String msg) {
		debug(msg);
	}

	@Override
	public void debug(long arg0, String msg, Throwable t) {
		debug(msg, t);	
	}

	@Override
	public void error(String msg) {
		logger.log(FQCN, Level.ERROR, msg, null);	
	}

	@Override
	public void error(String msg, Throwable t) {
		logger.log(FQCN, Level.ERROR, msg, t);	
	}

	@Override
	public void error(long arg0, String msg) {
		error(msg);
	}

	@Override
	public void error(long arg0, String msg, Throwable t) {
		error(msg, t);
	}

	@Override
	public void fatal(String msg) {
		logger.log(FQCN, Level.FATAL, msg, null);		
	}

	@Override
	public void fatal(String msg, Throwable t) {
		logger.log(FQCN, Level.FATAL, msg, t);	
	}

	@Override
	public void fatal(long arg0, String msg) {
		fatal(msg);
	}

	@Override
	public void fatal(long arg0, String msg, Throwable t) {
		fatal(msg, t);	
	}

	@Override
	public void info(String msg) {
		logger.log(FQCN, Level.INFO, msg, null);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger.log(FQCN, Level.INFO, msg, t);
	}

	@Override
	public void info(long arg0, String msg) {
		info(msg);
	}

	@Override
	public void info(long arg0, String msg, Throwable t) {
		info(msg, t);	
	}
	
	@Override
	public void trace(String msg) {
		logger.log(FQCN, Level.TRACE, msg, null);
	}

	@Override
	public void trace(String msg, Throwable t) {
		logger.log(FQCN, Level.TRACE, msg, t);
	}

	@Override
	public void trace(long arg0, String msg) {
		trace(msg);
	}

	@Override
	public void trace(long arg0, String msg, Throwable t) {
		trace(msg, t);
	}

	@Override
	public void warn(String msg) {
		logger.log(FQCN, Level.WARN, msg, null);
	}

	@Override
	public void warn(String msg, Throwable t) {
		logger.log(FQCN, Level.WARN, msg, t);
	}

	@Override
	public void warn(long arg0, String msg) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void warn(long arg0, String msg, Throwable t) {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public LogType getLoggingLevel() {
		LogType logType = LogType.DEBUG;

		if (logger.getLevel() == Level.DEBUG) {
			logType = LogType.DEBUG;
		} else if (logger.getLevel() == Level.WARN) {
			logType = LogType.WARN;
		} else if (logger.getLevel() == Level.ERROR) {
			logType = LogType.ERROR;
		} else if (logger.getLevel() == Level.FATAL) {
			logType = LogType.FATAL;
		} else if (logger.getLevel() == Level.TRACE) {
			logType = LogType.TRACE;
		}
		
		return logType;
	}

	@Override
	public void setLoggingLevel(LogType logLevel) {
		logger.setLevel(convertToLog4jLevel(logLevel));	
	}

	private Level convertToLog4jLevel(LogType logLevel) {
		
		Level level = Level.DEBUG;

		if (logLevel == LogType.DEBUG) {
			level = Level.DEBUG;
		} else if (logLevel == LogType.WARN) {
			level = Level.WARN;
		} else if (logLevel == LogType.ERROR) {
			level = Level.ERROR;
		} else if (logLevel == LogType.FATAL) {
			level = Level.FATAL;
		} else if (logLevel == LogType.TRACE) {
			level = Level.TRACE;
		}

		return level;
	}
}
