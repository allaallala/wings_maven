package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Search for employer service enrollment with specific result and check, that record quantity on the page and
 * on the DB is the same.
 * Created by a.vnuchko on 04.04.2017.
 */

@TestCase(id = "WINGS-11264")
public class TC_38_25_Employer_Service_Enrollment_Search_Result extends BaseWingsTest {
    private String result = "Completed";

    public void main() {
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT, Popup.Search);

        EmployerEnrollmentSearchForm searchForm = new EmployerEnrollmentSearchForm();

        logStep("Select some Result");
        searchForm.selectResult(result);

        logStep("Search");
        searchForm.clickButton(Buttons.Search);

        logResult("All Enrollment Services with result you have entered are shown");
        String recordCount = searchForm.getSearchedCountRegex().replace(",","");
        Integer expectedCount = EmployerSqlFunctions.getEmployerServiceEnrollmentResult(result.toUpperCase());

        CustomAssertion.softAssertEquals(recordCount, expectedCount.toString(), "Incorrect quantity of the employer service enrollment with "
                + result + " status");
    }
}
