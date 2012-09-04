/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.framework.base.ui.web.component.work.visitor;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * VisitableWebElement
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface VisitableWebElement {

    /**
     * Accepts visitor
     * 
     * @param visitor
     *            the visitor to be accepted
     * @throws VisitorException
     *             if problem by visiting
     */
    <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context) throws VisitorException;
}
