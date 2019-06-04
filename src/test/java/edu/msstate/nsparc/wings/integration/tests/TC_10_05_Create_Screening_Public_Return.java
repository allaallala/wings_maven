package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * While creating job order, is chosen, that this job is at public school. After getting to the questiong page, return
 * to the first one and choose, that this job is not at public school. Create a job order and check, that is
 * successfully created.
 * Created by a.vnuchko on 05.04.2017.
 */

@TestCase(id = "WINGS-10721")
public class TC_10_05_Create_Screening_Public_Return extends TC_10_04_Create_Screening_Public_Complete {

    public void main(){
        info("It's necessary to create an employer first to use it on creating a job order");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());

        JobOrderCreationForm creationPage = repeatedSteps(jobOrder);

        logStep("Return back to the first page and change 'Yes' value in the 'Is this job at a Public School in MS?' " +
                "radio button group to 'No'");
        creationPage.returnToTheFirstPage();
        creationPage.clickJobPublicSchool(Constants.FALSE);

        logStep("Go to the page with question");
        creationPage.clickButton(Buttons.Continue);
        creationPage.clickButton(Buttons.Continue);
        creationPage.checkNoQuestionsPresent();
        creationPage.clickButton(Buttons.Continue);

        logStep("Create");
        creationPage.clickAllowOnline(Constants.TRUE);
        creationPage.clickJobDevelopmentNo();
        creationPage.clickButton(Buttons.Create);
        BaseNavigationSteps.logout();

        logResult("Job Order without screening questions should be created");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);
        JobOrderSearchForm searchForm = new JobOrderSearchForm();
        searchForm.performSearch(jobOrder);
    }
}
