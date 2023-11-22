package common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Smart2StringUtil {
	public static final int RIGHT = 1;
    public static final int LEFT = 2;

    public Smart2StringUtil()
    {
    }

    public static String subString(String str, int offset, int leng)
    {
        return new String(str.getBytes(), offset - 1, leng);
    }

    public static String subString(String str, int offset)
    {
        byte bytes[] = str.getBytes();
        int size = bytes.length - (offset - 1);
        return new String(bytes, offset - 1, size);
    }

    public static String fitString(String str, int size)
    {
        return fitString(str, size, 2);
    }

    public static String fitString(String str, int size, int align)
    {
        byte bts[] = str.getBytes();
        int len = bts.length;
        if(len == size)
            return str;
        if(len > size)
        {
            String s = new String(bts, 0, size);
            if(s.length() == 0)
            {
                StringBuffer sb = new StringBuffer(size);
                for(int idx = 0; idx < size; idx++)
                    sb.append("?");

                s = sb.toString();
            }
            return s;
        }
        if(len < size)
        {
            int cnt = size - len;
            char values[] = new char[cnt];
            for(int idx = 0; idx < cnt; idx++)
                values[idx] = ' ';

            if(align == 1)
                return String.copyValueOf(values) + str;
            else
                return str + String.copyValueOf(values);
        } else
        {
            return str;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static String[] toStringArray(String str)
    {
        List list = new ArrayList();
        for(StringTokenizer st = new StringTokenizer(str); st.hasMoreTokens(); list.add(st.nextToken()));
        return toStringArray(list);
    }

    @SuppressWarnings("rawtypes")
    public static String[] toStringArray(List list)
    {
        String strings[] = new String[list.size()];
        for(int idx = 0; idx < list.size(); idx++)
            strings[idx] = list.get(idx).toString();

        return strings;
    }

    public static String replace(String src, String from, String to)
    {
        if(src == null)
            return null;
        if(from == null)
            return src;
        if(to == null)
            to = "";
        StringBuffer buf = new StringBuffer();
        int pos;
        while((pos = src.indexOf(from)) >= 0) 
        {
            buf.append(src.substring(0, pos));
            buf.append(to);
            src = src.substring(pos + from.length());
        }
        buf.append(src);
        return buf.toString();
    }

    public static String cutString(String str, int limit)
    {
        if(str == null || limit < 4)
            return str;
        int len = str.length();
        int cnt = 0;
        int index;
        for(index = 0; index < len && cnt < limit;)
            if(str.charAt(index++) < '\u0100')
                cnt++;
            else
                cnt += 2;

        if(index < len)
            str = str.substring(0, index - 1) + "...";
        return str;
    }

    public static String cutEndString(String src, String end)
    {
        if(src == null)
            return null;
        if(end == null)
            return src;
        int pos = src.indexOf(end);
        if(pos >= 0)
            src = src.substring(0, pos);
        return src;
    }

    public static char[] makeCharArray(char c, int cnt)
    {
        char a[] = new char[cnt];
        Arrays.fill(a, c);
        return a;
    }

    public static String getString(char c, int cnt)
    {
        return new String(makeCharArray(c, cnt));
    }

    public static String getLeftTrim(String lstr)
    {
        if(!lstr.equals(""))
        {
            int strlen = 0;
            int cptr = 0;
            boolean lpflag = true;
            strlen = lstr.length();
            cptr = 0;
            lpflag = true;
            do
            {
                char chk = lstr.charAt(cptr);
                if(chk != ' ')
                    lpflag = false;
                else
                if(cptr == strlen)
                    lpflag = false;
                else
                    cptr++;
            } while(lpflag);
            if(cptr > 0)
                lstr = lstr.substring(cptr, strlen);
        }
        return lstr;
    }

    public static String getRightTrim(String lstr)
    {
        if(!lstr.equals(""))
        {
            int strlen = 0;
            int cptr = 0;
            boolean lpflag = true;
            strlen = lstr.length();
            cptr = strlen;
            lpflag = true;
            do
            {
                char chk = lstr.charAt(cptr - 1);
                if(chk != ' ')
                    lpflag = false;
                else
                if(cptr == 0)
                    lpflag = false;
                else
                    cptr--;
            } while(lpflag);
            if(cptr < strlen)
                lstr = lstr.substring(0, cptr);
        }
        return lstr;
    }

    public static String getLeft(String str, int len)
    {
        if(str == null)
            return "";
        else
            return str.substring(0, len);
    }

    public static String getRight(String str, int len)
    {
        if(str == null)
            return "";
        String dest = "";
        for(int i = str.length() - 1; i >= 0; i--)
            dest = dest + str.charAt(i);

        str = dest;
        str = str.substring(0, len);
        dest = "";
        for(int i = str.length() - 1; i >= 0; i--)
            dest = dest + str.charAt(i);

        return dest;
    }

    public static String nvl(String str, String replace)
    {
        if(str == null)
            return replace;
        else
            return str;
    }

    public static String checkEmpty(String str, String replace)
    {
        if(str == null || str.equals(""))
            return replace;
        else
            return str;
    }

    public static String capitalize(String str)
    {
        int strLen;
        if(str == null || (strLen = str.length()) == 0)
            return str;
        else
            return (new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String getErrorTrace(Exception e)
    {
        if(e == null)
        {
            return "";
        } else
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String errTrace = sw.toString();
            return errTrace;
        }
    }

    public static String escapeXml(String s)
    {
        if(s == null)
            return "";
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(c == '&')
                sb.append("&amp;");
            else
                sb.append(c);
        }

        return sb.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getTokenList(String s, String token)
    {
        if(s == null || s.equals(""))
            return null;
        List tokenList = new ArrayList();
        for(StringTokenizer st = new StringTokenizer(s, token); st.hasMoreTokens(); tokenList.add(st.nextToken().trim()));
        return tokenList;
    }

    public static int getTokenLength(String s, String token)
    {
        if(s == null)
            return 0;
        int len = 0;
        for(StringTokenizer st = new StringTokenizer(s, token); st.hasMoreTokens();)
            len++;

        return len;
    }

    public static String getToken(int index, String s, String token)
    {
        if(s == null)
            return "";
        StringTokenizer st = new StringTokenizer(s, token);
        StringBuffer sb = new StringBuffer("");
        int i = 0;
        do
        {
            if(!st.hasMoreTokens())
                break;
            if(index == i)
            {
                sb.append(st.nextToken());
                break;
            }
            st.nextToken();
            i++;
        } while(true);
        if(sb.toString().length() > 0)
            return sb.toString().trim();
        else
            return "";
    }

    public static String getToken(int index, String s, String token, String nvl)
    {
        if(s == null)
            return nvl;
        StringTokenizer st = new StringTokenizer(s, token);
        StringBuffer sb = new StringBuffer("");
        int i = 0;
        do
        {
            if(!st.hasMoreTokens())
                break;
            if(index == i)
            {
                sb.append(st.nextToken());
                break;
            }
            st.nextToken();
            i++;
        } while(true);
        if(sb.toString().length() > 0)
            return sb.toString().trim();
        else
            return nvl;
    }

    public static void deleteStringBuffer(StringBuffer strBuf)
    {
        strBuf.delete(0, strBuf.length());
    }

    public static boolean isset(String str)
    {
        return str != null && str.length() > 0;
    }

    public static String collapse(String str, String characters, String replacement)
    {
        if(str == null)
            return null;
        StringBuffer newStr = new StringBuffer();
        boolean prevCharMatched = false;
        for(int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if(characters.indexOf(c) != -1)
            {
                if(!prevCharMatched)
                {
                    prevCharMatched = true;
                    newStr.append(replacement);
                }
            } else
            {
                prevCharMatched = false;
                newStr.append(c);
            }
        }

        return newStr.toString();
    }

    public static String getString(String str, int max)
    {
        byte temp[] = str.getBytes();
        int count = 0;
        int str_count;
        for(str_count = 0; max > str_count && str_count != temp.length; str_count++)
            if(temp[str_count] < 0)
                count++;

        if(count % 2 != 0)
            str = new String(temp, 0, str_count - 1);
        else
            str = new String(temp, 0, str_count);
        return str;
    }

    public static boolean checkUndefined(Object obj)
    {
        boolean result = false;
        if(obj.toString().equals("Undefined"))
            result = true;
        else
            result = false;
        return result;
    }

    public static String dashedPdaNo(String pdaNo)
    {
        String firstPdaNo = "";
        String secondPdaNo = "";
        String thirdPdaNo = "";
        if(null == pdaNo || 0 == pdaNo.trim().length())
            return "";
        if(pdaNo.trim().length() < 10 || 11 < pdaNo.trim().length())
        {
            return pdaNo;
        } else
        {
            firstPdaNo = pdaNo.substring(0, 3) + "-";
            secondPdaNo = pdaNo.substring(3, pdaNo.length() - 4) + "-";
            thirdPdaNo = pdaNo.substring(pdaNo.length() - 4);
            return firstPdaNo + secondPdaNo + thirdPdaNo;
        }
    }

    public static String makeLikeValue(String value)
    {
        StringBuffer sb = new StringBuffer();
        sb.append('%');
        if(value != null)
            sb.append(value);
        sb.append('%');
        return sb.toString();
    }

    public static boolean existsNonAscii(String src)
    {
        byte b[] = src.getBytes();
        for(int i = 0; i < b.length; i++)
            if(b[i] < 0)
                return true;

        return false;
    }

    public static String[] parseGuid(String input, String separator, int count)
    {
        StringTokenizer token = new StringTokenizer(input, separator);
        if(token.countTokens() != count)
            return null;
        String outputs[] = new String[token.countTokens()];
        int i = 0;
        while(token.hasMoreElements()) 
            outputs[i++] = token.nextToken();
        return outputs;
    }
    
    
    public static String encode( String s, String enc ) throws UnsupportedEncodingException {
        return URLEncoder.encode( s, enc ).replaceAll( "\\+", "%20" );
    }
    
    public static String getEncode(String boxName)
    {
        try {
            return encode(boxName, "UTF-8" );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }   
        return null;
    }
    
    public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer
					.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}
	
	public static Boolean getTestDecode(String password, byte[] p1){
		byte[] data =  new byte [256];
		
		p1 = "kisatest".getBytes();
		
		return true;
	}
}
