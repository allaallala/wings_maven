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
 * Edit data administred for CRC, open participant participantSSDetails page connected with crc chosen and check new date administred.
 * Created by a.vnuchko on 24.09.2015.
 */

@TestCase(id = "WINGS-10988")
public class TC_20_21_CRC_Create_Background_Data_Changes extends BaseWingsTest {

    public void main() {


        info("Generate test data");
        String date = CommonFunctions.getDaysAgoDate(Constants.DAYS_IN_PAST);
        String program = "Wagner-Peyser";
        String serviceName = "Career Readiness Certification";
        String staffMember = "Auto Staff";

        info("Precondition: Create some Career Readiness Certification");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        CRCSteps.openDetailedParticipantPageCRC(crc);

        CRCSteps.editCorrectDateAdministred(crc, date);
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.passParticipationRecalculationPage();

        CRCSteps.findPartipEnrollment(program, crc, serviceName, staffMember);

        logResult("'Career Readiness Certification' service date was changed, and it is the same as changed CRC created date"
                + " The exit participation period date was correctly rebooted also");


    }
}
