package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;


/**
 * Open CRC Search form, do not enter any information and click [Search]. Verify, that all records are displayed.
 * Created by a.vnuchko on 22.09.2015.
 */

@TestCase(id = "WINGS-10979")
public class TC_20_12_CRC_Search_No_Parameters extends BaseWingsTest {

    public void main() {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);

        logStep("Click the [Search] button ( Don't fill out any search criteria field)");
        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        searchPage.clickButton(Buttons.Search);

        logResult("All the Career Readiness Certifications are shown in the Search Results form");
        int crcCount = ParticipantSqlFunctions.getCareerReadinessCertificationCount();
        String recordsCount = CommonFunctions.regexGetMatch(searchPage.getSearchedCount(), "\\d{1,}?,?\\d{2,}");
        Assert.assertEquals("Incorrect quantity of CRC records on the search page", String.valueOf(crcCount), recordsCount.replace(",", ""));
    }
}
