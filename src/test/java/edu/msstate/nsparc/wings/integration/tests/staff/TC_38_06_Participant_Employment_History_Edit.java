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
import framework.customassertions.CustomAssertion;


/**
 * Create a participant with a trade enrollment, check, that the job used in the Employment History Section can be edited.
 * Created by a.vnuchko on 02.03.2017.
 */

@TestCase(id = "WINGS-11247")
public class TC_38_06_Participant_Employment_History_Edit extends BaseWingsTest {
    private String[] args = {"Budzin"};

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.STAFF);

        logStep("Login to the System and open search participant form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Select record from the preconditions and open it");
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearchAndOpen(tradeEnrollment.getParticipant());

        logStep("Expand Employment History section");
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandEmploymentHistory();

        logResult("Check, that Admin user can edit Previous Job record");
        detailsPage.editPreviousJob(args);
        detailsPage.expandEmploymentHistory();
        CustomAssertion.softTrue("Incorrect previous job information", detailsPage.getPreviousJobPageText().contains(args[0]));

    }
}
