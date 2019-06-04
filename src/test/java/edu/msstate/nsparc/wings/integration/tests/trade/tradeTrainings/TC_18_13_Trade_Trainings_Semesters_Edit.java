package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSemesterForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Created by a.vnuchko on 17.08.2015.
 * Add a semester to the trade trainings, edit some data and save. Check it.
 */

@TestCase(id = "WINGS-10936")
public class TC_18_13_Trade_Trainings_Semesters_Edit extends BaseWingsTest {

    public void main() {
        String startDate = CommonFunctions.getFutureDate(2);
        String endDate = CommonFunctions.getFutureDate(Constants.SEMESTER_DATE);
        String hours = "18";

        info("Preconditions: create new trade trainings");
        TradeTraining training = new TradeTraining();
        TradeEnrollmentSteps.createTradeEnrollment(training.getTradeEnrollment(), Roles.ADMIN);
        TrainingSteps.createTraining(training.getTrainingProvider(), Roles.ADMIN);

        logStep("Login to the system and open creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);

        logStep("Add a semester");
        TradeTrainingCreateForm creationPage = new TradeTrainingCreateForm();
        creationPage.addSemester();
        TradeTrainingSemesterForm semesterPage = new TradeTrainingSemesterForm();
        semesterPage.addSemester(training.getTradeTrainingSemesters().get(0));

        logStep("Select a Semester and click [Edit]");
        creationPage.chooseFirstSemester();
        creationPage.clickButton(Buttons.Edit);

        logStep("Edit any parameter and click [Save Changes] button");
        semesterPage.semesterActions(startDate, endDate, hours, TradeTrainingSemesterForm.SEMESTER_ACT.EDIT);

        logResult("The Trade Training Creation Screen is show. The changes are saved");
        creationPage.validateSemesterAdded(startDate, endDate, hours, Constants.RANDOM_ONE);
    }
}
