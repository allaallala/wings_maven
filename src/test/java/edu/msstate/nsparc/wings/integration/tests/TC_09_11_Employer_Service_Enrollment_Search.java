package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10718")
public class TC_09_11_Employer_Service_Enrollment_Search extends BaseWingsTest {

    private static final String SERVICE_TITLE = "Registration";

    public void main() {
        info("Creating Employer Service Enrollment");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT, Popup.Search);

        logStep("Choose an Employer");
        EmployerEnrollmentSearchForm searchForm = new EmployerEnrollmentSearchForm();
        searchForm.selectEmployer(employer);

        logStep("Search");
        searchForm.clickButton(Buttons.Search);

        logStep("Validate, that Services for selected Employer are shown");
        searchForm.checkLabelData(employer, SERVICE_TITLE, CommonFunctions.getCurrentDate());
    }
}
