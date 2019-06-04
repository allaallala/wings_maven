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


@TestCase(id = "WINGS-10853")
public class TC_15_04_ATAA_RTAA_Add_Remove_Reemployment extends BaseWingsTest {

    public void main() {
        AtaaRtaaEnrollment rtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createTradeEnrollment(rtaaEnrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(rtaaEnrollment.getParticipant(), rtaaEnrollment.getReemployment());

        logStep("Log in as Staff, open ATAA-RTAA Enrollment creation page and click Add Previous Job");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentCreationAndAddJob(rtaaEnrollment);

        logStep("Check job was added to the Qualifying Re-Employment table and removed from the drop-down");
        AtaaRtaaEnrollmentCreationForm enrollmentCreationForm = new AtaaRtaaEnrollmentCreationForm();
        enrollmentCreationForm.elementsPresent(Constants.TRUE);

        logStep("Select added job and click the 'Remove' button");
        enrollmentCreationForm.selectFirstPreviousJob();
        enrollmentCreationForm.removePreviousJob();

        logStep("Check job was deleted from the Qualifying Re-Employment table and is displayed in drop-down");
        enrollmentCreationForm.elementsPresent(Constants.FALSE);
    }
}

