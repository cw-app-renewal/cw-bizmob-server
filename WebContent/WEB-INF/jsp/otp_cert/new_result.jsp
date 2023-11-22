<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : ���� ��� ��� PAGE                                               = */
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
    request.setCharacterEncoding ( "euc-kr" ) ;
    /* ============================================================================== */
    /* =  ���� ���                                                                = */
    /* = -------------------------------------------------------------------------- = */
    String res_cd        = f_get_parm( request.getParameter( "res_cd"      ) );  // ��� �ڵ�
    String res_msg       = f_get_parm( request.getParameter( "res_msg"     ) );  // ��� �޼���
    /* = -------------------------------------------------------------------------- = */
    String per_cert_no   = f_get_parm( request.getParameter( "per_cert_no" ) );  // ����Ȯ�� �ŷ���ȣ
    String res_phone_no  = f_get_parm( request.getParameter( "res_phone_no"  ) ); // �޴�����ȣ
    String res_comm_id   = f_get_parm( request.getParameter( "res_comm_id"   ) ); // ��Ż��ڵ�
    String res_user_name = f_get_parm( request.getParameter( "res_user_name" ) ); // ����
    String CI            = f_get_parm( request.getParameter( "CI"          ) );  // CI
    String DI            = f_get_parm( request.getParameter( "DI"          ) );  // DI
    String CI_URL        = f_get_parm( request.getParameter( "CI_URL"      ) );  // CI_URL
    String DI_URL        = f_get_parm( request.getParameter( "DI_URL"      ) );  // DI_URL  
    String auth_tx_id    = f_get_parm( request.getParameter( "auth_tx_id"  ) );  // auth_tx_id
    /* ============================================================================== */
    String birth_day     = f_get_parm( request.getParameter( "birth_day"   ) );  // �������( KTF�� ��츸 ��� ) 
    String sex_code      = f_get_parm( request.getParameter( "sex_code"    ) );  // �����ڵ�( KTF�� ��츸 ��� )
    String local_code    = f_get_parm( request.getParameter( "local_code"  ) );  // ���ܱ���( KTF�� ��츸 ��� )
    /* ============================================================================== */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div id="sample_wrap">

        <!-- Ÿ��Ʋ Start -->
        <h1>[������]<span> �� �������� ���� ����� ����ϴ� ����(����)������ �Դϴ�.</span></h1>
        <!-- Ÿ��Ʋ End -->

        <!-- ��� ���̺� Start -->
        <div class="sample">
        <p>
            ���� ����� ����ϴ� ������ �Դϴ�.<br/>
            ��û�� ���������� ó���� ��� ����ڵ�(res_cd)���� 0000���� ǥ�õ˴ϴ�.
        </p>
        <!-- ��� ���̺� End -->

<%
    /* ============================================================================== */
    /* =   ���� ��� �ڵ� �� �޽��� ���(����������� �ݵ�� ������ֽñ� �ٶ��ϴ�.)= */
    /* = -------------------------------------------------------------------------- = */
    /* =   ���� ���� : res_cd���� 0000���� �����˴ϴ�.                              = */
    /* =   ���� ���� : res_cd���� 0000�̿��� ������ �����˴ϴ�.                     = */
    /* = -------------------------------------------------------------------------- = */
%>
        <!-- ��� ���� ��� ���̺� Start -->
        <h2>&sdot; �� �� �� ��</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- ��� �ڵ� -->
            <tr>
                <th>��� �ڵ�</th>
                <td><%=res_cd%></td>
            </tr>
            <!-- ��� �޽��� -->
            <tr>
                <th>��� �޼���</th>
                <td><%=res_msg%></td>
            </tr>
        </table>
<%
    /* = -------------------------------------------------------------------------- = */
    /* =   ���� ��� �ڵ� �� �޽��� ��� ��                                         = */
    /* ============================================================================== */
%>

<%
    /* ============================================================================== */
    /* =   ���� ��� ���                                                                                                                                                = */    
    /* ============================================================================== */
    /* =   1. ���� ���� �� ���� ��� ��� ( res_cd���� 0000�� ���)                 = */
    /* = -------------------------------------------------------------------------- = */
    if ( "0000".equals ( res_cd ) )
    {
%>
        <!-- ���� ��� ��� ���̺� Start -->
        <h2>&sdot; �� �� �� �� �� ��</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- ����Ȯ�� �ŷ���ȣ -->
            <tr>
                <th>per_cert_no</th>
                <td><%= per_cert_no %></td>
            </tr>
            <!-- �޴�����ȣ -->
            <tr>
                <th>res_phone_no</th>
                <td><%= res_phone_no %></td>
            </tr>
            <!-- ��Ż��ڵ� -->
            <tr>
                <th>res_comm_id</th>
                <td><%= res_comm_id %></td>
            </tr>
            <!-- ���� -->
            <tr>
                <th>res_user_name</th>
                <td><%= res_user_name %></td>
            </tr>

            <!-- CI -->
            <tr>
                <th>CI</th>
                <td><%= CI %></td>
            </tr>
            <!-- DI -->
            <tr>
                <th>DI</th>
                <td><%= DI %></td>
            </tr>
            <!-- CI_URL -->
            <tr>
                <th>CI_URL</th>
                <td><%= CI_URL %></td>
            </tr>
            <!-- DI_URL -->
            <tr>
                <th>DI_URL</th>
                <td><%= DI_URL %></td>
            </tr>
            
        <!-- auth_tx_id -->
<%
		if ( "SKT".equals (res_comm_id) )
		{
%>					
                    <tr>
                        <th>auth_tx_id</th>
                        <td><%= auth_tx_id %></td>
                    </tr>
<%
		}
%>

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
    /* = -------------------------------------------------------------------------- = */
    /* =   1. ���� ���� �� ���� ��� ��� END                                                  = */
    /* ============================================================================== */  
    }
%>
        </table>

        <!-- ��ư ���̺� Start -->
        <div class="btnset">
            <a href="../index.html" class="home">ó������</a>
        </div>
        <!-- ��ư ���̺� End -->
        </div>
        <div class="footer">
            Copyright (c) NHN KCP INC. All Rights reserved.
        </div>
</div>
</body>
</html>
