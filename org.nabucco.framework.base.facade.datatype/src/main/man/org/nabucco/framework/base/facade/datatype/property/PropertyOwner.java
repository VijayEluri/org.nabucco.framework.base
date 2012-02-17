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
package org.nabucco.framework.base.facade.datatype.property;

import org.nabucco.framework.base.facade.datatype.validation.constraint.dynamic.DynamicConstraintContainer;
import org.nabucco.framework.base.facade.datatype.visitor.Visitable;

/**
 * PropertyOwner
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface PropertyOwner extends Visitable, DynamicConstraintContainer {

    /**
     * Retrieve a property for a given name.
     * 
     * @param name
     *            the property name
     * 
     * @return the property
     */
    NabuccoProperty getProperty(String name);

    /**
     * Generic setter for a property instance.
     * 
     * @param property
     *            the property to set
     * 
     * @return <b>true</b> if the property has successfully been set, <b>false</b> if not
     */
    boolean setProperty(NabuccoProperty property);
}
