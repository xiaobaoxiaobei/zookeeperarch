/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: ZooKeeperThread.java, v 0.1 2018年4月23日 下午1:36:17 en.xuze@alipay.com Exp $
 */
/**
 * This is the main class for catching all the uncaught exceptions thrown by the
 * threads.
 */
public class ZooKeeperThread extends Thread {

    private UncaughtExceptionHandler uncaughtExceptionalHandler = new UncaughtExceptionHandler() {

        public void uncaughtException(Thread t, Throwable e) {
            handleException(t.getName(), e);
        }
    };

    public ZooKeeperThread(String threadName) {
        super(threadName);
        setUncaughtExceptionHandler(uncaughtExceptionalHandler);
    }

    /**
     * This will be used by the uncaught exception handler and just log a
     * warning message and return.
     * 
     * @param thName
     *            - thread name
     * @param e
     *            - exception object
     */
    protected void handleException(String thName, Throwable e) {}
}
