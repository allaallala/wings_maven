package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceCreationForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;

public class ServiceSteps extends BaseNavigationSteps {
    private static final String CATEGORY = "Information";
    private static final String DESCRIPTION = "Service For Automation Needs";

    /**
     * This method is used for creating Core service for Veterans with desired parameters
     * @param role - user role
     * @param serviceName Service name for new service
     * @param resetExitDate Indicates if service should reset exit date
     * @param youth Indicates if Service should be for WIA Youth program
     */
    public static void createService(Roles role, String serviceName, boolean resetExitDate, boolean youth) {
        String core = "Basic";
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.A_SERVICES, Popup.Create);
        ServiceCreationForm creationForm = new ServiceCreationForm();
        creationForm.inputServiceName(serviceName);
        creationForm.selectLevel(core);
        ServiceCreationForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        creationForm.waitWagnerPayserCheckBoxPresent(true);
        creationForm.inputBeginDate(CommonFunctions.getCurrentDate());
        creationForm.clickNoFee();
        creationForm.selectResetExitDate(resetExitDate);
        if (youth) {
            creationForm.selectWiaYouthCb();
        } else {
            creationForm.clickWPcheckbox();
        }
        creationForm.clickVeteranServiceYes();
        creationForm.selectCategory(CATEGORY);
        creationForm.inputDescription(DESCRIPTION);
        creationForm.clickButton(Buttons.Continue);
        creationForm.clickButton(Buttons.Create);
        creationForm.clickButton(Buttons.Done);
        logout();
    }

    /**
     * This method is used for creating Service with Custom Question
     * @param serviceName Service name
     * @param question Question
     */
    public static void createServiceWithQuestion(String serviceName, String question) {
        String core = "Core";
        loginAdminDashboard();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_SERVICES);
        BaseWingsSteps.popClick(Popup.Create);
        ServiceCreationForm serviceCreationForm = new ServiceCreationForm();
        serviceCreationForm.inputServiceName(serviceName);
        serviceCreationForm.selectLevel(core);
        ServiceCreationForm.BaseOtherElement.LOADING.getElement().waitForNotVisible();
        serviceCreationForm.inputBeginDate(CommonFunctions.getCurrentDate());
        serviceCreationForm.clickNoFee();
        serviceCreationForm.selectResetExitDate(Constants.FALSE);
        serviceCreationForm.clickVeteranServiceAll();
        serviceCreationForm.clickWPcheckbox();
        serviceCreationForm.selectCategory(CATEGORY);
        serviceCreationForm.inputDescription(DESCRIPTION);
        serviceCreationForm.clickButton(Buttons.Continue);
        serviceCreationForm.addOpenAnswerQuestion(question);
        serviceCreationForm.clickButton(Buttons.Create);
        serviceCreationForm.clickButton(Buttons.Done);
        logout();
    }

    public static void createEmployerService(String serviceName) {
        String level = "Employer";
        loginAdminDashboard();
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_SERVICES);
        BaseWingsSteps.popClick(Popup.Create);
        ServiceCreationForm serviceCreationForm = new ServiceCreationForm();
        serviceCreationForm.inputServiceName(serviceName);
        serviceCreationForm.selectLevel(level);
        serviceCreationForm.inputBeginDate(CommonFunctions.getCurrentDate());
        serviceCreationForm.clickNoFee();
        serviceCreationForm.selectCategory(CATEGORY);
        serviceCreationForm.inputDescription(DESCRIPTION);
        serviceCreationForm.clickButton(Buttons.Continue);
        serviceCreationForm.clickButton(Buttons.Create);
        serviceCreationForm.clickButton(Buttons.Done);
        logout();
    }

    /**
     * This method is used for Creating Service and Enrolling participant for it
     *
     * @param participant Registered participant that will be enrolled
     * @param serviceName Service name for new service
     * @param status Status of service enrollment that will be created
     * @param creationDate Service Enrollment creation date
     */
    public static void createParticipantServiceEnrollment(User user, Participant participant, String serviceName, String status, String creationDate) {
        String dateCreate;
        if (creationDate.isEmpty()) {
            dateCreate = CommonFunctions.getYesterdayDate();
        } else {
            dateCreate = creationDate;
        }

        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Create);
        ParticipantEnrollmentCreationForm creationForm = new ParticipantEnrollmentCreationForm();
        creationForm.selectParticipantByUser(user, participant);
        creationForm.selectService(serviceName);
        creationForm.selectYouthProvider();
        creationForm.chooseScheduledService(false);
        creationForm.inputCreationDate(dateCreate);

        if (Constants.IN_PROGRESS.equalsIgnoreCase(status)) {
            creationForm.chooseEndedService(false);
        } else {
            creationForm.chooseEndedService(true);
            creationForm.inputDateResult(dateCreate);
            creationForm.selectResult(status);
        }
        creationForm.clickButton(Buttons.Create);
        creationForm.passParticipationRecalculationPage();
        creationForm.clickButton(Buttons.Done);
        logout();
    }

    public static void createEmployerServiceEnrollment(Employer employer, String serviceName) {
        String enrollmentStatus = Constants.COMPLETED;
        String creationDate = CommonFunctions.getYesterdayDate();
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        ServiceSteps.createEmployerService(serviceName);
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT, Popup.Create);
        EmployerEnrollmentCreationForm creationForm = new EmployerEnrollmentCreationForm();
        creationForm.selectEmployer(employer);
        creationForm.selectService(serviceName);
        creationForm.chooseScheduledService(false);
        creationForm.inputCreationDate(creationDate);
        creationForm.chooseEndedService(true);
        creationForm.inputDateResult(creationDate);
        creationForm.selectResult(enrollmentStatus);
        creationForm.clickButton(Buttons.Create);
        creationForm.clickButton(Buttons.Done);
        logout();
    }
}
