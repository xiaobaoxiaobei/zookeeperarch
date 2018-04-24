/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: FinalRequestProcessor.java, v 0.1 2018年4月23日 下午1:33:21 en.xuze@alipay.com Exp $
 */
public class FinalRequestProcessor implements RequestProcessor {

    ZooKeeperServer zks;

    public FinalRequestProcessor(ZooKeeperServer zks) {
        this.zks = zks;
    }

    /** 
     * @see org.apache.zookeeper.server.RequestProcessor#processRequest(org.apache.zookeeper.server.Request)
     */
    public void processRequest(Request request) throws RequestProcessorException {}

    /** 
     * @see org.apache.zookeeper.server.RequestProcessor#shutdown()
     */
    public void shutdown() {}
}
