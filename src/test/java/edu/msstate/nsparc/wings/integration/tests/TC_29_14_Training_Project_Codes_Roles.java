package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ProjectCodeSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Search for training project codes on different user roles (Administrator, Area Director, Manager, Staff, Intake Staff,
 * Trade administrator, rapid response administrator, everify administrator, WIOA administrator, project code administrator,
 * DVOP, LVER, Executive, LWDA Staff, WIOA provider.
 * Created by a.vnuchko on 12.09.2016.
 */

@TestCase(id = "WINGS-11146")
public class TC_29_14_Training_Project_Codes_Roles extends BaseWingsTest {
    TrainingProvider trp = new TrainingProvider();

    public void main() {
        TrainingSteps.createTraining(trp, Roles.PCADMIN);

        //Role - everify administrator
        searchProjectCode(Roles.EVERIFY);

        //Role - wioa administrator
        searchProjectCode(Roles.WIOA);

        //Role - project code administrator
        searchProjectCode(Roles.PCADMIN);

        //Role - DVOP Veteran
        searchProjectCode(Roles.DVOP);

        //Role - LVER
        searchProjectCode(Roles.LVER);

        //Role - executive
        searchProjectCode(Roles.EXECUTIVE);

        //Role - LWDA Staff
        searchProjectCode(Roles.LWDASTAFF);

        //Role - WIOA provider
        searchProjectCode(Roles.WIOAPROVIDER);

        //Role - administrator
        searchProjectCode(Roles.ADMIN);

        //Role - area director
        searchProjectCode(Roles.AREADIRECTOR);

        //Role - manager
        searchProjectCode(Roles.MANAGER);

        //Role - staff
        searchProjectCode(Roles.STAFF);

        //Role - intake staff
        searchProjectCode(Roles.INTAKESTAFF);

        //Role - trade administrator
        searchProjectCode(Roles.TRADEDIRECTOR);

        //Role - rapid response administrator
        searchProjectCode(Roles.RRADMIN);
    }

    /**
     * Search for project codes with different roles
     *
     * @param role - user role.
     */
    private void searchProjectCode(Roles role) {
        logStep("Log in to the system as " + role.toString());
        BaseWingsSteps.logInAs(role);

        //If role is not equal to LVER, search for training project code.
        if (!role.equals(Roles.LVER)) {
            logStep("Open project code search form");
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PROJECT_CODES);
            ProjectCodeSearchForm searchPage = new ProjectCodeSearchForm();

            logStep("Search for training provider name and validate data");
            searchPage.selectProvider(trp.getName());
            searchPage.clickButton(Buttons.Search);
            searchPage.validateData(trp);

            logStep("Search for course name and validate data again");
            searchPage.selectCourse(trp.getCourseName());
            searchPage.clickButton(Buttons.Search);
            searchPage.validateData(trp);

        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.A_PROJECT_CODES, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
