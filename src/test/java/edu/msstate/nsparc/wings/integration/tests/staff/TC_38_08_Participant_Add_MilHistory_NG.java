package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create participant with national guard, add new military record and check, that all data is saved.
 * Created by a.vnuchko on 07.03.2017.
 */

@TestCase(id = "WINGS-11249")
public class TC_38_08_Participant_Add_MilHistory_NG extends BaseWingsTest {
    private String militaryBranch = "Marine Corps";
    private String newMilBranch = "Coast Guard";
    private String beginDate = CommonFunctions.getDaysAgoDate(Constants.DAYS_YEAR);
    private String endDate = CommonFunctions.getYesterdayDate();

    public void main() {
        info("Precondition: create national guard participant");
        Participant participant = new Participant();

        ParticipantCreationSteps.createParticipant(participant, false, true, false);
        //createNatGuardParticipant(part);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_RECORDS, Popup.Search);

        logStep("Find record from preconditions, and open it.");
        ParticipantSearchForm searchPage = new ParticipantSearchForm();
        searchPage.performSearchAndOpen(participant);

        logStep("Expand Veteran Details section.");
        BaseParticipantDetailsForm detailsPage = new BaseParticipantDetailsForm();
        detailsPage.expandVetDetails();

        logStep("Add new military record");
        detailsPage.addMilitaryRecord(militaryBranch, beginDate, endDate);

        logResult("New Military record was added. All data are saved.");
        detailsPage.expandVetDetails();
        detailsPage.validateVetAddedRecord(militaryBranch, beginDate, endDate);

        logStep("Edit a military record");
        detailsPage.editMilitaryRecord(newMilBranch);
        detailsPage.expandVetDetails();

        logResult("Check, that all changes are saved");
        detailsPage.validateVetAddedRecord(newMilBranch, beginDate, endDate);

        logStep("Remove existing record");
        detailsPage.removeMilitaryRecord();

        logResult("Selected military record was removed");
        detailsPage.expandVetDetails();
        detailsPage.noSearchResults();
    }
}
