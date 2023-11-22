package adapter.cert;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kcp.J_PP_CLI_N;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.NfcCommonResponse;
import common.ResponseUtil;
import kr.co.kcp.CT_CLI;

/**
 * 
 * 2017.08.02
 * 
 * 
 * **/

@Adapter( trcode = {"CGW300"} )
public class CGW300_ADT_Cert_request extends AbstractTemplateAdapter implements IAdapterJob{

	private static final Logger logger = LoggerFactory.getLogger(CGW300_ADT_Cert_request.class);
	
	@SuppressWarnings("static-access")
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode 	reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 	reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 	reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 		trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		    
		String g_conf_log_dir   = SmartConfig.getString("kcp.cert.logdir");
	    String g_conf_gw_url 	= SmartConfig.getString("kcp.cert.url");
	    String g_conf_gw_port   = SmartConfig.getString("kcp.cert.port");

		String g_conf_site_cd   = SmartConfig.getString("kcp.cert.sitecd");
	    String g_conf_site_key  = SmartConfig.getString("kcp.cert.sitekey");
	    String cust_ip			= "";
	    int    g_conf_tx_mode   = 0;
	    
	    ObjectNode	responseNode	= JsonNodeFactory.instance.objectNode();
	    
		try {
			InetAddress local = InetAddress.getLocalHost();
			cust_ip			= local.getHostAddress(); 
		} catch (UnknownHostException e1) {
			logger.error(e1.getMessage(), e1);
		}
	    
