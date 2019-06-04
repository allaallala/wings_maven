package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10857")
public class TC_15_09_ATAA_RTAA_Open_Petition_From_Search_Results_Grid extends BaseWingsTest {

    public void main() {
        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(ataaRtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Staff and open search ATAA/RTAA Enrollment page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        logStep("Fill out search criteria fields");
        AtaaRtaaEnrollmentSearchForm enrollmentSearchForm = new AtaaRtaaEnrollmentSearchForm();
        enrollmentSearchForm.performSearch(ataaRtaaEnrollment);

        logStep("Click 'Petition' link in the search results grid");
        new SearchResultsForm().openPetitionOfFirstResult();

        logStep("Check Petition detail screen for a selected petition is shown");
        PetitionDetailsForm petitionDetailsForm = new PetitionDetailsForm();
        petitionDetailsForm.validateInformation(ataaRtaaEnrollment.getPetition());
    }
}
