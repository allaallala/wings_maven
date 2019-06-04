package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityParticipantReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10755")
public class TC_11_43_Data_Integrity_Participant_Duplicates extends BaseWingsTest {

    private String ssnReport = "SSN";

    public void main() {

        info("We need to create 2 Participants with same SSN");
        String participantAccount = AccountUtils.getParticipantAccount();
        Participant participant1 = new Participant(participantAccount);
        ParticipantCreationSteps.createParticipantDriver(participant1, Boolean.TRUE, Boolean.FALSE);
        AdvancedSqlFunctions.resetAccount(participantAccount);
        Participant participant2 = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant2, Boolean.TRUE, Boolean.FALSE);
        ParticipantSqlFunctions.setParticipantSSN(participant2, participant1.getSsn());

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Reports->Data integrity->Participant report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_PARTICIPANT_REPORT);

        logStep("Select Duplicates records type");
        DataIntegrityParticipantReportForm participantReportForm = new DataIntegrityParticipantReportForm();

        logStep("Select SSN report");
        participantReportForm.selectDuplicatesRecords(ssnReport);

        logStep("Uncheck the 'Limit number of Results to 500'");
        participantReportForm.checkLimit();

        logStep("Click on Search button");
        participantReportForm.clickButton(Buttons.Search);


        logStep("Fill Participant First and Last names");
        participantReportForm.inputFirstLastName(participant1.getFirstName(), participant1.getLastName());

        logStep("Click on Search button");
        participantReportForm.clickButton(Buttons.Search);

        logStep("Validate that Participants were found");
        participantReportForm.validateParticipantSearchResultsByColumn(participant1, "1");
        participantReportForm.validateParticipantSearchResultsByColumn(participant2, "1");
    }
}
