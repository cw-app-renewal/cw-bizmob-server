package adapter.common;

import java.io.File;
import java.util.StringTokenizer;

import org.junit.Test;

import common.util.DateUtil;

public class TestCommon {

	
	@Test
	public void commonStringTest() {
		
		String str = "0123456789ABCD";
		
		System.out.println(str.substring(0, 3));
		System.out.println(str.substring(3, 6));
		
	}
	
	@Test
	public void commonDateTest() {
		
		System.out.println(DateUtil.getToday("yyyyMMdd"));
		
	}
	
	@Test
	public void commonTokenTest() {
		
		String str = "test";
		//String str = "test1\\test2\\test3\\test4";
		
		System.out.println(File.separator);
		
		StringTokenizer st = new StringTokenizer(str, File.separator);
		
		System.out.println(st.countTokens());
		
		while(st.hasMoreTokens() == true) {
			System.out.println(st.nextToken());
		}
		
		
	}
}
