package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Document upload entity (check user permissions)
 * Created by a.vnuchko on 14.09.2016.
 */
public class DocumentUpload {
    private Boolean documentUpload;
    private Boolean documentViewAttachedDocuments;
    private Boolean documentAttachDocumentRefferal;
    private Boolean documentDeleteDocument;
    private Boolean documentApproveSS;
    private static final String DOCUMENT_UPLOAD = "documentUpload";
    private static final String DOCUMENT_VIEW_ATTACHED_DOCUMENTS = "documentViewAttachedDocuments";
    private static final String DOCUMENT_ATTACH_DOCUMENT_REFERRAL = "documentAttachDocumentRefferal";
    private static final String DOCUMENT_DELETE_DOCUMENT = "documentDeleteDocument";
    private static final String DOCUMENT_APPROVE_SS = "documentApproveSS";

    /**
     * Base constructor
     * @param role - user role.
     */
    public DocumentUpload(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        documentUpload = Boolean.valueOf(prop.getProperty(DOCUMENT_UPLOAD));
        documentViewAttachedDocuments = Boolean.valueOf(prop.getProperty(DOCUMENT_VIEW_ATTACHED_DOCUMENTS));
        documentAttachDocumentRefferal = Boolean.valueOf(prop.getProperty(DOCUMENT_ATTACH_DOCUMENT_REFERRAL));
        documentDeleteDocument = Boolean.valueOf(prop.getProperty(DOCUMENT_DELETE_DOCUMENT));
        documentApproveSS = Boolean.valueOf(prop.getProperty(DOCUMENT_APPROVE_SS));
    }

    public Boolean getDocumentUpload() {
        return documentUpload;
    }

    public Boolean getDocumentViewAttachedDocuments() {
        return documentViewAttachedDocuments;
    }

    public Boolean getDocumentAttachDocumentRefferal() {
        return documentAttachDocumentRefferal;
    }

    public Boolean getDocumentDeleteDocument() {
        return documentDeleteDocument;
    }

    public Boolean getDocumentApproveSS() {
        return documentApproveSS;
    }
}
