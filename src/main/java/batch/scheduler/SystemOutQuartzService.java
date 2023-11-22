package batch.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Deprecated
public class SystemOutQuartzService extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		try {
			System.out.println("음... 따라 했는데도 배치가 실행되는 구만~~(이건 쿼츠로 한것임)");
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}

}
