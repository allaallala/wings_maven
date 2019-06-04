package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.denials.DenialSectionForm;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchCreateForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchEditForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionCreationForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.models.trade.*;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import framework.CommonFunctions;
import framework.Logger;

public class TradeEnrollmentSteps extends BaseNavigationSteps {

    public static void createTradeEnrollment(TradeEnrollment tradeEnrollment, Roles role) {
        ParticipantCreationSteps.createParticipantDriver(tradeEnrollment.getParticipant(), Constants.TRUE, Constants.FALSE);
        ParticipantSqlFunctions.insertParticipantInformationInReaui(tradeEnrollment.getParticipant());
        ParticipantDetailSteps.addEmployment(tradeEnrollment.getParticipant(), tradeEnrollment.getSeparationJob());

        TradeEnrollmentSteps.createPetition(tradeEnrollment.getPetition(), Roles.TRADEDIRECTOR);
        IndividualEmploymentPlan iep = new IndividualEmploymentPlan(tradeEnrollment.getParticipant());
        iep.setCreationDate(tradeEnrollment.getEligibilityDeterminationDate());
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), iep);
        BaseWingsSteps.logInAs(role);

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Create);

        TradeEnrollmentCreationForm creationForm = new TradeEnrollmentCreationForm();
        creationForm.fillOutCreationForm(tradeEnrollment);

        creationForm.completeCreation();

        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.clickButton(Buttons.Done);

        logout();
    }

    public static void createAtaaRtaaEnrollment(AtaaRtaaEnrollment ataaRtaaEnrollment, Roles role) {
        createAtaaRtaaWithoutLoggingOut(ataaRtaaEnrollment, role);
        AtaaRtaaEnrollmentDetailsForm detailsForm = new AtaaRtaaEnrollmentDetailsForm();
        detailsForm.clickButton(Buttons.Done);
        logout();
    }

    private static void createAtaaRtaaWithoutLoggingOut(AtaaRtaaEnrollment ataaRtaaEnrollment, Roles role) {
        createAtaaRtaaWithNoStatusAndLoggingOut(ataaRtaaEnrollment, role);
        AtaaRtaaEnrollmentDetailsForm enrollmentDetailsForm = new AtaaRtaaEnrollmentDetailsForm();
        enrollmentDetailsForm.clickButton(Buttons.Edit);
        AtaaRtaaEnrollmentEditForm editForm = new AtaaRtaaEnrollmentEditForm();
        editForm.changeEligibility(ataaRtaaEnrollment);
        editForm.finishEditing();
    }

    private static void createAtaaRtaaWithNoStatusAndLoggingOut(AtaaRtaaEnrollment ataaRtaaEnrollment, Roles role) {
        TradeEnrollmentSteps.createTradeEnrollment(ataaRtaaEnrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(ataaRtaaEnrollment.getParticipant(), ataaRtaaEnrollment.getReemployment());
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Create);
        AtaaRtaaEnrollmentCreationForm creationForm = new AtaaRtaaEnrollmentCreationForm();
        creationForm.fillOutCreationForm(ataaRtaaEnrollment);
        creationForm.completeCreation();
        new AtaaRtaaEnrollmentDetailsForm();
    }

    public static void createAtaaRtaaWithNoStatus(AtaaRtaaEnrollment ataaRtaaEnrollment, Roles role) {
        createAtaaRtaaWithNoStatusAndLoggingOut(ataaRtaaEnrollment, role);
        logout();
    }

    public static void createAppealedAtaaRtaaEnrollment(AtaaRtaaEnrollment enrollment) {
        TradeEnrollmentSteps.createAtaaRtaaWithoutLoggingOut(enrollment, Roles.TRADEDIRECTOR);

        DenialSectionForm denialSectionForm = new DenialSectionForm();
        denialSectionForm.openDenialAppealCreationForm();
        denialSectionForm.inputDateAppeal(CommonFunctions.getCurrentDate());
        denialSectionForm.clickButton(Buttons.Save);
        denialSectionForm.validateAppealDate(CommonFunctions.getCurrentDate());
        logout();
    }

    public static void addExpenditureEncumbrance(TradeEnrollment tradeEnrollment, ExpenditureEncumbrance expenditureEncumbrance) {
        BaseNavigationSteps.loginAdminDashboard();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(tradeEnrollment);

        TradeEnrollmentDetailsForm detailsPage = new TradeEnrollmentDetailsForm();
        detailsPage.expandExpendituresSection();
        detailsPage.clickManageExpEncumbrances();
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        manageExpenditureEncumbrancesForm.addExpenditure(expenditureEncumbrance);
        home();
        logout();
    }

    public static void addSeveralExpenditureEncumbrance(TradeEnrollment tradeEnrollment, ExpenditureEncumbrance[] encumbrances) {
        BaseNavigationSteps.loginAdminDashboard();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(tradeEnrollment);

        TradeEnrollmentDetailsForm detailsPage = new TradeEnrollmentDetailsForm();
        detailsPage.expandExpendituresSection();
        detailsPage.clickManageExpEncumbrances();
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        for (ExpenditureEncumbrance expenditureEncumbrance : encumbrances) {
            manageExpenditureEncumbrancesForm.addExpenditure(expenditureEncumbrance);
        }
        home();
        logout();
    }

    public static void createPetition(Petition petition, Roles role) {
        EmployerSteps.createEmployer(petition.getEmployer(), role);
        EmployerSqlFunctions.insertPetitionInformationInReaui(petition);

        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.E_TRADE_PETITIONS, Popup.Create);

        PetitionCreationForm creationForm = new PetitionCreationForm();
        creationForm.fillOutCreationForm(petition);

        creationForm.clickButton(Buttons.Create);

        creationForm.clickButton(Buttons.Done);

        logout();
    }

    public static void createIndividualEmploymentPlan(User user, IndividualEmploymentPlan iep) {
        if (!iep.isExistingParticipant()) {
            ParticipantCreationSteps.createVeteranParticipantNotDriver(iep.getParticipant());
        }
        if (Constants.WIOA.equalsIgnoreCase(iep.getAssessmentProgram())) {
            WiaEnrollmentSteps.createWIAEnrollment(user, iep.getParticipant(), Constants.FALSE, Constants.FALSE);
        }

        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Create);

        IndividualEmploymentPlanCreationForm creationForm = new IndividualEmploymentPlanCreationForm();
        creationForm.fillOutCreationForm(user, iep);

        creationForm.clickButton(Buttons.Create);

        if (iep.getIsLogout()) {
            BaseNavigationSteps.logout();
        }
    }

    public static void appealDenial(TradeEnrollment tradeEnrollment) {
        BaseNavigationSteps.loginAdminDashboard();

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(tradeEnrollment);

        TradeEnrollmentDetailsForm detailsPage = new TradeEnrollmentDetailsForm();
        detailsPage.expandDenials();
        detailsPage.inputAppealDate(CommonFunctions.getCurrentDate());
        new TradeEnrollmentDetailsForm().clickAndWait(BaseWingsForm.BaseButton.SAVE_CHANGES);
        new TradeEnrollmentDetailsForm().clickAndWait(BaseWingsForm.BaseButton.DONE);
        BaseNavigationSteps.logout();
    }

    public static void createJobSearch(JobSearch jobSearch, Roles role) {
        TradeEnrollmentSteps.createTradeEnrollment(jobSearch.getTradeEnrollment(), Roles.ADMIN);

        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Create);

        JobSearchCreateForm creationForm = new JobSearchCreateForm();
        creationForm.fillOutCreationForm(jobSearch);
        creationForm.clickButton(Buttons.Create);

        BaseNavigationSteps.logout();
    }

    /**
     * Create Job Search, then edit and set a status
     * @param jobSearch Object with jobSearch information
     */
    public static void createJobSearchWithStatus(JobSearch jobSearch) {
        TradeEnrollmentSteps.createTradeEnrollment(jobSearch.getTradeEnrollment(), Roles.ADMIN);

        BaseNavigationSteps.loginAdminDashboard();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH);
        BaseWingsSteps.popClick(Popup.Create);

        divideMessage("First create a Job Search");
        JobSearchCreateForm creationForm = new JobSearchCreateForm();
        creationForm.fillOutCreationForm(jobSearch);
        creationForm.clickButton(Buttons.Create);

        divideMessage("Edit Job Search Status");
        creationForm.clickButton(Buttons.Edit);

        JobSearchEditForm editForm = new JobSearchEditForm();
        editForm.editDetails(jobSearch);
        editForm.clickButton(Buttons.Save);

        BaseNavigationSteps.logout();
    }


    /**
     * Perform common steps:
     *  1. log in to the system;
     *  2. navigate to the Trade Enrollment search page;
     *  3. fill out search criteria fields;
     *  4. perform search;
     *  5. open detail page of found Trade Enrollment.
     * @param tradeEnrollment Trade Enrollment object to be found and opened
     * @param role User role
     */
    public static void openTradeEnrollmentDetailPage(TradeEnrollment tradeEnrollment, Roles role) {
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Popup.Search);

        Logger.getInstance().info("Search for 'Trade Enrollment' and open it");
        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(tradeEnrollment);
    }

    /**
     * Perform common steps:
     *  1. log in to the system;
     *  2. navigate to the Trade Enrollment search page;
     *  3. fill out search criteria fields;
     *  4. perform search;
     *  5. open detail page of found Trade Enrollment.
     *  6. expand Expenditures/Encumbrances section
     *  7. click "Manage Expenditures/Encumbrances" button
     * @param tradeEnrollment Trade Enrollment object to be found and opened
     * @param role User role
     */
    public static void openManageExpenditurePage(TradeEnrollment tradeEnrollment, Roles role) {
        openTradeEnrollmentDetailPage(tradeEnrollment, role);

        Logger.getInstance().info("Expand the Expenditures and Encumbrances Section");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandExpendituresSection();

        Logger.getInstance().info("Click [Manage Expenditures/Encumbrances]");
        tradeEnrollmentDetailsForm.clickManageExpEncumbrances();
    }
}
