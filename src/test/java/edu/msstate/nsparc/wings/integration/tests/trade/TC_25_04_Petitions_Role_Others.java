package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the petition module: users role - all, except 'Administrator'
 * Created by a.vnuchko on 11.03.2016.
 */

@TestCase(id = "WINGS-11081")
public class TC_25_04_Petitions_Role_Others extends TC_25_03_Petitions_Role_Admin {

    public void main() {
        //Role - trade administrator
        User user = new User(Roles.TRADEDIRECTOR);
        commonPetitionSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonPetitionSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonPetitionSteps(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonPetitionSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonPetitionSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonPetitionSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonPetitionSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonPetitionSteps(user);

        //Role - WIOA administrator PLUS
        user = new User(Roles.WIOAPLUS);
        commonPetitionSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonPetitionSteps(user);

        //Role - DVOP Veterinar :)
        user.setNewUser(Roles.DVOPVETERAN);
        commonPetitionSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonPetitionSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonPetitionSteps(user);

        //Role - Executive
        user.setNewUser(Roles.LWDASTAFF);
        commonPetitionSteps(user);

        //Role - Executive
        user.setNewUser(Roles.WIOAPROVIDER);
        commonPetitionSteps(user);
    }
}
