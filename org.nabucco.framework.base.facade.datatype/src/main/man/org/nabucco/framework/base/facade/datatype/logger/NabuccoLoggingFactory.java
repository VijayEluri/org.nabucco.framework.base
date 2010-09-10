/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.logger;

import org.apache.log4j.PropertyConfigurator;

/**
 * NabuccoLoggingFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoLoggingFactory {

    private static NabuccoLoggingFactory instance;

    private NabuccoLoggingFactory() {

        // TODO ConfigManager
        PropertyConfigurator.configureAndWatch("conf/log/log4j.properties");
    }

    public static synchronized NabuccoLoggingFactory getInstance() {
        if (instance == null) {
            instance = new NabuccoLoggingFactory();
        }
        return instance;
    }

    public NabuccoLogger getLogger(Class<?> clazz) {
        return new Log4JNabuccoLogger(clazz);
    }
}