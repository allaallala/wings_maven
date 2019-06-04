package edu.msstate.nsparc.wings.integration.models.wagnerPeyser;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.AccountUtils;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Describes the call-ins module
 * Created by a.vnuchko on 30.06.2016.
 */
public class CallIns {
    //User permissions fields
    private Boolean callinCreate;
    private Boolean callinView;
    private Boolean callinViewEdit;
    private Boolean callinViewAudit;
    private Boolean callinEdit;
    private static final String CALL_IN_CREATE = "callinCreate";
    private static final String CALL_IN_VIEW = "callinView";
    private static final String CALL_IN_VIEW_EDIT = "callinViewEdit";
    private static final String CALL_IN_VIEW_AUDIT = "callinViewAudit";
    private static final String CALL_IN_EDIT = "callinEdit";


    //Generate data
    private JobOrder jobOrder;
    String result = "Called,No Answer";

    /**
     * Default constructor, defines user permissions.
     * @param role - user role.
     */
    public CallIns(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        callinCreate = Boolean.valueOf(prop.getProperty(CALL_IN_CREATE));
        callinView = Boolean.valueOf(prop.getProperty(CALL_IN_VIEW));
        callinViewEdit = Boolean.valueOf(prop.getProperty(CALL_IN_VIEW_EDIT));
        callinViewAudit = Boolean.valueOf(prop.getProperty(CALL_IN_VIEW_AUDIT));
        callinEdit = Boolean.valueOf(prop.getProperty(CALL_IN_EDIT));
        jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
    }

    //Permissions getters

    public Boolean getCallinCreate() {
        return callinCreate;
    }

    public Boolean getCallinView() {
        return callinView;
    }

    public Boolean getCallinViewEdit() {
        return callinViewEdit;
    }

    public Boolean getCallinViewAudit() {
        return callinViewAudit;
    }

    public Boolean getCallinEdit() {
        return callinEdit;
    }

    public JobOrder getJobOrder() {
        return jobOrder;
    }

    public void setResult(String newResult) {
        this.result = newResult;
    }

    public String getResult() {
        return result;
    }
}
