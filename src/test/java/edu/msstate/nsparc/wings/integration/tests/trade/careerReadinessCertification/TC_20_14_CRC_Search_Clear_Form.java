package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open Career Readiness Certification Search form, fill it out some data, click [Clear] button and check, that all entries made are cleared.
 * Created by a.vnuchko on 23.09.2015.
 */

@TestCase(id = "WINGS-10981")
public class TC_20_14_CRC_Search_Clear_Form extends BaseWingsTest {

    public void main() {


        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Boolean.TRUE, Boolean.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        searchPage.selectParticipant(partp);

        logStep("Click the [Clear Form] button");
        searchPage.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        searchPage.clickAndWait(BaseWingsForm.BaseButton.PARTICIPANT_LOOK_UP);
    }
}
