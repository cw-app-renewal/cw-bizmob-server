<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="kr.co.kcp.*" %>
<%
    /* ============================================================================== */
    /* =   PAGE : 인증 요청 처리 PAGE                                               = */
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
    <%@ include file="../cfg/site_conf_inc.jsp"%>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "euc-kr" ) ;
    
    /* ============================================================================== */
    /* =   01. 지불 요청 정보 설정                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip     = request.getRemoteAddr();
    String ordr_idxx   = f_get_parm( request.getParameter( "ordr_idxx"   ) );  // 주문번호
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn  = f_get_parm( request.getParameter( "kcp_web_yn"  ) );  // KCP 표준인증창 사용여부    
    String per_cert_no = f_get_parm( request.getParameter( "per_cert_no" ) );  // 본인확인 거래번호 ( MVNO 조회(2400) 거래 수반한 경우 필수 )
    String phone_no    = f_get_parm( request.getParameter( "phone_no"    ) );  // 휴대폰번호
    String comm_id     = f_get_parm( request.getParameter( "comm_id"     ) );  // 이통사코드
    String birth_day   = f_get_parm( request.getParameter( "birth_day"   ) );  // 생년월일
    String user_name   = f_get_parm( request.getParameter( "user_name"   ) );  // 명의자명
    String local_code  = f_get_parm( request.getParameter( "local_code"  ) );  // 내외국인 구분
    String sex_code    = f_get_parm( request.getParameter( "sex_code"    ) );  // 성별구분
    /* = -------------------------------------------------------------------------- = */
    String cp_sms_msg      = f_get_parm( request.getParameter( "cp_sms_msg"      ) );  // CP지정 SMS 메시지
    String cp_callback     = f_get_parm( request.getParameter( "cp_callback"     ) );  // CP지정 SMS 회신번호
    /* = -------------------------------------------------------------------------- = */
    CT_CLI cc =  new CT_CLI();    
	//cc.setCharSetUtf8(); // UTF-8 인코딩
	/* 암호화 키 (외부에 노출되지 않도록 업체에서 관리)*/
    String ENC_KEY         = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0"; // 외부에 노출되지 않도록 업체에서 관리.
    String phone_no_hash   = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "phone_no"   ) ));
    String birth_day_hash  = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "birth_day"  ) ));
    String user_name_hash  = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "user_name"  ) ));
    String local_code_hash = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "local_code" ) ));
    String sex_code_hash   = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "sex_code"   ) ));
    System.out.println("phone_no_hash >>>" + phone_no_hash);
    System.out.println("birth_day_hash >>>" + birth_day_hash);
    System.out.println("user_name_hash >>>" + user_name_hash);
    System.out.println("phone_no_hash >>>" + local_code_hash);
    System.out.println("phone_no_hash >>>" + sex_code_hash);    
    System.out.println("=======================Byte Check==========================");
    System.out.println("ordr_idxx >>>" + ordr_idxx.getBytes("euc-kr"));    
    System.out.println("kcp_web_yn >>>" + kcp_web_yn.getBytes("euc-kr"));    
    System.out.println("per_cert_no >>>" + per_cert_no.getBytes("euc-kr"));    
    System.out.println("phone_no >>>" + phone_no.getBytes("euc-kr"));    
    System.out.println("comm_id >>>" + comm_id.getBytes("euc-kr"));    
    System.out.println("birth_day >>>" + birth_day.getBytes("euc-kr"));    
    System.out.println("user_name >>>" + user_name.getBytes("euc-kr"));    
    System.out.println("local_code >>>" + local_code.getBytes("euc-kr"));    
    System.out.println("sex_code >>>" + sex_code.getBytes("euc-kr"));    
    System.out.println("cp_sms_msg >>>" + cp_sms_msg.getBytes("euc-kr"));    
    System.out.println("cp_callback >>>" + cp_callback.getBytes("euc-kr"));    
    System.out.println("ENC_KEY >>>" + ENC_KEY.getBytes("euc-kr"));    
    System.out.println("phone_no_hash >>>" + phone_no_hash.getBytes("euc-kr"));    
    System.out.println("birth_day_hash >>>" + birth_day_hash.getBytes("euc-kr"));    
    System.out.println("user_name_hash >>>" + user_name_hash.getBytes("euc-kr"));    
    System.out.println("local_code_hash >>>" + local_code_hash.getBytes("euc-kr"));    
    System.out.println("sex_code_hash >>>" + sex_code_hash.getBytes("euc-kr"));     
    System.out.println("=======================Byte Check End==========================");
    /* = -------------------------------------------------------------------------- = */
    String tran_cd       = "" ;                                                 // 거래구분코드
    /* = -------------------------------------------------------------------------- = */
    String res_cd        = "" ;                                                 // 결과코드
    String res_msg       = "" ;                                                 // 결과메시지
    String iden_only_yn  = "" ;                                                 // 실명확인 ONLY 설정여부
    String CI            = "" ;                                                 // CI값
    String DI            = "" ;                                                 // DI값
    String CI_URL        = "" ;                                                 // URL인코딩된 CI값
    String DI_URL        = "" ;                                                 // URL인코딩된 DI값
    String safe_guard_yn = "" ;                                                 // 번호보호 서비스 가입여부
    String usim_otp_yn   = "" ;                                                 // 인증보호 서비스 가입여부
    String sms_snd_yn    = "" ;                                                 // SMS 발송여부
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

    //공통정보
    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );

    c_PayPlus.mf_set_us( common_data_set, "amount" , "0"       ); //고정
    c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip  );

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    // 주문 정보
    int ordr_data_set;

    ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );

    c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );

    // 인증 정보
    int cert_data_set;

    cert_data_set   = c_PayPlus.mf_add_set( "cert"      );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  "2100"             ); //고정
    c_PayPlus.mf_set_us( cert_data_set, "cert_type"       ,  "01"               ); //고정    
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn         ); //표준인증창 사용여부(허브형 Default:N)  
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no        ); //MVNO 사업자조회 완료 후 리턴받은 거래번호(알뜰폰사업자만 해당)
    c_PayPlus.mf_set_us( cert_data_set, "phone_no"        ,  phone_no           ); //휴대폰번호
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id            ); //이통사코드
    c_PayPlus.mf_set_us( cert_data_set, "birth_day"       ,  birth_day          ); //생년월일
    c_PayPlus.mf_set_us( cert_data_set, "user_name"       ,  user_name          ); //명의자명
    c_PayPlus.mf_set_us( cert_data_set, "local_code"      ,  local_code         ); //내외국인코드
    c_PayPlus.mf_set_us( cert_data_set, "sex_code"        ,  sex_code           ); //성별코드

    c_PayPlus.mf_set_us( cert_data_set, "cp_sms_msg"      ,  cp_sms_msg         ); //CP지정 메세지
    c_PayPlus.mf_set_us( cert_data_set, "cp_callback"     ,  cp_callback        ); //CP지정 콜백번호

    c_PayPlus.mf_set_us( cert_data_set, "phone_no_hash"   ,  phone_no_hash      ); //HASH 데이터
    c_PayPlus.mf_set_us( cert_data_set, "birth_day_hash"  ,  birth_day_hash     ); //HASH 데이터
    c_PayPlus.mf_set_us( cert_data_set, "user_name_hash"  ,  user_name_hash     ); //HASH 데이터
    c_PayPlus.mf_set_us( cert_data_set, "local_code_hash" ,  local_code_hash    ); //HASH 데이터
    c_PayPlus.mf_set_us( cert_data_set, "sex_code_hash"   ,  sex_code_hash      ); //HASH 데이터

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

    /* ============================================================================== */
    /* =   03-3. 실행                                                               = */
    /* ------------------------------------------------------------------------------ */
    if ( tran_cd.length() > 0 )
    {
//     	c_PayPlus.j_mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
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
        per_cert_no   = c_PayPlus.mf_get_res( "per_cert_no"   ); //본인확인 거래번호. 해당 거래번호를 OTP 발송/검증/재요청 시 사용
        iden_only_yn  = c_PayPlus.mf_get_res( "iden_only_yn"  ); //Only 실명확인 사용여부.(Default:N)
        safe_guard_yn = c_PayPlus.mf_get_res( "safe_guard_yn" ); //번호보호 서비스 가입여부
        usim_otp_yn   = c_PayPlus.mf_get_res( "usim_otp_yn"   ); //인증보호 서비스 가입여부
        sms_snd_yn    = c_PayPlus.mf_get_res( "sms_snd_yn"    ); //SMS 인증문자 발송 여부

        // 실명확인 ONLY 설정여부 Y일때 추가리턴정보
        if ( iden_only_yn.equals( "Y" ) )
        {
            CI            = c_PayPlus.mf_get_res( "CI"            );
            DI            = c_PayPlus.mf_get_res( "DI"            );
            CI_URL        = c_PayPlus.mf_get_res( "CI_URL"        );
            DI_URL        = c_PayPlus.mf_get_res( "DI_URL"        );
        }
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
                    window.location.href="./auth";
                }
            }
        </script>
    </head>

    <body onload="goResult();">
        <form name="pay_info" method="post" action="./pp_cli_otp">
            <input type="hidden" name="res_cd"        value="<%= res_cd        %>" />  <!-- 결과 코드 -->
            <input type="hidden" name="res_msg"       value="<%= res_msg       %>" />  <!-- 결과 메세지 -->
            <input type="hidden" name="ordr_idxx"     value="<%= ordr_idxx     %>" />  <!-- 주문번호 -->
            <input type="hidden" name="kcp_web_yn"    value="<%= kcp_web_yn    %>" />  <!-- KCP 표준인증창 사용여부 -->
            <input type="hidden" name="per_cert_no"   value="<%= per_cert_no   %>" />  <!-- 본인확인 거래번호 -->
            <input type="hidden" name="phone_no"      value="<%= phone_no      %>" />  <!-- 휴대폰번호 -->
            <input type="hidden" name="comm_id"       value="<%= comm_id       %>" />  <!-- 이동통신사 코드 -->
            <input type="hidden" name="iden_only_yn"  value="<%= iden_only_yn  %>" />  <!-- 실명확인 ONLY 설정여부 -->
            <input type="hidden" name="CI"            value="<%= CI            %>" />  <!-- CI값 -->
            <input type="hidden" name="DI"            value="<%= DI            %>" />  <!-- DI값 -->
            <input type="hidden" name="CI_URL"        value="<%= CI_URL        %>" />  <!-- URL인코딩된 CI값 -->
            <input type="hidden" name="DI_URL"        value="<%= DI_URL        %>" />  <!-- URL인코딩된 DI값 -->
            <input type="hidden" name="safe_guard_yn" value="<%= safe_guard_yn %>" />  <!-- 번호보호 서비스 가입여부 -->
            <input type="hidden" name="usim_otp_yn"   value="<%= usim_otp_yn   %>" />  <!-- 인증보호 서비스 가입여부 -->
            <input type="hidden" name="sms_snd_yn"    value="<%= sms_snd_yn    %>" />  <!-- SMS 발송여부 -->            
            <input type="hidden" name="cp_sms_msg"    value="<%= cp_sms_msg    %>" />  <!-- CP지정 SMS 메시지 -->            
            <input type="hidden" name="cp_callback"   value="<%= cp_callback   %>" />  <!-- CP지정 SMS 회신번호  -->
        </form>
    </body>
    </html>
