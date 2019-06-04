package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10531")
public class TC_01_97_Trade_Enrollment_Expenditures_Cancel_Remove extends BaseWingsTest {

    public void main() {

        TradeTraining tradeTraining = TradeEnrollmentObjects.getCreatedTradeTraining();
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance();
        TradeEnrollmentSteps.addExpenditureEncumbrance(tradeTraining.getTradeEnrollment(), expenditureEncumbrance);

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Fill out some search criteria fields with any data");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        manageExpenditureEncumbrancesForm.performSearch(expenditureEncumbrance);

        logStep("Choose any Expenditures-Encumbrance in search results using the radio-button");
        manageExpenditureEncumbrancesForm.clickFirstSearchResult();

        logStep("Click the [Remove] button and click on the 'No' in confirmation message");
        manageExpenditureEncumbrancesForm.cancelRemoveExpenditure();

        logStep("Validate that Expenditures-Encumbrances is NOT removed from the list");
        manageExpenditureEncumbrancesForm.checkFirstResult(Constants.TRUE);
    }
}
