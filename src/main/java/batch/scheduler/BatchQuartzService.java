package batch.scheduler;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import batch.cody.CodyBatchZip;
import batch.doctor.DoctorBatchZip;
import batch.user.close.UserCloseBatch;
public class BatchQuartzService extends QuartzJobBean {

	private static final Logger logger = LoggerFactory.getLogger(BatchQuartzService.class);

	//코디 디비 파일 생성
	CodyBatchZip codyBatchZip;
	
	//닥터 디비 파일 생성
	DoctorBatchZip doctorBatchZip;
	
	//사용자 해지 배치 
	UserCloseBatch userCloseBatch;

	public CodyBatchZip getCodyBatchZip() {
		return codyBatchZip;
	}
	public void setCodyBatchZip(CodyBatchZip codyBatchZip) {
		this.codyBatchZip = codyBatchZip;
	}

	public DoctorBatchZip getDoctorBatchZip() {
		return doctorBatchZip;
	}
	public void setDoctorBatchZip(DoctorBatchZip doctorBatchZip) {
		this.doctorBatchZip = doctorBatchZip;
	}

	public UserCloseBatch getUserCloseBatch() {
		return userCloseBatch;
	}
	public void setUserCloseBatch(UserCloseBatch userCloseBatch) {
		this.userCloseBatch = userCloseBatch;
	}
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		try {

			logger.debug("****************************************************** quartz event occur!! ******************************");
			//코디 배치
			logger.info("************* 코디 배치 Start !! *********************");
			codyBatchZip.codeBatchDatabase();
			logger.info("************* 코디 배치 End !! **********************");
	
			//닥터 배치
			logger.info("************* 닥터 배치 Start !! *********************");
			doctorBatchZip.doctorBatchDatabase();
			logger.info("************* 닥터 배치 End !! **********************");
			
			//사용자 해지 배치
			logger.info("************* 사용자 해지 배치 Start !! *******************");
			userCloseBatch.executeUserCloseBatch();
			logger.info("************* 사용자 해지 배치 End !! *******************");
			
			
			//
		} catch (Exception e) {
			logger.error("", e);
		}
		
	}
	
	
}
