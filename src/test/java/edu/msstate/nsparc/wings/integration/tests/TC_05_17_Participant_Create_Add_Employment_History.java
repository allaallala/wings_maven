package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation.*;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import org.testng.Assert;


@TestCase(id = "WINGS-10630")
public class TC_05_17_Participant_Create_Add_Employment_History extends BaseWingsTest {

    public void main() {


        Participant participant = new Participant(AccountUtils.getParticipantAccount());

        logStep("Fill in all required fields");
        ParticipantNavigationSteps.openParticipantCreationPage(Roles.STAFF);
        new LookupProfileForm().fillFirstPageAndContinue(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());
        new PersonalInformationForm().fillSecondPageAndContinue(participant, Constants.FALSE, Constants.FALSE);
        new ContactInformationForm().fillThirdPage(participant);
        new AdditionalInformationForm().fillFourthPageNotDriverAndContinue();

        logStep("Go to the page about Employment history");
        new AcademicHistoryForm().selectParticipantGradeAndContinue(participant);
        new CertificationsForm().skipCertificationsSection();

        logStep("Add some old places of work");
        new EmploymentHistoryForm().addEmploymentHistory();
        ParticipantAddEmploymentForm participantAddEmploymentForm = new ParticipantAddEmploymentForm();
        participantAddEmploymentForm.addRecordStaffAndContinue();
        new EmploymentPreferencesForm().clickContinue();

        logStep("Create");
        new AccessUsernameForm().fillMsAccessAccountAndSave(participant);


        logStep("Find new created participant record via searching participants records");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearch(participant);

        logStep("Check Participant Employment History");
        searchForm.clickOnSearchResult(participant);
        BaseParticipantDetailsForm detailsForm = new BaseParticipantDetailsForm();
        detailsForm.expandEmploymentHistory();
        Assert.assertTrue(detailsForm.getPreviousJobPageText().contains("Cook at Automation"));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
