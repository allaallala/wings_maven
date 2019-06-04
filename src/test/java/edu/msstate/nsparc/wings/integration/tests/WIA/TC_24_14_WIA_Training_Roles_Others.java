package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check WIA Training functionality using different roles - all, except administrator, area director, manager
 * Created by a.vnuchko on 03.03.2016.
 */

@TestCase(id = "WINGS-11074")
public class TC_24_14_WIA_Training_Roles_Others extends TC_24_13_WIA_Training_Roles_Admin_AD_Manager {

    public void main(){

        //Role Staff
        User user = new User(Roles.STAFF);
        commonWiaTrainingSteps(user);

        //Role - intake staff
        user = new User(Roles.INTAKESTAFF);
        commonWiaTrainingSteps(user);

        //Role - trade administrator
        user = new User(Roles.TRADEDIRECTOR);
        commonWiaTrainingSteps(user);

        //Role - rapid response administrator
        user = new User(Roles.RRADMIN);
        commonWiaTrainingSteps(user);

        //Role - everify administrator
        user = new User(Roles.EVERIFY);
        commonWiaTrainingSteps(user);

        //Role - WIOA administrator
        user = new User(Roles.WIOA);
        commonWiaTrainingSteps(user);

        //Role - WIOA administrator PLUS
        user = new User(Roles.WIOAPLUS);
        commonWiaTrainingSteps(user);

        //Role - project code administrator
        user = new User(Roles.PCADMIN);
        commonWiaTrainingSteps(user);

        //Role - DVOP
        user = new User(Roles.DVOP);
        commonWiaTrainingSteps(user);

        //Role - LVER
        user = new User(Roles.LVER);
        commonWiaTrainingSteps(user);

        //Role - Executive
        user = new User(Roles.EXECUTIVE);
        commonWiaTrainingSteps(user);
    }
}
