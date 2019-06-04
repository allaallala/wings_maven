package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;

public class WiaEnrollmentSteps extends BaseNavigationSteps {

    public static void createWIAEnrollment(User user, Participant participant, boolean youth, boolean dislocated) {
        String educationalStatus = "Not attending school; high school graduate/GED";
        String toolUsed = "Other, Not Listed";
        String[][] data = openWiaEnrlCreationForm(0);

        String applicationDate = data[0][0];
        String contactPerson = data[0][1];
        String contactPhone = data[0][2];
        String relation = data[0][3];

        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipantByUser(user, participant);
        if (youth) {
            wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, Constants.TRUE);
            wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
            wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(Constants.TRUE);
            wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(educationalStatus, toolUsed, Constants.TRUE);
        } else {
            wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, Constants.FALSE);
            if (dislocated) {
                wiaEnrollmentCreationForm.fillBasicInformationDislocatedWorker();
            } else {
                wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
            }

            wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(Constants.FALSE);
        }
        logout();
    }

    public static void createWIAEnrollmentByExternal(User user, Participant participant, boolean youth, boolean dislocated) {
        String educationalStatus = "Not attending school; high school graduate/GED";
        String toolUsed = "Other, Not Listed";
        String[][] data = openWiaEnrlCreationForm(0);

        String applicationDate = data[0][0];
        String contactPerson = data[0][1];
        String contactPhone = data[0][2];
        String relation = data[0][3];

        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipantByUser(user, participant);
        if (youth) {
            wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, Constants.TRUE);
            wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
            wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(Constants.TRUE);
            wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(educationalStatus, toolUsed, Constants.TRUE);
        } else {
            wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, Constants.FALSE);
            if (dislocated) {
                wiaEnrollmentCreationForm.fillBasicInformationDislocatedWorker();
            } else {
                wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
            }

            wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(Constants.FALSE);
        }
        logout();
    }

    public static void createWIAEnrollment(Participant participant, boolean youth, String date, String contactPerson, String phone, String relation) {
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        User user = new User(Roles.ADMIN);
        createWIAEnrollmentForCreatedParticipant(participant, youth, date, contactPerson, phone, relation, user);
    }

    public static void createWIAEnrollmentForCreatedParticipant(Participant participant, boolean youth, String date, String contactPerson, String phone,
                                                                String relation, User user) {
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Create);
        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipantByUser(user, participant);
        wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(date, contactPerson, phone, relation, youth);
        wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
        wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(youth);
        wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(Constants.WIA_EDUCATIONAL_STATUS, Constants.WIA_TOOL_USED, youth);
        WIAEnrollmentDetailsForm detailsForm = new WIAEnrollmentDetailsForm();
        detailsForm.validateBasicInformation(date, contactPerson, relation);
    }

    public static void createOldWIAEnrollment(Participant participant, boolean youth, int days) {
        String educationalStatus = "Not attending school; high school graduate/GED";
        String toolUsed = "Other, Not Listed";

        String[][] data = openWiaEnrlCreationForm(days);

        String applicationDate = data[0][0];
        String contactPerson = data[0][1];
        String contactPhone = data[0][2];
        String relation = data[0][3];

        WIAEnrollmentCreationForm wiaEnrollmentCreationForm = new WIAEnrollmentCreationForm();
        wiaEnrollmentCreationForm.selectParticipant(participant);
        if (youth) {
            wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, Constants.TRUE);
            wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
            wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(Constants.TRUE);
            wiaEnrollmentCreationForm.fillWIAEnrollmentYouthInformation(educationalStatus, toolUsed, Constants.TRUE);
        } else {
            wiaEnrollmentCreationForm.fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation, Constants.FALSE);
            wiaEnrollmentCreationForm.clickButton(Buttons.Continue);
            wiaEnrollmentCreationForm.fillWIAEnrollmentProgramInformation(Constants.FALSE);
        }
        logout();
    }


}
