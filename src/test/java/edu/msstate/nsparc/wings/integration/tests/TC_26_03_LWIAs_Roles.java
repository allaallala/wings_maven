package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreaCreationForm;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreaDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreaEditForm;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreasSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.administrative.LWIA;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check LWIAs for different user roles (external and internal).
 * Created by a.vnuchko on 04.07.2016.
 */

@TestCase(id = "WINGS-11093")
public class TC_26_03_LWIAs_Roles extends BaseWingsTest {
    private LWIA lwia;

    public void main(){

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonLWIASteps(user);

        //Role - area director.
        user.setNewUser(Roles.AREADIRECTOR);
        commonLWIASteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonLWIASteps(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonLWIASteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonLWIASteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonLWIASteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonLWIASteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonLWIASteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonLWIASteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonLWIASteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonLWIASteps(user);

        //Role - dvop veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonLWIASteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonLWIASteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonLWIASteps(user);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonLWIASteps(user);

        //Role - WIOA Provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonLWIASteps(user);
    }
    private void commonLWIASteps(User user){

        //(!) Check, that user can create a new workforce area
        if(user.getLwia().getLwiaCreate()){
            lwia = new LWIA(user.getRole());
            logStep("Create new workforce area");
            BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.A_WORKFORCE_AREAS, Popup.Create);
            WorkforceAreaCreationForm creationPage = new WorkforceAreaCreationForm();
            creationPage.createWorkForce(lwia);
        }

        //(!) Check, that user can create a new workforce area (create another)
        if(user.getLwia().getLwiaCreate()){
            logStep("Create one more workforce area");
            lwia = new LWIA(user.getRole());
            WorkforceAreaDetailsForm detailsForm = new WorkforceAreaDetailsForm();
            detailsForm.clickAnother();
            WorkforceAreaCreationForm creationPage = new WorkforceAreaCreationForm();
            creationPage.createWorkForce(lwia);
            BaseNavigationSteps.logout();
        }
        BaseWingsSteps.logInAs(user.getRole());
        if(user.getLwia().getLwiaView()){
            logStep("Check view functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.A_WORKFORCE_AREAS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_WORKFORCE_AREAS);

            if(user.getLwia().getLwiaCreate()){
                BaseWingsSteps.popClick(Popup.Search);
            }

            WorkforceAreasSearchForm searchPage = new WorkforceAreasSearchForm();
            searchPage.performSimpleSearch(lwia.getDetails()[0]);
            WorkforceAreaDetailsForm detailsForm = new WorkforceAreaDetailsForm();
            detailsForm.validateWorkforceArea(lwia);

            logStep("Check buttons [Edit], [Audit]");
            detailsForm.checkButtonsPresent(user.getLwia().getLwiaViewEditButton(), user.getLwia().getLwiaViewAuditButton());

            if(user.getLwia().getLwiaEdit()){
                logStep("Edit workforce area");
                lwia = new LWIA(user.getRole());
                detailsForm.clickButton(Buttons.Edit);
                WorkforceAreaEditForm editPage = new WorkforceAreaEditForm();
                editPage.editWorkforceAreaDetails(lwia); //Edit parameters (the first workforce area)
                editPage.clickButton(Buttons.Save);
                detailsForm.validateWorkforceArea(lwia);
                lwia.setParameters(lwia); //set to the first workforce area new edited data
            }
        }
        BaseNavigationSteps.logout();
    }

}
