package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check WIA Enrollment functionality using different roles - all, except administrator, area director, manager
 * Created by a.vnuchko on 01.03.2016.
 */

@TestCase(id = "WINGS-11071")
public class TC_24_12_WIA_Enrollment_Roles_Others extends TC_24_11_WIA_Enrollment_Roles_Admin_AD_Manager {

    public void main() {

        //Role Staff
        User user = new User(Roles.STAFF);
        commonWiaEnrollmentSteps(user);

        //Role - intake staff
        user = new User(Roles.INTAKESTAFF);
        commonWiaEnrollmentSteps(user);

        //Role - trade administrator
        user = new User(Roles.TRADEDIRECTOR);
        commonWiaEnrollmentSteps(user);

        //Role - rapid response administrator
        user = new User(Roles.RRADMIN);
        commonWiaEnrollmentSteps(user);

        //Role - everify administrator
        user = new User(Roles.EVERIFY);
        commonWiaEnrollmentSteps(user);

        //Role - WIOA administrator
        user = new User(Roles.WIOA);
        commonWiaEnrollmentSteps(user);

        //Role - WIOA administrator PLUS
        user = new User(Roles.WIOAPLUS);
        commonWiaEnrollmentSteps(user);

        //Role - project code administrator
        user = new User(Roles.PCADMIN);
        commonWiaEnrollmentSteps(user);

        //Role - LVER
        user = new User(Roles.LVER);
        commonWiaEnrollmentSteps(user);

        //Role - Executive
        user = new User(Roles.EXECUTIVE);
        commonWiaEnrollmentSteps(user);

        //Role - DVOP
        user = new User(Roles.DVOP);
        commonWiaEnrollmentSteps(user);
    }
}
