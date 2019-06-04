package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Check, that is possible to create career readiness certification with the date in the past.
 * Created by a.vnuchko on 21.09.2015.
 */

@TestCase(id = "WINGS-10972")
public class TC_20_04_CRC_Create_Date_Administred_Correct extends BaseWingsTest {

    public void main() {


        info("Generate some data for CRC");
        String date = CommonFunctions.getDaysAgoDate(Constants.DAYS_IN_PAST);
        CareerReadinessCertification crc = CRCSteps.generateCrcCommonData();

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);

        logStep("Input Date Administered from the past but not more than a year in the past (includes today)");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.inputDataAdministered(date);
        crc.setDateAdministired(date);

        logStep("Click the [Create] button");
        creationPage.finishCreating();

        logResult("The Career Readiness Certification Detail Screen is shown. " +
                "A new Career Readiness Certification was created and contains the same data you have entered");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.validateCareerReadinessCertificationInformation(crc);

    }
}
