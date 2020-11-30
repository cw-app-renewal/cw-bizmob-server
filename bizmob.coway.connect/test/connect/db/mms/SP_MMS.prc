CREATE OR REPLACE PROCEDURE WJSMS.SP_MMS(
    v_rtn           OUT NUMBER,    /* SQL Code                                          */
    v_msg           OUT VARCHAR2,  /* 작업결과                                          */
    v_rcv_phn_id    IN     VARCHAR2,  /* 받는 사람 휴대폰 번호    (공백 없이 숫자만 15 가능) */
    v_callback      IN     VARCHAR2,  /* 회신번호 (공백 없이 숫자만 15 가능)               */
    v_snd_title        IN     VARCHAR2,  /* 메시지 제목(80)                                   */
    v_snd_msg         IN     VARCHAR2,  /* 메시지 내용(2000)                                   */
    v_rsrvd_id        IN     VARCHAR2,   /* 호출한 PG ID(업체에서 임의로 사용(64)             */
    v_image_path1   IN     VARCHAR2,  /* 이미지1 경로(256)                                   */
    v_image_path2   IN     VARCHAR2,  /* 이미지1 경로(256)                                   */
    v_image_path3   IN     VARCHAR2  /* 이미지1 경로(256)                                   */
)
/*******************************************************************************************
  Descriptions   : 업무중에  발생된 긴급내용을 MMS로 전송
  Arguments      : MMS 전송
  History
********************************************************************************************
  작업일자      작성자     작업내용
********************************************************************************************
  2010/05/25    박성균     KT MMS 모듈 연동
********************************************************************************************/
IS
    ln_dummy1              VARCHAR2(11);
    ln_dummy2              VARCHAR2(11);
    ln_rsrvd_id            VARCHAR2(2);
    ln_msg_size            NUMBER(3) := 0;
    v_ecd                  VARCHAR2(5);

BEGIN
    v_rtn  := 0;
    v_ecd := '00000';

    /* Validation */
    IF LENGTH(REPLACE(v_rcv_phn_id,'-','')) > 13 OR ( LENGTH(REPLACE(v_rcv_phn_id,'-','')) >= 0 AND LENGTH(REPLACE(v_rcv_phn_id,'-','')) < 10) THEN
        v_msg  := '수신자 핸드폰번호가  부적합합니다.(10~13자리)';
        v_ecd  := 'P1003';
        v_rtn  := 1;
        --RETURN;
    ELSIF v_rcv_phn_id IS NULL THEN
        v_msg  := '수신자 핸드폰번호가 존재하지 않습니다.';
        v_ecd  := 'P1002';
        v_rtn  := 2;
        --RETURN;
    ELSIF v_rsrvd_id IS NULL  THEN
        v_msg  := '사용부서ID가 없습니다.' ;
        v_ecd  := 'P1008';
        v_rtn  := 3;
        --RETURN;
    END IF;
    
          /* 발송아이디가 발급된 아이디인지 체크*/
    BEGIN
        SELECT fn_rsrvdIdCk(v_rsrvd_id)
        INTO ln_rsrvd_id
        FROM dual;
    END;

    IF ln_rsrvd_id='N' THEN
        v_msg  := '미발급 아이디를 사용하였습니다.' ;
        v_ecd  := 'P1010';
        v_rtn  := 4;
        --RETURN;
    END IF;

    /* 전화번호가 숫자인지 체크 */
    BEGIN
        SELECT TO_NUMBER(REPLACE(v_rcv_phn_id,'-','')),TO_NUMBER(REPLACE(v_callback,'-',''))
        INTO ln_dummy1, ln_dummy2
        FROM dual;

        EXCEPTION
        WHEN OTHERS THEN
            v_msg  := '핸드폰 번호는 숫자만 가능합니다.' ;
            v_ecd  := 'P1003';
            v_rtn  := 5;
            --RETURN;
    END;

    /* 전송 메세지 2000Byte 체크 (VSIZE -> LENGTHB) */
    BEGIN
        SELECT LENGTHB(v_snd_msg)
        INTO ln_msg_size
        FROM dual;
    END;

    IF ln_msg_size > 20000 OR ln_msg_size = 0 THEN
        v_msg  := '전송할수 있는 문자수를 초과하거나 전송할 내용이 없습니다.';
        v_ecd  := 'P1007';
        v_rtn  := 6;
        --RETURN;
    END IF;
    
    
    /* 전송 제목 80Byte 체크 (VSIZE -> LENGTHB) */
    BEGIN
        SELECT LENGTHB(v_snd_title)
        INTO ln_msg_size
        FROM dual;
    END;

    IF ln_msg_size > 80 OR ln_msg_size = 0 THEN
        v_msg  := '전송할수 있는 제목의 문자수를 초과하거나 전송할 제목이 없습니다.';
        v_ecd  := 'P1007';
        v_rtn  := 7;
        --RETURN;
    END IF;

    IF v_rtn = 0 THEN

        /* MMS전송내역 생성 (KT)*/
        BEGIN
                    
             INSERT INTO WJSMS.KT_MMS (
                    cmp_msg_id,
                    kt_msg_id,
                    rcv_phn_id,
                    callback,
                    MSG_TITLE,
                    snd_msg,
                    IMAGE_PATH1,
                    IMAGE_PATH2,
                    IMAGE_PATH3,
                    msg_gb,
                    wrt_dttm,
                    snd_dttm,
                    snd_month,
                    rsrvd_id,
                    TRAN_PR,
                    ORIGIN
                    )
            VALUES (
                    TRIM(WJSMS.KT_MMS_PR01.NEXTVAL),
                    WJSMS.KT_MMS_PR02.NEXTVAL,
                    NVL(TRIM(REPLACE(v_rcv_phn_id,'-','')),'00000000000'),
                    TRIM(REPLACE(v_callback,'-','')),
                    TRIM(v_snd_title),
                    TRIM(v_snd_msg),
                    TRIM(v_image_path1),
                    TRIM(v_image_path2),
                    TRIM(v_image_path3),
                    'D',
                    TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),
                    TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),
                    substr( TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') , 0, 6), 
                    TRIM(v_rsrvd_id),
                    NULL,
                    'WEBMMS'
                    );
        END;
        
        
        v_msg  := '전송성공';
        v_rtn  := 0;

    ELSE

        BEGIN

            INSERT INTO WJSMS.WJ_MMS_ERROR (
                    wrt_dttm,
                    snd_dttm,
                    rcv_phn_id,
                    callback,
                    snd_msg,
                    IMAGE_PATH1,
                    IMAGE_PATH2,
                    IMAGE_PATH3,
                    rsrvd_id,
                    TRAN_PR,
                    ORIGIN,
                    ERROR_CD,
                    ERROR_MS
                    )
            VALUES (
                    TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),
                    TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS'),
                    REPLACE(v_rcv_phn_id,'-',''),
                    REPLACE(v_callback,'-',''),
                    v_snd_msg,
                    v_image_path1,
                    v_image_path2,
                    v_image_path3,
                    v_rsrvd_id,
                    NULL,
                    'WEBMMS',
                    v_ecd,
                    'WEBMMS: ' || v_msg
                    );
        END;

    END IF;


    EXCEPTION
    WHEN OTHERS THEN
        v_rtn  := 9;
        v_msg  := '['||SQLCODE||']MMS전송내역 생성중  에러:[SVC] ' ||v_rsrvd_id;
        --v_msg  := '[]MMS전송내역 생성중  에러:[SVC] ' ||v_rsrvd_id;

END SP_MMS; 
/

