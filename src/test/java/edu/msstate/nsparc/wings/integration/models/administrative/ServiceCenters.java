package edu.msstate.nsparc.wings.integration.models.administrative;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * Describes the Service Centers module.
 * Created by a.vnuchko on 08.07.2016.
 */
public class ServiceCenters {
    //user permissions
    private Boolean scCreate;
    private Boolean scView;
    private Boolean scViewEditJobCenterButton;
    private Boolean scViewCreateAnotherButton;
    private Boolean scViewAuditButton;
    private Boolean scViewEnableJobCenter;
    private Boolean scViewDisableJobCenter;
    private Boolean scEdit;

    //other fields
    private String workForceArea;
    private String centerName;
    private String centerNumber;
    private String disctrictCode;
    private String metroStatisticalArea;
    private String centerType;
    private String phoneNumber;
    private String ext;
    private String faxNumber;
    private String emailAddress;
    private String lineOne;
    private String lineTwo;
    private String city;
    private String zip;
    private String state;
    private String county;
    private String country;
    private static final String SC_CREATE = "scCreate";
    private static final String SC_VIEW = "scView";
    private static final String SC_VIEW_EDIT_JOB_CENTER = "scViewEditJobCenterButton";
    private static final String SC_VIEW_CREATE_ANOTHER = "scViewCreateAnotherButton";
    private static final String SC_VIEW_AUDIT = "scViewAuditButton";
    private static final String SC_VIEW_ENABLE_JOB_CENTER = "scViewEnableJobCenter";
    private static final String SC_VIEW_DISABLED_JOB_CENTER = "scViewDisableJobCenter";
    private static final String SC_EDIT = "scEdit";

    public ServiceCenters(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        scCreate = Boolean.valueOf(prop.getProperty(SC_CREATE));
        scView = Boolean.valueOf(prop.getProperty(SC_VIEW));
        scViewEditJobCenterButton = Boolean.valueOf(prop.getProperty(SC_VIEW_EDIT_JOB_CENTER));
        scViewCreateAnotherButton = Boolean.valueOf(prop.getProperty(SC_VIEW_CREATE_ANOTHER));
        scViewAuditButton = Boolean.valueOf(prop.getProperty(SC_VIEW_AUDIT));
        scViewEnableJobCenter = Boolean.valueOf(prop.getProperty(SC_VIEW_ENABLE_JOB_CENTER));
        scViewDisableJobCenter = Boolean.valueOf(prop.getProperty(SC_VIEW_DISABLED_JOB_CENTER));
        scEdit = Boolean.valueOf(prop.getProperty(SC_EDIT));
        generateRandomData();
    }

    public Boolean getScCreate() {
        return scCreate;
    }

    public Boolean getScView() {
        return scView;
    }

    public Boolean getScViewEditJobCenterButton() {
        return scViewEditJobCenterButton;
    }

    public Boolean getScViewCreateAnotherButton() {
        return scViewCreateAnotherButton;
    }

    public Boolean getScViewAuditButton() {
        return scViewAuditButton;
    }

    public Boolean getScViewEnableJobCenter() {
        return scViewEnableJobCenter;
    }

    public Boolean getScViewDisableJobCenter() {
        return scViewDisableJobCenter;
    }

    public Boolean getScEdit() {
        return scEdit;
    }

    public void generateRandomData() {
        workForceArea = "Mississippi Partnership";
        centerName = CommonFunctions.getRandomAlphanumericalCode(5);
        centerNumber = CommonFunctions.getRandomIntegerNumber(3);
        disctrictCode = CommonFunctions.getRandomIntegerNumber(2);
        metroStatisticalArea = CommonFunctions.getRandomIntegerNumber(4);
        centerType = "WIN Job Center";
        phoneNumber = "(" + CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH) + ") " + CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH)
                + "-" + CommonFunctions.getRandomIntegerNumber(Constants.ORIGINAL_DETAILS_LENGTH);
        ext = "1" + CommonFunctions.getRandomIntegerNumber(Constants.ORIGINAL_DETAILS_LENGTH);
        faxNumber = "1" + CommonFunctions.getRandomIntegerNumber(Constants.FEIN_LENGTH);
        emailAddress = "KBFFF@OXL." + CommonFunctions.getRandomAlphanumericalCode(3);
        lineOne = CommonFunctions.getRandomAlphanumericalCode(10);
        lineTwo = CommonFunctions.getRandomAlphanumericalCode(10);
        city = CommonFunctions.getRandomAlphanumericalCode(6);
        zip = CommonFunctions.getRandomIntegerNumber(5);
        country = "United States";
        state = "Mississippi";
        county = "Adams";
    }

    public String getWorkForceArea() {
        return workForceArea;
    }

    public String getCenterName() {
        return centerName;
    }

    public String getCenterNumber() {
        return centerNumber;
    }

    public String getDisctrictCode() {
        return disctrictCode;
    }

    public String getMetroStatisticalArea() {
        return metroStatisticalArea;
    }

    public String getCenterType() {
        return centerType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getExt() {
        return ext;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCounty() {
        return county;
    }

    public void setLineOne(String newLineOne) {
        this.lineOne = newLineOne;
    }

    public void setCity(String newCity) {
        this.city = newCity;
    }

    public void setZip(String newZip) {
        this.zip = newZip;
    }

    public void setCenterName(String newName) {
        this.centerName = newName;
    }
}
