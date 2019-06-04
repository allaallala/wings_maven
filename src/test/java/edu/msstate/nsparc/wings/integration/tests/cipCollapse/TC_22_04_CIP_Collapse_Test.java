package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that contextual information pane is collapsed, when its double clicked.
 * Created by a.vnuchko on 12.10.2015.
 */

@TestCase(id = "WINGS-11029")
public class TC_22_04_CIP_Collapse_Test extends BaseWingsTest {
    private String assessmentType = "Wagner-Peyser";

    public void main() {


        info("Precondition: create new participant, assessment");
        Participant participant = new Participant();
        Assessment assessment = new Assessment(participant, assessmentType);
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        AssessmentSearchForm searchPage = AssessmentSteps.openFillSearchForm(assessment);

        logStep("Click the [Info Pane] button on any record associated with a participant");
        searchPage.openContextualPane();

        logStep("Click the [Info Pane] button once again");
        logResult("The displayed [Info Pane] is collapsed. An arrow icon changed its direction.");
        searchPage.closeContextualPane();
    }
}
