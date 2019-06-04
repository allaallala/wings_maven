package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10513")
public class TC_01_102_Trade_Enrollment_Expenditures_Balance_On_Details_Page extends BaseWingsTest {

    // To perform the test we need expEnc of each type: EXPENSE, ENCUMBRANCE, DEOBLIGATION, REFUND, LOST_STOLEN
    private ExpenditureEncumbrance expenditureRefundType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.REFUND);
    private ExpenditureEncumbrance expenditureLostStolenType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.LOST_STOLEN);
    private ExpenditureEncumbrance expenditureEncumbranceType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.ENCUMBRANCE);
    private ExpenditureEncumbrance expenditureDeobligationType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.DEOBLIGATION);
    private ExpenditureEncumbrance expenditureExpenseType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.EXPENSE);
    // Store each expEnc objects to an array for validating
    private ExpenditureEncumbrance[] expenditureArray = {expenditureEncumbranceType, expenditureExpenseType, expenditureLostStolenType, expenditureDeobligationType, expenditureRefundType};


    public void main() {

        TradeTraining tradeTraining = new TradeTraining();
        TrainingSteps.createTradeTraining(tradeTraining, Roles.STAFF, Roles.ADMIN);
        TradeEnrollmentSteps.addSeveralExpenditureEncumbrance(tradeTraining.getTradeEnrollment(),expenditureArray);

        logStep("Log in as Trade Director and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Expand the Expenditures and Encumbrances Section");
        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.expandExpendituresSection();

        logStep("Check balances for fields in ExpendituresEncumbrances Section");
        detailsForm.validateExpendituresEncumbranceBalances(expenditureArray);
    }
}
