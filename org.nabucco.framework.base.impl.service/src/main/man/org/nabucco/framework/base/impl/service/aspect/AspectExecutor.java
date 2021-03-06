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
package org.nabucco.framework.base.impl.service.aspect;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;

/**
 * Common behavior of all executors of NABUCCO aspects.
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Frank Ratschinski
 */
public interface AspectExecutor {

    /**
     * Called before service execution.
     * 
     * @param aspect
     *            the aspect to execute
     * @param context
     *            the aspect execution context
     * @param request
     *            the service request
     * 
     * @throws AspectException
     *             when the aspect execution raised an exception
     */
    void executeBeforeAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request)
            throws AspectException;

    /**
     * Called after service execution.
     * 
     * @param aspect
     *            the aspect to execute
     * @param context
     *            the aspect execution context
     * @param request
     *            the service request
     * @param response
     *            the service response
     * 
     * @throws AspectException
     *             when the aspect execution raised an exception
     */
    void executeAfterAspect(Aspect aspect, AspectExecutionContext context, ServiceRequest<?> request,
            ServiceResponse<?> response) throws AspectException;

}
