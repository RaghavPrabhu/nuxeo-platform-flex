package org.nuxeo.ecm.platform.ui.flex.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.DocumentRef;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.impl.blob.ByteArrayBlob;
import org.nuxeo.ecm.core.api.impl.blob.FileBlob;
import org.nuxeo.ecm.flex.javadto.FlexDocumentModel;
import org.nuxeo.ecm.platform.filemanager.api.FileManager;

@Name("flexRepositoryService")
@Scope(ScopeType.STATELESS)
public class FlexRepositoryService {

    @In(create=true)
    private transient CoreSession flexDocumentManager;
    
    @In(create=true)
    private transient FileManager fileManager;


    private DocumentRef getRefFromString(String refAsString)
    {
        if (refAsString==null)
            return null;

        if (refAsString.startsWith("/"))
            return new PathRef(refAsString);
        else
            return new IdRef(refAsString);
    }

    public DocumentModel getDocument(String refAsString) throws Exception
    {
        DocumentRef ref = getRefFromString(refAsString);
        if (ref==null)
            return null;
        else
            return getDocumentByRef(ref);
    }


    protected DocumentModel getDocumentByRef(DocumentRef docRef) throws Exception
    {
        return flexDocumentManager.getDocument(docRef);
    }


    public DocumentModel saveDocument(DocumentModel doc) throws Exception
    {
        doc=flexDocumentManager.saveDocument(doc);
        flexDocumentManager.save();
        return doc;
    }

    public FlexDocumentModel createDocumentModel(String parentPath, String type, String name)
    {
        FlexDocumentModel fdm = new FlexDocumentModel();
        fdm.setType(type);
        fdm.setPath(parentPath+"/" + name);
        fdm.setName(name);
        return fdm;
    }

    public void deleteDocument(String refAsString) throws ClientException
    {
        DocumentRef docRef = getRefFromString(refAsString);
        if (docRef!=null)
            flexDocumentManager.removeDocument(docRef);
    }

    public List<DocumentModel> getChildren(String refAsString) throws Exception
    {
        DocumentRef docRef = getRefFromString(refAsString);
        if (docRef==null)
            return null;
        DocumentModelList children = flexDocumentManager.getChildren(docRef);
        return children;
    }


    public String uploadFile(byte[] bytes, String fileName, String path) throws Exception
    {
                 Blob b = new ByteArrayBlob(bytes);
                 b.setFilename(fileName);
         fileManager.createDocumentFromBlob(flexDocumentManager, b, path, false, fileName);  
         
        return "success"; 

    }
}
