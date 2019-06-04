package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10869")
public class TC_15_22_ATAA_RTAA_Cancel_Editing_Enrollment extends BaseWingsTest {

    private static final String NEW_DATE = CommonFunctions.getCurrentDate();

    public void main() {
        AtaaRtaaEnrollment rtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.RTAA);
        TradeEnrollmentSteps.createAtaaRtaaWithNoStatus(rtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Trade Director and open Edit ATAA/RTAA Enrollment page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEditPage(rtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Edit any fields, e.g. 'Application Date' and 'Federal Income Tax'");
        AtaaRtaaEnrollmentEditForm editForm = new AtaaRtaaEnrollmentEditForm();
        editForm.inputApplicationDate(NEW_DATE);
        editForm.selectHoldFederalIncome();

        logStep("Cancel editing and check changes were not saved");
        editForm.clickCancelAndConfirm();
        AtaaRtaaEnrollmentDetailsForm detailsForm = new AtaaRtaaEnrollmentDetailsForm();
        detailsForm.validateInformation(rtaaEnrollment);
        detailsForm.validateFederalIncomeTax(rtaaEnrollment);
    }
}
