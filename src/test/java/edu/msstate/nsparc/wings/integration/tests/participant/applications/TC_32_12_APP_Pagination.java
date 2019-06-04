package edu.msstate.nsparc.wings.integration.tests.participant.applications;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ViewYourApplicationsForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Created by a.korotkin on 11/24/2016.
 */

@TestCase(id = "WINGS-11187")
public class TC_32_12_APP_Pagination extends BaseWingsTest {

    public void main() {
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        int n = 0;
        while (n < 11) {
            divideMessage("Create Referral for the same participant");
            n++;
            AccountUtils.initEmployer();
            JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
            ReferralSteps.createReferralSameParticipant(participant, jobOrder, Roles.STAFF);
        }

        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.goApplicationsS_S();
        ViewYourApplicationsForm viewYourApplicationsForm = new ViewYourApplicationsForm();
        viewYourApplicationsForm.checkPaginationIsPresent();
    }
}
