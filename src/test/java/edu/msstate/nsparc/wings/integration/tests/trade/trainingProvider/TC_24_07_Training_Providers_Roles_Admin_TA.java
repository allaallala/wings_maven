package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingProvider.TrainingProviderSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the training provider for administrator and trade administrator.
 * Created by a.vnuchko on 23.02.2016.
 */

@TestCase(id = "WINGS-11067")
public class TC_24_07_Training_Providers_Roles_Admin_TA extends BaseWingsTest {
    protected TrainingProvider train;

    public void main(){

        //Role - administrator
        User user = new User(Roles.ADMIN);
        divideMessage("Check user permissions for the Training Provider module, user role - "+user.getRoleString());
        commonTrainingProviderSteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        divideMessage("Check user permissions for the Training Provider module, user role - "+user.getRoleString());
        commonTrainingProviderSteps(user);
    }

    /**
     * Describes common steps for checking user permissions.
     * @param user - current user.
     */
    protected void commonTrainingProviderSteps(User user){
        if(user.getTrProvider().getProviderCreate()){
           logStep("Create new training provider");
           train = new TrainingProvider();
           train.convertToTrade();
           TrainingSteps.createTraining(train, user.getRole());
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check View, Audit, Edit functionality.
     * @param user - current user.
     */
    private void checkOtherFunctionality(User user){
        String[] newValues = {train.getName()+"A", "123456789", "V1234567890"};
        BaseWingsSteps.logInAs(user.getRole());

        //(!)If user can view search results.
        if(user.getTrProvider().getProviderView()){
            divideMessage("Check view, edit and audit functionality");
            logStep("Check View functionality");

            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS);

            //(!) If user can create training provider - > he should confirm pop-up window.
            if(user.getTrProvider().getProviderCreate()){
                BaseWingsSteps.popClick(Popup.Search);
            }

            TrainingProviderSearchForm searchPage = new TrainingProviderSearchForm();
            searchPage.performSearch(train);

            searchPage.openFirstSearchResult();
            TrainingProviderDetailsForm detailsPage = new TrainingProviderDetailsForm();

            logStep("Check buttons [Audit], [Edit] are present or not on the Details Training Provider form");
            detailsPage.checkButtonsPresent(user.getTrProvider().getProviderViewEdit(), user.getTrProvider().getProviderViewAudit());

            logStep("Check, if user can edit training provider");
            if(user.getTrProvider().getProviderEdit()){
                detailsPage.editProvider(train, newValues[0], newValues[1], newValues[2]);
            }
        }else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.A_TRAINING_PROVIDERS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}
