package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.common.HeaderForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerEditForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import framework.CommonFunctions;
import framework.Logger;
import framework.elements.Link;

public class BaseNavigationSteps extends BaseWingsSteps {

    public static void logout() {
        scrollUp();
        new HeaderForm().logOut();
    }

    public static void home() {
        new HeaderForm().goHome();
    }

    protected static void navigateToEmployerRecordsSearch() {
        new StaffLocationForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
    }

    protected static EmployerEditForm openEmployerEditForm(Employer employer) {
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearch(employer);
        employerSearchForm.openFirstSearchResult();
        EmployerDetailsForm employerDetailsForm = new EmployerDetailsForm();
        employerDetailsForm.openEmployerEditForm();
        return new EmployerEditForm();
    }

    public static void navigateToReferralSearch() {
        new StaffLocationForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
        BaseWingsSteps.popClick(Popup.Search);
    }

    public static void addEmployment(Participant participant, PreviousJob job) {
        openAddNewEmploymentRecordForm(participant);
        ParticipantAddEmploymentForm addEmploymentForm = new ParticipantAddEmploymentForm();
        addEmploymentForm.addRecord(job);
        logout();
    }

    /**
     * This method is used for getting total number of search results pages
     * @param lnkPagesQuantity Link with pages number
     * @return Last page number
     */
    public static int getLastPage(Link lnkPagesQuantity) {
        String temp = CommonFunctions.regexGetMatch(lnkPagesQuantity.getText(), "[\\d]+ Next");
        return Integer.parseInt(CommonFunctions.regexGetMatch(temp, "[\\d]+"));
    }

    public static void loginEmployer() {
        Logger.getInstance().info("Log in WINGS as Employer");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();
    }

    public static void loginParticipant() {
        Logger.getInstance().info("Log in WINGS as Participant");
        LoginForm loginForm = new LoginForm();
        loginForm.loginParticipant();
        loginForm.checkInternalError();
    }

    public static void loginYouthProviderDashboard() {
        LoginForm loginForm = new LoginForm();
        loginForm.loginYouthProvider();
        StaffLocationForm staffPage = new StaffLocationForm();
        staffPage.clickButton(Buttons.Continue);
    }

    public static void loginAdminDashboard() {
        LoginForm loginForm = new LoginForm();
        loginForm.loginAdmin();
        StaffLocationForm staffPage = new StaffLocationForm();
        staffPage.clickButton(Buttons.Continue);
    }

    public static String[][] openWiaEnrlCreationForm(Integer days) {
        String[] basicInformationData = {CommonFunctions.getDaysAgoDate(days + 1),//Application Date
                CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH),//Contact person
                CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH),//Contact Phone
                CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH)//Relation to Participant
        };
        String [] programInformationData =  {"1" + CommonFunctions.getRandomIntegerNumber(1),//Number In Family
                "1" + CommonFunctions.getRandomIntegerNumber(1),//Preprogram Wages
                "1" + CommonFunctions.getRandomIntegerNumber(2)//AnnualFamilyIncome
        };
        String[][] data = {basicInformationData, programInformationData};

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Create);
        return data;
    }

    public static void openAddNewEmploymentRecordForm(Participant participant) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpen(participant);
        expandAndOpenNewEmploymentRecordForm();
    }

    public static void expandAndOpenNewEmploymentRecordForm() {
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.expandEmploymentHistory();
        detailsForm.clickPreviousJob();
    }
}
