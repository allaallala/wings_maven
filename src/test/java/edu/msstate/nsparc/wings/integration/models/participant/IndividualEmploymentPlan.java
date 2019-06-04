package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.AccountUtils;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Individual employment plan enity
 */
public class IndividualEmploymentPlan {

    private Boolean iepCreate;
    private Boolean iepEdit;
    private Boolean iepView;
    private Boolean iepViewFormSign;
    private Boolean iepViewEditButton;
    private Boolean iepViewCreateAnother;
    private Boolean iepViewPrint;
    private Boolean iepViewAudit;
    private Participant participant;
    private boolean existingParticipant;
    private boolean nonTraditionalEmployment;
    private String barriersDescription;
    private String aptitudes;
    private String abilities;
    private List<Assessment> assessments;
    private String planDescription;
    private String assessmentProgram;
    private List<PlanStep> planSteps;
    private String creationDate;
    private static final Integer RANDOM_STEPS = 5;
    private static final Integer RANDOM_DATE = 60;
    String program = "Wagner-Peyser";
    private Boolean islogout;

    private static final String IEP_CREATE = "iepCreate";
    private static final String IEP_EDIT = "iepEdit";
    private static final String IEP_VIEW = "iepView";
    private static final String IEP_VIEW_FORM_SIGN = "iepViewFormSign";
    private static final String IEP_VIEW_EDIT = "iepViewEditButton";
    private static final String IEP_VIEW_CREATE_ANOTHER = "iepViewCreateAnother";
    private static final String IEP_VIEW_PRINT = "iepViewPrint";
    private static final String IEP_VIEW_AUDIT = "iepViewAudit";
    private static final String ASSESSMENT_PROGRAM = "assessmentProgram";

    /**
     * Default Individual employment plan
     */
    public IndividualEmploymentPlan() {
        participant = new Participant(AccountUtils.getParticipantAccount(), false);
        existingParticipant = false;
        generateRandomData(program);
    }

    /**
     * Initialize some logical variables, that shows user posibilities to change this module.
     * @param role - user role
     */
    public IndividualEmploymentPlan(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        iepCreate = Boolean.valueOf(prop.getProperty(IEP_CREATE));
        iepEdit = Boolean.valueOf(prop.getProperty(IEP_EDIT));
        iepView = Boolean.valueOf(prop.getProperty(IEP_VIEW));
        iepViewFormSign = Boolean.valueOf(prop.getProperty(IEP_VIEW_FORM_SIGN));
        iepViewEditButton = Boolean.valueOf(prop.getProperty(IEP_VIEW_EDIT));
        iepViewCreateAnother = Boolean.valueOf(prop.getProperty(IEP_VIEW_CREATE_ANOTHER));
        iepViewPrint = Boolean.valueOf(prop.getProperty(IEP_VIEW_PRINT));
        iepViewAudit = Boolean.valueOf(prop.getProperty(IEP_VIEW_AUDIT));
        participant = new Participant();
        existingParticipant = false;
        assessmentProgram = prop.getProperty(ASSESSMENT_PROGRAM);
        generateRandomData(assessmentProgram);
    }

    /**
     * Individual employment plan with specified participant
     * @param p - participant p
     */
    public IndividualEmploymentPlan(Participant p) {
        participant = p;
        existingParticipant = true;
        generateRandomData(program);
    }

    //User permissions

    public Boolean getIepCreate() {
        return iepCreate;
    }

    public Boolean getIepEdit() {
        return iepEdit;
    }

    public Boolean getIepView() {
        return iepView;
    }

    public Boolean getIepViewFormSign() {
        return iepViewFormSign;
    }

    public Boolean getIepViewEditButton() {
        return iepViewEditButton;
    }

    public Boolean getIepViewCreateAnother() {
        return iepViewCreateAnother;
    }

    public Boolean getIepViewPrint() {
        return iepViewPrint;
    }

    public Boolean getIepViewAudit() {
        return iepViewAudit;
    }


    /**
     * Generate random data
     * @param program - program (WIA, Wagner-Peyser, Trade)
     */
    public final void generateRandomData(String program) {
        nonTraditionalEmployment = false;
        islogout = true;
        barriersDescription = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        aptitudes = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        abilities = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        Assessment assessment = new Assessment(participant, program);
        assessment.setParticipantPrePopulated(true);
        assessments = new ArrayList<>();
        assessments.add(assessment);
        planDescription = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        creationDate = CommonFunctions.getCurrentDate();
        assessmentProgram = program;
        generatePlanSteps();
    }

    /**
     * Generate plan steps
     */
    public final void generatePlanSteps() {
        planSteps = new ArrayList<>();
        int n = CommonFunctions.getRandomInteger(RANDOM_STEPS);
        while (n == 0) {
            n = CommonFunctions.getRandomInteger(RANDOM_STEPS);
        }
        for (int i = 0; i < n; i++) {
            planSteps.add(new PlanStep());
        }
    }

    public Participant getParticipant() {
        return participant;
    }

    public boolean isNonTraditionalEmployment() {
        return nonTraditionalEmployment;
    }

    public String getBarriersDescription() {
        return barriersDescription;
    }

    public String getAptitudes() {
        return aptitudes;
    }

    public String getAbilities() {
        return abilities;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public String getAssessmentProgram() {
        return  assessmentProgram;
    }

    public List<PlanStep> getPlanSteps() {
        return planSteps;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Boolean getIsLogout() {
        return islogout;
    }

    public void setIsLogout(Boolean newIsLogout) {
        this.islogout = newIsLogout;
    }

    public void setCreationDate(String dateCreation) {
        this.creationDate = dateCreation;
    }

    public boolean isExistingParticipant() {
        return existingParticipant;
    }

    /**
     * Plan steps entity
     */
    public static class PlanStep {
        private String stepInformation;
        private String completionDate;

        /**
         * Default plan step
         */
        public PlanStep() {
            stepInformation = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
            completionDate = CommonFunctions.getRandomFutureDate(RANDOM_DATE);
        }

        public String getStepInformation() {
            return stepInformation;
        }

        public String getCompletionDate() {
            return completionDate;
        }
    }
}
