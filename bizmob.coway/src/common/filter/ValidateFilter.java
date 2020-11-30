package common.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import com.mcnc.smart.common.Smart;


public class ValidateFilter implements Filter {
	
	private final String CONFIG_PROPERTIES = "smart.properties";
	private final String CONFIG_FILE_LOCATION = Smart.HOME_DIR + File.separator + Smart.CONF_DIR_NAME + File.separator + CONFIG_PROPERTIES;
	
	final static String _TRCODE_LIST = "A88888";
	private FilterConfig fc;
	private static Configuration _conf;
	
	public void destroy() {
		this.fc = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String servletPath = httpRequest.getServletPath(); 
		//System.out.println( "servletPath : " + servletPath );
		if( servletPath.contains(".json") ){
			String trcode;
			if( servletPath.startsWith( "/" ) ) 
				trcode = servletPath.substring(1, servletPath.indexOf(".json"));
			else
				trcode = servletPath.substring(0, servletPath.indexOf(".json"));
				
			System.out.println("TRCODE : " + trcode);
			
			fc.getServletContext().getRequestDispatcher( "/validate" + servletPath).forward(request, response);
			
		}else{
			chain.doFilter(request, response);
		}
		
	}


	public void init(FilterConfig fc) throws ServletException {
		// TODO Auto-generated method stub
		this.fc = fc;
	}	
}
