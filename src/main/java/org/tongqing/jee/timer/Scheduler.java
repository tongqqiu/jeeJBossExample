package org.tongqing.jee.timer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;

/**
 * User: TQiu
 * Date: 3/31/2014
 */

@Startup
@Singleton
public class Scheduler {

    public static final long DEFAULT_TIMER_INITIAL_INTERVAL = 60L * 1000L;			// 60 seconds
    public static final long DEFAULT_TIMER_INTERVAL = 5L * 1000L;					// 5 seconds

    @Resource
    TimerService timerService;
    private Timer timer=null;


    @PostConstruct
    private void startup()
    {
        // start the timer to process periodic tasks
        TimerConfig config = new TimerConfig();
        config.setPersistent(false);
        this.timer = this.timerService.createIntervalTimer(DEFAULT_TIMER_INITIAL_INTERVAL, DEFAULT_TIMER_INTERVAL, config);
    }

    @PreDestroy
    private void shutdown()
    {
        if (this.timer != null)
        {
            try
            {
                this.timer.cancel();
            }
            catch (Throwable e)
            {
                // ignore
            }
        }
    }

    @Timeout
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void handleTimer()
    {
		System.out.println("\t handleTimer called...");
    }
}
