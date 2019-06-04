package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.BaseTest;
import framework.CommonFunctions;
import framework.Logger;
import org.testng.Assert;

/**
 * This class is used for storing functions for Job Order related operations
 */
public class JobOrderSteps extends BaseTest {
    private static String openState = "OPEN";
    /**
     * This method is used for creation Job Order with Instructions for Participant
     * @param jobOrder Job Order object with data for Job Order creation
     * @return Instructions text
     */
    public static String createJobOrderWithInstructionsForParticipant(JobOrder jobOrder) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        String disclaimer = jobOrderCreationForm.createJobOrderWithInstructionsForParticipant(jobOrder);

        BaseNavigationSteps.logout();
        return disclaimer;
    }

    /**
     * This method is used for creation Job Order with Disclaimer
     * @param jobOrder Job Order object with data for Job Order creation
     * @return Disclaimer text
     */
    public static String createJobOrderWithDisclaimer(JobOrder jobOrder) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        String disclaimer = jobOrderCreationForm.createJobOrderWithDisclaimer(jobOrder);

        BaseNavigationSteps.logout();
        return disclaimer;
    }

    /**
     * This method is used for creating standard Job Order
     * @param employer Registered Employer
     * @param jobTitle Job Title
     */
     public static void createJobOrder(Employer employer, String jobTitle) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        jobOrderCreationForm.createJobOrder(employer.getCompanyName(), employer.getAddress().getZipCode(), employer.getAddress().getCity(), jobTitle);
        BaseNavigationSteps.logout();
    }

    /**
     * This method is used for creating Job Order which location is different from Employers location
     * @param jobOrder Job Order with data for creation
     */
    public static void createJobOrderWithLocation(JobOrder jobOrder) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        jobOrderCreationForm.createJobOrderWithLocation(jobOrder);
        BaseNavigationSteps.logout();
    }

    public static void editJobOrderOpenDate(JobOrder jobOrder) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.findJobOrder(jobOrder.getJobTitle());
        jobOrderSearchForm.clickJobTitle();
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();
        jobOrderDetailsForm.editOpenDate(jobOrder, Roles.STAFF);
        BaseNavigationSteps.logout();
    }

    /**
     * This method is used for Job Order creation to use it for Participant S-S tests
     * @param jobOrder Job Order entities with parameters
     * @param allowOnline Indicates should Job Order be available for Apply Online
     * @param question Indicates if Job Order should have custom question
     * @return Created Job Order id
     */
    public static String createJobOrder(JobOrder jobOrder, Boolean allowOnline, boolean question) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        String id = jobOrderCreationForm.createJobOrder(jobOrder, allowOnline, question);
        BaseNavigationSteps.logout();

        return  id;
    }

    /**
     * This method is used for creating Job Order using Employers Self-Services
     * @return Created Job Order
     */
    public static JobOrder createJobOrderSelfServices() {
        JobOrder jobOrder = new JobOrder();
        jobOrder.setEmployer(EmployerSteps.createEmployerSelfRegistration());

        BaseNavigationSteps.loginEmployer();

        EmployerHomePage homePage = new EmployerHomePage(jobOrder.getEmployer().getCompanyName());
        homePage.openJobOrderCreate();
        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm(Constants.JOB_ORDER_SS);
        jobOrderCreationForm.createJobOrderSelfServices(jobOrder, false, false);

        BaseNavigationSteps.logout();
        return jobOrder;
    }

    /**
     * This method is used for confirming Job Order by Admin, so it will be available for applying from Participant S-S
     * @return Created Job Order
     */
    public static JobOrder createJobOrderForApplyOnline() {
        JobOrder jobOrder = createJobOrderSelfServices();
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);
        JobOrderSearchForm searchForm = new JobOrderSearchForm();
        searchForm.performSearch(jobOrder);
        jobOrder.setJobID(searchForm.getJobOrderNumber());
        searchForm.clickJobTitle();
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();
        jobOrderDetailsForm.editStaffInformation();
        JobOrderEditForm editPage = new JobOrderEditForm();
        editPage.clickJobDevelopmentNo();
        editPage.clickAllowOnline(Constants.TRUE);
        editPage.clickOverrideNonVeteran();
        editPage.inputNonVeteranReleaseDate(CommonFunctions.getCurrentDate());
        editPage.clickButton(Buttons.Save);
        jobOrderDetailsForm.clickButton(Buttons.Done);
        searchForm.clickButton(Buttons.Search);
        Assert.assertTrue(openState.equals(searchForm.getFirstStatusTable()));
        BaseNavigationSteps.logout();
        return jobOrder;
    }

    /**
     * This method is used for Job Order creation with desired parameters
     * @param jobOrder Job Order object with data for creation
     * @param driver Indicates if Drivers license will be required
     * @param education Indicates if Education level will be required
     * @param question Indicates if custom question will be added to Job Order
     */
    public static void createJobOrder(JobOrder jobOrder, Boolean driver, Boolean education, Boolean question) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        jobOrderCreationForm.createJobOrder(jobOrder, driver, education, question);

        BaseNavigationSteps.logout();
    }

    public static void createJobOrder(JobOrder jobOrder) {
        createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());
    }

    /**
     * Perform common steps:
     *  1. log in to the system;
     *  2. navigate to the jobSearch searching page;
     *  3. fill out search criteria fields;
     *  4. perform search;
     *  5. open detail page of found jobSearch.
     * @param jobSearch JobSearch object to be found and opened
     * @param role User role
     */
    public static void openJobSearchDetailPage(JobSearch jobSearch, Roles role) {
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH, Popup.Search);

        Logger.getInstance().info("Search for 'Job Search' and open it");
        JobSearchSearchForm jobSearchSearchForm = new JobSearchSearchForm();
        jobSearchSearchForm.searchByCompanyAndOpen(jobSearch);
    }

    /**
     * This method is used for Job Order applying from Participant S-S
     * @param participant Participant that will apply for Job Order
     * @param jobOrder Job Order for applying
     */
    public static void applyForJobOrder(Participant participant, JobOrder jobOrder) {
        BaseNavigationSteps.loginParticipant();

        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.searchForJobs();

        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        jobFindForm.performSearch(jobOrder);

        jobFindForm.openJobOrderDetailsNew(jobOrder.getJobTitle());

        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm(jobOrder.getJobTitle());
        jobOrderDetailsForm.apply();
        jobOrderDetailsForm.checkAnswerAndApply();

        jobOrderDetailsForm.inputInitialsConfirm(participant.getFirstName().substring(0, 1)
                + participant.getLastName().substring(0, 1));
        if (BaseWingsForm.BaseButton.CREATE.getButton().isPresent()) {
            jobOrderDetailsForm.clickButton(Buttons.Create);
        }

        BaseNavigationSteps.logout();
    }
}
