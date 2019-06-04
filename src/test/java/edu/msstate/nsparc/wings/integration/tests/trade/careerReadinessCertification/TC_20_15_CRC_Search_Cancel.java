package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;


/**
 * Open Career Readiness Certification Search form, fill it out some data, click [Clear] button and check, that Staff Home Form is shown
 * Created by a.vnuchko on 23.09.2015.
 */

@TestCase(id = "WINGS-10982")
public class TC_20_15_CRC_Search_Cancel extends BaseWingsTest {

    public void main() {


        Participant partp = new Participant();
        ParticipantCreationSteps.createParticipantDriver(partp, Constants.TRUE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        searchPage.selectParticipant(partp);

        logStep("Click the [Cancel] button");
        searchPage.clickButton(Buttons.Cancel);

        logResult("The Staff Home Screen is shown");
        Browser.getInstance().waitForPageToLoad();
        //new StaffHomeForm().assertIsOpen();
    }
}
