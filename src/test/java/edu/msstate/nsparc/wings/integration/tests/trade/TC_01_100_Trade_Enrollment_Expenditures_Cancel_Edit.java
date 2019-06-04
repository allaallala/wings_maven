package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10511")
public class TC_01_100_Trade_Enrollment_Expenditures_Cancel_Edit extends BaseWingsTest {
    String transactionCategory = "Books";

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
        manageExpenditureEncumbrancesForm.selectExpenditureAndOpenEditForm();

        logStep("Edit any parameters");
        manageExpenditureEncumbrancesForm.changeExpenditureType(expenditureEncumbrance);
        manageExpenditureEncumbrancesForm.selectTransactionCategory(transactionCategory);

        logStep("Click Cancel button");
        manageExpenditureEncumbrancesForm.clickButton(Buttons.Cancel);
        manageExpenditureEncumbrancesForm.areYouSure(Popup.Yes);

        logStep("Validate that the changes aren't saved");
        manageExpenditureEncumbrancesForm.validateExpenditureDetails(expenditureEncumbrance);
    }
}
