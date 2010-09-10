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
package org.nabucco.framework.base.facade.datatype.validation;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintContainer;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintParser;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeValidationVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeValidationVisitor extends DatatypeVisitor implements Visitor {

    private ValidationResult result;

    /**
     * Creates a new {@link DatatypeValidationVisitor} instance.
     * 
     * @param result
     *            the validation result containing validation errors
     */
    public DatatypeValidationVisitor(ValidationResult result) {
        if (result == null) {
            throw new IllegalArgumentException("ValidationResult [null] is not valid.");
        }
        this.result = result;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        ConstraintContainer container = ConstraintParser.getInstance().parseConstraint(datatype);

        if (container != null && !container.isEmpty()) {

            Object[] children = datatype.getProperties();
            for (int index = 0; index < children.length; index++) {
                container.check(children[index], index, this.result);
            }
        }

        super.visit(datatype);
    }

}