package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10447")
public class TC_05_01_Trade_Enrollment_Full_Workflow extends BaseWingsTest {


    public void main() {

        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        ParticipantCreationSteps.createParticipantDriver(tradeEnrollment.getParticipant(), Boolean.TRUE, Boolean.FALSE);
        ParticipantSqlFunctions.insertParticipantInformationInReaui(tradeEnrollment.getParticipant());
        ParticipantDetailSteps.addEmployment(tradeEnrollment.getParticipant(), tradeEnrollment.getSeparationJob());
        TradeEnrollmentSteps.createPetition(tradeEnrollment.getPetition(), Roles.TRADEDIRECTOR);
        IndividualEmploymentPlan iep = new IndividualEmploymentPlan(tradeEnrollment.getParticipant());
        iep.setCreationDate(tradeEnrollment.getEligibilityDeterminationDate());
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), iep);

        logStep("Log in as Admin and open Trade Enrollment creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Popup.Create);

        logStep("Fill out the creation form");
        TradeEnrollmentCreationForm creationForm = new TradeEnrollmentCreationForm();
        creationForm.fillOutCreationForm(tradeEnrollment);

        logStep("Press Create button");
        creationForm.completeCreation();

        logStep("Make Sure the record was created");
        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.validateInformation(tradeEnrollment);

        logStep("Navigate to Participants - Trade - Trade Enrollments - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Trade Enrollment and open it");
        TradeEnrollmentSearchForm searchForm = new TradeEnrollmentSearchForm();
        searchForm.performSearchAndOpen(tradeEnrollment);

        logStep("Press edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit information");
        tradeEnrollment.setApplicationDate(CommonFunctions.getDaysAgoDate(Constants.DAYS_MONTH));
        tradeEnrollment.setEligibilityDeterminationDate(CommonFunctions.getDaysAgoDate(Constants.DAYS_MONTH));
        TradeEnrollmentEditForm editForm = new TradeEnrollmentEditForm();
        editForm.fillOutEditForm(tradeEnrollment);

        logStep("Save Changes");
        editForm.finishEditing();

        logStep("Validate that changes were saved");
        detailsForm.validateInformation(tradeEnrollment);
    }
}
