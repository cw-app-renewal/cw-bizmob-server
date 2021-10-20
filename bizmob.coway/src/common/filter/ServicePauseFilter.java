package common.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ServicePauseFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(ServicePauseFilter.class);
	
	private static String _ERROR_CODE = "ERR9999";
    private static PropertiesConfiguration configuration = null;

    private FilterConfig _fc;
    private boolean _servicePause = false;
    private Date _from = null;
    private Date _to = null;
    private String _message = "Service Pause";
    
	public void destroy() {
		this._fc = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		if (isPaused()) {
			try {
				int pos = 0;
				String trcode = null;
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				String servletPath = httpRequest.getServletPath();
				
				if((pos=servletPath.indexOf(".json")) >= 0) {
					response.setContentType("application/json; charset=utf-8");
					response.setCharacterEncoding("utf-8");

					PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
					
					trcode = servletPath.substring(1, pos);
					out.println(getOutputString(trcode));
					out.flush();
					out.close();
				
				} else {
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					httpResponse.setContentType("text/plain; charset=utf-8");
					httpResponse.setCharacterEncoding("utf-8");
					httpResponse.addHeader("error_code", _ERROR_CODE);
					httpResponse.addHeader("info_text", getMimeText(_message));
					httpResponse.addHeader("error_text", getMimeText(_message));
					httpResponse.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
				}
				
			} catch (Exception e) {
				e.printStackTrace(System.err);
				chain.doFilter(request, response);
			}
			
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @return
	 */
	private boolean isPaused() {
		Date now = new Date(System.currentTimeMillis());
		
		return _servicePause && now.after(_from) && now.before(_to);
	}

	/**
	 * @param trcode 
	 * @return
	 */
	private String getOutputString(String trcode) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("{\"header\": {");
		builder.append("\"trcode\":\"").append(trcode).append("\", ");
		builder.append("\"result\":").append(false).append(", ");
		builder.append("\"error_code\":\"").append(_ERROR_CODE).append("\", ");
		builder.append("\"error_text\":\"").append(_message).append("\"");
		builder.append("} }");
		
		return builder.toString();
	}

	public void init(FilterConfig fc) throws ServletException {
		this._fc = fc;
		
        try {
        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            configuration = new PropertiesConfiguration();
			configuration.load(this._fc.getInitParameter("configFile"));
	        //configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
	        
	        _servicePause = configuration.getBoolean("ENABLE", false);
	        logger.info("service pause enable = " + (_servicePause ? "true" : "false"));
	        
	        _message = configuration.getString("MESSAGE", "Service Pause");
	        logger.info("service pause message = " + _message);
	        //message = getKorString(message);
	        //message = getMimeText(message);
	        
	        _from = format.parse(configuration.getString("FROM_TIME", "19010101010101"));
	        _to = format.parse(configuration.getString("TO_TIME", "19010101010101"));
	        logger.info("service pause time = " + _from.toString() + " ~ " + _to.toString());
	        
		} catch (ConfigurationException e) {
			e.printStackTrace(System.err);
		} catch (ParseException e) {
			e.printStackTrace(System.err);
			_from = new Date();
			_to = new Date();
		}
	}
	
    protected String getMimeText(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
    
    protected String getKorString(String str) {
    	try {
    		return new String(str.getBytes("ISO-8859-1"));
    	} catch (UnsupportedEncodingException e) {
            return str;
        }
    }
}
