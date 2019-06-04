package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationAddExpenseForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10780")
public class TC_12_16_Relocation_Lump_Value_With_Petition_70000_79999 extends BaseWingsTest {

    private static final String LUMP_AMOUNT_MORE_THAN_1500 = "1501";
    private static final String LUMP_MAX_AMOUNT = "1501";

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment(PetitionType.ATAA_HIGH);
        Relocation relocation = new Relocation(Constants.TRUE);
        relocation.setTradeEnrollment(tradeEnrollment);
        // For adding expenses, the Relocation should be with Approved status
        RelocationCreationSteps.createRelocationAndEditStatus(relocation);

        info("Test works with Petition number :" + relocation.getTradeEnrollment().getPetition().getNumber());
        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Click [Add] button in the Expenses area");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.addExpence();

        logStep("Fill out the required fields with valid data");
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        RelocationAddExpenseForm relocationAddExpenseForm = new RelocationAddExpenseForm();
        relocationAddExpenseForm.fillOutStatusRelatedFields(relocationExpense);
        relocationAddExpenseForm.fillOutPayeeInfo(relocationExpense);

        logStep("Click on the checkbox 'Include Lump Sum'");
        relocationAddExpenseForm.chkIncludeLumpSum.click();
        relocationAddExpenseForm.waitForNotVisible(BaseWingsForm.BaseOtherElement.LOADING);

        logStep("Check that 'Lump Sum Amount' cell with value 1500$ appears");
        CustomAssertion.softAssertContains(relocationAddExpenseForm.tbcLumpSumAmountPaid.getText(), RelocationAddExpenseForm.DEFAULT_LUMP_FOR_PETITION_70000_TO_79999, "Default 'Lump Sum Amount' is not 1500!");

        logStep("Click on the checkbox 'Override'");
        relocationAddExpenseForm.clickOverrideCheckbox();

        logStep("Enter value which is more than 1500 in the 'Lump Sum Amount' and try to add such expense");
        relocationAddExpenseForm.inputLumpSum(LUMP_AMOUNT_MORE_THAN_1500);
        relocationAddExpenseForm.clickAdd();

        logStep("Check that it's impossible to enter value which more than 1500$");
        relocationAddExpenseForm.checkMaxLumpSumPresent(LUMP_MAX_AMOUNT);
    }
}
