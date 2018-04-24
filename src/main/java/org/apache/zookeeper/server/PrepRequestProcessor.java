/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: PrepRequestProcessor.java, v 0.1 2018年4月23日 下午1:31:40 en.xuze@alipay.com Exp $
 */
public class PrepRequestProcessor extends ZooKeeperCriticalThread implements RequestProcessor {

    ZooKeeperServer zks;
    private final RequestProcessor nextProcessor;
    LinkedBlockingQueue<Request> submittedRequests = new LinkedBlockingQueue<Request>();

    /**
     * @param zooKeeperServer
     * @param syncProcessor
     */
    public PrepRequestProcessor(ZooKeeperServer zks, RequestProcessor nextProcessor) {
        super("ProcessThread(sid:" + zks.getServerId() + " cport:" + zks.getClientPort() + "):", zks.getZooKeeperServerListener());
        this.nextProcessor = nextProcessor;
        this.zks = zks;
    }

    /** 
     * @see org.apache.zookeeper.server.RequestProcessor#processRequest(org.apache.zookeeper.server.Request)
     */
    public void processRequest(Request request) throws RequestProcessorException {
        submittedRequests.add(request);
    }

    /** 
     * @see org.apache.zookeeper.server.RequestProcessor#shutdown()
     */
    public void shutdown() {}

    @Override
    public void run() {
        try {
            while (true) {
                Request request = submittedRequests.take();
                nextProcessor.processRequest(request);
                //                long traceMask = ZooTrace.CLIENT_REQUEST_TRACE_MASK;
                //                if (request.type == OpCode.ping) {
                //                    traceMask = ZooTrace.CLIENT_PING_TRACE_MASK;
                //                }
                //                if (LOG.isTraceEnabled()) {
                //                    ZooTrace.logRequest(LOG, traceMask, 'P', request, "");
                //                }
                //                if (Request.requestOfDeath == request) {
                //                    break;
                //                }
                //                pRequest(request);
            }
            //        } catch (RequestProcessorException e) {
            //            if (e.getCause() instanceof XidRolloverException) {
            //                LOG.info(e.getCause().getMessage());
            //            }
            //            handleException(this.getName(), e);
        } catch (Exception e) {
            //            handleException(this.getName(), e);
        }
        //        LOG.info("PrepRequestProcessor exited loop!");
    }
}
