package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

/**
 * Create some career readiness certification, set assessment's scores connected with this crc to incorrect values (like 5).
 * Edit it and save changes. Check, that warning messages are not displayed.
 * Created by a.vnuchko on 16.12.2015.
 */

@TestCase(id = "WINGS-10992")
public class TC_20_26_CRC_Edit_Old_Assessments_Scores_Edit extends TC_20_25_CRC_Edit_Old_Assessments_Scores_Preview {
    @Override
    public void main() {
        super.main();

        logStep("Click [Edit]");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Click [Save Changes]");
        detailsPage.clickButton(Buttons.Save);
        BaseNavigationSteps.logout();

        logResult("Verify that a yellow triangle in not dispalyed near the record in the list of search results anymore");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);
        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        CareerReadinessCertification crc = getCrc();
        searchPage.performSearch(crc);

        searchPage.checkExclamationPresent(Constants.TRUE);

        logResult("Verify the red message that the user should update these Assessments is not dispaled anymore");
        searchPage.openFirstSearchResult();
        detailsPage.checkWarningMessageNotPresent();
    }
}
