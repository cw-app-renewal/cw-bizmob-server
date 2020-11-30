package connector.sqlite.doctor.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

@Deprecated
public interface DoctorBomMapper {

	
	@Update(
		"CREATE TABLE IF NOT EXISTS PRODUCT (  " +
	    " GOODS_CD			TEXT		/* 제품코드 */ " +
	    ",PART_CD			TEXT		/* 부품코드 */ " + 
	    ",PRDHA 				TEXT		/* 제품계층구조 */ " + 
	    ",PART_NM			TEXT		/* 부품명 */ " + 
	    ",FILTER_YN			TEXT		/* 필터여부 */ " + 
	    ",EXAM_YN 			TEXT		/* 점검코드여부 */ " + 
	    ",BARCODE_YN		TEXT		/* 바코드필수여부 */ " +  
	    ",EXPEND_YN			TEXT		/* 소모성자재여부 */ " +
	    ",GB_TP 				TEXT		/* 설치자재여부(아답터 피팅류) */ " +
	    ",SALE_AMT			TEXT		/* 판매금액 */ " +
	    ",EXTWG_TP			TEXT		/* 자재세부구분(A:아답터류) */ " +
	    ",HEART_PLUS_YN 	TEXT		/* 순환키트등록대상여부 */ " +
	    ",SLT_PROC_TP		TEXT		/* 지정부품대상처리형태 */ " +
	    ",LIMIT_QTY     		TEXT		/* 입력제한수향 */  " +
	    " ) " 
	)
	int createProductTable();
	
	@Update(
		"CREATE INDEX IDX_GOODS_CD ON PRODUCT ( " + 
		"	    GOODS_CD ASC " + 
		"	    ,PART_CD ASC " + 
		"	)	"
	)
	int createProductIndex();
	
	
	@Update(
			"DROP TABLE IF EXISTS PRODUCT "
	)	
	int dropProductTable();
	
	@Delete(
			"DELETE FROM PRODUCT "
	)
	int deleteProductData();
	
	@Update( "create table test (dummy text) ")
	int createTestTable();
	
}
