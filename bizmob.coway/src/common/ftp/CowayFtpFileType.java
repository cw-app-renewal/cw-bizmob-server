package common.ftp;

public class CowayFtpFileType {

	protected static final String _IMG_TYPE_INSTALL = "1";		//설치 이미지
	protected static final String _IMG_TYPE_ADDRESS = "2";		//주소 이미지
	protected static final String _IMG_TYPE_MEMO = "3";			//메모 
	protected static final String _IMG_TYPE_GOODS = "4";		//제품
	protected static final String _IMG_TYPE_PARTS = "5";		//부품
	protected static final String _IMG_TYPE_QAIMG = "6";		//QA 품질정보
	protected static final String _IMG_TYPE_QAMP4 = "7";		//QA 품질정보 동영상
	protected static final String _IMG_TYPE_NAN = "8";			//난공사
	protected static final String _IMG_TYPE_PL = "9";			// PL
	protected static final String _IMG_TYPE_FIRE = "10";		// FIRE 이미지
	protected static final String _IMG_TYPE_NAN1 = "11";		// NAN1 이미지
	protected static final String _IMG_TYPE_NAN2 = "12";		// NAN2 이미지
	protected static final String _IMG_TYPE_NAN3 = "13";		// NAN3 이미지
	protected static final String _IMG_TYPE_INSTALL02 = "14";	// 설치 이미지02
	protected static final String _IMG_TYPE_HRT = "15";			// HTR 이미지
	protected static final String _IMG_TYPE_AFTER = "16";		// 홈케어 이미지
	protected static final String _IMG_TYPE_NOINST = "W";		// 작업 : 미설치 이미지
	protected static final String _IMG_TYPE_MIBUNJIN = "ff";	// 고객 : 미분진필터
	protected static final String _IMG_TYPE_RETURN = "17";		// 고객 : 설치 타사반환이미지
	protected static final String _IMG_TYPE_REC = "R";			// 고객 : 음성통화 녹취파일
	protected static final String _IMG_TYPE_QR = "18";			// 고객 : QR코드 등록 승인 요청
	protected static final String _IMG_TYPE_PLREC = "P";		// 고객 : PL음성통화 녹취파일
	protected static final String _IMG_TYPE_NOINST2 = "19";		// 고객 : 설치불가 대응자재 사용 이미지
	protected static final String _IMG_TYPE_ARTN = "20";		// 작업 : 아답타 미반환 사유 이미지
	protected static final String _IMG_TYPE_QRH1 = "21";		// 작업 : QR코드 등록 승인요청(홈케어닥터)
	protected static final String _IMG_TYPE_QRH2 = "22";		// 작업 : QR코드 등록 승인요청(홈케어닥터)
	protected static final String _IMG_TYPE_PMAT = "24";		// 고객 : 피해부품 이미지(닥터)
	protected static final String _IMG_TYPE_AIR = "25";			// 고객 : 에어컨동행동의 이미지(홈케어닥터)
	protected static final String _IMG_TYPE_HOMEPL = "26";		// 고객 : PL동행 동의 이미지(홈케어닥터)
	protected static final String _IMG_TYPE_PLHC = "27";		// 고객 : PL등록(코디)
	protected static final String _IMG_TYPE_CORE = "28";		// 작업 : 현장제보 이미지(코디)
	protected static final String _IMG_TYPE_CLEA = "29";		// 작업 : 위생모니터링 이미지(닥터)
	protected static final String _IMG_TYPE_FEEDER = "30";		// 작업 : 피더 스크류 이미지(코디)
	protected static final String _IMG_TYPE_KINDIMG = "31";		// 작업 : 착한문자 이미지(닥터)
	protected static final String _IMG_TYPE_EORDIMG = "32";		// 작업 : 제품실사 이미지(코디)

	protected static final String _IMG_TYPE_NAN4 = "33";		// 2016-10-06 (코디) 난 이미지_4
	protected static final String _IMG_TYPE_NAN5 = "34";		// 2016-10-06 (코디) 난 이미지_5
	protected static final String _IMG_TYPE_NAN6 = "35";		// 2016-10-06 (코디) 난 이미지_6
	
	protected static final String _IMG_TYPE_TGCL = "36";		// 고객 : 도시가스마감 이미지
	
	protected static final String _IMG_TYPE_CSQ = "37";		// 2017-03-14 CSQ 점검 후 촬영 이미지
	
	protected static final String _IMG_TYPE_PLIMG = "38";		// 2017-06-29 PL 현장제보 이미지 
	protected static final String _IMG_TYPE_PLMP4 = "39";		// 2017-06-29 PL 현장제보 동영상 
	protected static final String _IMG_TYPE_WMAP = "40";		// 2017-06-29 WATERMAP 간이측정 이미지 
	protected static final String _IMG_TYPE_UNIF = "41";		// 2017-08-07 uniform 이미지 
	protected static final String _IMG_TYPE_TCPI = "45";		// 2017-09-08 타사 고객 영업 추천 
	protected static final String _IMG_TYPE_PLM = "42";			// 2017-09-18 PL 동영상 
	protected static final String _IMG_TYPE_FIRM = "43";		// 2017-09-18 화재동영상 
	protected static final String _IMG_TYPE_OPIN = "44";		// 2017-09-18 닥터 소견서 이미지 
	protected static final String _IMG_TYPE_LADD = "46";		// 2017-10-18 사다리 이미지 
	protected static final String _IMG_TYPE_BILL = "47";		// 2017-10-18 영수증 이미지 
	
