package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create trade enrollment for a chosen participant and check, that it is displayed correctly
 * Created by a.vnuchko on 02.03.2017.
 */

@TestCase(id = "WINGS-11246")
public class TC_38_05_Participant_TE_Section extends BaseWingsTest {

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.STAFF);

        logStep("Login to the System and open search participant form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Select record from the preconditions and open it");
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearchAndOpen(tradeEnrollment.getParticipant());

        logStep("Expand Trade Enrollments section");
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandTradeEnrollments();

        logResult("The existing Trade Enrollment record is displayed in the section");
        detailsPage.validateTradeEnrollments(tradeEnrollment);
    }
}
