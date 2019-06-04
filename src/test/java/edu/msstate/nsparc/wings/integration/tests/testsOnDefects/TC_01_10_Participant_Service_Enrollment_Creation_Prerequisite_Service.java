package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10509")
public class TC_01_10_Participant_Service_Enrollment_Creation_Prerequisite_Service extends BaseWingsTest {

    //service name that requires a prerequisite service
    private static final String SERVICE_NAME_REQUIRED_PRERQUISITE = "Automation Prerequisite Service";
    private static final String SERVICE_NAME = "Automation Required Service";
    private String creationDate = CommonFunctions.getYesterdayDate();


    //sub-task WINGS-2410
    public void main() {
        String errorText = "This participant cannot be enrolled";
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createRegularParticipant(participant, Boolean.TRUE);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Create);

        logStep("Select participant");
        ParticipantEnrollmentCreationForm creationForm = new ParticipantEnrollmentCreationForm();
        creationForm.selectParticipant(participant);

        logStep("Select service");
        creationForm.selectService(SERVICE_NAME_REQUIRED_PRERQUISITE);

        logStep("Fill out other information");
        // selecting date and STATUS
        creationForm.chooseScheduledService(false);
        creationForm.inputCreationDate(creationDate);

        creationForm.chooseEndedService(true);
        creationForm.inputDateResult(creationDate);
        creationForm.selectResult(Constants.COMPLETED);

        logStep("Create");
        creationForm.clickButton(Buttons.Create);
        //Check validation message
        CustomAssertion.softTrue("Validation message is not visible", creationForm.getServiceErrorText().contains(errorText));
        logStep("Select service");
        //selecting required service
        creationForm.selectService(SERVICE_NAME);

        logStep("Create");
        creationForm.clickButton(Buttons.Create);
        creationForm.passParticipationRecalculationPage();

        logStep("Check infomation added");
        ParticipantEnrollmentCreationForm creForm = new ParticipantEnrollmentCreationForm();
        CustomAssertion.softTrue("Incorrect service name", creForm.getServicesText().contains(SERVICE_NAME));
        CustomAssertion.softTrue("Incorrect service name prerequisite", creForm.getServicesText().contains(SERVICE_NAME_REQUIRED_PRERQUISITE));
    }
}
