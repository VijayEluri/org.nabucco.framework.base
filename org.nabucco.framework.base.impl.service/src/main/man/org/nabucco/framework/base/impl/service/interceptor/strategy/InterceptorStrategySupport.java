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
package org.nabucco.framework.base.impl.service.interceptor.strategy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;

/**
 * InterceptorStrategySupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class InterceptorStrategySupport implements InterceptorStrategy {

    /** Sub strategies to delegate to. */
    private List<InterceptorStrategy> delegateStrategies = new ArrayList<InterceptorStrategy>();

    /**
     * Adds a strategy to the current strategy.
     * 
     * @param strategy
     *            the strategy to add
     */
    protected void addStrategy(InterceptorStrategy strategy) {
        if (strategy != null) {
            this.delegateStrategies.add(strategy);
        }
    }

    @Override
    public void beforeInvocation(InterceptorContext context, Service service, Method method,
            ServiceRequest<?> request, NabuccoLogger logger) {
        for (InterceptorStrategy strategy : this.delegateStrategies) {
            strategy.beforeInvocation(context, service, method, request, logger);
        }
    }

    @Override
    public void afterInvocation(InterceptorContext context, Service service, Method method,
            ServiceRequest<?> request, ServiceResponse<?> response, NabuccoLogger logger,
            Throwable exception) {
        for (InterceptorStrategy strategy : this.delegateStrategies) {
            strategy.afterInvocation(context, service, method, request, response, logger, exception);
        }
    }

}
