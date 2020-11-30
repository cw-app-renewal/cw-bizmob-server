package common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FilenameUtils;

import com.mcnc.smart.common.Smart;

public class ConfigurationManager {
	
	static ConfigurationManager configManager;
	private static HashMap<String, PropertiesConfiguration> configurations = new HashMap<String, PropertiesConfiguration>();
	
	static {
		if( configManager == null) {
			String home = Smart.HOME_DIR; 
			if(home.equals(".")) {
				home = "D:/bizmob/workspace/bizmob.coway.SMART_HOME";
			}
			
			home = home + "/conf";
			init(home);	
		}
	}
	
	static void init(String home) {
		try {
			
			File 						SMART2_HOME 		= new File(home);
			File[] 						propertiesList 		= SMART2_HOME.listFiles();
			PropertiesConfiguration 	propConfig 			= new PropertiesConfiguration();
			propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			
			for (File item : propertiesList) {
				if(item.isFile()) {
					String	fileName 	= item.getName(); 
					String 	fileExt 	= FilenameUtils.getExtension( fileName );
					
					if(fileExt.equalsIgnoreCase("properties")) {
						fileName = FilenameUtils.getBaseName(fileName);
						propConfig = new PropertiesConfiguration(item.getAbsolutePath());						
						propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
						configurations.put(fileName, propConfig);
					}
				}
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PropertiesConfiguration getConfiguration(String confID) {
		if(configurations.containsKey(confID)) {
			return configurations.get(confID);
		} else {
			return null;
		}
	}
	
	public static void addConfiguration(String configID, String configurationURL) {
		try {
			PropertiesConfiguration propConfig = new PropertiesConfiguration(configurationURL);
			propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			configurations.put(configID, propConfig);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getString(String configID, String key) {
		if(configurations.containsKey(configID)){
			Configuration config = configurations.get(configID);
			if(config.containsKey(key)) {
				try {
					String newValue = new String(config.getString(key).getBytes("ISO-8859-1"), "UTF-8");
					return newValue;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public String[] getConfigurationsKey() {
		Set<String> 		keySet 	= configurations.keySet();
		Object[]			result	= keySet.toArray();
		String[]			response	= new String[result.length];
		for (int i = 0; i < result.length; i++) {
			response[i] = (String) result[i];
		}
		
		return response;
	}
	
	public static boolean containsConfiguration(String configID) {
		if(configurations.containsKey(configID)){
			return true;
		}
		return false;
	}
}
