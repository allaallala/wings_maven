package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create some CRC, open its Edit page. Edit 'Date Administred' with correct value and check, that changes are saved.
 * Created by a.vnuchko on 23.09.2015.
 */

@TestCase(id = "WINGS-10987")
public class TC_20_20_CRC_Edit_Correct_Date_Administred extends BaseWingsTest {

    public void main() {


        info("Generate test data");
        String date = CommonFunctions.getDaysAgoDate(Constants.DAYS_IN_PAST);

        info("Precondition: Create some Career Readiness Certification");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        CRCSteps.openDetailedParticipantPageCRC(crc);

        CRCSteps.editCorrectDateAdministred(crc, date);

        logResult("The Career Readiness Certification Detail Screen is shown. The changes are saved");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.passParticipationRecalculationPage();
        detailsPage.validateCareerReadinessCertificationInformation(crc);
    }
}
