package edu.msstate.nsparc.wings.integration.models.administrative;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Describes the [Services] module
 * Created by a.vnuchko on 08.04.2016.
 */
public class Services {
    private Boolean servicesCreate;

    private Boolean servicesViewAuditButton;
    private Boolean servicesEdit;
    private static final String SERVICES_CREATE = "servicesCreate";
    private static final String SERVICES_VIEW_AUDIT = "servicesViewAuditButton";
    private static final String SERVICES_EDIT = "servicesEdit";

    public Services(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        servicesCreate = Boolean.valueOf(prop.getProperty(SERVICES_CREATE));
        servicesViewAuditButton = Boolean.valueOf(prop.getProperty(SERVICES_VIEW_AUDIT));
        servicesEdit = Boolean.valueOf(prop.getProperty(SERVICES_EDIT));
    }

    public Boolean getServicesCreate() {
        return servicesCreate;
    }

    public Boolean getAuditButton() {
        return servicesViewAuditButton;
    }

    public Boolean getServicesEdit() {
        return servicesEdit;
    }
}
