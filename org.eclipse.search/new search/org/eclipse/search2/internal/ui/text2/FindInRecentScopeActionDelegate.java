/*******************************************************************************
 * Copyright (c) 2006 Wind River Systems and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Markus Schorn - initial API and implementation 
 *******************************************************************************/
package org.eclipse.search2.internal.ui.text2;

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import org.eclipse.search2.internal.ui.SearchMessages;


/**
 * @author markus.schorn@windriver.com
 */
public class FindInRecentScopeActionDelegate extends RetrieverAction implements IWorkbenchWindowActionDelegate, IEditorActionDelegate {
	private IWorkbenchWindow fWindow;

	public FindInRecentScopeActionDelegate() {
		this(SearchMessages.FindInRecentScopeActionDelegate_text);
	}

	public FindInRecentScopeActionDelegate(String text) {
		setText(text);
	}

	// IWorkbenchWindowActionDelegate
	public void dispose() {
		fWindow= null;
	}

	// IWorkbenchWindowActionDelegate
	public void init(IWorkbenchWindow window) {
		fWindow= window;
	}

	// IEditorActionDelegate
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		fWindow= targetEditor.getSite().getWorkbenchWindow();
	}

	// IActionDelegate
	public void selectionChanged(IAction action, ISelection selection) {
	}

	// IActionDelegate
	final public void run(IAction action) {
		run();
	}

	// RetrieverAction
	protected IWorkbenchPage getWorkbenchPage() {
		if (fWindow != null) {
			return fWindow.getActivePage();
		}
		return null;
	}

	protected boolean modifyQuery(RetrieverQuery query) {
		IWorkbenchPage page= getWorkbenchPage();
		if (page == null) {
			return false;
		}
		String searchFor= extractSearchTextFromSelection(page.getSelection());
		if (searchFor == null || searchFor.length() == 0) {
			searchFor= extractSearchTextFromEditor(page.getActiveEditor());
		}
		query.setSearchString(searchFor);
		return true;
	}
	
	protected Object extractObject(Class clazz, ISelection sel) {
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection ssel= (IStructuredSelection) sel;
			for (Iterator iter= ssel.iterator(); iter.hasNext();) {
				Object cand= iter.next();
				if (clazz.isAssignableFrom(cand.getClass())) {
					return cand;
				}
				if (cand instanceof IAdaptable) {
					cand= ((IAdaptable) cand).getAdapter(clazz);
					if (cand != null) {
						return cand;
					}
				}
			}
		}
		return null;
	}

}