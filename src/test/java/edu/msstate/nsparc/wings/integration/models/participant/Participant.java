package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.enums.ParticipantType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.Address;
import framework.AccountUtils;
import framework.CommonFunctions;

/**
* This class represents Participant entity
*/
public class Participant {
    private String ssn;
    private String citizenship;
    private String firstName;
    private String lastName;
    private Address address;
    private String grade;
    private String participantAccount;
    private String employmentPreferences;
    private String veteranStatus;
    private String disabledVeteran;
    private String serviceCenterName;
    private String workforceArea;
    private String primaryPhone;
    private String email;
    private String degree;
    private String certification;
    private String employerName;
    private String jobTitle;
    private String osoc;
    private String gpa;
    private String id;
    private ParticipantBioInfo participantBioInfo;
    private ParticipantType type;
    private EmploymentRecord employmentRecord;
    private ParticipantPermissions participantPermissions;
    private static final Integer TRUNCATED = 5;
    private static final Integer NAME_LENGTH = 15;

    /**
     * Participant with specified account
     * @param account - account
     */
    public Participant(String account) {
        this(account, false);
    }

    /**
     * Constructor with specifed user role
     * @param role - current user role
     */
    public Participant(Roles role) {
        this(ParticipantType.ADULT);
        this.participantPermissions = new ParticipantPermissions(role);
    }

    /**
     * Participant with specified account and youth type (if required)
     * @param account - account of the participant
     * @param youth -  true/false
     */
    public Participant(String account, boolean youth) {
        participantAccount = account;
        ssn = CommonFunctions.getRandomSSN();
        if (youth) {
            type = ParticipantType.YOUTH;
        } else {
            type = ParticipantType.ADULT;
        }
        participantBioInfo = new ParticipantBioInfo(type);
        firstName = CommonFunctions.getRandomLiteralCode(NAME_LENGTH);
        lastName = CommonFunctions.getRandomLiteralCode(NAME_LENGTH);
        address = new Address();
        citizenship = "United States Citizen";
        grade = "Bachelor's Diploma or Degree";
        serviceCenterName = "Automation Test";
        workforceArea = "Mississippi Partnership";
        primaryPhone = "(123) 111-1234";
        email = "wingsqa@gmail.com";
        employmentPreferences = "";
        veteranStatus = "Eligible Veteran";
        disabledVeteran = "Yes";
    }

    /**
     * Participant data with desired type
     * @param desiredType desired type of participant
     */
    public Participant(ParticipantType desiredType) {
        veteranStatus = "Eligible Veteran";
        disabledVeteran = "Yes";
        participantAccount = AccountUtils.getParticipantAccount();
        ssn = CommonFunctions.getRandomSSN();
        type = desiredType;
        firstName = CommonFunctions.getRandomLiteralCode(NAME_LENGTH);
        lastName = CommonFunctions.getRandomLiteralCode(NAME_LENGTH);
        address = new Address();
        citizenship = "United States Citizen";
        grade = "Bachelor's Diploma or Degree";
        serviceCenterName = "Automation Test";
        workforceArea = "Mississippi Partnership";
        primaryPhone = "(123) 111-1234";
        email = "wingsqa@gmail.com";
        degree = "High School Diploma";
        certification = "Oracle";
        employerName = "Shaman friend";
        jobTitle = "enhancement";
        osoc = "Cooks, Restaurant";
        participantBioInfo = new ParticipantBioInfo(type);
        gpa = "";
    }

    /**
     * Default participant
     */
    public Participant() {
        this(ParticipantType.ADULT);
    }

    public ParticipantBioInfo getParticipantBioInfo() {
        return participantBioInfo;
    }

    public EmploymentRecord getEmploymentRecord() {
        return employmentRecord;
    }

    public void setEmploymentRecord(EmploymentRecord employmentRecord) {
        this.employmentRecord = employmentRecord;
    }

    public void setFirstName(String firstN) {
        this.firstName = firstN;
    }

    public void setLastName(String lastN) {
        this.lastName = lastN;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNameForSearchPages() {
        return String.format("%1$s %2$s (%3$s)", firstName, lastName, ssn.substring(TRUNCATED));
    }

    public String getFullName() {
        return String.format("%1$s %2$s", firstName, lastName);
    }

    public String getLastName() {
        return lastName;
    }

    public String getTruncatedSsn() {
        return ssn.substring(TRUNCATED);
    }

    public String getHiddenSsn() {
        return String.format("XXXXX%1$s", getTruncatedSsn());
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSsn(String socialSerialNumber) {
        this.ssn = socialSerialNumber;
    }

    public void setCitizenship(String newCitizenship) {
        this.citizenship = newCitizenship;
    }

    public String getInitials() {
        return firstName.substring(0, 1) + lastName.substring(0, 1);
    }

    public ParticipantType getType() {
        return type;
    }

    public String getSsn() {
        return ssn;
    }

    public Address getAddress() {
        return address;
    }

    public String getLocationHome() {
        return String.format("%1$s, %2$s %3$s", address.getCity(), address.getState(), address.getZipCode());
    }

    public void setNewAddress(Address newAddress) {
        this.address = newAddress;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getGrade() {
        return grade;
    }

    public String getParticipantAccount() {
        return participantAccount;
    }

    public void setParticipantAccount(String newParticipantAccount) {
        this.participantAccount = newParticipantAccount;
    }

    public String getVeteranStatus() {
        return veteranStatus;
    }

    public String getDisabledVeteran() {
        return disabledVeteran;
    }

    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public String getWorkforceArea() {
        return workforceArea;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String newPrimaryPhone) {
        this.primaryPhone = newPrimaryPhone;
    }

    public String getDegree() {
        return degree;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String newCertificate) {
        this.certification = newCertificate;
    }

    public void setEmployerName(String newEmployerName) {
        this.employerName = newEmployerName;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setJobTitle(String newJobTitle) {
        this.jobTitle = newJobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getOsoc() {
        return osoc;
    }

    public void setOsoc(String osoc) {
        this.osoc = osoc;
    }

    public ParticipantBioInfo getParticipantNations() {
        return participantBioInfo;
    }

    public void setGpa(String newGpa) {
        this.gpa = newGpa;
    }

    public String getGpa() {
        return gpa;
    }

    public ParticipantPermissions getParticipantPermissions() {
        return participantPermissions;
    }
}
