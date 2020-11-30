package common.ftp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mcnc.smart.common.config.SmartConfig;

import common.util.DateUtil;

public class CowayFtpFilePath extends CowayFtpFileType {

	public static final String _FOLDER_SEPARATOR = "/";

	// 개발서버인 경우 /dev 를, 운영서버는 ""를  사용하도록 설정
	public static final String _DEV_ROOT_FOLDER = Boolean.parseBoolean(SmartConfig.getString("media.ftp.mode.dev", "false"))?"/dev":"";
	
	// /photo 를 root로 사용
	private static final String _IMAGES_ROOT_FOLDER = _DEV_ROOT_FOLDER + _FOLDER_SEPARATOR + "photo";
	private static final String _MOVIE_ROOT_FOLDER = _DEV_ROOT_FOLDER + _FOLDER_SEPARATOR + "photo1";
	private static final String _CUSTOMER_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "customer";
	private static final String _WORK_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "work";
	private static final String _MOVWORK_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR + "work";		//photo1 work 폴더
	private static final String _MOVCUST_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR + "customer"; //photo1 customer 폴더
	private static final String _MEMO_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "memo";
	private static final String _PRODUCT_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "product";
	private static final String _GOODS_ROOT_FOLDER = _PRODUCT_ROOT_FOLDER + _FOLDER_SEPARATOR + "goods";	
	private static final String _PARTS_ROOT_FOLDER = _PRODUCT_ROOT_FOLDER + _FOLDER_SEPARATOR + "parts";
	private static final String _GOODS2_ROOT_FOLDER = _PRODUCT_ROOT_FOLDER + _FOLDER_SEPARATOR + "goods2";
	
	private static final String _SURVEY_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "SURVEY";
	private static final String _RECEIPTMMS_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "mms";	// 영수증MMS는 별도의 ftp 폴더에서 사용함(MMS발송 ftp)
	private static final String _MIBUNJIN_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "ff";
	private static final String _QR_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "qr";
	private static final String _QRH1_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "qrh1";	// 2015-01-16 추가
	private static final String _QRH2_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "qrh2";	// 2015-01-16 추가
	private static final String _COWAYMMS_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "mms";	// 2015-01-27 전단지 이미지 추가(coway_mms)
	private static final String _PMAT_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "pl";		// 2015-03-04 (닥터) 피해부품이미지
	private static final String _AIR_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "air";		// 2015-03-25 (홈케어닥터) 에어컨동행동의 이미지
	private static final String _HOMEPL_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "hcpl";		// 2015-03-25 (홈케어닥터) PL동행 동의 이미지
	private static final String _PLHC_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "plhc";		// 2015-05-20 (코디) PL등록
	private static final String _CORE_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "codyRec";		// 2015-09-02 (코디) 현장제보 이미지
	private static final String _CLEA_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "cleanliness";		// 2016-04-11 (닥터)작업 : 위생모니터링 이미지
	private static final String _FEEDER_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "feederSc";		// 2016-06-14 (코디) 피더스크류 이미지
	private static final String _EORD_ROOT_FOLDER = _IMAGES_ROOT_FOLDER + _FOLDER_SEPARATOR + "etcOrder";		// 2016-08-24 (코디) 제품실사 이미지
	private static final String _UNIF_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR + "uniform";		// 2017-08-07 유니폼 이미 저장경로
	private static final String _TCPI_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR + "tcpi";		// 2017-09-07 타사고객영업추천 저장경로
	private static final String _MENT_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR + "ment";		//2018-01-15 멘토멘티 이미지
	
	private static final String _QM_ROOT_FOLDER = _DEV_ROOT_FOLDER + _FOLDER_SEPARATOR +"qm";		//2018-05-24 qmImage
	private static final String _RVIM_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR +"rvim";		//2018-10-12 rvimImage
	
	
	private static final String _QRH3_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR + "qrh3";	// 2015-01-16 추가
	
