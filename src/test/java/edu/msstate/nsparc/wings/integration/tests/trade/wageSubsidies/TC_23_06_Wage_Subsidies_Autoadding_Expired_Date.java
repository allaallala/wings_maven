package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Create ATAA/RTAA Enrollment with participant having reemployment, and reemployment date + 728 < current date.
 * Manage subsidies of ATAA/RTAA Enrollment and check its wage status and zeby all wage are presented in the manage wage subsidies table.
 * Created by a.vnuchko on 29.10.2015.
 */

@TestCase(id = "WINGS-11041")
public class TC_23_06_Wage_Subsidies_Autoadding_Expired_Date extends TC_23_05_Wage_Subsidies_Autoadding_Current_Date {
    private static final Integer DAYS_START_REEMPLOYMENT_DATE = 731;

    public void main() {


        info("1. Participant has one job which can be used as re-employment; current date should be later than start re-employement date + 728 days\n"
                + "2. Create trade enrollment for this participant");
        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.RTAA, DAYS_START_REEMPLOYMENT_DATE);
        atrt.getTradeEnrollment().getSeparationJob().setEndDate(CommonFunctions.getDaysNextDate(atrt.getTradeEnrollment().getSeparationJob().getStartDate(), 1));
        atrt.getTradeEnrollment().getPetition().setImpactDate(CommonFunctions.getDaysAgoDate(770));
        makeActions(atrt);

        logResult("The Manage Wage Subsidies Screen Screen is shown. \n"
                + "Table 'Search Results' contains subsidies for every week between the start of qualifying re-employment and the "
                + "expiration of benefits date (last record is first Saturday after Deadline date). All records have status 'Not yet entered' ");
        ManageWageSubsidiesForm subsidiesPage = new ManageWageSubsidiesForm();
        String expectedSubsidiesCount = CommonFunctions.getNumberOfWageWeeks(atrt.getReemployment().getStartDate());
        CustomAssertion.softTrue("Incorrect quantity of subsidies records on form", subsidiesPage.getSearchedCount().contains(expectedSubsidiesCount));
    }
}
