package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


@TestCase(id = "WINGS-10852")
public class TC_15_03_ATAA_RTAA_Search_With_No_Parameters extends BaseWingsTest {

    public void main() {
        String regex = "\\d{1,}?,?\\d{2,}";
        logStep("Log in as Staff and open ATAA/RTAA Enrollment search page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        logStep("Perform search with no parameters entered");
        AtaaRtaaEnrollmentSearchForm enrollmentSearchForm = new AtaaRtaaEnrollmentSearchForm();
        enrollmentSearchForm.clickButton(Buttons.Search);

        logStep("Check at least one item was found");
        String recordsCount = CommonFunctions.regexGetMatch(enrollmentSearchForm.getSearchedCount(), regex);
        Integer expectedCount = ParticipantSqlFunctions.getCountATRAEnrollments();
        Assert.assertEquals("Incorrect quantity of assessment records on the search page", expectedCount.toString(),
                recordsCount.replace(",", ""));
    }
}
