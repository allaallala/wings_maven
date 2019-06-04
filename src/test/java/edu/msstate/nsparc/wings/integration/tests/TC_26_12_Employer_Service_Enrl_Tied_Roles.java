package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the Employer Service Enrollment module for different user roles. Check only employers that are tied to the Trade or Petition
 * (Area Director, Manager, Staff, Intake Staff, Trade Administrator, Rapid Response Administrator, LVER)
 * Created by a.vnuchko on 14.07.2016.
 */

@TestCase(id = "WINGS-11102")
public class TC_26_12_Employer_Service_Enrl_Tied_Roles extends TC_26_11_Employer_Service_Enr_Not_Tied_Roles {

    public void main(){

        //Role - trade administrator
        User user = new User(Roles.TRADEDIRECTOR);
        commonEmployerServiceEnrollmentSteps(user, true);


        //Issue - WINGS-9565
        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonEmployerServiceEnrollmentSteps(user, true);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonEmployerServiceEnrollmentSteps(user, true);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonEmployerServiceEnrollmentSteps(user, true);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonEmployerServiceEnrollmentSteps(user, true);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonEmployerServiceEnrollmentSteps(user, true);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonEmployerServiceEnrollmentSteps(user, true);
    }
}
