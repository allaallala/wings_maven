package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10621")
public class TC_05_07_Participant_Service_Enrollment_Custom_Question_In_Progress extends BaseWingsTest {

    private static final String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final String QUESTION = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
    private static final String CREATION_DATE = CommonFunctions.getCurrentDate();
    private static final String ERROR_MESSAGE = "An answer is required";


    //sub-task WINGS-2761
    public void main() {

        info("Creating Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);

        divideMessage("Create Service with Question");
        ServiceSteps.createServiceWithQuestion(SERVICE_NAME, QUESTION);

        ParticipantEnrollmentCreationForm creationForm = ParticipantNavigationSteps.openFillEnrlCreationPage(participant, SERVICE_NAME);

        logStep("Select Creation date and 'In Progress' status");
        creationForm.chooseScheduledService(false);
        creationForm.inputCreationDate(CREATION_DATE);
        creationForm.chooseEndedService(false);

        logStep("Don't answer the question, click on Create");
        creationForm.clickButton(Buttons.Continue);
        creationForm.clickButton(Buttons.Create);

        logStep("Check that Service is created");
        creationForm.clickButton(Buttons.Done);

        logStep("Open Enrollment search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for created enrollment");
        ParticipantEnrollmentSearchForm searchForm = new ParticipantEnrollmentSearchForm();
        searchForm.performSearch(participant, SERVICE_NAME);

        logStep("Open Enrollment");
        searchForm.openFirstSearchResult();
        ParticipantEnrollmentDetailsForm detailsForm = new ParticipantEnrollmentDetailsForm();

        logStep("Click on Edit");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Don't set result and try to save changes");
        ParticipantEnrollmentEditForm editForm = new ParticipantEnrollmentEditForm();
        editForm.clickButton(Buttons.Continue);
        editForm.clickButton(Buttons.Save);

        logStep("Check that Service is saved");
        CustomAssertion.softTrue("Service wasn't saved", editForm.isButtonPresent(BaseWingsForm.BaseButton.DONE));

        logStep("Open Enrollment again");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Set Result and Result date");
        creationForm.chooseEndedService(true);
        creationForm.inputDateResult(CREATION_DATE);
        creationForm.selectResult(Constants.COMPLETED);
        editForm.clickButton(Buttons.Continue);

        logStep("Try to save Enrollment");
        editForm.clickButton(Buttons.Save);

        logStep("Check that error message is displayed");
        CustomAssertion.softTrue("Error message wasn't displayed", editForm.getErrorText().contains(ERROR_MESSAGE));

        logStep("Answer the question and try to save enrollment");
        editForm.answerQuestion(QUESTION);
        editForm.clickButton(Buttons.Save);

        logStep("Check that Service is saved");
        CustomAssertion.softTrue("Service wasn't saved",editForm.isButtonPresent(BaseWingsForm.BaseButton.DONE));

        logEnd();
    }
}
