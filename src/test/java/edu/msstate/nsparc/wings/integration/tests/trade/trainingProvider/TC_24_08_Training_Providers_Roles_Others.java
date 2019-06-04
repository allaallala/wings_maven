package edu.msstate.nsparc.wings.integration.tests.trade.trainingProvider;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check functionality of the training provider for all roles except administrator and trade administrator.
 * Created by a.vnuchko on 23.02.2016.
 */

@TestCase(id = "WINGS-11068")
public class TC_24_08_Training_Providers_Roles_Others extends TC_24_07_Training_Providers_Roles_Admin_TA {

    public void main(){

        //Role - project code administrator
        User user = new User(Roles.PCADMIN);
        commonTrainingProviderSteps(user);

        //Role - MDES manager
        user.setNewUser(Roles.MANAGER);
        commonTrainingProviderSteps(user);

        //Role - MDES staff
        user.setNewUser(Roles.STAFF);
        commonTrainingProviderSteps(user);

        //Role - Intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonTrainingProviderSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonTrainingProviderSteps(user);

        //Role - e-verify administrator
        user.setNewUser(Roles.EVERIFY);
        commonTrainingProviderSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9173
        commonTrainingProviderSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9173
        commonTrainingProviderSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9175
        commonTrainingProviderSteps(user);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        commonTrainingProviderSteps(user);

        //External roles
        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonTrainingProviderSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonTrainingProviderSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonTrainingProviderSteps(user);
    }
}
