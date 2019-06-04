package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.*;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * Create some RTAA Enrollment with specified preconditions:
 * Pre-conditions:
 * - UI exhaustion date is sooner than the first qualifying re-employment date
 * - the worker is re-employed
 * Check that your 2 year eligibility period for RTAA subsistence has not ended
 * Created by a.vnuchko on 30.12.2015.
 */

@TestCase(id = "WINGS-10874")
public class TC_15_27_RTAA_Edit_SDED extends BaseWingsTest {
    protected String newFormat = "yyyyMMdd";
    protected String oldFormat = "MMddyyyy";
    protected Integer futureDays = 728;
    String reason = "Layoff";

    public void main() {
        PreviousJob previousJob = new PreviousJob(PreviousJobType.REEMPLOYMENT, null);
        List<PreviousJob> previousJobs = new ArrayList<>();
        previousJobs.add(previousJob);
        AtaaRtaaEnrollment enrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH, previousJobs);
        enrollment.getReemployment().setStartDate(CommonFunctions.getDaysAgoDate(20));
        enrollment.getFullReemploymentList().get(1).setStartDate(CommonFunctions.getDaysAgoDate(100));
        enrollment.getFullReemploymentList().get(1).setEnded(Constants.TRUE);
        enrollment.getFullReemploymentList().get(1).setEndDate(CommonFunctions.getDaysAgoDate(50));
        enrollment.getFullReemploymentList().get(1).setReasonForLeaving(reason);


        TradeEnrollmentSteps.createTradeEnrollment(enrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(enrollment.getParticipant(), enrollment.getFullReemploymentList().get(0));
        ParticipantDetailSteps.addEmployment(enrollment.getParticipant(), enrollment.getFullReemploymentList().get(1));

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Create);

        AtaaRtaaEnrollmentCreationForm creationForm = new AtaaRtaaEnrollmentCreationForm();
        creationForm.fillOutCreationForm(enrollment);

        creationForm.completeCreation();

        new AtaaRtaaEnrollmentDetailsForm();
        BaseNavigationSteps.logout();


        AtaaRtaaEnrollmentSteps.openAtaaRtaaEditPage(enrollment, Roles.TRADEDIRECTOR);
        AtaaRtaaEnrollmentEditForm editPage = new AtaaRtaaEnrollmentEditForm();
        editPage.changeEligibility(enrollment);

        logStep(" Check that your 2 year eligibility period for RTAA subsistence has not ended.\n "
                + "a. UI exhaustion date <= qualifying re-employment date <= UI ex. date + 104 weeks\n"
                + "b. UI exhaustion date <= Application date <= UI ex. date + 104 weeks");


        Integer exhaustionInt = Integer.valueOf(CommonFunctions.changeDateFormat(enrollment.getUiExhaustionDate().replace("/", Constants.EMPTY), oldFormat, newFormat));
        Integer quarInt = Integer.valueOf(CommonFunctions.changeDateFormat(enrollment.getReemployment().getStartDate().replace("/", Constants.EMPTY), oldFormat, newFormat));
        Integer applicationInt = Integer.valueOf(CommonFunctions.changeDateFormat(enrollment.getApplicationDate().replace("/", Constants.EMPTY), oldFormat, newFormat));
        String exhaustionWeeks = CommonFunctions.getDaysNextDate(enrollment.getReemployment().getStartDate(), futureDays);
        Integer exWeekInt = Integer.valueOf(CommonFunctions.changeDateFormat(exhaustionWeeks.replace("/", Constants.EMPTY), oldFormat, newFormat));

        //a
        assertDate(exhaustionInt, quarInt);
        assertDate(quarInt, exWeekInt);
        //b
        assertDate(exhaustionInt, applicationInt);
        assertDate(applicationInt, exWeekInt);

        logStep("Click the [Save Changes] button");
        editPage.clickButton(Buttons.Save);

        logResult("Changes to an RTAA Enrollment are saved. The ATAA-RTAA Enrollment Detail Screen is shown.");
        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
        Assert.assertTrue(detailsPage.getStatusPage().contains(enrollment.getEligibilityString()));
    }

    /**
     * Check, that required date less/greater date specified.
     *
     * @param lessDate    - date, that should be less
     * @param greaterDate - date, that should be greater.
     */
    protected void assertDate(Integer lessDate, Integer greaterDate) {
        if (lessDate <= greaterDate) {
            info(String.format("Comparison between %1$s and %2$s is correct", lessDate, greaterDate));
        } else {
            fatal(String.format("Comparison between %1$s and %2$s is incorrect", lessDate, greaterDate));
        }
    }
}
