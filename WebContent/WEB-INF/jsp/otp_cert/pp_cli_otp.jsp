<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : OTP ������ ó�� PAGE                                              = */
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
    <%@ include file="../cfg/site_conf_inc.jsp" %>
<%
    /* ============================================================================== */

    request.setCharacterEncoding ( "utf-8" ) ;
    /* ============================================================================== */
    /* =   01. ó�� ��û ���� ����                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip         = request.getRemoteAddr();
    String ordr_idxx       = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // �ֹ���ȣ
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn      = f_get_parm( request.getParameter( "kcp_web_yn"      ) );  // KCP ǥ������â ��뿩��
    String per_cert_no     = f_get_parm( request.getParameter( "per_cert_no"     ) );  // ����Ȯ�� �ŷ���ȣ
    String comm_id         = f_get_parm( request.getParameter( "comm_id"         ) );  // �̵���Ż� �ڵ�
    String cp_sms_msg      = f_get_parm( request.getParameter( "cp_sms_msg"      ) );  // CP���� SMS �޽���
    String cp_callback     = f_get_parm( request.getParameter( "cp_callback"     ) );  // CP���� SMS ȸ�Ź�ȣ
    /* = -------------------------------------------------------------------------- = */
    String tran_cd         = "" ;                                                 // �ŷ������ڵ�
    /* = -------------------------------------------------------------------------- = */
    String res_cd          = "" ;                                                 // ����ڵ�
    String res_msg         = "" ;                                                 // ����޽���
    String safe_guard_yn   = "" ;                                                 // ��ȣ��ȣ ���� ���Կ���
    String usim_otp_yn     = "" ;                                                 // ������ȣ ���� ���Կ���
    String sms_snd_yn      = "" ;                                                 // SMS �߼ۿ���
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

    c_PayPlus.mf_set_us( common_data_set, "amount" , "0"          ); //����
    c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip     ); 

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );
    
    //�ֹ� ����
    int ordr_data_set;
    
    ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );
    
    c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );

    //��������
    int cert_data_set;
    
    cert_data_set = c_PayPlus.mf_add_set( "cert" );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  "2200"          ); //����
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn      ); 
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no     ); 
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id         ); 
    c_PayPlus.mf_set_us( cert_data_set, "cp_sms_msg"      ,  cp_sms_msg      );
    c_PayPlus.mf_set_us( cert_data_set, "cp_callback"     ,  cp_callback     );

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
        c_PayPlus.m_res_msg = "���� ����";
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
        safe_guard_yn = c_PayPlus.mf_get_res( "safe_guard_yn" );
        usim_otp_yn   = c_PayPlus.mf_get_res( "usim_otp_yn"   );
        sms_snd_yn    = c_PayPlus.mf_get_res( "sms_snd_yn"    );
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

<body onload="goResult()">
    <form name="pay_info" method="post" action="./otp">
        <input type="hidden" name="res_cd"          value="<%= res_cd          %>"/>  <!-- ��� �ڵ� -->
        <input type="hidden" name="res_msg"         value="<%= res_msg         %>"/>  <!-- ��� �޼��� -->
        <input type="hidden" name="ordr_idxx"       value="<%= ordr_idxx       %>"/>  <!-- �ֹ���ȣ -->
        <input type="hidden" name="kcp_web_yn"      value="<%= kcp_web_yn      %>"/>  <!-- KCP ǥ������â ��뿩�� -->
        <input type="hidden" name="per_cert_no"     value="<%= per_cert_no     %>"/>  <!-- ����Ȯ�� �ŷ���ȣ -->
        <input type="hidden" name="comm_id"         value="<%= comm_id         %>"/>  <!-- ��Ż� -->        
        <input type="hidden" name="cp_sms_msg"      value="<%= cp_sms_msg      %>"/>  <!-- CP���� SMS �޽��� -->        
        <input type="hidden" name="cp_callback"     value="<%= cp_callback     %>"/>  <!-- CP���� SMS ȸ�Ź�ȣ  -->
    </form>
</body>
</html>