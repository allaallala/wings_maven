package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10635")
public class TC_05_42_Participant_Quick_Referrals extends BaseWingsTest {

    private String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);


    public void main() {

        info("Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);
        info("Employer");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        employer.setAddress(participant.getAddress());
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        info("Job Order");
        JobOrderSteps.createJobOrder(employer, jobTitle);

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Select some records and open it");
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);
        participantSearchForm.clickOnSearchResult(participant);
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();

        logStep("Select link Quick referrals available and check, that window with Jobs is shown");
        detailsForm.showQuickReferrals();
    }
}
