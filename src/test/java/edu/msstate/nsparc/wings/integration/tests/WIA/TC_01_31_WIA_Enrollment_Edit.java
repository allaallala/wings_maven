package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10520")
public class TC_01_31_WIA_Enrollment_Edit extends BaseWingsTest {
    String applicationDate = CommonFunctions.getYesterdayDate();
    String contactPerson = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    String contactPhone = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
    String relation = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);


    public void main() {
        TC_01_10_WIA_Enrollment_Create createWIAEnrollment = new TC_01_10_WIA_Enrollment_Create();
        Participant participant = createWIAEnrollment.createWIAE();

        logStep("Participants->Participant Records->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Perform search for participant");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);
        participantSearchForm.openFirstSearchResult();

        logStep("Open Participant participantSSDetails form");
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();
        participantDetailsForm.openWiaEnrollmentDetailsForm();

        logStep("Click [Edit] button");
        new WIAEnrollmentDetailsForm().clickAndWait(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION);

        logStep("Edit some data and click [Save Changes]");
        WIAEnrollmentEditForm wiaEnrollmentEditForm = new WIAEnrollmentEditForm();
        wiaEnrollmentEditForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation);
        wiaEnrollmentEditForm.clickButton(Buttons.Save);

        logStep("Check, that changes are saved");
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        assertTrue("Assert Edited WIA Enrollment Basic Information Failed",
                wiaEnrollmentDetailsForm.validateBasicInformation(applicationDate, contactPerson, relation));
    }
}
