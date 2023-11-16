package common.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import adapter.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

import com.mcnc.smart.hybrid.server.web.io.Downloader;

import adapter.sync.CGS000_ADT_SyncZipDownloader;

/*
 * http://127.0.0.1:8080/bizmob/cowaydownload/syncDatabase/1?mode=1&file_name=WD_COM.zip&file_path=SYNC/CSDR/CSDR
 */

@Controller
@RequestMapping("cowaydownload")
//@Deprecated
public class CowayDownloadController {
	
	private static final Logger logger = LoggerFactory.getLogger(CowayDownloadController.class);
		
	@Autowired
	private CGS000_ADT_SyncZipDownloader syncZipDownloader;
	@Autowired
	private CGR101_ADT_InstallImageDownloader installImageDownloader;
	@Autowired
	private CGR102_ADT_AddressImageDownloader addressImageDownloader;
	@Autowired
	private CGR103_ADT_MemoImageDownloader memoImageDownloader;
	@Autowired
	private CGR104_ADT_GoodsImageDownloader goodsImageDownloader;
	@Autowired
	private CGR105_ADT_PartsImageDownloader partsImageDownloader;
	@Autowired
	private CGR106_ADT_MediaDownloader mediaDownloader;
	
	@Autowired
	private CGR113_ADT_PlImageDownloader plImageDownloader;

	@Autowired
	private CGR114_ADT_FireImageDownloader fireImageDownloader;
	
	@Autowired
	private CGR115_ADT_QAImageDownloader qaImageDownloader;
	
	@Autowired
	private CGR116_ADT_BbsSmtImageDownloader bbsSmtImageDownloader;
	
	@Autowired
	private CGR117_ADT_AfterImageDownloader afterImageDownloader;
	
	@Autowired
	private CGR118_ADT_NoinstImageDownloader noinstImageDownloader;

	@Autowired
	private CGR119_ADT_NanImageDownloader nanImageDownloader;

	@Autowired
	private CGR120_ADT_SurveyImageDownloader surveyImageDownloader;
	
	@Autowired
	private CGR121_ADT_InstallReturnImageDownloader installReturnImageDownloader;
	
	@Autowired
	private CGR122_ADT_Noinst2ImageDownloader noinst2ImageDownloader;

	@Autowired
	private CGR123_ADT_Nan1ImageDownloader nan1ImageDownloader;
	@Autowired
	private CGR124_ADT_Nan2ImageDownloader nan2ImageDownloader;
	@Autowired
	private CGR125_ADT_Nan3ImageDownloader nan3ImageDownloader;

	@Autowired
	private CGR126_ADT_CowayMMSImageDownloader cowayMMSImageDownloader;
	
	@Autowired
	private CGR127_ADT_AirImageDownloader airImageDownloader;
	@Autowired
	private CGR128_ADT_HomeplImageDownloader homeplImageDownloader;

	@Autowired
	private CGR129_ADT_PlhcImageDownloader	plhcImageDownloader;

	@Autowired
	private CGR130_ADT_CoreImageDownloader	coreImageDownloader;

	@Autowired
	private CGR131_ADT_FeederImageDownloader	feederImageDownloader;

	@Autowired
	private CGR132_ADT_KINDImageDownloader	kindImageDownloader;
	
	@Autowired
	private CGR133_ADT_EtcOrderImageDownloader	etcOrderImageDownloader;
	
	@Autowired
	private CGR134_ADT_Nan4ImageDownloader	nan4ImageDownloader;
	
	@Autowired
	private CGR135_ADT_Nan5ImageDownloader	nan5ImageDownloader;
	
	@Autowired
	private CGR136_ADT_Nan6ImageDownloader	nan6ImageDownloader;
	
	@Autowired
	private CGR137_ADT_townGasCloseImageDownloader	townGasCloseImageDownloader;
	
	@Autowired
	private CGR138_ADT_csqImageDownloader	csqImageDownloader;
	
	@Autowired
	private CGR139_ADT_PlrImageDownloader	plrImageDownloader;
	
	@Autowired
	private CGR140_ADT_PlrMovImageDownloader	plrMovImageDownloader;
	
	@Autowired
	private CGR141_ADT_WatermapImageDownloader	wmapImageDownloader;
	
	@Autowired
	private CGR142_ADT_UniformImageDownloader	unifImageDownloader;
	
	@Autowired
	private CGR143_ADT_plMovDownloader	plMovDownloader;
	
	@Autowired
	private CGR144_ADT_firmMovDownloader	firmMovDownloader;
	
	@Autowired
	private CGR145_ADT_opinImageDownloader	opinImageDownloader;
	
	@Autowired
	private CGR148_ADT_mentImgDownloader	mentImgDownloader;
	
	@Autowired
	private CGR146_ADT_LadderImageDownloader	ladderImageDownloader;
	
	@Autowired
	private CGR147_ADT_BillImageDownloader	billImageDownloader;

