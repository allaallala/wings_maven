package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.*;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


// Author: d.poznyak

@TestCase(id = "WINGS-10627")
public class TC_05_12_Participant_Create_Regular extends BaseWingsTest {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        commonSteps(participant, Constants.FALSE);

    }

    /**
     * Contains all steps to reproduce in the test
     *
     * @param participant - participant
     * @param veteran     - if participant is veteran (true, false)
     */
    protected void commonSteps(Participant participant, Boolean veteran) {
        logStep("On the first page select No in 'Is the participant a veteran' radio buttons group ");
        ParticipantCreationForm participantCreationForm = ParticipantNavigationSteps.openParticipantCreationPage(Roles.STAFF);
        new LookupProfileForm().fillFirstPageAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new PersonalInformationForm().fillSecondPageAndContinue(participant, veteran, Constants.FALSE);


        logStep("Fill in all required fields");
        if (veteran) {
            new MilitaryInformationForm().fillInEligibleVeteranAndContinue();
        }

        new ContactInformationForm().fillContactInformationAndContinue(participant);
        new AdditionalInformationForm().fillFourthPageNotDriverAndContinue();
        new AcademicHistoryForm().selectParticipantGradeAndContinue(participant);
        new CertificationsForm().skipCertificationsSection();
        new EmploymentHistoryForm().skipEmploymentHistorySection();
        new EmploymentPreferencesForm().clickContinue();
        logStep("Select Yes in Unemployment Services System account radio buttons group and enter correct username");
        new AccessUsernameForm().fillMsAccessAccountAndSave(participant);


        logStep("Find new created participant record via searching participants records");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearch(participant);

        logStep("Checking that Participant isn't Veteran");
        participantSearchForm.clickOnSearchResult(participant);
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        if (veteran) {
            CustomAssertion.softAssertEquals(detailsForm.getVeteranText(), Constants.YES_ANSWER, "Veteran assert fail");
        } else {
            CustomAssertion.softAssertContains(detailsForm.getVeteranText(), Constants.NO_ANSWER, "Veteran assert fail");
        }
    }
}
