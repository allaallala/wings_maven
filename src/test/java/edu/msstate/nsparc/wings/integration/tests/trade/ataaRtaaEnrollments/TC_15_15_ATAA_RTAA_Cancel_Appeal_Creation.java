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


@TestCase(id = "WINGS-10863")
public class TC_15_15_ATAA_RTAA_Cancel_Appeal_Creation extends BaseWingsTest {

    public void main() {
        AtaaRtaaEnrollment ineligibleHighEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        ineligibleHighEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(ineligibleHighEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Trade Director and open appeal form of ATAA/RTAA enrolment");
        AtaaRtaaEnrollmentSteps.openAppealAtaaRtaaEnrollmentPage(ineligibleHighEnrollment, Roles.TRADEDIRECTOR);

        logStep("Fill in 'Appeal Date' field and cancel");
        DenialSectionForm denialSectionForm = new DenialSectionForm(Constants.ATRT_ENRLMS);
        denialSectionForm.inputDateAppeal(CommonFunctions.getCurrentDate());
        denialSectionForm.clickCancelAndConfirm();

        logStep("Check appeal wasn't performed");
        denialSectionForm.validateAppealingIsNotPerformed();
    }
}
