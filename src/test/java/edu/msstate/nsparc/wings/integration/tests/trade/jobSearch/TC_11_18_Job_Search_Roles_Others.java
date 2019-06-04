package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check job search functionality using different roles - all, except administrator, area director, manager.
 * Created by a.vnuchko on 04.03.2016.
 */

@TestCase(id = "WINGS-10749")
public class TC_11_18_Job_Search_Roles_Others extends TC_11_17_Job_Search_Roles_Admin_AD_Manager {

    public void main() {

        User user = new User(Roles.STAFF);
        commonJobSearchSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonJobSearchSteps(user);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonJobSearchSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonJobSearchSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonJobSearchSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonJobSearchSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.RRADMIN);
        commonJobSearchSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonJobSearchSteps(user);

        //Role - DVOP
        user.setNewUser(Roles.DVOP);
        commonJobSearchSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonJobSearchSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonJobSearchSteps(user);

        user.setNewUser(Roles.WIOAPROVIDER);
        commonJobSearchSteps(user);

        user.setNewUser(Roles.LWDASTAFF);
        commonJobSearchSteps(user);
    }
}
