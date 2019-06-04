package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
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
 * Open [Add Semester Form], click [Cancel] button. Check that new semester wasn't added.
 * Created by a.vnuchko on 14.08.2015.
 */

@TestCase(id = "WINGS-10935")
public class TC_18_12_Trade_Trainings_Semesters_Add_Cancel extends BaseWingsTest {

    public void main(){
        String startDate = CommonFunctions.getFutureDate(2);

        info("Preconditions: create new trade trainings");
        TradeTraining training = new TradeTraining();
        TrainingSteps.createTraining(training.getTrainingProvider(), Roles.TRADEDIRECTOR);
        TradeEnrollmentSteps.createTradeEnrollment(training.getTradeEnrollment(), Roles.ADMIN);

        logStep("Login to the system and open creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Popup.Create);

        logStep("Enter data for all required fields");
        TradeTrainingCreateForm creatingPage = new TradeTrainingCreateForm();
        creatingPage.fillOutCreationForm(training);

        logStep("Click [Add] button in the Semesters area");
        creatingPage.addSemester();

        logStep("Fill out any data");
        TradeTrainingSemesterForm semesterPage = new TradeTrainingSemesterForm();
        semesterPage.inputStartDate(startDate);

        logStep("Click the [Cancel] button");
        semesterPage.clickButton(Buttons.Cancel);

        logResult("The Trade Training Creation Screen is shown, the changes are not saved");
        creatingPage.checkSecondSemesterNotPresent();
    }
}
