package edu.msstate.nsparc.wings.integration.models.wia;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Youth Goal class.
 * Created by a.vnuchko on 28.04.2016.
 */
public class YouthGoal {
    //User permissions fields
    private Boolean ygCreate;
    private Boolean ygView;
    private Boolean ygsViewEditButton;
    private Boolean ygViewAuditButton;
    private Boolean ygEdit;
    private static final String YG_CREATE = "ygCreate";
    private static final String YG_VIEW = "ygView";
    private static final String YG_VIEW_EDIT = "ygsViewEditButton";
    private static final String YG_VIEW_AUDIT = "ygViewAuditButton";
    private static final String YG_EDIT = "ygEdit";

    /**
     * Defines user permission for specified role
     * @param role - user role
     */
    public YouthGoal(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        ygCreate = Boolean.valueOf(prop.getProperty(YG_CREATE));
        ygView = Boolean.valueOf(prop.getProperty(YG_VIEW));
        ygsViewEditButton = Boolean.valueOf(prop.getProperty(YG_VIEW_EDIT));
        ygViewAuditButton = Boolean.valueOf(prop.getProperty(YG_VIEW_AUDIT));
        ygEdit = Boolean.valueOf(prop.getProperty(YG_EDIT));
    }

    public Boolean getGoalCreate() {
        return ygCreate;
    }

    public Boolean getGoalView() {
        return ygView;
    }

    public Boolean getGoalViewEditButton() {
        return ygsViewEditButton;
    }

    public Boolean getGoalViewAuditButton() {
        return ygViewAuditButton;
    }

    public Boolean getGoalEdit() {
        return ygEdit;
    }
}
