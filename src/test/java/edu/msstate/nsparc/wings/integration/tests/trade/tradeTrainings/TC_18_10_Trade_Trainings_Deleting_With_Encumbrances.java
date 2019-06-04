package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances.ManageExpenditureEncumbrancesForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.elements.TableCell;
import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;

/**
 * Checking that Encumbrances are removed with removing Trade Trainings. The warning message should be displayed during this process.
 * Created by a.korotkin on 10/23/2015.
 */

@TestCase(id = "WINGS-10933")
public class TC_18_10_Trade_Trainings_Deleting_With_Encumbrances extends BaseWingsTest {

    private String type = "Encumbrance";
    private String itemPath = "//table[@id='expenditureEncumbrances-table']/tbody/tr[%1$d]/td[2]";
    private Integer itemCount = 4;

    public void main() {
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
        expenditureEncumbrance.setEncumbranceType(ExpenditureEncumbrance.ExpenditureType.ENCUMBRANCE);
        ManageExpenditureEncumbrancesForm manageForm = new ManageExpenditureEncumbrancesForm();
        manageForm.addExpenditure(expenditureEncumbrance);
        manageForm.addExpenditure(expenditureEncumbrance);
        manageForm.addExpenditure(expenditureEncumbrance); //creating three records

        logStep("Remove Trade Training");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING);
        BaseWingsSteps.popClick(Popup.Search);
        TradeTrainingSearchForm tradeTrainingSearchForm = new TradeTrainingSearchForm();
        tradeTrainingSearchForm.performSearchAndOpen(tradeTraining);

        TradeTrainingDetailsForm tradeTrainingDetailsForm = new TradeTrainingDetailsForm();
        tradeTrainingDetailsForm.deleteRecord();

        for (int i=1; i<itemCount; i++) {
            TableCell tbcItems = new TableCell(By.xpath(String.format(itemPath, i)),"Items table cell");
            assertEquals("Encumbrance record doesn't exist", type, tbcItems.getText());
        }

        tradeTrainingDetailsForm.confirmDeletion(); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-8993
        tradeTrainingDetailsForm.areYouSure(Popup.Yes);
        BaseNavigationSteps.logout();

        logStep("Checking that Encumbrances are removed");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeTraining.getTradeEnrollment(), Roles.ADMIN);
        detailsForm.expandExpendituresSection();
        detailsForm.clickManageExpEncumbrances();

        manageForm.clickButton(Buttons.Search);
        manageForm.noSearchResults();
    }
}