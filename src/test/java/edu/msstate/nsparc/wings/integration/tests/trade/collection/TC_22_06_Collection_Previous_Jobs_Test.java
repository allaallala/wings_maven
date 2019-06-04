package edu.msstate.nsparc.wings.integration.tests.trade.collection;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


/**
 * Check, that preview for previous jobs can be opened from participant and contains correct information
 * Created by a.vnuchko on 14.10.2015.
 */

@TestCase(id = "WINGS-11031")
public class TC_22_06_Collection_Previous_Jobs_Test extends BaseWingsTest {

    public void main() {
        info("Precondition: create participant");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in WINGS as Staff");
        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, partp);

        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.addPreviousJob();

        logStep("Check that the preview can be successfully opened for the previous jobs (from the Participant participantSSDetails page) and contains the correct information");

        BaseNavigationSteps.logout();

        ParticipantNavigationSteps.openParticipantDetailsPage(Roles.STAFF, partp);

        logResult("Preview window is opened and contains correct information.");
        detailsPage.expandEmploymentHistory();
        Assert.assertTrue(detailsPage.getPreviousJobPageText().contains("Cook at Automation"));
    }
}
