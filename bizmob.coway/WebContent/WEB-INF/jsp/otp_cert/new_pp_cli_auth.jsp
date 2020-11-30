<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="kr.co.kcp.*" %>
<%
    /* ============================================================================== */
    /* =   PAGE : ���� ��û ó�� PAGE                                                     = */
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
    /* = ���̺귯�� �� ����Ʈ ���� include                                                    = */
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
	String tx_type     = f_get_parm( request.getParameter( "tx_type"     ) );  // ��û ����
    String ordr_idxx   = f_get_parm( request.getParameter( "ordr_idxx"   ) );  // �ֹ���ȣ
    /* = -------------------------------------------------------------------------- = */    
    String per_cert_no = f_get_parm( request.getParameter( "per_cert_no" ) );  // ����Ȯ�� �ŷ���ȣ ( MVNO ��ȸ(2700) �ŷ� ������ ��� �ʼ� )
    String phone_no    = f_get_parm( request.getParameter( "phone_no"    ) );  // ���� ��û �޴�����ȣ
    String comm_id     = f_get_parm( request.getParameter( "comm_id"     ) );  // ���� ��û ��Ż��ڵ�
	String user_name   = f_get_parm( request.getParameter( "user_name"   ) );  // ���� ��û ����
	String kcp_web_yn  = f_get_parm( request.getParameter( "kcp_web_yn"  ) );  // KCP ǥ������â ��뿩��
    /* = -------------------------------------------------------------------------- = */
	/* �ֿ����� HASH ó�� */	
    CT_CLI cc =  new CT_CLI();
    //cc.setCharSetUtf8(); 
    String ENC_KEY       = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0"; // �ݵ�� �ܺο� ������� �ʵ��� �����Ͻñ� �ٶ��ϴ�.	
    String phone_no_hash   = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "phone_no"   ) ));
    String user_name_hash  = cc.makeHashData(ENC_KEY, f_get_parm( request.getParameter( "user_name"  ) ));
    /* = -------------------------------------------------------------------------- = */
    String tran_cd       = "" ;                                                 // �ŷ������ڵ�
    /* = -------------------------------------------------------------------------- = */
    String res_cd        = "" ;                                                 // ����ڵ�
    String res_msg       = "" ;                                                 // ����޽���
	String res_phone_no  = "" ;                                                 // ���� ��� �޴�����ȣ
    String res_comm_id   = "" ;                                                 // ���� ��� ��Ż��ڵ�	
	String birth_day     = "" ;                                                 // �������   
    String local_code    = "" ;                                                 // ���ܱ��� ����
    String sex_code      = "" ;                                                 // ��������   
    String safe_guard_yn = "" ;                                                 // ��ȣ��ȣ ���� ���Կ���
	String auth_tx_id    = "" ;                                                 // Transaction ID(���)	- SKT ������� ��� ����
	//String van_tx_id     = "" ;                                               // 
    //String van_res_cd    = "" ;                                               // 
    /* ============================================================================== */


    /* ============================================================================== */
    /* =   02. �ν��Ͻ� ���� �� �ʱ�ȭ                                              = */
    /* = -------------------------------------------------------------------------- = */
    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

