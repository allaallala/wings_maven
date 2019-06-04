package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityParticipantReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10753")
public class TC_11_41_Data_Integrity_Participant extends BaseWingsTest {

    public void main() {
        info("We need to perform Participant registration");
        String participantAccount = AccountUtils.getParticipantAccount();
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        info("And reset access username");
        AdvancedSqlFunctions.resetAccount(participantAccount);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Reports->Data integrity->Participant report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_PARTICIPANT_REPORT);

        logStep("Select unfinished records type");
        DataIntegrityParticipantReportForm reportForm = new DataIntegrityParticipantReportForm();
        reportForm.checkUnfinishedRecords();
        reportForm.selectApplicationUser("Partial Registration (Self-Service or Legacy)");
        reportForm.checkLimit();

        logStep("Search");
        reportForm.inputFirstLastName(participant.getFirstName(), participant.getLastName());
        reportForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        reportForm.validateParticipantSearchResultsByColumn(participant, "1");
        BaseNavigationSteps.logout();
    }
}