	protected static final String _IMG_TYPE_MENT = "48";		// 2018-01-15 멘토멘티 이미지
	protected static final String _IMG_TYPE_NAN7 = "49";		// 2018-03-29 (코디) 난 이미지_7
	protected static final String _IMG_TYPE_NAN8 = "50";		// 2018-03-29 (코디) 난 이미지_8
	
	protected static final String _IMG_TYPE_HYBRID = "52";		// 2018-04-05 하이브리드 탑퍼 이미지
	protected static final String _IMG_TYPE_QM = "53";		// 2018-04-05 하이브리드 탑퍼 이미지
	
	protected static final String _MOV_TYPE_QM = "54";		// 2018-04-05 하이브리드 탑퍼 이미지
	protected static final String _IMG_TYPE_RVIM = "55";		// 2018-10-12 경쟁사 이미지
	
	protected static final String _MOV_TYPE_CPLR = "56";		// 2019-01-08 PL현장제보 동영상(코디)
	protected static final String _MOV_TYPE_CPLM = "57";		// 2018-01-08 PL동영상(코디)
	protected static final String _IMG_TYPE_ASTP = "58";		// 2018-01-08 PL동영상(코디)
	
	protected static final String _IMG_TYPE_NAN9 = "59";		// 2018-03-29 (코디) 난 이미지_9
	protected static final String _MOV_TYPE_QABM = "60";		// 2019-05-24 (코디) 미설치 동영상
	
	protected static final String _IMG_TYPE_HPLB = "61";		// 2019-05-24 (코디) PL 영수증 이미지
	protected static final String _IMG_TYPE_QR_HOMECARE = "62";	// 2019-08-28 (코디) QR 이미지(홈케어)
	
	public static final int _IMG_FLAG_DEFAULT_WORK = 0;
	public static final int _IMG_FLAG_WORK = 2;
	public static final int _IMG_FLAG_CUSTOMER = 1;
	public static final int _IMG_FLAG_MEMO = 3;
	
	//private String imgTypeName = "";
	//private String sapRfcName = "";
	
