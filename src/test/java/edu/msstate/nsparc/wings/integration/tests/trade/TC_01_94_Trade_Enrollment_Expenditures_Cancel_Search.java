package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10528")
public class TC_01_94_Trade_Enrollment_Expenditures_Cancel_Search extends BaseWingsTest {


    public void main() {

        TradeTraining tradeTraining = TradeEnrollmentObjects.getCreatedTradeTraining();
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance();
        TradeEnrollmentSteps.addExpenditureEncumbrance(tradeTraining.getTradeEnrollment(), expenditureEncumbrance);

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Fill out some search criteria fields with any data");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        manageExpenditureEncumbrancesForm.fillSearchCriteria(expenditureEncumbrance);

        logStep("Click the [Cancel] button");
        manageExpenditureEncumbrancesForm.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logStep("The Trade Enrollment Detail Screen is shown");
         new TradeEnrollmentDetailsForm();
    }
}
