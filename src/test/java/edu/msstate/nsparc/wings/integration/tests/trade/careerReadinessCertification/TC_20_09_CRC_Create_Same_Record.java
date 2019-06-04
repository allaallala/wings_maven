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
 * Open CRC Creation Form, fill out with correct data, create CRC, and click [Create another]. Check, that is impossible to select Assessments.
 * Created by a.vnuchko on 22.09.2015.
 */

@TestCase(id = "WINGS-10977")
public class TC_20_09_CRC_Create_Same_Record extends BaseWingsTest {

    public void main(){

        info("Generate some data for CRC");
        CareerReadinessCertification crc = CRCSteps.generateCrcCommonData();

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);

        logStep("Click the [Create] button");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.finishCreating();

        logStep("Click the [Create another] button");
        new CareerReadinessCertificationDetailsForm().clickButton(Buttons.CreateAnother);

        logStep("Verify that Assessments, used in Step 5 can not be used again during the new record creation");
        logResult("The Career Readiness Certification creation page displayed. There is no possibility to select Assessments, " +
                "which were already used during the another CRC creation");
        creationPage.assessmentsIsNotAvailable();
    }
}
