package edu.msstate.nsparc.wings.integration.models.wia;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**Describes WIA training module
 * Created by a.vnuchko on 21.04.2016.
 */
public class WIATraining {

    //User permissions fields
    private Boolean wteCreate;
    private Boolean wiaTrainingEnrollmentView;
    private Boolean wiaTrainingEnrollmentViewEditButton;
    private Boolean wiaTrainingEnrollmentViewCreateAnotherButton;
    private Boolean wiaTrainingEnrollmentViewAuditButton;
    private Boolean wiaTrainingEnrollmentViewDeleteButton;
    private Boolean wteEdit;
    private static final String WTE_CREATE = "wteCreate";
    private static final String WTE_TRAINING_ENROLLMENT_VIEW = "wiaTrainingEnrollmentView";
    private static final String WTE_TRAINING_ENROLLMENT_VIEW_EDIT = "wiaTrainingEnrollmentViewEditButton";
    private static final String WTE_TRAINING_ENROLLMENT_VIEW_CREATE_ANOTHER = "wiaTrainingEnrollmentViewCreateAnotherButton";
    private static final String WTE_TRAINING_ENROLLMENT_VIEW_AUDIT = "wiaTrainingEnrollmentViewAuditButton";
    private static final String WTE_TRAINING_ENROLLMENT_VIEW_DELETE = "wiaTrainingEnrollmentViewDeleteButton";
    private static final String WTE_EDIT = "wteEdit";

    /**
     * Defines user permission for specified role
     * @param role - user role
     */
    public WIATraining(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        wteCreate = Boolean.valueOf(prop.getProperty(WTE_CREATE));
        wiaTrainingEnrollmentView = Boolean.valueOf(prop.getProperty(WTE_TRAINING_ENROLLMENT_VIEW));
        wiaTrainingEnrollmentViewEditButton = Boolean.valueOf(prop.getProperty(WTE_TRAINING_ENROLLMENT_VIEW_EDIT));
        wiaTrainingEnrollmentViewCreateAnotherButton = Boolean.valueOf(prop.getProperty(WTE_TRAINING_ENROLLMENT_VIEW_CREATE_ANOTHER));
        wiaTrainingEnrollmentViewAuditButton = Boolean.valueOf(prop.getProperty(WTE_TRAINING_ENROLLMENT_VIEW_AUDIT));
        wiaTrainingEnrollmentViewDeleteButton = Boolean.valueOf(prop.getProperty(WTE_TRAINING_ENROLLMENT_VIEW_DELETE));
        wteEdit = Boolean.valueOf(prop.getProperty(WTE_EDIT));
    }

    public Boolean getWiaTrainingCreate() {
        return wteCreate;
    }

    public Boolean getWiaTrainingView() {
        return wiaTrainingEnrollmentView;
    }

    public Boolean getWiaTrainingViewEdit() {
        return wiaTrainingEnrollmentViewEditButton;
    }

    public Boolean getWiaTrainingViewCreateAnother() {
        return wiaTrainingEnrollmentViewCreateAnotherButton;
    }

    public Boolean getWiaTrainingViewAudit() {
        return wiaTrainingEnrollmentViewAuditButton;
    }

    public Boolean getWiaTrainingViewDelete() {
        return wiaTrainingEnrollmentViewDeleteButton;
    }

    public Boolean getWiaTrainingEdit() {
        return wteEdit;
    }
}
