package edu.msstate.nsparc.wings.integration.tests.trade.collection;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that preview for the wage subsidies and payments can be opened from the ATAA/ RTAA Enrollment and contains correct information
 * Created by a.vnuchko on 15.10.2015.
 */

@TestCase(id = "WINGS-11036")
public class TC_22_12_Collection_Wage_Subsidies_Payments_Test extends BaseWingsTest {
    private static final String AMOUNT_ALLOWED = "10,000.00";
    private static final String AMOUNT_TOTAL = "0.00";
    private static final String AMOUNT_BALANCE = "10,000.00";

    public void main() {
        AtaaRtaaEnrollment rtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.RTAA);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(rtaaEnrollment, Roles.TRADEDIRECTOR);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        logStep("Check that the preview can be successfully opened for the Wage Subsidies " +
                "and Payments (from the ATAA/RTAA Enrollment participantSSDetails page) and contains the correct information");
        AtaaRtaaEnrollmentSearchForm searchPage = new AtaaRtaaEnrollmentSearchForm();
        searchPage.performSearchAndOpen(rtaaEnrollment);

        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
        detailsPage.validateWageSubsidies(AMOUNT_ALLOWED, AMOUNT_TOTAL, AMOUNT_BALANCE);

    }
}
