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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BulkEditorButtonGroupExtension<p/>NABUCCO Bulk Editor button extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-02
 */
public class BulkEditorButtonGroupExtension extends BulkEditorButtonExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String BUTTONLIST = "buttonList";

    /** The list of referenced buttons */
    private NabuccoList<BulkEditorButtonExtension> buttonList;

    /** Constructs a new BulkEditorButtonGroupExtension instance. */
    public BulkEditorButtonGroupExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BulkEditorButtonGroupExtension.
     */
    protected void cloneObject(BulkEditorButtonGroupExtension clone) {
        super.cloneObject(clone);
        if ((this.buttonList != null)) {
            clone.buttonList = this.buttonList.cloneCollection();
        }
    }

    /**
     * Getter for the ButtonListJPA.
     *
     * @return the List<BulkEditorButtonExtension>.
     */
    List<BulkEditorButtonExtension> getButtonListJPA() {
        if ((this.buttonList == null)) {
            this.buttonList = new NabuccoListImpl<BulkEditorButtonExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<BulkEditorButtonExtension>) this.buttonList).getDelegate();
    }

    /**
     * Setter for the ButtonListJPA.
     *
     * @param buttonList the List<BulkEditorButtonExtension>.
     */
    void setButtonListJPA(List<BulkEditorButtonExtension> buttonList) {
        if ((this.buttonList == null)) {
            this.buttonList = new NabuccoListImpl<BulkEditorButtonExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<BulkEditorButtonExtension>) this.buttonList).setDelegate(buttonList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(BulkEditorButtonExtension.class).getPropertyMap());
        propertyMap.put(BUTTONLIST, PropertyDescriptorSupport.createCollection(BUTTONLIST,
                BulkEditorButtonExtension.class, 12, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(BulkEditorButtonGroupExtension.getPropertyDescriptor(BUTTONLIST),
                this.buttonList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(BUTTONLIST) && (property.getType() == BulkEditorButtonExtension.class))) {
            this.buttonList = ((NabuccoList<BulkEditorButtonExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public BulkEditorButtonGroupExtension cloneObject() {
        BulkEditorButtonGroupExtension clone = new BulkEditorButtonGroupExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list of referenced buttons
     *
     * @return the NabuccoList<BulkEditorButtonExtension>.
     */
    public NabuccoList<BulkEditorButtonExtension> getButtonList() {
        if ((this.buttonList == null)) {
            this.buttonList = new NabuccoListImpl<BulkEditorButtonExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.buttonList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BulkEditorButtonGroupExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BulkEditorButtonGroupExtension.class).getAllProperties();
    }
}
