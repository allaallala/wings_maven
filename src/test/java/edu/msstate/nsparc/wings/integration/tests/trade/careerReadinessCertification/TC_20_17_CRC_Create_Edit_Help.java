package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open CRC Edit form, check, that all data in the help section is correct.
 * Created by a.vnuchko on 23.09.2015.
 */

@TestCase(id = "WINGS-10984")
public class TC_20_17_CRC_Create_Edit_Help extends BaseWingsTest {

    public void main() {


        info("Precondition: Create some Career Readiness Certification");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        CRCSteps.openDetailedParticipantPageCRC(crc);

        logStep("Click the [Edit] button");
        new CareerReadinessCertificationDetailsForm().clickButton(Buttons.Edit);

        logStep("Click [Help] button to open Help section");
        CareerReadinessCertificationEditForm editPage = new CareerReadinessCertificationEditForm();
        editPage.openHelpBlock();

        logResult("Help section opened and contains the correct information ");
        editPage.validateHelpInformation();
    }
}
