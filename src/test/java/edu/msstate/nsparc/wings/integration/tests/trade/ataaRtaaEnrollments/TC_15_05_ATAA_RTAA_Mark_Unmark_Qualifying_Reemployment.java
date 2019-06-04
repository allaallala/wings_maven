package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10442")
public class TC_15_05_ATAA_RTAA_Mark_Unmark_Qualifying_Reemployment extends BaseWingsTest {


    public void main() {
        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createTradeEnrollment(ataaRtaaEnrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(ataaRtaaEnrollment.getParticipant(), ataaRtaaEnrollment.getReemployment());

        logStep("Log in as Staff, open ATAA-RTAA Enrollment creation page and click Add Previous Job");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentCreationAndAddJob(ataaRtaaEnrollment);
        AtaaRtaaEnrollmentCreationForm enrollmentCreationForm = new AtaaRtaaEnrollmentCreationForm();
        enrollmentCreationForm.selectFirstPreviousJob();

        logStep("Click 'Mark as Qualifying Re-Employment' button");
        enrollmentCreationForm.markUnmarkReemployment(Constants.TRUE);

        logStep("Check the record is marked as 'Verified Qualifying Re-employment'");
        enrollmentCreationForm.checkQualifyingReEmployment();

        logStep("Select Employment record and click 'Unmark as Qualifying Re-Employment' button");
        enrollmentCreationForm.selectFirstPreviousJob();
        enrollmentCreationForm.markUnmarkReemployment(Constants.FALSE);

        logStep("Check the record is unmarked as 'Verified Qualifying Re-employment'");
        enrollmentCreationForm.checkUnmarkedQualifyingReEmployment();
    }
}
