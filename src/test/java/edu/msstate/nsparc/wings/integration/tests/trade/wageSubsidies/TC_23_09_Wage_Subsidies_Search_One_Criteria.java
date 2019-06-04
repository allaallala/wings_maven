package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;

/**
 * Create ataa/rtaa enrollment and open its participantSSDetails page. Make one payment - it will be used for checking search criteria.
 * Fill out search criteria fields one by one and check, that displayed data is correct.
 * Created by a.vnuchko on 06.11.2015.
 */

@TestCase(id = "WINGS-11043")
public class TC_23_09_Wage_Subsidies_Search_One_Criteria extends TC_23_10_Wage_Subsidies_Search_Clear {
    private static final String ONE_ITEM = "One item found.";
    String[] criteria = {"Employer", "Status", "WeekDate", "DataKeyedFrom", "DataKeyedTo"};

    public void main(){
        String wageSubsidy = "other";

        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsidiesPage = openManageWageSubsidiesForm(atrt, wageSubsidy);

        info("Make one payment");
        String wageWeekAmount = subsidiesPage.selectWeekMakePayment(Constants.RANDOM_ONE, WORK_STATUS);
        String dateStart = CommonFunctions.getFirstWageWeek(atrt.getReemployment().getStartDate());

        searchAndValidate(criteria[0], subsidiesPage, atrt, dateStart, wageWeekAmount);
        searchAndValidate(criteria[1], subsidiesPage, atrt, dateStart, wageWeekAmount);
        searchAndValidate(criteria[2], subsidiesPage, atrt, dateStart, wageWeekAmount);
        searchAndValidate(criteria[3], subsidiesPage, atrt, dateStart, wageWeekAmount);
        searchAndValidate(criteria[4], subsidiesPage, atrt, dateStart, wageWeekAmount);
    }

    /**
     * Search form by typing one search criteria and check the result table.
     * @param type - search type
     * @param subsidiesPage - subsidies page
     * @param atrt - atrt/rtaa enrollment
     * @param dateStart - date start of the week
     * @param wageWeekAmount - amount of the week.
     */
    private void searchAndValidate(String type, ManageWageSubsidiesForm subsidiesPage, AtaaRtaaEnrollment atrt, String dateStart, String wageWeekAmount){
        logStep("Fill out any search criteria fields with the data of existing Wage Subsidy (all the fields one by one) ");
        subsidiesPage.searchOneCriteriaWeek(type, atrt, STATE_PENDING, dateStart);

        logStep("Click the [Search] button");
        subsidiesPage.clickButton(Buttons.Search);

        Assert.assertTrue(subsidiesPage.getSearchedCount().equals(ONE_ITEM));

        logResult("The suitable Wage Subsidies are shown in the Search Results form");
        subsidiesPage.validateWageRecords(dateStart, Constants.RANDOM_ONE, STATE_PENDING, atrt, wageWeekAmount);

        subsidiesPage.clickButton(Buttons.Clear);
    }
}

