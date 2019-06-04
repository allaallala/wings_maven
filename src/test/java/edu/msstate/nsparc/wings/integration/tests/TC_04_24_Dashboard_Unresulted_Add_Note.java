package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;

import static org.testng.Assert.assertNotSame;

@TestCase(id = "WINGS-10611")
public class TC_04_24_Dashboard_Unresulted_Add_Note extends BaseWingsTest {
    Integer timeout = 5;

    public void main() {

        info("We will check, if there is already unresulted referral");
        BaseWingsSteps.logInAs(Roles.STAFF);
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        if (!staffHomeForm.checkUnresultedReferralsPresent()) {
            BaseNavigationSteps.logout();
            Participant participant = new Participant(AccountUtils.getParticipantAccount());
            JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
            User staff = new User(Roles.STAFF);
            ReferralSteps.createReferral(participant, jobOrder, staff);
            ReferralSteps.setUnresultedReferralCreationDate(participant);
            BaseWingsSteps.logInAs(Roles.STAFF);
        }

        logStep("Select unresulted referral->Details->Notes");
        staffHomeForm.selectUnresultedReferrals();
        ReferralDetailsForm referralDetailsForm = new ReferralDetailsForm();

        logStep("Add Note->Done");
        String text = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
        String currentCount = referralDetailsForm.getNoteCount();
        NotesForm notesForm = new NotesForm();
        notesForm.addNote(text);
        referralDetailsForm.clickButton(Buttons.Done);

        logStep("Open Home page and select this unresulted referral");
        BaseNavigationSteps.home();
        staffHomeForm.selectUnresultedReferrals();

        logStep("Validate, that note is added");
        CustomAssertion.softTrue("Note wasn't saved", notesForm.checkNote(text));
         //to be sure, that loading div is not displayed.
        assertNotSame("Notes Count wasn't changed", currentCount, referralDetailsForm.getNoteCount());
        notesForm.closeNotes();

        BaseNavigationSteps.logout();
        logEnd();
    }
}
