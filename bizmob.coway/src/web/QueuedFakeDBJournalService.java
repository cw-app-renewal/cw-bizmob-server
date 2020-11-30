/*
 * QueuedFakeDBJournalService.java
 * Copyright 2010, MOBILE C&C LTD. All rights reserved.
 * 2011. 9. 8.
 */

package web;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;

import com.mcnc.smart.common.logging.ILogger;
import com.mcnc.smart.common.logging.LoggerService;
import com.mcnc.smart.journal.IQueuedJournalService;
import com.mcnc.smart.journal.JournalInfo;

/**
 * @author spchang
 * @version 
 *
 */
public class QueuedFakeDBJournalService implements IQueuedJournalService {
    private ILogger logger  = LoggerService.getLogger( QueuedFakeDBJournalService.class );
    private int queueSize = 0;
    private ArrayBlockingQueue<JournalInfo> queue = null;

    private static final long _SLEEP_TIME = 1000;

    /**
     * Queue의 기본 크기
     */
    public static final int MAX_JOURNAL_QUEUE_SIZE = 30;
    /**
     * Queue에 block 되었을 때 기본 대기 시간
     */
    public static final long QUEUE_DELAY_SECONDS = 5;

    public QueuedFakeDBJournalService() {
    	this(MAX_JOURNAL_QUEUE_SIZE);
    }
    
    public QueuedFakeDBJournalService(int size) {
        this.queueSize = size;
        queue = new ArrayBlockingQueue<JournalInfo>(this.queueSize, false);
    }
    
	/**
	 * {@inheritDoc}
	 * @param arg0
	 */
	public void keep(JournalInfo info) {
        logger.debug("Start::keep()");
        
        try {
            if(!queue.offer(info, QUEUE_DELAY_SECONDS, TimeUnit.SECONDS)) {
                // queue가 가득 찼음.
                flushAll();
                queue.offer(info);
            }
            
            // queue에 80% 이상 차면 flush
            if(queue.size() >= queueSize * 0.8) {
                flushAll();
            }
        } catch (InterruptedException e) {
            logger.error("Failed to keep journal! Cause: ", e);
        } finally {
            logger.debug("End::keep()");
        }
	}

	/**
	 * {@inheritDoc}
	 */
    @Scheduled(fixedRate = 600000) 
	public void flush() {
        logger.debug("Start::flush()");
        logger.debug("Periodic Flushing journals every 600000ms!");
        
        if(queue.peek() != null) {
            flushAll();
        }
        
        logger.debug("End::flush()");
	}
    
    protected void flushAll() {
        ArrayList<JournalInfo> arrayList = new ArrayList<JournalInfo>(queueSize);
        
        try {
            int nSize = queue.drainTo(arrayList, queueSize);
            
            if (nSize > 0) {
            	Thread.sleep(_SLEEP_TIME);
            	logger.debug("# of Jourals : " + arrayList.size());
            }
        } catch (Exception e) {
            logger.warn("Failed to flush journal! Cause: " + e.getMessage());
        } finally {
            logger.debug("Finihsed to flush journals!");
        }
    }
}
