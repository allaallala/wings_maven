package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the job order for the following roles: Intake Staff, Trade Administrator, Rapid Response Administrator,
 * Everify administrator, WIOA administrator, WIOA administrator PLUS, Project Code Administrator, DVOP veteran, LVER, Executive, LWDA Staff, WIOA provider.
 * Created by a.vnuchko on 23.02.2016.
 */

@TestCase(id = "WINGS-11143")
public class TC_29_11_Job_Order_Roles_Others extends TC_29_07_Job_Order_Roles_Admin {

    public void main() {
        //Role - intake staff
        String restrictedFname = "Intake";
        String restrictedLname = "Staff";
        User user = new User(Roles.INTAKESTAFF);
        user.getOrder().setJobRestrictedFName(restrictedFname);
        user.getOrder().setJobRestrictedLName(restrictedLname);
        commonStepsJobOrder(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonStepsJobOrder(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonStepsJobOrder(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonStepsJobOrder(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonStepsJobOrder(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonStepsJobOrder(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonStepsJobOrder(user);

        //Role - DVOP
        user.setNewUser(Roles.DVOP);
        commonStepsJobOrder(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        //WINGS-10139
        commonStepsJobOrder(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonStepsJobOrder(user);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonStepsJobOrder(user);

        //Role - WIOA Provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonStepsJobOrder(user);
    }
}
