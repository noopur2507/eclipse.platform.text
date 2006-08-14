/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.texteditor.rulers;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;

import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IVerticalRulerColumn;

import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Class that has to be implemented by contributions to the
 * <code>org.eclipse.ui.texteditor.rulerColumns</code> extension point.
 * <p>
 * Subclasses must have a zero-argument constructor so that they can be created by
 * {@link IConfigurationElement#createExecutableExtension(String)}.
 * </p>
 * <p>
 * This API is provisional and may change any time before the 3.3 API freeze.
 * </p>
 * 
 * @since 3.3
 */
public abstract class RulerColumn implements IVerticalRulerColumn {
	/** The contribution descriptor. */
	private RulerColumnDescriptor fDescriptor;
	/** The target editor. */
	private ITextEditor fEditor;

	/**
	 * Creates a new ruler column.
	 */
	protected RulerColumn() {
	}

	/**
	 * Returns the extension point descriptor of this ruler. If the receiver was not created via the
	 * extension point mechanism, the descriptor may be <code>null</code>.
	 * 
	 * @return descriptor the extension point descriptor of this ruler
	 */
	public final RulerColumnDescriptor getDescriptor() {
		return fDescriptor;
	}

	/**
	 * Sets the descriptor (called right after the extension was instantiated).
	 * 
	 * @param descriptor the descriptor of the ruler column extension.
	 */
	final void setDescriptor(RulerColumnDescriptor descriptor) {
		Assert.isTrue(fDescriptor == null);
		Assert.isTrue(descriptor != null);
		fDescriptor= descriptor;
	}

	/**
	 * Sets the editor (called right after the extension was instantiated).
	 * 
	 * @param editor the editor targeted by this ruler instance
	 */
	final void setEditor(ITextEditor editor) {
		Assert.isLegal(editor != null);
		fEditor= editor;
	}

	/**
	 * Returns the editor targeted by this ruler instance, may return <code>null</code> before the
	 * receiver is fully initialized.
	 * 
	 * @return the editor targeted by this ruler instance
	 */
	protected final ITextEditor getEditor() {
		return fEditor;
	}

	/**
	 * Hook method called after a column has been instantiated, but before it is added to a
	 * {@link CompositeRuler}. The {@link #getEditor()} and {@link #getDescriptor()} methods are
	 * guaranteed to return non-<code>null</code> values after this call. Subclasses may replace.
	 */
	protected void columnCreated() {
	}

	/**
	 * Hook method called after a column has been removed from the {@link CompositeRuler}.
	 * Subclasses may replace.
	 */
	protected void columnRemoved() {
	}
}