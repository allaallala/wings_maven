package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

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
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Create some RTAA Enrollment with specified preconditions:
 * Pre-conditions:
 - the first qualifying re-employment date is sooner than UI exhaustion date
 - the worker is re-employed
 Check that your 2 year eligibility period for RTAA subsistence has not ended
 * Created by a.vnuchko on 29.12.2015.
 */

@TestCase(id = "WINGS-10872")
public class TC_15_25_RTAA_Edit_SDQD extends TC_15_27_RTAA_Edit_SDED {

    public void main() {
        AtaaRtaaEnrollment enrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaWithNoStatus(enrollment, Roles.TRADEDIRECTOR);

        AtaaRtaaEnrollmentSteps.openAtaaRtaaEditPage(enrollment, Roles.TRADEDIRECTOR);
        AtaaRtaaEnrollmentEditForm editPage = new AtaaRtaaEnrollmentEditForm();
        editPage.changeEligibility(enrollment);

        logStep("Check that your 2 year eligibility period for RTAA subsistence has not ended: \n"
                + "   the first qualifying re-employment date <= Application date <= the first qualifying re-employment date + 104 weeks");

        Integer quarInt = Integer.valueOf(CommonFunctions.changeDateFormat(enrollment.getReemployment().getStartDate().replace("/", Constants.EMPTY), oldFormat, newFormat));
        Integer applicationInt = Integer.valueOf(CommonFunctions.changeDateFormat(enrollment.getApplicationDate().replace("/", Constants.EMPTY), oldFormat, newFormat));
        String exhaustionWeeks = CommonFunctions.getDaysNextDate(enrollment.getReemployment().getStartDate(), futureDays);
        Integer exWeekInt  = Integer.valueOf(CommonFunctions.changeDateFormat(exhaustionWeeks.replace("/", Constants.EMPTY), oldFormat, newFormat));

        assertDate(quarInt, applicationInt);
        assertDate(applicationInt, exWeekInt);

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult("Changes to an RTAA Enrollment are saved. The ATAA-RTAA Enrollment Detail Screen is shown.");
        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
        Assert.assertTrue(detailsPage.getStatusPage().contains(enrollment.getEligibilityString()));
    }


}
