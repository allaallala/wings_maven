package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


@TestCase(id = "WINGS-10448")
public class TC_06_01_ATAA_RTAA_Enrollment_50000_Full_Workflow extends BaseWingsTest {


    public void main() {

        AtaaRtaaEnrollment ataaRtaaEnrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_LOW);
        TradeEnrollmentSteps.createTradeEnrollment(ataaRtaaEnrollment.getTradeEnrollment(), Roles.ADMIN);
        ParticipantDetailSteps.addEmployment(ataaRtaaEnrollment.getParticipant(), ataaRtaaEnrollment.getReemployment());


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
    }
}

