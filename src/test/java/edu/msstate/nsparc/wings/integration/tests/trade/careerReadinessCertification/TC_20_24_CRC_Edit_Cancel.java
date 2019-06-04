package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create some Career Readiness Certification, open its edit page and change some value, cancel edition and check,that changes are not saved.
 * Created by a.vnuchko on 24.09.2015.
 */

@TestCase(id = "WINGS-10990")
public class TC_20_24_CRC_Edit_Cancel extends BaseWingsTest {

    public void main() {


        info("Generate test data");
        String date = CommonFunctions.getDaysAgoDate(Constants.DAYS_IN_PAST);

        info("Precondition: Create some Career Readiness Certification");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        CRCSteps.openDetailedParticipantPageCRC(crc);

        logStep("Click the [Edit] button");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Edit any parameters");
        CareerReadinessCertificationEditForm editPage = new CareerReadinessCertificationEditForm();
        editPage.inputDataAdministered(date);

        logStep("Click the [Cancel] button");
        detailsPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);

        logResult("The Career Readiness Certification Detail Screen is shown, the changes are not saved");
        detailsPage.validateCareerReadinessCertificationInformation(crc);
    }
}
