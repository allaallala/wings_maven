package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10527")
public class TC_01_93_Trade_Enrollment_Expenditures_Clear_Search_Form extends BaseWingsTest {


    public void main() {

        TradeTraining tradeTraining = TradeEnrollmentObjects.getCreatedTradeTraining();
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance();
        TradeEnrollmentSteps.addExpenditureEncumbrance(tradeTraining.getTradeEnrollment(), expenditureEncumbrance);

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Fill out some search criteria fields with any data");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        manageExpenditureEncumbrancesForm.fillSearchCriteria(expenditureEncumbrance);

        logStep("Click the [Clear Form] button");
        manageExpenditureEncumbrancesForm.clickButton(Buttons.Clear);

        logStep("Validate search criteria fields are cleared");
        manageExpenditureEncumbrancesForm.validateDefaultSearchParameters();
    }
}
