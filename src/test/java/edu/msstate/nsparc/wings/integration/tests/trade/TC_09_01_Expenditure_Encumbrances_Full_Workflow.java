package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10452")
public class TC_09_01_Expenditure_Encumbrances_Full_Workflow extends BaseWingsTest {

    public void main () {

        TradeTraining tradeTraining = new TradeTraining();
        TrainingSteps.createTradeTraining(tradeTraining, Roles.STAFF, Roles.ADMIN);
        ExpenditureEncumbrance expenditureEncumbrance = new ExpenditureEncumbrance();

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeTraining.getTradeEnrollment(), Roles.ADMIN);

        logStep("Open Expenditures Encumbrances page");
        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.expandExpendituresSection();
        detailsForm.clickManageExpEncumbrances();

        logStep("Add Expenditure");
        ManageExpenditureEncumbrancesForm manageForm = new ManageExpenditureEncumbrancesForm();
        manageForm.addExpenditure(expenditureEncumbrance);

        logStep("Search for created Expenditure");
        manageForm.performSearch(expenditureEncumbrance);

        logStep("Make sure the record is found");
        manageForm.validateExpenditureDetails(expenditureEncumbrance);

        logStep("Edit the created Expenditure");
        expenditureEncumbrance.generateRandomData();
        manageForm.editExpenditure(expenditureEncumbrance);

        logStep("Validate that the changes were saved");
        manageForm.performSearch(expenditureEncumbrance);
        manageForm.validateExpenditureDetails(expenditureEncumbrance);

        logStep("Remove Expenditure record");
        manageForm.removeExpenditure();

        logStep("Make sure the record was deleted");
        manageForm.performSearch(expenditureEncumbrance);
        manageForm.checkFirstResult(false);
    }
}
