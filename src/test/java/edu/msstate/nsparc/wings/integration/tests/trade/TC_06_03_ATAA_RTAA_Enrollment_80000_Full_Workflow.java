package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.PreviewPreviousJob;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


@TestCase(id = "WINGS-10645")
public class TC_06_03_ATAA_RTAA_Enrollment_80000_Full_Workflow extends BaseWingsTest {


    public void main() {


        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createTradeEnrollment(ataaRtaaEnrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(ataaRtaaEnrollment.getParticipant(), ataaRtaaEnrollment.getReemployment());

        logStep("Log in WINGS as Participant");
        BaseNavigationSteps.loginParticipant();

        logStep("Make sure that Print Wage button is not displayed");
        ParticipantHomePage homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.printWageIsPresent(Constants.FALSE);
        BaseNavigationSteps.logout();

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Create);

        logStep("Fill out the creation form");
        AtaaRtaaEnrollmentCreationForm creationForm = new AtaaRtaaEnrollmentCreationForm();
        creationForm.fillOutCreationForm(ataaRtaaEnrollment);

        logStep("Press Create button");
        creationForm.completeCreation();

        logStep("Make Sure the record was created");
        AtaaRtaaEnrollmentDetailsForm detailsForm = new AtaaRtaaEnrollmentDetailsForm();
        detailsForm.validateInformation(ataaRtaaEnrollment);

        logStep("Navigate to Participants - Trade - ATAA/RTAA Enrollments - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for ATAA-RTAA Enrollment and open it");
        AtaaRtaaEnrollmentSearchForm searchForm = new AtaaRtaaEnrollmentSearchForm();
        searchForm.performSearchAndOpen(ataaRtaaEnrollment);

        logStep("Press edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Change Enrollment status");
        AtaaRtaaEnrollmentEditForm editForm = new AtaaRtaaEnrollmentEditForm();
        editForm.changeEligibility(ataaRtaaEnrollment);

        logStep("Save Changes");
        editForm.finishEditing();

        logStep("Validate that the status was saved");
        Assert.assertTrue(detailsForm.getStatusPage().contains(ataaRtaaEnrollment.getEligibilityString()));

        logStep("Press edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit 1 Parameter");
        editForm.inputExhaustionDate(ataaRtaaEnrollment.getUiExhaustionDate());

        logStep("Save Changes");
        editForm.finishEditing();

        logStep("Validate that the edited parameter was saved");
        detailsForm.checkExhaustionDate(ataaRtaaEnrollment);

        logStep("Expand Qualifying Re-Employment and open preview");
        detailsForm.openPreview();

        logStep("Make sure Re-Employment participantSSDetails are displayed");
        PreviewPreviousJob previewForm = new PreviewPreviousJob();
        previewForm.validatePreviousJobInformation(ataaRtaaEnrollment.getReemployment());

        logStep("Close Preview");
        PreviewPreviousJob.BTN_CLOSE_PREVIEW.click();
        PreviewPreviousJob.BTN_CLOSE_PREVIEW.waitForNotVisible();

        logStep("Log in WINGS as Participant");
        BaseNavigationSteps.logout();
        BaseNavigationSteps.loginParticipant();

        logStep("Make sure that Print Wage button is now displayed");
        homePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        homePage.printWageIsPresent(Constants.TRUE);
    }
}
