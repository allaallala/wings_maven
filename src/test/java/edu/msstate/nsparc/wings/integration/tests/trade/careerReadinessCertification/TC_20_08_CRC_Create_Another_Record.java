package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open CRC Creation Form, fill out with correct data, create CRC, and click [Create another]. Check, that CRC form is displayed.
 * Created by a.vnuchko on 22.09.2015.
 */

@TestCase(id = "WINGS-10976")
public class TC_20_08_CRC_Create_Another_Record extends BaseWingsTest {

    public void main(){


        info("Generate some data for CRC");
        CareerReadinessCertification crc = CRCSteps.generateCrcCommonData();

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);

        logStep("Click the [Create] button");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.finishCreating();

        logStep("Click the [Create another] button");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.clickButton(Buttons.CreateAnother);

        logResult("The Career Readiness Certification creation page displayed.");
    }
}
