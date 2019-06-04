package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityParticipantReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10754")
public class TC_11_42_Data_Integrity_Participant_Service_Enrollments extends BaseWingsTest {

    protected String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(15);
    protected String dateChange = "2100-01-01";
    protected String serviceEnrollmentDates = "Invalid Service Enrollment Dates";
    Participant participant;

    /**
     * Test
     * @throws Exception - exception
     */

    public void main() throws Exception {
        info("We need to create Participant Service Enrollment");
        participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, Constants.FALSE, Constants.FALSE);
        AdvancedSqlFunctions.changeServiceEnrollmentDate(participant, dateChange);

        logStep("Login to the System");
        BaseWingsSteps.logInAs(Roles.ADMIN);

        logStep("Reports->Data integrity->Participant report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_PARTICIPANT_REPORT);

        logStep("Select Service Enrollments records type");
        searchForRecordsTypes(participant);

        BaseNavigationSteps.logout();
    }

    /**
     * Search for records types
     * @param participant - participant
     */
    protected void searchForRecordsTypes(Participant participant) {
        DataIntegrityParticipantReportForm dataIntegrityParticipantReportForm = new DataIntegrityParticipantReportForm();
        dataIntegrityParticipantReportForm.selectServiceEnrollments(serviceEnrollmentDates);

        divideMessage("Search");
        dataIntegrityParticipantReportForm.inputFirstLastName(participant.getFirstName(), participant.getLastName());
        dataIntegrityParticipantReportForm.searchServiceEnrollment();

        divideMessage("Validate search results");
        dataIntegrityParticipantReportForm.validateParticipantSearchResultsByColumn(participant, "1");
    }

}
