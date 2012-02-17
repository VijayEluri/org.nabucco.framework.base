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
package org.nabucco.framework.base.ui.web.action.handler.work.editor;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.handler.work.RefreshEditorDependenciesHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.model.control.ControlModel;

/**
 * Action handler to synchronize control elements
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SynchronizeControlHandler extends WebActionHandler {

    public final static String ID = "System.SynchronizeControl";

    private final static String VALUE = "value";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {
        WebActionResult result = new WebActionResult();
        try {

            WebElement element = parameter.getElement();

            if (element.getType() == WebElementType.EDITOR_CONTROL) {
                String value = parameter.getJsonRequest().getValue(VALUE);
                result = this.validate((EditorControl) element, value);

                // Process dependencies check
                RefreshEditorDependenciesHandler handler = new RefreshEditorDependenciesHandler();
                WebActionResult dependencyResult = handler.execute(parameter);

                result.addResult(dependencyResult);
            }

        } catch (ClientException e) {
            throw new ActionException("Cannot validate control", e);
        }
        return result;
    }

    /**
     * Validate the control.
     * 
     * @param control
     *            the editor control
     * @param editor
     *            the editor instance
     * @throws ClientException
     */
    private WebActionResult validate(EditorControl control, String value) throws ClientException {
        WebActionResult result = new WebActionResult();

        if (value != null) {
            ControlModel<?> model = control.getModel();

            model.setValue(value);
            String id = model.getId();

            RefreshItem item = new RefreshItem(WebElementType.CONTROL, id);
            result.addItem(item);

        }

        return result;
    }
}
