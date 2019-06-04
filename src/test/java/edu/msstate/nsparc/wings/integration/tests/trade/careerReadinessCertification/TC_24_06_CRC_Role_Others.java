package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the career readiness certification for intake staff, rapid response administrator, e-verify administrator, wioa administrator, Project code administrator,
 * DVOP, LVER, executive.
 * Created by a.vnuchko on 11.02.2016.
 */

@TestCase(id = "WINGS-11066")
public class TC_24_06_CRC_Role_Others extends TC_24_04_CRC_Role_Admin_AD_DVOPVet {

    public void main() {

        info("Create career readiness certification for manipulation");
        crc = new CareerReadinessCertification();
        createCrcRegularParticipant();

        //Role - Intake staff
        User user = new User(Roles.INTAKESTAFF);
        commonCrcSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonCrcSteps(user);

        //Role - Rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonCrcSteps(user);

        //Role - e-verify administrator
        user.setNewUser(Roles.EVERIFY);
        commonCrcSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonCrcSteps(user);

        //Role  - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonCrcSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonCrcSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonCrcSteps(user);
    }

    /**
     * Create career readiness certification, participant is not a veteran.
     */
    private void createCrcRegularParticipant() {
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Constants.FALSE, Constants.FALSE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, crc.getAppliedMathematics());
        AssessmentSteps.createAssessment(staff, crc.getLocatingInformation());
        AssessmentSteps.createAssessment(staff, crc.getReadingForInformation());

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Create);

        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.fillCRCInformation(crc);
        creationPage.finishCreating();

        BaseNavigationSteps.logout();
    }
}
