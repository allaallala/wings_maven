package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Search by all Assessments without parameters entered.
 * Created by a.vnuchko on 04.09.2015.
 */

@TestCase(id = "WINGS-10957")
public class TC_19_12_Assessment_Search_With_No_Parameters extends BaseWingsTest {
    private String regexp = "\\d{1,}?,?\\d{2,}";

    public void main() {
        AssessmentSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);

        logStep("Click the [Search] button (Don't fill out any search criteria field)");
        AssessmentSearchForm searchPage = new AssessmentSearchForm();
        searchPage.clickButton(Buttons.Search);

        logResult("All the Assessments are shown in the Search Results form");
        int assmCount = ParticipantSqlFunctions.getAssessmentCount();
        String recordsCount = CommonFunctions.regexGetMatch(searchPage.getSearchedCount(), regexp);
        Assert.assertEquals("Incorrect quantity of assessment records on the search page", String.valueOf(assmCount),
                recordsCount.replace(",", ""));
    }
}
