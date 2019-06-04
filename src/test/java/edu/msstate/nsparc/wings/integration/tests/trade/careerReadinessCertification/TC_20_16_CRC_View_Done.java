package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Create some CRC, find it using [Search] form, open Participant Details page, choose [Done] and check, that information on the [Search] page
 * Created by a.vnuchko on 23.09.2015.
 */

@TestCase(id = "WINGS-10983")
public class TC_20_16_CRC_View_Done extends BaseWingsTest {

    public void main() {


        info("Generate test data");
        String staffMember = "Auto Staff";

        info("Precondition: Create some Career Readiness Certification");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        CRCSteps.openDetailedParticipantPageCRC(crc);

        logStep("Click [Done] button");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.clickButton(Buttons.Done);

        String result = detailsPage.getResult(crc);

        logResult("The Career Readiness Certification Search Screen with correct and actual information is shown");
        SearchResultsForm resultsForm = new SearchResultsForm();
        CustomAssertion.softTrue("First column does not contain participant first name", BaseWingsForm.BaseOtherElement.PARTICIPANT.getElement().getText().contains(crc.getParticipant().getFirstName()));
        CustomAssertion.softTrue("Second column does not contain participant first name", resultsForm.getFirstRowRecordText(2).contains(crc.getParticipant().getFirstName()));
        CustomAssertion.softTrue("Third column does not contain data administred", resultsForm.getFirstRowRecordText(3).contains(crc.getDateAdministired()));
        CustomAssertion.softTrue("Fourth column does not contain result", resultsForm.getFirstRowRecordText(4).contains(result));
        CustomAssertion.softTrue("Fifth column does not contain staff member", resultsForm.getFirstRowRecordText(5).contains(staffMember));
    }
}
