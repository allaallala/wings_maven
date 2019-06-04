package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyEditForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check e-verify functionality for specified user role - administrator, manager, staff.
 * Created by a.vnuchko on 17.03.2016.
 */

@TestCase(id = "WINGS-11085")
public class TC_25_08_Everify_Roles_Admin_Manager_Staff extends BaseWingsTest {
    private Participant participant;
    private Everify everify;
    private String newDocNumber = CommonFunctions.getRandomIntegerNumber(3);
    private String dateSelection = CommonFunctions.getCurrentDate();
    String newDocIdentityType = "School record or report card";
    String newDocEmploymentType = "U.S. Citizen ID Card";

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonEverifySteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonEverifySteps(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonEverifySteps(user);
    }

    /**
     * Common steps for checking user permissions
     *
     * @param user - current user
     */
    protected void commonEverifySteps(User user) {
        //(!) Create new Everify if possible
        if (user.getEverify().getEverifyCreate()) {
            logStep("Create new e-verify");
            AccountUtils.init();
            participant = new Participant();
            everify = new Everify();
            ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
            EverifySteps.createEverify(everify, participant, new User(Roles.STAFF));
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check view, audit and edit functionality
     *
     * @param user - current user.
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");

        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can view search results.
        if (user.getEverify().getEverifyView()) {
            logStep("Check View functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY);

            //(!) If user can create Everify - > he should confirm pop-up window.
            if (user.getEverify().getEverifyCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }


            EverifySearchForm searchPage = new EverifySearchForm();
            searchPage.selectParticipantByUser(user, participant);
            searchPage.clickButton(Buttons.Search);
            searchPage.openFirstSearchResult();

            //(!) Check buttons on the detail Everify page.
            logStep("Check [Audit], [Delete], [Edit] buttons on the page");
            EverifyDetailsForm detailsPage = new EverifyDetailsForm();
            detailsPage.validateData(everify);
            detailsPage.checkButtons(user);

            //Check edit funtionality
            if (user.getEverify().getEverifyEdit()) {
                logStep("Check edit functionality");
                detailsPage.clickButton(Buttons.Edit);

                EverifyEditForm editPage = new EverifyEditForm();

                //(!) Check, if possible to edit current docs header.
                editPage.editDocsHeader(user, newDocIdentityType, newDocEmploymentType, everify);

                //(!) Check, if possible to edit Doc Identify Establishment Section
                editPage.editEstablishmentSection(user, newDocNumber, everify);

                //(!) Check, if possible to edit or view data selection.
                editPage.editDateSelection(user, dateSelection, everify);

                //(!) Check, if possible to edit or view case number input.
                editPage.editCaseNumberInput(user, newDocNumber, everify);
                editPage.clickButton(Buttons.Save);
                editPage.passParticipationRecalculationPage();

                //(!) Validate data.
                detailsPage.validateData(everify);
            }

        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
        //TODO I9 Certification letters and Referral notification are not ready
    }
}
