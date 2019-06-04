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


@TestCase(id = "WINGS-10864")
public class TC_15_16_ATAA_RTAA_Edit_Appeal extends BaseWingsTest {

    private static final String EDITED_DATE = CommonFunctions.getYesterdayDate();

    public void main() {

        AtaaRtaaEnrollment ineligibleRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.RTAA);
        ineligibleRtaaEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createAppealedAtaaRtaaEnrollment(ineligibleRtaaEnrollment);

        logStep("Log in as Trade Director and open ATAA/RTAA Enrollment participantSSDetails page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(ineligibleRtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Click 'Edit Appeal' button");
        DenialSectionForm denialSectionForm = new DenialSectionForm();
        denialSectionForm.editAppeal();

        logStep("Edit any field and save");
        denialSectionForm.inputDateAppeal(EDITED_DATE);
        denialSectionForm.clickButton(Buttons.Save);

        logStep("Check Appeal was edited");
        // edited date should be displayed
        denialSectionForm.validateAppealDate(EDITED_DATE);
    }
}
