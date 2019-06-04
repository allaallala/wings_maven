package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Represents document upload functionality for a manager, staff and intake staff
 * Created by a.vnuchko on 14.09.2016.
 */

@TestCase(id = "WINGS-11150")
public class TC_29_18_Document_Upload_Manager_Staff_IS extends TC_29_17_Document_Upload_Admin_AD {

    public void main() {
        init();
        User user = new User(Roles.MANAGER);
        docSteps(user, fileName);

        init();
        user.setNewUser(Roles.STAFF);
        docSteps(user, fileName);

        init();
        user.setNewUser(Roles.INTAKESTAFF);
        docSteps(user, fileName);
    }
}
