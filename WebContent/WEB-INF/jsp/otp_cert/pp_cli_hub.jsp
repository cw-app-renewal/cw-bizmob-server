<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="kr.co.kcp.*" %>
<%
    /* ============================================================================== */
    /* =   PAGE : ���� ��û ó�� PAGE                                               = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2017.06  NHN KCP Inc.   All Rights Reserverd.             = */
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

    request.setCharacterEncoding ( "utf-8" ) ;
    /* ============================================================================== */
    /* =   01. ���� ��û ���� ����                                                  = */
    /* = -------------------------------------------------------------------------- = */
    String cust_ip         = request.getRemoteAddr();
    String ordr_idxx       = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // �ֹ���ȣ
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn      = f_get_parm( request.getParameter( "kcp_web_yn"     ) );  // KCP ǥ������â ��뿩��
    String per_cert_no     = f_get_parm( request.getParameter( "per_cert_no"    ) );  // ����Ȯ�� �ŷ���ȣ
    String comm_id         = f_get_parm( request.getParameter( "comm_id"        ) );  // �̵���Ż� �ڵ�
    String otp_no          = f_get_parm( request.getParameter( "otp_no"         ) );  // ����Ȯ�� ������ȣ
    String ad1_agree_yn    = f_get_parm( request.getParameter( "ad1_agree_yn"   ) );  // ����#1 ���� ���ǿ���
    /* = -------------------------------------------------------------------------- = */
    String tran_cd     = "" ;                                                  // �ŷ������ڵ�
    /* = -------------------------------------------------------------------------- = */
    String res_cd      = "" ;                                                  // ����ڵ�
    String res_msg     = "" ;                                                  // ����޽���
    /* = -------------------------------------------------------------------------- = */
    String CI          = "" ;                                                  // CI
    String DI          = "" ;                                                  // DI
    String CI_URL      = "" ;                                                  // CI_URL
    String DI_URL      = "" ;                                                  // DI_URL
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

    payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
    common_data_set = c_PayPlus.mf_add_set( "common"    );

    c_PayPlus.mf_set_us( common_data_set, "amount" , "0"          ); //����
    c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip     ); //�ɼ�

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    // ���� ����
    int cert_data_set;

    cert_data_set   = c_PayPlus.mf_add_set( "cert"      );

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"      ,  "2300"       ); //����
    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"   ,  kcp_web_yn   );
    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"  ,  per_cert_no  );
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"      ,  comm_id      );
    c_PayPlus.mf_set_us( cert_data_set, "otp_no"       ,  otp_no       ); //SMS ������ȣ
    c_PayPlus.mf_set_us( cert_data_set, "ad1_agree_yn" ,  ad1_agree_yn );

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

    /* ============================================================================== */
    /* =   03-3. ����                                                               = */
    /* ------------------------------------------------------------------------------ */
    if ( tran_cd.length() > 0 )
    {
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
        per_cert_no = c_PayPlus.mf_get_res( "per_cert_no" );
        CI          = c_PayPlus.mf_get_res( "CI"          );
        DI          = c_PayPlus.mf_get_res( "DI"          );
        CI_URL      = URLDecoder.decode(c_PayPlus.mf_get_res( "CI_URL" ));
        DI_URL      = URLDecoder.decode(c_PayPlus.mf_get_res( "DI_URL" ));
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
                    window.location.href="./auth.jsp";
                }
            }
        </script>
    </head>

    <body onload="goResult();">
        <form name="pay_info" method="post" action="./result">
            <input type="hidden" name="res_cd"      value="<%= res_cd      %>" />  <!-- ��� �ڵ� -->
            <input type="hidden" name="res_msg"     value="<%= res_msg     %>" />  <!-- ��� �޼��� -->
            <input type="hidden" name="per_cert_no" value="<%= per_cert_no %>" />  <!-- ����Ȯ�� �ŷ���ȣ -->
            <input type="hidden" name="CI"          value="<%= CI          %>" />  <!-- CI -->
            <input type="hidden" name="DI"          value="<%= DI          %>" />  <!-- DI -->
            <input type="hidden" name="CI_URL"      value="<%= CI_URL      %>" />  <!-- CI_URL -->
            <input type="hidden" name="DI_URL"      value="<%= DI_URL      %>" />  <!-- DI_URL -->
        </form>
    </body>
    </html>
