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
package org.nabucco.framework.base.facade.datatype.extension.msgset.searchtree;

/**
 * MessageSearchElement
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public abstract class MessageSearchElement implements MessageSearchComponent {

    MessageSearchComponent[] EMPTY_ARRAY = new MessageSearchComponent[0];

    private String id;

    private MessageSearchComponent parent;

    /**
     * Creates a new {@link MessageSearchElement} instance.
     * 
     * @param parent
     *            the parent component
     * @param id
     *            the component id
     */
    public MessageSearchElement(MessageSearchComponent parent, String id) {
        this.id = id;
        this.parent = parent;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public MessageSearchComponent getParent() {
        return this.parent;
    }

    @Override
    public void add(MessageSearchComponent component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MessageSearchComponent[] getComponents() {
        return EMPTY_ARRAY;
    }

    @Override
    public MessageSearchComponent get(String id) {
        return null;
    }
}
