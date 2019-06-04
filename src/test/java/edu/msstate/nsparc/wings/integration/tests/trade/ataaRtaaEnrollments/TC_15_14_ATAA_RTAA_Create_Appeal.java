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


@TestCase(id = "WINGS-10862")
public class TC_15_14_ATAA_RTAA_Create_Appeal extends BaseWingsTest {

    public void main() {
        AtaaRtaaEnrollment ineligibleLowEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_LOW);
        ineligibleLowEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(ineligibleLowEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Trade Director and open ATAA/RTAA enrolment participantSSDetails page");
        AtaaRtaaEnrollmentSteps.openAppealAtaaRtaaEnrollmentPage(ineligibleLowEnrollment, Roles.TRADEDIRECTOR);

        logStep("Fill in required fields ('Appeal Date' is enough) and save");
        DenialSectionForm denialSectionForm = new DenialSectionForm(Constants.ATRT_ENRLMS);
        denialSectionForm.inputDateAppeal(CommonFunctions.getCurrentDate());
        denialSectionForm.clickButton(Buttons.Save);

        logStep("Check the appeal was created and is shown in the Denial section");
        denialSectionForm.validateAppealDate(CommonFunctions.getCurrentDate());
    }
}
