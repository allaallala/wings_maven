package edu.msstate.nsparc.wings.integration.models.participant;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PreviousJobType;
import framework.CommonFunctions;

/**
 * Previous job entity
 */
public class PreviousJob {

    private String employer;
    private String lineOne;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    private String jobTitle;
    private String osocTitle;
    private String startDate;
    private Boolean ended;
    private String endDate;
    private String reasonForLeaving;
    private String hoursPerWeek;
    private String wages;
    private static final Integer START_SEPARATION_DATE = 60;
    private static final Integer END_SEPARATION_DATE = 50;
    private static final Integer START_REEMPLOYMENT_DATE = 40;
    private static final Integer LINE_ONE = 8;

    /**
     * Generates data for previous job with specified job type
     * @param type = job type
     * @param days - specified days for reemployment or separation.
     */
    public PreviousJob(PreviousJobType type, Integer days) {
        generateRandomData();
        switch (type) {
            case SEPARATION:
                generateSeparationData(days);
                break;
            case REEMPLOYMENT:
                generateReemploymentData(days);
                break;
            case NORMAL:
                break;
            default:
                break;
        }
    }

    /**
     * Generate separate date for previous job
     * @param separationDays - date start separation
     */
    private void generateSeparationData(Integer separationDays) {
        osocTitle = "Cleaner";
        startDate = CommonFunctions.getDaysAgoDate(START_SEPARATION_DATE);
        if (separationDays != null) {
            startDate = CommonFunctions.getDaysAgoDate(separationDays);
        }
        ended = true;
        endDate = CommonFunctions.getDaysAgoDate(END_SEPARATION_DATE);
        reasonForLeaving = "Layoff";
        hoursPerWeek = "40";
        wages = "40";
    }

    /**
     * Generate reemployment data
     * @param reemploymentDays - days before current date, when reemployment starts.
     */
    private void generateReemploymentData(Integer reemploymentDays) {
        osocTitle = "Driver";
        startDate = CommonFunctions.getDaysAgoDate(START_REEMPLOYMENT_DATE);
        if (reemploymentDays != null) {
            startDate = CommonFunctions.getDaysAgoDate(reemploymentDays);
        }
        ended = false;
        hoursPerWeek = "30";
        wages = "30";
    }

    /**
     * Generate random data for previous job
     */
    private void generateRandomData() {
        employer = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        lineOne = CommonFunctions.getRandomLiteralCode(LINE_ONE);
        city = CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH);
        state = "Mississippi";
        zipCode = CommonFunctions.getRandomIntegerNumber(Constants.EMAIL_LENGTH);
        county = "Adams";
        jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    }

    public String getEmployer() {
        return employer;
    }

    public String getCity() {
        return city;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getOsocTitle() {
        return osocTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public Boolean isEnded() {
        return ended;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public String getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(String newHoursPerWeek) {
        this.hoursPerWeek = newHoursPerWeek;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String newWages) {
        this.wages = newWages;
    }

    public void setStartDate(String dateStart) {
        this.startDate = dateStart;
    }

    public void setEndDate(String dateEnd) {
        this.endDate = dateEnd;
    }

    public void setEnded(boolean newEnded) {
        this.ended = newEnded;
    }

    public void setReasonForLeaving(String newReasonForLeaving) {
        this.reasonForLeaving = newReasonForLeaving;
    }
}
