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
package org.nabucco.framework.base.impl.service.maintain;

import org.nabucco.framework.base.facade.exception.persistence.EntityExistsException;
import org.nabucco.framework.base.facade.exception.persistence.EntityNotFoundException;
import org.nabucco.framework.base.facade.exception.persistence.NonUniqueResultException;
import org.nabucco.framework.base.facade.exception.persistence.OptimisticLockException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * PersistenceExceptionMapper
 * <p/>
 * Maps technical persistence exceptions to according NABUCCO exceptions.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PersistenceExceptionMapper {

    /**
     * Private constructor must not be invoked.
     */
    private PersistenceExceptionMapper() {
    }

    /**
     * Maps all sub-classes of <code>javax.persistence.PersistenceException</code> to
     * <code>org.nabucco.framework.base.facade.exception.PersistenceException</code>:
     * <p>
     * EntityExistsException, EntityNotFoundException, NonUniqueResultException, NoResultException,
     * OptimisticLockException, RollbackException, TransactionRequiredException
     * 
     * @param exception
     *            the input exception
     * 
     * @return the according NABUCCO exception
     */
    public static PersistenceException resolve(Exception exception) {

        PersistenceException out = null;

        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("Error during persistence operation: ");
        resultMessage.append(exception.getMessage());

        if (exception instanceof PersistenceException) {
            return (PersistenceException) exception;
        } else if (exception instanceof javax.persistence.EntityExistsException) {
            out = new EntityExistsException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.EntityNotFoundException) {
            out = new EntityNotFoundException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.NonUniqueResultException) {
            out = new NonUniqueResultException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.NoResultException) {
            out = new EntityNotFoundException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.OptimisticLockException) {
            out = new OptimisticLockException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.RollbackException) {
            out = new PersistenceException(resultMessage.toString(), exception);
        } else if (exception instanceof javax.persistence.TransactionRequiredException) {
            out = new PersistenceException(resultMessage.toString(), exception);
        } else {
            out = new PersistenceException(resultMessage.toString(), exception);
        }

        return out;
    }

    /**
     * Maps all sub-classes of <code>javax.persistence.PersistenceException</code> to
     * <code>org.nabucco.framework.base.facade.exception.PersistenceException</code>:
     * <p>
     * EntityExistsException, EntityNotFoundException, NonUniqueResultException, NoResultException,
     * OptimisticLockException, RollbackException, TransactionRequiredException
     * 
     * @param exception
     *            the input exception
     * @param entityName
     *            name of the entity
     * @param id
     *            primary key of the entity
     * 
     * @return the according NABUCCO exception
     */
    public static PersistenceException resolve(Exception exception, String entityName, Long id) {

        PersistenceException out = null;

        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append("Error during persistence of entity ");
        resultMessage.append(entityName);
        resultMessage.append(" with id [");
        resultMessage.append(id);
        resultMessage.append("]. ");
        resultMessage.append(exception.getMessage());

        if (exception instanceof PersistenceException) {
            return (PersistenceException) exception;
        } else if (exception instanceof javax.persistence.EntityExistsException) {
            out = new EntityExistsException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.EntityNotFoundException) {
            out = new EntityNotFoundException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.NonUniqueResultException) {
            out = new NonUniqueResultException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.NoResultException) {
            out = new EntityNotFoundException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.OptimisticLockException) {
            out = new OptimisticLockException(resultMessage.toString());
        } else if (exception instanceof javax.persistence.RollbackException) {
            out = new PersistenceException(resultMessage.toString(), exception);
        } else if (exception instanceof javax.persistence.TransactionRequiredException) {
            out = new PersistenceException(resultMessage.toString(), exception);
        } else {
            out = new PersistenceException(resultMessage.toString(), exception);
        }

        return out;
    }

}
