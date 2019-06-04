package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps.createAtaaRtaaEnrollment;

@TestCase(id = "WINGS-10860")
public class TC_15_12_ATAA_RTAA_View_Form_Date_Signed extends BaseWingsTest {

    public void main() {

        AtaaRtaaEnrollment lowAtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_LOW);
        createAtaaRtaaEnrollment(lowAtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Staff and open ATAA/RTAA Enrollment detail page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(lowAtaaEnrollment, Roles.STAFF);

        logStep("Fill in 'Date Signed' for ATAA Selection Form and save it");
        AtaaRtaaEnrollmentDetailsForm ataaEnrollmentDetailsForm = new AtaaRtaaEnrollmentDetailsForm();
        ataaEnrollmentDetailsForm.enterAtaaSelectionDateAndSave(CommonFunctions.getCurrentDate());

        logStep("Fill in 'Date Signed' for ATAA Approval Form and save it");
        ataaEnrollmentDetailsForm.enterAtaaApprovalDateAndSave(CommonFunctions.getCurrentDate());

        logStep("Check dates were saved");
        ataaEnrollmentDetailsForm.validateAtaaSelectionDateInfo(CommonFunctions.getCurrentDate());
        ataaEnrollmentDetailsForm.validateAtaaApprovalDateInfo(CommonFunctions.getCurrentDate());
    }
}
