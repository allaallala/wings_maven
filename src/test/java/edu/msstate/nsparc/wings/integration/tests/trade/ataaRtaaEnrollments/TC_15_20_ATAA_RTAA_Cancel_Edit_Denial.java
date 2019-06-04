package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.denials.DenialSectionForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10867")
public class TC_15_20_ATAA_RTAA_Cancel_Edit_Denial extends BaseWingsTest {

    private static final String REASON_ENDED = CommonFunctions.getRandomLiteralCode(5);
    private static final String DENIAL_END_DATE = CommonFunctions.getCurrentDate();

    public void main() {
        AtaaRtaaEnrollment ineligibleRtaa = new AtaaRtaaEnrollment(PetitionType.RTAA);
        ineligibleRtaa.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(ineligibleRtaa, Roles.TRADEDIRECTOR);

        logStep("Log in as Trade Director and open ineligible ATAA/RTAA Enrollment");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(ineligibleRtaa, Roles.TRADEDIRECTOR);

        logStep("Click the [Edit Denial] button");
        DenialSectionForm denialSectionForm = new DenialSectionForm();
        denialSectionForm.editDenial();

        logStep("Edit fields: enter 'Denial End Date' and 'Reason Denial Ended', then cancel");
        denialSectionForm.inputDenialsDateReason(DENIAL_END_DATE, REASON_ENDED);
        denialSectionForm.clickCancelAndConfirm();

        logStep("Check that denial was not edited - 'Denial End Date' and 'Reason Denial Ended' should be absent");
        denialSectionForm.checkDenialsNotPresent();
    }
}
