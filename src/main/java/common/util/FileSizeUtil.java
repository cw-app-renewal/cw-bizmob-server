package common.util;

import java.math.BigDecimal;

public class FileSizeUtil {
    
	public static String getAttachFileSize(String fileSize) {
		
		if(fileSize == null || fileSize.equals("") == true) {
			return fileSize;
		}
		
		long size = Long.parseLong(fileSize);
		
		return getFileSize(size);
	}
	
	
    public static String getFileSize(long fileSize) {
        String attacheSize;
         
        
        if(fileSize >= 1024)
        {
            fileSize = fileSize/1024;
            if(fileSize >= 1024)
            {
                BigDecimal size = new BigDecimal(fileSize);
                BigDecimal dev  = new BigDecimal("1024");
                BigDecimal file = size.divide(dev,1, BigDecimal.ROUND_HALF_UP);
                
                attacheSize = file.doubleValue() + "M";
            }
            else
            {
                attacheSize = String.valueOf(fileSize)+"K";
            }                                   
        }
        else
        {
            attacheSize = String.valueOf(fileSize)+"b";
        }
        
        return attacheSize;
    }
    
    public static String getFileSize( final String fileSize ) {
        return getFileSize( Long.valueOf( fileSize ) );
    }

    public static String toKB( final long fileSize ) {
        String result = processRoundAndUnitName( fileSize / 1024.0, " KB" );
        if(result.equalsIgnoreCase("0 KB")) {
            result = "1 KB";
        }
        return result;
    }
    
    public static String KbToKb(final long fileSize) {
    	String result = processRoundAndUnitName( fileSize, " KB" );
    	if(result.equalsIgnoreCase("0 KB")) {
            result = "1 KB미만";
        }
    	
    	return result;
    }
    
    public static String toKB( final String fileSize ) {
        String result = processRoundAndUnitName( Double.valueOf( fileSize ) / 1024.0, " KB" );
        
        if(result.equalsIgnoreCase("0 KB")) {
            result = "1 KB";
        }
        return result;
    }
    
    public static final String processRoundAndUnitName( double size, String unitName ) {
        return Math.round( size ) + unitName;
    }
    
    

}
