package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create new career readiness certification, find participant and open its participantSSDetails page and check exit period date
 * Created by a.vnuchko on 20.10.2015.
 */
@TestCase(id = "WINGS-10443")
public class TC_20_11_CRC_Reset_Exit_Period_date extends BaseWingsTest {

    public void main() {
        info("Generate some data for CRC");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Boolean.TRUE, Boolean.FALSE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, crc.getAppliedMathematics());
        AssessmentSteps.createAssessment(staff, crc.getLocatingInformation());
        AssessmentSteps.createAssessment(staff, crc.getReadingForInformation());

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);

        logStep("Click the [Create] button");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.finishCreating();

        logStep("Go to Participants -> Participant Profiles");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Find a participant for which the CRC record was created and open it participantSSDetails page");
        ParticipantSearchForm searchPage = new ParticipantSearchForm();
        searchPage.performSearchAndOpen(crc.getParticipant());

        logStep("Expand the 'Participation Periods' section");
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandParticipationPeriods();

        logStep("Check the exit participation period date");
        logResult("The exit participation period date was correctly rebooted");
        detailsPage.validateParticipantPerios();
    }
}
