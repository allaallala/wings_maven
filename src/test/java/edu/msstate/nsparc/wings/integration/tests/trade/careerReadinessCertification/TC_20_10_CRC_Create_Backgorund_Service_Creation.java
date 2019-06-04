package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create new CRC and verify that "Career Readiness Certification" service was created for the chosen participant.
 * Created by a.vnuchko on 22.09.2015.
 */

@TestCase(id = "WINGS-10978")
public class TC_20_10_CRC_Create_Backgorund_Service_Creation extends BaseWingsTest {

    public void main(){


        info("Generate some data for CRC");
        CareerReadinessCertification crc = CRCSteps.generateCrcCommonData();
        String program = "Wagner-Peyser";
        String serviceName = "Career Readiness Certification";
        String staffMember = "Auto Staff";

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);

        logStep("Click the [Create] button");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.finishCreating();

        logStep("Go to Participants -> Participant Service Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Find a participant for which the CRC record was created");
        ParticipantEnrollmentSearchForm searchPage = new ParticipantEnrollmentSearchForm();
        searchPage.selectProgram(program);
        searchPage.performSearch(crc.getParticipant(), serviceName);

        CRCSteps.findPartipEnrollment(program, crc, serviceName, staffMember);

        logResult("'Career Readiness Certification' background service was created for the participant. The service associated with Wagner-Peyser program.");
    }
}
