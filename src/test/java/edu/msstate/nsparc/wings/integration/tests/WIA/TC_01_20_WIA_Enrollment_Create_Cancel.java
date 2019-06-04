package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10518")
public class TC_01_20_WIA_Enrollment_Create_Cancel extends TC_01_12_WIA_Enrollment_Create_Dislocated_Worker {

    private static final String ERROR_MESSAGE = "Nothing found to display.";


    public void main () {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = openEnrollmentCreationForm(participant);

        logStep("Fill in all fields you need->Cancel");
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, false);
        wiaEnrollmentCreationForm.clickButton(Buttons.Cancel);
        wiaEnrollmentCreationForm.areYouSure(Popup.Yes);

        logStep("Check, that WIA Enrollment wasn't created");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        WIAEnrollmentSearchForm wiaEnrollmentSearchForm = new WIAEnrollmentSearchForm();
        wiaEnrollmentSearchForm.performSearch(participant);
        assertEquals("WIA Enrollment was created", ERROR_MESSAGE, new SearchResultsForm().getTableContentText());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
