package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10540")
public class TC_02_02_WIA_Enrollment_Check_Dates extends BaseWingsTest {

    private String lastDayTraining = CommonFunctions.getCurrentDate();
    private String enrollmentText = "Not yet ended";


    //Bug WINGS-WINGS-2371 , sub-task WINGS-WINGS-2411
    public void main () {

        info("We need to create participant,WIA Enrollment, WIA Training and complete it");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        TrainingSteps.createWiaTrainingWithUnregisteredParticipant(participant);
        TrainingSteps.editWIATraining(participant, Constants.COMPLETED, lastDayTraining);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Select some data for searching(1 parameter)->Search");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);
        logStep("Open Participant record");
        participantSearchForm.openFirstSearchResult();
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        logStep("Validate WIA Enrollment dates");
        detailsForm.expandWIAEnrollments();
        CustomAssertion.softTrue("Incorrect current date", detailsForm.getWiaEnrrlDetailsText().contains(String.format("Started: %1$s", CommonFunctions.getCurrentDate())));
        CustomAssertion.softTrue("Date has already finished", detailsForm.getWiaEnrrlDetailsText().contains(enrollmentText));
    }
}
