package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check WIA Enrollment functionality using different roles - administrator, area director, manager
 * Created by a.vnuchko on 01.03.2016.
 */

@TestCase(id = "WINGS-11069")
public class TC_24_11_WIA_Enrollment_Roles_Admin_AD_Manager extends BaseWingsTest {
    private String applicationDate;
    private String contactPerson;
    private String contactPhone;
    private String relation;
    private String numberFamily;
    private String wages;
    private String familyIncome;
    private Participant partip;

    public void main() {

        //Role administrator
        User user = new User(Roles.ADMIN);
        commonWiaEnrollmentSteps(user);

        //Role area director
        user = new User(Roles.AREADIRECTOR);
        commonWiaEnrollmentSteps(user);

        //Role manager
        user = new User(Roles.MANAGER);
        commonWiaEnrollmentSteps(user);
    }

    /**
     * Common steps for checking user permissions
     *
     * @param user - current user
     */
    protected void commonWiaEnrollmentSteps(User user) {
        //(!) Create new WIA Enrollment
        if (user.getWiaEnrollment().getEnrollmentCreate()) {
            logStep("Create new WIA enrollment");
            divideMessage("Generate required date");
            setBasicInformationData();
            setProgramInformationData();
            AccountUtils.init();
            partip = new Participant();
            ParticipantCreationSteps.createParticipantDriver(partip, Constants.TRUE, Constants.FALSE);
            WiaEnrollmentSteps.createWIAEnrollmentForCreatedParticipant(partip, false, applicationDate, contactPerson, contactPhone, relation, user);

            //(!) If possible to click create another button and create new WIA Enrollment
            if (user.getWiaEnrollment().getEnrollmentCreateAnotherButton()) {
                logStep("Create another WIA enrollment if possible");
                setBasicInformationData();
                setProgramInformationData();
                WIAEnrollmentDetailsForm detailsPage = new WIAEnrollmentDetailsForm();
                detailsPage.createAnotherWiaEnrollment(applicationDate, contactPerson, contactPhone, relation, false);
            }
            BaseNavigationSteps.logout();
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check other functionality: edit, view, audit.
     *
     * @param user - current user.
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");
        BaseWingsSteps.logInAs(user.getRole());

        //(!) If user can view search results.
        if (user.getWiaEnrollment().getEnrollmentView()) {
            logStep("Check View functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);

            //(!) If user can create WIA enrollment - > he should confirm pop-up window.
            if (user.getWiaEnrollment().getEnrollmentCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }
            WIAEnrollmentSearchForm searchPage = new WIAEnrollmentSearchForm();
            searchPage.selectParticipantByUser(user, partip);
            searchPage.clickButton(Buttons.Search);
            searchPage.openFirstSearchResult();


            //(!) Check buttons on the detail WIA enrollment page.
            logStep("Check [Audit], [Delete], [Edit basic information], [Edit program information] buttons on the page");
            WIAEnrollmentDetailsForm detailsPage = new WIAEnrollmentDetailsForm();
            detailsPage.checkButtonsAuditDelete(user);

            //(!) Check edit basic...
            logStep("Edit basic and program information.");
            if (user.getWiaEnrollment().getEnrollmentEditBasicInfo()) {
                setBasicInformationData();
                detailsPage.editBasicInformation(applicationDate, contactPerson, contactPhone, relation);
                detailsPage.validateBasicInformation(applicationDate, contactPerson, relation);
            }

            //(!) ...and program information.
            if (user.getWiaEnrollment().getEnrollmentEditProgramInfo()) {
                setProgramInformationData();
                detailsPage.editProgramInformation(numberFamily, wages, familyIncome);
                detailsPage.validateProgramInformation(numberFamily, wages, familyIncome);
            }

            //(!) If user cannot view detailed information about WIA enrollment
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }

    /**
     * Set random basic information to create WIA Enrollment
     */
    private void setBasicInformationData() {
        this.applicationDate = CommonFunctions.getYesterdayDate();
        this.contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        this.contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        this.relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    }

    /**
     * Set random program information to create WIA Enrollment
     */
    private void setProgramInformationData() {
        this.numberFamily = "1" + CommonFunctions.getRandomIntegerNumber(1);
        this.wages = CommonFunctions.getRandomIntegerNumber(2);
        this.familyIncome = CommonFunctions.getRandomIntegerNumber(2);
    }
}
