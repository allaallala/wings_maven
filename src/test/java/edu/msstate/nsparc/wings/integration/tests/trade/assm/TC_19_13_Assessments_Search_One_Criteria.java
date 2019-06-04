package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create assessment, open Assessment Search Form and find assessment using only one criteria (one by one).
 * Created by a.vnuchko on 15.09.2015.
 */

@TestCase(id = "WINGS-10958")
public class TC_19_13_Assessments_Search_One_Criteria extends TC_19_01_Assessment_Create_Cancel {
    String participant = "Participant";
    String dateAdministred = "DateAdministred";
    String program = "Program";

    public void main() {
        info("Precondition: Create some Assessment");
        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(partp, WAGNER_PEYSER);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        AssessmentSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);

        performSearchAndValidate(assessment, participant);
        performSearchAndValidate(assessment, dateAdministred);
        performSearchAndValidate(assessment, program);
    }

    /**
     * Perform search by one criteria and check, that the search result is correct.
     *
     * @param assem - assessment
     * @param type  - search criteria.
     */
    private void performSearchAndValidate(Assessment assem, String type) {
        logStep("Fill out any search criteria fields with the data of existing Assessment (all the fields one by one)");
        AssessmentSearchForm searchPage = new AssessmentSearchForm();
        searchPage.fillOneCriteria(assem, type);

        logStep("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        logResult("The suitable assessments are shown in the Search Results form");
        searchPage.validateOneCriteria(assem);
    }
}
