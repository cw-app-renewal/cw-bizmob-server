<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="kr.co.kcp.*" %>
<%
    /* ============================================================================== */
    /* =   PAGE : 인증 요청 처리 PAGE                                               = */
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
    /* = 라이브러리 및 사이트 정보 include                                          = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*"%>
    <%@ page import="java.net.URLDecoder"%>
    <%@ include file="../cfg/site_conf_inc.jsp"%>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "utf-8" ) ;
    /* ============================================================================== */
    /* =   01. 지불 요청 정보 설정                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip         = request.getRemoteAddr();
    String ordr_idxx       = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // 주문번호
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn      = f_get_parm( request.getParameter( "kcp_web_yn"     ) );  // KCP 표준인증창 사용여부
    String per_cert_no     = f_get_parm( request.getParameter( "per_cert_no"    ) );  // 본인확인 거래번호
    String comm_id         = f_get_parm( request.getParameter( "comm_id"        ) );  // 이동통신사 코드
    String otp_no          = f_get_parm( request.getParameter( "otp_no"         ) );  // 본인확인 인증번호
    String ad1_agree_yn    = f_get_parm( request.getParameter( "ad1_agree_yn"   ) );  // 광고#1 선택 동의여부
    /* = -------------------------------------------------------------------------- = */
    String tran_cd     = "" ;                                                  // 거래구분코드
    /* = -------------------------------------------------------------------------- = */
    String res_cd      = "" ;                                                  // 결과코드
    String res_msg     = "" ;                                                  // 결과메시지
    /* = -------------------------------------------------------------------------- = */
    String CI          = "" ;                                                  // CI
    String DI          = "" ;                                                  // DI
    String CI_URL      = "" ;                                                  // CI_URL
    String DI_URL      = "" ;                                                  // DI_URL
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
    /* =   03-1. 인증 요청                                                          = */
    /* = -------------------------------------------------------------------------- = */
    tran_cd = "00402200";

    int payx_data_set;
    int common_data_set;

    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );

    c_PayPlus.mf_set_us( common_data_set, "amount" , "0"          ); //고정
    c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip     ); //옵션

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    // 인증 정보
    int cert_data_set;

    cert_data_set   = c_PayPlus.mf_add_set( "cert"      );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"      ,  "2300"       ); //고정
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"   ,  kcp_web_yn   );
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"  ,  per_cert_no  );
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"      ,  comm_id      );
    c_PayPlus.mf_set_us( cert_data_set, "otp_no"       ,  otp_no       ); //SMS 인증번호
    c_PayPlus.mf_set_us( cert_data_set, "ad1_agree_yn" ,  ad1_agree_yn );

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

    /* ============================================================================== */
    /* =   03-3. 실행                                                               = */
    /* ------------------------------------------------------------------------------ */
    if ( tran_cd.length() > 0 )
    {
        c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
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
        per_cert_no = c_PayPlus.mf_get_res( "per_cert_no" );
        CI          = c_PayPlus.mf_get_res( "CI"          );
        DI          = c_PayPlus.mf_get_res( "DI"          );
        CI_URL      = URLDecoder.decode(c_PayPlus.mf_get_res( "CI_URL" ));
        DI_URL      = URLDecoder.decode(c_PayPlus.mf_get_res( "DI_URL" ));
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

    <html>
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
                    window.location.href="./auth.jsp";
                }
            }
        </script>
    </head>

    <body onload="goResult();">
        <form name="pay_info" method="post" action="./result">
            <input type="hidden" name="res_cd"      value="<%= res_cd      %>" />  <!-- 결과 코드 -->
            <input type="hidden" name="res_msg"     value="<%= res_msg     %>" />  <!-- 결과 메세지 -->
            <input type="hidden" name="per_cert_no" value="<%= per_cert_no %>" />  <!-- 본인확인 거래번호 -->
            <input type="hidden" name="CI"          value="<%= CI          %>" />  <!-- CI -->
            <input type="hidden" name="DI"          value="<%= DI          %>" />  <!-- DI -->
            <input type="hidden" name="CI_URL"      value="<%= CI_URL      %>" />  <!-- CI_URL -->
            <input type="hidden" name="DI_URL"      value="<%= DI_URL      %>" />  <!-- DI_URL -->
        </form>
    </body>
    </html>
