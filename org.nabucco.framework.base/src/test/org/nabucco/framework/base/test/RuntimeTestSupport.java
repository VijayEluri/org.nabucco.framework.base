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
package org.nabucco.framework.base.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.adapter.Adapter;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.component.connection.ConnectionSpecification;
import org.nabucco.framework.base.facade.component.connection.ConnectionType;
import org.nabucco.framework.base.facade.component.locator.AdapterLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodeFacade;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.test.security.SecurityTestUtil;

/**
 * RuntimeTestSupport
 * 
 * @author Nicolas Moser, Silas Schwarz, PRODYNA AG
 */
public abstract class RuntimeTestSupport {

    private static final String URL_SEPARATOR = "/";

    private static final String ENVIRONMENT = "env";

    private static final String DEVELOPMENT = "dev";

    private static final String DEFAULT_PROPERTIES = "default.properties";

    private static final String USER_PROPERTIES = "test.properties";

    private static final String TYPE = "connection.type";

    private static final String HOST = "connection.host";

    private static final String PORT = "connection.port";

    private static final String TEST = "DummyInterceptor";

    /**
     * Lookup of the component for the given locator.
     * 
     * @param <C>
     *            the component to locate
     * @param locator
     *            the locator
     * 
     * @return the lookuped component
     * 
     * @throws ConnectionException
     *             when the connection cannot be established
     */
    public <C extends Component> C getComponent(ComponentLocator<C> locator) throws ConnectionException {
        return locator.getComponent(this.getConnection());
    }

    /**
     * Lookup of the adapter for the given locator.
     * 
     * @param <C>
     *            the adapter to locate
     * @param locator
     *            the locator
     * 
     * @return the lookuped adapter
     * 
     * @throws ConnectionException
     *             when the connection cannot be established
     */
    public <A extends Adapter> A getAdapter(AdapterLocator<A> locator) throws ConnectionException {
        return locator.getAdapter(this.getConnection());
    }

    /**
     * Getter for the service context.
     * 
     * @return the service context
     */
    public ServiceMessageContext getServiceContext() {
        Subject subject = SecurityTestUtil.createSubject();
        return ServiceContextFactory.getInstance().newServiceMessageContext(subject);
    }

    /**
     * Getter for the code with the given name registered for the given code path.
     * 
     * @param codePath
     *            the code path to find the code for
     * @param name
     *            the name of the code
     * 
     * @return the resolved code, or null if no code was found
     */
    public Code getCode(String codePath, String name) {
        return CodeFacade.getInstance().getCode(new CodePath(codePath), new Name(name));
    }

    /**
     * Getter for the codes registered for the given code path.
     * 
     * @param codePath
     *            the code path to find the codes for
     * 
     * @return the list of codes
     */
    public List<Code> getCode(String codePath) {
        return CodeFacade.getInstance().getCodes(new CodePath(codePath));
    }

    /**
     * Establishes the connection.
     * 
     * @return the new connection.
     * 
     * @throws ConnectionException
     */
    protected Connection getConnection() throws ConnectionException {

        Properties prop;
        try {
            prop = getEnvironmentProperties();
        } catch (IOException e) {
            throw new ConnectionException("unable to get connection properties", e);
        }

        ConnectionType type = ConnectionType.valueOf(prop.getProperty(TYPE));
        String host = prop.getProperty(HOST);
        String port = prop.getProperty(PORT);

        ConnectionSpecification specification = new ConnectionSpecification(type, TEST, host, port);

        return ConnectionFactory.getInstance().createConnection(specification);
    }

    /**
     * Creates a dummy service context.
     * 
     * @return the service context.
     */
    public static ServiceMessageContext createServiceContext() {
        Subject subject = SecurityTestUtil.createSubject();
        return ServiceContextFactory.getInstance().newServiceMessageContext(subject);
    }

    /**
     * Gets the environment properties by loading the default values and trying to replaces them
     * with user local overrides.
     * 
     * @return the test environment properties
     * 
     * @throws IOException
     *             if I/O fails
     */
    private Properties getEnvironmentProperties() throws IOException {
        Properties result = new Properties();

        InputStream propertiesStream = RuntimeTestSupport.class.getResourceAsStream(URL_SEPARATOR
                + ENVIRONMENT + URL_SEPARATOR + DEVELOPMENT + URL_SEPARATOR + DEFAULT_PROPERTIES);
        result.load(propertiesStream);
        propertiesStream.close();

        Properties localSettings = getLocalProperties();

        // fully force local settings, may override existing settings
        for (String localPropName : localSettings.stringPropertyNames()) {
            result.put(localPropName, localSettings.getProperty(localPropName));
        }

        return result;
    }

    /**
     * Attempt to load connections.properties in users home directory.
     * 
     * @return local setting properties
     */
    private Properties getLocalProperties() {
        Properties result = new Properties();
        String propertyFilePath = System.getProperty("user.home");
        File propertiesFile = new File(propertyFilePath + File.separatorChar + USER_PROPERTIES);
        if (propertiesFile.exists() && propertiesFile.canRead()) {
            try {
                FileInputStream fis = new FileInputStream(propertiesFile);
                result.load(fis);
                fis.close();
            } catch (IOException e) {
                throw new IllegalStateException("File I/O failed for getLocalProperties", e);
            }
        }
        return result;
    }
}
