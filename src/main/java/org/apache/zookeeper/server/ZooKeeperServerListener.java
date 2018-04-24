/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: ZooKeeperServerListener.java, v 0.1 2018年4月23日 下午1:41:46 en.xuze@alipay.com Exp $
 */
public interface ZooKeeperServerListener {

    /**
     * This will notify the server that some critical thread has stopped.
     * It usually takes place when fatal error occurred.
     *
     * @param threadName
     *            - name of the thread
     * @param errorCode
     *            - error code
     */
    void notifyStopping(String threadName, int errorCode);
}
