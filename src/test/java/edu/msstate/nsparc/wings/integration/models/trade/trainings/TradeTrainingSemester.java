package edu.msstate.nsparc.wings.integration.models.trade.trainings;

public class TradeTrainingSemester {
    private String beginDate;
    private String endDate;
    private String creditHours;
    private boolean partTimeTraining;

    /**
     * Constructor data for semesters
     * @param stDate - start semester's date
     * @param enDate - end semester's date
     * @param hours - hours
     * @param partTime - if semester is part time
     */
    public TradeTrainingSemester(String stDate, String enDate, String hours, boolean partTime) {
        beginDate = stDate;
        endDate = enDate;
        creditHours = hours;
        partTimeTraining = partTime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public boolean isPartTimeTraining() {
        return partTimeTraining;
    }
}
