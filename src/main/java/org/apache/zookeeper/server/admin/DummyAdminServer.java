/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server.admin;

import org.apache.zookeeper.server.ZooKeeperServer;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: DummyAdminServer.java, v 0.1 2018年4月24日 上午11:05:27 en.xuze@alipay.com Exp $
 */
public class DummyAdminServer implements AdminServer {

    /** 
     * @see org.apache.zookeeper.server.admin.AdminServer#start()
     */
    @Override
    public void start() throws AdminServerException {}

    /** 
     * @see org.apache.zookeeper.server.admin.AdminServer#shutdown()
     */
    @Override
    public void shutdown() throws AdminServerException {}

    /** 
     * @see org.apache.zookeeper.server.admin.AdminServer#setZooKeeperServer(org.apache.zookeeper.server.ZooKeeperServer)
     */
    @Override
    public void setZooKeeperServer(ZooKeeperServer zkServer) {}
}
