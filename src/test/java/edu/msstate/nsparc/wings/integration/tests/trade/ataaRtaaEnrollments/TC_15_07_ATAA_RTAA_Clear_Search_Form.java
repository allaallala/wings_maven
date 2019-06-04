package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10855")
public class TC_15_07_ATAA_RTAA_Clear_Search_Form extends BaseWingsTest {

    private static final String EMPTY_SEARCH_FIELD = "";

    public void main() {

        AtaaRtaaEnrollment enrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(enrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Staff and open search ATAA/RTAA Enrollment page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        logStep("Fill out some search criteria fields and perform search");
        AtaaRtaaEnrollmentSearchForm searchForm = new AtaaRtaaEnrollmentSearchForm();
        searchForm.selectParticipant(enrollment.getParticipant());
        searchForm.clickButton(Buttons.Search);

        logStep("Fill out the least fields and click 'Clear form'");
        searchForm.selectInactivePetition(enrollment.getPetition());
        searchForm.selectAnyAvailableServiceCenter();
        searchForm.inputStatusDate(enrollment);
        searchForm.clickButton(Buttons.Clear);

        logStep("Check all the entries made in the search criteria fields are cleared");
        searchForm.validateSearchCriteria(EMPTY_SEARCH_FIELD);
    }
}
