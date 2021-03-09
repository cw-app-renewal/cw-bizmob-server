package adapter.cert;

import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.kcp.J_PP_CLI_N;
import com.mcnc.bizmob.adapter.AbstractTemplateAdapter;
import com.mcnc.smart.common.config.SmartConfig;
import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.hybrid.adapter.api.Adapter;
import com.mcnc.smart.hybrid.adapter.api.IAdapterJob;
import com.mcnc.smart.hybrid.common.code.Codes;
import com.mcnc.smart.hybrid.common.server.JsonAdaptorObject;

import adapter.common.NfcCommonResponse;
import common.ResponseUtil;


/**
 * 
 * 2017.08.02
 * OTP 인증 요청 전문
 * 
 * **/

@Adapter( trcode = {"CGW310"} )
public class CGW310_ADT_OTP_request extends AbstractTemplateAdapter implements IAdapterJob{

	private ILogger logger = LoggerService.getLogger(CGW300_ADT_Cert_request.class);
	
	@Override
	public JsonAdaptorObject onProcess(JsonAdaptorObject obj) {
		
		JsonNode 	reqRootNode 		= obj.get(JsonAdaptorObject.TYPE.REQUEST);
		JsonNode 	reqHeaderNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_HEADER);
		JsonNode 	reqBodyNode 		= reqRootNode.findValue(Codes._JSON_MESSAGE_BODY);
		String 		trCode 				= reqHeaderNode.findPath("trcode").getValueAsText();
		    
		String 		g_conf_log_dir   	= SmartConfig.getString("kcp.cert.logdir");
	    String 		g_conf_gw_url 		= SmartConfig.getString("kcp.cert.url");
	    String 		g_conf_gw_port   	= SmartConfig.getString("kcp.cert.port");
	    int    		g_conf_tx_mode   	= 0;
			
		String 		g_conf_site_cd   	= SmartConfig.getString("kcp.cert.sitecd");
	    String 		g_conf_site_key  	= SmartConfig.getString("kcp.cert.sitekey");
	    String 		cust_ip				= "";
	    
		try {
			InetAddress local = InetAddress.getLocalHost();
			cust_ip			= local.getHostAddress(); 
		} catch (UnknownHostException e1) {
			logger.error(e1.getMessage(), e1);
		}
	    
	    /* ============================================================================== */
		try {
			long	start			= System.currentTimeMillis();			
			//요청 정보 확인
			/* = -------------------------------------------------------------------------- = */
		    String ordr_idxx        = reqBodyNode.findPath( "ordr_idxx"       ).getValueAsText() ;  // 주문번호
			    /* = -------------------------------------------------------------------------- = */
		    String kcp_web_yn       = reqBodyNode.findPath( "kcp_web_yn"      ).getValueAsText() ;  // KCP 표준인증창 사용여부
		    String per_cert_no      = reqBodyNode.findPath( "per_cert_no"     ).getValueAsText() ;  // 본인확인 거래번호
		    String comm_id          = reqBodyNode.findPath( "comm_id"         ).getValueAsText() ;  // 이통사 코드
			
		    String otp_no          = reqBodyNode.findPath( "otp_no"         ).getValueAsText() ;  // 본인확인 인증번호
		    String ad1_agree_yn    = reqBodyNode.findPath( "ad1_agree_yn"   ).getValueAsText() ;  // 광고#1 선택 동의여부
		    /* = -------------------------------------------------------------------------- = */
		    String tran_cd			= "" ;                                                  // 거래구분코드
		    /* = -------------------------------------------------------------------------- = */
		    String res_cd      		= "" ;                                                  // 결과코드
		    String res_msg     		= "" ;                                                  // 결과메시지
		    /* = -------------------------------------------------------------------------- = */
		    String CI          		= "" ;                                                  // CI
		    String DI          		= "" ;                                                  // DI
		    String CI_URL      		= "" ;                                                  // CI_URL
		    String DI_URL      		= "" ;                                                  // DI_URL
			
		    /* ============================================================================== */
		    /* =   02. 인스턴스 생성 및 초기화                                              = */
		    /* = -------------------------------------------------------------------------- = */
		    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();
		    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
		    c_PayPlus.mf_init_set();
		    /* ============================================================================== */


		    /* ============================================================================== */
		    /* =   03. 처리 요청 정보 설정, 실행                                            = */
		    /* = -------------------------------------------------------------------------- = */

		    /* = -------------------------------------------------------------------------- = */
		    /* =   03-1. 인증 요청                                                          = */
		    /* = -------------------------------------------------------------------------- = */
		    tran_cd = "00402200";

		    int payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
		    int common_data_set = c_PayPlus.mf_add_set( "common"    );

		    c_PayPlus.mf_set_us( common_data_set, "amount" , "0"          ); //고정
		    c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip     ); //옵션
		    c_PayPlus.mf_add_rs( payx_data_set, common_data_set );

