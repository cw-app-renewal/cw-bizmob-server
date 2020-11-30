package common.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;

import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

public class Html2Txt {
	 public static String convertHtmltoTxt2(String htmlStr) throws IOException
	    {    
	        if(htmlStr == null) return "";
	                
	        htmlStr = replaceHtmlSymbols(htmlStr);
	        htmlStr = replacePRE(htmlStr);
	        htmlStr = Smart2Constants.HTML_TITLE_PATTERN.matcher(htmlStr).replaceAll("");
	        htmlStr = htmlStr.replace("<BR>", "#####");
	        htmlStr = htmlStr.replace("<br>", "#####");
	        htmlStr = htmlStr.replace("<P ", "#####<P ");
	        htmlStr = htmlStr.replace("<p ", "#####<p ");
	        htmlStr = htmlStr.replace("<P>", "#####<P>");
	        htmlStr = htmlStr.replace("<p>", "#####<p>");
	        htmlStr = htmlStr.replace("<div>", "#####<div>");
	        htmlStr = htmlStr.replace("<DIV>", "#####<div>");
	        htmlStr = htmlStr.replace("<DIV ", "#####<div ");
	        
	        StringReader reader = new StringReader(htmlStr);

	        Source              source          = new Source(reader);                       
	        Segment             seg             = new Segment(source, source.getBegin(), source.getEnd());                              
	                    
	        String              result          = seg.getTextExtractor().toString();        
	        result = Smart2Constants.TAG_PATTERN.matcher(result).replaceAll("");
	        result = result.replace("#####", "\r\n");
	        result = replaceHtmlSymbols(result);
	                
	        return result;
	    }
	    
	    public static String replaceHtmlSymbols(String htmlStr)
	    {
	        htmlStr = Smart2Constants.AMP_PATTERN.matcher(htmlStr).replaceAll("&");
	        htmlStr = Smart2Constants.CDATA_TAG_PATTERN.matcher(htmlStr).replaceAll(" ");
	        htmlStr = Smart2Constants.QUOTE_PATTERN.matcher(htmlStr).replaceAll("\"");
	        htmlStr = Smart2Constants.LAQUO_PATTERN.matcher(htmlStr).replaceAll("<<");
	        htmlStr = Smart2Constants.RAQUO_PATTERN.matcher(htmlStr).replaceAll(">>");
	        htmlStr = Smart2Constants.APOSTROPHE_PATTERN.matcher(htmlStr).replaceAll("'");
	        htmlStr = Smart2Constants.GT_PATTERN.matcher(htmlStr).replaceAll(">");
	        htmlStr = Smart2Constants.LT_PATTERN.matcher(htmlStr).replaceAll("<");
	        htmlStr = Smart2Constants.MIDDOT_PATTERN.matcher(htmlStr).replaceAll("\267");
	        htmlStr = Smart2Constants.ELLIPSIS_PATTERN.matcher(htmlStr).replaceAll("...");
	        
	        return htmlStr;
	    }
	    
	    public static String replacePRE(String htmlStr)
	    {
	        StringBuffer sb = new StringBuffer();
	        int start = -1;
	        int end = -1;
	        String strInTag;
	        for(Matcher m = Smart2Constants.PRE_TAG_PATTERN.matcher(htmlStr); m.find(); sb.append(strInTag))
	        {
	            start = m.start();
	            if(end == -1 && start > 0)
	                sb.append(htmlStr.substring(0, start));
	            if(end > -1 && start > end)
	                sb.append(htmlStr.substring(end, start));
	            end = m.end();
	            String preStr = htmlStr.substring(start, end);
	            Matcher m1 = Smart2Constants.PRE_TAG_PATTERN1.matcher(preStr);
	            String pre1 = m1.replaceAll("");
	            Matcher m2 = Smart2Constants.PRE_TAG_PATTERN2.matcher(pre1);
	            strInTag = m2.replaceAll("");
	            strInTag = Smart2StringUtil.replace(strInTag, "\r", "");
	            strInTag = Smart2StringUtil.replace(strInTag, "\n", "<BR>");
	        }

	        if(end < htmlStr.length() - 1)
	            if(end > -1)
	                sb.append(htmlStr.substring(end));
	            else
	                sb.append(htmlStr);
	        return sb.toString();
	    }
	    

	    public static String reverseMailBody(String content)
	    {           
	        
	        
	        content = content.replace("\r\n", "<br>").replace("\r", "<br>").replace("\n", "<br>");
//	      String[] str = content.split("\t");
//	      String temp="".trim();
//	      
//	      for(int i=1; i < str.length; i++)
//	      {
//	          temp += str[i];         
//	      }
//	      String[] str2 = temp.split("\r\n");
//	      String cleanBody="".trim();
//	      for(int j=0; j < str2.length; j++)
//	      {
//	          if(!str2[j].equals(""))
//	          {
//	              cleanBody += str2[j];
//	              
//	          }
//	          else
//	          {
//	              cleanBody += "\r\n\r\n";
//	          }           
//	      }
//	      
//	      cleanBody = str[0] + cleanBody;
//	      System.out.println(cleanBody);
//	      return cleanBody;
	        return content;
	    }
	    
}
