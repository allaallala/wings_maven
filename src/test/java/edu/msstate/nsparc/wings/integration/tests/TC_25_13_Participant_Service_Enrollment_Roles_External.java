package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionaly [Participant Service Enrollment] using different external user roles.
 * Created by a.vnuchko on 22.06.2016.
 */

@TestCase(id = "WINGS-11089")
public class TC_25_13_Participant_Service_Enrollment_Roles_External extends TC_25_06_Participant_Service_Enrollment_Roles_Admin_AD_Manager_Staff_IS {

    public void main() {

        User user = new User(Roles.WIOAPROVIDER);
        commonStepsParticipantServiceEnrollment(user);

        user.setNewUser(Roles.LWDASTAFF);
        commonStepsParticipantServiceEnrollment(user);
    }
}

