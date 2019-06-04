package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10842")
public class TC_14_81_Trade_Enrollment_Remove_And_Add_Employment_Separation extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Open edit form of the Trade Enrollment");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.clickButton(Buttons.Edit);

        logStep("Select any Employment at Separation and remove it");
        TradeEnrollmentEditForm tradeEnrollmentEditForm = new TradeEnrollmentEditForm();
        tradeEnrollmentEditForm.selectFirstJobEdit();
        tradeEnrollmentEditForm.removeSeparationJob();

        logStep("Add Employment at Separation");
        tradeEnrollmentEditForm.addPreviousJob();
        tradeEnrollmentEditForm.selectFirstJobEdit();
        tradeEnrollmentEditForm.markQualifyingSeparation();

        logStep("Save changes");
        tradeEnrollmentEditForm.clickButton(Buttons.Save);

        logStep("Expand Employment at Separation section");
        tradeEnrollmentDetailsForm.expandEmploymentSeparation();

        logStep("Validate an Employment at Separation was deleted");
        tradeEnrollmentDetailsForm.checkEmploymentSeparation();
        tradeEnrollmentDetailsForm.noSearchResults();

        logStep("Open edit form of the Trade Enrollment and add Employment at Separation");
        tradeEnrollmentDetailsForm.clickButton(Buttons.Edit);
        tradeEnrollmentEditForm.addPreviousJob();
        tradeEnrollmentEditForm.selectFirstJobEdit();
        tradeEnrollmentEditForm.clickButton(Buttons.Save);
        tradeEnrollmentEditForm.clickButton(Buttons.Done);

        logStep("Validate an Employment at Separation was added");
        tradeEnrollmentDetailsForm.yesSearchResult();
    }
}
