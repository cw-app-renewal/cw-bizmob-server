package batch.scheduler;

import java.util.Timer;
import java.util.TimerTask;

@Deprecated
public class SystemOutTimerService {

	private Timer timer;
	
//	@Autowired
//	CodyBatchZip codyBatchZip;
//	
//	@Autowired
//	DoctorBatchZip doctorBatchZip;
	
	public void activate() {
		
		timer = new Timer();
		timer.schedule(new SysteomOutTask(), 5000, 100000);
		
	}
	
	public void deactivate() {
		if(timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	class SysteomOutTask extends TimerTask {

		@Override
		public void run() {
			System.out.println("음... 시킨대로 했는데도 배치가 실행되는 구만~~(이건 타이머로 한것임)");
			
			/*
			System.out.println(" 먼저 코디 배치를 실행~~~~!!");
			codyBatchZip.codeBatchDatabase();
			
			System.out.println(" 다음 닥터 배치를 실행~~~~!!");
			doctorBatchZip.doctorBatchDatabase();
			
			System.out.println("!! 배치 실행이 끝났어..^^ 다음번 실행까지 안녕~~!!@-@");
			*/
		}
		
	}
}
