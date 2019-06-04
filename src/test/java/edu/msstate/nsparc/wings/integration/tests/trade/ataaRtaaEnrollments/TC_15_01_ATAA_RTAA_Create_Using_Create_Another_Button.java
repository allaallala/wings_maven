package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.*;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps.createTradeEnrollment;

@TestCase(id = "WINGS-10850")
public class TC_15_01_ATAA_RTAA_Create_Using_Create_Another_Button extends BaseWingsTest {

    public void main() {
        final String ataaRecords = "2";
        String regex = "\\d{1,}";

        // we need at least two Previous Jobs for the participant
        PreviousJob previousJob = new PreviousJob(PreviousJobType.SEPARATION, null);
        List<PreviousJob> previousJobs = new ArrayList<>();
        previousJobs.add(previousJob);
        // create ataaRtaaEnrollment and add several reemployments (Previous Jobs)
        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH, previousJobs);
        createTradeEnrollment(ataaRtaaEnrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(ataaRtaaEnrollment.getParticipant(), ataaRtaaEnrollment.getFullReemploymentList());

        logStep("Log in as Staff and open creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Create);

        logStep("Create ATAA/RTAA Enrollment: fill out required fields");
        AtaaRtaaEnrollmentCreationForm creationForm = new AtaaRtaaEnrollmentCreationForm();
        creationForm.fillOutCreationForm(ataaRtaaEnrollment);
        creationForm.completeCreation();

        logStep("Click 'Create Another' button and create one more ATAA/RTAA Enrollment");
        //creationForm.assertIsOpen();
        creationForm.clickButton(Buttons.CreateAnother);
        creationForm.fillOutCreateAnotherForm(ataaRtaaEnrollment);
        creationForm.completeCreation();

        logStep("Check two ATAA/RTAA Enrollments were created: navigate to the search page");
        //creationForm.assertIsOpen();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Perform search by Participant and check two records were found");
        AtaaRtaaEnrollmentSearchForm searchForm = new AtaaRtaaEnrollmentSearchForm();
        searchForm.selectParticipant(ataaRtaaEnrollment.getParticipant());
        searchForm.clickButton(Buttons.Search);
        String recordsCount = CommonFunctions.regexGetMatch(searchForm.getSearchedCount(), regex);
        info(recordsCount);
        Assert.assertTrue(ataaRecords.equals(recordsCount));
    }
}
