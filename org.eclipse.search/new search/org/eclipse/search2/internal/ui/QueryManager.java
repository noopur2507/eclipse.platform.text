/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.search2.internal.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.search.ui.IQueryListener;
import org.eclipse.search.ui.ISearchQuery;

class QueryManager {
	private List fQueries;
	private List fListeners;
	public QueryManager() {
		super();
		// an ArrayList should be plenty fast enough (few searches).
		fQueries= new ArrayList();
		fListeners= new ArrayList();
	}
	synchronized ISearchQuery[] getQueries() {
		ISearchQuery[] result= new ISearchQuery[fQueries.size()];
		return (ISearchQuery[]) fQueries.toArray(result);
	}

	void removeQuery(ISearchQuery query) {
		synchronized (fQueries) {
			fQueries.remove(query);
		}
		fireRemoved(query);
	}

	void addQuery(ISearchQuery query) {
		synchronized (fQueries) {
			if (fQueries.contains(query))
				return;
			fQueries.add(0, query);
		}
		fireAdded(query);
	}
	
	void addQueryListener(IQueryListener l) {
		synchronized (fListeners) {
			fListeners.add(l);
		}
	}

	void removeQueryListener(IQueryListener l) {
		synchronized (fListeners) {
			fListeners.remove(l);
		}
	}
	
	void fireAdded(ISearchQuery query) {
		Set copiedListeners= new HashSet();
		synchronized (fListeners) {
			copiedListeners.addAll(fListeners);
		}
		Iterator listeners= copiedListeners.iterator();
		while (listeners.hasNext()) {
			IQueryListener l= (IQueryListener) listeners.next();
			l.queryAdded(query);
		}
	}

	void fireRemoved(ISearchQuery query) {
		Set copiedListeners= new HashSet();
		synchronized (fListeners) {
			copiedListeners.addAll(fListeners);
		}
		Iterator listeners= copiedListeners.iterator();
		while (listeners.hasNext()) {
			IQueryListener l= (IQueryListener) listeners.next();
			l.queryRemoved(query);
		}
	}
	
	void fireStarting(ISearchQuery query) {
		Set copiedListeners= new HashSet();
		synchronized (fListeners) {
			copiedListeners.addAll(fListeners);
		}
		Iterator listeners= copiedListeners.iterator();
		while (listeners.hasNext()) {
			IQueryListener l= (IQueryListener) listeners.next();
			l.queryStarting(query);
		}
	}

	void fireFinished(ISearchQuery query) {
		Set copiedListeners= new HashSet();
		synchronized (fListeners) {
			copiedListeners.addAll(fListeners);
		}
		Iterator listeners= copiedListeners.iterator();
		while (listeners.hasNext()) {
			IQueryListener l= (IQueryListener) listeners.next();
			l.queryFinished(query);
		}
	}

	void removeAll() {
		Set copiedSearches= new HashSet();
		synchronized (fQueries) {
			copiedSearches.addAll(fQueries);
			fQueries.clear();
			Iterator iter= copiedSearches.iterator();
			while (iter.hasNext()) {
				ISearchQuery element= (ISearchQuery) iter.next();
				fireRemoved(element);
			}
		}
	}

	void queryFinished(ISearchQuery query) {
		fireFinished(query);
	}

	void queryStarting(ISearchQuery query) {
		fireStarting(query);
	}

}
