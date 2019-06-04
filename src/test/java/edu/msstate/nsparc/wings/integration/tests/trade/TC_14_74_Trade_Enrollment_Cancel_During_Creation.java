package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentCreationForm;
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


@TestCase(id = "WINGS-10839")
public class TC_14_74_Trade_Enrollment_Cancel_During_Creation extends BaseWingsTest {

    public void main () {

        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        ParticipantCreationSteps.createParticipantDriver(tradeEnrollment.getParticipant(), Boolean.TRUE, Boolean.FALSE);
        ParticipantSqlFunctions.insertParticipantInformationInReaui(tradeEnrollment.getParticipant());
        ParticipantDetailSteps.addEmployment(tradeEnrollment.getParticipant(), tradeEnrollment.getSeparationJob());
        TradeEnrollmentSteps.createPetition(tradeEnrollment.getPetition(), Roles.TRADEDIRECTOR);
        IndividualEmploymentPlan iep = new IndividualEmploymentPlan(tradeEnrollment.getParticipant());
        iep.setCreationDate(tradeEnrollment.getEligibilityDeterminationDate());
        TradeEnrollmentSteps.createIndividualEmploymentPlan(new User(Roles.STAFF), iep);

        logStep("Log in as Staff and open Trade Enrollment creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Popup.Create);

        logStep("Fill out some fields");
        TradeEnrollmentCreationForm tradeEnrollmentCreationForm = new TradeEnrollmentCreationForm();
        tradeEnrollmentCreationForm.fillOutCreationForm(tradeEnrollment);

        logStep("Click the [Cancel] button");
        tradeEnrollmentCreationForm.clickButton(Buttons.Cancel);
        tradeEnrollmentCreationForm.areYouSure(Popup.Yes);

        logStep("The Staff Home screen is shown");
        new StaffHomeForm();

        logStep("Open Trade Enrollment search page");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Validate the Trade Enrollment wasn't created");
        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.performSearch(tradeEnrollment);
        tradeEnrollmentSearchForm.noSearchResults();
    }
}
