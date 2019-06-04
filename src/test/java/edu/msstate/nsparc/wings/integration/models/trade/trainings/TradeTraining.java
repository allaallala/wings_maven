package edu.msstate.nsparc.wings.integration.models.trade.trainings;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Trade Training entity
 */
public class TradeTraining {
    private TradeEnrollment tradeEnrollment;
    private TrainingProvider trainingProvider;
    private String type;
    private String applicationDate;
    private String firstDayOfTraining;
    private String completionDate;
    private String lengthOfProgram;
    private boolean distanceLearning;
    private boolean pellGrantRecipient;
    private List<TradeTrainingSemester> tradeTrainingSemesters;
    private String result;
    private String lastDayOfTraining;
    private boolean resultPassed;
    private boolean leadToAssociateDegree;

    //User permissions.
    private Boolean tteCreate;
    private Boolean tteEdit;
    private Boolean tteView;
    private Boolean tteAddSemester;
    private Boolean tteEditSemester;
    private static final String TTE_CREATE = "tteCreate";
    private static final String TTE_EDIT = "tteEdit";
    private static final String TTE_VIEW = "tteView";
    private static final String TTE_ADD_SEMESTER = "tteAddSemester";
    private static final String TTE_EDIT_SEMESTER = "tteEditSemester";

    /**
     * Constructor
     */
    public TradeTraining() {
        tradeEnrollment = new TradeEnrollment(PetitionType.RTAA);
        trainingProvider = new TrainingProvider();
        trainingProvider.convertToTrade();
        generateRandomData();
    }

    /**
     * Constructor with specified user role
     * @param role - current role.
     */
    public TradeTraining(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        tteCreate = Boolean.valueOf(prop.getProperty(TTE_CREATE));
        tteEdit = Boolean.valueOf(prop.getProperty(TTE_EDIT));
        tteView = Boolean.valueOf(prop.getProperty(TTE_VIEW));
        tteAddSemester  = Boolean.valueOf(prop.getProperty(TTE_ADD_SEMESTER));
        tteEditSemester = Boolean.valueOf(prop.getProperty(TTE_EDIT_SEMESTER));
        tradeEnrollment = new TradeEnrollment(role);
        trainingProvider = new TrainingProvider();
        trainingProvider.convertToTrade();
        generateRandomData();
    }

    /**
     * Generate random date for trade training
     */
    private void generateRandomData() {
        type = "Apprenticeship";
        applicationDate = CommonFunctions.getCurrentDate();
        firstDayOfTraining = CommonFunctions.getCurrentDate();
        lastDayOfTraining = CommonFunctions.getFutureDate(Constants.DAYS_MONTH);
        completionDate = CommonFunctions.getFutureDate(Constants.DAYS_YEAR);
        lengthOfProgram = "4";
        distanceLearning = false;
        pellGrantRecipient = false;
        tradeTrainingSemesters = new ArrayList<>();
        tradeTrainingSemesters.add(new TradeTrainingSemester(firstDayOfTraining, lastDayOfTraining, "20", false));
        result = Constants.IN_PROGRESS;
        leadToAssociateDegree = false;
    }

    /**
     * Change result of trade training
     * @param newResult new result of trade training
     */
    public void changeResult(String newResult) {
        result = newResult;
        if (Constants.COMPLETED.equalsIgnoreCase(result)) {
            lastDayOfTraining = CommonFunctions.getCurrentDate();
            resultPassed = true;
        }
    }

    //User permissions block.

    public Boolean getTradeTrainingCreate() {
        return tteCreate;
    }

    public Boolean getTradeTrainingView() {
        return tteView;
    }

    public Boolean getTradeTrainingEdit() {
        return tteEdit;
    }

    public Boolean getTrainingAddSemester() {
        return tteAddSemester;
    }

    public Boolean getTrainingEditSemester() {
        return tteEditSemester;
    }

    public TradeEnrollment getTradeEnrollment() {
        return tradeEnrollment;
    }

    public TrainingProvider getTrainingProvider() {
        return trainingProvider;
    }

    public String getType() {
        return type;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getFirstDayOfTraining() {
        return firstDayOfTraining;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public String getLengthOfProgram() {
        return lengthOfProgram;
    }

    public boolean isDistanceLearning() {
        return distanceLearning;
    }

    public boolean isPellGrantRecipient() {
        return pellGrantRecipient;
    }

    public List<TradeTrainingSemester> getTradeTrainingSemesters() {
        return tradeTrainingSemesters;
    }

    public String getResult() {
        return result;
    }

    public String getLastDayOfTraining() {
        return lastDayOfTraining;
    }

    public boolean isResultPassed() {
        return resultPassed;
    }

    public boolean isLeadToAssociateDegree() {
        return leadToAssociateDegree;
    }

    public void setCompletionDate(String newCompletionDate) {
        this.completionDate = newCompletionDate;
    }

    /**
     * Add semester to a list.
     * @param firstDayTraining - first day of training
     * @param lastDayTraining - last day of training
     * @param creditHours - credit hours.
     */
    public void addSemester(String firstDayTraining, String lastDayTraining, String creditHours) {
        tradeTrainingSemesters.add(new TradeTrainingSemester(firstDayTraining, lastDayTraining, creditHours, false));
    }

    /**
     * Remove last semester and add new value for it.
     * @param startDate - semester start date
     * @param endDate - semester end date
     * @param hours - semester hours
     */
    public void setSemesters(String startDate, String endDate, String hours) {
        tradeTrainingSemesters.remove(tradeTrainingSemesters.size() - 1);
        tradeTrainingSemesters.add(new TradeTrainingSemester(startDate, endDate, hours, false));
    }
}
