/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2023 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ui;

import org.eclipse.jface.viewers.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite selection provider
 */
public class CustomSelectionProvider implements ISelectionProvider {

    private List<ISelectionChangedListener> listeners = new ArrayList<>();
    private ISelection selection;

    public CustomSelectionProvider()
    {
        selection = new StructuredSelection();
    }

    @Override
    public void addSelectionChangedListener(ISelectionChangedListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeSelectionChangedListener(ISelectionChangedListener listener)
    {
        listeners.remove(listener);
    }

    @Override
    public ISelection getSelection()
    {
        return selection;
    }

    @Override
    public void setSelection(ISelection selection)
    {
        this.selection = selection;
        for (ISelectionChangedListener listener : listeners) {
            listener.selectionChanged(new SelectionChangedEvent(this, selection));
        }

    }
}