		try {
			long	start			= System.currentTimeMillis();
			String 	req_ordr_idxx	= reqHeaderNode.findPath("ordr_idxx").getValueAsText();
			String 	ordr_idxx 		= makeOrderNo();
			
			if(req_ordr_idxx != null) {//otp 재시도를 위함
				ordr_idxx		= req_ordr_idxx; 
			}
			
			String user_name		= reqBodyNode.findPath("user_name").getValueAsText();
			String phone_no			= reqBodyNode.findPath("phone_no").getValueAsText();
			String comm_id			= reqBodyNode.findPath("comm_id").getValueAsText();
			String birth_day		= reqBodyNode.findPath("birth_day").getValueAsText();
			String sex_code			= reqBodyNode.findPath("sex_code").getValueAsText();
			String local_code		= reqBodyNode.findPath("local_code").getValueAsText();
			String kcp_web_yn		= "N";
			String cp_sms_msg		= SmartConfig.getString("kcp.cert.msg");
			String cp_callback		= SmartConfig.getString("kcp.cert.cpcallback");
			String per_cert_no		= "";
			String per_cert_no_mvno = "";

			CT_CLI cc = new CT_CLI();
			String ENC_KEY         	= "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0";
			String phone_no_hash   	= cc.makeHashData(ENC_KEY, phone_no );
			String birth_day_hash  	= cc.makeHashData(ENC_KEY, birth_day );
			String user_name_hash  	= cc.makeHashData(ENC_KEY, user_name );
			String local_code_hash 	= cc.makeHashData(ENC_KEY, local_code );
			String sex_code_hash   	= cc.makeHashData(ENC_KEY, sex_code );

			//otp 
			/* = -------------------------------------------------------------------------- = */
			String tran_cd       = "00402200";                                                // 거래구분코드
			/* = -------------------------------------------------------------------------- = */
			String res_cd        = "" ;                                                 // 결과코드
			String res_msg       = "" ;                                                 // 결과메시지
			String iden_only_yn  = "" ;                                                 // 실명확인 ONLY 설정여부
			String CI            = "" ;                                                 // CI값
			String DI            = "" ;                                                 // DI값
			String CI_URL        = "" ;                                                 // URL인코딩된 CI값
			String DI_URL        = "" ;                                                 // URL인코딩된 DI값
			String safe_guard_yn = "" ;                                                 // 번호보호 서비스 가입여부
			String usim_otp_yn   = "" ;                                                 // 인증보호 서비스 가입여부
			String sms_snd_yn    = "" ;                                                 // SMS 발송여부

			String mvno_name     = "" ;                                                 // 알뜰폰 사업자명
			String res_comm_id   = "" ;                                                 // 조회 결과 통신사코드
			String res_phone_no  = "" ;                                                 // 조회 결과 휴대폰번호

			if("KTM".equals(comm_id) || "LGM".equals(comm_id)){
				J_PP_CLI_N 	c_PayPlus3 			= new J_PP_CLI_N();
				c_PayPlus3.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
				c_PayPlus3.mf_init_set();				

				int 		payx_data_set3 		= c_PayPlus3.mf_add_set("payx_data");
				int 		common_data_set3 	= c_PayPlus3.mf_add_set("common");

				c_PayPlus3.mf_set_us( common_data_set3, "amount" , "0"); //고정
				c_PayPlus3.mf_set_us( common_data_set3, "cust_ip",  cust_ip);
				c_PayPlus3.mf_add_rs( payx_data_set3, common_data_set3 );

				// 주문 정보
				int 		ordr_data_set3 		= c_PayPlus3.mf_add_set( "ordr_data" );
				String 		order_idxx_mvno 	= makeOrderNo_MVNO();
				c_PayPlus3.mf_set_us( ordr_data_set3, "ordr_idxx", order_idxx_mvno );

				// 인증 정보
				int 		cert_data_set 		= c_PayPlus3.mf_add_set("cert");

				c_PayPlus3.mf_set_us( cert_data_set, "cert_type"       ,  "01"               ); //고정
				c_PayPlus3.mf_set_us( cert_data_set, "tx_type"         ,  "2400"             ); //고정
				c_PayPlus3.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn         ); //표준인증창 사용여부(Default:N)    
				c_PayPlus3.mf_set_us( cert_data_set, "phone_no"        ,  phone_no           );
				c_PayPlus3.mf_set_us( cert_data_set, "comm_id"         ,  comm_id            );
				c_PayPlus3.mf_set_us( cert_data_set, "birth_day"       ,  birth_day          );
				c_PayPlus3.mf_set_us( cert_data_set, "user_name"       ,  user_name          );
				c_PayPlus3.mf_set_us( cert_data_set, "local_code"      ,  local_code         );
				c_PayPlus3.mf_set_us( cert_data_set, "sex_code"        ,  sex_code           );

				c_PayPlus3.mf_set_us( cert_data_set, "phone_no_hash"   ,  phone_no_hash      );
				c_PayPlus3.mf_set_us( cert_data_set, "birth_day_hash"  ,  birth_day_hash     );
				c_PayPlus3.mf_set_us( cert_data_set, "user_name_hash"  ,  user_name_hash     );
				c_PayPlus3.mf_set_us( cert_data_set, "local_code_hash" ,  local_code_hash    );
				c_PayPlus3.mf_set_us( cert_data_set, "sex_code_hash"   ,  sex_code_hash      );

				c_PayPlus3.mf_add_rs( payx_data_set3, cert_data_set );

				if ( tran_cd.length() > 0 )
				{
					c_PayPlus3.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, order_idxx_mvno, "3", "1" );
				}
				else
				{
					c_PayPlus3.m_res_cd  = "9562";
					c_PayPlus3.m_res_msg = "연동 오류";
				}
				res_cd  = c_PayPlus3.m_res_cd;                      // 결과 코드
				res_msg = c_PayPlus3.m_res_msg;                     // 결과 메시지
				//			    System.out.println("결과:" + res_msg);
				if ( res_cd.equals( "0000" ) )
				{
					per_cert_no_mvno   = c_PayPlus3.mf_get_res( "per_cert_no"   ); //MVNO사업자조회 거래번호(해당 거래번호를 알뜰폰사업자 본인확인 시 사용)
					mvno_name     = c_PayPlus3.mf_get_res( "mvno_name"     ); //MVNO사업자명
					res_phone_no  = c_PayPlus3.mf_get_res( "phone_no"      ); 
					res_comm_id   = c_PayPlus3.mf_get_res( "comm_id"       );			    	
				}
				else
				{		    	
					responseNode.put("res_cd", res_cd);
					responseNode.put("res_msg", res_msg);
					responseNode.put("comm_id", comm_id);
					responseNode.put("ordr_idxx", ordr_idxx);
					
					NfcCommonResponse response = new NfcCommonResponse( reqHeaderNode, responseNode );
						
					JsonAdaptorObject resObj = new JsonAdaptorObject();

					return makeResponse(resObj, response.getNfcCommonResponse());
				}
				per_cert_no = per_cert_no_mvno;
			}
			//kcp
			J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

			c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
			c_PayPlus.mf_init_set();

			/* = -------------------------------------------------------------------------- = */
			/* =   03-1.                                                        = */
			/* = -------------------------------------------------------------------------- = */

			int payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
			int common_data_set = c_PayPlus.mf_add_set( "common"    );

			c_PayPlus.mf_set_us( common_data_set, "amount" , "0"       ); //고정
			c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip  );

			c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

			int ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );

			c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );

			// 
			int cert_data_set   = c_PayPlus.mf_add_set( "cert"      );

			c_PayPlus.mf_set_us( cert_data_set, "tx_type"         ,  "2100"             ); //고정
			c_PayPlus.mf_set_us( cert_data_set, "cert_type"       ,  "01"               ); //고정   
			c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"      ,  kcp_web_yn         ); //표준인증창 사용여부(허브형 Default:N)  
			c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"     ,  per_cert_no        ); //MVNO 사업자조회 완료 후 리턴받은 거래번호(알뜰폰사업자만 해당)
			c_PayPlus.mf_set_us( cert_data_set, "phone_no"        ,  phone_no           ); //휴대폰번호
			c_PayPlus.mf_set_us( cert_data_set, "comm_id"         ,  comm_id            ); //이통사코드
			c_PayPlus.mf_set_us( cert_data_set, "birth_day"       ,  birth_day          ); //생년월일
			c_PayPlus.mf_set_us( cert_data_set, "user_name"       ,  user_name          ); //명의자명
			c_PayPlus.mf_set_us( cert_data_set, "local_code"      ,  local_code         ); //내외국인코드
			c_PayPlus.mf_set_us( cert_data_set, "sex_code"        ,  sex_code           ); //성별코드

			c_PayPlus.mf_set_us( cert_data_set, "cp_sms_msg"      ,  cp_sms_msg         ); //CP지정 메세지
			c_PayPlus.mf_set_us( cert_data_set, "cp_callback"     ,  cp_callback        ); //CP지정 콜백번호

			c_PayPlus.mf_set_us( cert_data_set, "phone_no_hash"   ,  phone_no_hash      ); //HASH 데이터
			c_PayPlus.mf_set_us( cert_data_set, "birth_day_hash"  ,  birth_day_hash     ); //HASH 데이터
			c_PayPlus.mf_set_us( cert_data_set, "user_name_hash"  ,  user_name_hash     ); //HASH 데이터
			c_PayPlus.mf_set_us( cert_data_set, "local_code_hash" ,  local_code_hash    ); //HASH 데이터
			c_PayPlus.mf_set_us( cert_data_set, "sex_code_hash"   ,  sex_code_hash      ); //HASH 데이터
			c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

			/* ============================================================================== */
			/* =   모듈 통신
		    /* ------------------------------------------------------------------------------ */
			if ( tran_cd.length() > 0 )
			{
				c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "1" );
			}
			else
			{
				c_PayPlus.m_res_cd  = "9562";
				c_PayPlus.m_res_msg = "연동 오류";
			}
			
			res_cd  = c_PayPlus.m_res_cd;                      // 결과코드
			res_msg = c_PayPlus.m_res_msg;                     // 결과 메세지
			
			long end = System.currentTimeMillis();	
			
			if ( res_cd.equals( "0000" ) )
			{
				/* = -------------------------------------------------------------------------- = */
				/* =   04-1. 인증 결과처리                                                     = */
				/* = -------------------------------------------------------------------------- = */
				per_cert_no   = c_PayPlus.mf_get_res( "per_cert_no"   ); //본인확인 거래번호
				iden_only_yn  = c_PayPlus.mf_get_res( "iden_only_yn"  ); //실명확인 only 설정여부(Default:N)
				safe_guard_yn = c_PayPlus.mf_get_res( "safe_guard_yn" ); //번호보호 서비스 가입 여부
				usim_otp_yn   = c_PayPlus.mf_get_res( "usim_otp_yn"   ); //인증보호 서비스 가입 여부
				sms_snd_yn    = c_PayPlus.mf_get_res( "sms_snd_yn"    ); //SMS 발송여부

				// 실명확인 ONLY 설정여부 Y일때 추가리턴정보
				if ( iden_only_yn.equals( "Y" ) )
				{
					CI            = c_PayPlus.mf_get_res( "CI"            );
					DI            = c_PayPlus.mf_get_res( "DI"            );
					CI_URL        = c_PayPlus.mf_get_res( "CI_URL"        );
					DI_URL        = c_PayPlus.mf_get_res( "DI_URL"        );
				}

				J_PP_CLI_N c_PayPlus2 = new J_PP_CLI_N();

				c_PayPlus2.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
				c_PayPlus2.mf_init_set();

				int payx_data_set2;
				int common_data_set2;

				//공통정보
				payx_data_set2   = c_PayPlus2.mf_add_set( "payx_data" );
				common_data_set2 = c_PayPlus2.mf_add_set( "common"    );

				c_PayPlus2.mf_set_us( common_data_set2, "amount" , "0"          ); //고정
				c_PayPlus2.mf_set_us( common_data_set2, "cust_ip",  cust_ip     ); 

				c_PayPlus2.mf_add_rs( payx_data_set2, common_data_set2 );

				//주문 정보
				int ordr_data_set2;

				ordr_data_set2 = c_PayPlus2.mf_add_set( "ordr_data" );

				c_PayPlus2.mf_set_us( ordr_data_set2, "ordr_idxx", ordr_idxx );

				//인증정보
				int cert_data_set2;

				cert_data_set2 = c_PayPlus2.mf_add_set( "cert" );
				//		         System.out.println("per_cert_no4:" + per_cert_no);
				c_PayPlus2.mf_set_us( cert_data_set2, "tx_type"         ,  "2200"          ); //고정
				c_PayPlus2.mf_set_us( cert_data_set2, "kcp_web_yn"      ,  kcp_web_yn      ); 
				c_PayPlus2.mf_set_us( cert_data_set2, "per_cert_no"     ,  per_cert_no     ); 
				c_PayPlus2.mf_set_us( cert_data_set2, "comm_id"         ,  comm_id         ); 
				c_PayPlus2.mf_set_us( cert_data_set2, "cp_sms_msg"      ,  cp_sms_msg      );
				c_PayPlus2.mf_set_us( cert_data_set2, "cp_callback"     ,  cp_callback     );

				c_PayPlus2.mf_add_rs( payx_data_set2, cert_data_set2 );

				if ( tran_cd.length() > 0 )
				{
					c_PayPlus2.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, "3", "1" );
				}
				else
				{
					c_PayPlus2.m_res_cd  = "9562";
					c_PayPlus2.m_res_msg = "연동 오류";
				}
				res_cd    = c_PayPlus2.m_res_cd; //결과코드
				res_msg   = c_PayPlus2.m_res_msg;//결과메세지
				
				end = System.currentTimeMillis();
				
				if ( res_cd.equals( "0000" ) )
				{
					safe_guard_yn = c_PayPlus2.mf_get_res( "safe_guard_yn" );
					usim_otp_yn   = c_PayPlus2.mf_get_res( "usim_otp_yn"   );
					sms_snd_yn    = c_PayPlus2.mf_get_res( "sms_snd_yn"    );

					responseNode.put("res_cd", res_cd);
					responseNode.put("res_msg", res_msg);
					responseNode.put("ordr_idxx", ordr_idxx);
					responseNode.put("kcp_web_yn", kcp_web_yn);
					responseNode.put("per_cert_no", per_cert_no);		         
					responseNode.put("comm_id", comm_id);
					responseNode.put("cp_sms_msg", cp_sms_msg);		         
					responseNode.put("cp_callback", cp_callback);
					responseNode.put("iden_only_yn", iden_only_yn);
					responseNode.put("safe_guard_yn", safe_guard_yn);
					responseNode.put("usim_otp_yn", usim_otp_yn);
					responseNode.put("sms_snd_yn", sms_snd_yn);
					
					
					NfcCommonResponse 	response 	= new NfcCommonResponse( reqHeaderNode, responseNode );
					
					return ResponseUtil.makeResponse(obj, response.getNfcCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());		        
				}    // End of [res_cd = "0000"]
				else //OTP 요청 실패시
				{		    	
					responseNode.put("res_cd", res_cd);
					responseNode.put("res_msg", res_msg);
					responseNode.put("comm_id", comm_id);
					responseNode.put("ordr_idxx", ordr_idxx);
					responseNode.put("per_cert_no", per_cert_no);
					
		            NfcCommonResponse response = new NfcCommonResponse( reqHeaderNode, responseNode );
		            return ResponseUtil.makeResponse(obj, response.getNfcCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
				}
				
			} else //본인인증 실패시
			{	
				responseNode.put("res_cd", res_cd);
				responseNode.put("res_msg", res_msg);
				responseNode.put("comm_id", comm_id);
				responseNode.put("ordr_idxx", ordr_idxx);
				
				if(checkMvno(comm_id)) {						
					responseNode.put("per_cert_no", per_cert_no);						
				}
				
				NfcCommonResponse response = new NfcCommonResponse( reqHeaderNode, responseNode );
				return ResponseUtil.makeResponse(obj, response.getNfcCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
			}

		} catch( Exception e ) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL1", "인증번호 요청중 오류가 발생하였습니다.", trCode, reqBodyNode, e, this.getClass().getName());
		}
		
	}
	
	public String makeOrderNo() {
		
		Date today = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String orderDate = sdf.format(today);
		String orderNo = "COWAY" + orderDate;
		
		return orderNo;
	}
	public String makeOrderNo_MVNO() {
		
		Date today = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String orderDate = sdf.format(today);
		String orderNo = "MVNO" + orderDate;
		
		return orderNo;
	}
	public String changeEUCToUTF( String str ) {
		String changeStr = "";
		try {
			byte[] euckrStringBuffer = str.getBytes("utf-8");
			String decodedFromEucKr = new String(euckrStringBuffer, "utf-8");
			changeStr = decodedFromEucKr;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return changeStr;
	}
	public boolean checkMvno( String mvnoValue ) {
		boolean result = false;
		if(mvnoValue.equals( "KTM" ) || mvnoValue.equals( "LGM" )){
			result = true;
			return result;
		}
		return result;
	}
}
