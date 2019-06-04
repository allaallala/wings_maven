package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.ParticipantAddEmploymentForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Create a participant with some record in the employment history. Add new record with rre created and check, that new
 * record is successfully added.
 * Created by User on 02.03.2017.
 */

@TestCase(id = "WINGS-11248")
public class TC_38_07_Participant_Edit_With_RRE extends BaseWingsTest {
    private String date = CommonFunctions.getCurrentDate();

    public void main() {
        info("Precondition: create participant with an employment history. Create rapid response event also");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.STAFF);

        AccountUtils.init();
        RapidResponseEvent event = new RapidResponseEvent();
        EmployerSteps.createRapidResponseEvent(event, Roles.RRADMIN);

        logStep("Login to the System and open search participant form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Select record from the preconditions and open it");
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearchAndOpen(tradeEnrollment.getParticipant());

        logStep("Go to the page with employment history");
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandEmploymentHistory();

        logStep("Add new Previous Job record with a rapid response event");
        detailsPage.clickPreviousJob();
        ParticipantAddEmploymentForm employeePage = new ParticipantAddEmploymentForm();
        employeePage.addRecordWithRre(event.getEmployer(), date, event);

        logResult("Check, that Previous job has successfully added to a participant");
        detailsPage.validateEmploymentHistory(event.getEmployer(),date);
    }

}
