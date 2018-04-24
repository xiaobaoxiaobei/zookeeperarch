/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: ZKDatabase.java, v 0.1 2018年4月23日 下午2:02:31 en.xuze@alipay.com Exp $
 */
public class ZKDatabase {

    /**
     * make sure on a clear you take care of
     * all these members.
     */
    protected DataTree dataTree;
    protected ConcurrentHashMap<Long, Integer> sessionsWithTimeouts;
    protected FileTxnSnapLog snapLog;
    protected long minCommittedLog, maxCommittedLog;
    /**
     * Default value is to use snapshot if txnlog size exceeds 1/3 the size of snapshot
     */
    public static final String SNAPSHOT_SIZE_FACTOR = "zookeeper.snapshotSizeFactor";
    public static final double DEFAULT_SNAPSHOT_SIZE_FACTOR = 0.33;
    private double snapshotSizeFactor;
    public static final int commitLogCount = 500;
    protected static int commitLogBuffer = 700;
    //    protected LinkedList<Proposal> committedLog = new LinkedList<Proposal>();
    protected ReentrantReadWriteLock logLock = new ReentrantReadWriteLock();
    volatile private boolean initialized = false;

    /**
     * the filetxnsnaplog that this zk database
     * maps to. There is a one to one relationship
     * between a filetxnsnaplog and zkdatabase.
     * @param snapLog the FileTxnSnapLog mapping this zkdatabase
     */
    public ZKDatabase(FileTxnSnapLog snapLog) {
        dataTree = new DataTree();
        sessionsWithTimeouts = new ConcurrentHashMap<Long, Integer>();
        this.snapLog = snapLog;
        try {
            snapshotSizeFactor = Double.parseDouble(System.getProperty(SNAPSHOT_SIZE_FACTOR, Double.toString(DEFAULT_SNAPSHOT_SIZE_FACTOR)));
            if (snapshotSizeFactor > 1) {
                snapshotSizeFactor = DEFAULT_SNAPSHOT_SIZE_FACTOR;
                //                LOG.warn("The configured {} is invalid, going to use " + "the default {}", SNAPSHOT_SIZE_FACTOR, DEFAULT_SNAPSHOT_SIZE_FACTOR);
            }
        } catch (NumberFormatException e) {
            //            LOG.error("Error parsing {}, using default value {}", SNAPSHOT_SIZE_FACTOR, DEFAULT_SNAPSHOT_SIZE_FACTOR);
            snapshotSizeFactor = DEFAULT_SNAPSHOT_SIZE_FACTOR;
        }
        //        LOG.info("{} = {}", SNAPSHOT_SIZE_FACTOR, snapshotSizeFactor);
    }
}
