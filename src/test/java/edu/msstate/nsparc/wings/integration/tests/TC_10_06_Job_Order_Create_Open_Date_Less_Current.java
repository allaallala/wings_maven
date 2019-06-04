package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.BasicInformationStaffForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * In this case we check, that when a user is trying to create a job order with open date less than current date,
 * the error message appears.
 * Created by a.vnuchko on 05.04.2017.
 */

@TestCase(id = "WINGS-10722")
public class TC_10_06_Job_Order_Create_Open_Date_Less_Current extends BaseWingsTest {

    public void main() {
        String COOKS = "Cooks, Restaurant";
        String errorText = "Open Date cannot be in the past.";
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());

        info("Precondition: create an employer to use in the job order");
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        logStep("Select employer and job title and add occupation code. Select open date less than current date");
        JobOrderCreationForm creationPage = new JobOrderCreationForm();
        creationPage.selectEmployer(jobOrder.getEmployer());
        creationPage.selectOsocCode(COOKS);
        new BasicInformationStaffForm().fillTitleDateDescription(jobOrder.getJobTitle(),
                CommonFunctions.getYesterdayDate(), CommonFunctions.getNextWeekDate());

        logStep("Try to process to the next page");
        creationPage.clickButton(Buttons.Continue);

        logResult("The message about invalid date appears");
        creationPage.checkErrorOpenDate(errorText);
    }
}
