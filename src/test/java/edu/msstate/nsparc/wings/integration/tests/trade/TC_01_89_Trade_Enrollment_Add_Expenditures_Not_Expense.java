package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10523")
public class TC_01_89_Trade_Enrollment_Add_Expenditures_Not_Expense extends BaseWingsTest {

    public void main() {

        TradeTraining tradeTraining = TradeEnrollmentObjects.getCreatedTradeTraining();

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Click [Add] button and Fill out the required fields with the valid data. Choose any option except Expense in block Transaction Type ");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.DEOBLIGATION);
        manageExpenditureEncumbrancesForm.addExpenditure(expenditureEncumbrance);

        logStep("Validate that new Expenditures-Encumbrances was added to the list");
        manageExpenditureEncumbrancesForm.validateExpenditureDetails(expenditureEncumbrance);
    }
}
