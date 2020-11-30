package common.util;

import java.util.regex.Pattern;

public class Smart2Constants {
	 
	public static final Pattern         HTML_TITLE_PATTERN                      = Pattern.compile("(<title.*?>)([\\s\\S]*?)(</title>)", 2);
	public static final Pattern         AMP_PATTERN                             = Pattern.compile("(&amp;|&#38;)");
	public static final Pattern         CDATA_TAG_PATTERN                       = Pattern.compile("(&nbsp;)");
    public static final Pattern         QUOTE_PATTERN                           = Pattern.compile("(&quot;|&#34;|&ldquo;|&rdquo;)");
    public static final Pattern         LAQUO_PATTERN                           = Pattern.compile("(&laquo;|&#171;)");
    public static final Pattern         RAQUO_PATTERN                           = Pattern.compile("(&raquo;|&#187;)");
    public static final Pattern         APOSTROPHE_PATTERN                      = Pattern.compile("(&apos;|&#39;|&lsquo;)");
    public static final Pattern         GT_PATTERN                              = Pattern.compile("(&gt;|&#62;)");
    public static final Pattern         LT_PATTERN                              = Pattern.compile("(&lt;|&#60;)");
    public static final Pattern         MIDDOT_PATTERN                          = Pattern.compile("(&middot;|&#183;)");
    public static final Pattern         ELLIPSIS_PATTERN                        = Pattern.compile("(&hellip;|&#133;)");
    
    public static final Pattern         PRE_TAG_PATTERN                         = Pattern.compile("(<pre.*?>)([\\s\\S]*?)(</pre>)", 2);
    public static final Pattern         PRE_TAG_PATTERN1                        = Pattern.compile("(<pre.*?>)", 2);
    public static final Pattern         PRE_TAG_PATTERN2                        = Pattern.compile("(</pre>)", 2);
	    
    public static final Pattern         TAG_PATTERN                             = Pattern.compile("(<)([\\s\\S]*?)(>)", 2);
    
    public static final String          MAIL_HTML_HEADER                        = "<html><head><meta http-equiv=Content-Type content=\"text/html; charset=UTF-8\" /><title>MobileC&C Mail</title></head><body>";
    public static final String          MAIL_HTML_FOOTER                        = "</body></html>";
}
