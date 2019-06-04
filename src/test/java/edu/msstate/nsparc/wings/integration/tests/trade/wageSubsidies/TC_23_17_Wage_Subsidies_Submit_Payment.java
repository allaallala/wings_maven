package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * Create new ataa/rtaa enrollment, open its participantSSDetails page. Make one pending payment - check that results are displayed correctly.
 * Submit payment, check that state of the payment became "Sent". Manage subsidy payments and check, that amount is displayed correctly
 * Created by a.vnuchko on 06.11.2015.
 */
@TestCase(id = "WINGS-10458")
public class TC_23_17_Wage_Subsidies_Submit_Payment extends TC_23_10_Wage_Subsidies_Search_Clear {
    private static final String ONE_ITEM = "One item found.";
    private static final Integer NUMBER_OF_PAYMENTS = 1;

    public void main() {


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsidiesPage = openManageWageSubsidiesForm(atrt, "other");

        logStep("Make one payment");
        String wagePaymentAmount = subsidiesPage.selectWeekMakePayment(NUMBER_OF_PAYMENTS, WORK_STATUS);

        logStep("Fill out any search criteria field with the data of existing Wage Subsidy with all status");
        subsidiesPage.selectEmployer(atrt.getReemployment().getEmployer());
        subsidiesPage.selectStatus(STATE_PENDING);

        logStep("click the [Search] button");
        subsidiesPage.clickButton(Buttons.Search);

        logResult("There is only one record with status 'Pending' among records from search results");
        info(subsidiesPage.getSearchedCount());
        CustomAssertion.softTrue("Incorrect subsidy count", subsidiesPage.getSearchedCount().equals(ONE_ITEM));
        String dateStart = CommonFunctions.getFirstWageWeek(atrt.getReemployment().getStartDate());
        subsidiesPage.validateWageRecords(dateStart, NUMBER_OF_PAYMENTS, STATE_PENDING, atrt, wagePaymentAmount);

        logStep("Click the 'Submit Payment'");
        subsidiesPage.submitPayment();

        logResult("Payment is created. Status of all subsidies became 'Sent'. Manage Wage Subsidies Screen is shown");
        subsidiesPage.selectEmployer(atrt.getReemployment().getEmployer());
        subsidiesPage.selectStatus(STATE_SENT);
        subsidiesPage.clickButton(Buttons.Search);
        subsidiesPage.validateWageRecords(CommonFunctions.getFirstWageWeek(atrt.getReemployment().getStartDate()), NUMBER_OF_PAYMENTS, STATE_SENT, atrt, wagePaymentAmount);

        logStep("Return to ATAA-RTAA Enrollment Details page and click the 'Manage subsidy payments'");
        AtaaRtaaEnrollmentDetailsForm detailsPage = subsidiesPage.cancel();
        detailsPage.openWagePaymentsForm();

        logStep("View payments");
        logResult("Amount of just added payment = sum of amount of all sent subsidy (if there is no federal tax foe this subsidy)");
        subsidiesPage.fillOutPaymentForm(Constants.EMPTY, STATE_PENDING, Constants.ANY, Constants.EMPTY);
        subsidiesPage.clickButton(Buttons.Search);
        subsidiesPage.validatePaymentsRecords(wagePaymentAmount, STATE_PENDING, NUMBER_OF_PAYMENTS);
    }
}
