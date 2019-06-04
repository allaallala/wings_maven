package edu.msstate.nsparc.wings.integration.models.trade.trainings;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Describes training courses entity.
 * Created by a.vnuchko on 27.04.2016.
 */
public class TrainingCourses {

    //Fields for checking user permissions
    private Boolean tcCreate;
    private Boolean tcView;
    private Boolean tcViewEditButton;
    private Boolean tcViewAuditButton;
    private Boolean tcEdit;
    private static final String TC_CREATE = "tcCreate";
    private static final String TC_VIEW = "tcView";
    private static final String TC_VIEW_EDIT = "tcViewEditButton";
    private static final String TC_VIEW_AUDIT = "tcViewAuditButton";
    private static final String TC_EDIT = "tcEdit";

    /**
     * Constructor with user permissions
     * @param role - user role
     */
    public TrainingCourses(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        tcCreate = Boolean.valueOf(prop.getProperty(TC_CREATE));
        tcView = Boolean.valueOf(prop.getProperty(TC_VIEW));
        tcViewEditButton = Boolean.valueOf(prop.getProperty(TC_VIEW_EDIT));
        tcViewAuditButton = Boolean.valueOf(prop.getProperty(TC_VIEW_AUDIT));
        tcEdit = Boolean.valueOf(prop.getProperty(TC_EDIT));
    }

    public Boolean getCourseCreate() {
        return tcCreate;
    }

    public Boolean getCourseView() {
        return tcView;
    }

    public Boolean getCourseViewEditButton() {
        return tcViewEditButton;
    }

    public Boolean getCourseViewAuditButton() {
        return tcViewAuditButton;
    }

    /**
     * If it's possible to edit training course
     * @return - true, if possible
     */
    public Boolean getCourseEdit() {
        return tcEdit;
    }

    public enum CourseType {
        ANY("any"), ACTIVE("Active"), INACTIVE("Inactive");

         CourseType(String type) {
             this.value = type;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }
}