	// /rec 를 root로 사용
	private static final String _RECORD_ROOT_FOLDER = _DEV_ROOT_FOLDER + _FOLDER_SEPARATOR + "rec";
	private static final String _REC_ROOT_FOLDER = _RECORD_ROOT_FOLDER + _FOLDER_SEPARATOR + "customer";
	
	// /pl root로 사용
	private static final String _PLRECORD_ROOT_FOLDER = _DEV_ROOT_FOLDER + _FOLDER_SEPARATOR + "pl" + _FOLDER_SEPARATOR + "customer";
	private static final String _HPLB_ROOT_FOLDER = _MOVIE_ROOT_FOLDER + _FOLDER_SEPARATOR + "hplb";		// 2015-05-20 (코디) PL등록
	/**
	 * 제품(주문번호)과 관련된 이미지 저장 경로를 생성합니다.
	 * 주문번호당 1개의 경로가 생성되고, 제품과 관련된 이미지(설치이미지, 설치주소이미지)등이 저장됩니다.
	 */
	public static String getCustomerFolder(String orderNo) {
		
		String path = null;
		
		path = _CUSTOMER_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/**
	 * 작업과 관련된 이미지 저장 경로를 생성합니다.
	 * 작업은 jobdate, jobtype, jobseq마다 1개의 이미지 경로를 생성합니다.
	 */
	public static String getWorkFolder(String jobDate, String jobType, String jobSeq) {
		
		String path = null;
		
		path = _WORK_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ jobDate + _FOLDER_SEPARATOR
				+ jobType + _FOLDER_SEPARATOR
				+ jobSeq;
	
		return path;		
	}
	
	/**
	 * 작업과 관련된 이미지 저장 경로를 생성합니다.
	 * 작업은 jobdate, jobtype, jobseq마다 1개의 이미지 경로를 생성합니다.
	 */
	public static String getMovieWorkFolder(String jobDate, String jobType, String jobSeq) {
		
		String path = null;
		
		path = _MOVWORK_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ jobDate + _FOLDER_SEPARATOR
				+ jobType + _FOLDER_SEPARATOR
				+ jobSeq;
		
		return path;		
	}
	
	
	
	/**
	 * 고객과 관련된 이미지 저장 경로를 생성합니다.
	 * orderNo 마다 1개의 이미지 경로를 생성합니다.
	 */
	public static String getMovieCustFolder(String orderNo) {
		
		String path = null;
		
		path = _MOVCUST_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	public static String getUniformFolder(String procId) {
		
		String path = null;
		
		path = _UNIF_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ procId;
		
		return path;		
	}
	
	/**
	 * 홈케이 어미지 저장소를 가져오는 메소드
	 * 
	 * <pre>
	 * 	설명 : orderNo의 길이가 틀렸을 경우 커스터머 폴더에서 orderNo로 폴더 경로를 지정한다.
	 * 		그 외에의 경우에는 3자리씩 끊어서 폴더 경로를 지정한다.
	 * </pre>
	 * 
	 * @param orderNo 주문번호
	 * @return path 홈케어 이미지 저장소 결과 값
	 */
	public static String getAfterFolder(String orderNo) {

		String path = "";
		
		try {
			
			path = _CUSTOMER_ROOT_FOLDER + _FOLDER_SEPARATOR
					+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
					+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
					+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
					+ orderNo.substring(9, 12);
			
		} catch ( StringIndexOutOfBoundsException e ) {
			
			path = _CUSTOMER_ROOT_FOLDER + _FOLDER_SEPARATOR + orderNo;
		} catch ( NullPointerException e ) {
			
			path = _CUSTOMER_ROOT_FOLDER + _FOLDER_SEPARATOR;
		}
		
		
		return path;
	}
	
	/*
	 * 
	 */
	public static String getMemoFolder(String tmpOrderNo) {
		
		String path = null;
		
		path = _MEMO_ROOT_FOLDER + _FOLDER_SEPARATOR
				+ tmpOrderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ tmpOrderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ tmpOrderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ tmpOrderNo.substring(9, tmpOrderNo.length());
		
		return path;
	}
	
	/*
	 * 
	 */
	public static String getGoodsFolder(String goodsCode) {
		
		String path = null;
		
		path = _GOODS_ROOT_FOLDER + _FOLDER_SEPARATOR
				+ goodsCode.substring(0, 3);
		
		return path;
	}
	
	/*
	 *  
	 */
	public static String getPartsFolder(String partsCode) {
		
		String path = null;
		
		path = _PARTS_ROOT_FOLDER + _FOLDER_SEPARATOR
				+ partsCode.substring(0, 3);
		
		return path;	
	}

	/*
	 * 미분진 이미지 저장 폴더 생성
	 */
	public static String getMibunjinFolder(String orderNo) {
		
		String path = null;
		
		path = _MIBUNJIN_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}

	/*
	 * 미분진 이미지 저장 폴더 생성
	 */
	public static String getQRCustomerFolder(String orderNo) {
		
		String path = null;
		
		path = _QR_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * QR코드 등록 승인요청
	 */
	public static String getQrh1WorkFolder(String orderNo) {
		
		String path = null;
		
		path = _QRH1_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * QR코드 미촬영 이미지
	 */
	public static String getQrh2WorkFolder(String orderNo) {
		
		String path = null;
		
		path = _QRH2_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}

	/*
	 * 전단지 이미지
	 */
	public static String getCowayMMSFolder() {
		
		String path = null;
		
		path = _COWAYMMS_ROOT_FOLDER;

		return path;
	}
	
	/*
	 * 피해부품 이미지
	 */
	public static String getPmatFolder() {
		
		String path = null;
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMM");
		String yyyymm = date.format(new Date());
				
		path = _PMAT_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ yyyymm;

		return path;
	}

	/*
	 * 에어컨동행동의 이미지
	 */
	public static String getAirFolder(String orderNo) {
		
		String path = null;
		
		path = _AIR_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * PL동행 동의 이미지
	 */
	public static String getHomeplFolder(String orderNo) {
		
		String path = null;
		
		path = _HOMEPL_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * PL등록
	 */
	public static String getPlhcFolder(String orderNo) {
		
		String path = null;
		
		path = _PLHC_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * 현장제보 이미지
	 */
	public static String getCoreFolder(String orderNo) {
		
		String path = null;
		
		path = _CORE_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * 제고실사 이미지
	 */
	public static String getEtcOrderFolder(String orderNo) {
		
		String path = null;
		
		path = _EORD_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/**
	 * 위생모니터링 이미지 생성 경로
	 * 
	 * @since 2016-04-11
	 * @param orderNo
	 * @return
	 */
	public static String getCleaFolder(String orderNo) {
		
		String path = null;
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMM");
		String yyyymm = date.format(new Date());
				
		path = _CLEA_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ yyyymm;

		return path;
	}
	
	/**
	 * 타사고객영업추천 이미지 생성 경로
	 * 
	 * @since 2017-09-07
	 * @param orderNo
	 * @return
	 */
	public static String getTcpiFolder(String orderNo) {
		
		String path = null;
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMM");
		String yyyymm = date.format(new Date());
		
		path = _TCPI_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ yyyymm;
		
		return path;
	}
	/*
	 * 피더스크류 이미지
	 */
	public static String getFeederFolder(String orderNo) {
		
		String path = null;
		
		path = _FEEDER_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * 2018-01-15, 이재성, 멘토멘티 이미지
	 * 
	 */
	/**
	 * @param orderNo 멘토 등록번호
	 * @param jobDate 등록날짜
	 * @return 멘토멘티 이미지 경로
	 */
	public static String getMentFolder(String orderNo, String jobDate) {
		
		String path = null;
		
		path = _MENT_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo + _FOLDER_SEPARATOR
				+ jobDate + _FOLDER_SEPARATOR;

		return path;
	}
	
	/*
	 * 2018-05-24, 이재성, qm이미지 다운로드 경로
	 * 
	 */
	/**
	 * @param jobDate 등록날짜
	 * @return qm이미지 경로
	 */
	public static String getqmImageFolder(String jobDate) {
		String path = _QM_ROOT_FOLDER + _FOLDER_SEPARATOR + jobDate + _FOLDER_SEPARATOR; 
		return path;
	}
	
	/*
	 * 2018-07-04, 이재성, qm동영상 다운로드 경로
	 * 
	 */
	/**
	 * @param jobDate 등록날짜
	 * @return qm이미지 경로
	 */
	public static String getQmMovFolder(String jobDate) {
		String path = _QM_ROOT_FOLDER + _FOLDER_SEPARATOR + jobDate + _FOLDER_SEPARATOR; 
		return path;
	}
	
	/*
	 * QR코드 등록 승인요청
	 */
	public static String getQrh3WorkFolder(String orderNo) {
		
		String path = null;
		
		path = _QRH3_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	public static String getRvimWorkFolder(String orderNo, String jobDate) {
		String path = null;
		path = _RVIM_ROOT_FOLDER + _FOLDER_SEPARATOR + orderNo + _FOLDER_SEPARATOR + jobDate; 
		return path;
	}

	public static String getGoods2Folder(String goodsCode) {

		String path = null;
		
		path = _GOODS2_ROOT_FOLDER + _FOLDER_SEPARATOR
				+ goodsCode.substring(0, 3);
		
		return path;
	}

	public static String getCommonGoodsFolder(String goodsCode, String goodsCodeFolderName) {
		
		String path = null;
		
		path = _PRODUCT_ROOT_FOLDER + _FOLDER_SEPARATOR + goodsCodeFolderName + _FOLDER_SEPARATOR
				+ goodsCode.substring(0, 3);
		
		return path;
	}
	
	public static String getReceiptMMSFolder(String mmsSeq){
		
		String path = null;
		
		// 12자리가 안된는 경우 강제로 폴더 경로를 맞춤
		while( mmsSeq.length() < 12)
			mmsSeq = mmsSeq.concat("0");
			
		path = _RECEIPTMMS_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ mmsSeq.substring(0, 4) + _FOLDER_SEPARATOR
				+ mmsSeq.substring(4, 8) + _FOLDER_SEPARATOR
				+ mmsSeq.substring(8, 12);
		
		return path;
	}
	
	public static String getSurveyWorkFolder(String date) {
		String path = _SURVEY_ROOT_FOLDER 
					+ _FOLDER_SEPARATOR 
					+ date;
		
		return path;		
	}
	
	/**
	 * 녹취 경로 생성
	 * @param orderNo
	 * @return
	 */
	public static String getRecCustomerFolder(String orderNo) {
		
		String path = null;
		
		path = _REC_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/**
	 * PL녹취 경로 생성
	 * @param orderNo
	 * @return
	 */
	public static String getPLRecCustomerFolder(String orderNo) {
		
		String path = null;
		
		path = _PLRECORD_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}

	public static String getMentFolder(DownloadDO downloadDO) {
		
		String jobDate 		= downloadDO.getJobDate();
		//String jobType 		= downloadDO.getJobType();
//		String jobSeq 		= downloadDO.getJobSeq();
//		String imgSeq 		= downloadDO.getImgSeq();
		String orderNo 		= downloadDO.getOrderNo();		
		//String procID 		= downloadDO.getProcId();
		
		return getMentFolder(orderNo, jobDate);
	}
	
	/*
	 * HPLB
	 */
	public static String getHplbFolder(String orderNo) {
		
		String path = null;
		
		path = _HPLB_ROOT_FOLDER + _FOLDER_SEPARATOR 
				+ orderNo.substring(0, 3) + _FOLDER_SEPARATOR
				+ orderNo.substring(3, 6) + _FOLDER_SEPARATOR
				+ orderNo.substring(6, 9) + _FOLDER_SEPARATOR
				+ orderNo.substring(9, orderNo.length());

		return path;
	}
	
	/*
	 * 
	 */
	public static String getCowayFtpFilePath(String imgType, String jobDate, String jobType, String orderNo, String jobSeq, String imgSeq, String procID) {
		
		String path = null;
		
		if(imgType.equals(_IMG_TYPE_INSTALL)) {
			path = getCustomerFolder(orderNo);

		} else if(imgType.equals(_IMG_TYPE_ADDRESS)) {
			path = getCustomerFolder(orderNo);

		} else if(imgType.equals(_IMG_TYPE_MEMO)) {
			path = getMemoFolder(orderNo);
		
		} else if(imgType.equals(_IMG_TYPE_GOODS)) {
			//path = getGoodsFolder(goodsCode);
			path = "";
		
		} else if(imgType.equals(_IMG_TYPE_PARTS)) {
			//path = getPartsFolder(partsCode);
			path = "";
		
		} else if(imgType.equals(_IMG_TYPE_QAIMG)) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if(imgType.equals(_IMG_TYPE_QAMP4)) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if(imgType.equals(_IMG_TYPE_NAN)) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PL) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FIRE) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN1) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN2) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN3) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_INSTALL02) ) {
			path = getCustomerFolder(orderNo);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HRT) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_AFTER) ) {
			path = getAfterFolder(orderNo);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NOINST) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_MIBUNJIN) ) {
			path = getMibunjinFolder(orderNo);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_RETURN) ) {
			path = getCustomerFolder(orderNo);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_REC) ) {
			path = getRecCustomerFolder(orderNo);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QR) ) {
			path = getQRCustomerFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLREC) ) {
			path = getPLRecCustomerFolder(orderNo);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NOINST2) ) {
			path = getCustomerFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_ARTN) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QRH1) ) {
			path = getQrh1WorkFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QRH2) ) {
			path = getQrh2WorkFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PMAT) ) {
			path = getPmatFolder();
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_AIR) ) {
			path = getAirFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HOMEPL) ) {
			path = getHomeplFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLHC) ) {
			path = getPlhcFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CORE) ) {
			path = getCoreFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CLEA) ) {
			path = getCleaFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FEEDER) ) {
//			path = getFeederFolder(orderNo);
			path = getWorkFolder(jobDate, jobType, jobSeq);
			
		} else if(imgType.equals(_IMG_TYPE_KINDIMG)) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_EORDIMG) ) {
			path = getEtcOrderFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN4) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN5) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN6) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_TGCL) ) {
			path = getCustomerFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CSQ) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLIMG) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLMP4) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_WMAP) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_UNIF) ) {
			path = getUniformFolder(procID);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_TCPI) ) {
			path = getTcpiFolder(procID);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLM) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FIRM) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_OPIN) ) {
			path = getMovieCustFolder(orderNo);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_MENT) ) {
			path = getMentFolder(orderNo, jobDate);
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_LADD) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_BILL) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
			
		} 
		else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN7) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN8) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HYBRID) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QM) ) {
			path = getqmImageFolder(jobDate);
		
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_QM) ) {
			path = getQmMovFolder(jobDate);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_RVIM) ) {
			path = getRvimWorkFolder(orderNo, jobDate);
			
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_CPLR) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_CPLM) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_ASTP) ) {
			path = getMovieWorkFolder(jobDate, jobType, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN9) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_QABM) ) {
			path = getWorkFolder(jobDate, jobType, jobSeq);
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HPLB) ) {
			path = getHplbFolder(orderNo);
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QR_HOMECARE) ) {
			path = getQrh3WorkFolder(orderNo);
		}
		
		else {
			path = "";
		}
		
		return path;
	}

}
