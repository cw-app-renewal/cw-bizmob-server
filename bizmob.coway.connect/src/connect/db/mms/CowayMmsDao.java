package connect.db.mms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import connect.db.AbstractMybatisDao;

public class CowayMmsDao extends AbstractMybatisDao {


	public Map<String, Object> callSpMms(String phn_id, String invnr, String title, String msg, String deptId, String imgPath) throws DataAccessException {
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("rcv_phn_id", phn_id);
		inputMap.put("callback", invnr);
		inputMap.put("snd_title", title);
		inputMap.put("snd_msg", msg);
		inputMap.put("rsrvd_id", deptId);
		inputMap.put("img_path", imgPath);
		
		getCowayMmsSessionTemplate().insert(MMS_NAMESPACE + ".callSpMms", inputMap);
		
		return inputMap;		
	}
	
	
	public Map<String, Object> callSpMmsTest(String phn_id, String invnr, String title, String msg, String deptId, String imgPath) throws DataAccessException {
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("rcv_phn_id", phn_id);
		inputMap.put("callback", invnr);
		inputMap.put("snd_title", title);
		inputMap.put("snd_msg", msg);
		inputMap.put("rsrvd_id", deptId);
		inputMap.put("img_path", imgPath);		
		
		getCowayMmsSessionTemplate().insert(MMS_NAMESPACE + ".callSpMmsTest", inputMap);
		
		return inputMap;		
	}	
	
	public String getSystemTime() throws DataAccessException {
		
		return (String) getCowayMmsSessionTemplate().selectOne(MMS_NAMESPACE + ".getSystemTime");
		
	}


	public Map<String, Object> callSpLmsTest(String phnId, String invnr, String title, String content, String rsrvdId) {

		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("rcv_phn_id", phnId);
		inputMap.put("callback", invnr);
		inputMap.put("snd_title", title);
		inputMap.put("snd_msg", content);
		inputMap.put("rsrvd_id", rsrvdId);
		
		getCowayMmsSessionTemplate().insert(MMS_NAMESPACE + ".callSpLmsTest", inputMap);
		
		return inputMap;		
	}


	public Map<String, Object> callSpLms(String phnId, String invnr, String title, String content, String rsrvdId) {

		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("rcv_phn_id", phnId);
		inputMap.put("callback", invnr);
		inputMap.put("snd_title", title);
		inputMap.put("snd_msg", content);
		inputMap.put("rsrvd_id", rsrvdId);
		
		getCowayMmsSessionTemplate().insert(MMS_NAMESPACE + ".callSpLms", inputMap);
		
		return inputMap;		
	}
	
	public Map<String, Object> callSpMms3Image(String phn_id, String invnr, String title, String msg, String deptId, String imgPath, String imgPath2, String imgPath3) throws DataAccessException {
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("rcv_phn_id", phn_id);
		inputMap.put("callback", invnr);
		inputMap.put("snd_title", title);
		inputMap.put("snd_msg", msg);
		inputMap.put("rsrvd_id", deptId);
		inputMap.put("img_path", imgPath);
		inputMap.put("img_path2", imgPath2);
		inputMap.put("img_path3", imgPath3);
		
		getCowayMmsSessionTemplate().insert(MMS_NAMESPACE + ".callSpMms3Image", inputMap);
		
		return inputMap;		
	}
	
	
	public Map<String, Object> callSpMms3ImageTest(String phn_id, String invnr, String title, String msg, String deptId, String imgPath, String imgPath2, String imgPath3) throws DataAccessException {
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put("rcv_phn_id", phn_id);
		inputMap.put("callback", invnr);
		inputMap.put("snd_title", title);
		inputMap.put("snd_msg", msg);
		inputMap.put("rsrvd_id", deptId);
		inputMap.put("img_path", imgPath);
		inputMap.put("img_path2", imgPath2);
		inputMap.put("img_path3", imgPath3);
		
		getCowayMmsSessionTemplate().insert(MMS_NAMESPACE + ".callSpMms3ImageTest", inputMap);
		
		return inputMap;		
	}	

}
