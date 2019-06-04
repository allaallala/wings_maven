package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10710")
public class TC_08_12_Employer_Search extends BaseWingsTest {

    private static final String COMPANY_NAME = "ompany";

    public void main() {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);

        logStep("Input company name in employer search form");
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.inputCompanyName(COMPANY_NAME);

        logStep("Click [Search] button");
        employerSearchForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        employerSearchForm.validateSearchResults(COMPANY_NAME);
    }
}
