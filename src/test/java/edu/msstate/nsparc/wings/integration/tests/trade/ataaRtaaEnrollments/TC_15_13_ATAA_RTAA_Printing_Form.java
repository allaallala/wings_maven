package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps.createAtaaRtaaEnrollment;

@TestCase(id = "WINGS-10861")
public class TC_15_13_ATAA_RTAA_Printing_Form extends BaseWingsTest {

    public void main(){

        AtaaRtaaEnrollment lowAtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_LOW);
        createAtaaRtaaEnrollment(lowAtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Staff and open search ATAA/RTAA Enrollment page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(lowAtaaEnrollment, Roles.STAFF);

        logStep("Check 'Print' buttons are available in 'Forms' section");
        AtaaRtaaEnrollmentDetailsForm ataaEnrollmentDetailsForm = new AtaaRtaaEnrollmentDetailsForm();
        // unable to check printing using Selenium, so we just check that Print button are displayed and available
        ataaEnrollmentDetailsForm.validatePrintButtonsAvailable();
    }
}
