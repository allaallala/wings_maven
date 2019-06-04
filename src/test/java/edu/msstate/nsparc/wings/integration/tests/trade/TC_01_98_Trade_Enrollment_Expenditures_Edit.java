package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10532")
public class TC_01_98_Trade_Enrollment_Expenditures_Edit extends BaseWingsTest {

    public void main() {

        TradeTraining tradeTraining = TradeEnrollmentObjects.getCreatedTradeTraining();
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance();
        TradeEnrollmentSteps.addExpenditureEncumbrance(tradeTraining.getTradeEnrollment(), expenditureEncumbrance);

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Fill out search criteria fields with Expenditures-Encumbrances data");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        manageExpenditureEncumbrancesForm.performSearch(expenditureEncumbrance);

        logStep("Choose the Expenditures-Encumbrance and open Edit form");
        manageExpenditureEncumbrancesForm.selectLastExpenditureAndOpenEditForm();

        logStep("All the fields are pre-filled with actual correct data");
        manageExpenditureEncumbrancesForm.validateExpenditureEditForm(expenditureEncumbrance);

        logStep("Change Transaction Type to NOT expense");
        manageExpenditureEncumbrancesForm.changeExpenditureType(expenditureEncumbrance);

        logStep("Validate that related to Expense fields are not displayed");
        manageExpenditureEncumbrancesForm.validateExpenditureEditForm(expenditureEncumbrance);
    }
}
