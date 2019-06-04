package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Open participant service enrollment search page and click [Search]. Check, quantity of the records on the page and in
 * the database are the same.
 * Created by a.vnuchko on 09.03.2017.
 */

@TestCase(id = "WINGS-11251")
public class TC_38_11_Participant_Enrollment_Search_Participant extends TC_38_09_Participant_Enrollment_Create_Cancel {

    public void main() {
        String creationDate = CommonFunctions.getCurrentDate();
        String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
        String status = Constants.COMPLETED;
        info("Precondition: create participant and service first");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());

        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, false, false);
        User admin = new User(Roles.ADMIN);
        ServiceSteps.createParticipantServiceEnrollment(admin, participant, SERVICE_NAME, status, creationDate);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Search);

        ParticipantEnrollmentSearchForm searchForm = new ParticipantEnrollmentSearchForm();
        searchForm.clickButton(Buttons.Search);

        logStep("Choose a participant and click [Search]");
        searchForm.performSearch(participant, SERVICE_NAME);

        logResult("All Enrollment Services for this participant are shown");
        searchForm.validateFirstSearchResult(participant, SERVICE_NAME, creationDate, status);

        logStep("Select some status and click [Search]");
        searchForm.removeSearchedParticipant();
        searchForm.removeSearchedService();
        searchForm.selectStatus(status);
        searchForm.clickButton(Buttons.Search);

        logResult("All Enrollment Services with status you have selected are shown");
        //TODO

        logStep("Select some Result");
        searchForm.selectResult(status);
        searchForm.clickButton(Buttons.Search);

        logResult("All Enrollment Services with result you have selected are shown");
        //TODO
    }
}
