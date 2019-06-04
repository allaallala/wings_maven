package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterCreateForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.administrative.ServiceCenters;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check Service Centers functionality for different user roles (external and internal).
 * Created by a.vnuchko on 07.07.2016.
 */

@TestCase(id = "WINGS-11095")
public class TC_26_05_Service_Centers_Roles extends BaseWingsTest {
    ServiceCenters serviceCenter;
    ServiceCenters serviceCenterAnother;

    public void main(){

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonServiceCentersSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonServiceCentersSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonServiceCentersSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonServiceCentersSteps(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonServiceCentersSteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonServiceCentersSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonServiceCentersSteps(user);

        //Role - everify administaror
        user.setNewUser(Roles.EVERIFY);
        commonServiceCentersSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);  //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9167
        commonServiceCentersSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);  //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9167
        commonServiceCentersSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonServiceCentersSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonServiceCentersSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonServiceCentersSteps(user);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        commonServiceCentersSteps(user);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        commonServiceCentersSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonServiceCentersSteps(user);
    }

    /**
     * Describes common steps for checking user permissions in the Service Centers module
     * @param user - current user
     */
    private void commonServiceCentersSteps(User user){

        //(!) Create new service center, if possible
        if(user.getServiceCenters().getScCreate()){
            logStep("Create new service center");
            serviceCenter = new ServiceCenters(user.getRole());
            BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.A_CENTERS, Popup.Create);
            createServiceCenter(serviceCenter);
            CenterDetailsForm detailsPage = new CenterDetailsForm();
            detailsPage.validateData(serviceCenter);

            //(!) Create another service center if possible
            if(user.getServiceCenters().getScViewCreateAnotherButton()){
                logStep("Create another service center");
                serviceCenterAnother = new ServiceCenters(user.getRole());
                detailsPage.clickCreateAnother();
                createServiceCenter(serviceCenterAnother);
                detailsPage.validateData(serviceCenterAnother);
            }
            BaseNavigationSteps.logout();
        }

        checkOtherFunctionality(user);
    }

    /**
     * Create new service center
     * @param sc - service center.
     */
    private void createServiceCenter(ServiceCenters sc){
        CenterCreateForm creationPage = new CenterCreateForm();
        creationPage.fillCreationForm(sc);
    }

    /**
     * Check other service center functionality (View, Edit, Audit)
     * @param user - current user
     */
    private void checkOtherFunctionality(User user){
        BaseWingsSteps.logInAs(user.getRole());

        //(!) Check, if user can view service centers.
        if(user.getServiceCenters().getScView()){
            logStep("Check view functionality");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_CENTERS);
            if(user.getServiceCenters().getScCreate()){
                BaseWingsSteps.popClick(Popup.Search);
            }

            CenterSearchForm searchPage = new CenterSearchForm();
            searchPage.performSearch(serviceCenter.getCenterName());
            searchPage.openFirstSearchResult();

            CenterDetailsForm detailsPage = new CenterDetailsForm();

            logStep("Check buttons [Edit], [Audit]");
            detailsPage.checkButtonsPresent(user.getServiceCenters().getScViewEditJobCenterButton(), user.getServiceCenters().getScViewAuditButton());

            logStep("Check buttons [Disable], [Enable]");
            detailsPage.checkEnableDisable(user);

            if(user.getServiceCenters().getScEdit()){
                logStep("Check edit functionality");
                detailsPage.clickButton(Buttons.Edit);
                CenterEditForm editPage = new CenterEditForm();
                editPage.editSomeParameters(serviceCenter);
            }
        }else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.A_CENTERS, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
