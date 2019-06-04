package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Create ATAA/RTAA Enrollment with participant having reemployment, and reemployment date + 728 > current date.
 * Manage subsidies of ATAA/RTAA Enrollment and check its wage status and zeby all wage be presented in the manage wage subsidies table.
 * Created by a.vnuchko.
 */

@TestCase(id = "WINGS-11040")
public class TC_23_05_Wage_Subsidies_Autoadding_Current_Date extends BaseWingsTest {
    public static final String WAGE_STATUS = "Not Yet Entered";

    public void main() {


        info("Precondition: 1. Participant has one job which can be used as re-employment; "
                + "this job is stated though on last week (or later but current date should be before start re-employement date + 728 days\n"
                + "2. Create trade enrollment for this participant");
        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        makeActions(atrt);

        logResult("The Manage Wage Subsidies Screen Screen is shown. Table 'Search Results' contains subsidies for every week "
                + "between the start of qualifying re-employment and the current date. All records have status 'Not yet entered'");
        ManageWageSubsidiesForm subsidiesPage = new ManageWageSubsidiesForm();
        String expectedSubsidiesCount = CommonFunctions.getNumberOfWageWeeks(atrt.getReemployment().getStartDate());
        String firstWageWeek = CommonFunctions.getFirstWageWeek(atrt.getReemployment().getStartDate());
        Assert.assertEquals("Incorrect quantity of subsidies records on form", expectedSubsidiesCount,
                CommonFunctions.regexGetMatch(subsidiesPage.getSearchedCount(), Constants.COUNT_REGEX));

        subsidiesPage.validateWageRecords(firstWageWeek, Integer.valueOf(expectedSubsidiesCount), WAGE_STATUS, atrt, Constants.EMPTY);
    }

    /**
     * Make some action step in one method
     * @param atrt - ataa/ rtaa enrollment
     */
    public void makeActions(AtaaRtaaEnrollment atrt) {
        TradeEnrollmentSteps.createTradeEnrollment(atrt.getTradeEnrollment(), Roles.ADMIN);
        BaseNavigationSteps.addEmployment(atrt.getParticipant(), atrt.getReemployment());


        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Create);

        logStep("Fill out the required fields with valid data and select Status = Eligible. Use participant from precondition");
        AtaaRtaaEnrollmentCreationForm creationPage = new AtaaRtaaEnrollmentCreationForm();
        creationPage.fillOutCreationForm(atrt);

        logStep("Click the [Create] button");
        creationPage.completeCreation();

        logStep("On the 'AATA-RTAA Detail' page expand section 'Wage Subsidies'");
        AtaaRtaaEnrollmentDetailsForm enrollmentDetailsForm = new AtaaRtaaEnrollmentDetailsForm();

        enrollmentDetailsForm.clickButton(Buttons.Edit);
        AtaaRtaaEnrollmentEditForm editForm = new AtaaRtaaEnrollmentEditForm();
        editForm.changeEligibility(atrt);
        editForm.finishEditing();

        logStep("On the 'AATA-RTAA Detail' page expand section 'Wage Subsidies' and Click on the button 'Manage Wage Subsidies' and view data");
        enrollmentDetailsForm.openWageManage();
    }
}

