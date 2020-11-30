package com.mcnc.bizmob.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
 
public class SimpleRequestWrapper extends HttpServletRequestWrapper {
	
//	private static ILogger logger       = LoggerService.getLogger(DownloadController.class);
	
	private static final String PARENT_PATH = "./";
	private static final int NO_GOT_PARENT_PATH = -1;
	
	public SimpleRequestWrapper(HttpServletRequest servletRequest) {   
        super(servletRequest);   
    } 
	
	public String[] getParameterValues(String parameter) {   
		   
		
	      String[] values = super.getParameterValues(parameter);   
	      if (values==null)  {   
	                  return null;   
	          }   
	      int count = values.length;   
	      String[] encodedValues = new String[count];   
	      for (int i = 0; i < count; i++) {
	          encodedValues[i] = filter(values[i]);   
	       }     
	      return encodedValues;    
	    }   
	
	public String getParameter(String parameter) {   
	      String value = super.getParameter(parameter);   
	      if (value == null) {   
	             return null;    
	              }   
	      return filter(value);   
	}   
   
	public String getHeader(String name) {
		return name;
//	    String value = super.getHeader(name);   
//	    if (value == null)   
//	        return null;   
//	    return filter(value);   
	       
	}   
	  
    private String filter(String input) {
        if(input == null) {
            return null;
        }

        int rootIndex = input.indexOf(PARENT_PATH);
        
        if( rootIndex == NO_GOT_PARENT_PATH ) {
        	return input;
        } else {
        	input = input.replaceAll(PARENT_PATH, "");
//        	logger.error("SC_BAD_REQUEST due to invalid parameter : " + input);
        	return input;
        }
    }
	
}
