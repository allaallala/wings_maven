package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10851")
public class TC_15_02_ATAA_RTAA_Cancel_Creation extends BaseWingsTest {

    public void main() {
        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createTradeEnrollment(ataaRtaaEnrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(ataaRtaaEnrollment.getParticipant(), ataaRtaaEnrollment.getReemployment());

        logStep("Log in as Staff and open creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Create);

        logStep("Fill out required fields");
        AtaaRtaaEnrollmentCreationForm creationForm = new AtaaRtaaEnrollmentCreationForm();
        creationForm.fillOutCreationForm(ataaRtaaEnrollment);

        logStep("Click 'Cancel'");
        creationForm.clickButton(Buttons.Cancel);
        creationForm.areYouSure(Popup.Yes);
        new StaffHomeForm();

        logStep("Check ATAA-RTAA Enrollment was not created");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        AtaaRtaaEnrollmentSearchForm searchForm = new AtaaRtaaEnrollmentSearchForm();
        searchForm.performSearch(ataaRtaaEnrollment);
        searchForm.noSearchResults();
    }
}
