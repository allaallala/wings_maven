package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
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
import framework.CommonFunctions;


@TestCase(id = "WINGS-10436")
public class TC_10_01_Job_Order_Create extends BaseWingsTest {

    private static final String JOB_TITLE = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);

    public void main() {
        logStep("Creating Employer for Job Order");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        logStep("Filling all required data and Creating");
        JobOrderCreationForm jobOrderCreationForm = new JobOrderCreationForm();
        jobOrderCreationForm.createJobOrder(employer.getCompanyName(), employer.getAddress().getZipCode(), employer.getAddress().getCity(), JOB_TITLE);

        logStep("Open Job Order search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS);
        BaseWingsSteps.popClick(Popup.Search);
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();

        logStep("Select employer");
        jobOrderSearchForm.selectEmployer(employer);

        logStep("Type Job title and Creation dates");
        jobOrderSearchForm.inputTitleDate(JOB_TITLE, CommonFunctions.getCurrentDate(), CommonFunctions.getCurrentDate());

        logStep("Click on Search button");
        jobOrderSearchForm.clickButton(Buttons.Search);

        logStep("Check if Job Order was found");
        jobOrderSearchForm.checkSearchResult(JOB_TITLE);
    }
}
