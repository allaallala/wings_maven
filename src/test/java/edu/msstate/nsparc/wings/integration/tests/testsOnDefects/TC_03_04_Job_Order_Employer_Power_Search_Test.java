package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10564")
public class TC_03_04_Job_Order_Employer_Power_Search_Test extends BaseWingsTest {


    //sub-task WINGS-2569
    //sub-task WINGS-2624
    public void main() {

        info("Creating employer for use in test");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);

        logStep("Perform Job Order search for created Employer");
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.selectEmployer(employer);
        jobOrderSearchForm.clickButton(Buttons.Search);

        logStep("Then open Job Order creation form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS);
        BaseWingsSteps.popClick(Popup.Create);
        JobOrderCreationForm creationForm = new JobOrderCreationForm();

        logStep("Check, that Employer is prepopulated in the employer box");
        CustomAssertion.softTrue("Employer text does not contain company name", creationForm.getEmployerText().contains(employer.getCompanyName()));
        CustomAssertion.softTrue("Employer location does not contain city", creationForm.getEmployerLocation().contains(employer.getAddress().getCity()));
    }
}
