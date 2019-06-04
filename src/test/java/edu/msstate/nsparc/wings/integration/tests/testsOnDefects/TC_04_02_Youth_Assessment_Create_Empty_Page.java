package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10584")
public class TC_04_02_Youth_Assessment_Create_Empty_Page extends BaseWingsTest {


    //sub-task WINGS-2667
    public void main() {


        logStep("Login to the WINGS");
        BaseNavigationSteps.loginYouthProviderDashboard();

        logStep("Open Youth Assessments create form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);
        BaseWingsSteps.popClick(Popup.Create);
        AssessmentCreationForm youthAssessmentCreationForm = new AssessmentCreationForm();

        logStep("Don't fill any fields, press Create button");
        youthAssessmentCreationForm.clickButton(Buttons.Create);

        logStep("Check, that warning message is displayed (not 500 error)");
        assertTrue("Warning message wasn't displayed", youthAssessmentCreationForm.checkValidatingMessagePresent());
    }
}
