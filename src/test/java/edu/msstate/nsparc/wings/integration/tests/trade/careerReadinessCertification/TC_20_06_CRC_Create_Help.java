package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open CRC Creation form, open [Help] section and check, that all fields display correctly
 * Created by a.vnuchko on 22.09.2015.
 */

@TestCase(id = "WINGS-10974")
public class TC_20_06_CRC_Create_Help extends BaseWingsTest {

    public void main(){
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Create);

        logStep("Click [Help] button to open Help section");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.openHelpBlock();

        logStep("Verify that 'Description' and 'Scoring Tables' sections information correctness");
        logResult("All information displayed correctly.");
        creationPage.validateHelpInformation();

    }
}
