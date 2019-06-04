package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Create new ataa/rtaa enrollment, open its participantSSDetails page. Expand wage subsidies section and manage wage payments. Fill out some search criteria of the existing payment
 * and select [Search] button. Check the results table.
 * Created by a.vnuchko on 05.11.2015.
 */

@TestCase(id = "WINGS-11053")
public class TC_23_21_Wage_Subsidies_Payments_Search extends TC_23_10_Wage_Subsidies_Search_Clear {
    private static final Integer NUMBER_OF_PAYMENTS = 2;
    String manageType = "Other";

    public void main(){


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, manageType);

        String wageWeekPayment = makeSomePayments(subsPage);

        logResult("The suitable Wage Subsidy Payments are shown in the Search Results form");
        String recordsCount = CommonFunctions.regexGetMatch(subsPage.getSearchedCount(), Constants.COUNT_REGEX);
        Assert.assertEquals("Incorrect quantity of payment records on the page", NUMBER_OF_PAYMENTS.toString(), recordsCount);
        subsPage.validatePaymentsRecords(wageWeekPayment, STATE_PENDING, NUMBER_OF_PAYMENTS);
    }

    /**
     * Describes some steps to make payments with pre-defined quantity and state
     * @param subsPage - manage wage subsidies form.
     * @return wageWeekPayment.
     */
    public String makeSomePayments(ManageWageSubsidiesForm subsPage){
        info("Make some payments for chosen ataa/rtaa enrollment and open the [Manage wage subsidies payments search] form");
        String wageWeekPayment = subsPage.selectWeekMakePayment(Constants.RANDOM_TWO, WORK_STATUS);
        subsPage.submitPayment();
        subsPage.selectWeekMakePayment(Constants.RANDOM_THREE, WORK_STATUS);
        subsPage.submitPayment();
        AtaaRtaaEnrollmentDetailsForm detailsForm = subsPage.cancel();
        detailsForm.openWagePaymentsForm();

        logStep("Fill out some search criteria fields with any data");
        subsPage.fillOutPaymentForm(Constants.EMPTY, STATE_PENDING, Constants.ANY, Constants.EMPTY);

        logStep("Click the [Search] button");
        subsPage.clickButton(Buttons.Search);
        return wageWeekPayment;
    }
}
