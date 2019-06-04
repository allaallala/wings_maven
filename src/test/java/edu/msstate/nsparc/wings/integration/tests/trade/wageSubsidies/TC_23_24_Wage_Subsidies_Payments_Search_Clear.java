package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create new ataa/rtaa enrollment, open its participantSSDetails page. Expand wage subsidies section and manage wage payments. Fill out some search criteria and choose [Clear] button.
 * Check, that all fields are cleared.
 * Created by a.vnuchko on 05.11.2015.
 */

@TestCase(id = "WINGS-11056")
public class TC_23_24_Wage_Subsidies_Payments_Search_Clear extends TC_23_10_Wage_Subsidies_Search_Clear {
    private String[] searchData = {"123", "Pending", "Check"};
    String wageType = "Payments";

    public void main(){


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, wageType);

        logStep("Fill out some search criteria fields with any data");
        subsPage.fillOutPaymentForm(searchData[0], searchData[1], searchData[2], CommonFunctions.getCurrentDate());

        logStep("Click the [Clear Form] button");
        subsPage.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        subsPage.checkPaymentFormClear();
    }
}
