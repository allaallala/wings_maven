package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10787")

public class TC_12_23_Relocation_Expenses_Calculation extends BaseWingsTest {

    private static final String DENIED_REASON = "Denied";
    private static final String NEW_REQUESTED_AMOUNT = CommonFunctions.getRandomIntegerNumber(6);
    private static final String NEW_DECREASED_REQUESTED_AMOUNT = CommonFunctions.getRandomIntegerNumber(5);

    private static final String NEW_REIMBURSED_AMOUNT = CommonFunctions.getRandomIntegerNumber(5);
    private static final String NEW_DECREASED_REIMBURSED_AMOUNT = CommonFunctions.getRandomIntegerNumber(4);

    public void main() {

        Relocation relocation = new Relocation(Constants.TRUE);
        RelocationExpense defaultExpense = new RelocationExpense(Constants.TRUE);
        RelocationCreationSteps.createRelocationWithExpense(relocation, defaultExpense);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);
        RelocationExpense additionalApprovedExpense = new RelocationExpense(Constants.TRUE);

        logStep("Add expense with Approved status");
        RelocationCreationSteps.addRelocationExpenseWithoutLoggingOut(additionalApprovedExpense);

        logStep("Validate initial total amounts");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        RelocationExpense[] expenseArray = {defaultExpense, additionalApprovedExpense};
        relocationDetailsForm.validateTotalAmounts(expenseArray);

        logStep("Add Denied expense");
        RelocationExpense additionalDeniedExpense = new RelocationExpense(Constants.FALSE);
        RelocationCreationSteps.addRelocationExpenseWithoutLoggingOut(additionalDeniedExpense);

        logStep("Validate total amounts after added relocation expense");
        RelocationExpense[] expenseArrayWithDenied = {defaultExpense, additionalApprovedExpense, additionalDeniedExpense};
        relocationDetailsForm.validateTotalAmounts(expenseArrayWithDenied);

        logStep("Edit approved expense: change status to Denied");
        defaultExpense.setApproved(Constants.FALSE);
        defaultExpense.setReasonDenied(DENIED_REASON);
        relocationDetailsForm.editFirstAddedExpense(defaultExpense);

        logStep("Check total amounts after added expense");
        relocationDetailsForm.validateTotalAmounts(expenseArrayWithDenied);

        logStep("Edit unapproved expense: change status to Approved");
        defaultExpense.setApproved(Constants.TRUE);
        relocationDetailsForm.editFirstAddedExpense(defaultExpense);

        logStep("Validate total amounts after changins status of expense");
        relocationDetailsForm.validateTotalAmounts(expenseArrayWithDenied);

        logStep("Edit approved expense: increase all amounts");
        defaultExpense.setRequestedAmount(NEW_REQUESTED_AMOUNT);
        defaultExpense.setEmployerReimbursedAmount(NEW_REIMBURSED_AMOUNT);
        relocationDetailsForm.editFirstAddedExpense(defaultExpense);

        logStep("Validate total amounts");
        relocationDetailsForm.validateTotalAmounts(expenseArrayWithDenied);

        logStep("Edit approved expense: decrease all amounts");
        defaultExpense.setRequestedAmount(NEW_DECREASED_REQUESTED_AMOUNT);
        defaultExpense.setEmployerReimbursedAmount(NEW_DECREASED_REIMBURSED_AMOUNT);
        relocationDetailsForm.editFirstAddedExpense(defaultExpense);

        logStep("Validate total amounts after edition of the expense");
        relocationDetailsForm.validateTotalAmounts(expenseArrayWithDenied);

        logStep("Remove unapproved expense and validate total amounts");
        relocationDetailsForm.removeLastAddedExpense();
        RelocationExpense[] expenseArrayWithoutDenied = {defaultExpense, additionalApprovedExpense};
        relocationDetailsForm.validateTotalAmounts(expenseArrayWithoutDenied);

        logStep("Remove approved expense and validate total amounts");
        relocationDetailsForm.removeLastAddedExpense();
        RelocationExpense[] expenseArrayWithoutDeniedAndApproved = {defaultExpense};
        relocationDetailsForm.validateTotalAmounts(expenseArrayWithoutDeniedAndApproved);
    }
}
