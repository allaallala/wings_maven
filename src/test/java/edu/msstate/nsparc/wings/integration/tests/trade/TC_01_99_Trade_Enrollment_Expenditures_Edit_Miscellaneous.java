package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10533")
public class TC_01_99_Trade_Enrollment_Expenditures_Edit_Miscellaneous extends BaseWingsTest {
    private String category = "Miscellaneous";

    public void main() {

        TradeTraining tradeTraining = TradeEnrollmentObjects.getCreatedTradeTraining();
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance(ExpenditureEncumbrance.ExpenditureType.DEOBLIGATION, category);
        TradeEnrollmentSteps.addExpenditureEncumbrance(tradeTraining.getTradeEnrollment(), expenditureEncumbrance);

        logStep("Log in as Trade Director and open Expenditure/Encumbrance managing page");
        TradeEnrollmentSteps.openManageExpenditurePage(tradeTraining.getTradeEnrollment(), Roles.TRADEDIRECTOR);

        logStep("Fill out search criteria fields with Expenditures-Encumbrances data");
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        manageExpenditureEncumbrancesForm.performSearch(expenditureEncumbrance);

        logStep("Choose the Expenditures-Encumbrance and open Edit form");
        manageExpenditureEncumbrancesForm.selectExpenditureAndOpenEditForm();

        logStep("Change value in Transaction Type from Miscellaneous to any other. ");
        manageExpenditureEncumbrancesForm.selectTransactionCategory("Books");

        logStep("Validate that Specific Reason field is disappeared.");

        CustomAssertion.softNotTrue("Specific Reason field is displayed!", manageExpenditureEncumbrancesForm.isPresentMiscellaneousField());

        logStep("Change value in Transaction Type to Miscellaneous");
        manageExpenditureEncumbrancesForm.selectTransactionCategory("Miscellaneous");

        logStep("Enter data into Specific Reason");
        manageExpenditureEncumbrancesForm.fillInMiscellaneousFields(expenditureEncumbrance);

        logStep("Click the [Save Changes] button");
        manageExpenditureEncumbrancesForm.clickButton(Buttons.Save);
        manageExpenditureEncumbrancesForm.validateCategoryAndSpecificReason(expenditureEncumbrance);
    }
}
