/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * InstantiationEffectExtension<p/>Extension for configuring a log effect.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-15
 */
public class InstantiationEffectExtension extends WorkflowEffectExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String CLASSNAME = "className";

    /** The class name to instantiate. */
    private ClassProperty className;

    /** Constructs a new InstantiationEffectExtension instance. */
    public InstantiationEffectExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the InstantiationEffectExtension.
     */
    protected void cloneObject(InstantiationEffectExtension clone) {
        super.cloneObject(clone);
        if ((this.getClassName() != null)) {
            clone.setClassName(this.getClassName().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowEffectExtension.class).getPropertyMap());
        propertyMap.put(CLASSNAME, PropertyDescriptorSupport.createDatatype(CLASSNAME, ClassProperty.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(InstantiationEffectExtension.getPropertyDescriptor(CLASSNAME),
                this.getClassName(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CLASSNAME) && (property.getType() == ClassProperty.class))) {
            this.setClassName(((ClassProperty) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final InstantiationEffectExtension other = ((InstantiationEffectExtension) obj);
        if ((this.className == null)) {
            if ((other.className != null))
                return false;
        } else if ((!this.className.equals(other.className)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.className == null) ? 0 : this.className.hashCode()));
        return result;
    }

    @Override
    public InstantiationEffectExtension cloneObject() {
        InstantiationEffectExtension clone = new InstantiationEffectExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The class name to instantiate.
     *
     * @param className the ClassProperty.
     */
    public void setClassName(ClassProperty className) {
        this.className = className;
    }

    /**
     * The class name to instantiate.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getClassName() {
        return this.className;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(InstantiationEffectExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(InstantiationEffectExtension.class).getAllProperties();
    }
}
