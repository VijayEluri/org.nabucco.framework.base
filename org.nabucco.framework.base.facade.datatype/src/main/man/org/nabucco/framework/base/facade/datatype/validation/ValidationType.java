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
package org.nabucco.framework.base.facade.datatype.validation;

/**
 * ValidationType
 * <p/>
 * Defines the depth of the datatype validation (deep or shallow).
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum ValidationType {

    /**
     * Validates the whole datatype tree.
     */
    DEEP,

    /**
     * Validates only the first datatype in the tree.
     */
    SHALLOW

}
