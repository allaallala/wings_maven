package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import org.testng.Assert;


@TestCase(id = "WINGS-10652")
public class TC_06_05_Participant_Enrollment_Create_Progress extends TC_06_01_Participant_Enrollment_Create {


    public void main() {

        logStep("Creating Participant and Service for using in test");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, false, false);

        logStep("Select participant");
        ParticipantEnrollmentCreationForm creationForm = openCreationFormInputDate(participant, false);

        logStep("Select Ended - No status");
        creationForm.chooseEndedService(false);

        logStep("Create");
        creationForm.clickButton(Buttons.Create);
        creationForm.clickButton(Buttons.Done);

        logStep("Find created service");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantEnrollmentSearchForm enrollmentSearchForm = new ParticipantEnrollmentSearchForm();
        enrollmentSearchForm.performSearch(participant, SERVICE_NAME);

        logStep("Check search results");
        enrollmentSearchForm.checkSomeFields(participant.getNameForSearchPages(), SERVICE_NAME, CREATION_DATE);

        //Steps for WINGS-3698
        logStep("Open Participant search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Participant");
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearch(participant);

        logStep("Open search result");
        searchForm.openFirstSearchResult();

        logStep("Make sure no errors are displayed");
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        Assert.assertTrue(detailsForm.getLinkTitle().contains(participant.getFirstName()));
        Assert.assertTrue(detailsForm.getLinkTitle().contains(participant.getLastName()));
    }
}
