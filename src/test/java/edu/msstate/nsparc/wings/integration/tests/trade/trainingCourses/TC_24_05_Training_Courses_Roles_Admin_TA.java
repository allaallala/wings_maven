package edu.msstate.nsparc.wings.integration.tests.trade.trainingCourses;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check actions, which can be done by the specific user role: administrator or trade administrator.
 * Created by a.vnuchko on 19.02.2016.
 */
@TestCase(id = "WINGS-10457")
public class TC_24_05_Training_Courses_Roles_Admin_TA extends TC_24_04_Training_Courses_Roles_Others {

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonTrainingCoursesSteps(user);

        //Role - Trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonTrainingCoursesSteps(user);
    }
}
