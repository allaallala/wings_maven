package edu.msstate.nsparc.wings.integration.models.wagnerPeyser;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Represents contact entity
 * Created by a.vnuchko on 13.07.2016.
 */
public class Contact {
    //User permission fields
    private Boolean conCreate;
    private Boolean conView;
    private Boolean conEdit;
    private Boolean conCheckNeutral;
    private Boolean conCheckTied;
    private Boolean conCheckNotTied;
    private static final String CON_CREATE = "conCreate";
    private static final String CON_VIEW = "conView";
    private static final String CON_EDIT = "conEdit";
    private static final String CON_CHECK_NEUTRAL = "conCheckNeutral";
    private static final String CON_CHECK_TIED = "conCheckTied";
    private static final String CON_CHECK_NOT_TIED = "conCheckNotTied";

    /**
     * Default constructor
     * @param role - user role
     */
    public Contact(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        conCreate = Boolean.valueOf(prop.getProperty(CON_CREATE));
        conView = Boolean.valueOf(prop.getProperty(CON_VIEW));
        conEdit = Boolean.valueOf(prop.getProperty(CON_EDIT));
        conCheckNeutral = Boolean.valueOf(prop.getProperty(CON_CHECK_NEUTRAL));
        conCheckTied = Boolean.valueOf(prop.getProperty(CON_CHECK_TIED));
        conCheckNotTied = Boolean.valueOf(prop.getProperty(CON_CHECK_NOT_TIED));
    }

    //Permissons getters

    public Boolean getConCreate() {
        return conCreate;
    }

    public Boolean getConView() {
        return conView;
    }

    public Boolean getConEdit() {
        return conEdit;
    }

    public Boolean getConCheckNeutral() {
        return conCheckNeutral;
    }

    public Boolean getConCheckTied() {
        return conCheckTied;
    }

    public Boolean getConCheckNotTied() {
        return conCheckNotTied;
    }
}
