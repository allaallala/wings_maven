package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import org.testng.Assert;


@TestCase(id = "WINGS-10449")
public class TC_06_04_ATAA_RTAA_Enrollment_Wages_Workflow extends BaseWingsTest {


    public void main() {

        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(ataaRtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Lof in as Admin and open ATAA/RTAA Enrollment participantSSDetails page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(ataaRtaaEnrollment, Roles.ADMIN);

        logStep("Open Manage Wage Subsidies page");
        AtaaRtaaEnrollmentDetailsForm detailsForm = new AtaaRtaaEnrollmentDetailsForm();
        detailsForm.openWageManage();

        logStep("Make sure that all eligible weeks are displayed");
        Assert.assertTrue(detailsForm.getWageSubsidiesText().contains(String.format("%1$s items found",
                CommonFunctions.getNumberOfWageWeeks(ataaRtaaEnrollment.getReemployment().getStartDate()))));
        ataaRtaaEnrollment.setFirstWageWeek(CommonFunctions.getFirstWageWeek(ataaRtaaEnrollment.getReemployment().getStartDate()));

        CustomAssertion.softTrue("Incorrect first wage week", detailsForm.getWageSubsidiesText().contains(ataaRtaaEnrollment.getFirstWageWeek()));

        logStep("Search for a Wage Subsidy record");
        detailsForm.performWageSubsidySearch(ataaRtaaEnrollment.getFirstWageWeek());

        logStep("Open Subsidy participantSSDetails");
        new SearchResultsForm().openSelectedResult();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Make sure Wage Details are opened");
        Assert.assertTrue(detailsForm.getWeekEndingDate().contains(ataaRtaaEnrollment.getFirstWageWeek()));

        logStep("Press 'Save and Go to Next Week button");
        detailsForm.saveNextWeek();

        logStep("Make sure the next Wage Week Details are opened");
        info(detailsForm.getWeekEndingDate());
        info(CommonFunctions.getNextWageWeek(ataaRtaaEnrollment.getFirstWageWeek()));
        CustomAssertion.softTrue("Incorrect next wage week", detailsForm.getWeekEndingDate().contains(CommonFunctions.getNextWageWeek(ataaRtaaEnrollment.getFirstWageWeek())));
    }
}

