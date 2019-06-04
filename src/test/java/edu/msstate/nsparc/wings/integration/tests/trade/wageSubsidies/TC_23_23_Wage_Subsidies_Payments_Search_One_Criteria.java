package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Create ataa/rtaa enrollment with wage subsidies. Make some payments. Search it by only one search criteria.
 * Created by a.vnuchko on 11.11.2015.
 */

@TestCase(id = "WINGS-11055")
public class TC_23_23_Wage_Subsidies_Payments_Search_One_Criteria extends TC_23_21_Wage_Subsidies_Payments_Search {
    private Integer countInteger;

    public void main() {
        String searchType = "Status";
        String otherReason = "other";

        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsidiesPage = openManageWageSubsidiesForm(atrt, otherReason);

        String weekAmount = makeSomePayments(subsidiesPage);
        Integer count = ParticipantSqlFunctions.getCountWagePayments(atrt.getParticipant().getFirstName());
        String countPage = CommonFunctions.regexGetMatch(subsidiesPage.getSearchedCount(), Constants.COUNT_REGEX);
        if (countPage == null) {
            countInteger = 0;
        } else {
            countInteger = Integer.valueOf(countPage);
        }
        CustomAssertion.softAssertEquals(count.toString(), countInteger.toString(), "Incorrect Count Integer");

        logStep("Fill out any search criteria fields with the data of existing Wage Subsidy Payment (all the fields one by one) and click [Search] button");
        subsidiesPage.searchOneCriteriaPayment(searchType, STATE_PENDING);

        logResult("The suitable Wage Subsidy Payments are shown in the Search Results form");
        subsidiesPage.validatePaymentsRecords(weekAmount, STATE_PENDING, count);
    }
}
