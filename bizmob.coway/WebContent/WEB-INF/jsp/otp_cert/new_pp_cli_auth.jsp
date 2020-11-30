<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="kr.co.kcp.*" %>
<%
    /* ============================================================================== */
    /* =   PAGE : 인증 요청 처리 PAGE                                                     = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2017.06  NHN KCP Inc.   All Rights Reserverd.             = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null 값을 처리하는 메소드                                                = */
    /* = -------------------------------------------------------------------------- = */
        public String f_get_parm( String val )
        {
          if ( val == null ) val = "";
          return  val;
        }
    /* ============================================================================== */
%>
<%
    /* ============================================================================== */
    /* = 라이브러리 및 사이트 정보 include                                                    = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*"%>
    <%@ page import="java.net.URLDecoder"%>
    <%@ include file="../cfg/site_conf_inc.jsp"%>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "euc-kr" ) ;
    /* ============================================================================== */
    /* =   01. 지불 요청 정보 설정                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip     = request.getRemoteAddr();
	String tx_type     = f_get_parm( request.getParameter( "tx_type"     ) );  // 요청 구분
    String ordr_idxx   = f_get_parm( request.getParameter( "ordr_idxx"   ) );  // 주문번호
    /* = -------------------------------------------------------------------------- = */    
    String per_cert_no = f_get_parm( request.getParameter( "per_cert_no" ) );  // 본인확인 거래번호 ( MVNO 조회(2700) 거래 수반한 경우 필수 )
    String phone_no    = f_get_parm( request.getParameter( "phone_no"    ) );  // 인증 요청 휴대폰번호
    String comm_id     = f_get_parm( request.getParameter( "comm_id"     ) );  // 인증 요청 통신사코드
	String user_name   = f_get_parm( request.getParameter( "user_name"   ) );  // 인증 요청 고객명
	String kcp_web_yn  = f_get_parm( request.getParameter( "kcp_web_yn"  ) );  // KCP 표준인증창 사용여부
    /* = -------------------------------------------------------------------------- = */
	/* 주요정보 HASH 처리 */	
    CT_CLI cc =  new CT_CLI();
    //cc.setCharSetUtf8(); 
    String ENC_KEY       = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0"; // 반드시 외부에 노출되지 않도록 관리하시기 바랍니다.	
    String phone_no_hash   = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "phone_no"   ) ));
    String user_name_hash  = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "user_name"  ) ));
    /* = -------------------------------------------------------------------------- = */
    String tran_cd       = "" ;                                                 // 거래구분코드
    /* = -------------------------------------------------------------------------- = */
    String res_cd        = "" ;                                                 // 결과코드
    String res_msg       = "" ;                                                 // 결과메시지
	String res_phone_no  = "" ;                                                 // 인증 결과 휴대폰번호
    String res_comm_id   = "" ;                                                 // 인증 결과 통신사코드	
	String birth_day     = "" ;                                                 // 생년월일   
    String local_code    = "" ;                                                 // 내외국인 구분
    String sex_code      = "" ;                                                 // 성별구분   
    String safe_guard_yn = "" ;                                                 // 번호보호 서비스 가입여부
	String auth_tx_id    = "" ;                                                 // Transaction ID(기관)	- SKT 사용자의 경우 리턴
	//String van_tx_id     = "" ;                                               // 
    //String van_res_cd    = "" ;                                               // 
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   02. 인스턴스 생성 및 초기화                                              = */
    /* = -------------------------------------------------------------------------- = */
    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

