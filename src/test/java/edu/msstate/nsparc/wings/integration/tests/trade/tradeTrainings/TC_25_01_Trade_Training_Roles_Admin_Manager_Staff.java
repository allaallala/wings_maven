package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check trade training enrollment functionality using different roles - administrator, manager, staff.
 * Created by a.vnuchko on 09.03.2016.
 */
@TestCase(id = "WINGS-11078")
public class TC_25_01_Trade_Training_Roles_Admin_Manager_Staff extends BaseWingsTest {
    private TradeTraining trTraining;
    private String newCompletionDay = CommonFunctions.getFutureDate(Constants.SEMESTER_DATE);
    private String[] newSemesterData = {CommonFunctions.getFutureDate(35), CommonFunctions.getFutureDate(36), "24"};

    public void main(){
        //Role administrator
        User user = new User(Roles.ADMIN);
        commonTradeTrainingSteps(user);

        //Role manager
        user.setNewUser(Roles.MANAGER);
        commonTradeTrainingSteps(user);

        //Role staff
        user.setNewUser(Roles.STAFF);
        commonTradeTrainingSteps(user);
    }

    /**
     * Common steps for checking user permissions. Module - trade training.
     * @param user - current user
     */
    protected void commonTradeTrainingSteps(User user){
        //(!) Create new trade training enrollment, if possible
        if(user.getTradeTraining().getTradeTrainingCreate()){
            logStep("Create new trade training enrollment");
            AccountUtils.init();
            trTraining = new TradeTraining();
            TrainingSteps.createTradeTraining(trTraining, user.getRole(), Roles.ADMIN);
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check view, edit and semester functionality
     * @param user - current user
     */
    private void checkOtherFunctionality(User user){
        String editStartDate = CommonFunctions.getFutureDate(33);
        String editEndDate = CommonFunctions.getFutureDate(34);
        String editHours = "22";
        divideMessage("Check other functionality");
        logStep("Check View functionality");
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can view search results.
        if(user.getTradeTraining().getTradeTrainingView()){
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING);

            //(!) If user can create Everify - > he should confirm pop-up window.
            if(user.getTradeTraining().getTradeTrainingCreate()){
                BaseWingsSteps.popClick(Popup.Search);
            }

            TradeTrainingSearchForm searchForm = new TradeTrainingSearchForm();
            searchForm.performSearchAndOpen(trTraining);

            TradeTrainingDetailsForm detailsPage = new TradeTrainingDetailsForm();
            detailsPage.validateInformation(trTraining);
            detailsPage.checkTradeTrainingButtonsPresent(user);

            //(!)Check edit funtionality
            if(user.getTradeTraining().getTradeTrainingEdit()){
                logStep("Check edit functionality");
                trTraining.setCompletionDate(newCompletionDay); //set new value for date anticipated completion
                detailsPage.editSomeParam(trTraining);
                detailsPage.validateInformation(trTraining);
            }

            //(!) Check add semester functionality
            if(user.getTradeTraining().getTrainingAddSemester()){
                logStep("Add training semester");
                detailsPage.clickButton(Buttons.Edit);
                trTraining.addSemester(newSemesterData[0], newSemesterData[1], newSemesterData[2]);
                detailsPage.inputNewSemester(trTraining, 1);
            }

            //(!) Check edit semester functionality
            if(user.getTradeTraining().getTrainingEditSemester()){
                logStep("Edit training semester");
                detailsPage.clickButton(Buttons.Edit);
                trTraining.setSemesters(editStartDate, editEndDate, editHours);  //Removes last semester and add new String[].
                detailsPage.editSemester(trTraining, editStartDate, editEndDate, editHours, trTraining.getTradeTrainingSemesters().size()); //Select required semester and edit it.
            }
        }else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}
