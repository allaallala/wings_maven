package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.BaseElement;


@TestCase(id = "WINGS-10522")
public class TC_01_36_WIA_Enrollment_Edit_Cancel extends TC_01_32_WIA_Enrollment_Edit_One_Parameter {

    private static final String NEW_CONTACT_PERSON = "Automation Test";
    private static final String NEW_RELATION_TO_PARTICIPANT = "Automation Test Relation";
    String WIB = "National Emergency Grant (NEG)";
    String description = "Tornado 2011";


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, false);

        openWiaEnrollmentDetails(participant);
        BaseParticipantDetailsForm participantDetailsForm = new BaseParticipantDetailsForm();

        logStep("Click Edit");
        participantDetailsForm.openWiaEnrollmentDetailsForm();
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();
        wiaEnrollmentDetailsForm.clickAndWait(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION);

        logStep("Edit some parameters");
        WIAEnrollmentEditForm editForm = new WIAEnrollmentEditForm();
        editForm.inputContactPerson(NEW_CONTACT_PERSON);
        editForm.inputApplicationDate(CommonFunctions.getYesterdayDate());
        editForm.inputParticipantRelation(NEW_RELATION_TO_PARTICIPANT);

        //sub-task 3025
        logStep("Check that Tornado 2011 shows up in the National Emergency Grant drop down");
        editForm.selectWIB(WIB);
        editForm.waitCheckNEG(description);

        logStep("Click on Cancel button");
        editForm.clickButton(Buttons.Cancel);
        editForm.areYouSure(Popup.Yes);

        logStep("Search changed WIA Enrollment and be sure that all changes are NOT saved");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        WIAEnrollmentSearchForm searchForm = new WIAEnrollmentSearchForm();
        searchForm.performSearch(participant);
        searchForm.openFirstSearchResult();

        logStep("Check, that changes aren't saved");
        CustomAssertion.softNotSame(wiaEnrollmentDetailsForm.getContactPersonText(), NEW_CONTACT_PERSON, "Changes are saved");
        CustomAssertion.softNotSame(wiaEnrollmentDetailsForm.getApplicationDate(), CommonFunctions.getYesterdayDate(),"sertNotSame(Changes are saved");
        CustomAssertion.softNotSame(wiaEnrollmentDetailsForm.getRelationshipParticipantText(), NEW_RELATION_TO_PARTICIPANT,"Changes are saved");

        BaseNavigationSteps.logout();
        logEnd();
    }
}
