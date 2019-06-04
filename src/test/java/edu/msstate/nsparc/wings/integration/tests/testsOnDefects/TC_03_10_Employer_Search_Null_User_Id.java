package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10570")
public class TC_03_10_Employer_Search_Null_User_Id extends BaseWingsTest {

    //Bug WINGS-2606 , sub-task WINGS-2607
    public void main() {

        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        AdvancedSqlFunctions.resetAccount(employer.getEmployerAccount());

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);

        logStep("Try to find employer with null user id");
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearch(employer);
    }
}
