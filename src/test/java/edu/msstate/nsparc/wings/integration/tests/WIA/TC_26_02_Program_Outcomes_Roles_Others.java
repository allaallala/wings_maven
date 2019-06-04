package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check user permissions for the Program Outcomes module. Roles - rapid response administrator, everify administrator, wioa administrator, wioa administrator PLUS,
 * project code administrator, DVOP veteran, LVER, EXECUTIVE, WIOA provider, LWDA Staff.
 * Created by a.vnuchko on 04.07.2016.
 */

@TestCase(id = "WINGS-11092")
public class TC_26_02_Program_Outcomes_Roles_Others extends TC_26_01_Program_Outcomes_Roles_Admin_AD_Manager_Staff_IS_TA {

    public void main() {
        createProgramOutcome();

        //Role - rapid response administrator
        User user = new User(Roles.RRADMIN);
        commonProgramOutcomesSteps(user);

        //Role - everify administrator
        user = new User(Roles.EVERIFY);
        commonProgramOutcomesSteps(user);

        //Role - WIOA administrator
        user = new User(Roles.WIOA);
        commonProgramOutcomesSteps(user);

        //Role - WIOA administrator PLUS
        user = new User(Roles.WIOAPLUS);
        commonProgramOutcomesSteps(user);

        //Role - project code administrator
        user = new User(Roles.PCADMIN);
        commonProgramOutcomesSteps(user);

        //Role - LVER
        user = new User(Roles.LVER);
        commonProgramOutcomesSteps(user);

        //Role - Executive
        user = new User(Roles.EXECUTIVE);
        commonProgramOutcomesSteps(user);

        //Role - WIOA provider
        user = new User(Roles.WIOAPROVIDER);
        commonProgramOutcomesSteps(user);

        //Role - LWDA Staff
        user = new User(Roles.LWDASTAFF);
        commonProgramOutcomesSteps(user);

        //Role - DVOP veteran
        user = new User(Roles.DVOPVETERAN);
        commonProgramOutcomesSteps(user);
    }
}
