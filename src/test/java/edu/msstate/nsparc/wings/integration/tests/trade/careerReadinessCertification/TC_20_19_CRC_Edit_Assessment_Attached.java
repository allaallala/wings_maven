package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create a career readiness certification, change a score of an assessment, open crc participantSSDetails form and check, that result and score was changed.
 * Created by a.vnuchko on 21.10.2015.
 */

@TestCase(id = "WINGS-10986")
public class TC_20_19_CRC_Edit_Assessment_Attached extends BaseWingsTest {
    private static final String SCORE = "80";

    public void main() {
        info("Precondition: create crc and change one of the score of assessment attached.");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        AssessmentEditForm editPage = AssessmentSteps.openEditAssessment(crc.getAppliedMathematics(), Constants.RANDOM_ONE);
        editPage.changeScore(SCORE);
        crc.getAppliedMathematics().setScore(SCORE);
        BaseNavigationSteps.logout();

        CRCSteps.openDetailedParticipantPageCRC(crc);
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();

        logStep("Click [Edit] button ");
        detailsPage.clickButton(Buttons.Edit);

        logStep("Click [Save Changes] button");
        detailsPage.clickButton(Buttons.Save);

        logResult("The Career Readiness Certification Detail Screen is shown. The changes are saved. "
                + "The Result Determination was correctly recalculated in accordance with Assessments changes.");
        detailsPage.validateLevel(crc);
    }
}
