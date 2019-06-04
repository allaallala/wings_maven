package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationAddExpenseForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check Relocation functionality using different roles - administrator, area director, manager
 * Created by a.vnuchko on 04.03.2016.
 */

@TestCase(id = "WINGS-11076")
public class TC_24_16_Relocation_Roles_Admin_AD_Manager extends BaseWingsTest {
    private Relocation reloc;
    private RelocationExpense relocExp;

    public void main(){

        //Role - area director
        User user = new User(Roles.AREADIRECTOR);
        commonRelocationSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonRelocationSteps(user);

        //Role - administrator
        user.setNewUser(Roles.ADMIN);
        commonRelocationSteps(user);
    }

    /**
     * Common steps for checking user permissions
     * @param user - current user
     */
    protected void commonRelocationSteps(User user){
        //(!) Create new Relocation.
        if(user.getRelocation().getRelocationCreate()){
            AccountUtils.init(); //reset data to create new objects
            reloc = new Relocation(Constants.TRUE);
            relocExp = new RelocationExpense(Constants.TRUE);
            RelocationCreationSteps.createRelocationWithoutLoggingOut(reloc, user.getRole());

            if(user.getRelocation().getRelocationEdit()){
                RelocationCreationSteps.editAndSaveRelocationStatusWithoutLoggingOut(reloc);
            }
            BaseNavigationSteps.logout();
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check other functionality: edit, view, audit.
     * @param user - current user.
     */
    private void checkOtherFunctionality(User user){
        divideMessage("Check other functionality");
        BaseWingsSteps.logInAs(user.getRole());

        if(user.getRelocation().getRelocationView()){
            logStep("Check View functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);

            //(!) If user can create Relocation - > he should confirm pop-up window.
            if(user.getRelocation().getRelocationCreate()){
                BaseWingsSteps.popClick(Popup.Search);
            }

            //(!) If user can view search results.
            RelocationSearchForm searchPage = new RelocationSearchForm();
            searchPage.performSearch(reloc);

            searchPage.openFirstSearchResult();
            RelocationDetailsForm detailsPage = new RelocationDetailsForm();
            detailsPage.validateInformation(reloc);

            //(!) Check buttons on the detail Relocation page.
            logStep("Check [Edit], [Add expense], [Edit expense] buttons on the page");
            detailsPage.checkButtonsPresent(user);

            //(!) Check that possible to add expense
            if(user.getRelocation().getRelocationAddExpense()){
                logStep("Add relocation expense");
                detailsPage.addExpence();
                RelocationAddExpenseForm relocationAddExpenseForm = new RelocationAddExpenseForm();
                relocationAddExpenseForm.fillExpenseFormAndSave(relocExp);
                detailsPage.validateLastAddedExpenseData(relocExp);
            }

            //(!) Check that is possible to edit expense
            if(user.getRelocation().getRelocationEditExpense()){
                logStep("Edit relocation expense");
                relocExp = new RelocationExpense(Constants.TRUE);
                detailsPage.editFirstAddedExpense(relocExp);
                detailsPage.validateLastAddedExpenseData(relocExp);
            }

            //(!) Check, that is possible to edit expense
            if(user.getRelocation().getRelocationEdit()){
                detailsPage.editRelocation();
                RelocationEditForm editPage = new RelocationEditForm();
                reloc.setRelocationDate(CommonFunctions.getYesterdayDate()); //change data to edit
                reloc.setApplicationDate(CommonFunctions.getYesterdayDate());
                editPage.editRelocationDate(reloc);
                editPage.editApplicationDate(reloc);
                editPage.clickButton(Buttons.Save);
                detailsPage.validateInformation(reloc);
            }

        }else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}
