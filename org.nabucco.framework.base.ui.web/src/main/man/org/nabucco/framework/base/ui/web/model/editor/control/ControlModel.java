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
package org.nabucco.framework.base.ui.web.model.editor.control;

import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationError;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintType;
import org.nabucco.framework.base.ui.web.component.messageset.MessageSetConstraintType;
import org.nabucco.framework.base.ui.web.component.messageset.UiMessageSet;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.EditorGridElementModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.formatter.ControlFormatter;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParser;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.editor.util.validator.NabuccoValidator;

/**
 * Model for an editor control.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ControlModel<N extends NType> extends EditorGridElementModel {

    private static final String PARSE_ERROR = "Given value cannot be parsed";

    private static final String VALIDATION_ERROR = "Given value is not correct";

    private static final String JSON_ERRORS = "errors";

    private boolean invalidParsing;

    private String propertyPath;

    /** Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ControlModel.class);

    /** The Model Value */
    private N value;

    /** Calidates element */
    private NabuccoValidator<N> validator;

    /** Formats the result */
    private ControlFormatter<N> formatter;

    /** Control Parser */
    private ControlParser<N> parser;

    /** Type of the control */
    private ControlType controlType;

    /** Indicates that the model is not valid **/
    private boolean valid;

    /** Bound property */
    private NabuccoProperty property;

    /** Defines if the validation messages should be sent to the UI */
    private boolean validating = false;

    /**
     * 
     * Creates a new {@link ControlModel} instance.
     * 
     * @param id
     *            the id of the control
     * @param propertyPath
     *            the property path of the control
     * @param type
     *            the type of control
     * @param validator
     *            the validator to use
     * @param dependencyController
     *            the dependency set of control
     * @param editable
     *            indicator if the control is editable
     */
    public ControlModel(String id, String propertyPath, ControlType type, NabuccoValidator<N> validator,
            DependencyController dependencyController, boolean editable) {

        super(id, dependencyController, editable);

        if (propertyPath == null) {
            throw new IllegalArgumentException("Cannot create control model with propertyPath [null].");
        }

        this.controlType = type;

        this.setValidator(validator);
        this.setInitialized(false);
        this.setPropertyPath(propertyPath);

    }

    @Override
    public final void init() {
        super.init();
    }

    /**
     * Getter for the controlType.
     * 
     * @return Returns the controlType.
     */
    public ControlType getControlType() {
        return this.controlType;
    }

    /**
     * Defines if the validating messages need to be sent to the ui
     * 
     * @param validating
     *            true if should be send
     */
    public void setValidating(boolean validating) {
        this.validating = validating;
    }

    /**
     * Indicates if the validation messages need to be sent to UI
     * 
     * @return
     */
    public boolean isValidating() {
        return this.validating;
    }

    /**
     * Instantiate a new basetype instance.
     * 
     * @return the new created instance
     */
    @SuppressWarnings("unchecked")
    protected N instantiate() {
        if (this.property == null) {
            logger.warning("Property is not valid '", this.propertyPath, "'.");
            return null;
        }

        try {
            Object newInstance = this.property.getType().newInstance();
            NabuccoProperty newProperty = this.property.createProperty(newInstance);
            this.property.getParent().setProperty(newProperty);

            if (newInstance == null) {
                logger.error("Cannot instanciate ", this.property.getType().toString());
            }
            return (N) newInstance;

        } catch (InstantiationException ie) {
            logger.error(ie, "Cannot instantiate Type '", this.property.getPropertyType(), "'.");
        } catch (IllegalAccessException ae) {
            logger.error(ae, "Cannot access Type '", this.property.getPropertyType(), "'.");
        }

        return null;
    }

    /**
     * Validates actual element. Process semantic check and contstraint check
     * 
     * @return {@link ValidationResult} result of validation
     */
    public Set<String> validate() {
        Set<String> retVal = new HashSet<String>();

        NabuccoProperty checkProperty = this.getProperty();

        boolean editable = this.isEditable();

        if (checkProperty != null && editable) {
            UiMessageSet messageSet = UiMessageSet.getInstance();

            // Check is the value is valid according to the validator
            if (this.validator != null) {
                boolean configuredValidationResult = this.validator.validateElement(this.getValue());

                if (!configuredValidationResult) {
                    String errorText = messageSet.resolveText(this.controlType, this.getId(),
                            MessageSetConstraintType.VALIDATION_ERROR, VALIDATION_ERROR);
                    retVal.add(errorText);
                }
            }

            // Check if the value was parsed succesfully
            if (this.isInvalidParsing()) {
                String errorText = messageSet.resolveText(this.controlType, this.getId(),
                        MessageSetConstraintType.INVALID_PARSING, PARSE_ERROR);
                retVal.add(errorText);
            }

            // Validate configured constraints
            if (retVal.isEmpty() && checkProperty.getParent() instanceof Validatable) {
                Validatable owner = (Validatable) checkProperty.getParent();

                Set<ConstraintType> validated = new HashSet<ConstraintType>();
                for (Constraint constraint : this.getProperty().getConstraints()) {
                    if (validated.add(constraint.getType())) {
                        ValidationResult result = new ValidationResult();
                        constraint.check(owner, checkProperty, result);

                        if (!result.isEmpty()) {
                            ConstraintType type = constraint.getType();
                            ValidationError validationError = result.getErrorList().get(0);
                            String originalMessage = validationError.getMessage();
                            String errorText = messageSet.resolveText(this.controlType, this.getId(), type,
                                    originalMessage);
                            retVal.add(errorText);
                        }
                    }
                }
            }

            boolean valid = retVal.isEmpty();

            this.setValid(valid);
        } else if (!editable) {
            this.setValid(true);
        }

        return retVal;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // The dependency status has changed. Let all elements depending on me know about my state.
        if (this.getProperty() != null) {
            if (event.getNewValue().equals(false)) {
                boolean isValidating = this.isValidating();

                this.resetValue();

                // Reset validating for the field be changing of constraint
                if (isValidating == false) {
                    this.setValidating(false);
                }
            }

            this.setRefreshNeeded(true);
        }
    }

    /**
     * Getter for the property.
     * 
     * @return Returns the property.
     */
    public NabuccoProperty getProperty() {
        return this.property;
    }

    /**
     * Set a new property into the model and the parent type (when a property already exists).
     * 
     * @param property
     *            the new property
     */
    @SuppressWarnings("unchecked")
    public void setProperty(NabuccoProperty property) {
        if (this.property != null) {
            this.property.getParent().setProperty(property);
        }

        this.property = property;
        this.setParsedValue((N) property.getInstance());

        super.getConstraintController().setServerConstraints(property.getConstraints());
    }

    /**
     * Changes controlled property. Does not update it by the parent datatype!
     * 
     * @param property
     *            the property to control
     */
    @SuppressWarnings("unchecked")
    public void changeProperty(NabuccoProperty property) {
        if (property == null) {
            this.property = null;
            return;
        }

        this.property = property;
        this.value = (N) property.getInstance();

        super.getConstraintController().setServerConstraints(property.getConstraints());
    }

    /**
     * Setter for the value.
     * 
     * @param newValue
     *            The value to set.
     */
    private final void setParsedValue(N newValue) {
        N oldValue = this.getValue();

        super.updateProperty(this.getPropertyPath(), oldValue, newValue);

        this.value = newValue;
    }

    /**
     * Perform reset of the control element
     * 
     * Mechanism to reset disabled values etc
     */
    final void resetValue() {
        this.updateBoundProperty(null, true);
    }

    /**
     * Parse the String and update the datatype value if possible
     * 
     * @param value
     *            the new string value
     */
    public final void setValue(String value) {
        this.updateBoundProperty(value, false);
    };

    /**
     * Perform updating of the bound value
     * 
     * @param value
     *            the new value to be set
     * @param force
     *            ignore access constraints
     */
    private final void updateBoundProperty(String value, boolean force) {
        if (!force) {
            // Security aspect that no value may be setted for disabled or invisible controls
            if (!super.getConstraintController().isEditable() || !super.getConstraintController().isVisible()) {
                return;
            }
        }

        this.setValidating(true);
        this.invalidParsing = false;

        NabuccoProperty oldProperty = this.getProperty();

        if (oldProperty != null && oldProperty.isCollection() && (value == null || value.isEmpty())) {

            if (oldProperty.getInstance() instanceof Collection<?>) {
                ((Collection<?>) oldProperty.getInstance()).clear();
            }
            this.setProperty(oldProperty);

        } else {
            try {
                // The case if the value was deleted
                if (value == null || value.isEmpty()) {
                    NabuccoProperty newProperty = this.getProperty().createProperty(null);
                    this.setProperty(newProperty);
                    return;
                }

                N parsedValue = this.parse(value);

                if (parsedValue == null) {
                    this.invalidParsing = true;
                    logger.error("Cannot convert String '", value, "' to NType.'");

                    return;
                }

                NabuccoProperty newProperty = this.getProperty().createProperty(parsedValue);
                this.setProperty(newProperty);

            } catch (ControlParserException cpe) {
                this.invalidParsing = true;
                logger.warning("Cannot convert String '", value, "' to NType.'");
            } catch (Exception e) {
                this.invalidParsing = true;
                logger.error(e, "Cannot convert String '", value, "' to NType.'");
            }
        }
    }

    /**
     * Getter for the value object.
     * 
     * @return Returns the value.
     */
    public final N getValue() {
        return this.value;
    }

    public abstract String getStringValue();

    /**
     * Internal parse method, that parses the the string value to the appropriate control model
     * type.
     * 
     * @param strignValue
     *            the string value to parse
     * 
     * @throws ControlParserException
     *             when the parsing fails
     */
    protected abstract N parse(String stringValue) throws ControlParserException;

    /**
     * Getter for the dirty. Indicates that the model is in unsaved stage
     * 
     * @return Returns the dirty.
     */
    public final boolean isDirty() {
        if (this.property == null) {
            logger.warning("Property is not valid '", this.propertyPath, "'.");
            return false;
        }

        PropertyOwner parent = this.property.getParent();
        if (parent instanceof NabuccoDatatype) {
            NabuccoDatatype parentDatatype = (NabuccoDatatype) parent;
            DatatypeState datatypeState = parentDatatype.getDatatypeState();
            boolean dirty = (datatypeState != DatatypeState.PERSISTENT);
            if (dirty) {
                dirty = true;
            }
            return dirty;
        }

        return false;
    }

    /**
     * Setter for the valid. Indicates that the model is not valid
     * 
     * @param valid
     *            The valid to set.
     */
    protected final void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Getter for the valid. Indicates that the model is not valid
     * 
     * @return Returns the valid.
     */
    public final boolean isValid() {
        return this.valid;
    }

    /**
     * Setter for the invalidParsing.
     * 
     * @param invalidParsing
     *            The invalidParsing to set.
     */
    protected final void setInvalidParsing(boolean invalidParsing) {
        this.invalidParsing = invalidParsing;
    }

    /**
     * Getter for the invalidParsing.
     * 
     * @return Returns the invalidParsing.
     */
    protected final boolean isInvalidParsing() {
        return this.invalidParsing;
    }

    /**
     * Setter for the validator.
     * 
     * @param validator
     *            The validator to set.
     */
    protected final void setValidator(NabuccoValidator<N> validator) {
        this.validator = validator;
    }

    /**
     * Getter for the validator.
     * 
     * @return Returns the validator.
     */
    protected final NabuccoValidator<N> getValidator() {
        return this.validator;
    }

    /**
     * Set for formatter
     * 
     * @param formatter
     */
    protected final void setFormatter(ControlFormatter<N> formatter) {
        this.formatter = formatter;
    }

    /**
     * Get for formatter
     * 
     * @return returns the formatter instance
     */
    protected final ControlFormatter<N> getFormatter() {
        return this.formatter;
    }

    /**
     * Setter for the parser.
     * 
     * @param parser
     *            The parser to set.
     */
    protected final void setParser(ControlParser<N> parser) {
        this.parser = parser;
    }

    /**
     * Getter for the parser.
     * 
     * @return Returns the parser.
     */
    protected final ControlParser<N> getParser() {
        return this.parser;
    }

    /**
     * Setter for the propertyPath.
     * 
     * @param propertyPath
     *            The propertyPath to set.
     */
    public final void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    /**
     * Setter for the propertyPath.
     * 
     * @param propertyPath
     *            The propertyPath to set.
     */
    public final String getPropertyPath() {
        return this.propertyPath;
    }

    @Override
    public JsonMap toJson() {

        JsonMap json = super.toJson();

        json.add(JSON_TYPE, this.controlType);
        json.add(JSON_DIRTY, this.isDirty());

        if (this.isValidating()) {

            Set<String> validationErrors = this.validate();
            if (!this.isValid()) {
                JsonList errors = new JsonList();
                for (String error : validationErrors) {
                    errors.add(error);
                }
                json.add(JSON_ERRORS, errors);
            }

            json.add(JSON_VALID, this.isValid());
        }

        return json;
    }

}
