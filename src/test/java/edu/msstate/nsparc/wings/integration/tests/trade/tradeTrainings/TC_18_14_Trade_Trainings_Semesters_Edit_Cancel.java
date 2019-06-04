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
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Created by a.vnuchko on 17.08.2015.
 * Add some semester to the trade training, edit data of the semester (do not save!). Click [Cancel] button and check, that data of the semester is not changed.
 */

@TestCase(id = "WINGS-10937")
public class TC_18_14_Trade_Trainings_Semesters_Edit_Cancel extends BaseWingsTest {

    public void main(){
        String startDate = CommonFunctions.getFutureDate(2);
        String endDate = CommonFunctions.getFutureDate(Constants.SEMESTER_DATE);
        String hours = "18";

        info("Preconditions: generate trade training data");
        TradeTraining training = new TradeTraining();

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

        logStep("Edit any parameter and click the [Cancel] button");
        semesterPage.semesterActions(startDate, endDate, hours, TradeTrainingSemesterForm.SEMESTER_ACT.CANCEL);

        logResult("The Trade Training Creation Screen is shown, the changes are not saved");
        creationPage.validateSemesterAdded(training.getTradeTrainingSemesters().get(0).getBeginDate(),training.getTradeTrainingSemesters().get(0).getEndDate(),
                training.getTradeTrainingSemesters().get(0).getCreditHours(), Constants.RANDOM_ONE);
    }
}
