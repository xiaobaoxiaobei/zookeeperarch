/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * This RequestProcessor logs requests to disk. It batches the requests to do
 * the io efficiently. The request is not passed to the next RequestProcessor
 * until its log has been synced to disk.
 *
 * SyncRequestProcessor is used in 3 different cases
 * 1. Leader - Sync request to disk and forward it to AckRequestProcessor which
 *             send ack back to itself.
 * 2. Follower - Sync request to disk and forward request to
 *             SendAckRequestProcessor which send the packets to leader.
 *             SendAckRequestProcessor is flushable which allow us to force
 *             push packets to leader.
 * 3. Observer - Sync committed request to disk (received as INFORM packet).
 *             It never send ack back to the leader, so the nextProcessor will
 *             be null. This change the semantic of txnlog on the observer
 *             since it only contains committed txns.
 */
public class SyncRequestProcessor extends ZooKeeperCriticalThread implements RequestProcessor {

    private final ZooKeeperServer zks;
    private final RequestProcessor nextProcessor;
    volatile private boolean running;
    private final LinkedBlockingQueue<Request> queuedRequests = new LinkedBlockingQueue<Request>();

    /**
     * @param zooKeeperServer
     * @param finalProcessor
     */
    public SyncRequestProcessor(ZooKeeperServer zks, RequestProcessor nextProcessor) {
        super("SyncThread:" + zks.getServerId(), zks.getZooKeeperServerListener());
        this.zks = zks;
        this.nextProcessor = nextProcessor;
        running = true;
    }

    /** 
     * @see org.apache.zookeeper.server.RequestProcessor#processRequest(org.apache.zookeeper.server.Request)
     */
    public void processRequest(Request request) throws RequestProcessorException {
        queuedRequests.add(request);
    }

    /** 
     * @see org.apache.zookeeper.server.RequestProcessor#shutdown()
     */
    public void shutdown() {}

    @Override
    public void run() {
        while (true) {
            try {
                Request si = queuedRequests.take();
                nextProcessor.processRequest(si);
            } catch (Exception e) {
            }
        }
    }
}
