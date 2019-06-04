package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10712")
public class TC_08_19_Employer_View extends BaseWingsTest {

    public void main() {
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);

        logStep("Perform search for employer");
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearch(employer);

        logStep("Open employer participantSSDetails form");
        String employerLocation = new SearchResultsForm().getFirstRowRecordText(3);
        String employerName = employerSearchForm.clickEmployerName();
        EmployerDetailsForm employerDetailsForm = new EmployerDetailsForm();

        logStep("Check, that location displayed correctly");
        CustomAssertion.softTrue("Location assert fail", employerDetailsForm.getLocationAddressText().contains(employerLocation));
        CustomAssertion.softAssertEquals(employerDetailsForm.getEmployerName(), employerName,"Incorrect employer name");
    }
}
