package common;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTelNo {

	
	private String[] getSplitTelNo(String noStr) {
		
		Pattern telPattern = Pattern.compile("^(01\\d{1}|\\+82\\d{2})-?(\\d{3,4})-?(\\d{4})");
		if(noStr == null)
			return new String[]{"", "", ""};
		Matcher matcher = telPattern.matcher(noStr);
		if(matcher.matches()) {
			return new String[]{matcher.group(1), matcher.group(2), matcher.group(3)};
		} else {
			return new String[]{"", "", ""};
		}		
	}
	
	@Test
	public void testTelNo() {
		
		try {
			
			String telNo = "01045671234";
			
			String[] phone = getSplitTelNo(telNo);
			
			System.out.println(phone[0] + "-" + phone[1] + "-" + phone[2]);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
