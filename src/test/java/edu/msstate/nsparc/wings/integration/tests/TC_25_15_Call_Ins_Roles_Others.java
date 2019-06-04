package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionaly call-ins using different user roles - all, except administrator, area director, manager.
 * Created by a.vnuchko on 30.06.2016.
 */
@TestCase(id = "WINGS-11091")
public class TC_25_15_Call_Ins_Roles_Others extends TC_25_14_Call_Ins_Roles_Admin_AD_Manager {

    public void main(){

        //Role - Staff
        User user = new User(Roles.STAFF);
        commonStepsCallIns(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonStepsCallIns(user);

        //Role - Intake Staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonStepsCallIns(user);

        //Role - Trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonStepsCallIns(user);

        //Role - Rapid Response administrator
        user.setNewUser(Roles.RRADMIN);
        commonStepsCallIns(user);

        //Role - Everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonStepsCallIns(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonStepsCallIns(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonStepsCallIns(user);

        //Role - Project Code administrator
        user.setNewUser(Roles.PCADMIN);
        commonStepsCallIns(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonStepsCallIns(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonStepsCallIns(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonStepsCallIns(user);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonStepsCallIns(user);
    }
}