	@Autowired
	private CGR149_ADT_Nan7ImageDownloader	nan7ImageDownloader;
	
	@Autowired
	private CGR150_ADT_Nan8ImageDownloader	nan8ImageDownloader;
	
	@Autowired
	private CGR151_ADT_SpManualDownloader spManualDownloader;
	
	@Autowired
	private CGR152_ADT_HybridTopperImageDownloader hybridTopperImageDownloader;
	
	@Autowired
	private CGR153_ADT_QMImageDownloader qmImageDownloader;
	
	@Autowired
	private CGR154_ADT_QMMovDownloader qmMovDownloader;
	
	@Autowired
	private CGR155_ADT_RvImageDownloader rvImageDownloader;
	
	@Autowired
	private CGR156_ADT_cPlrMovDownloader cPlrMovDownloader;
	
	@Autowired
	private CGR157_ADT_cPlMovDownloader cPlMovDownloader;
	
	@Autowired
	private CGR158_ADT_asSkillDownloader asSkillDownloader;
	
	@Autowired
	private CGR159_ADT_Nan9ImageDownloader	nan9ImageDownloader;
	
	@Autowired
	private CGR160_ADT_QabmMovDownloader	qabmMovDownloader;
	
	@Autowired
	private CGR161_ADT_HplbImageDownloader	hplbImageDownloader;

	@Autowired
	private CGR163_ADT_WithServiceImageDownloader withServiceImageDownloader;
	