//     c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
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

    c_PayPlus.mf_set_us( common_data_set, "cust_ip"   ,  cust_ip        );

    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

    // �ֹ� ����
    int ordr_data_set;

    ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );

    c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );

    // ���� ����
    int cert_data_set;

    cert_data_set   = c_PayPlus.mf_add_set( "cert"      );    

    c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  tx_type            ); // ��û ����(2500 ����)
    c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id            ); // ��Ż��ڵ�
	c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn         ); // KCP ǥ������â ��뿩��(Default:N)
    c_PayPlus.mf_set_us( cert_data_set, "phone_no"        ,  phone_no           ); // �޴�����ȣ
	c_PayPlus.mf_set_us( cert_data_set, "phone_no_hash"   ,  phone_no_hash      ); // �޴�����ȣ HASH	
    c_PayPlus.mf_set_us( cert_data_set, "user_name"       ,  user_name          ); // ����
	c_PayPlus.mf_set_us( cert_data_set, "user_name_hash"  ,  user_name_hash     ); // ���� HASH
	c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no        ); // ����Ȯ�� �ŷ���ȣ ( MVNO ��ȸ(2700) �ŷ� ������ ��� �ʼ� )

    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

    /* ============================================================================== */
    /* =   03-3. ����                                                               = */
    /* ------------------------------------------------------------------------------ */
    if ( tran_cd.length() > 0 )
    {
//         c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "0" );
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
        per_cert_no   = c_PayPlus.mf_get_res( "per_cert_no"   ); // ����Ȯ�� �ŷ���ȣ
		res_phone_no  = c_PayPlus.mf_get_res( "phone_no"      ); // �޴�����ȣ
		res_comm_id   = c_PayPlus.mf_get_res( "comm_id"       ); // ��Ż��ڵ�		
		safe_guard_yn = c_PayPlus.mf_get_res( "safe_guard_yn" ); // �޴�����ȣ���� �ξ��� ���Կ���
		birth_day     = c_PayPlus.mf_get_res( "birth_day"     ); // �������
        sex_code      = c_PayPlus.mf_get_res( "sex_code"      ); // �����ڵ�
        local_code    = c_PayPlus.mf_get_res( "local_code"    ); // ��/�ܱ��� ����
		auth_tx_id    = c_PayPlus.mf_get_res( "auth_tx_id"    ); // Transaction ID(���) - SKT ������� ��� ����
		//van_tx_id     = c_PayPlus.mf_get_res( "van_tx_id"     ); // Transaction ID(KCP)
		//van_res_cd    = c_PayPlus.mf_get_res( "van_res_cd"    ); // ��������ڵ�

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
	<title>*** KCP Online Payment System [JSP Version] ***</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <link href="css/style.css" rel="stylesheet" type="text/css"/>

    </head>

 <body>
	<div id="sample_wrap">
    <form name="pay_info" method="post" action="./new_pp_cli_otp.jsp">
	       <!-- Ÿ��Ʋ Start -->
               <h1>[CI /DI ��û] <span>�� �������� �� ���� �Ϸ� �� CI /DI �� ��û�ϴ� ����(����) �������Դϴ�.</span></h1>
           <!-- Ÿ��Ʋ End -->

            <div class="sample">
		    <!-- ��� ���̺� Start -->     
               <p>
                    �ҽ� ���� �� �������� ��Ȳ�� �°� ������ ���� �����Ͻñ� �ٶ��ϴ�.<br />
                    ��û �� �ʿ��� ������ ��Ȯ�ϰ� �Է��Ͻþ� �����Ͻñ� �ٶ��ϴ�.
               </p>
            <!-- ��� ���̺� End -->

				<!-- ��û ��� ��� ���̺� Start -->
                <h2>&sdot; ��û ��� ����</h2>
                <table class="tbl" cellpadding="0" cellspacing="0">
                     <!-- ����ڵ� -->
                    <tr>
                        <th>����ڵ�</th>
                        <td><%=res_cd%></td>
                    </tr>
                    <!-- ����޼��� -->
                    <tr>
                        <th>����޼���</th>
                        <td><%= res_msg %></td>
                    </tr>                    
                </table>
                <!-- ��û ��� ��� ���̺� End -->

<%
            if ( "0000".equals ( res_cd ) )
            {
%>
		        <!-- �ֹ� ���� ��� ���̺� Start -->
                <h2>&sdot; ����Ȯ�� ��� ����</h2>
                <table class="tbl" cellpadding="0" cellspacing="0">
                     <!-- �ֹ���ȣ -->
                    <tr>
                        <th>�ֹ� ��ȣ</th>
                        <td><%=ordr_idxx%></td>
                    </tr>
                    <!-- per_cert_no -->
                    <tr>
                        <th>�ŷ���ȣ</th>
                        <td><%= per_cert_no %></td>
                    </tr>
                    <!-- res_phone_no -->
                    <tr>
                        <th>�޴�����ȣ</th>
                        <td><%= res_phone_no %></td>
                    </tr>
                    <!-- res_comm_id -->
                    <tr>
                        <th>��Ż��ڵ�</th>
                        <td><%= res_comm_id %></td>
                    </tr>
                    <!-- safe_guard_yn -->
                    <tr>
                        <th>"�ξ���" ���� ����</th>
                        <td><%= safe_guard_yn %></td>
                    </tr>
                    
                    <!-- birth_day -->
<%
		if ( ! "".equals (birth_day) )
		{
%>					
                    <tr>
                        <th>�������</th>
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
                        <th>���� ����</th>
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
                        <th>��/�ܱ��� ����</th>
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
                <!-- �ֹ� ���� ��� ���̺� End -->
<%
			}
%>

                <!-- ���� ��ư ���̺� Start -->
                    <div class="btnset">
                    <input name="" type="submit" class="submit" value="CI/DI��û"/>
                    <a href="../index.html" class="home">ó������</a>
                    </div>
                    <!-- ���� ��ư ���̺� End -->

	        <div class="footer">
                Copyright (c) NHN KCP INC. All Rights reserved.
            </div>            

		    <!-- CI / DI ��û ���̺� Start -->
            <input type="hidden" name="auth_tx_id"    value="<%= auth_tx_id    %>" />  <!-- Transaction ID(���) - SKT ������� ��� ����. -->
            <input type="hidden" name="kcp_web_yn"    value="<%= kcp_web_yn    %>" />  <!-- KCP ǥ������â ��뿩�� -->
            <input type="hidden" name="comm_id"       value="<%= comm_id       %>" />  <!-- ��Ż��ڵ� -->
            <input type="hidden" name="per_cert_no"   value="<%= per_cert_no   %>" />  <!-- ����Ȯ�� �ŷ���ȣ -->
			<input type="hidden" name="tx_type"       value="2600"                 />  <!-- ��û ���� -->
			<!-- CI / DI ��û ���̺� End -->
 </div>
        </form>
		</div>
    </body>
    </html>
