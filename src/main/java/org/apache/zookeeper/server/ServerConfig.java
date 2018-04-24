/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: ServerConfig.java, v 0.1 2018年4月23日 下午4:21:26 en.xuze@alipay.com Exp $
 */
public class ServerConfig {

    protected InetSocketAddress clientPortAddress;
    protected InetSocketAddress secureClientPortAddress;
    protected File dataDir;
    protected File dataLogDir;
    protected int tickTime = ZooKeeperServer.DEFAULT_TICK_TIME;
    protected int maxClientCnxns;
    /** defaults to -1 if not set explicitly */
    protected int minSessionTimeout = -1;
    /** defaults to -1 if not set explicitly */
    protected int maxSessionTimeout = -1;

    public InetSocketAddress getClientPortAddress() {
        return clientPortAddress;
    }

    public InetSocketAddress getSecureClientPortAddress() {
        return secureClientPortAddress;
    }

    public File getDataDir() {
        return dataDir;
    }

    public File getDataLogDir() {
        return dataLogDir;
    }

    public int getTickTime() {
        return tickTime;
    }

    public int getMaxClientCnxns() {
        return maxClientCnxns;
    }

    /** minimum session timeout in milliseconds, -1 if unset */
    public int getMinSessionTimeout() {
        return minSessionTimeout;
    }

    /** maximum session timeout in milliseconds, -1 if unset */
    public int getMaxSessionTimeout() {
        return maxSessionTimeout;
    }

    /**
     * 
     * @param string
     */
    public void parse(String string) {}

    public void parse(String[] args) {
        if (args.length < 2 || args.length > 4) {
            throw new IllegalArgumentException("Invalid number of arguments:" + Arrays.toString(args));
        }
        clientPortAddress = new InetSocketAddress(Integer.parseInt(args[0]));
        dataDir = new File(args[1]);
        dataLogDir = dataDir;
        if (args.length >= 3) {
            tickTime = Integer.parseInt(args[2]);
        }
        if (args.length == 4) {
            maxClientCnxns = Integer.parseInt(args[3]);
        }
    }
}
