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
package org.nabucco.framework.base.impl.service.aspect.indexing;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.Aspect;

/**
 * Interface for joinpoint org.nabucco.aspect.indexing. Handles indexing of business objects.
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Frank Ratschinski
 */
public interface IndexingAspect extends Aspect {

    /**
     * Converting the datatype content of a service message to a fulltext document for indexing.
     * 
     * @param requestMessage
     *            The request message of the service operation.
     * 
     * @return A fulltextdocument, ready for indexing.
     * 
     * @throws IndexingException
     *             Thrown if converting cannot be done.
     */
    void indexBefore(ServiceMessage requestMessage) throws IndexingException;

    /**
     * Converting the datatype content of a service message to a fulltext document for indexing.
     * 
     * @param requestMessage
     *            The request message of the service operation.
     * @param responseMessage
     *            The response message of the service operation
     * 
     * @return A fulltextdocument, ready for indexing.
     * 
     * @throws IndexingException
     *             Thrown if converting cannot be done.
     */
    void indexAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws IndexingException;

}
