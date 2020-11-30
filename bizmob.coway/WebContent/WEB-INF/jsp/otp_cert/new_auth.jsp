<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>*** NHN KCP Online Payment System [JSP Version] ***</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">

        // 결제 방법 선택
        function auth_type_check()
        {
            var auth_form      = document.form_auth;           

            auth_form.phone_no.value  = auth_form.phone_no0.value + auth_form.phone_no1.value + auth_form.phone_no2.value;

            var return_Val = false ;

            /* comm_id 이통사 코드값 구분 */
            var comm_id = auth_form.comm_id_type_sel;

            for ( var inx = 0 ; inx < comm_id.length ; inx++ )
            {
                if ( comm_id[ inx ].checked )
                {
                    auth_form.comm_id.value = comm_id[ inx ].value;
                    break;
                }
            }
           
            if ( auth_form.ordr_idxx.value == "" )
            {
                alert( "주문자 번호가 없습니다. 다시 시도해 주세요." );
            }
            else if( auth_form.user_name.value == "" )
            {
                alert( "인증자 이름을 입력해 주세요." );
            }
            else if (auth_form.phone_no.value == "" || auth_form.phone_no.value.length < 10 )
            {
                alert("휴대폰 번호를 다시 확인해 주세요.");
            }
            else if ( auth_form.comm_id.value == "")
            {
                alert ("이동통신사를 선택해주세요.") ;
            }
            else
            {
                return_Val = true ;
            }

            return return_Val ;
        }

        /* 주문번호 생성 예제 */
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
    <form name="form_auth" method="post" action="new_pp_cli_auth.jsp">

        <!-- 타이틀 Start -->
        <h1>[본인확인]<span> 이 페이지는 앱 인증 요청의 샘플(예제)페이지 입니다.</span></h1>
        <!-- 타이틀 End -->

        <!-- 상단 테이블 Start -->
        <div class="sample">
        <p>
            소스 수정 시 가맹점의 상황에 맞게 적절히 수정 적용하시길 바랍니다.<br />
            요청 시 필요한 정보를 정확하게 입력하시어 진행하시기 바랍니다.
        </p>
        <!-- 상단 테이블 End -->

        <!-- 요청 정보 출력 테이블 Start -->
        <h2>&sdot; 요 청 정 보</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- 명의자명 -->
            <tr>
                <th>성명</th>
                <td><input type="text" name="user_name" maxlength="50" class="frminput" value="" style="width:50px" /></td>
            </tr>
            <!-- 휴대폰번호 -->
            <tr>
                <th>휴대폰번호</th>
                <td>
                    <select name='phone_no0' class="frmselect" style="width:50px">
                        <option value='010'>010</option>
                        <option value='011'>011</option>
                        <option value='016'>016</option>
                        <option value='018'>018</option>
                        <option value='019'>019</option>
                    </select>
                    <input type="text" name="phone_no1" value="" maxlength="4" class="frminput" style="width:30px" />
                    <input type="text" name="phone_no2" value="" maxlength="4" class="frminput" style="width:30px" />
                </td>
            </tr>
            <!-- 이통사코드 -->
            <tr>
                <th>이동통신사</th>
                <td>
                    <input type="radio" name="comm_id_type_sel" value="SKT" />SKT
                    <input type="radio" name="comm_id_type_sel" value="KTF" checked />KTF
                    <input type="radio" name="comm_id_type_sel" value="LGT"  />LGT
                    <input type="radio" name="comm_id_type_sel" value="KTM" />KTM
                    <input type="radio" name="comm_id_type_sel" value="LGM" />LGM                    
                </td>
            </tr>
            <!-- MVNO 사업자조회 거래번호. SKM 제외 -->
            <tr>
                <th>MVNO 사업자조회 거래번호</th>
                <td><input type="text" name="per_cert_no" maxlength="14" class="frminput" value="" style="width:50px" /></td>
            </tr>
        </table>
        <!-- 주문 정보 출력 테이블 End -->

        <!-- 버튼 테이블 Start -->
        <div class="btnset">
            <input name="" type="submit" class="submit" value="앱 인증 요청" onclick="auth_type_check();"/>
            <a href="../index.html" class="home">처음으로</a>
        </div>
        <!-- 버튼 테이블 End -->
        </div>
        <div class="footer">
            Copyright (c) NHN KCP INC. All Rights reserved.
        </div>

        <!-- 요청 타입 -->
        <input type="hidden" name="tx_type"         value="2500" />
        <!-- 휴대폰 번호 -->
        <input type="hidden" name="phone_no"        value="" />
        <!-- 이통사코드 -->
        <input type="hidden" name="comm_id"         value="" />
        <!-- 주문번호 -->
        <input type="hidden" name="ordr_idxx"       value="" />
		<!-- KCP 표준인증창 사용여부 -->
        <input type="hidden" name="kcp_web_yn"      value="N" />
    </form>
</div>
</body>
</html>
