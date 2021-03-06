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
package org.nabucco.framework.base.facade.datatype.visitor;

/**
 * Visitor
 * <p/>
 * Visitor for NABUCCO Datatypes and Basetypes.
 * 
 * @see Visitable
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Visitor {

    /**
     * Visits a {@link Visitable} instance and its children.
     * 
     * @param visitable
     *            the visitable to visit
     * 
     * @throws VisitorException
     *             if an error occurs during the visitation
     */
    void visit(Visitable visitable) throws VisitorException;

    /**
     * Checks whether the visitor has already visited the appropriate object.
     * 
     * @param visitable
     *            the visitable to check
     * 
     * @return <b>true</b> if the object has already been visted, <b>false</b> if not
     */
    boolean hasVisited(Visitable visitable);

}
