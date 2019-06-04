package edu.msstate.nsparc.wings.integration.tests.trade.individualEmploymentPlan;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.steps.IEPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Open individual employment search form, find existing IEP. Click the participant's name and edit [Data Signed] field. Save it. Check it out.
 * Created by a.vnuchko on 27.08.2015.
 */

@TestCase(id = "WINGS-11011")
public class TC_21_19_IEP_View_Data_Signed extends BaseWingsTest {

    public void main() {
        info("Precondition: Create some Individual Employment Plans");
        IndividualEmploymentPlan plan = new IndividualEmploymentPlan();
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), plan);

        IEPSteps.openSearchedParticipantDetails(new User(Roles.STAFF), plan);

        logStep("Enter any date in the Date Signed field");
        IndividualEmploymentPlanDetailsForm detailsPage = new IndividualEmploymentPlanDetailsForm();
        detailsPage.inputDataSigned(CommonFunctions.getCurrentDate());

        logStep("Press 'Save' button");
        detailsPage.saveChanges();
        detailsPage.areYouSure(Popup.Yes);

        logResult("Date is saved (it should not be displayed on printable version)");
        info(CommonFunctions.getCurrentDate());
        Assert.assertTrue(detailsPage.getDataSignedText().contains("Date Signed: " + CommonFunctions.getCurrentDate()));
    }
}
