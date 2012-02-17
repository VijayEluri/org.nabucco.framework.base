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
package org.nabucco.framework.base.facade.service;

import java.io.Serializable;

/**
 * Service
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Service extends Serializable {

    /**
     * Returns the name of the service.
     * 
     * @return the service name.
     */
    String getName();

    /**
     * Gets the aspects for a specific operation.
     * 
     * @param operation
     *            The name of the operation.
     * 
     * @return The list of aspects of the operation.
     */
    String[] getAspects(String operation);

}
