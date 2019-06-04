package edu.msstate.nsparc.wings.integration.tests.participant.applications;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
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

import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Created by a.korotkin on 11/21/2016.
 */

@TestCase(id = "WINGS-11186")
public class TC_32_11_APP_Search_By_Each_Field extends BaseWingsTest {

    public void main() {
        info("We need to create Participant first");
        Participant participant = new Participant();
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));

        logStep("Creating a Job");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        ReferralSteps.createReferralWithSettingID(participant, jobOrder, Roles.STAFF);

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("View Your Applications");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.goApplicationsS_S();
        ViewYourApplicationsForm viewYourApplicationsForm = new ViewYourApplicationsForm();
        viewYourApplicationsForm.inputJobOrderId(jobOrder.getJobID());
        viewYourApplicationsForm.clickButton(Buttons.Search);
        softTrue("Incorrect Application is found", viewYourApplicationsForm.getJobTitleAndID().contains(jobOrder.getJobTitle()));
        viewYourApplicationsForm.clearJobID();
        viewYourApplicationsForm.inputJobOrderTitle(jobOrder.getJobTitle());
        viewYourApplicationsForm.clickButton(Buttons.Search);
        softTrue("Incorrect Application is found", viewYourApplicationsForm.getJobTitleAndID().contains(jobOrder.getJobID()));
        viewYourApplicationsForm.clearJobTitle();
        viewYourApplicationsForm.inputEmployer(jobOrder.getEmployer().getCompanyName());
        viewYourApplicationsForm.clickButton(Buttons.Search);
        softTrue("Incorrect Application is found", viewYourApplicationsForm.getJobTitleAndID().contains(jobOrder.getJobID()));
        //// TODO: 11/24/2016 searching by Date Applied, Date of interview and Status

    }
}
