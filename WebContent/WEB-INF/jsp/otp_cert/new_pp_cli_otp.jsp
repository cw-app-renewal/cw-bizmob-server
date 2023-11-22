<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : OTP ������ ó�� PAGE                                                  = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2017.06  NHN KCP Inc.   All Rights Reserverd.             = */
    /* ============================================================================== */
%>
<%!
    /* ============================================================================== */
    /* =   null ���� ó���ϴ� �޼ҵ�                                                                                                                           = */
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
    <%@ include file="../cfg/site_conf_inc.jsp" %>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "euc-kr" ) ;
    /* ============================================================================== */
    /* =   01. ó�� ��û ���� ����                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip         = request.getRemoteAddr();
    String ordr_idxx       = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // �ֹ���ȣ
    /* = -------------------------------------------------------------------------- = */
    String tx_type         = f_get_parm( request.getParameter( "tx_type"         ) );  // ��û Ÿ��
    String kcp_web_yn      = f_get_parm( request.getParameter( "kcp_web_yn"      ) );  // KCP ǥ������â ��뿩��
    String per_cert_no     = f_get_parm( request.getParameter( "per_cert_no"     ) );  // ����Ȯ�� �ŷ���ȣ
    String comm_id         = f_get_parm( request.getParameter( "comm_id"         ) );  // ��Ż��ڵ�
    String auth_tx_id      = f_get_parm( request.getParameter( "auth_tx_id"      ) ); // Transaction ID(���)
    /* = -------------------------------------------------------------------------- = */
    String tran_cd         = "" ;                                                 // �ŷ������ڵ�
    /* = -------------------------------------------------------------------------- = */
    String res_cd          = "" ;                                                 // ����ڵ�
    String res_msg         = "" ;                                                 // ����޽���  
    String CI              = "" ;                                                 // CI��
    String DI              = "" ;                                                 // DI��
    String CI_URL          = "" ;                                                 // URL���ڵ��� CI��
    String DI_URL          = "" ;                                                 // URL���ڵ��� DI��    
    String res_phone_no    = "" ;                                                 // �޴�����ȣ
    String res_user_name   = "" ;                                                 // ����
    String res_comm_id     = "" ;                                                 // ��Ż��ڵ�
    String res_auth_tx_id  = "" ;                                                 // Transaction ID(���)
    //String van_tx_id       = "" ;                                               // Transaction ID(KCP)                                          
    /* ============================================================================== */ 
    String birth_day       = "" ;                                                 // �������( KTF�� ��츸 ��� ) 
    String sex_code        = "" ;                                                 // �����ڵ�( KTF�� ��츸 ��� )
    String local_code      = "" ;                                                 // ���ܱ���( KTF�� ��츸 ��� )
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

    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );
    
    c_PayPlus.mf_set_us( common_data_set, "cust_ip"         ,  cust_ip         ); // �ɼ�

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    int cert_data_set;
    cert_data_set = c_PayPlus.mf_add_set( "cert" );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  tx_type         ); // ��û ���� (������ : 2600)
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn      ); // KCP ǥ������â ��뿩��(Default:N)
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id         ); // ��Ż��ڵ�
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no     ); // ����Ȯ�� �ŷ���ȣ
    
    if ( comm_id.equals("SKT") )
    {
        c_PayPlus.mf_set_us( cert_data_set, "auth_tx_id"      ,  auth_tx_id      ); // Transaction ID(���) - SKT ���� �� �ʼ�
    }

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. ����                                                                 = */
    /* = -------------------------------------------------------------------------- = */
   if ( tran_cd.length() > 0 )
    {
        c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
    }
    else
    {
        c_PayPlus.m_res_cd  = "9562";
        c_PayPlus.m_res_msg = "���� ����|tran_cd���� ��� �ֽ��ϴ�.";
    }
    res_cd    = c_PayPlus.m_res_cd; //����ڵ�
    res_msg   = c_PayPlus.m_res_msg;//����޼���
    /* = -------------------------------------------------------------------------- = */
    /* =   04. ���� END                                                             = */
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   04. ���� ��� ó��                                                       = */
    /* = -------------------------------------------------------------------------- = */
    if ( res_cd.equals( "0000" ) )
    {
    /* = -------------------------------------------------------------------------- = */
    /* =   04-1. ���� ��� ó��                                                     = */
    /* = -------------------------------------------------------------------------- = */
        per_cert_no   = c_PayPlus.mf_get_res( "per_cert_no" ); // ���� �ŷ���ȣ
        res_phone_no  = c_PayPlus.mf_get_res( "phone_no"    ); // �޴�����ȣ
        res_comm_id   = c_PayPlus.mf_get_res( "comm_id"     ); // ��Ż��ڵ�
        res_user_name = c_PayPlus.mf_get_res( "user_name"   ); // ����
        CI            = c_PayPlus.mf_get_res( "CI"          ); // CI
        DI            = c_PayPlus.mf_get_res( "DI"          ); // DI
        CI_URL        = c_PayPlus.mf_get_res( "CI_URL"      ); // CI_URL        
        DI_URL        = c_PayPlus.mf_get_res( "DI_URL"      ); // DI_URL
        res_auth_tx_id  = c_PayPlus.mf_get_res( "auth_tx_id"  ); // Transaction ID(���)
        //van_tx_id     = c_PayPlus.mf_get_res( "van_tx_id"   ); // Transaction ID(KCP)

        birth_day       = c_PayPlus.mf_get_res( "birth_day"   ); // �������
        sex_code        = c_PayPlus.mf_get_res( "sex_code"    ); // �����ڵ�
        local_code      = c_PayPlus.mf_get_res( "local_code"  ); // ���ܱ���
        
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
        <input type="hidden" name="res_cd"          value="<%= res_cd          %>" />  <!-- ��� �ڵ� -->
        <input type="hidden" name="res_msg"         value="<%= res_msg         %>" />  <!-- ��� �޼��� -->
        <input type="hidden" name="ordr_idxx"       value="<%= ordr_idxx       %>" />  <!-- �ֹ���ȣ -->
        <input type="hidden" name="per_cert_no"     value="<%= per_cert_no     %>" />  <!-- ������ȣ -->

        <input type="hidden" name="res_user_name"   value="<%= res_user_name   %>" />  <!-- ���� -->
        <input type="hidden" name="res_phone_no"    value="<%= res_phone_no    %>" />  <!-- �޴�����ȣ -->
        <input type="hidden" name="res_comm_id"     value="<%= res_comm_id     %>" />  <!-- ��Ż��ڵ�-->

        <input type="hidden" name="CI"              value="<%= CI              %>" />  <!-- CI -->
        <input type="hidden" name="DI"              value="<%= DI              %>" />  <!-- DI -->
        <input type="hidden" name="CI_URL"          value="<%= CI_URL          %>" />  <!-- CI_URL -->
        <input type="hidden" name="DI_URL"          value="<%= DI_URL          %>" />  <!-- DI_URL -->
        <input type="hidden" name="auth_tx_id"      value="<%= auth_tx_id      %>" />  <!-- auth_tx_id -->

        <input type="hidden" name="birth_day"       value="<%= birth_day       %>" />  <!-- ������� -->
        <input type="hidden" name="sex_code"        value="<%= sex_code        %>" />  <!-- �����ڵ� -->
        <input type="hidden" name="local_code"      value="<%= local_code      %>" />  <!-- ���ܱ���-->

    </form>
</body>
</html>