package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Check the functionality of Services using different user roles
 * Created by a.vnuchko on 11.03.2016.
 */

@TestCase(id = "WINGS-11082")
public class TC_25_05_Services_Roles extends BaseWingsTest {
    private String titleService;

    public void main(){

        //Role - administrator
        commonSteps(Roles.ADMIN);

        //Role - area director
        commonSteps(Roles.AREADIRECTOR);

        //Role - manager
        commonSteps(Roles.MANAGER);

        //Role - staff
        commonSteps(Roles.STAFF);

        //Role - intake staff
        commonSteps(Roles.INTAKESTAFF);

        //Role - trade administrator
        commonSteps(Roles.TRADEDIRECTOR);

        //Role - rapid response administrator
        commonSteps(Roles.RRADMIN);

        //Role - everify administrator
        commonSteps(Roles.EVERIFY);

        //Role - WIOA administrator
        commonSteps(Roles.WIOA);

        //Role - project code administrator
        commonSteps(Roles.PCADMIN);

        //Role - DVOP veteran
        commonSteps(Roles.DVOPVETERAN);

        //Role - LVER
        commonSteps(Roles.LVER);

        //Role - Executive
        commonSteps(Roles.EXECUTIVE);
    }

    /**
     * Common steps for checking user permissions.
     * @param role - user role
     */
    private void commonSteps(Roles role){
        User user = new User(role);

        //(!) Check, if user can create a service.
        if(user.getService().getServicesCreate()){
            titleService = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
            ServiceSteps.createService(role, titleService, Constants.FALSE, Constants.FALSE);
        }

        divideMessage("Check other functionality of the services.");
        checkOtherFunctionality(user);
    }
    /**
     * Open service participantSSDetails page
     * @param user - current user
     */
    private void checkOtherFunctionality(User user){
        logStep("Open services detail page");
        BaseWingsSteps.logInAs(user.getRole());

        divideMessage("Navigate to " + WingsTopMenu.WingsStaffMenuItem.A_SERVICES.name());
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_SERVICES);

        //(!) If user can create, hence he can search. Pop-up confirmation is required.
        if(user.getService().getServicesCreate()){
            BaseWingsSteps.popClick(Popup.Search);
        }

        ServiceSearchForm serviceSearchForm = new ServiceSearchForm();
        serviceSearchForm.performSearchAndOpen(titleService);
        ServiceDetailsForm detailsPage = new ServiceDetailsForm();

        logStep("Check buttons [Edit], [Audit] present or not on the page.");
        detailsPage.checkButtonsPresent(user.getService().getServicesEdit(), user.getService().getAuditButton());

        logStep("Check audit functionality");
        if(user.getService().getAuditButton()){
            detailsPage.audit();
        }

        logStep("Check edit functionality");
        if(user.getService().getServicesEdit()){
            titleService = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
            detailsPage.editService(titleService);
        }
        BaseNavigationSteps.logout();
    }
}
