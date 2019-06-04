package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Create new ataa/rtaa enrollment, open its participantSSDetails page. Expand wage subsidies section and manage wage payments. Do NOT fill out any search criteria
 * and select [Search] button. Check the results table.
 * Created by a.vnuchko on 05.11.2015.
 */

@TestCase(id = "WINGS-11054")
public class TC_23_22_Wage_Subsidies_Payments_Search_No_Parameters extends TC_23_21_Wage_Subsidies_Payments_Search {
    private String wageType = "Subsidie";

    public void main() {


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, wageType);

        makeSomePayments(subsPage);

        logResult("All the Wage Subsidy Payments are shown in the Search Results form");
        Integer countWagePayments = ParticipantSqlFunctions.getCountWagePayments(atrt.getParticipant().getFirstName());
        String recordsCount = CommonFunctions.regexGetMatch(subsPage.getSearchedCount(), Constants.COUNT_REGEX);
        Assert.assertEquals("Incorrect quantity of payment records on the page", countWagePayments.toString(), recordsCount);
    }
}
