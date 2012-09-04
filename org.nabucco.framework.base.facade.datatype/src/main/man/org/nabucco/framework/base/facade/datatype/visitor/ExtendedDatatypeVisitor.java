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

import java.util.HashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;

/**
 * DatatypeVisitor
 * <p/>
 * Datatype Visitor that visits children last. Each instance is only visited once, when calling the
 * <code>reset()</code> Method the cache will be cleaned.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ExtendedDatatypeVisitor extends DatatypeVisitor {

    /** Traverse over component borders. */
    private boolean traverseComponents;

    /** Set of visited object ids. */
    private Set<Integer> visited = new HashSet<Integer>();

    /**
     * Creates a new {@link ExtendedDatatypeVisitor} instance that traverses over component borders.
     * <p/>
     * This constructor is equivalent to {@link ExtendedDatatypeVisitor#DatatypeVisitor(boolean)}.
     */
    public ExtendedDatatypeVisitor() {
        this(true);
    }

    /**
     * Creates a new {@link ExtendedDatatypeVisitor} instance.
     * 
     * @param traverseComponents
     *            <b>true</b> when component borders should be traversed, <b>false</b> if not
     */
    public ExtendedDatatypeVisitor(boolean traverseComponents) {
        super(traverseComponents);
    }

    /**
     * Visits all sub-properties of a visitable.
     * 
     * @param visitable
     *            the visitable containing the properties
     * 
     * @throws VisitorException
     */
    @Override
    protected void visitChildren(Visitable visitable) throws VisitorException {
        for (NabuccoProperty property : visitable.getProperties()) {

            switch (property.getPropertyType()) {
            case DATATYPE:
                DatatypeProperty datatypeProperty = (DatatypeProperty) property;
                if (datatypeProperty.getAssociationType() != PropertyAssociationType.COMPONENT
                        || this.traverseComponents) {
                    datatypeProperty.accept(this);
                }

                break;

            case COLLECTION:
                CollectionProperty listProperty = (CollectionProperty) property;
                if (listProperty.getAssociationType() != PropertyAssociationType.COMPONENT || this.traverseComponents) {
                    listProperty.accept(this);
                }
                break;

            case COMPONENT_RELATION: {
                ComponentRelationProperty crProperty = (ComponentRelationProperty) property;
                if (crProperty.getAssociationType() != PropertyAssociationType.COMPONENT || this.traverseComponents) {
                    NabuccoList<ComponentRelation<?>> instance = crProperty.getInstance();
                    for (ComponentRelation<?> relation : instance) {
                        if (relation.getTarget() != null) {
                            relation.getTarget().accept(this);
                        }

                        relation.accept(this);
                    }
                }
                break;
            }

            default:
                property.accept(this);
            }

        }
    }

    /**
     * Cleans the cache of already visited instances.
     */
    @Override
    public void reset() {
        this.visited.clear();
    }

}
