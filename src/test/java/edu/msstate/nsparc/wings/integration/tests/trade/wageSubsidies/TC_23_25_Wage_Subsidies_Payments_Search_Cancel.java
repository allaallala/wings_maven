package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create new ataa/rtaa enrollment, open its participantSSDetails page. Expand wage subsidies section and manage wage payments. Fill out some search criteria and choose [Cancel] button.
 * Check, that [ATAA/RTAA Enrollment Details] form is opened.
 * Created by a.vnuchko on 05.11.2015.
 */

@TestCase(id = "WINGS-11057")
public class TC_23_25_Wage_Subsidies_Payments_Search_Cancel extends TC_23_10_Wage_Subsidies_Search_Clear {
    private String[] searchData = {"123", "Pending", "Check"};
    String wageType = "Payments";

    public void main(){
        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, wageType);

        logStep("Fill out some search criteria fields with any data");
        subsPage.fillOutPaymentForm(searchData[0], searchData[1], searchData[2], CommonFunctions.getCurrentDate());

        logStep("Click the [Cancel] button");
        subsPage.clickButton(Buttons.Cancel);

        logResult("The ATAA-RTAA Enrollment Detail Screen is shown");
        new AtaaRtaaEnrollmentDetailsForm();
    }
}
