package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Create CRC, change the score of one assessment. Find CRC and open its participantSSDetails page. Check, that message score is changed.
 * Created by a.vnuchko on 23.09.2015.
 */

@TestCase(id = "WINGS-10985")
public class TC_20_18_CRC_Create_Message_Attached extends BaseWingsTest {


    public void main() {
        info("Generate test data");
        String newScore = "80";

        info("Precondition: Create CRC and change the score of one (or more) Assessments that was (were) attached to the created CRC");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        AssessmentSteps.openEditAssessment(crc.getAppliedMathematics(), Constants.RANDOM_ONE);
        AssessmentEditForm editPage = new AssessmentEditForm();
        editPage.typeScore(newScore);
        editPage.saveChanges();
        BaseNavigationSteps.logout();

        CRCSteps.openDetailedParticipantPageCRC(crc);

        logResult("Message that score for some assessment attached to the CRC was changed displayed to the User");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        CustomAssertion.softAssertEquals(detailsPage.getWarningMessage(), CareerReadinessCertificationDetailsForm.WARNING_TEXT, "Incorrect warn text on the page");

    }
}
