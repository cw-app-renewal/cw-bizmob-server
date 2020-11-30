<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : OTP 재전송 처리 PAGE                                              = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2017.06  NHN KCP Inc.   All Rights Reserved.              = */
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
    /* = 라이브러리 및 사이트 정보 include                                          = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*"%>
    <%@ page import="java.net.URLDecoder"%>
    <%@ include file="../cfg/site_conf_inc.jsp" %>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "utf-8" ) ;
    /* ============================================================================== */
    /* =   01. 처리 요청 정보 설정                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip         = request.getRemoteAddr();
    String ordr_idxx       = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // 주문번호
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn      = f_get_parm( request.getParameter( "kcp_web_yn"      ) );  // KCP 표준인증창 사용여부
    String per_cert_no     = f_get_parm( request.getParameter( "per_cert_no"     ) );  // 본인확인 거래번호
    String comm_id         = f_get_parm( request.getParameter( "comm_id"         ) );  // 이동통신사 코드
    String cp_sms_msg      = f_get_parm( request.getParameter( "cp_sms_msg"      ) );  // CP지정 SMS 메시지
    String cp_callback     = f_get_parm( request.getParameter( "cp_callback"     ) );  // CP지정 SMS 회신번호
    /* = -------------------------------------------------------------------------- = */
    String tran_cd         = "" ;                                                 // 거래구분코드
    /* = -------------------------------------------------------------------------- = */
    String res_cd          = "" ;                                                 // 결과코드
    String res_msg         = "" ;                                                 // 결과메시지
    String safe_guard_yn   = "" ;                                                 // 번호보호 서비스 가입여부
    String usim_otp_yn     = "" ;                                                 // 인증보호 서비스 가입여부
    String sms_snd_yn      = "" ;                                                 // SMS 발송여부
    /* ============================================================================== */

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

    //공통정보
    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );

    c_PayPlus.mf_set_us( common_data_set, "amount" , "0"          ); //고정
    c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip     ); 

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );
    
    //주문 정보
    int ordr_data_set;
    
    ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );
    
    c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );

    //인증정보
    int cert_data_set;
    
    cert_data_set = c_PayPlus.mf_add_set( "cert" );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  "2200"          ); //고정
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn      ); 
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no     ); 
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id         ); 
    c_PayPlus.mf_set_us( cert_data_set, "cp_sms_msg"      ,  cp_sms_msg      );
    c_PayPlus.mf_set_us( cert_data_set, "cp_callback"     ,  cp_callback     );

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
        c_PayPlus.m_res_msg = "연동 오류";
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
        safe_guard_yn = c_PayPlus.mf_get_res( "safe_guard_yn" );
        usim_otp_yn   = c_PayPlus.mf_get_res( "usim_otp_yn"   );
        sms_snd_yn    = c_PayPlus.mf_get_res( "sms_snd_yn"    );
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
                if (document.pay_info.res_cd.value == "0000")
                {
                    document.pay_info.submit();
                }
                else
                {
                    alert("에러 코드 : " + document.pay_info.res_cd.value + ", 에러 메세지 : " + document.pay_info.res_msg.value + "");
                    window.location.href="./auth";
                }
            }
    </script>
</head>

<body onload="goResult()">
    <form name="pay_info" method="post" action="./otp">
        <input type="hidden" name="res_cd"          value="<%= res_cd          %>"/>  <!-- 결과 코드 -->
        <input type="hidden" name="res_msg"         value="<%= res_msg         %>"/>  <!-- 결과 메세지 -->
        <input type="hidden" name="ordr_idxx"       value="<%= ordr_idxx       %>"/>  <!-- 주문번호 -->
        <input type="hidden" name="kcp_web_yn"      value="<%= kcp_web_yn      %>"/>  <!-- KCP 표준인증창 사용여부 -->
        <input type="hidden" name="per_cert_no"     value="<%= per_cert_no     %>"/>  <!-- 본인확인 거래번호 -->
        <input type="hidden" name="comm_id"         value="<%= comm_id         %>"/>  <!-- 통신사 -->        
        <input type="hidden" name="cp_sms_msg"      value="<%= cp_sms_msg      %>"/>  <!-- CP지정 SMS 메시지 -->        
        <input type="hidden" name="cp_callback"     value="<%= cp_callback     %>"/>  <!-- CP지정 SMS 회신번호  -->
    </form>
</body>
</html>