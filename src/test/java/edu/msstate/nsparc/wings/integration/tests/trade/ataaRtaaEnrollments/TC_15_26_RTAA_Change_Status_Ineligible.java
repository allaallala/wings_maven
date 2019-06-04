package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create some RTAA enrollment with empty status, change its state to ineligible, save info and check, that it was successfully saved.
 * Created by a.vnuchko on 18.12.2015.
 */

@TestCase(id = "WINGS-10873")
public class TC_15_26_RTAA_Change_Status_Ineligible extends BaseWingsTest {

    public void main() {
        AtaaRtaaEnrollment enrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaWithNoStatus(enrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Trade Director and open ATAA/RTAA Enrollment Edit page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEditPage(enrollment, Roles.TRADEDIRECTOR);

        AtaaRtaaEnrollmentEditForm editForm = new AtaaRtaaEnrollmentEditForm();
        enrollment.setEligible(Constants.FALSE);
        editForm.changeEligibility(enrollment);
        editForm.clickButton(Buttons.Save);

        logResult("The ATAA-RTAA Enrollment Detail Screen is shown. Changes to the enrollment have been saved successfully and are shown on the detail screen.");
        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
        detailsPage.validateDeterminationDate(enrollment.getEligibilityDeterminationDate());
    }
}
