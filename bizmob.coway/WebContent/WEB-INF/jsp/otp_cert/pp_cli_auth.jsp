<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="kr.co.kcp.*" %>
<%
    /* ============================================================================== */
    /* =   PAGE : ���� ��û ó�� PAGE                                               = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2017.06  NHN KCP Inc.   All Rights Reserved.              = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null ���� ó���ϴ� �޼ҵ�                                                = */
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
    /* = ���̺귯�� �� ����Ʈ ���� include                                          = */
    /* = -------------------------------------------------------------------------- = */
%>
    <%@ page import="com.kcp.*"%>
    <%@ page import="java.net.URLDecoder"%>
    <%@ include file="../cfg/site_conf_inc.jsp"%>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "euc-kr" ) ;
    
    /* ============================================================================== */
    /* =   01. ���� ��û ���� ����                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip     = request.getRemoteAddr();
    String ordr_idxx   = f_get_parm( request.getParameter( "ordr_idxx"   ) );  // �ֹ���ȣ
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn  = f_get_parm( request.getParameter( "kcp_web_yn"  ) );  // KCP ǥ������â ��뿩��    
    String per_cert_no = f_get_parm( request.getParameter( "per_cert_no" ) );  // ����Ȯ�� �ŷ���ȣ ( MVNO ��ȸ(2400) �ŷ� ������ ��� �ʼ� )
    String phone_no    = f_get_parm( request.getParameter( "phone_no"    ) );  // �޴�����ȣ
    String comm_id     = f_get_parm( request.getParameter( "comm_id"     ) );  // ������ڵ�
    String birth_day   = f_get_parm( request.getParameter( "birth_day"   ) );  // �������
    String user_name   = f_get_parm( request.getParameter( "user_name"   ) );  // �����ڸ�
    String local_code  = f_get_parm( request.getParameter( "local_code"  ) );  // ���ܱ��� ����
    String sex_code    = f_get_parm( request.getParameter( "sex_code"    ) );  // ��������
    /* = -------------------------------------------------------------------------- = */
    String cp_sms_msg      = f_get_parm( request.getParameter( "cp_sms_msg"      ) );  // CP���� SMS �޽���
    String cp_callback     = f_get_parm( request.getParameter( "cp_callback"     ) );  // CP���� SMS ȸ�Ź�ȣ
    /* = -------------------------------------------------------------------------- = */
    CT_CLI cc =  new CT_CLI();    
	//cc.setCharSetUtf8(); // UTF-8 ���ڵ�
	/* ��ȣȭ Ű (�ܺο� ������� �ʵ��� ��ü���� ����)*/
    String ENC_KEY         = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0"; // �ܺο� ������� �ʵ��� ��ü���� ����.
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
    String tran_cd       = "" ;                                                 // �ŷ������ڵ�
    /* = -------------------------------------------------------------------------- = */
    String res_cd        = "" ;                                                 // ����ڵ�
    String res_msg       = "" ;                                                 // ����޽���
    String iden_only_yn  = "" ;                                                 // �Ǹ�Ȯ�� ONLY ��������
    String CI            = "" ;                                                 // CI��
    String DI            = "" ;                                                 // DI��
    String CI_URL        = "" ;                                                 // URL���ڵ��� CI��
    String DI_URL        = "" ;                                                 // URL���ڵ��� DI��
    String safe_guard_yn = "" ;                                                 // ��ȣ��ȣ ���� ���Կ���
    String usim_otp_yn   = "" ;                                                 // ������ȣ ���� ���Կ���
    String sms_snd_yn    = "" ;                                                 // SMS �߼ۿ���
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   02. �ν��Ͻ� ���� �� �ʱ�ȭ                                              = */
    /* = -------------------------------------------------------------------------- = */
    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();
    
    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
    c_PayPlus.mf_init_set();
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   03. ó�� ��û ���� ����, ����                                            = */
    /* = -------------------------------------------------------------------------- = */

    /* = -------------------------------------------------------------------------- = */
    /* =   03-1. ���� ��û                                                          = */
    /* = -------------------------------------------------------------------------- = */
    tran_cd = "00402200";

    int payx_data_set;
    int common_data_set;

    //��������
    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );

    c_PayPlus.mf_set_us( common_data_set, "amount" , "0"       ); //����
    c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip  );

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    // �ֹ� ����
    int ordr_data_set;

    ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );

    c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );

    // ���� ����
    int cert_data_set;

    cert_data_set   = c_PayPlus.mf_add_set( "cert"      );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  "2100"             ); //����
    c_PayPlus.mf_set_us( cert_data_set, "cert_type"       ,  "01"               ); //����    
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn         ); //ǥ������â ��뿩��(����� Default:N)  
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no        ); //MVNO �������ȸ �Ϸ� �� ���Ϲ��� �ŷ���ȣ(�˶�������ڸ� �ش�)
    c_PayPlus.mf_set_us( cert_data_set, "phone_no"        ,  phone_no           ); //�޴�����ȣ
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id            ); //������ڵ�
    c_PayPlus.mf_set_us( cert_data_set, "birth_day"       ,  birth_day          ); //�������
    c_PayPlus.mf_set_us( cert_data_set, "user_name"       ,  user_name          ); //�����ڸ�
    c_PayPlus.mf_set_us( cert_data_set, "local_code"      ,  local_code         ); //���ܱ����ڵ�
    c_PayPlus.mf_set_us( cert_data_set, "sex_code"        ,  sex_code           ); //�����ڵ�

    c_PayPlus.mf_set_us( cert_data_set, "cp_sms_msg"      ,  cp_sms_msg         ); //CP���� �޼���
    c_PayPlus.mf_set_us( cert_data_set, "cp_callback"     ,  cp_callback        ); //CP���� �ݹ��ȣ

    c_PayPlus.mf_set_us( cert_data_set, "phone_no_hash"   ,  phone_no_hash      ); //HASH ������
    c_PayPlus.mf_set_us( cert_data_set, "birth_day_hash"  ,  birth_day_hash     ); //HASH ������
    c_PayPlus.mf_set_us( cert_data_set, "user_name_hash"  ,  user_name_hash     ); //HASH ������
    c_PayPlus.mf_set_us( cert_data_set, "local_code_hash" ,  local_code_hash    ); //HASH ������
    c_PayPlus.mf_set_us( cert_data_set, "sex_code_hash"   ,  sex_code_hash      ); //HASH ������

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

    /* ============================================================================== */
    /* =   03-3. ����                                                               = */
    /* ------------------------------------------------------------------------------ */
    if ( tran_cd.length() > 0 )
    {
//     	c_PayPlus.j_mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
        c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
    }
    else
    {
        c_PayPlus.m_res_cd  = "9562";
        c_PayPlus.m_res_msg = "���� ����";
    }
    res_cd  = c_PayPlus.m_res_cd;                      // ��� �ڵ�
    res_msg = c_PayPlus.m_res_msg;                     // ��� �޽���
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. ���� ��� ó��                                                       = */
    /* = -------------------------------------------------------------------------- = */
    if ( res_cd.equals( "0000" ) )
    {
    /* = -------------------------------------------------------------------------- = */
    /* =   04-1. ���� ��� ó��                                                     = */
    /* = -------------------------------------------------------------------------- = */
        per_cert_no   = c_PayPlus.mf_get_res( "per_cert_no"   ); //����Ȯ�� �ŷ���ȣ. �ش� �ŷ���ȣ�� OTP �߼�/����/���û �� ���
        iden_only_yn  = c_PayPlus.mf_get_res( "iden_only_yn"  ); //Only �Ǹ�Ȯ�� ��뿩��.(Default:N)
        safe_guard_yn = c_PayPlus.mf_get_res( "safe_guard_yn" ); //��ȣ��ȣ ���� ���Կ���
        usim_otp_yn   = c_PayPlus.mf_get_res( "usim_otp_yn"   ); //������ȣ ���� ���Կ���
        sms_snd_yn    = c_PayPlus.mf_get_res( "sms_snd_yn"    ); //SMS �������� �߼� ����

        // �Ǹ�Ȯ�� ONLY �������� Y�϶� �߰���������
        if ( iden_only_yn.equals( "Y" ) )
        {
            CI            = c_PayPlus.mf_get_res( "CI"            );
            DI            = c_PayPlus.mf_get_res( "DI"            );
            CI_URL        = c_PayPlus.mf_get_res( "CI_URL"        );
            DI_URL        = c_PayPlus.mf_get_res( "DI_URL"        );
        }
    }    // End of [res_cd = "0000"]
    /* = -------------------------------------------------------------------------- = */
    /* =   04-2. ���� ���и� ��ü ��ü������ DB ó�� �۾��Ͻô� �κ��Դϴ�.         = */
    /* = -------------------------------------------------------------------------- = */
    else
    {

    }
    /* ============================================================================== */

    /* ============================================================================== */
    /* =   05. �� ���� �� ��������� ȣ��                                           = */
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
                    alert("���� �ڵ� : " + document.pay_info.res_cd.value + ", ���� �޼��� : " + document.pay_info.res_msg.value + "");
                    window.location.href="./auth";
                }
            }
        </script>
    </head>

    <body onload="goResult();">
        <form name="pay_info" method="post" action="./pp_cli_otp">
            <input type="hidden" name="res_cd"        value="<%= res_cd        %>" />  <!-- ��� �ڵ� -->
            <input type="hidden" name="res_msg"       value="<%= res_msg       %>" />  <!-- ��� �޼��� -->
            <input type="hidden" name="ordr_idxx"     value="<%= ordr_idxx     %>" />  <!-- �ֹ���ȣ -->
            <input type="hidden" name="kcp_web_yn"    value="<%= kcp_web_yn    %>" />  <!-- KCP ǥ������â ��뿩�� -->
            <input type="hidden" name="per_cert_no"   value="<%= per_cert_no   %>" />  <!-- ����Ȯ�� �ŷ���ȣ -->
            <input type="hidden" name="phone_no"      value="<%= phone_no      %>" />  <!-- �޴�����ȣ -->
            <input type="hidden" name="comm_id"       value="<%= comm_id       %>" />  <!-- �̵���Ż� �ڵ� -->
            <input type="hidden" name="iden_only_yn"  value="<%= iden_only_yn  %>" />  <!-- �Ǹ�Ȯ�� ONLY �������� -->
            <input type="hidden" name="CI"            value="<%= CI            %>" />  <!-- CI�� -->
            <input type="hidden" name="DI"            value="<%= DI            %>" />  <!-- DI�� -->
            <input type="hidden" name="CI_URL"        value="<%= CI_URL        %>" />  <!-- URL���ڵ��� CI�� -->
            <input type="hidden" name="DI_URL"        value="<%= DI_URL        %>" />  <!-- URL���ڵ��� DI�� -->
            <input type="hidden" name="safe_guard_yn" value="<%= safe_guard_yn %>" />  <!-- ��ȣ��ȣ ���� ���Կ��� -->
            <input type="hidden" name="usim_otp_yn"   value="<%= usim_otp_yn   %>" />  <!-- ������ȣ ���� ���Կ��� -->
            <input type="hidden" name="sms_snd_yn"    value="<%= sms_snd_yn    %>" />  <!-- SMS �߼ۿ��� -->            
            <input type="hidden" name="cp_sms_msg"    value="<%= cp_sms_msg    %>" />  <!-- CP���� SMS �޽��� -->            
            <input type="hidden" name="cp_callback"   value="<%= cp_callback   %>" />  <!-- CP���� SMS ȸ�Ź�ȣ  -->
        </form>
    </body>
    </html>
