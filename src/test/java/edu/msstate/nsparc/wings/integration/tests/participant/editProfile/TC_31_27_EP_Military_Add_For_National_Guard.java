package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Created by a.korotkin on 4/12/2017.
 */
@TestCase(id = "WINGS-11175")
public class TC_31_27_EP_Military_Add_For_National_Guard extends BaseWingsTest {

    private String militaryBranch = "Army";
    private String dischargeType = "Honorable";
    private String serviceDate = CommonFunctions.getCurrentDate();

    public void main() {
        Participant participant = precondition();

        logStep("Go to the Military History section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        info("If we have an existing military record, it should be deleted");
        detailsPage.removeMilitaryRecordPresent();

        logStep("Add new one");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.ADD_MILITARY);
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.MILITARY);
        editPage.addMilitaryRecord(militaryBranch, dischargeType, serviceDate, CommonFunctions.getDaysNextDate(serviceDate, 1));

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        ParticipantDetailSteps.validateMilitary(militaryBranch, dischargeType
                + " Discharge", serviceDate, CommonFunctions.getDaysNextDate(serviceDate, 1));
    }


    /**
     * Make 'before' actions - create a participant
     */
    protected Participant precondition() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();

        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.FALSE, Constants.TRUE);
        return participant;
    }

    /**
     * Open participant S-S detail form
     *
     * @param participant - participant to open
     * @return new participant participantSSDetails form.
     */
    protected BaseParticipantSsDetailsForm openParticipantDetail(Participant participant) {
        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Navigate to My Profile");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.openMyProfile();

        return new BaseParticipantSsDetailsForm(participant);
    }
}
