package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check trade training enrollment functionality using different roles - all, except administrator, manager, staff.
 * Created by a.vnuchko on 09.03.2016.
 */

@TestCase(id = "WINGS-11079")
public class TC_25_02_Trade_Training_Roles_Others extends TC_25_01_Trade_Training_Roles_Admin_Manager_Staff {

    public void main(){

        //Role - trade administrator
        User user = new User(Roles.TRADEDIRECTOR);
        commonTradeTrainingSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonTradeTrainingSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonTradeTrainingSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonTradeTrainingSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonTradeTrainingSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonTradeTrainingSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonTradeTrainingSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonTradeTrainingSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonTradeTrainingSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonTradeTrainingSteps(user);

        //Role - Executive
        user.setNewUser(Roles.EXECUTIVE);
        commonTradeTrainingSteps(user);

        //Role - WIOA Prov
        user.setNewUser(Roles.WIOAPROVIDER);
        commonTradeTrainingSteps(user);

        //Role - Executive
        user.setNewUser(Roles.LWDASTAFF);
        commonTradeTrainingSteps(user);
    }
}
