package edu.msstate.nsparc.wings.integration.models.employer;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Represents employer service enrollment entity
 * Created by a.vnuchko on 14.07.2016.
 */
public class EmployerServiceEnrollment {
    //User permission fields
    private Boolean eseCreate;
    private Boolean eseView;
    private Boolean eseEdit;
    private Boolean eseCheckNeutral;
    private Boolean eseCheckTied;
    private Boolean eseCheckNotTied;
    private static final String ESE_CREATE = "eseCreate";
    private static final String ESE_VIEW = "eseView";
    private static final String ESE_EDIT = "eseEdit";
    private static final String ESE_CHECK_NEUTRAL = "eseCheckNeutral";
    private static final String ESE_CHECK_TIED = "eseCheckTied";
    private static final String ESE_CHECK_NOT_TIED = "eseCheckNotTied";

    public EmployerServiceEnrollment(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        eseCreate = Boolean.valueOf(prop.getProperty(ESE_CREATE));
        eseView = Boolean.valueOf(prop.getProperty(ESE_VIEW));
        eseEdit = Boolean.valueOf(prop.getProperty(ESE_EDIT));
        eseCheckNeutral = Boolean.valueOf(prop.getProperty(ESE_CHECK_NEUTRAL));
        eseCheckTied = Boolean.valueOf(prop.getProperty(ESE_CHECK_TIED));
        eseCheckNotTied = Boolean.valueOf(prop.getProperty(ESE_CHECK_NOT_TIED));
    }

    //Permissons getters

    public Boolean getEseCreate() {
        return eseCreate;
    }

    public Boolean getEseView() {
        return eseView;
    }

    public Boolean getEseEdit() {
        return eseEdit;
    }

    public Boolean getEseCheckNeutral() {
        return eseCheckNeutral;
    }

    public Boolean getEseCheckTied() {
        return eseCheckTied;
    }

    public Boolean getEseCheckNotTied() {
        return eseCheckNotTied;
    }
}
