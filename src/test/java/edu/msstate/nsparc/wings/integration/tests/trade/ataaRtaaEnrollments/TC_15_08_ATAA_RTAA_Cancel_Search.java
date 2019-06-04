package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10856")
public class TC_15_08_ATAA_RTAA_Cancel_Search extends BaseWingsTest {

    public void main() {

        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(ataaRtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Staff and open search ATAA/RTAA Enrollment page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        logStep("Fill out search criteria fields");
        AtaaRtaaEnrollmentSearchForm enrollmentSearchForm = new AtaaRtaaEnrollmentSearchForm();
        enrollmentSearchForm.fillOutSearchCriteriaFields(ataaRtaaEnrollment);

        logStep("Click 'Cancel' button");
        enrollmentSearchForm.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logStep("Check Staff Home screen is displayed");
        new StaffHomeForm();
    }
}
