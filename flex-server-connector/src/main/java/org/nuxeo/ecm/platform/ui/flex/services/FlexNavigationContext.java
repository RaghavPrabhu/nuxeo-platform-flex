/*
 * (C) Copyright 2006-2008 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Nuxeo - initial API and implementation
 *
 * $Id$
 */

package org.nuxeo.ecm.platform.ui.flex.services;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.flex.javadto.FlexDocumentModel;
import org.nuxeo.ecm.platform.ui.flex.mapping.DocumentModelTranslator;

/**
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
@Name("flexNavigationContext")
@Scope(ScopeType.SESSION)
public class FlexNavigationContext implements FlexContextManager {

    @In(create = true)
    private CoreSession flexDocumentManager;

    private DocumentModel currentDocument;

    @WebRemote
    public FlexDocumentModel getCurrentFlexDocument() throws Exception {
        return DocumentModelTranslator.toFlexType(currentDocument);
    }

    @WebRemote
    public void setCurrentFlexDocument(FlexDocumentModel currentDocument)
            throws Exception {
        this.currentDocument = DocumentModelTranslator.toDocumentModel(
                currentDocument, flexDocumentManager);
    }

    public DocumentModel getCurrentDocument() {
        return currentDocument;
    }

    public void setCurrentDocument(DocumentModel currentDocument) {
        this.currentDocument = currentDocument;
    }

}