	/**
	 * 
	 * noInstImage 단말기 호출 경로 : cowaydownload/noinstImage/1?mode=1&file_name={file_name}&order_no={ORDER_NO}&img_seq={img_seq}&job_dt={JOB_DT}&job_tp={JOB_TP}&job_seq={JOB_SEQ}
	 * @param request
	 * @param response
	 * @param target
	 * @param fileName
	 * @param mode
	 * @throws Exception
	 */
	@RequestMapping(value = "/{target}/**", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseStatus(HttpStatus.OK)
	public void download(HttpServletRequest request, HttpServletResponse response,
						@PathVariable("target") final String target,
						@RequestParam("file_name") final String fileName, 
						@RequestParam("mode") final int mode) throws Exception {
		
		int fileStartPos = 0;
        String uid = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		logger.info(String.format("===== Coway Download Controller Start::download() of %s : uid : %s, file : %s", target, uid, fileName));
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("index", fileStartPos);
		params.put("HttpServletRequest", request);
		params.put("HttpServletResponse", response);
		
		Downloader downloader = null;
		if ( target.equals("syncZip") == true ) {
			downloader = syncZipDownloader;

		} else if ( target.equals("installImage") == true ) {
			downloader = installImageDownloader;

		} else if ( target.equals("addressImage") == true ) {
			downloader = addressImageDownloader;

		} else if ( target.equals("memoImage") == true ) {
			downloader = memoImageDownloader;

		} else if ( (target.equals("goodsImage")) 
				|| (target.equalsIgnoreCase("goodsImage2"))
				|| (target.equalsIgnoreCase("goodsImage3"))
				|| (target.equalsIgnoreCase("goodsImage4"))
				|| (target.equalsIgnoreCase("goodsImage5"))
				|| (target.equalsIgnoreCase("goodsImage6")) ) {

			downloader = goodsImageDownloader;

		} else if ( target.equals("partsImage") == true ) {
			downloader = partsImageDownloader;
		
		} else if ( target.equals("media") == true ) {
			downloader = mediaDownloader;
		
		} else if ( target.equalsIgnoreCase("plImage") ) { 
			downloader = plImageDownloader;
		
		} else if ( target.equalsIgnoreCase("fireImage") ) {
			downloader = fireImageDownloader;
		
		} else if ( target.equalsIgnoreCase("qaImage") ) {
			downloader = qaImageDownloader;
		
		} else if ( target.equalsIgnoreCase("bbsSmtImageDownloader")) {
			downloader = bbsSmtImageDownloader;
		
		} else if ( target.equalsIgnoreCase("afterImage") ) { 
			downloader = afterImageDownloader;
			
		} else if ( target.equalsIgnoreCase("noinstImage") ) { 
			downloader = noinstImageDownloader;

		} else if ( target.equalsIgnoreCase("nanImage") ) { 
			downloader = nanImageDownloader;

		} else if ( target.equalsIgnoreCase("surveyImage") ) {
			downloader = surveyImageDownloader;

		} else if ( target.equalsIgnoreCase("installReturnImage") ) { 
			downloader = installReturnImageDownloader;
			
		} else if ( target.equalsIgnoreCase("noinst2Image") ) { 
			downloader = noinst2ImageDownloader;
			
		} else if ( target.equalsIgnoreCase("nan1Image") ) { 
			downloader = nan1ImageDownloader;

		} else if ( target.equalsIgnoreCase("nan2Image") ) { 
			downloader = nan2ImageDownloader;

		} else if ( target.equalsIgnoreCase("nan3Image") ) { 
			downloader = nan3ImageDownloader;
			
		} else if ( target.equalsIgnoreCase("coway_mms") ) { 
			downloader = cowayMMSImageDownloader;
			
		} else if ( target.equalsIgnoreCase("home_air") ) { 
			downloader = airImageDownloader;
			
		} else if ( target.equalsIgnoreCase("home_pl") ) { 
			downloader = homeplImageDownloader;
		
		} else if ( target.equalsIgnoreCase("hcpl") ) { 
			downloader = plhcImageDownloader;

		} else if ( target.equalsIgnoreCase("codyRecImage") ) { 
			downloader = coreImageDownloader;
			
		} else if ( target.equalsIgnoreCase("feederScrew") ) { 
			downloader = feederImageDownloader;
			
		} else if ( target.equalsIgnoreCase("kindImage") ) {
			downloader = kindImageDownloader;
		
		} else if ( target.equalsIgnoreCase("etcOrder") ) { 
			downloader = etcOrderImageDownloader;
			
		} else if ( target.equalsIgnoreCase("nan4Image") ) { 
			downloader = nan4ImageDownloader;
			
		} else if ( target.equalsIgnoreCase("nan5Image") ) { 
			downloader = nan5ImageDownloader;
			
		} else if ( target.equalsIgnoreCase("nan6Image") ) { 
			downloader = nan6ImageDownloader;
			
		} else if ( target.equalsIgnoreCase("townGasClose") ) { 
			downloader = townGasCloseImageDownloader;
			
		} else if ( target.equalsIgnoreCase("csqImage") ) { 
			downloader = csqImageDownloader;
			
		} else if ( target.equalsIgnoreCase("plReportImg") ) { 
			downloader = plrImageDownloader;
			
		} else if ( target.equalsIgnoreCase("plReportMov") ) { 
			downloader = plrMovImageDownloader;
			
		} else if ( target.equalsIgnoreCase("waterImg") ) { 
			downloader = wmapImageDownloader;
			
		} else if ( target.equalsIgnoreCase("uniformImage") ) { 
			downloader = unifImageDownloader;
			
		} else if ( target.equalsIgnoreCase("plMov") ) { 
			downloader = plMovDownloader;
			
		} else if ( target.equalsIgnoreCase("firmMov") ) { 
			downloader = firmMovDownloader;
			
		} else if ( target.equalsIgnoreCase("opinImage") ) { 
			downloader = opinImageDownloader;
		
		// 2018-01-15, 이재성 추가, 멘토멘티 이미지 추가 업로드/다운로드
		} else if ( target.equalsIgnoreCase("mentImg") ) { 
			downloader = mentImgDownloader;
		
		} else if ( target.equalsIgnoreCase("ladderImg") ) { 
			downloader = ladderImageDownloader;
			
		} else if ( target.equalsIgnoreCase("billImg") ) { 
			downloader = billImageDownloader;
			
		} else if ( target.equalsIgnoreCase("nan7Image") ) { 
			downloader = nan7ImageDownloader;
			
		} else if ( target.equalsIgnoreCase("nan8Image") ) { 
			downloader = nan8ImageDownloader;
		} else if ( target.equalsIgnoreCase("nan9Image") ) { 
			downloader = nan9ImageDownloader;
			
		} else if ( target.equalsIgnoreCase("plBill") ) { 
			downloader = hplbImageDownloader;
			
		} else if ( target.equalsIgnoreCase("spManual") ) { 
			downloader = spManualDownloader;
			
		} else if ( target.equalsIgnoreCase("HybridTopperImg") ) { 
			downloader = hybridTopperImageDownloader;
			
		} else if ( target.equalsIgnoreCase("qmImage") ) { 
			downloader = qmImageDownloader;
			
		} else if ( target.equalsIgnoreCase("qmMov") ) { 
			downloader = qmMovDownloader;
		} else if ( target.equalsIgnoreCase("rvImage") ) { 
			downloader = rvImageDownloader;
		
		} else if ( target.equalsIgnoreCase("cPlReportMov") ) {
		
			downloader = cPlrMovDownloader;
		} else if ( target.equalsIgnoreCase("cPlMov") ) { 
			
			downloader = cPlMovDownloader;
		} else if ( target.equalsIgnoreCase("asSkill") ) { 
				
				downloader = asSkillDownloader;
		} else if ( target.equalsIgnoreCase("noinstMov") ) { 
					
					downloader = qabmMovDownloader;
		} else if ( target.equalsIgnoreCase("with") ) {
			downloader = withServiceImageDownloader;
		} else {
			throw new Exception("downloader target 경로가 잘못되었습니다.");
		}
		downloader.download(target, uid, params);
		
        logger.info("End::download() of " + target + " =====");
	}
	
}
