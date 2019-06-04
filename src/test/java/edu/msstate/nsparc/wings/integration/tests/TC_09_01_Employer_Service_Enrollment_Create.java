package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10715")
public class TC_09_01_Employer_Service_Enrollment_Create extends BaseWingsTest {

    protected static final String SERVICE_TITLE = "Business Information";
    protected static final String CREATION_DATE = CommonFunctions.getCurrentDate();
    protected static final String SCHEDULED_DATE = CommonFunctions.getTomorrowDate();
    protected Employer employer;

    public void main() {
        EmployerEnrollmentCreationForm creationForm = repeatedFirstSteps(Constants.FALSE);

        logStep("Select Ended - Yes status");
        creationForm.chooseEndedService(Constants.TRUE);
        creationForm.inputDateResult(CREATION_DATE);
        creationForm.selectResult(Constants.COMPLETED);

        repeatedLastSteps(creationForm);
    }

    /**
     * Some steps, that repeat in different tests
     *
     * @param scheduledStatus - true, if 'Yes' status
     * @return - employer enrollment creation form.
     */
    protected EmployerEnrollmentCreationForm repeatedFirstSteps(Boolean scheduledStatus) {
        employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT, Popup.Create);

        logStep("Select employer");
        EmployerEnrollmentCreationForm creationForm = new EmployerEnrollmentCreationForm();
        creationForm.selectEmployer(employer);

        logStep("Select service");
        creationForm.selectService(SERVICE_TITLE);

        logStep("Select Scheduled - No status");
        creationForm.chooseScheduledService(scheduledStatus);
        creationForm.inputCreationDate(CREATION_DATE);
        return creationForm;
    }

    /**
     * Some last steps, that repeated for several test cases.
     *
     * @param creationForm - creation page.
     */
    protected EmployerEnrollmentSearchForm repeatedLastSteps(EmployerEnrollmentCreationForm creationForm) {
        logStep("Click the Create button");
        creationForm.clickButton(Buttons.Create);
        creationForm.clickButton(Buttons.Done);

        logStep("Open the Employer Service Enrollment Search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for created enrollment");
        EmployerEnrollmentSearchForm searchForm = new EmployerEnrollmentSearchForm();
        searchForm.performSearch(employer, SERVICE_TITLE);

        logStep("Validate displayed information");
        searchForm.checkLabelData(employer, SERVICE_TITLE, CREATION_DATE);
        return searchForm;
    }
}
