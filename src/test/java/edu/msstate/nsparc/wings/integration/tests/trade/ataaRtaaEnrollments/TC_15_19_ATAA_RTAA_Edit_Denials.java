package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.denials.DenialSectionForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10866")
public class TC_15_19_ATAA_RTAA_Edit_Denials extends BaseWingsTest {

    private static final String DENIAL_END_DATE = CommonFunctions.getCurrentDate();
    private static final String REASON_ENDED = CommonFunctions.getRandomLiteralCode(5);

    public void main() {
        AtaaRtaaEnrollment ineligibleHighAtaa = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        ineligibleHighAtaa.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(ineligibleHighAtaa, Roles.TRADEDIRECTOR);

        logStep("Log in as Trade Director and open ineligible ATAA/RTAA Enrollment");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(ineligibleHighAtaa, Roles.TRADEDIRECTOR);

        logStep("Click the [Edit Denial] button");
        DenialSectionForm denialSectionForm = new DenialSectionForm();
        denialSectionForm.editDenial();

        logStep("Edit fields: enter 'Denial End Date' and 'Reason Denial Ended'");
        denialSectionForm.inputDenialsDateReason(DENIAL_END_DATE, REASON_ENDED);

        logStep("Save and validate denial was edited");
        denialSectionForm.clickButton(Buttons.Save);
        denialSectionForm.validateDenialEndDateAndReasonEnded(DENIAL_END_DATE, REASON_ENDED);
    }
}
