package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10859")
public class TC_15_11_ATAA_RTAA_Sorting_Search_Results extends BaseWingsTest {

    public void main() {

        logStep("Log in as Staff and open search ATAA/RTAA Enrollment page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        logStep("Fill out search criteria fields");
        AtaaRtaaEnrollmentSearchForm enrollmentSearchForm = new AtaaRtaaEnrollmentSearchForm();
        enrollmentSearchForm.clickButton(Buttons.Search);

        logStep("Check sorting by Participant is available");
        enrollmentSearchForm.sortByParticipantAndCheckSorting();

        logStep("Check sorting by Petition is available");
        enrollmentSearchForm.sortByPetitionAndCheckSorting();

        logStep("Check sorting by Separation Date is available");
        enrollmentSearchForm.sortBySeparationDateAndCheckSorting();

        logStep("Check sorting by Service Center is available");
        enrollmentSearchForm.sortByServiceCenterAndCheckSorting();
    }
}
