package edu.msstate.nsparc.wings.integration.models.employer;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.AccountUtils;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;


/**
 * Rapid Response event entity
 */
public class RapidResponseEvent {
    //Role flags
    private Boolean rrCreate;
    private Boolean rrViewEditButton;
    private Boolean rrViewAuditButton;
    private Boolean rrEdit;
    private Boolean rrEditDateNotificationEdit;
    private static final String RR_CREATE = "rrCreate";
    private static final String RR_VIEW_EDIT = "rrViewEditButton";
    private static final String RR_VIEW_AUDIT = "rrViewAuditButton";
    private static final String RR_EDIT = "rrEdit";
    private static final String RR_EDIT_DATE_NOTIFICATION = "rrEditDateNotificationEdit";

    //RRE class fields
    private Employer employer;
    private String workforceArea;
    private String notificationDate;
    private String impactDate;
    private String eventDate;
    private String numberOfPotentiallyAffectedEmployees;
    private String numberOfEmployeesServed;
    private boolean tradeAffected;
    private String description;

    /**
     * Specified variables connected with user permissions
     * @param role  - current user role
     */
    public RapidResponseEvent(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        rrCreate = Boolean.valueOf(prop.getProperty(RR_CREATE));
        rrViewEditButton = Boolean.valueOf(prop.getProperty(RR_VIEW_EDIT));
        rrViewAuditButton = Boolean.valueOf(prop.getProperty(RR_VIEW_AUDIT));
        rrEdit = Boolean.valueOf(prop.getProperty(RR_EDIT));
        rrEditDateNotificationEdit = Boolean.valueOf(prop.getProperty(RR_EDIT_DATE_NOTIFICATION));
    }

    public RapidResponseEvent() {
        employer = new Employer(AccountUtils.getEmployerAccount());
        generateRandomData();
    }

    /**
     * Rapid response event with specified employer
     * @param empl - employer
     */
    public RapidResponseEvent(Employer empl) {
        this.employer = empl;
        generateRandomData();
    }

    //User permissions block

    public Boolean getRRCreate() {
        return rrCreate;
    }

    public Boolean getRRViewEditButton() {
        return rrViewEditButton;
    }

    public Boolean getRrViewAuditButton() {
        return rrViewAuditButton;
    }

    public Boolean getRREdit() {
        return rrEdit;
    }

    public Boolean getRREditDateNotification() {
        return rrEditDateNotificationEdit;
    }

    /**
     * Generates random data for rre
     */
    public final void generateRandomData() {
        workforceArea = "Delta";
        notificationDate = CommonFunctions.getCurrentDate();
        impactDate = CommonFunctions.getCurrentDate();
        eventDate = CommonFunctions.getCurrentDate();
        numberOfPotentiallyAffectedEmployees = CommonFunctions.getRandomIntegerNumber(2);
        numberOfEmployeesServed = CommonFunctions.getRandomIntegerNumber(1);
        tradeAffected = false;
        description = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    }

    public Employer getEmployer() {
        return employer;
    }

    public String getWorkforceArea() {
        return workforceArea;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getImpactDate() {
        return impactDate;
    }

    public String getNumberOfPotentiallyAffectedEmployees() {
        return numberOfPotentiallyAffectedEmployees;
    }

    public String getNumberOfEmployeesServed() {
        return numberOfEmployeesServed;
    }

    public boolean isTradeAffected() {
        return tradeAffected;
    }

    public String getDescription() {
        return description;
    }

    public void setNotificationDate(String newNotifDate) {
        this.notificationDate = newNotifDate;
    }

    public void setAffectedEmployee(String newEmployee) {
        this.numberOfPotentiallyAffectedEmployees = newEmployee;
    }

    public void setServedEmployee(String newServedEmployee) {
        this.numberOfEmployeesServed = newServedEmployee;
    }
}
