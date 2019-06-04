package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create some W-P Assessments attached to CRC, open its detailed page and check fields ("Date Administered", "Scale Score"), that can be edited
 * Three types of CRC should be checked.
 * Created by a.vnuchko on 15.09.2015.
 */

@TestCase(id = "WINGS-10961")
public class TC_19_16_Assessment_Edit_WP_Attached_CRC extends BaseWingsTest {

    public void main() {
        String resultMessage = "Verify that 'Date Administered' and 'Scale Score' values can be edited only.";
        info("Precondition: Create some W-P Assessments attached to CRC with different types");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        AssessmentSteps.openEditAssessment(crc.getAppliedMathematics(), Constants.RANDOM_ONE);

        logResult(resultMessage);
        AssessmentEditForm editPage = new AssessmentEditForm();
        editPage.validateResult();
        BaseNavigationSteps.logout();

        info("Check the second assessment");
        AssessmentSteps.openEditAssessment(crc.getAppliedMathematics(), Constants.RANDOM_TWO);

        logResult(resultMessage);
        editPage.validateResult();
        BaseNavigationSteps.logout();

        info("Check the third assessment");
        AssessmentSteps.openEditAssessment(crc.getAppliedMathematics(), Constants.RANDOM_THREE);

        logResult(resultMessage);
        editPage.validateResult();
        BaseNavigationSteps.logout();
    }
}
