package edu.msstate.nsparc.wings.integration.models.administrative;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * Describes user permission for LWIA
 * Created by a.vnuchko on 05.07.2016.
 */
public class LWIA {
    //User permission field
    private Boolean lwiaCreate;
    private Boolean lwiaView;
    private Boolean lwiaViewEditButton;
    private Boolean lwiaViewCreateAnotherButton;
    private Boolean lwiaViewAuditButton;
    private Boolean lwiaEdit;
    private static final String LWIA_CREATE = "lwiaCreate";
    private static final String LWIA_VIEW = "lwiaView";
    private static final String LWIA_VIEW_EDIT = "lwiaViewEditButton";
    private static final String LWIA_VIEW_CREATE_ANOTHER = "lwiaViewCreateAnotherButton";
    private static final String LWIA_VIEW_AUDIT = "lwiaViewAuditButton";
    private static final String LWIA_EDIT = "lwiaEdit";
    private String[] detailsFirst;
    private String[] addressFirst;

    public LWIA(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        lwiaCreate = Boolean.valueOf(prop.getProperty(LWIA_CREATE));
        lwiaView = Boolean.valueOf(prop.getProperty(LWIA_VIEW));
        lwiaViewEditButton = Boolean.valueOf(prop.getProperty(LWIA_VIEW_EDIT));
        lwiaViewCreateAnotherButton = Boolean.valueOf(prop.getProperty(LWIA_VIEW_CREATE_ANOTHER));
        lwiaViewAuditButton = Boolean.valueOf(prop.getProperty(LWIA_VIEW_AUDIT));
        lwiaEdit = Boolean.valueOf(prop.getProperty(LWIA_EDIT));
        detailsFirst = new String[]{CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH) + "area",
                CommonFunctions.getRandomIntegerNumber(Constants.EMAIL_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomIntegerNumber(Constants.EMAIL_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH) + "@" + CommonFunctions.getRandomLiteralCode(Constants.CODE_LENGTH)
                        + "." + CommonFunctions.getRandomLiteralCode(Constants.CODE_LENGTH), "(123) 456-" + CommonFunctions.getRandomIntegerNumber(4)};
        addressFirst = new String[]{CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),
                CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH),
                CommonFunctions.getRandomIntegerNumber(Constants.EMAIL_LENGTH),
                "Hawaii",
                "United States"};
    }

    public String[] getDetails() {
        return detailsFirst;
    }

    public String[] getAddress() {
        return addressFirst;
    }

    //User permission getters.

    public Boolean getLwiaCreate() {
        return lwiaCreate;
    }

    public Boolean getLwiaView() {
        return lwiaView;
    }

    public Boolean getLwiaViewEditButton() {
        return lwiaViewEditButton;
    }

    public Boolean getLwiaViewCreateAnotherButton() {
        return lwiaViewCreateAnotherButton;
    }

    public Boolean getLwiaViewAuditButton() {
        return lwiaViewAuditButton;
    }

    public Boolean getLwiaEdit() {
        return lwiaEdit;
    }

    public void setParameters(LWIA lwia) {
        this.detailsFirst = lwia.getDetails();
        this.addressFirst = lwia.getAddress();
    }
}
