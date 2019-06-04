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
import org.testng.Assert;


/**
 * Create RTAA enrollment with reemployment more than 30 hours per week, change its status to eligible, save it, and check that is successfully saved.
 * Created by a.vnuchko on 26.11.2015.
 */

@TestCase(id = "WINGS-10871")
public class TC_15_24_RTAA_Create_Recipient_Valid_Data extends BaseWingsTest {
    private static final String HOURS_WEEK = "35";
    private static final String SALARY_WEEK = "25";

    public void main() {
        info("Precondition: Create ATAA-RTAA Enrollment with a Re-Employment Job");
        AtaaRtaaEnrollment atrta = new AtaaRtaaEnrollment(PetitionType.RTAA);
        atrta.getReemployment().setHoursPerWeek(HOURS_WEEK);
        atrta.getReemployment().setWages(SALARY_WEEK);

        TradeEnrollmentSteps.createAtaaRtaaEnrollment(atrta, Roles.TRADEDIRECTOR);

        AtaaRtaaEnrollmentSteps.openAtaaRtaaEditPage(atrta, Roles.TRADEDIRECTOR);

        AtaaRtaaEnrollmentEditForm editForm = new AtaaRtaaEnrollmentEditForm();
        atrta.setEligible(Constants.TRUE);
        editForm.changeEligibility(atrta);
        editForm.clickButton(Buttons.Save);

        logResult("The ATAA-RTAA Enrollment Detail Screen is shown. Changes to the enrollment have been saved successfully and are shown on the detail screen.");
        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
        info(detailsPage.getStatusPage());
        Assert.assertTrue(detailsPage.getStatusPage().equals(Constants.ELIGIBLE));
    }
}
