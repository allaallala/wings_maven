package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * This class is used to store information for an Assessment record
 */
public class Assessment {
    private Participant participant;
    private String program;
    private String functionalArea;
    private String dateAdministered;
    private String type;
    private String score;
    private String category;
    private String educationalLevel;
    private boolean isPreTest;
    private boolean participantPrePopulated = false;

    //User permissions
    private Boolean asmCreate;
    private Boolean asmView;
    private Boolean asmViewEditButton;
    private Boolean asmViewAuditButton;
    private Boolean asmEdit;
    private Boolean asmDelete;
    private static final String ASM_CREATE = "asmCreate";
    private static final String ASM_VIEW = "asmView";
    private static final String ASM_VIEW_EDIT = "asmViewEditButton";
    private static final String ASM_VIEW_AUDIT = "asmViewAuditButton";
    private static final String ASM_EDIT = "asmEdit";
    private static final String ASM_DELETE = "asmDelete";
    private static final String ASM_PROGRAM = "asmProgram";

    /**
     * This method is used for creating Assessment record with some pre-defined parameters
     * @param partp Participant that will be used during creation
     * @param programm Program (Wagner-Peyser/WIA/TRADE)
     */
    public Assessment(Participant partp, String programm) {
        generateRandomData(partp,programm);
    }

    public Assessment(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        asmCreate = Boolean.valueOf(prop.getProperty(ASM_CREATE));
        asmView = Boolean.valueOf(prop.getProperty(ASM_VIEW));
        asmViewEditButton = Boolean.valueOf(prop.getProperty(ASM_VIEW_EDIT));
        asmViewAuditButton = Boolean.valueOf(prop.getProperty(ASM_VIEW_AUDIT));
        asmEdit = Boolean.valueOf(prop.getProperty(ASM_EDIT));
        asmDelete = Boolean.valueOf(prop.getProperty(ASM_DELETE));
        generateRandomData(new Participant(), prop.getProperty(ASM_PROGRAM));
    }

    /**
     * This method is used for creating Assessment record with some pre-defined parameters
     * @param partp Participant that will be used during creation
     * @param programm Program (Wagner-Peyser/WIA/TRADE)
     * @param newDateAdministered - date administred
     * @param newType - assessment type
     * @param newScore - assessment score
     * @param newFunctionalArea - functional area of the assessment.
     */
    public Assessment(Participant partp, String programm, String newDateAdministered, String newType, String newScore, String newFunctionalArea) {
        this.participant = partp;
        this.program = programm;
        dateAdministered = newDateAdministered;
        type = newType;
        score = newScore;
        functionalArea = newFunctionalArea;
        isPreTest = true;
    }

    /**
     * Generate random data for assessment
     * @param partp - participant
     * @param programm - assessment program.
     */
    private void generateRandomData(Participant partp, String programm) {
        this.participant = partp;
        this.program = programm;
        dateAdministered = CommonFunctions.getYesterdayDate();
        type = "ABLE";
        score = CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH);
        isPreTest = true;
        functionalArea = "Reading/Reading for Information";
        category = "ABE";
        educationalLevel = "Beginning ESL Literacy";
    }

    //User permissions

    public Boolean getAsmCreate() {
        return asmCreate;
    }

    public Boolean getAsmView() {
        return asmView;
    }

    public Boolean getAsmViewEditButton() {
        return asmViewEditButton;
    }

    public Boolean getAsmViewAuditButton() {
        return asmViewAuditButton;
    }

    public Boolean getAsmEdit() {
        return asmEdit;
    }

    public Boolean getAsmDelete() {
        return asmDelete;
    }

    public boolean isParticipantPrePopulated() {
        return participantPrePopulated;
    }

    public void setParticipantPrePopulated(boolean partpPrePopulated) {
        this.participantPrePopulated = partpPrePopulated;
    }

    public Participant getParticipant() {
        return participant;
    }

    public String getFunctionalArea() {
        return functionalArea;
    }

    public String getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }

    public String getProgram() {
        return program;
    }

    public String getType() {
        return type;
    }

    public String getDateAdministered() {
        return dateAdministered;
    }

    public String getEducationalLevel() {
        return educationalLevel;
    }

    public Boolean getPreTest() {
        return isPreTest;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public void setScore(String newScore) {
        this.score = newScore;
    }

    public void setFunctionalArea(String newFunctionalArea) {
        this.functionalArea = newFunctionalArea;
    }

    public void setEducationalLevel(String newEduLevel) {
        this.educationalLevel = newEduLevel;
    }

    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    public void setDateAdministered(String newDate) {
        this.dateAdministered = newDate;
    }

    public void setPreTest(Boolean stateNew) {
        this.isPreTest = stateNew;
    }

    public void setParticipant(Participant newParticipant) {
        this.participant = newParticipant;
    }
}
