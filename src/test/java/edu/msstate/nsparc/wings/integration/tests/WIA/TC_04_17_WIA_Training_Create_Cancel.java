package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;


@TestCase(id = "WINGS-10607")
public class TC_04_17_WIA_Training_Create_Cancel extends TC_04_12_WIA_Training_Create_Dislocated_Worker {

    private static final String ERROR_MESSAGE = "Nothing found to display.";


    public void main () {


        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);

        WIATrainingCreateForm wiaTrainingCreateForm = repeatedSteps(participant);

        logStep("Enter valid Project code->Cancel");
        wiaTrainingCreateForm.selectProjectCode("");
        wiaTrainingCreateForm.clickButton(Buttons.Cancel);
        wiaTrainingCreateForm.areYouSure(Popup.Yes);

        logStep("Try to find WIA Training");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
        BaseWingsSteps.popClick(Popup.Search);
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.performSearch(participant);

        assertEquals("WIA Training was created", ERROR_MESSAGE, new SearchResultsForm().getTableContentText());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
