/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

import org.apache.zookeeper.server.RequestProcessor.RequestProcessorException;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;

/**
 * This class implements a simple standalone ZooKeeperServer. It sets up the
 * following chain of RequestProcessors to process requests:
 * PrepRequestProcessor -> SyncRequestProcessor -> FinalRequestProcessor
 */
public class ZooKeeperServer {

    public static final int DEFAULT_TICK_TIME = 3000;
    /** value of -1 indicates unset, use default */
    protected int minSessionTimeout = -1;
    /** value of -1 indicates unset, use default */
    protected int maxSessionTimeout = -1;
    private FileTxnSnapLog txnLogFactory = null;
    private ZKDatabase zkDb;
    protected int tickTime = DEFAULT_TICK_TIME;
    protected RequestProcessor firstProcessor;
    private final ZooKeeperServerListener listener;

    protected enum State {
        INITIAL, RUNNING, SHUTDOWN, ERROR
    }

    public ZooKeeperServer() {
        //        serverStats = new ServerStats(this);
        listener = new ZooKeeperServerListenerImpl(this);
    }

    public ZooKeeperServer(FileTxnSnapLog txnLogFactory, int tickTime, int minSessionTimeout, int maxSessionTimeout, ZKDatabase zkDb) {
        //        serverStats = new ServerStats(this);
        this.txnLogFactory = txnLogFactory;
        this.zkDb = zkDb;
        this.tickTime = tickTime;
        setMinSessionTimeout(minSessionTimeout);
        setMaxSessionTimeout(maxSessionTimeout);
        listener = new ZooKeeperServerListenerImpl(this);
    }

    public synchronized void startup() {
        //        if (sessionTracker == null) {
        //            createSessionTracker();
        //        }
        //        startSessionTracker();
        setupRequestProcessors();
        //        registerJMX();
        setState(State.RUNNING);
        //        notifyAll();
    }

    protected void setupRequestProcessors() {
        RequestProcessor finalProcessor = new FinalRequestProcessor(this);
        RequestProcessor syncProcessor = new SyncRequestProcessor(this, finalProcessor);
        ((SyncRequestProcessor) syncProcessor).start();
        firstProcessor = new PrepRequestProcessor(this, syncProcessor);
        ((PrepRequestProcessor) firstProcessor).start();
    }

    public void submitRequest(Request si) {
        try {
            firstProcessor.processRequest(si);
        } catch (RequestProcessorException e) {
        }
    }

    public void setMinSessionTimeout(int min) {
        this.minSessionTimeout = min == -1 ? tickTime * 2 : min;
        //        LOG.info("minSessionTimeout set to {}", this.minSessionTimeout);
    }

    public int getMaxSessionTimeout() {
        return maxSessionTimeout;
    }

    public void setMaxSessionTimeout(int max) {
        this.maxSessionTimeout = max == -1 ? tickTime * 20 : max;
        //        LOG.info("maxSessionTimeout set to {}", this.maxSessionTimeout);
    }

    public ZooKeeperServerListener getZooKeeperServerListener() {
        return listener;
    }

    /**
     * 
     * @param error
     */
    public void setState(State error) {}

    public long getServerId() {
        return 0;
    }

    public int getClientPort() {
        //        return serverCnxnFactory != null ? serverCnxnFactory.getLocalPort() : -1;
        return -1;
    }

    /**
     * 
     * @return
     */
    public boolean isRunning() {
        return false;
    }

    /**
     * 
     * @return
     */
    public Object getServerCnxnFactory() {
        return null;
    }
}