//     c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
    c_PayPlus.mf_init_set();
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   03. 처리 요청 정보 설정, 실행                                            = */
    /* = -------------------------------------------------------------------------- = */

    /* = -------------------------------------------------------------------------- = */
    /* =   03-1. 인증 요청                                                          = */
    /* = -------------------------------------------------------------------------- = */
    tran_cd = "00402200";

    int payx_data_set;
    int common_data_set;

    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );

    c_PayPlus.mf_set_us( common_data_set, "cust_ip"   ,  cust_ip        );

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    // 주문 정보
    int ordr_data_set;

    ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );

    c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );

    // 인증 정보
    int cert_data_set;

    cert_data_set   = c_PayPlus.mf_add_set( "cert"      );    

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  tx_type            ); // 요청 구분(2500 고정)
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id            ); // 통신사코드
	c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn         ); // KCP 표준인증창 사용여부(Default:N)
    c_PayPlus.mf_set_us( cert_data_set, "phone_no"        ,  phone_no           ); // 휴대폰번호
	c_PayPlus.mf_set_us( cert_data_set, "phone_no_hash"   ,  phone_no_hash      ); // 휴대폰번호 HASH	
    c_PayPlus.mf_set_us( cert_data_set, "user_name"       ,  user_name          ); // 고객명
	c_PayPlus.mf_set_us( cert_data_set, "user_name_hash"  ,  user_name_hash     ); // 고객명 HASH
	c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no        ); // 본인확인 거래번호 ( MVNO 조회(2700) 거래 수반한 경우 필수 )

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

    /* ============================================================================== */
    /* =   03-3. 실행                                                               = */
    /* ------------------------------------------------------------------------------ */
    if ( tran_cd.length() > 0 )
    {
//         c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
    }
    else
    {
        c_PayPlus.m_res_cd  = "9562";
        c_PayPlus.m_res_msg = "연동 오류";
    }
    res_cd  = c_PayPlus.m_res_cd;                      // 결과 코드
    res_msg = c_PayPlus.m_res_msg;                     // 결과 메시지
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. 인증 결과 처리                                                       = */
    /* = -------------------------------------------------------------------------- = */
    if ( res_cd.equals( "0000" ) )
    {
    /* = -------------------------------------------------------------------------- = */
    /* =   04-1. 인증 결과 처리                                                     = */
    /* = -------------------------------------------------------------------------- = */
        per_cert_no   = c_PayPlus.mf_get_res( "per_cert_no"   ); // 본인확인 거래번호
		res_phone_no  = c_PayPlus.mf_get_res( "phone_no"      ); // 휴대폰번호
		res_comm_id   = c_PayPlus.mf_get_res( "comm_id"       ); // 통신사코드		
		safe_guard_yn = c_PayPlus.mf_get_res( "safe_guard_yn" ); // 휴대폰보호서비스 민앤지 가입여부
		birth_day     = c_PayPlus.mf_get_res( "birth_day"     ); // 생년월일
        sex_code      = c_PayPlus.mf_get_res( "sex_code"      ); // 성별코드
        local_code    = c_PayPlus.mf_get_res( "local_code"    ); // 내/외국인 구분
		auth_tx_id    = c_PayPlus.mf_get_res( "auth_tx_id"    ); // Transaction ID(기관) - SKT 사용자의 경우 리턴
		//van_tx_id     = c_PayPlus.mf_get_res( "van_tx_id"     ); // Transaction ID(KCP)
		//van_res_cd    = c_PayPlus.mf_get_res( "van_res_cd"    ); // 기관응답코드

    }    // End of [res_cd = "0000"]
    /* = -------------------------------------------------------------------------- = */
    /* =   04-2. 인증 실패를 업체 자체적으로 DB 처리 작업하시는 부분입니다.         = */
    /* = -------------------------------------------------------------------------- = */
    else
    {

    }
    /* ============================================================================== */

    /* ============================================================================== */
    /* =   05. 폼 구성 및 결과페이지 호출                                           = */
    /* ============================================================================== */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
	<title>*** KCP Online Payment System [JSP Version] ***</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <link href="css/style.css" rel="stylesheet" type="text/css"/>

    </head>

 <body>
	<div id="sample_wrap">
    <form name="pay_info" method="post" action="./new_pp_cli_otp.jsp">
	       <!-- 타이틀 Start -->
               <h1>[CI /DI 요청] <span>이 페이지는 앱 인증 완료 후 CI /DI 를 요청하는 샘플(예시) 페이지입니다.</span></h1>
           <!-- 타이틀 End -->

            <div class="sample">
		    <!-- 상단 테이블 Start -->     
               <p>
                    소스 수정 시 가맹점의 상황에 맞게 적절히 수정 적용하시길 바랍니다.<br />
                    요청 시 필요한 정보를 정확하게 입력하시어 진행하시기 바랍니다.
               </p>
            <!-- 상단 테이블 End -->

				<!-- 요청 결과 출력 테이블 Start -->
                <h2>&sdot; 요청 결과 정보</h2>
                <table class="tbl" cellpadding="0" cellspacing="0">
                     <!-- 결과코드 -->
                    <tr>
                        <th>결과코드</th>
                        <td><%=res_cd%></td>
                    </tr>
                    <!-- 결과메세지 -->
                    <tr>
                        <th>결과메세지</th>
                        <td><%= res_msg %></td>
                    </tr>                    
                </table>
                <!-- 요청 결과 출력 테이블 End -->

