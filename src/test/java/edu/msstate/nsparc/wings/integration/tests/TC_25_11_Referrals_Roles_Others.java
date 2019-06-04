package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check Referrals for different user roles. Roles: all, except administrator, area director, manager.
 * Created by a.vnuchko on 25.08.2016.
 */

@TestCase(id = "WINGS-11088")
public class TC_25_11_Referrals_Roles_Others extends TC_25_10_Referrals_Roles_Admin_AD_Manager {

    public void main(){

        //Role - Staff
        User user = new User(Roles.STAFF);
        commonReferralSteps(user);

        //Role - Intake Staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonReferralSteps(user);

        //Role - Trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonReferralSteps(user);

        //Role - Rapid Response Administrator
        user.setNewUser(Roles.RRADMIN);
        commonReferralSteps(user);

        //Role - Everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonReferralSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonReferralSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonReferralSteps(user);

        //Role - Project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonReferralSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonReferralSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonReferralSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonReferralSteps(user);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonReferralSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonReferralSteps(user);
    }
}