	public static int getCowayImageTypeFlag(String imgType) {
		
		if(imgType.equalsIgnoreCase(_IMG_TYPE_INSTALL) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_ADDRESS) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_INSTALL02) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_MIBUNJIN) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_RETURN) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_REC) ||			
			imgType.equalsIgnoreCase(_IMG_TYPE_PLREC) ||			
			imgType.equalsIgnoreCase(_IMG_TYPE_QR) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NOINST2) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_PMAT) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_PLHC) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_TGCL) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_AFTER) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_UNIF) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_MENT) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_QM) || //2018-05-24 QM이미지 구분값 C(고객)
			imgType.equalsIgnoreCase(_MOV_TYPE_QM) || //2018-07-04 QM 동영상 구분값 C(고객)
			imgType.equalsIgnoreCase(_IMG_TYPE_RVIM) || //2018-10-12 RVIM(경쟁사이미지) C(고객)
			imgType.equalsIgnoreCase(_IMG_TYPE_HPLB)	||
			imgType.equalsIgnoreCase(_IMG_TYPE_OPIN)) {
			
			return _IMG_FLAG_CUSTOMER;//고객이미지 (설치 이미지, 주소 이미지)
		} else if(imgType.equalsIgnoreCase(_IMG_TYPE_NAN) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_QAIMG) || 
			imgType.equalsIgnoreCase(_IMG_TYPE_QAMP4) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_PL) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_FIRE) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN1) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN2) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN3) || 
			imgType.equalsIgnoreCase(_IMG_TYPE_NOINST) || 
			imgType.equalsIgnoreCase(_IMG_TYPE_ARTN) || 
			imgType.equalsIgnoreCase(_IMG_TYPE_QRH1) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_QRH2) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_AIR) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_HOMEPL) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_CORE) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_CLEA) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_FEEDER) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_KINDIMG) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_EORDIMG) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN4) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN5) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN6) || 
			imgType.equalsIgnoreCase(_IMG_TYPE_HRT) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_CSQ) || 
			imgType.equalsIgnoreCase(_IMG_TYPE_PLIMG) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_PLMP4) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_WMAP) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_TCPI) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_PLM)  ||
			imgType.equalsIgnoreCase(_IMG_TYPE_FIRM) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_LADD) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_BILL) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN7) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN8) ||
			imgType.equalsIgnoreCase(_MOV_TYPE_CPLR) ||
			imgType.equalsIgnoreCase(_MOV_TYPE_CPLM) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_NAN9) ||
			imgType.equalsIgnoreCase(_MOV_TYPE_QABM) ||
			imgType.equalsIgnoreCase(_IMG_TYPE_HYBRID) ||
			
			imgType.equalsIgnoreCase(_IMG_TYPE_QR_HOMECARE)) {
			
			return _IMG_FLAG_WORK;//작업 이미지
		} else if(imgType.equalsIgnoreCase(_IMG_TYPE_MEMO)) {
			return _IMG_FLAG_MEMO;//영업노트 메모 이미지
		} else {
			return _IMG_FLAG_DEFAULT_WORK;//작업 이미지
		}

	}
	
	// !! imgTypeName은 최대 4자리 고정
	public static String getImgTypeName(String imgType) {
		
		if(imgType.equalsIgnoreCase(_IMG_TYPE_INSTALL)) {
			return "INST";

		} else if(imgType.equalsIgnoreCase(_IMG_TYPE_ADDRESS)) {
			return "ADDR";
		
		} else if(imgType.equalsIgnoreCase(_IMG_TYPE_NAN)) {
			return "NAN";
		
		} else if(imgType.equalsIgnoreCase(_IMG_TYPE_QAIMG)) {
			return "QA_IMG";
		
		} else if(imgType.equalsIgnoreCase(_IMG_TYPE_QAMP4)) {
			return "QA_MOV";
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PL) ) {
			return "PL_IMG";
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FIRE) ) {
			return "FIRE";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN1) ) {
			return "NAN1";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN2) ) {
			return "NAN2";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN3) ) {
			return "NAN3";

		} else  if ( imgType.equalsIgnoreCase(_IMG_TYPE_INSTALL02) ) {
			return "SP1";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HRT) ) {
			return "HRT1";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_AFTER) ) {
			return "AFTER";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NOINST) ) {
			return "NOINST";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_MIBUNJIN) ) {
			return "FF";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_RETURN) ) {
			return "RTN";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_REC) ) {
			return "REC";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QR) ) {
			return "QR";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLREC) ) {
			return "PL";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NOINST2) ) {
			return "NOI2";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_ARTN) ) {
			return "ARTN";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QRH1) ) {
			return "QRH1";
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QRH2) ) {
			return "QRH2";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PMAT) ) {
			return "PMAT";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_AIR) ) {
			return "AIR";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HOMEPL) ) {
			return "HCPL";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLHC) ) {
			return "PLHC";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CORE) ) {
			return "CORE";
			
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CLEA) ) {
			return "CLEA";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FEEDER) ) {
			return "FDSC";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_KINDIMG) ) {
			return "KIND";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_EORDIMG) ) {
			return "EORD";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN4) ) {
			return "NAN4";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN5) ) {
			return "NAN5";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN6) ) {
			return "NAN6";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_TGCL) ) {
			return "TGCL";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_CSQ) ) {
			return "CSQ";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLIMG) ) {
			return "PLRI";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLMP4) ) {
			return "PLRM";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_WMAP) ) {
			return "WATER";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_UNIF) ) {
			return "UNIF";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_TCPI) ) {
			return "TCPI";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_PLM) ) {
			return "PLM";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_FIRM) ) {
			return "FIRM";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_OPIN) ) {
			return "OPIN";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_MENT) ) {
			return "MENT";
		
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_LADD) ) {
			return "LADD";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_BILL) ) {
			return "BILL";
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN7) ) {
			return "NAN7";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN8) ) {
			return "NAN8";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HYBRID) ) {
			return "HYDR";

		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QM) ) {
			return "QM";
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_QM) ) {
			return "QMMOV";
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_RVIM) ) {
			return "RVIM";

		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_CPLR) ) {
				return "CPLR";
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_CPLM) ) {
			return "CPLM";
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_ASTP) ) {
			return "ASTP";
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_NAN9) ) {
			return "NAN9";
		} else if ( imgType.equalsIgnoreCase(_MOV_TYPE_QABM) ) {
			return "QABM";
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_HPLB) ) {
			return "HPLB";	
		} else if ( imgType.equalsIgnoreCase(_IMG_TYPE_QR_HOMECARE) ) {
					return "QRH3";	
		 
		} else {
			return "";
		}
		
	}
	
	@Deprecated
	public static String getSapRfcName(String imgType) {
		
		if(imgType.equals(_IMG_TYPE_INSTALL) == true) {
			return "INST";
		} else if(imgType.equals(_IMG_TYPE_ADDRESS) == true) {
			return "ADDR";
		} else if(imgType.equals(_IMG_TYPE_NAN) == true) {
			return "NAN";
		} else if(imgType.equals(_IMG_TYPE_QAIMG) == true) {
			return "QA_IMG";
		} else if(imgType.equals(_IMG_TYPE_QAMP4) == true) {
			return "QA_MOV";
		} else if(imgType.equals(_IMG_TYPE_KINDIMG) == true) {
			return "KIND";
		} else {
			return "";
		}
		
	}

}
 