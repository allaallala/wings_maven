package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Program outcomes entity (mainly to describe user permissions)
 * Created by a.vnuchko on 04.07.2016.
 */
public class ProgramOutcomes {

    //permission variables
    private Boolean poView;
    private Boolean poViewManage;
    private Boolean poEdit;
    private Boolean poAudit;
    private static final String PO_VIEW = "poView";
    private static final String PO_VIEW_MANAGE = "poViewManage";
    private static final String PO_EDIT = "poEdit";
    private static final String PO_AUDIT = "poAudit";

    /**
     * Default constructor
     * @param role - user role.
     */
    public ProgramOutcomes(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        poView = Boolean.valueOf(prop.getProperty(PO_VIEW));
        poViewManage = Boolean.valueOf(prop.getProperty(PO_VIEW_MANAGE));
        poEdit = Boolean.valueOf(prop.getProperty(PO_EDIT));
        poAudit = Boolean.valueOf(prop.getProperty(PO_AUDIT));
    }

    public Boolean getPoView() {
        return poView;
    }

    public Boolean getPoViewManage() {
        return poViewManage;
    }

    public Boolean getPoEdit() {
        return poEdit;
    }

    public Boolean getPoAudit() {
        return poAudit;
    }
}
