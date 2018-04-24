/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

package org.apache.zookeeper.server.admin;

import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author en.xuze@alipay.com
 * @version $Id: AdminServerFactory.java, v 0.1 2018年4月24日 上午10:54:37 en.xuze@alipay.com Exp $
 */
public class AdminServerFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AdminServerFactory.class);

    /**
     * This method encapsulates the logic for whether we should use a
     * JettyAdminServer (i.e., the AdminServer is enabled) or a DummyAdminServer
     * (i.e., the AdminServer is disabled). It uses reflection when attempting
     * to create a JettyAdminServer, rather than referencing the class directly,
     * so that it's ok to omit Jetty from the classpath if a user doesn't wish
     * to pull in Jetty with ZooKeeper.
     */
    public static AdminServer createAdminServer() {
        if (!"false".equals(System.getProperty("zookeeper.admin.enableServer"))) {
            try {
                Class<?> jettyAdminServerC = Class.forName("org.apache.zookeeper.server.admin.JettyAdminServer");
                Object adminServer = jettyAdminServerC.getConstructor().newInstance();
                return (AdminServer) adminServer;
            } catch (ClassNotFoundException e) {
                LOG.warn("Unable to start JettyAdminServer", e);
            } catch (InstantiationException e) {
                LOG.warn("Unable to start JettyAdminServer", e);
            } catch (IllegalAccessException e) {
                LOG.warn("Unable to start JettyAdminServer", e);
            } catch (InvocationTargetException e) {
                LOG.warn("Unable to start JettyAdminServer", e);
            } catch (NoSuchMethodException e) {
                LOG.warn("Unable to start JettyAdminServer", e);
            } catch (NoClassDefFoundError e) {
                LOG.warn("Unable to load jetty, not starting JettyAdminServer", e);
            }
        }
        return new DummyAdminServer();
    }
}
