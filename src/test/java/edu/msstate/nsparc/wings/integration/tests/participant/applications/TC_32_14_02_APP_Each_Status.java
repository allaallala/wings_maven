package edu.msstate.nsparc.wings.integration.tests.participant.applications;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ViewYourApplicationsForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Created by a.korotkin on 12/2/2016.
 */

@TestCase(id = "WINGS-11189")
public class TC_32_14_02_APP_Each_Status extends BaseWingsTest {
    private String[] arrayJobs = new String[3];
    private String[] arrayResults = {"Hired", "Refused Offer", "Not Hired"};

    public void main() {

        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        int n = 0;
        while (n < 3) {
            n++;
            AccountUtils.initEmployer();
            JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
            ReferralSteps.createReferralWithSettingID(participant, jobOrder, Roles.STAFF);
            arrayJobs[n - 1] = jobOrder.getJobID();
            if (!arrayResults[n - 1].equals("Pending")) {
                setStatusForApplication(participant, jobOrder.getEmployer(), n - 1);
            }
        }


        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.goApplicationsS_S();
        ViewYourApplicationsForm viewYourApplicationsForm = new ViewYourApplicationsForm();
        viewYourApplicationsForm.sortByDateApplied();
        for (int i = 0; i < 3; i++) {
            softTrue("Record #" + (i + 1) + " has incorrect status", viewYourApplicationsForm.getStatusOfApplication(i).equals(arrayResults[i]) && viewYourApplicationsForm.getRecord(i).contains(arrayJobs[i]));
        }
    }

    private void setStatusForApplication(Participant participant, Employer employer, int i) {
        ReferralSteps.searchReferralAndOpen(participant, employer);
        ReferralDetailsForm referralDetailsForm = new ReferralDetailsForm();
        referralDetailsForm.clickButton(Buttons.Edit);
        ReferralEditForm referralEditForm = new ReferralEditForm();
        referralEditForm.resultReferral(arrayResults[i], CommonFunctions.getCurrentDate());
        referralEditForm.clickButton(Buttons.Save);
        BaseNavigationSteps.logout();
    }
}
