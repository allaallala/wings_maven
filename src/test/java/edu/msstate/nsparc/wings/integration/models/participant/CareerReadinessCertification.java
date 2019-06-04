package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * Store information for a Career Readiness Certification record
 */
public class CareerReadinessCertification {

    private Boolean crcCreate;
    private Boolean crcView;
    private Boolean crcViewEdit;
    private Boolean crcViewAudit;
    private Boolean crcEdit;
    private static final String CRC_CREATE = "crcCreate";
    private static final String CRC_VIEW = "crcView";
    private static final String CRC_VIEW_EDIT = "crcViewEdit";
    private static final String CRC_VIEW_AUDIT = "crcViewAudit";
    private static final String CRC_EDIT = "crcEdit";
    private static final String PROGRAM = "program";

    private Participant participant;
    private String dateAdministired;
    private Assessment appliedMathematics;
    private Assessment locatingInformation;
    private Assessment readingForInformation;
    private String result;
    private String crcProgram;
    private static final String WAGNER_PEYSER = "Wagner-Peyser";
    private static final String WORKKEYS = "WorkKeys";
    private static final String UNO_DOS_TRES = "%1$s - %2$s - %3$s";

    private static final Integer SCORE_MIN = 65;
    private static final Integer SCORE_MAX = 90;

    /**
     * Generate data for career readiness certification
     */
    public CareerReadinessCertification() {
        generateRadnomData(WAGNER_PEYSER);
    }

    /**
     * Generate data for career readiness certification
     */
    public CareerReadinessCertification(String program) {
        generateRadnomData(program);
    }

    public CareerReadinessCertification(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        crcCreate = Boolean.valueOf(prop.getProperty(CRC_CREATE));
        crcView = Boolean.valueOf(prop.getProperty(CRC_VIEW));
        crcViewEdit = Boolean.valueOf(prop.getProperty(CRC_VIEW_EDIT));
        crcViewAudit = Boolean.valueOf(prop.getProperty(CRC_VIEW_AUDIT));
        crcEdit = Boolean.valueOf(prop.getProperty(CRC_EDIT));
        crcProgram = prop.getProperty(PROGRAM);
        generateRadnomData(crcProgram);
    }

    /**
     * Generate data for career readiness certification
     * @param program - program.
     */
    private void generateRadnomData(String program) {
        participant = new Participant();
        dateAdministired = CommonFunctions.getCurrentDate();
        appliedMathematics = new Assessment(participant, program);
        appliedMathematics.setFunctionalArea("Mathematics");
        appliedMathematics.setType(WORKKEYS);
        appliedMathematics.setScore(CommonFunctions.getRandomIntegerInLimits(SCORE_MIN, SCORE_MAX,2).toString());

        locatingInformation = new Assessment(participant, program);
        locatingInformation.setFunctionalArea("Locating Information");
        locatingInformation.setType(WORKKEYS);
        locatingInformation.setScore(CommonFunctions.getRandomIntegerInLimits(SCORE_MIN, SCORE_MAX,2).toString());

        readingForInformation = new Assessment(participant, program);
        readingForInformation.setFunctionalArea("Reading/Reading for Information");
        readingForInformation.setType(WORKKEYS);
        readingForInformation.setScore(CommonFunctions.getRandomIntegerInLimits(SCORE_MIN, SCORE_MAX,2).toString());

        crcProgram = program;
        result = "Below Certification Level";
    }

    //User permission block

    public Boolean getCrcCreate() {
        return crcCreate;
    }

    public Boolean getCrcView() {
        return crcView;
    }

    public Boolean getCrcViewEdit() {
        return crcViewEdit;
    }

    public Boolean getCrcViewAudit() {
        return crcViewAudit;
    }

    public Boolean getCrcEdit() {
        return crcEdit;
    }

    /**
     * Applied mathematics data in string format
     * @return data for applied mathematics in String.
     */
    public String getAppliedMathematicsString() {
        return String.format(UNO_DOS_TRES, appliedMathematics.getType(), appliedMathematics.getFunctionalArea(), appliedMathematics.getDateAdministered());
    }

    /**
     * Locating information data in string format
     * @return data for locating information in String.
     */
    public String getLocatingInformationString() {
        return String.format(UNO_DOS_TRES, locatingInformation.getType(), locatingInformation.getFunctionalArea(), locatingInformation.getDateAdministered());
    }

    /**
     * Reading information data in string format
     * @return data for reading information.
     */
    public String getReadingForInformationString() {
        return String.format(UNO_DOS_TRES, readingForInformation.getType(), readingForInformation.getFunctionalArea(), readingForInformation.getDateAdministered());
    }

    public String getCrcProgram() {
        return crcProgram;
    }

    public Participant getParticipant() {
        return participant;
    }

    public String getDateAdministired() {
        return dateAdministired;
    }

    public Assessment getAppliedMathematics() {
        return appliedMathematics;
    }

    public Assessment getLocatingInformation() {
        return locatingInformation;
    }

    public Assessment getReadingForInformation() {
        return readingForInformation;
    }

    public String getResult() {
        return result;
    }

    public void setDateAdministired(String dateAdmin) {
        this.dateAdministired = dateAdmin;
    }

    public void setParticipant(Participant newParticipant) {
        this.participant = newParticipant;
    }
}
