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
package org.nabucco.framework.base.facade.datatype.validation;

import org.nabucco.framework.base.facade.datatype.exceptionmsg.MessageOwner;
import org.nabucco.framework.base.facade.datatype.exceptionmsg.field.FieldMessageSet;

/**
 * ValidationException
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ValidationException extends Exception implements MessageOwner {

    private static final long serialVersionUID = 1L;

    /** The error message set specific for each field. */
    private FieldMessageSet fieldMessageSet;

    /**
     * Creates a new {@link ValidationException} instance.
     */
    public ValidationException() {
        super();
    }

    /**
     * Creates a new {@link ValidationException} instance.
     * 
     * @param message
     *            the error message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link ValidationException} instance.
     * 
     * @param cause
     *            the causing exception
     */
    public ValidationException(Exception cause) {
        super(cause);
    }

    /**
     * Creates a new {@link ValidationException} instance.
     * 
     * @param message
     *            the error message
     * @param cause
     *            the causing exception
     */
    public ValidationException(String message, Exception cause) {
        super(message, cause);
    }

    @Override
    public FieldMessageSet getFieldMessageSet() {
        return this.fieldMessageSet;
    }

    @Override
    public void setFieldMessageSet(FieldMessageSet fieldMessageSet) {
        this.fieldMessageSet = fieldMessageSet;
    }

}
