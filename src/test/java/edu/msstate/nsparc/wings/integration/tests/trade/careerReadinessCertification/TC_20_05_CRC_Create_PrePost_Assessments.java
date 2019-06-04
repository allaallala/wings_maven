package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationBaseForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * In this case we create more than one Assessments of the one type and check, that is possible to choose it on creating Career Readiness Certifications.
 * Created by a.vnuchko on 25.09.2015.
 */

@TestCase(id = "WINGS-10973")
public class TC_20_05_CRC_Create_PrePost_Assessments extends BaseWingsTest {

    public void main() {


        info("Generate test data");
        String date = CommonFunctions.getDaysAgoDate(Constants.DAYS_IN_PAST);
        String secondDate = CommonFunctions.getDaysAgoDate(Constants.SECOND_TEST_DATE);

        info("Precondition: 1. There are some pretests/posttests Applied Mathematics W-P WorkKeys Assessments for the participant.\n"
                + "2. There are some pretests/posttests Locating Information W-P WorkKeys Assessments for the participant.\n"
                + "3. There are some pretests/posttests Reading for Information W-P WorkKeys Assessments for the participant.");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Boolean.TRUE, Boolean.FALSE);
        crc.getAppliedMathematics().setDateAdministered(date);
        crc.getLocatingInformation().setDateAdministered(date);
        crc.getReadingForInformation().setDateAdministered(date);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, crc.getAppliedMathematics());
        AssessmentSteps.createAssessment(staff, crc.getLocatingInformation());
        AssessmentSteps.createAssessment(staff, crc.getReadingForInformation());

        crc.getAppliedMathematics().setDateAdministered(secondDate);
        crc.getReadingForInformation().setDateAdministered(secondDate);
        crc.getLocatingInformation().setDateAdministered(secondDate);
        AssessmentSteps.createAssessment(staff, crc.getAppliedMathematics());
        AssessmentSteps.createAssessment(staff, crc.getLocatingInformation());
        AssessmentSteps.createAssessment(staff, crc.getReadingForInformation());

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();

        logStep("Verify the following fields: - Applied Mathematics Assessment, - Locating Information Assessment, - Reading for Information Assessment.");


        logResult("Fields displayed correctly. Appropriate drop-down lists contain all pretests/posttests WP WorkKeys Assessments from the precondition. (no matter what type it is a test - pretest or posttest)\n"
                + "User is able to select any Assessments for the CRC creation.");
        creationPage.selectAssessments(crc.getAppliedMathematicsString(), CareerReadinessCertificationBaseForm.AssessmentTypes.MATH);
        creationPage.selectAssessments(crc.getLocatingInformationString(), CareerReadinessCertificationBaseForm.AssessmentTypes.LOC);
        creationPage.selectAssessments(crc.getReadingForInformationString(), CareerReadinessCertificationBaseForm.AssessmentTypes.READ);
    }
}
