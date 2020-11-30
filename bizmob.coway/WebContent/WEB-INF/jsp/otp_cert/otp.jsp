<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* = ※ OTP 요청 페이지 ※                                                      = */
    /* = -------------------------------------------------------------------------- = */
    /* =   Copyright (c)  2017.06  NHN KCP Inc.   All Rights Reserverd.             = */
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
    request.setCharacterEncoding ( "utf-8" ) ;
    /* ============================================================================== */
    /* =   01. 요청 정보 설정                                                       = */
    /* = -------------------------------------------------------------------------- = */
    String res_cd           = f_get_parm( request.getParameter( "res_cd"          ) );  // 결과 코드
    String res_msg          = f_get_parm( request.getParameter( "res_msg"         ) );  // 결과 메세지
    /* = -------------------------------------------------------------------------- = */
    String ordr_idxx        = f_get_parm( request.getParameter( "ordr_idxx"       ) );  // 주문번호
    /* = -------------------------------------------------------------------------- = */
    String kcp_web_yn       = f_get_parm( request.getParameter( "kcp_web_yn"      ) );  // KCP 표준인증창 사용여부
    String per_cert_no      = f_get_parm( request.getParameter( "per_cert_no"     ) );  // 본인확인 거래번호
    String comm_id          = f_get_parm( request.getParameter( "comm_id"         ) );  // 이통사 코드
    String cp_sms_msg       = f_get_parm( request.getParameter( "cp_sms_msg"      ) );  // CP지정 SMS 메시지
    String cp_callback      = f_get_parm( request.getParameter( "cp_callback"     ) );  // CP지정 SMS 회신번호
    /* = -------------------------------------------------------------------------- = */
    /* =   01. 요청 정보 설정 End                                                   = */
    /* ============================================================================== */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <title>*** KCP Payment System ***</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">

    var gap = 120; //남은초 기본값 ( 최대 120초 )

    function msg_time()
    {
        m = Math.floor(gap/60)+"분 "+(gap%60)+"초";
        // window.status = m;
        document.all.x.innerHTML="<font color='red'>" + m + "</font> 남았습니다.";
        gap--;
        if(gap < 0)
        {
            if(!confirm("타임오버 재요청"))
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
            alert("인증번호를 입력하여 주십시오.");
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

        <!-- 타이틀 Start -->
        <h1>[본인확인]<span> 이 페이지는 본인확인 페이지의 샘플(예제)페이지 입니다.</span></h1>
        <!-- 타이틀 End -->

        <!-- 상단 테이블 Start -->
        <div class="sample">
        <p>
            소스 수정 시 가맹점의 상황에 맞게 적절히 수정 적용하시길 바랍니다.<br />
            요청시 필요한 정보를 정확하게 입력하시어 추가요청을 진행하시기 바랍니다.
        </p>
        <!-- 상단 테이블 End -->

        <!-- 요청 정보 출력 테이블 Start -->
        <h2>&sdot; 요 청 정 보</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- 인증 제한시간 -->
            <tr>
                <th>인증제한시간</th>
                <td id="x"></td>
            </tr>
            <!-- OTP번호 -->
            <tr>
                <th>OTP번호</th>
                <td><input type="text" name="otp_no" value="" maxlength="6" style="width:50px" /></td>
            </tr>
            <!-- 휴대폰 인증보호 수신동의
			    /* == 2.3 인증보호 수신동의 거래건 처리(LGU only)                     == */ 
                /* 민앤지 번호보호 서비스 미가입자이면서, LGU+ USIM OTP 미가입자가       */ 
                /* 표준(허브) 인증창에서 "인증보호 수신동의"한 거래가 최종 성공하였을때  */ 
                /* LGU+로 서비스 가입을 권유하는 메시지 전송을 요청                      */
            -->
            <tr>
                <th>휴대폰 인증보호 수신동의</th>
                <td>
                    <select name='ad1_agree_yn' class="frmselect" style="width:70px">
                        <option value='Y'>Y</option>
                        <option value='N' selected>N</option>
                    </select>
                </td>
            </tr>
        </table>
        <!-- 요청 정보 출력 테이블 End -->

        <!-- 버튼 테이블 Start -->
        <div class="btnset">
            <input name="" type="submit" class="submit" value="인증요청" onclick="jsf__pay();"/>
            <a href="../index.html" class="home">처음으로</a>
        </div>
        <!-- 버튼 테이블 End -->
        </div>
        <div class="footer">
            Copyright (c) NHN KCP INC. All Rights reserved.
        </div>

    <!-- 본인확인 전달데이터 -->
    <input type="hidden" name="ordr_idxx"       value="<%= ordr_idxx       %>" />  <!-- 주문번호 -->
    <input type="hidden" name="kcp_web_yn"      value="<%= kcp_web_yn      %>" />  <!-- KCP 표준인증창 사용여부 -->
    <input type="hidden" name="per_cert_no"     value="<%= per_cert_no     %>" />  <!-- 본인확인 거래번호 -->
    <input type="hidden" name="comm_id"         value="<%= comm_id         %>" />  <!-- 이동통신사 코드 -->    
    <input type="hidden" name="cp_sms_msg"      value="<%= cp_sms_msg      %>" />  <!-- CP지정 SMS 메시지 -->
    <input type="hidden" name="cp_callback"     value="<%= cp_callback     %>" />  <!-- CP지정 SMS 회신번호 -->
</form>
</div>
</body>
</html>
