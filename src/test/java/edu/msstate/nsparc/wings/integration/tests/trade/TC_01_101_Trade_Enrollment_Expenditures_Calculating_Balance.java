package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10512")
public class TC_01_101_Trade_Enrollment_Expenditures_Calculating_Balance extends BaseWingsTest {

    // ExpEnc will be displayed in table. Each type of expEnc should have a row number for easiest validating
    private static final int REFUND_TYPE_ROW_NUMBER = 1;
    private static final int ENCUMBRANCE_TYPE_ROW_NUMBER = 2;
    private static final int LOST_STOLEN_TYPE_ROW_NUMBER = 3;
    private static final int DEOBLIGATION_TYPE_ROW_NUMBER = 4;
    private static final int EXPENSE_TYPE_ROW_NUMBER = 5;


    public void main() {

        TradeTraining tradeTraining = new TradeTraining();
        TrainingSteps.createTradeTraining(tradeTraining, Roles.STAFF, Roles.ADMIN);

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Add several records with different Transaction Type: add types which increase the balance");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        ExpenditureEncumbrance expenditureRefundType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.REFUND);
        ExpenditureEncumbrance expenditureLostStolenType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.LOST_STOLEN);
        ExpenditureEncumbrance expenditureEncumbranceType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.ENCUMBRANCE);

        manageExpenditureEncumbrancesForm.addExpenditure(expenditureRefundType);
        manageExpenditureEncumbrancesForm.addExpenditure(expenditureEncumbranceType);
        manageExpenditureEncumbrancesForm.addExpenditure(expenditureLostStolenType);

        logStep("Add several records with different Transaction Type: add type which decrease the balance");
        ExpenditureEncumbrance expenditureDeobligationType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.DEOBLIGATION);
        ExpenditureEncumbrance expenditureExpenseType = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.EXPENSE);

        manageExpenditureEncumbrancesForm.addExpenditure(expenditureDeobligationType);
        manageExpenditureEncumbrancesForm.addExpenditure(expenditureExpenseType);

        logStep("Perform search with empty parameters to view all records");
        manageExpenditureEncumbrancesForm.clickButton(Buttons.Search);

        logStep("Check values in column 'Balance'");
        manageExpenditureEncumbrancesForm.validateBalanceInRow(expenditureRefundType, REFUND_TYPE_ROW_NUMBER);
        manageExpenditureEncumbrancesForm.validateBalanceInRow(expenditureEncumbranceType, ENCUMBRANCE_TYPE_ROW_NUMBER);
        manageExpenditureEncumbrancesForm.validateBalanceInRow(expenditureLostStolenType, LOST_STOLEN_TYPE_ROW_NUMBER);
        manageExpenditureEncumbrancesForm.validateBalanceInRow(expenditureDeobligationType, DEOBLIGATION_TYPE_ROW_NUMBER);
        manageExpenditureEncumbrancesForm.validateBalanceInRow(expenditureExpenseType, EXPENSE_TYPE_ROW_NUMBER);
    }
}
