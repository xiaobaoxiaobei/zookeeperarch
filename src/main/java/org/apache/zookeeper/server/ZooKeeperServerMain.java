/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server;

import java.io.IOException;
import org.apache.zookeeper.server.admin.AdminServer;
import org.apache.zookeeper.server.admin.AdminServerFactory;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: ZooKeeperServerMain.java, v 0.1 2018年4月23日 下午2:00:31 en.xuze@alipay.com Exp $
 */
public class ZooKeeperServerMain {

    private static final Logger LOG = LoggerFactory.getLogger(ZooKeeperServerMain.class);
    private static final String USAGE = "Usage: ZooKeeperServerMain configfile | port datadir [ticktime] [maxcnxns]";
    private AdminServer adminServer;

    public static void main(String[] args) throws IOException {
        args = new String[] {"6666", "/tmp/zookeeper"};
        ZooKeeperServerMain main = new ZooKeeperServerMain();
        ServerConfig config = new ServerConfig();
        if (args.length == 1) {
            config.parse(args[0]);
        } else {
            config.parse(args);
        }
        main.runFromConfig(config);
        System.in.read();
        //        try {
        ////            main.initializeAndRun(args);
        //            
        //        } catch (IllegalArgumentException e) {
        //            LOG.error("Invalid arguments, exiting abnormally", e);
        //            LOG.info(USAGE);
        //            System.err.println(USAGE);
        //            System.exit(2);
        //        } catch (ConfigException e) {
        //            LOG.error("Invalid config, exiting abnormally", e);
        //            System.err.println("Invalid config, exiting abnormally");
        //            System.exit(2);
        //        } catch (DatadirException e) {
        //            LOG.error("Unable to access datadir, exiting abnormally", e);
        //            System.err.println("Unable to access datadir, exiting abnormally");
        //            System.exit(3);
        //        } catch (AdminServerException e) {
        //            LOG.error("Unable to start AdminServer, exiting abnormally", e);
        //            System.err.println("Unable to start AdminServer, exiting abnormally");
        //            System.exit(4);
        //        } catch (Exception e) {
        //            LOG.error("Unexpected exception, exiting abnormally", e);
        //            System.exit(1);
        //        }
        //        LOG.info("Exiting normally");
        System.exit(0);
    }

    //    protected void initializeAndRun(String[] args) throws ConfigException, IOException, AdminServerException {
    //        try {
    //            ManagedUtil.registerLog4jMBeans();
    //        } catch (JMException e) {
    //            LOG.warn("Unable to register log4j JMX control", e);
    //        }
    //        ServerConfig config = new ServerConfig();
    //        if (args.length == 1) {
    //            config.parse(args[0]);
    //        } else {
    //            config.parse(args);
    //        }
    //        runFromConfig(config);
    //    }
    public void runFromConfig(ServerConfig config) throws IOException {
        //            throws IOException, AdminServerException {
        LOG.info("Starting server");
        FileTxnSnapLog txnLog = null;
        try {
            // Note that this thread isn't going to be doing anything else,
            // so rather than spawning another thread, we will just call
            // run() in this thread.
            // create a file logger url from the command line args
            txnLog = new FileTxnSnapLog(config.dataLogDir, config.dataDir);
            final ZooKeeperServer zkServer = new ZooKeeperServer(txnLog, config.tickTime, config.minSessionTimeout, config.maxSessionTimeout, null);
            adminServer = AdminServerFactory.createAdminServer();
            adminServer.setZooKeeperServer(zkServer);
            adminServer.start();
            zkServer.startup();
        } catch (Exception e) {
            // warn, but generally this is ok
            //                        LOG.warn("Server interrupted", e);
        } finally {
            if (txnLog != null) {
                txnLog.close();
            }
        }
    }
}