		    // 인증 정보
		    int cert_data_set   = c_PayPlus.mf_add_set( "cert"      );

		    c_PayPlus.mf_set_us( cert_data_set, "tx_type"      ,  "2300"       ); //고정
		    c_PayPlus.mf_set_us( cert_data_set, "kcp_web_yn"   ,  kcp_web_yn   );
		    c_PayPlus.mf_set_us( cert_data_set, "per_cert_no"  ,  per_cert_no  );
		    c_PayPlus.mf_set_us( cert_data_set, "comm_id"      ,  comm_id      );
		    c_PayPlus.mf_set_us( cert_data_set, "otp_no"       ,  otp_no       ); //SMS 인증번호
		    c_PayPlus.mf_set_us( cert_data_set, "ad1_agree_yn" ,  ad1_agree_yn );

		    c_PayPlus.mf_add_rs( payx_data_set, cert_data_set );

		    /* ============================================================================== */
		    /* =   03-3. 실행                                                               = */
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
		    res_cd  = c_PayPlus.m_res_cd;                      // 결과 코드
		    res_msg = c_PayPlus.m_res_msg;                     // 결과 메시지
		    
		    ObjectNode	responseNode	= JsonNodeFactory.instance.objectNode();
		    long 		end 			= System.currentTimeMillis();	
		    /* ============================================================================== */


		    /* ============================================================================== */
		    /* =   04. 인증 결과 처리                                                       = */
		    /* = -------------------------------------------------------------------------- = */
		    if ( res_cd.equals( "0000" ) )
		    {
		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-1. 인증 결과 처리                                                     = */
		    /* = -------------------------------------------------------------------------- = */
		        per_cert_no = c_PayPlus.mf_get_res( "per_cert_no" );
		        CI          = c_PayPlus.mf_get_res( "CI"          );
		        DI          = c_PayPlus.mf_get_res( "DI"          );
		        CI_URL      = URLDecoder.decode(c_PayPlus.mf_get_res( "CI_URL" ));
		        DI_URL      = URLDecoder.decode(c_PayPlus.mf_get_res( "DI_URL" ));
		        
		        responseNode.put("res_cd", res_cd);
		        responseNode.put("res_msg", res_msg);
		        responseNode.put("CI", CI);
		        responseNode.put("DI", DI);
	            
		        NfcCommonResponse response = new NfcCommonResponse( reqHeaderNode, responseNode );
		        return ResponseUtil.makeResponse(obj, response.getNfcCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
		        
		        
		    }    // End of [res_cd = "0000"]
		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-2. 인증 실패를 업체 자체적으로 DB 처리 작업하시는 부분입니다.         = */
		    /* = -------------------------------------------------------------------------- = */
		    else
		    {
		    	responseNode.put("res_cd", res_cd);
		    	responseNode.put("res_msg", res_msg);
	            
		    	NfcCommonResponse response = new NfcCommonResponse( reqHeaderNode, responseNode );
				return ResponseUtil.makeResponse(obj, response.getNfcCommonResponse(), trCode, (end - start), reqBodyNode,this.getClass().getName());
		    }
			
		} catch( Exception e ) {
			logger.error(e.getMessage(), e);
			return ResponseUtil.makeFailResponse(obj, "IMPL1", "인증번호 요청중 오류가 발생하였습니다.", trCode, reqBodyNode, e, this.getClass().getName());
		}
		
	}

}
