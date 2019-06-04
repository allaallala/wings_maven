package edu.msstate.nsparc.wings.integration.models.trade;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionStatus;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.PetitionValues;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.AccountUtils;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

import static framework.CommonFunctions.getRandomInteger;
import static framework.CommonFunctions.getRandomLiteralCode;

/**
 * Petition entity
 */
public class Petition {

    private Employer employer;
    private String number;
    private PetitionType type;
    private PetitionStatus status;
    private String fileDate;
    // Also may be displayed as Certification Date:
    private String decisionDate;
    private String impactDate;
    private String department;
    private String typeOfWork;
    private boolean outOfState;
    private static final Integer DATE_DECISION = 320;
    private static final Integer DATE_IMPACT = 410;

    //User permissions
    private Boolean petCreate;
    private Boolean petEdit;
    private Boolean petView;
    private Boolean petManageAffectedEmployees;
    private static final String PET_CREATE = "petCreate";
    private static final String PET_EDIT = "petEdit";
    private static final String PET_VIEW = "petView";
    private static final String PET_MANAGE_AFFECTED_EMPLOYEES = "petManageAffectedEmployees";


    /**
     * Petition with specified desired type
     * @param desiredType - desired type of petition
     */
    public Petition(PetitionType desiredType) {
        employer = new Employer(AccountUtils.getEmployerAccount());
        type = desiredType;
        number = getRandomPetitionNumber(type);
        status = PetitionStatus.CERTIFIED;
        generateRandomData();
    }

    /**
     * Describe user permissions for petitions;
     * @param role - current user role
     */
    public Petition(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        petCreate = Boolean.valueOf(prop.getProperty(PET_CREATE));
        petEdit = Boolean.valueOf(prop.getProperty(PET_EDIT));
        petView = Boolean.valueOf(prop.getProperty(PET_VIEW));
        petManageAffectedEmployees = Boolean.valueOf(prop.getProperty(PET_MANAGE_AFFECTED_EMPLOYEES));
    }
    /**
     * Generates random data
     */
    public final void generateRandomData() {
        decisionDate = CommonFunctions.getDaysAgoDate(DATE_DECISION + getRandomInteger(Constants.CONTACT_LENGTH));
        fileDate = decisionDate;
        impactDate = CommonFunctions.getDaysAgoDate(DATE_IMPACT + getRandomInteger(Constants.CONTACT_LENGTH));
        department = getRandomLiteralCode(Constants.CONTACT_LENGTH);
        typeOfWork = getRandomLiteralCode(Constants.CONTACT_LENGTH);
        outOfState = false;
    }

    /**
     * Gets random petition number
     * @param type - type of petition.
     * @return new petition number.
     */
    private String getRandomPetitionNumber(PetitionType type) {
        int initialNumber;
        int maximumRandom;
        switch (type) {
            case ATAA_LOW:
                initialNumber = PetitionValues.ATAA_LOW_MIN.getPetitionValue();
                maximumRandom = PetitionValues.ATAA_LOW_RANDOM.getPetitionValue();
                break;
            case ATAA_HIGH:
                initialNumber = PetitionValues.ATAA_HIGH_MIN.getPetitionValue();
                maximumRandom = PetitionValues.ATAA_HIGH_RANDOM.getPetitionValue();
                break;
            case RTAA:
                initialNumber = PetitionValues.RTAA_MIN.getPetitionValue();
                maximumRandom = PetitionValues.RTAA_RANDOM.getPetitionValue();
                break;
            case TAA_2014:
                initialNumber = PetitionValues.TAA_2014_MIN.getPetitionValue();
                maximumRandom = PetitionValues.TAA_2014_RANDOM.getPetitionValue();
                break;
            default:
                initialNumber = PetitionValues.ATAA_HIGH_MIN.getPetitionValue();
                maximumRandom = PetitionValues.ATAA_HIGH_RANDOM.getPetitionValue();
        }

        int resultNumber = initialNumber + getRandomInteger(maximumRandom);
        StringBuilder result = new StringBuilder(String.valueOf(resultNumber));
        while (result.length() < PetitionValues.PETITION_NUMBER.getPetitionValue()) {
            result.append(getRandomLiteralCode(1));
        }

        return result.toString();
    }

    /**
     * Mark petition as out of state
     */
    public void markAsOutOfState() {
        outOfState = true;
        employer.setOutOfStateEmployer(true);
    }

    //User permissions.

    public Boolean getPetitionCreate() {
        return petCreate;
    }

    public Boolean getPetitionEdit() {
        return petEdit;
    }

    public Boolean getPetitionManageEmployee() {
        return petManageAffectedEmployees;
    }

    public Employer getEmployer() {
        return employer;
    }

    public String getNumber() {
        return number;
    }

    public PetitionStatus getStatus() {
        return status;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public String getCertificationDate() {
        return decisionDate;
    }

    public String getImpactDate() {
        return impactDate;
    }

    public String getDepartment() {
        return department;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public String getFileDate() {
        return fileDate;
    }

    public boolean isOutOfState() {
        return outOfState;
    }

    public void setFileDate(String dateFile) {
        this.fileDate = dateFile;
    }

    public void setDecisionDate(String dateDecision) {
        this.decisionDate = dateDecision;
    }

    public void setImpactDate(String dateImpact) {
        this.impactDate = dateImpact;
    }

    public void setDepartment(String newDepartment) {
        this.department = newDepartment;
    }

    public void setNewTypeWork(String newTypeWork) {
        this.typeOfWork = newTypeWork;
    }

    public PetitionType getType() {
        return type;
    }
}
