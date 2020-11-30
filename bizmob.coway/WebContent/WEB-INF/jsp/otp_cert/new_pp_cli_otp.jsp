<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : OTP 재전송 처리 PAGE                                                  = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2017.06  NHN KCP Inc.   All Rights Reserverd.             = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null 값을 처리하는 메소드                                                                                                                           = */
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
    /* = 라이브러리 및 사이트 정보 include                                          = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*"%>
    <%@ page import="java.net.URLDecoder"%>
    <%@ include file="../cfg/site_conf_inc.jsp" %>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "euc-kr" ) ;
    /* ============================================================================== */
    /* =   01. 처리 요청 정보 설정                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip         = request.getRemoteAddr();
    String ordr_idxx       = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // 주문번호
    /* = -------------------------------------------------------------------------- = */
    String tx_type         = f_get_parm( request.getParameter( "tx_type"         ) );  // 요청 타입
    String kcp_web_yn      = f_get_parm( request.getParameter( "kcp_web_yn"      ) );  // KCP 표준인증창 사용여부
    String per_cert_no     = f_get_parm( request.getParameter( "per_cert_no"     ) );  // 본인확인 거래번호
    String comm_id         = f_get_parm( request.getParameter( "comm_id"         ) );  // 통신사코드
    String auth_tx_id      = f_get_parm( request.getParameter( "auth_tx_id"      ) ); // Transaction ID(기관)
    /* = -------------------------------------------------------------------------- = */
    String tran_cd         = "" ;                                                 // 거래구분코드
    /* = -------------------------------------------------------------------------- = */
    String res_cd          = "" ;                                                 // 결과코드
    String res_msg         = "" ;                                                 // 결과메시지  
    String CI              = "" ;                                                 // CI값
    String DI              = "" ;                                                 // DI값
    String CI_URL          = "" ;                                                 // URL인코딩된 CI값
    String DI_URL          = "" ;                                                 // URL인코딩된 DI값    
    String res_phone_no    = "" ;                                                 // 휴대폰번호
    String res_user_name   = "" ;                                                 // 고객명
    String res_comm_id     = "" ;                                                 // 통신사코드
    String res_auth_tx_id  = "" ;                                                 // Transaction ID(기관)
    //String van_tx_id       = "" ;                                               // Transaction ID(KCP)                                          
    /* ============================================================================== */ 
    String birth_day       = "" ;                                                 // 생년월일( KTF의 경우만 사용 ) 
    String sex_code        = "" ;                                                 // 성별코드( KTF의 경우만 사용 )
    String local_code      = "" ;                                                 // 내외국인( KTF의 경우만 사용 )
    /* ============================================================================== */
    /* =   02. 인스턴스 생성 및 초기화                                              = */
    /* = -------------------------------------------------------------------------- = */
    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
    c_PayPlus.mf_init_set();
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   03. 처리 요청 정보 설정, 실행                                            = */
    /* = -------------------------------------------------------------------------- = */

    /* = -------------------------------------------------------------------------- = */
    /* =   03-1. 승인 요청                                                          = */
    /* = -------------------------------------------------------------------------- = */
    tran_cd = "00402200";

    int payx_data_set;
    int common_data_set;

    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );
    
    c_PayPlus.mf_set_us( common_data_set, "cust_ip"         ,  cust_ip         ); // 옵션

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    int cert_data_set;
    cert_data_set = c_PayPlus.mf_add_set( "cert" );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  tx_type         ); // 요청 구분 (앱인증 : 2600)
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn      ); // KCP 표준인증창 사용여부(Default:N)
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id         ); // 통신사코드
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no     ); // 본인확인 거래번호
    
    if ( comm_id.equals("SKT") )
    {
        c_PayPlus.mf_set_us( cert_data_set, "auth_tx_id"      ,  auth_tx_id      ); // Transaction ID(기관) - SKT 인증 시 필수
    }

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. 실행                                                                 = */
    /* = -------------------------------------------------------------------------- = */
   if ( tran_cd.length() > 0 )
    {
        c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
    }
    else
    {
        c_PayPlus.m_res_cd  = "9562";
        c_PayPlus.m_res_msg = "연동 오류|tran_cd값이 비어 있습니다.";
    }
    res_cd    = c_PayPlus.m_res_cd; //결과코드
    res_msg   = c_PayPlus.m_res_msg;//결과메세지
    /* = -------------------------------------------------------------------------- = */
    /* =   04. 실행 END                                                             = */
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. 승인 결과 처리                                                       = */
    /* = -------------------------------------------------------------------------- = */
    if ( res_cd.equals( "0000" ) )
    {
    /* = -------------------------------------------------------------------------- = */
    /* =   04-1. 인증 결과 처리                                                     = */
    /* = -------------------------------------------------------------------------- = */
        per_cert_no   = c_PayPlus.mf_get_res( "per_cert_no" ); // 인증 거래번호
        res_phone_no  = c_PayPlus.mf_get_res( "phone_no"    ); // 휴대폰번호
        res_comm_id   = c_PayPlus.mf_get_res( "comm_id"     ); // 통신사코드
        res_user_name = c_PayPlus.mf_get_res( "user_name"   ); // 고객명
        CI            = c_PayPlus.mf_get_res( "CI"          ); // CI
        DI            = c_PayPlus.mf_get_res( "DI"          ); // DI
        CI_URL        = c_PayPlus.mf_get_res( "CI_URL"      ); // CI_URL        
        DI_URL        = c_PayPlus.mf_get_res( "DI_URL"      ); // DI_URL
        res_auth_tx_id  = c_PayPlus.mf_get_res( "auth_tx_id"  ); // Transaction ID(기관)
        //van_tx_id     = c_PayPlus.mf_get_res( "van_tx_id"   ); // Transaction ID(KCP)

        birth_day       = c_PayPlus.mf_get_res( "birth_day"   ); // 생년월일
        sex_code        = c_PayPlus.mf_get_res( "sex_code"    ); // 성별코드
        local_code      = c_PayPlus.mf_get_res( "local_code"  ); // 내외국인
        
    }    // End of [res_cd = "0000"]
    /* = -------------------------------------------------------------------------- = */
    /* =   04-2. 승인 실패를 업체 자체적으로 DB 처리 작업하시는 부분입니다.         = */
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
    <script type="text/javascript">
            function goResult()
            {
                document.pay_info.submit()
            }
    </script>
