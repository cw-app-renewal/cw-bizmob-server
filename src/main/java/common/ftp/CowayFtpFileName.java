package common.ftp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CowayFtpFileName extends CowayFtpFileType {

	private static final String _IMG_POSTFIX = ".jpg";
	private static final String _MP4_POSTFIX = ".mp4";
	private static final String _3GP_POSTFIX = ".3gp";
	
	// !! 주의 !!
	// prefix는 4자리 고정(DB size 때문에 4자리 초과 사용 불가)
	//	(예외, work로 저장되는 값은 6자리 까지 가능함)
	// 
	private static final String _INSTALL_IMG_PREFIX = "inst";
	private static final String _ADDRESS_IMG_PREFIX = "addr";
	private static final String _NAN_IMG_PREFIX = "nan";
	private static final String _QA_IMG_PREFIX = "qa";
	private static final String _MEMO_IMG_PREFIX = "memo";
	private static final String _THUMBNAIL_IMG_PREFIX = "thn";
	private static final String _PL_IMG_PREFIX = "pl";
	private static final String _FIRE_IMG_PREFIX = "fire";
	private static final String _NAN1_IMG_PREFIX = "nan1";
	private static final String _NAN2_IMG_PREFIX = "nan2";
	private static final String _NAN3_IMG_PREFIX = "nan3";
	private static final String _INSTALL_SP_IMG_PREFIX = "sp1";
	private static final String _HRT_IMG_PREFIX = "hrt1";
	private static final String _NOINST_IMG_PREFIX = "noinst";	// 작업에 저장됨
	private static final String _GUBUN2 = "_AFTER_";
	private static final String _MIBUNJIN_IMG_PREFIX = "ff";	// 미분진
	private static final String _RETURN_IMG_PREFIX = "rtn";	// 설치 타사반환이미지
	private static final String _REC_IMG_PREFIX = "rec";	// 음성통화 녹취파일
	private static final String _QR_IMG_PREFIX = "qr";		// QR코드 등록 승인 요청 이미지
	private static final String _PLREC_IMG_PREFIX = "pl";	// PL음성통화 녹취파일
	private static final String _NOINST2_IMG_PREFIX = "noinst2";	// 설치불가 대응자재 사용 이미지
	private static final String _QRTN_IMG_PREFIX = "artn";	// 아답타 미반환 사유 이미지
	private static final String _QRHC_IMG_PREFIX = "qr";	// QR코드 등록 승인요청(홈케어닥터)
	private static final String _PMAT_IMG_PREFIX = "pmat";	// 피해부품 이미지(닥터)
	private static final String _AIR_IMG_PREFIX = "air";		// 2015-03-25 (홈케어닥터) 에어컨동행동의 이미지
	private static final String _HOMEPL_IMG_PREFIX = "hcpl";		// 2015-03-25 (홈케어닥터) PL동행 동의 이미지
	private static final String _CORE_IMG_PREFIX = "codyRec";		// 2015-09-02 (코디) 현장제보 이미지
	private static final String _CLEA_IMG_PREFIX = "cleanliness";		// 2016-04-11 (닥터)작업 : 위생모니터링 이미지	// 미사용
	private static final String _FEEDER_IMG_PREFIX = "feederSc";		// 2016-06-14 (코디) 피더스크류 이미지
	private static final String _KIND_IMG_PREFIX = "kindMms";			// 2016-08-16 (닥터) 착한문자 이미지
	private static final String _EORD_IMG_PREFIX = "etcOrder";			// 2016-08-24 (코디) 제품실사 이미지

	private static final String _NAN4_IMG_PREFIX = "nan4";		// 2016-10-06 (코디) 난 이미지_4
	private static final String _NAN5_IMG_PREFIX = "nan5";		// 2016-10-06 (코디) 난 이미지_5
	private static final String _NAN6_IMG_PREFIX = "nan6";		// 2016-10-06 (코디) 난 이미지_6
	private static final String _NAN7_IMG_PREFIX = "nan7";		// 2018-03-29 (코디) 난 이미지_7
	private static final String _NAN8_IMG_PREFIX = "nan8";		// 2016-03-29 (코디) 난 이미지_8
	
	private static final String _QM_IMG_PREFIX = "qm";		// 2016-03-29 (코디) 난 이미지_8
	private static final String _QM_MOV_PREFIX = "qmMov";		// 2016-03-29 (코디) 난 이미지_8
	
	private static final String _TGCL_IMG_PREFIX = "tgcl";	// 도시가스마감 이미지
	
	private static final String _CSQ_IMG_PREFIX = "csq";	// 도시가스마감 이미지
	
	private static final String _PLIMG_IMG_PREFIX = "plrImg";	// 2017-06-29 PL현장제보 이미지
	private static final String _PLMOV_IMG_PREFIX = "plrMov";	// 2017-06-29 PL현장제보 동영상
	
	private static final String _WMAP_IMG_PREFIX = "water";	// 2017-06-29 WATERMAP 간이측정 이미지
	private static final String _UNIF_IMG_PREFIX = "unif";	// 2017-07-25 유니폼 이미지
	private static final String _TCPI_IMG_PREFIX = "tcpi";	// 2017-09-07 타사고객 영업 추천
	private static final String _PLM_MOV_PREFIX	 = "plm";	// 2017-09-18 PL 동영상
	private static final String _FIRM_MOV_PREFIX = "firm";	// 2017-09-18 화재동영상
	private static final String _OPIN_IMG_PREFIX = "opin";	// 2017-09-18 닥터 소견서 이미지
	private static final String _LADD_IMG_PREFIX = "ladder";	// 2017-10-18 사다리 이미지
	private static final String _BILL_IMG_PREFIX = "bill";	// 2017-10-18 영수증 이미지
	private static final String _HYBRID_IMG_PREFIX = "hydt";	// 2017-10-18 영수증 이미지
	private static final String _RVIM_IMG_PREFIX = "rvim";	// 2017-10-18 영수증 이미지
	
	private static final String _CPLRMOV_IMG_PREFIX = "cPlrMov";	// 2019-01-08 PL현장제보 동영상	
	private static final String _CPLMOV_IMG_PREFIX = "cPlm";	// 2019-01-08 PL현장제보 동영상
	private static final String _ASTP_IMG_PREFIX = "astp";	// 2019-01-28 AS SKill 가이드 동영상
	
	private static final String _NAN9_IMG_PREFIX = "nan9";		// 2019-05-24 (코디) 난 이미지_9
	private static final String _QABM_MOV_PREFIX = "qabm";		// 2016-05-24 (코디) 난 이미지_9
	private static final String _HPLB_IMG_PREFIX = "hplb";		// 2019-08-05 (코디) HPLB 이미지_9
	private static final String _QR_HOMECARE_IMG_PREFIX = "qr";		// 2019-08-05 qr 이미지_9
	private static final String _WITH_IMG_PREFIX = "with";
	/*
	 * 고객 제품 설치 사진 파일명 규칙 = inst_고객주문번호_이미지순번.jpg
	 */
	public static String getInstallImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _INSTALL_IMG_PREFIX + "_" +  imgSeq + _IMG_POSTFIX;
	}
	
	/*
	 * 고객 주소 사진 파일명 규칙 = addr_고객주문번호_이미지순번.jpg
	 */
	public static String getAddressImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _ADDRESS_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/*
	 * 난공사 작업 사진 파일명 규칙 = nan_작업일자_작업구분_고객주문번호_작업순번.jpg 
	 */
	public static String getWorkNanImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan1ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN1_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan2ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN2_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan3ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN3_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan4ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN4_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan5ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN5_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan6ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN6_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan7ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN7_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan8ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN8_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkNan9ImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NAN9_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkHplbImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getHRTImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _HRT_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getWorkHybridImageName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _HYBRID_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}

	public static String getWorkQabmMOVName(String jobDate, String jobType, String orderNo, String jobSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _QABM_MOV_PREFIX + _MP4_POSTFIX;
	}
	
	/*
	 * QA풀질정보 사진 파일명 규칙 = qa_작업일자_작업구분_고객주문번호_작업순번.jpg
	 */
	public static String getWorkQaImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		
		if ( imgSeq.equalsIgnoreCase("01") ) {
			
			imgSeq = "";
		}
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _QA_IMG_PREFIX + imgSeq +_IMG_POSTFIX;
	}
	
	/*
	 * 착한문자 이미지 사진 파일명 규칙 = kindMms_작업일자_작업구분_고객주문번호_작업순번.jpg
	 */
	public static String getWorkKindImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		
		if ( imgSeq.equalsIgnoreCase("01") ) {
			
			imgSeq = "";
		}
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _KIND_IMG_PREFIX + imgSeq +_IMG_POSTFIX;
	}	
	
	/**
	 * PL 이미지 사진 파일명 규칙 = 날짜_작업구분_고객주문번호_작업시퀀스_pl_01.jpg
	 * @param imgSeq 이미지 번호(01, 02, 03)
	 */
	public static String getPLImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _PL_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * FIRE 이미지 사진 파일명 규칙 = 날짜_작업구분_고객주문번호_작업시퀀스_pl_01.jpg
	 * @param imgSeq 이미지 번호(01, 02, 03)
	 */
	public static String getFireImgName(String jobDate, String jobType,	String orderNo, String jobSeq, String imgSeq) {
		
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _FIRE_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/*
	 * QA품질정보 동영상 파일명 규칙 = qa_작업일자_작업구분_고객주문번호_작업순번.mp4
	 */
	public static String getWorkQaMp4Name(String jobDate, String jobType, String orderNo, String jobSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _QA_IMG_PREFIX + _MP4_POSTFIX;
	}
	
	/*
	 * 고객 메모 사진 파일명 규칙 = memo_(임시)고객주문번호_이미지순번.jpg
	 */
	public static String getMemoImgName(String tmpOrderNo, String imgSeq) {
		return tmpOrderNo + "_" + _MEMO_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}	
	
	/*
	 * 
	 */
	public static String getGoodsImgName(String goodsCode, String isThumbNail) {
		
		if(isThumbNail.equals("Y") == true)
			return _THUMBNAIL_IMG_PREFIX + goodsCode + _IMG_POSTFIX;
		else		
			return goodsCode + _IMG_POSTFIX;
	}
	
	/*
	 * 
	 */
	public static String getPartsImgName(String partsCode, String isThumbNail) {
		
		if(isThumbNail.equals("Y") == true)
			return _THUMBNAIL_IMG_PREFIX + partsCode + _IMG_POSTFIX;
		else 
			return partsCode + _IMG_POSTFIX;
	}
	
	/**
	 * @method getInstallSpImgName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} imgSeq 이미지 순번
	 * @return {String} 이미지이름
	 * @since 2013-07-09
	 * @description 설치 이미지 SP1 이 붙은 이름
	 */
	private static String getInstallSpImgName(String orderNo, String imgSeq) {
		
		return orderNo + "_" + _INSTALL_SP_IMG_PREFIX + "_" +  imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * 홈케어 이미지 파일 이름을 만드는 메소드
	 * 
	 * @since 2013-11-20
	 * @param orderNo 주문번호
	 * @param imgSeq 이미지 번호
	 * @return result 홈케이 이미지 파일 이름
	 */
	public static String getAfterImgName(String orderNo, String imgSeq) {

		String result = orderNo + _GUBUN2 + imgSeq + _IMG_POSTFIX;
		
		return result;
	}
	
	/**
	 * 작업중 미설치 이미지 파일 이름을 생성
	 * 파일명   : 작업날짜_작업구분_주문번호_작업시퀀스_noinst_순번.jpg
	 * 
	 * @since 2014-01-14
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @param imgSeq 순번(최대 9까지) : 단말기에서 제공
	 * @return
	 */
	public static String getNoinstImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _NOINST_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * @method getMibunjinImgName
	 * @param {String} orderNo 고객 주문번호
	 * @return {String} 이미지이름
	 * @since 2014-05-08
	 * @description 설치 이미지 ff붙은 이름,
	 * 				작업중 미설치 이미지 파일 이름을 생성
	 */
	public static String getMibunjinImgName(String orderNo) {
		return orderNo + "_" + _MIBUNJIN_IMG_PREFIX + _IMG_POSTFIX;
	}
	
	/**
	 * @method getInstallReturnImgName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} imgSeq 이미지순번
	 * @return {String} 이미지이름
	 * @since 2014-06-11
	 * @description 고객이미지중 설치 타사 반환 이미지 파일 이름 생성
	 */
	public static String getInstallReturnImgName(String orderNo, String imgSeq) {

		String result = orderNo + "_" + _RETURN_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
		
		return result;
	}

	/**
	 * @method getRecFileName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} imgSeq 이미지순번(01 ~ 99)
	 * @return {String} 이미지이름
	 * @since 2014-06-11
	 * @description 고객이미지 중 음성통화 녹취 파일 이름 생성
	 */
	public static String getRecFileName(String orderNo, String imgSeq) {
		return orderNo + "_" + _REC_IMG_PREFIX + "_" + imgSeq + _3GP_POSTFIX;
	}

	/**
	 * @method getPLRecFileName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} imgSeq 이미지순번(01 ~ 99)
	 * @return {String} 이미지이름
	 * @since 2014-06-11
	 * @description 고객이미지 중 PL음성통화 녹취 파일 이름 생성
	 */
	public static String getPLRecFileName(String orderNo, String imgSeq) {
		return orderNo + "_" + _PLREC_IMG_PREFIX + "_" + imgSeq + _3GP_POSTFIX;
	}
	
	/**
	 * @method getQRImgName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} imgSeq 이미지순번(1 ~ 2)
	 * @return {String} 이미지이름
	 * @since 2014-07-28
	 * @description QR코드 등록 승인 요청 이미지 이름 생성
	 */
	public static String getQRImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _QR_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * @method getNoInst2ImgName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} imgSeq 이미지순번(1 ~ 9)
	 * @return {String} 이미지이름
	 * @since 2014-07-28
	 * @description 고객이미지 중 설치불가 대응자재 사용 이미지 이름 생성
	 */
	public static String getNoInst2ImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _NOINST2_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * @method getTownGasCloseImgName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} imgSeq 이미지순번(1 ~ 9)
	 * @return {String} 이미지이름
	 * @since 2016-10-11
	 * @description 고객이미지 중 도시가스마감 이미지 이름 생성
	 */
	public static String getTownGasCloseImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _TGCL_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}

	/**
	 * @method getArtnImgName : 날짜_작업구분_고객주문번호_시퀀스_artn.jpg
	 * @param {String} jobDate 작업일자
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} jobSeq jobSeq
	 * @param {String} imgSeq 이미지순번(1 ~ 9)
	 * @return {String} 이미지이름
	 * @since 2015-01-19
	 * @description 작업이미지 중 아답타 미반환 사유 이미지
	 */
	public static String getArtnImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _QRTN_IMG_PREFIX+ "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * @method getQRHCImgName
	 * @param {String} orderNo 고객 주문번호
	 * @param {String} jobDate 작업일자
	 * @return {String} 이미지이름
	 * @since 2015-01-27
	 * @description QR코드 등록 승인 요청 이미지 이름 생성
	 */
	public static String getQRHCImgName(String orderNo, String jobDate) {
		return orderNo + "_" + _QRHC_IMG_PREFIX + "_" + jobDate + _IMG_POSTFIX;
	}
	
	/**
	 * @method getPmatImgName
	 * @param {String} orderNo 고객 주문번호
	 * @return {String} 이미지이름
	 * @since 2015-03-12
	 * @description 피해부품 이미지
	 */
	public static String getPmatImgName(String orderNo) {
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		String dateStr = date.format(new Date());
		
		return dateStr + "_" + orderNo + _IMG_POSTFIX;
	}
	
	/**
	 * @method getAirImgName
	 * @param {String} orderNo 고객 주문번호
	 * @return {String} 이미지이름
	 * @since 2015-03-25
	 * @description 에어컨동행동의 이미지
	 */
	public static String getAirImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * @method getHomeplImgName
	 * @param {String} orderNo 고객 주문번호
	 * @return {String} 이미지이름
	 * @since 2015-03-25
	 * @description PL동행 동의 이미지
	 */
	public static String getHomeplImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * @method getPlhcImgName
	 * @param {String} orderNo 고객 주문번호
	 * @return {String} 이미지이름
	 * @since 2015-05-20
	 * @description PL등록
	 */
	public static String getPlhcImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * 작업중 현장제보 이미지 파일 이름을 생성
	 * 파일명   : 주문번호_codyRec_사진번호.jpg
	 * 
	 * @since 2015-09-02
	 * @param orderNo
	 * @param imgSeq 순번(최대 99까지) : 단말기에서 제공
	 * @return
	 */
	public static String getCoreImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _CORE_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * 제품실사 이미지 파일 이름을 생성
	 * 파일명   : 주문번호_etcOrder_사진번호.jpg
	 * 
	 * @since 2016-08-24
	 * @param orderNo
	 * @param imgSeq 순번(최대 2까지) : 단말기에서 제공
	 * @return
	 */
	public static String getEtcOrderImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _EORD_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * 위생모니터링 이미지
	 * 파일명   : 현재년월일시간분초userID.jpg
	 * 
	 * @since 2016-04-11
	 * @param orderNo
	 * @param procID : 사용자 ID(user id)
	 * @return
	 */
	public static String getCleaImgName(String orderNo, String procID) {
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr = date.format(new Date());

		return dateStr + procID + _IMG_POSTFIX;
	}

	/**
	 * 피더 스크류 이미지 파일 이름을 생성
	 * FDSC 이미지 사진 파일명 규칙 = 날짜_작업구분_고객주문번호_작업시퀀스_pl_01.jpg
	 * @param imgSeq 이미지 번호(01, 02, 03)
	 */
