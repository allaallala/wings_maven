package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10525")
public class TC_01_91_Trade_Enrollment_Cancel_Adding_Expenditures extends BaseWingsTest {

    public void main() {
        TradeTraining tradeTraining = TradeEnrollmentObjects.getCreatedTradeTraining();

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Cancel adding an Expenditure");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance();
        manageExpenditureEncumbrancesForm.cancelAddExpenditure(expenditureEncumbrance);

        logStep("Validate that Expenditure wasn't added");
        manageExpenditureEncumbrancesForm.noSearchResults();
    }
}
