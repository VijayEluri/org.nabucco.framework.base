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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DialogButtonExtension<p/>NABUCCO Relation tab Button Extension interface<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-02
 */
public class DialogButtonExtension extends ButtonExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String VALIDATE = "validate";

    /** The model need to be valid before the operation can proceed */
    private BooleanProperty validate;

    /** Constructs a new DialogButtonExtension instance. */
    public DialogButtonExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DialogButtonExtension.
     */
    protected void cloneObject(DialogButtonExtension clone) {
        super.cloneObject(clone);
        if ((this.getValidate() != null)) {
            clone.setValidate(this.getValidate().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ButtonExtension.class).getPropertyMap());
        propertyMap.put(VALIDATE, PropertyDescriptorSupport.createDatatype(VALIDATE, BooleanProperty.class, 9,
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
        properties.add(super.createProperty(DialogButtonExtension.getPropertyDescriptor(VALIDATE), this.getValidate(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(VALIDATE) && (property.getType() == BooleanProperty.class))) {
            this.setValidate(((BooleanProperty) property.getInstance()));
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
        final DialogButtonExtension other = ((DialogButtonExtension) obj);
        if ((this.validate == null)) {
            if ((other.validate != null))
                return false;
        } else if ((!this.validate.equals(other.validate)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.validate == null) ? 0 : this.validate.hashCode()));
        return result;
    }

    @Override
    public DialogButtonExtension cloneObject() {
        DialogButtonExtension clone = new DialogButtonExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The model need to be valid before the operation can proceed
     *
     * @param validate the BooleanProperty.
     */
    public void setValidate(BooleanProperty validate) {
        this.validate = validate;
    }

    /**
     * The model need to be valid before the operation can proceed
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getValidate() {
        return this.validate;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DialogButtonExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DialogButtonExtension.class).getAllProperties();
    }
}