//	public static String getFeederImgName(String orderNo, String imgSeq) {
//		return orderNo + "_" + _FEEDER_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
//	}
	public static String getFeederImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _FEEDER_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * CSQ 점검후 촬영 이미지 
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_csq_01.jpg
	 * 
	 * @since 2017-03-14
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @param imgSeq
	 * @return
	 */
	public static String getCsqImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _CSQ_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * PL현장제보 촬영 이미지 
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_plrImg_01.jpg
	 * 
	 * @since 2017-06-29
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @param imgSeq
	 * @return
	 */
	public static String getPlrImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _PLIMG_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * PL현장제보 촬영 동영상 이미지
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_plrMov_01.jpg
	 * 
	 * @since 2017-06-29
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @param imgSeq
	 * @return
	 */
	public static String getPlrMovName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _PLMOV_IMG_PREFIX + "_" + imgSeq + _MP4_POSTFIX;
	}
	
	/**
	 * PL현장제보 동영상(코디)
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_cPlrMov_01.mov
	 * 
	 * @since 2019-01-08
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @param imgSeq
	 * @return
	 */
	public static String getCPlrMovName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _CPLRMOV_IMG_PREFIX + "_" + imgSeq + _MP4_POSTFIX;
	}
	
	/**
	 * PL동영상(코디)
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_cPlm.mp4
	 * 
	 * @since 2019-01-08
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @return
	 */
	public static String getCPlMovName(String jobDate, String jobType, String orderNo, String jobSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _CPLMOV_IMG_PREFIX + _MP4_POSTFIX;
	}
	
	/**
	 * AS스킬 가이드
	 * 파일명   : 날짜_작업구분_고객주문번호_작업시퀀스_astp_이미지시퀀스.mp4
	 * 
	 * @since 2019-01-08
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @return
	 */
	public static String getAsSkillName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq ) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _ASTP_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * WATERMAP 간이측정 촬영 이미지 이미지
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_water_01.jpg
	 * 
	 * @since 2017-06-29
	 * @param jobDate
	 * @param jobType
	 * @param orderNo
	 * @param jobSeq
	 * @param imgSeq
	 * @return
	 */
	public static String getWmapImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq + "_" + _WMAP_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	
	/**
	 * uniform 유니폼 이미지
	 * 파일명   : 유니폼구매일련번호_unif_01.jpg
	 * 
	 * @since 2017-08-07
	 * @param orderNo 유니폼 일련번호
	 * @param imgSeq
	 * @return
	 */
	public static String getUnifImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _UNIF_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * 타사고객영업추천 이미지
	 * 파일명   : 현재년월일시간분초userID.jpg
	 * 
	 * @since 2017-09-07
	 * @param orderNo
	 * @param procID : 사용자 ID(user id)
	 * @return
	 */
	public static String getTcpiImgName(String orderNo, String procID) {
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr = date.format(new Date());

		return dateStr + procID + _IMG_POSTFIX;
	}
	/**
	 * 사다리 이미지
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_ladder_01.jpg
	 * @return
	 */
	public static String getLadderImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _LADD_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	/**
	 * 영수증 이미지
	 * 파일명   : 날짜_작업구분_고객주문번호_시퀀스_bill_01.jpg
	 * 
	 * @since 2017-10-18
	 */
	public static String getBillImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _BILL_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getPlmMovName(String jobDate, String jobType, String orderNo, String jobSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _PLM_MOV_PREFIX + _MP4_POSTFIX;
	}
	
	public static String getfirmMovName(String jobDate, String jobType, String orderNo, String jobSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _FIRM_MOV_PREFIX + _MP4_POSTFIX;
	}
	
	public static String getOpinImgName(String orderNo, String imgSeq) {
		return orderNo + "_" + _OPIN_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	
	public static String getRvimImgName(String orderNo, String jobDate, String imgSeq) {
		return orderNo + "_" + jobDate + "_" +  _RVIM_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}
	/**
	 * 멘토멘티 이미지 업로드 파일명 세팅
	 * @param jobDate 날짜
	 * @param orderNo 멘토등록번호
	 * @param procID 멘티등록번호
	 * @param imgSeq 시퀀스
	 * @return
	 */
	public static String getMentImgName(String jobDate, String orderNo, String imgSeq) {
		return jobDate + "_" + orderNo+ "_ment" + "_" + imgSeq + _IMG_POSTFIX;
	}

	public static String getWithImgName(String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq) {
		return jobDate + "_" + jobType + "_" + orderNo + "_" + jobSeq  + "_" + _WITH_IMG_PREFIX + "_" + imgSeq + _IMG_POSTFIX;
	}

	/*
	 * 
	 */
	public static String getCowayFtpFileName(String imgType, String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq, String procID, String fileCa) {
		
		String name = null;
		
		if(imgType.equals(_IMG_TYPE_INSTALL)) {
			name = getInstallImgName(orderNo, imgSeq);
		
		} else if(imgType.equals(_IMG_TYPE_ADDRESS)) {
			name = getAddressImgName(orderNo, imgSeq);
		
		} else if(imgType.equals(_IMG_TYPE_MEMO)) {
			name = getMemoImgName(orderNo, imgSeq);
		
		} else if(imgType.equals(_IMG_TYPE_GOODS)) {
			//name = getGoodsImgName(goodsCode);
			name = "";
		
		} else if(imgType.equals(_IMG_TYPE_PARTS)) {
			//name = getPartsImgName(partsCode);
			name = "";
		
		} else if(imgType.equals(_IMG_TYPE_QAIMG)) {
			name = getWorkQaImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		
		} else if(imgType.equals(_IMG_TYPE_QAMP4)) {
			name = getWorkQaMp4Name(jobDate, jobType, orderNo, jobSeq);
		
		} else if(imgType.equals(_IMG_TYPE_NAN)) {
			name = getWorkNanImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PL) ) {
			name = getPLImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FIRE) ) {
			name = getFireImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN1) ) {
			name = getWorkNan1ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN2) ) {
			name = getWorkNan2ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN3) ) { 
			name = getWorkNan3ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_INSTALL02) ) {
			name = getInstallSpImgName(orderNo, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HRT) ) {
			name = getHRTImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_AFTER) ) {
			name = getAfterImgName(orderNo, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NOINST) ) {
			name = getNoinstImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_MIBUNJIN) ) {
			name = getMibunjinImgName(orderNo);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_RETURN) ) {
			name = getInstallReturnImgName(orderNo, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_REC) ) {
			name = getRecFileName(orderNo, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QR) ) {
			name = getQRImgName(orderNo, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLREC) ) {
			name = getPLRecFileName(orderNo, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NOINST2) ) {
			name = getNoInst2ImgName(orderNo, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_ARTN) ) {
			name = getArtnImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QRH1) ) {
			name = getQRHCImgName(orderNo, jobDate);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QRH2) ) {
			name = getQRHCImgName(orderNo, jobDate);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PMAT) ) {
			name = getPmatImgName(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_AIR) ) {
			name = getAirImgName(orderNo, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HOMEPL) ) {
			name = getHomeplImgName(orderNo, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLHC) ) {
			name = getPlhcImgName(orderNo, imgSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CORE) ) {
			name = getCoreImgName(orderNo, imgSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CLEA) ) {
			name = getCleaImgName(orderNo, procID);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FEEDER) ) {
			name = getFeederImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		
		} else if(imgType.equals(_IMG_TYPE_KINDIMG)) {
			name = getWorkKindImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_EORDIMG) ) {
			name = getEtcOrderImgName(orderNo, imgSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN4) ) { 
			name = getWorkNan4ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN5) ) { 
			name = getWorkNan5ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN6) ) { 
			name = getWorkNan6ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_TGCL) ) {
			name = getTownGasCloseImgName(orderNo, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CSQ) ) {
			name = getCsqImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLIMG) ) {
			name = getPlrImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLMP4) ) {
			name = getPlrMovName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_WMAP) ) {
			name = getWmapImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_UNIF) ) {
			name = getUnifImgName(orderNo, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_TCPI) ) {
			name = getTcpiImgName(orderNo, procID);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLM) ) {
			name = getPlmMovName(jobDate, jobType, orderNo, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FIRM) ) {
			name = getfirmMovName(jobDate, jobType, orderNo, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_OPIN) ) {
			name = getOpinImgName(orderNo, imgSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_MENT) ) {
			name = getMentImgName(jobDate, orderNo, imgSeq);	
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_LADD) ) {
			name = getLadderImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_BILL) ) {
			name = getBillImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN7) ) {
			name = getWorkNan7ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN8) ) {
			name = getWorkNan8ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HYBRID) ) {
			name = getWorkHybridImageName(jobDate, jobType, orderNo, jobSeq, imgSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QM) ) {
			name = getQMImageName(jobDate, orderNo, imgSeq, fileCa);
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_QM) ) {
			name = getQmMovName(jobDate, orderNo, imgSeq, fileCa);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_RVIM) ) {
			name = getRvimImgName(orderNo, jobDate, imgSeq);
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_CPLR) ) {
			name = getCPlrMovName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_CPLM) ) {
			name = getCPlMovName(jobDate, jobType, orderNo, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_ASTP) ) {
				name = getAsSkillName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN9) ) {
			name = getWorkNan9ImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);			
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_QABM) ) {
			name = getWorkQabmMOVName(jobDate, jobType, orderNo, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HPLB) ) {
			name = getWorkHplbImgName(orderNo, imgSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QR_HOMECARE) ) {
			name = getQRHCImgName(orderNo, jobDate);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_WITH) ) {
			name = getWithImgName(jobDate, jobType, orderNo, jobSeq, imgSeq);
		} else {
			name = "";
		}
		
		return name;
	}

	public static String getQMImageName(String jobDate, String orderNo, String imgSeq, String fileCa) {
		if(fileCa == null || "".equals(fileCa) ) {
			return orderNo + "_" + jobDate + "_" + imgSeq + _IMG_POSTFIX;
		} else {
			return orderNo + "_" + jobDate + "_" + fileCa + "_" + imgSeq + _IMG_POSTFIX;
		}
	}
	
	public static String getQmMovName(String jobDate, String orderNo, String imgSeq, String fileCa) {
		if(fileCa == null || "".equals(fileCa) ) {
			return orderNo + "_" + jobDate + "_" + imgSeq + _MP4_POSTFIX;
		} else {
			return orderNo + "_" + jobDate + "_" + fileCa + "_" + imgSeq + _MP4_POSTFIX;
		}
	}
	
	public static String getMentImgName(DownloadDO downloadDO) {
			
		String jobDate 		= downloadDO.getJobDate();
		String imgSeq 		= downloadDO.getImgSeq();
		String orderNo 		= downloadDO.getOrderNo();		
		
		return getMentImgName(jobDate, orderNo, imgSeq);
	}

}
