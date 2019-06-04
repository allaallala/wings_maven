package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationEditForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10454")
public class TC_10_70_Career_Readiness_Certification_Full_Workflow extends BaseWingsTest {


    public void main() {

        CareerReadinessCertification crc = new CareerReadinessCertification();
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Boolean.TRUE, Boolean.FALSE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, crc.getAppliedMathematics());
        AssessmentSteps.createAssessment(staff, crc.getLocatingInformation());
        AssessmentSteps.createAssessment(staff, crc.getReadingForInformation());

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Create);

        logStep("Fill out the CRC creation form");
        CareerReadinessCertificationCreationForm creationForm = new CareerReadinessCertificationCreationForm();
        creationForm.fillCRCInformation(crc);

        logStep("Click Create");
        creationForm.finishCreating();

        logStep("Navigate to Participants - Career Readiness Certifications - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Find created CRC record");
        CareerReadinessCertificationSearchForm searchForm = new CareerReadinessCertificationSearchForm();
        searchForm.performSearchAndOpen(crc);

        logStep("Validate that correct information is displayed");
        CareerReadinessCertificationDetailsForm detailsForm = new CareerReadinessCertificationDetailsForm();
        detailsForm.validateCareerReadinessCertificationInformation(crc);

        logStep("Click Edit");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit one parameter");
        crc.setDateAdministired(CommonFunctions.getYesterdayDate());
        CareerReadinessCertificationEditForm editForm = new CareerReadinessCertificationEditForm();
        editForm.inputDataAdministered(crc.getDateAdministired());
        editForm.finishEditing();

        logStep("Validate that changes are saved");
        detailsForm.validateCareerReadinessCertificationInformation(crc);
    }
}

