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

/**
 * ConnectionType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum ConnectionType implements ConnectionConstants {

    /**
     * Glassfish connection.
     */
    GLASSFISH(PROTOCOL_IIOP, "3700"),

    /**
     * JBoss connection.
     */
    JBOSS(PROTOCOL_JNP, "1099");

    private String defaultProtocol;

    private String defaultPort;

    /**
     * Constructs a new ConnectionType instance with default connection protocol and port.
     * 
     * @param defaultProtocol
     *            the default protocol
     * @param defaultPort
     *            the default port
     */
    private ConnectionType(String defaultProtocol, String defaultPort) {
        this.defaultProtocol = defaultProtocol;
        this.defaultPort = defaultPort;
    }

    /**
     * Getter for the default connection protocol.
     * 
     * @return Returns the defaultProtocol.
     */
    public String getDefaultProtocol() {
        return this.defaultProtocol;
    }

    /**
     * Getter for the default connection port.
     * 
     * @return Returns the defaultPort.
     */
    public String getDefaultPort() {
        return this.defaultPort;
    }

}
