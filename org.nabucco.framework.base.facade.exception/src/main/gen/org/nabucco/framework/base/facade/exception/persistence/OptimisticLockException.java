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
package org.nabucco.framework.base.facade.exception.persistence;

import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * OptimisticLockException<p/>Thrown when an optimistic locking conflict occurs<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class OptimisticLockException extends PersistenceException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new OptimisticLockException instance. */
    public OptimisticLockException() {
        super();
    }

    /**
     * Constructs a new OptimisticLockException instance.
     *
     * @param message the String.
     */
    public OptimisticLockException(String message) {
        super(message);
    }

    /**
     * Constructs a new OptimisticLockException instance.
     *
     * @param cause the Throwable.
     */
    public OptimisticLockException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new OptimisticLockException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public OptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
