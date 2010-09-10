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
package org.nabucco.framework.base.facade.message;

import org.nabucco.framework.base.facade.message.validation.MessageConstraintValidationVisitor;

import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * ServiceMessageSupport
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void validate(ValidationResult result) throws ValidationException {
        if (result == null) {
            throw new IllegalArgumentException("Validation result is not valid [null].");
        }

        try {
            MessageConstraintValidationVisitor visitor = new MessageConstraintValidationVisitor(
                    result);
            this.accept(visitor);
        } catch (VisitorException e) {
            throw new ValidationException("Error visiting message for validation.", e);
        }
    }

    @Override
    public String[] getConstraints() {
        return null;
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
    }

    @Override
    public Object[] getProperties() {
        return null;
    }

    @Override
    public String[] getPropertyNames() {
        return null;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

}
