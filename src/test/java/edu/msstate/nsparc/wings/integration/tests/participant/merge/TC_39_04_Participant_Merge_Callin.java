package edu.msstate.nsparc.wings.integration.tests.participant.merge;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ParticipantMergeForm;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInDetailForm;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.*;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_12_88_Participant_Record_Merge;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Create a participant with call-in (discard participant). Create another one to keep. Merge them and check, that
 * they are succefully merged.
 * Created by a.vnuchko on 07.04.2017.
 */

@TestCase(id = "WINGS-11270")
public class TC_39_04_Participant_Merge_Callin extends TC_12_88_Participant_Record_Merge {

    public void main() {
        String callIn = "Phone Call on " + CommonFunctions.getCurrentDate();
        String result = "Called,No Answer";
        info("Precondition: create a participant with Call-in.");
        String participantAccount = AccountUtils.getParticipantAccount();
        Participant discardParticipant = new Participant(participantAccount);
        JobOrder order = new JobOrder(AccountUtils.getEmployerAccount());
        ParticipantCreationSteps.createParticipantDriver(discardParticipant, Boolean.TRUE, Boolean.FALSE);
        EmployerSteps.createEmployer(order.getEmployer(), Roles.ADMIN);
        JobOrderSteps.createJobOrder(order);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(discardParticipant);
        CallInSteps.createCallIn(discardParticipant, order, Roles.ADMIN);
        //create a participant to keep with the same ssn
        createSecondParticipant(participantAccount, discardParticipant);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Advanced -> Data Utilities -> Participant Record Merge");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_PARTICIPANT_PROFILE_MERGE);

        logStep("Select record to discard");
        ParticipantMergeForm mergeForm = new ParticipantMergeForm();
        mergeForm.selectDiscardParticipant(discardParticipant);

        logStep("Select record to keep");
        mergeForm.selectKeepParticipant(keepParticipant);

        logStep("Select parameter (Call-in) from the precondition for merging");
        mergeForm.addCallIn(callIn);

        logStep("Merge");
        mergeForm.completeMerging();
        BaseNavigationSteps.logout();

        logResult("The second Participant Profile is successfully merged.The following information was successfully "
                + "merged: - Call-Ins");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Search);
        CallInSearchForm searchPage = new CallInSearchForm();
        searchPage.performSearch(order, keepParticipant);
        new CallInDetailForm().validateCallInData(keepParticipant, order, result);
    }
}
