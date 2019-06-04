package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_04_11_WIA_Training_Create_Adult;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import org.testng.Assert;


@TestCase(id = "WINGS-10619")
public class TC_05_05_WIA_Training_Youth_Provider_Requirement extends TC_04_11_WIA_Training_Create_Adult {

    private String [] trainingDetails = {"Other Occupational Skills Training",
                                         CommonFunctions.getCurrentDate(),
                                         CommonFunctions.getTomorrowDate()
    };

    private static final String EDITED_CENTER_NAME = "Golden Triangle Region";


    //Bug WINGS-2657, sub-task WINGS-2661
    public void main() {

        info("Creating WIA Enrollment");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);

        WIATrainingCreateForm wiaTrainingCreateForm = fillWiaTrainingCreateForm(participant);

        logStep("Create");
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();

        logStep("Click on 'Edit' button");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.clickButton(Buttons.Edit);

        logStep("Change 'Service Center' field");
        WIATrainingEditForm wiaTrainingEditForm = new WIATrainingEditForm();
        wiaTrainingEditForm.changeServiceCenter(EDITED_CENTER_NAME);

        logStep("Click on 'Save Changes' button");
        wiaTrainingEditForm.clickButton(Buttons.Save);

        logStep("Click on 'Done' button");
        wiaTrainingCreateForm.clickButton(Buttons.Done);

        logStep("Open Participant Service Enrollment search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Perform search");
        ParticipantEnrollmentSearchForm searchForm = new ParticipantEnrollmentSearchForm();
        searchForm.performSearch(participant, trainingDetails[0]);

        logStep("Check that 'Service Center' is changed");
        Assert.assertTrue(searchForm.getServiceCenterText().contains(EDITED_CENTER_NAME));
    }
}
