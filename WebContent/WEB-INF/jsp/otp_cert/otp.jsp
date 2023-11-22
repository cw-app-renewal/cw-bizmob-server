<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* = �� OTP ��û ������ ��                                                      = */
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
    request.setCharacterEncoding ( "utf-8" ) ;
    /* ============================================================================== */
    /* =   01. ��û ���� ����                                                       = */
    /* = -------------------------------------------------------------------------- = */
    String res_cd           = f_get_parm( request.getParameter( "res_cd"          ) );  // ��� �ڵ�
    String res_msg          = f_get_parm( request.getParameter( "res_msg"         ) );  // ��� �޼���
    /* = -------------------------------------------------------------------------- = */
    String ordr_idxx        = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // �ֹ���ȣ
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn       = f_get_parm( request.getParameter( "kcp_web_yn"      ) );  // KCP ǥ������â ��뿩��
    String per_cert_no      = f_get_parm( request.getParameter( "per_cert_no"     ) );  // ����Ȯ�� �ŷ���ȣ
    String comm_id          = f_get_parm( request.getParameter( "comm_id"         ) );  // ����� �ڵ�
    String cp_sms_msg       = f_get_parm( request.getParameter( "cp_sms_msg"      ) );  // CP���� SMS �޽���
    String cp_callback      = f_get_parm( request.getParameter( "cp_callback"     ) );  // CP���� SMS ȸ�Ź�ȣ
    /* = -------------------------------------------------------------------------- = */
    /* =   01. ��û ���� ���� End                                                   = */
    /* ============================================================================== */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>*** KCP Payment System ***</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">

    var gap = 120; //������ �⺻�� ( �ִ� 120�� )

    function msg_time()
    {
        m = Math.floor(gap/60)+"�� "+(gap%60)+"��";
        // window.status = m;
        document.all.x.innerHTML="<font color='red'>" + m + "</font> ���ҽ��ϴ�.";
        gap--;
        if(gap < 0)
        {
            if(!confirm("Ÿ�ӿ��� ���û"))
            {
                clearInterval(tid);
                window.location.href="./auth";
                return true;
            }
            retry();
            clearInterval(tid);
        }
    }

    function retry()
    {
        document.formOrder.action = "pp_cli_otp";
        document.formOrder.method = "post";
        document.formOrder.submit();
    }

    function  jsf__pay()
    {
        if (document.formOrder.otp_no.value == "")
        {
            alert("������ȣ�� �Է��Ͽ� �ֽʽÿ�.");
        }
        else
        {
            document.formOrder.action = "pp_cli_hub";
            document.formOrder.method = "post";
            document.formOrder.submit();
        }
    }

    </script>
</head>
<body onload = "tid=setInterval('msg_time()',1000);">
<div id="sample_wrap">
    <form name="formOrder" method="post">

        <!-- Ÿ��Ʋ Start -->
        <h1>[����Ȯ��]<span> �� �������� ����Ȯ�� �������� ����(����)������ �Դϴ�.</span></h1>
        <!-- Ÿ��Ʋ End -->

        <!-- ��� ���̺� Start -->
        <div class="sample">
        <p>
            �ҽ� ���� �� �������� ��Ȳ�� �°� ������ ���� �����Ͻñ� �ٶ��ϴ�.<br />
            ��û�� �ʿ��� ������ ��Ȯ�ϰ� �Է��Ͻþ� �߰���û�� �����Ͻñ� �ٶ��ϴ�.
        </p>
        <!-- ��� ���̺� End -->

        <!-- ��û ���� ��� ���̺� Start -->
        <h2>&sdot; �� û �� ��</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- ���� ���ѽð� -->
            <tr>
                <th>�������ѽð�</th>
                <td id="x"></td>
            </tr>
            <!-- OTP��ȣ -->
            <tr>
                <th>OTP��ȣ</th>
                <td><input type="text" name="otp_no" value="" maxlength="6" style="width:50px" /></td>
            </tr>
            <!-- �޴��� ������ȣ ���ŵ���
			    /* == 2.3 ������ȣ ���ŵ��� �ŷ��� ó��(LGU only)                     == */ 
                /* �ξ��� ��ȣ��ȣ ���� �̰������̸鼭, LGU+ USIM OTP �̰����ڰ�       */ 
                /* ǥ��(���) ����â���� "������ȣ ���ŵ���"�� �ŷ��� ���� �����Ͽ�����  */ 
                /* LGU+�� ���� ������ �����ϴ� �޽��� ������ ��û                      */
            -->
            <tr>
                <th>�޴��� ������ȣ ���ŵ���</th>
                <td>
                    <select name='ad1_agree_yn' class="frmselect" style="width:70px">
                        <option value='Y'>Y</option>
                        <option value='N' selected>N</option>
                    </select>
                </td>
            </tr>
        </table>
        <!-- ��û ���� ��� ���̺� End -->

        <!-- ��ư ���̺� Start -->
        <div class="btnset">
            <input name="" type="submit" class="submit" value="������û" onclick="jsf__pay();"/>
            <a href="../index.html" class="home">ó������</a>
        </div>
        <!-- ��ư ���̺� End -->
        </div>
        <div class="footer">
            Copyright (c) NHN KCP INC. All Rights reserved.
        </div>

    <!-- ����Ȯ�� ���޵����� -->
    <input type="hidden" name="ordr_idxx"       value="<%= ordr_idxx       %>" />  <!-- �ֹ���ȣ -->
    <input type="hidden" name="kcp_web_yn"      value="<%= kcp_web_yn      %>" />  <!-- KCP ǥ������â ��뿩�� -->
    <input type="hidden" name="per_cert_no"     value="<%= per_cert_no     %>" />  <!-- ����Ȯ�� �ŷ���ȣ -->
    <input type="hidden" name="comm_id"         value="<%= comm_id         %>" />  <!-- �̵���Ż� �ڵ� -->    
    <input type="hidden" name="cp_sms_msg"      value="<%= cp_sms_msg      %>" />  <!-- CP���� SMS �޽��� -->
    <input type="hidden" name="cp_callback"     value="<%= cp_callback     %>" />  <!-- CP���� SMS ȸ�Ź�ȣ -->
</form>
</div>
</body>
</html>
