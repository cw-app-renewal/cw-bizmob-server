<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>*** NHN KCP Online Payment System [JSP Version] ***</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        
        function auth_type_check()
        {
            var auth_form      = document.form_auth;
            var sex_code_type  = document.form_auth.sex_code_sel;

            auth_form.phone_no.value  = auth_form.phone_no0.value + auth_form.phone_no1.value + auth_form.phone_no2.value;

            var return_Val = false ;

            /* comm_id ����� �ڵ尪 ���� */
            var comm_id = auth_form.comm_id_type_sel;

            for ( var inx = 0 ; inx < comm_id.length ; inx++ )
            {
                if ( comm_id[ inx ].checked )
                {
                    auth_form.comm_id.value = comm_id[ inx ].value;
                    break;
                }
            }

            /* ���� �ڵ� ���� */
            if ( sex_code_type[0].checked )
            {
                auth_form.sex_code.value = sex_code_type[0].value
            }
            if ( sex_code_type[1].checked )
            {
                auth_form.sex_code.value = sex_code_type[1].value
            }

            if ( auth_form.ordr_idxx.value == "" )
            {
                alert( "�ֹ��� ��ȣ�� �����ϴ�. �ٽ� �õ��� �ּ���." );
            }
            else if( auth_form.user_name.value == "" )
            {
                alert( "������ �̸��� �Է��� �ּ���." );
            }
            else if (auth_form.phone_no.value == "" || auth_form.phone_no.value.length < 10 )
            {
                alert("�޴��� ��ȣ�� �ٽ� Ȯ���� �ּ���.");
            }
            else if ( auth_form.comm_id.value == "")
            {
                alert ("�̵���Ż縦 �������ּ���.") ;
            }
            else if( auth_form.birth_day.value == "" || auth_form.birth_day.value.length != 8 )
            {
                alert( "��������� �Է��� �ּ���." );
            }
            else if ( auth_form.sex_code.value =="" )
            {
                alert ("������ �������ּ���.") ;
            }
            else if( auth_form.local_code.value == "" )
            {
                alert( "������ ������ �ּ���." );
            }
            else
            {
                return_Val = true ;
            }

            return return_Val ;
        }

        /* �ֹ���ȣ ���� ���� */
        function init_orderid()
        {
            var today = new Date();
            var year  = today.getFullYear();
            var month = today.getMonth() + 1;
            var date  = today.getDate();
            var time  = today.getTime();

            if(parseInt(month) < 10) {
                month = "0" + month;
            }

            if(parseInt(date) < 10) {
                date = "0" + date;
            }

            var order_idxx = "TEST" + year + "" + month + "" + date + "" + time;

            document.form_auth.ordr_idxx.value = order_idxx;
        }
    </script>
</head>

<body onload="init_orderid()">
<div id="sample_wrap">
    <form name="form_auth" method="post" action="pp_cli_auth">

        <!-- Ÿ��Ʋ Start -->
        <h1>[����Ȯ��]<span> �� �������� ����Ȯ�� �������� ����(����)������ �Դϴ�.</span></h1>
        <!-- Ÿ��Ʋ End -->

        <!-- ��� ���̺� Start -->
        <div class="sample">
        <p>�ҽ� ���� �� �������� ��Ȳ�� �°� ������ ���� �����Ͻñ� �ٶ��ϴ�.<br />
                       ��û�� �ʿ��� ������ ��Ȯ�ϰ� �Է��Ͻþ� �߰���û�� �����Ͻñ� �ٶ��ϴ�.
        </p>
        <!-- ��� ���̺� End -->

        <!-- ��û ���� ��� ���̺� Start -->
        <h2>&sdot; �� û �� ��</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- �����ڸ� -->
            <tr>
                <th>����</th>
                <td><input type="text" name="user_name" maxlength="50" class="frminput" value="" style="width:50px" /></td>
            </tr>
            <!-- �޴�����ȣ -->
            <tr>
                <th>�޴�����ȣ</th>
                <td>
                    <select name='phone_no0' class="frmselect" style="width:50px">
                        <option value='010' selected >010</option>
                        <option value='011'>011</option>
                        <option value='016'>016</option>
                        <option value='018'>018</option>
                        <option value='019'>019</option>
                    </select>
                    <input type="text" name="phone_no1" value="" maxlength="4" class="frminput" style="width:30px" />
                    <input type="text" name="phone_no2" value="" maxlength="4" class="frminput" style="width:30px" />
                </td>
            </tr>
            <!-- ������ڵ� -->
            <tr>
                <th>�̵���Ż�</th>
                <td>
                    <input type="radio" name="comm_id_type_sel" value="SKT" />SKT
                    <input type="radio" name="comm_id_type_sel" value="KTF" checked />KTF
                    <input type="radio" name="comm_id_type_sel" value="LGT" />LGT                    
                    <input type="radio" name="comm_id_type_sel" value="SKM" />SKM
                    <input type="radio" name="comm_id_type_sel" value="KTM" />KTM
                    <input type="radio" name="comm_id_type_sel" value="LGM" />LGM                    
                </td>
            </tr>
            <!-- ������� -->
            <tr>
                <th>�������</th>
                <td><input type="text" name="birth_day" maxlength="8" class="frminput" value="" style="width:70px" /></td>
            </tr>
            <!-- �������� -->
            <tr>
                <th>��������</th>
                <td>
                    <input type="radio" name="sex_code_sel" value="01" checked />����
                    <input type="radio" name="sex_code_sel" value="02" />����
                    <!-- ��/�ܱ��α��� -->
                    <select name='local_code' class="frmselect" style="width:70px">
                        <option value='01'selected >������</option>
                        <option value='02'>�ܱ���</option>
                    </select>
                </td>
            </tr>
			<!-- MVNO �������ȸ �ŷ���ȣ. SKM ���� -->
            <tr>
                <th>MVNO �������ȸ �ŷ���ȣ</th>
                <td><input type="text" name="per_cert_no" maxlength="14" class="frminput" value="" style="width:50px" /></td>
            </tr>
        </table>
        <!-- �ֹ� ���� ��� ���̺� End -->

        <!-- ��ư ���̺� Start -->
        <div class="btnset">
            <input name="" type="submit" class="submit" value="������û" onclick="return auth_type_check();"/>
            <a href="../index.html" class="home">ó������</a>
        </div>
        <!-- ��ư ���̺� End -->
        </div>
        <div class="footer">
            Copyright (c) NHN KCP INC. All Rights reserved.
        </div>

        <!-- KCP ǥ������â ��뿩�� -->
        <input type="hidden" name="kcp_web_yn"      value="N" />
        <!-- �޴��� ��ȣ -->
        <input type="hidden" name="phone_no"        value="" />
        <!-- ������ڵ� -->
        <input type="hidden" name="comm_id"         value="" />
        <!-- ���� -->
        <input type="hidden" name="sex_code"        value="" />
        <!-- CP���� SMS �޽���  -->
        <input type="hidden" name="cp_sms_msg"      value="[NHNKCP SHOP]�̿��ڹ�ȣ��[000000]�Դϴ�" />
        <!-- CP���� SMS ȸ�Ź�ȣ  -->
        <input type="hidden" name="cp_callback"     value="15448661" />
        <!-- �ֹ���ȣ -->
        <input type="hidden" name="ordr_idxx"       value="" />
    </form>
</div>
</body>
</html>
