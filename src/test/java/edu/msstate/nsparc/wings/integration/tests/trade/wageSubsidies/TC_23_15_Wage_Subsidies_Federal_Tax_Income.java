package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 *
 * Created by a.vnuchko on 30.10.2015.
 */

@TestCase(id = "WINGS-11049")
public class TC_23_15_Wage_Subsidies_Federal_Tax_Income extends BaseWingsTest {
    private static final String STATE_INITIAL = "Not Yet Entered";

    public void main() {
        info("Precondition: Create ATAA-RTAA Enrollment with several Wage Subsidies");
        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(atrt, Roles.TRADEDIRECTOR);

        info("Search for created ataa/rtaa enrollment and open its participantSSDetails page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(atrt, Roles.TRADEDIRECTOR);

        logStep("Expand Wage Subsidies and Click [Manage Wage Subsidies]");
        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
        detailsPage.openWageManage();

        logStep("Fill out any search criteria field with the data of existing Wage Subsidy");
        ManageWageSubsidiesForm subsPage = new ManageWageSubsidiesForm();
        subsPage.selectStatus(STATE_INITIAL);

        logStep("Click the [Search] button");
        subsPage.clickButton(Buttons.Search);

        logStep("Select any Wage Subsidy");
        subsPage.chooseFirstRecord();

        logStep("Click [Edit] button");
        subsPage.clickButton(Buttons.Edit);

        logStep("Choose option Yes in the field 'Does the participant wish to have Federal Income Tax withheld from their Wage Subsidy Payment?'");

        logStep("Click on the button 'Save changes and go to the next week'");

        logStep("Choose option Yes in the field 'Does the participant wish to have Federal Income Tax withheld from their Wage Subsidy Payment?' for the next subsidy");

        logStep("Click on the button 'Save and finish'");

        logResult("The Manage Wage Subsidies Screen is shown. The changes are saved");
    }
}
