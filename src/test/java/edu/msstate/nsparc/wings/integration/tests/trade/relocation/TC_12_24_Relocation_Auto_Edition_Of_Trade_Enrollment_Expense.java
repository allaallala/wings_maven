package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10788")

public class TC_12_24_Relocation_Auto_Edition_Of_Trade_Enrollment_Expense extends BaseWingsTest {

    private static final String NEW_REQUESTED_AMOUNT = CommonFunctions.getRandomIntegerNumber(6);

    public void main() {
        Relocation relocation = new Relocation(Constants.TRUE);
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        RelocationCreationSteps.createRelocationWithExpense(relocation, relocationExpense);

        logStep("Log in as Trade Director and open Relocation participantSSDetails page");
        RelocationNavigationSteps.openRelocationDetailPage(relocation, Roles.TRADEDIRECTOR);

        logStep("Select expense and edit Requested Amount parameter");
        relocationExpense.setRequestedAmount(NEW_REQUESTED_AMOUNT);
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.editRequestedAmount(relocationExpense.getRequestedAmount());
        String editedExpense = relocationDetailsForm.getTotalAmountToBePaid();

        logStep("Open detail page of the Trade Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearchAndOpen(relocation.getTradeEnrollment());

        logStep("Expand the Expenditures section and check total Encumbrance and Expense");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandExpendituresSection();

        info("Expected Encumbrance and Expense: " + editedExpense);
        tradeEnrollmentDetailsForm.validateEncumbranceExpense(editedExpense);
    }
}
