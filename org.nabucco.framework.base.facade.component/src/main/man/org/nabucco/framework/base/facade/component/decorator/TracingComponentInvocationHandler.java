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
package org.nabucco.framework.base.facade.component.decorator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * TracingComponentInvocationHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class TracingComponentInvocationHandler implements ComponentDelegate {

    private Component target;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            TracingComponentInvocationHandler.class);

    @Override
    public void setTargetObject(Component component) {
        this.target = component;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {

        long start = System.currentTimeMillis();

        logger.debug("Invoking: ", formatMethodSignature(method, parameters));

        try {

            Method targetMethod = getMethodForTarget(method, method.getParameterTypes());
            Object returnVal = targetMethod.invoke(target, parameters);

            long end = System.currentTimeMillis();

            logger.debug(method.getName(), " returns: ", returnVal + " performed in: ",
                    (end - start) / 1000.0 + "s.");

            return returnVal;

        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new IllegalStateException("Unexpected invocation exception: " + e.getMessage());
        }
    }

    private Method getMethodForTarget(Method method, Class<?>[] parameterTypes)
            throws SecurityException {
        String name = method.getName();
        try {
            return this.target.getClass().getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Method not found '" + name + "'.", e);
        }
    }

    private String formatMethodSignature(Method method, Object[] parameters) {
        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(target.getClass().getSimpleName());
        methodSignature.append(' ');
        methodSignature.append(method.getReturnType().getSimpleName());
        methodSignature.append(' ');
        methodSignature.append(method.getName());
        methodSignature.append("(");

        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                Object object = parameters[i];
                methodSignature.append(object.getClass().getSimpleName());
                methodSignature.append(' ');
                methodSignature.append("arg");
                methodSignature.append(i + 1);

                if (i != parameters.length - 1) {
                    methodSignature.append(',');
                    methodSignature.append(' ');
                }
            }
        }

        methodSignature.append(")");
        return methodSignature.toString();
    }
}
