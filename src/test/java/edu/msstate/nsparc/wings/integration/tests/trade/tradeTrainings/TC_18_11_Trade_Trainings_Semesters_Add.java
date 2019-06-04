package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSemesterForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * In this case we add new semester while creating trade training and check, that this semester is displayed correctly
 * Created by a.vnuchko on 13.08.2015.
 */

@TestCase(id = "WINGS-10934")
public class TC_18_11_Trade_Trainings_Semesters_Add extends BaseWingsTest {
    private static final Integer DAYS_START = 32;
    private static final Integer DAYS_END = 61;
    private String startDate = CommonFunctions.getFutureDate(DAYS_START);
    private String endDate = CommonFunctions.getFutureDate(DAYS_END);
    private String hours = "8";
    private Integer recordNumber = 2;

    public void main() {
        info("Preconditions: create new trade trainings");
        TradeTraining training = new TradeTraining();
        createTrainingWithSemester(training, Roles.STAFF);
    }

    /**
     * Create training with semester
     * @param trTraining - trade training
     * @param role - user role
     */
    protected void createTrainingWithSemester(TradeTraining trTraining, Roles role) {
        TrainingSteps.createTraining(trTraining.getTrainingProvider(), Roles.TRADEDIRECTOR);
        TradeEnrollmentSteps.createTradeEnrollment(trTraining.getTradeEnrollment(), Roles.ADMIN);


        logStep("Login to the system and open creation form");
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);

        logStep("Enter data for all required fields");
        TradeTrainingCreateForm creatingPage = new TradeTrainingCreateForm();
        creatingPage.fillOutCreationForm(trTraining);

        logStep("Click [Add] button in the Semesters area");
        creatingPage.addSemester();

        logStep("Fill out the required fields with valid data and click [Add]");
        TradeTrainingSemesterForm semesterPage = new TradeTrainingSemesterForm();
        semesterPage.semesterActions(startDate, endDate, hours, TradeTrainingSemesterForm.SEMESTER_ACT.ADD);

        logResult("The Trade Training Creation Screen is shown. A new Semester was added and contains the same data you have just entered");
        creatingPage.validateSemesterAdded(startDate, endDate, hours, recordNumber);
        BaseNavigationSteps.logout();
    }
}
