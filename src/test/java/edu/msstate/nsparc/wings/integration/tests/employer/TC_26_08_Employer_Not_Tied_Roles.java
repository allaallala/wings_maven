package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check user permissions for the [Employer] module. For those roles, who care if an employer tied or not to Trade or Petition. Check if it's not tied.
 * (Area director, Manager, Staff, Intake staff, Trade administrator, Rapid response administrator, WIOA administrator, LVER]
 * Created by a.vnuchko on 12.07.2016.
 */

@TestCase(id = "WINGS-11098")
public class TC_26_08_Employer_Not_Tied_Roles extends TC_26_06_Employer_Neutral_Roles{

    public void main(){

        //Role - area director
        User user = new User(Roles.AREADIRECTOR);
        commonEmployerSteps(user, false);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonEmployerSteps(user, false);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonEmployerSteps(user, false);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonEmployerSteps(user, false);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonEmployerSteps(user, false);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonEmployerSteps(user, false);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonEmployerSteps(user, false); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9172

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonEmployerSteps(user, false); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9172

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonEmployerSteps(user, false);

    }
}
