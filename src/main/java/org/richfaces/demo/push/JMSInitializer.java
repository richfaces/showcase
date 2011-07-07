/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.demo.push;

import static org.richfaces.demo.push.JMSMessageProducer.PUSH_JMS_TOPIC;
import static org.richfaces.demo.push.PushEventObserver.PUSH_CDI_TOPIC;
import static org.richfaces.demo.push.TopicsContextMessageProducer.PUSH_TOPICS_CONTEXT_TOPIC;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.richfaces.demo.push.provider.AS6MessagingProviderManagement;
import org.richfaces.demo.push.provider.AS7MessagingProviderManagement;
import org.richfaces.demo.push.provider.CustomMessagingServerManagement;
import org.richfaces.demo.push.provider.InitializationFailedException;
import org.richfaces.demo.push.provider.MessagingProviderManagement;

/**
 * Initializes JMS server and creates requested topics.
 *
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 */
public class JMSInitializer extends AbstractCapabilityInitializer {

    private static final Logger LOGGER = Logger.getLogger(JMSInitializer.class.getName());

    private MessagingProviderManagement provider;

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.demo.push.Initializer#initialize()
     */
    public void initializeCapability() throws Exception {
        provider = initializeCurrentProvider();

        provider.createTopic(PUSH_JMS_TOPIC, "/topic/" + PUSH_JMS_TOPIC);
        provider.createTopic(PUSH_TOPICS_CONTEXT_TOPIC, "/topic/" + PUSH_TOPICS_CONTEXT_TOPIC);
        provider.createTopic(PUSH_CDI_TOPIC, "/topic/" + PUSH_CDI_TOPIC);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.demo.push.Initializer#unload()
     */
    public void finalizeCapability() throws Exception {
        provider.finalizeProvider();
    }

    /**
     * Returns all providers which are available from current context
     *
     * @return all providers which are available from current context
     */
    @SuppressWarnings("unchecked")
    private Class<? extends MessagingProviderManagement>[] getAvailableProviders() {
        if (isConnectionFactoryRegistered()) {
            return new Class[] { AS7MessagingProviderManagement.class, AS6MessagingProviderManagement.class };
        } else {
            return new Class[] { CustomMessagingServerManagement.class };
        }
    }

    /**
     * Returns one of providers available from current context which are able to initialize successfully
     *
     * @return one of providers available from current context which are able to initialize successfully
     */
    private MessagingProviderManagement initializeCurrentProvider() {
        for (Class<? extends MessagingProviderManagement> c : getAvailableProviders()) {
            try {
                MessagingProviderManagement provider = c.newInstance();
                provider.initializeProvider();
                return provider;
            } catch (InitializationFailedException e) {
                // TODO
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        throw new IllegalStateException("no management provider has been successfully initialized");
    }

    /**
     * Returns true if ConnectionFactory is already registered
     *
     * @return true if ConnectionFactory is already registered
     */
    private boolean isConnectionFactoryRegistered() {
        try {
            return null != InitialContext.doLookup("java:/ConnectionFactory");
        } catch (NamingException e) {
            if (!(e instanceof NameNotFoundException)) {
                LOGGER.log(Level.SEVERE, "Can't access naming context", e);
            }
            return false;
        }
    }

}