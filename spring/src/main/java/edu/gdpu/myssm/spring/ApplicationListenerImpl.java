package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.spring.ApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 23:28:58
 */
public class ApplicationListenerImpl implements ApplicationListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private long startTime;
    private long endTime;
    private long scanStart;
    private long scanEnd;

    @Override
    public void begin() {
        logger.info("MySSM start running ");
        startTime = System.currentTimeMillis();
        logger.info("start scanning");
        scanStart = System.currentTimeMillis();
    }

    @Override
    public void completeScan() {
        logger.info("complete scanning");
        scanEnd = System.currentTimeMillis();
        logger.info("consume "+(scanEnd-scanStart)+"ms");
    }

    @Override
    public void prepared() {
        logger.info("complete inject and aspect");
    }

    @Override
    public void start() {
        endTime = System.currentTimeMillis();
        logger.info("application start,consume "+(endTime-startTime)+"ms");
    }
}
