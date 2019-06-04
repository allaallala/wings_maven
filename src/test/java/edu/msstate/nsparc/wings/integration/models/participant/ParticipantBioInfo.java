package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.enums.ParticipantType;
import framework.CommonFunctions;

public class ParticipantBioInfo {
    private String hispanic;
    private String asian;
    private String hawaiian;
    private String alaskan;
    private String black;
    private String white;
    private String gender;
    private String dateOfBirth;
    private String prefix;
    private String suffix;
    private String preferredName;
    private static final String MALE = "Male";
    private static final Integer YOUTH_AGE = 18;
    private static final Integer ADULT_AGE = 30;
    private static final Integer OLD_AGE = 51;

    public ParticipantBioInfo(ParticipantType type) {
        hispanic = "";
        asian = "";
        hawaiian = "";
        alaskan = "";
        black = "";
        white = "";
        prefix = "";
        suffix = "";
        preferredName = "";
        gender = MALE;
        switch (type) {
            case ATAA:
                dateOfBirth = CommonFunctions.getDaysAndYearsAgoDate(0, OLD_AGE);
                break;
            case YOUTH:
                dateOfBirth = CommonFunctions.getDaysAndYearsAgoDate(0, YOUTH_AGE);
                break;
            case ADULT:
                dateOfBirth = CommonFunctions.getDaysAndYearsAgoDate(0,ADULT_AGE);
                break;
            case DISLOCATED:
                dateOfBirth = CommonFunctions.getDaysAndYearsAgoDate(0,ADULT_AGE);
                break;
            default:
                break;
        }
    }

    /**
     * Set all nations for a participant
     */
    public void setAllNations() {
        this.hispanic = "Hispanic/Latino";
        this.asian = "Asian";
        this.hawaiian = "Native Hawaiian/Pacific Islander";
        this.alaskan = "Native American/Alaskan Native";
        this.black = "Black/African American";
        this.white = "White/Caucasian";
    }

    public String getHispanic() {
        return hispanic;
    }

    public String getAsian() {
        return asian;
    }

    public String getHawaiian() {
        return hawaiian;
    }

    public String getAlaskan() {
        return alaskan;
    }

    public String getBlack() {
        return black;
    }

    public String getWhite() {
        return white;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String birthDate) {
        this.dateOfBirth = birthDate;
    }

    public void setGender(String newGender) {
        this.gender = newGender;
    }

    public String getGender() {
        return gender;
    }

    public void setPrefix(String newPrefix) {
        this.prefix = newPrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setSuffix(String newSuffix) {
        this.suffix = newSuffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setPreferredName(String newPreferredName) {
        this.preferredName = newPreferredName;
    }

    public String getPreferredName() {
        return preferredName;
    }
}
