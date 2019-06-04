package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.*;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Login with this account and create a participant.
 * Created by a.vnuchko on 16.01.2017.
 */

@TestCase(id = "WINGS-10416")
public class TC_36_01_Account_General_Create extends BaseWingsTest {

    public void main() {
        fillParticipantInfoDontComplete(true, false);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        logStep("Fill in all required fields and Create");
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logResult("New account is created, it is possible to login into the WINGS and work");
        BaseNavigationSteps.loginParticipant();
        new ParticipantHomePage(Constants.PARTICIPANT_SS);
    }

    protected void fillParticipantInfoDontComplete(Boolean isVeteran, Boolean isNationalGuard) {
        Participant participant = new Participant();
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Create);

        new LookupProfileForm().fillFirstPageAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new PersonalInformationForm().fillSecondPageAndContinue(participant, isVeteran, isNationalGuard);

        if (isVeteran) {
            new MilitaryInformationForm().fillInEligibleVeteranAndContinue();
        }

        if (isNationalGuard) {
            new MilitaryInformationForm().fillInNotEligibleVeteranAndContinue();
        }

        new ContactInformationForm().fillThirdPage(participant);


        new AdditionalInformationForm().fillFourthPageNotDriverAndContinue();
        new AcademicHistoryForm().selectParticipantGradeAndContinue(participant);
        new CertificationsForm().skipCertificationsSection();
        new EmploymentHistoryForm().skipEmploymentHistorySection();
        new EmploymentPreferencesForm().clickContinue();
        new AccessUsernameForm().fillMsAccessAccount(participant);

        BaseNavigationSteps.logout();
    }
}
