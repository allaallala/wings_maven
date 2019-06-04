package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


// Author: d.poznyak

@TestCase(id = "WINGS-10497")
public class TC_01_02_Employer_Create_Staff_Search extends TC_01_01_Employer_Create {


    public void main () {
        Employer employer = createEmployer();

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearch(employer);
    }
}
