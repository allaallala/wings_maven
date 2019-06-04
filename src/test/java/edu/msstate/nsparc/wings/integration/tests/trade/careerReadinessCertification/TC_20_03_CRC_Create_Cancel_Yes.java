package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open and fill out career readiness certification form, click [Cancel] button and confirm it. Check, that new crc is not created.
 * Created by a.vnuchko on 21.09.2015.
 */

@TestCase(id = "WINGS-10971")
public class TC_20_03_CRC_Create_Cancel_Yes extends BaseWingsTest {

    public void main() {
        info("Generate some data for CRC");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Constants.TRUE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Create);

        logStep("Fill out some fields");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.selectParticipant(crc.getParticipant());

        logStep("Click the [Cancel] button");
        creationPage.clickButton(Buttons.Cancel);

        logStep("Click [Yes] button in the pop up");
        creationPage.areYouSure(Popup.Yes);

        logResult("The Staff Home screen is shown. A new Career Readiness Certification isn't created");
        BaseNavigationSteps.logout();

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);
        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        searchPage.performSearch(crc);
        searchPage.noSearchResults();
    }
}
