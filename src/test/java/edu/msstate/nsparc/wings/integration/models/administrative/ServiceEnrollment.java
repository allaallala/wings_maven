package edu.msstate.nsparc.wings.integration.models.administrative;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Describe participant service enrollment module.
 * Created by a.vnuchko on 28.03.2016.
 */
public class ServiceEnrollment {
    private Boolean pseCreate;
    private Boolean pseCreateYouthProviderLookup;
    private Boolean pseView;
    private Boolean pseViewEditButton;
    private Boolean pseViewAuditButton;
    private Boolean pseViewDeleteButton;
    private Boolean pseEdit;
    private Boolean pseEditYouthProviderLookup;
    private static final String PSE_CREATE = "pseCreate";
    private static final String PSE_CREATE_YOUTH_PROVIDER = "pseCreateYouthProviderLookup";
    private static final String PSE_VIEW = "pseView";
    private static final String PSE_VIEW_EDIT = "pseViewEditButton";
    private static final String PSE_VIEW_AUDIT = "pseViewAuditButton";
    private static final String PSE_VIEW_DELETE = "pseViewDeleteButton";
    private static final String PSE_EDIT = "pseEdit";
    private static final String PSE_EDIT_YOUTH_PROVIDER = "pseEditYouthProviderLookup";

    public ServiceEnrollment(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        pseCreate = Boolean.valueOf(prop.getProperty(PSE_CREATE));
        pseCreateYouthProviderLookup = Boolean.valueOf(prop.getProperty(PSE_CREATE_YOUTH_PROVIDER));
        pseView = Boolean.valueOf(prop.getProperty(PSE_VIEW));
        pseViewEditButton = Boolean.valueOf(prop.getProperty(PSE_VIEW_EDIT));
        pseViewAuditButton = Boolean.valueOf(prop.getProperty(PSE_VIEW_AUDIT));
        pseViewDeleteButton = Boolean.valueOf(prop.getProperty(PSE_VIEW_DELETE));
        pseEdit = Boolean.valueOf(prop.getProperty(PSE_EDIT));
        pseEditYouthProviderLookup = Boolean.valueOf(prop.getProperty(PSE_EDIT_YOUTH_PROVIDER));
    }

    public Boolean getPseCreate() {
        return pseCreate;
    }

    public Boolean getPseCreateYouthProviderLookup() {
        return pseCreateYouthProviderLookup;
    }

    public Boolean getPseView() {
        return pseView;
    }

    public Boolean getPseViewEditButton() {
        return pseViewEditButton;
    }

    public Boolean getPseViewAuditButton() {
        return pseViewAuditButton;
    }

    public Boolean getPseViewDeleteButton() {
        return pseViewDeleteButton;
    }

    public Boolean getPseEdit() {
        return pseEdit;
    }

    public Boolean getPseEditYouthProviderLookup() {
        return pseEditYouthProviderLookup;
    }
}
