/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: ZooKeeperCriticalThread.java, v 0.1 2018年4月23日 下午1:40:20 en.xuze@alipay.com Exp $
 */
public class ZooKeeperCriticalThread extends ZooKeeperThread {

    //    private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperCriticalThread.class);
    private final ZooKeeperServerListener listener;

    public ZooKeeperCriticalThread(String threadName, ZooKeeperServerListener listener) {
        super(threadName);
        this.listener = listener;
    }

    /**
     * This will be used by the uncaught exception handler and make the system
     * exit.
     *
     * @param threadName
     *            - thread name
     * @param e
     *            - exception object
     */
    @Override
    protected void handleException(String threadName, Throwable e) {
        //        LOG.error("Severe unrecoverable error, from thread : {}", threadName, e);
        listener.notifyStopping(threadName, ExitCode.UNEXPECTED_ERROR);
    }
}
