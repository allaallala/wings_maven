package edu.msstate.nsparc.wings.integration.models.wia;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * WIA Enrollment
 */
public class WIAEnrollment {
    private Participant participant;
    private String eligibilityDate;
    private String fundingStream;
    private String wIB;

    //Fields for checking user permissions
    private Boolean weCreate;
    private Boolean weView;
    private Boolean weCreateAnotherButton;
    private Boolean weViewAuditButton;
    private Boolean weViewDeleteButton;
    private Boolean weEditBasicInfo;
    private Boolean weEditProgramInfo;
    private static final String WE_CREATE = "weCreate";
    private static final String WE_VIEW = "weView";
    private static final String WE_CREATE_ANOTHER = "weCreateAnotherButton";
    private static final String WE_VIEW_AUDIT = "weViewAuditButton";
    private static final String WE_VIEW_DELETE = "weViewDeleteButton";
    private static final String WE_EDIT_BASIC_INFORMATION = "weEditBasicInfo";
    private static final String WE_EDIT_PROGRAM_INFORMATION = "weEditProgramInfo";

    /**
     * Generate data for wia enrollment
     * @param partip participant user
     */
    public WIAEnrollment(Participant partip) {
        this.participant = partip;
        eligibilityDate = CommonFunctions.getYesterdayDate();
        fundingStream = "Local";
        wIB = "Mississippi Partnership";
    }

    /**
     * Constructor with user permissions
     * @param role - current user role
     */
    public WIAEnrollment(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        weCreate = Boolean.valueOf(prop.getProperty(WE_CREATE));
        weView = Boolean.valueOf(prop.getProperty(WE_VIEW));
        weCreateAnotherButton = Boolean.valueOf(prop.getProperty(WE_CREATE_ANOTHER));
        weViewAuditButton = Boolean.valueOf(prop.getProperty(WE_VIEW_AUDIT));
        weViewDeleteButton = Boolean.valueOf(prop.getProperty(WE_VIEW_DELETE));
        weEditBasicInfo = Boolean.valueOf(prop.getProperty(WE_EDIT_BASIC_INFORMATION));
        weEditProgramInfo = Boolean.valueOf(prop.getProperty(WE_EDIT_PROGRAM_INFORMATION));
    }

    public Boolean getEnrollmentCreate() {
        return weCreate;
    }

    public Boolean getEnrollmentView() {
        return weView;
    }

    public Boolean getEnrollmentCreateAnotherButton() {
        return weCreateAnotherButton;
    }

    public Boolean getEnrollmentViewAuditButton() {
        return weViewAuditButton;
    }

    public Boolean getEnrollmentDeleteButton() {
        return weViewDeleteButton;
    }

    public Boolean getEnrollmentEditBasicInfo() {
        return weEditBasicInfo;
    }

    public Boolean getEnrollmentEditProgramInfo() {
        return weEditProgramInfo;
    }

    public Participant getParticipant() {
        return participant;
    }

    public String getEligibilityDate() {
        return eligibilityDate;
    }

    public String getFundingStream() {
        return fundingStream;
    }

    public String getWIB() {
        return wIB;
    }
}