<%
            if ( "0000".equals ( res_cd ) )
            {
%>
		        <!-- 주문 정보 출력 테이블 Start -->
                <h2>&sdot; 본인확인 결과 정보</h2>
                <table class="tbl" cellpadding="0" cellspacing="0">
                     <!-- 주문번호 -->
                    <tr>
                        <th>주문 번호</th>
                        <td><%=ordr_idxx%></td>
                    </tr>
                    <!-- per_cert_no -->
                    <tr>
                        <th>거래번호</th>
                        <td><%= per_cert_no %></td>
                    </tr>
                    <!-- res_phone_no -->
                    <tr>
                        <th>휴대폰번호</th>
                        <td><%= res_phone_no %></td>
                    </tr>
                    <!-- res_comm_id -->
                    <tr>
                        <th>통신사코드</th>
                        <td><%= res_comm_id %></td>
                    </tr>
                    <!-- safe_guard_yn -->
                    <tr>
                        <th>"민앤지" 가입 여부</th>
                        <td><%= safe_guard_yn %></td>
                    </tr>
                    
                    <!-- birth_day -->
<%
		if ( ! "".equals (birth_day) )
		{
%>					
                    <tr>
                        <th>생년월일</th>
                        <td><%= birth_day %></td>
                    </tr>
<%
		}
%>

					<!-- sex_code -->
<%
		if ( ! "".equals (sex_code) )
		{
%>	
                    <tr>
                        <th>성별 정보</th>
                        <td><%= sex_code %></td>
                    </tr>
<%
		}
%>

                    <!-- local_code -->
<%
		if ( ! "".equals (local_code) )
		{
%>	
                    <tr>
                        <th>내/외국인 정보</th>
                        <td><%= local_code %></td>
                    </tr>
<%
		}
%>

                   <!-- auth_tx_id -->
<%
		if ( "SKT".equals (comm_id) )
		{
%>
					
                    <tr>
                        <th>auth_tx_id</th>
                        <td><%= auth_tx_id %></td>
                    </tr>
<%
		}
%>

                </table>
                <!-- 주문 정보 출력 테이블 End -->
<%
			}
%>

                <!-- 결제 버튼 테이블 Start -->
                    <div class="btnset">
                    <input name="" type="submit" class="submit" value="CI/DI요청"/>
                    <a href="../index.html" class="home">처음으로</a>
                    </div>
                    <!-- 결제 버튼 테이블 End -->

	        <div class="footer">
                Copyright (c) NHN KCP INC. All Rights reserved.
            </div>            

		    <!-- CI / DI 요청 테이블 Start -->
            <input type="hidden" name="auth_tx_id"    value="<%= auth_tx_id    %>" />  <!-- Transaction ID(기관) - SKT 사용자의 경우 리턴. -->
            <input type="hidden" name="kcp_web_yn"    value="<%= kcp_web_yn    %>" />  <!-- KCP 표준인증창 사용여부 -->
            <input type="hidden" name="comm_id"       value="<%= comm_id       %>" />  <!-- 통신사코드 -->
            <input type="hidden" name="per_cert_no"   value="<%= per_cert_no   %>" />  <!-- 본인확인 거래번호 -->
			<input type="hidden" name="tx_type"       value="2600"                 />  <!-- 요청 구분 -->
			<!-- CI / DI 요청 테이블 End -->
 </div>
        </form>
		</div>
    </body>
    </html>
