package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10854")
public class TC_15_06_ATAA_RTAA_Search_By_One_Parameter extends BaseWingsTest {

    public void main() {
        String regex = "\\ \\(\\d+\\)";
        AtaaRtaaEnrollment enrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(enrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Staff and open search ATAA/RTAA Enrollment page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        logStep("Select Participant and perform search");
        AtaaRtaaEnrollmentSearchForm searchForm = new AtaaRtaaEnrollmentSearchForm();
        searchForm.selectParticipant(enrollment.getParticipant().getFirstName(), enrollment.getParticipant().getLastName());
        searchForm.clickButton(Buttons.Search);

        logStep("Check correct  ATAA/RTAA Enrollment was found");
        // using regExp to cut off last numbers of SSN from participant full name
        CustomAssertion.softAssertEquals(searchForm.getFoundParticipantName().replaceAll(regex, Constants.EMPTY), enrollment.getParticipant().getFullName(),"Incorrect Participant found!");

        logStep("Clear search form and perform search for Petition");
        searchForm.clickButton(Buttons.Clear);
        searchForm.selectInactivePetition(enrollment.getPetition());
        searchForm.clickButton(Buttons.Search);

        logStep("Check correct  ATAA/RTAA Enrollment was found");
        String petition = String.format("%1$s - %2$s - Certified %3$s", enrollment.getPetition().getNumber(),
                enrollment.getPetition().getEmployer().getCompanyName(), enrollment.getPetition().getCertificationDate());
        CustomAssertion.softAssertEquals(new SearchResultsForm().getPetitionOfFirstResultText(), petition,"Incorrect Petition found!");
    }
}
