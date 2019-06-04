package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 20.08.2015.
 * Create new CRC and check, that is created and displayed correcty.
 */
@TestCase(id = "WINGS-10969")
public class TC_20_01_CRC_Create_With_All_Fields extends BaseWingsTest {

    public void main() {


        info("Generate some data for CRC");
        CareerReadinessCertification crc = CRCSteps.generateCrcCommonData();

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);

        logStep("Click the [Create] button");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.finishCreating();

        logResult("The Career Readiness Certification Detail Screen is shown. "
                + "A new Career Readiness Certification was created and contains the same data you have entered");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.validateCareerReadinessCertificationInformation(crc);
    }
}
