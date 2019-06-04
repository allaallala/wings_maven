package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityReferralReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10759")
public class TC_11_58_Data_Integrity_Referral extends BaseWingsTest {

    public void main() {

        info("We need to perform Referral creation and set invalid Creation date");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        createReferralWithInvalidData(participant);

        logStep("Login to the System");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Reports->Data integrity->Job Order report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_REFERRAL_REPORT);

        logStep("Select Invalid Dates radio button");
        DataIntegrityReferralReportForm dataIntegrityReferralReportForm = new DataIntegrityReferralReportForm();
        dataIntegrityReferralReportForm.checkInvalidDates();

        logStep("Select Creation Date");
        BaseWingsForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        dataIntegrityReferralReportForm.selectDateTypes("Creation Date");

        logStep("Search");
        dataIntegrityReferralReportForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        dataIntegrityReferralReportForm.validateParticipantSearchResults(participant);

        logEnd();
    }

    /**
     * Create referral with incorrect data
     * @param participant participant for creating referral
     */
    private void createReferralWithInvalidData(Participant participant) {
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User user = new User(Roles.STAFF);
        ReferralSteps.createReferral(participant, jobOrder, user);
        ParticipantSqlFunctions.setInvalidReferralCreationDate(participant);
    }
}