</head>

<body onload="goResult()">
    <form name="pay_info" method="post" action="./new_result.jsp">
        <input type="hidden" name="res_cd"          value="<%= res_cd          %>" />  <!-- 결과 코드 -->
        <input type="hidden" name="res_msg"         value="<%= res_msg         %>" />  <!-- 결과 메세지 -->
        <input type="hidden" name="ordr_idxx"       value="<%= ordr_idxx       %>" />  <!-- 주문번호 -->
        <input type="hidden" name="per_cert_no"     value="<%= per_cert_no     %>" />  <!-- 인증번호 -->

        <input type="hidden" name="res_user_name"   value="<%= res_user_name   %>" />  <!-- 고객명 -->
        <input type="hidden" name="res_phone_no"    value="<%= res_phone_no    %>" />  <!-- 휴대폰번호 -->
        <input type="hidden" name="res_comm_id"     value="<%= res_comm_id     %>" />  <!-- 통신사코드-->

        <input type="hidden" name="CI"              value="<%= CI              %>" />  <!-- CI -->
        <input type="hidden" name="DI"              value="<%= DI              %>" />  <!-- DI -->
        <input type="hidden" name="CI_URL"          value="<%= CI_URL          %>" />  <!-- CI_URL -->
        <input type="hidden" name="DI_URL"          value="<%= DI_URL          %>" />  <!-- DI_URL -->
        <input type="hidden" name="auth_tx_id"      value="<%= auth_tx_id      %>" />  <!-- auth_tx_id -->

        <input type="hidden" name="birth_day"       value="<%= birth_day       %>" />  <!-- 생년월일 -->
        <input type="hidden" name="sex_code"        value="<%= sex_code        %>" />  <!-- 성별코드 -->
        <input type="hidden" name="local_code"      value="<%= local_code      %>" />  <!-- 내외국인-->

    </form>
</body>
</html>