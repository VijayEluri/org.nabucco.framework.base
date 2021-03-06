/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.component.connection;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * GlassfishConnection
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class GlassfishConnection extends Connection {

    /**
     * Creates a new {@link GlassfishConnection}.
     * 
     * @param specification
     *            the connection specification
     * 
     * @throws ConnectionException
     */
    GlassfishConnection(ConnectionSpecification specification) throws ConnectionException {
        super(specification);
    }

    Context initContext(Properties properties) throws ConnectionException {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            env.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
            env.put(Context.STATE_FACTORIES, "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

            if (properties != null && !properties.isEmpty()) {
                env.putAll(properties);
            }

            return new InitialContext(env);
        } catch (NamingException e) {
            throw new ConnectionException("Error creating Glassfish connection.", e);
        }
    }

}
