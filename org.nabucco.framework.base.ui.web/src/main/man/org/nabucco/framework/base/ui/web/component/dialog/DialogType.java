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
package org.nabucco.framework.base.ui.web.component.dialog;

/**
 * DialogType The type of the dialog
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public enum DialogType {

    /** Easy dialog with simply message */
    MESSAGEDIALOG("messageDialog"),

    /** Dialog with message AND Grid (with controls) */
    GRIDDIALOG("gridDialog");


    private String value;

    /**
     * Creates a new {@link DialogType} instance.
     * 
     */
    private DialogType(String value) {
        this.value = value;
    }

    /**
     * Return the value
     * 
     * @return
     */
    public String getValue() {
        return this.value;
    }
}
