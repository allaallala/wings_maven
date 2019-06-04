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


@TestCase(id = "WINGS-10865")
public class TC_15_18_ATAA_RTAA_Cancel_Edit_Appeal extends BaseWingsTest {

    private static final String EDITED_DATE = CommonFunctions.getYesterdayDate();

    public void main() {

        AtaaRtaaEnrollment ineligibleTaaEnrollment = new AtaaRtaaEnrollment(PetitionType.TAA_2014);
        ineligibleTaaEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createAppealedAtaaRtaaEnrollment(ineligibleTaaEnrollment);

        logStep("Log in as Trade Director and open ATAA/RTAA enrolment participantSSDetails page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(ineligibleTaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Click 'Edit Appeal' button");
        DenialSectionForm denialSectionForm = new DenialSectionForm(Constants.ATRT_ENRLMS);
        denialSectionForm.editAppeal();

        logStep("Edit any field and cancel");
        denialSectionForm.inputDateAppeal(EDITED_DATE);
        denialSectionForm.clickCancelAndConfirm();

        logStep("Check Appeal wasn't edited");
        // current date should be displayed
        denialSectionForm.validateAppealDate(CommonFunctions.getCurrentDate());
    }
}
