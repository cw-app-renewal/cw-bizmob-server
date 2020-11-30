<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%
    /* ============================================================================== */
    /* =   PAGE : 인증 결과 출력 PAGE                                               = */
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
    request.setCharacterEncoding ( "euc-kr" ) ;
    /* ============================================================================== */
    /* =  인증 결과                                                                = */
    /* = -------------------------------------------------------------------------- = */
    String res_cd        = f_get_parm( request.getParameter( "res_cd"      ) );  // 결과 코드
    String res_msg       = f_get_parm( request.getParameter( "res_msg"     ) );  // 결과 메세지
    /* = -------------------------------------------------------------------------- = */
    String per_cert_no   = f_get_parm( request.getParameter( "per_cert_no" ) );  // 본인확인 거래번호
    String res_phone_no  = f_get_parm( request.getParameter( "res_phone_no"  ) ); // 휴대폰번호
    String res_comm_id   = f_get_parm( request.getParameter( "res_comm_id"   ) ); // 통신사코드
    String res_user_name = f_get_parm( request.getParameter( "res_user_name" ) ); // 고객명
    String CI            = f_get_parm( request.getParameter( "CI"          ) );  // CI
    String DI            = f_get_parm( request.getParameter( "DI"          ) );  // DI
    String CI_URL        = f_get_parm( request.getParameter( "CI_URL"      ) );  // CI_URL
    String DI_URL        = f_get_parm( request.getParameter( "DI_URL"      ) );  // DI_URL  
    String auth_tx_id    = f_get_parm( request.getParameter( "auth_tx_id"  ) );  // auth_tx_id
    /* ============================================================================== */
    String birth_day     = f_get_parm( request.getParameter( "birth_day"   ) );  // 생년월일( KTF의 경우만 사용 ) 
    String sex_code      = f_get_parm( request.getParameter( "sex_code"    ) );  // 성별코드( KTF의 경우만 사용 )
    String local_code    = f_get_parm( request.getParameter( "local_code"  ) );  // 내외국인( KTF의 경우만 사용 )
    /* ============================================================================== */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div id="sample_wrap">

        <!-- 타이틀 Start -->
        <h1>[결과출력]<span> 이 페이지는 인증 결과를 출력하는 샘플(예제)페이지 입니다.</span></h1>
        <!-- 타이틀 End -->

        <!-- 상단 테이블 Start -->
        <div class="sample">
        <p>
            인증 결과를 출력하는 페이지 입니다.<br/>
            요청이 정상적으로 처리된 경우 결과코드(res_cd)값이 0000으로 표시됩니다.
        </p>
        <!-- 상단 테이블 End -->

<%
    /* ============================================================================== */
    /* =   인증 결과 코드 및 메시지 출력(결과페이지에 반드시 출력해주시기 바랍니다.)= */
    /* = -------------------------------------------------------------------------- = */
    /* =   인증 정상 : res_cd값이 0000으로 설정됩니다.                              = */
    /* =   인증 실패 : res_cd값이 0000이외의 값으로 설정됩니다.                     = */
    /* = -------------------------------------------------------------------------- = */
%>
        <!-- 결과 정보 출력 테이블 Start -->
        <h2>&sdot; 결 과 정 보</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- 결과 코드 -->
            <tr>
                <th>결과 코드</th>
                <td><%=res_cd%></td>
            </tr>
            <!-- 결과 메시지 -->
            <tr>
                <th>결과 메세지</th>
                <td><%=res_msg%></td>
            </tr>
        </table>
<%
    /* = -------------------------------------------------------------------------- = */
    /* =   인증 결과 코드 및 메시지 출력 끝                                         = */
    /* ============================================================================== */
%>

<%
    /* ============================================================================== */
    /* =   인증 결과 출력                                                                                                                                                = */    
    /* ============================================================================== */
    /* =   1. 정상 인증 시 인증 결과 출력 ( res_cd값이 0000인 경우)                 = */
    /* = -------------------------------------------------------------------------- = */
    if ( "0000".equals ( res_cd ) )
    {
%>
        <!-- 인증 결과 출력 테이블 Start -->
        <h2>&sdot; 인 증 결 과 정 보</h2>
        <table class="tbl" cellpadding="0" cellspacing="0">
            <!-- 본인확인 거래번호 -->
            <tr>
                <th>per_cert_no</th>
                <td><%= per_cert_no %></td>
            </tr>
            <!-- 휴대폰번호 -->
            <tr>
                <th>res_phone_no</th>
                <td><%= res_phone_no %></td>
            </tr>
            <!-- 통신사코드 -->
            <tr>
                <th>res_comm_id</th>
                <td><%= res_comm_id %></td>
            </tr>
            <!-- 고객명 -->
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
                        <th>생년월일</th>
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
                        <th>성별 정보</th>
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
                        <th>내/외국인 정보</th>
                        <td><%= local_code %></td>
                    </tr>
<%
		}
    /* = -------------------------------------------------------------------------- = */
    /* =   1. 정상 인증 시 인증 결과 출력 END                                                  = */
    /* ============================================================================== */  
    }
%>
        </table>

        <!-- 버튼 테이블 Start -->
        <div class="btnset">
            <a href="../index.html" class="home">처음으로</a>
        </div>
        <!-- 버튼 테이블 End -->
        </div>
        <div class="footer">
            Copyright (c) NHN KCP INC. All Rights reserved.
        </div>
</div>
</body>
</html>
