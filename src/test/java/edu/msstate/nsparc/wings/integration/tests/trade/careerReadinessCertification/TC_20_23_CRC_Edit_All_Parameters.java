package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationBaseForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create career readiness certification, edit all parameters and save, check that changes are saved.
 * Created by a.vnuchko on 20.10.2015.
 */

@TestCase(id = "WINGS-10989")
public class TC_20_23_CRC_Edit_All_Parameters extends BaseWingsTest {

    public void main() {
        info("Generate test data");
        String date = CommonFunctions.getDaysAgoDate(Constants.DAYS_IN_PAST);
        String secondDate = CommonFunctions.getDaysAgoDate(Constants.SECOND_TEST_DATE);

        info("Precondition: Create some career readiness certification");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Boolean.TRUE, Boolean.FALSE);
        crc.getAppliedMathematics().setDateAdministered(date);
        crc.getLocatingInformation().setDateAdministered(date);
        User staff = new User(Roles.STAFF);
        crc.getReadingForInformation().setDateAdministered(date);
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
        creationPage.finishCreating();
        BaseNavigationSteps.logout();

        CRCSteps.openDetailedParticipantPageCRC(crc);

        logStep("Click the [Edit] button");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Edit all the parameters");
        CareerReadinessCertificationEditForm editPage = new CareerReadinessCertificationEditForm();
        crc.setDateAdministired(date);
        editPage.inputDataAdministered(date);
        crc.getAppliedMathematics().setDateAdministered(date);
        crc.getLocatingInformation().setDateAdministered(date);
        crc.getReadingForInformation().setDateAdministered(date);
        editPage.selectAssessments(crc.getAppliedMathematicsString(), CareerReadinessCertificationBaseForm.AssessmentTypes.MATH);
        editPage.selectAssessments(crc.getLocatingInformationString(), CareerReadinessCertificationBaseForm.AssessmentTypes.LOC);
        editPage.selectAssessments(crc.getReadingForInformationString(), CareerReadinessCertificationBaseForm.AssessmentTypes.READ);

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult("The Career Readiness Certification Detail Screen is shown. The changes are saved");
        detailsPage.passParticipationRecalculationPage();
        detailsPage.validateCareerReadinessCertificationInformation(crc);
    }
}
