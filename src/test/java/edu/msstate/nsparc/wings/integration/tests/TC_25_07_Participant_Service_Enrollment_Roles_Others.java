package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionaly [Participant Service Enrollment] using different internal user roles - all, except administrator, area director, manager, staff, intake staff.
 * Created by a.vnuchko on 14.03.2016.
 */

@TestCase(id = "WINGS-11084")
public class TC_25_07_Participant_Service_Enrollment_Roles_Others extends TC_25_06_Participant_Service_Enrollment_Roles_Admin_AD_Manager_Staff_IS {

    public void main() {

        //Role - WIOA administrator
        User user = new User(Roles.WIOA);
        commonStepsParticipantServiceEnrollment(user);

        //Role - WIOA Provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonStepsParticipantServiceEnrollment(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonStepsParticipantServiceEnrollment(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonStepsParticipantServiceEnrollment(user);

        //Role - e-verify administrator
        user.setNewUser(Roles.EVERIFY);
        commonStepsParticipantServiceEnrollment(user);

        user.setNewUser(Roles.EXECUTIVE);
        commonStepsParticipantServiceEnrollment(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonStepsParticipantServiceEnrollment(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonStepsParticipantServiceEnrollment(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonStepsParticipantServiceEnrollment(user);

        //External users
        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonStepsParticipantServiceEnrollment(user);

        //Role - WIOA Provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonStepsParticipantServiceEnrollment(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonStepsParticipantServiceEnrollment(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonStepsParticipantServiceEnrollment(user);
    }
}
