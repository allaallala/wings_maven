package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.xray.info.TestCase;


/**
 *  Check functionality denials for trade for all roles, except an administrator.
 * Created by a.vnuchko on 31.08.2016.
 */

@TestCase(id = "WINGS-11145")
public class TC_29_13_Denials_Trade_Roles_Others extends TC_29_12_Denials_Trade_Roles_Admin {

    public void main(){
        /*
        divideMessage("Preconditions: create trade enrollment");
        trenrl = new TradeEnrollment();

        BaseTradeEnrollmentFunctions.createTradeEnrollment(trenrl, Roles.TRADEDIRECTOR);

        //Role - trade administrator
        User user = new User(Roles.TRADEDIRECTOR);
        commonDenialsSteps(user);

        //Role - LVER
        user = new User(Roles.LVER);
        commonDenialsSteps(user);

        //Role - area director
        user = new User(Roles.AREADIRECTOR);
        commonDenialsSteps(user);

        //Role - manager
        user = new User(Roles.MANAGER);
        commonDenialsSteps(user);

        //Role - staff
        user = new User(Roles.STAFF);
        commonDenialsSteps(user);

        //Role - intake staff
        user = new User(Roles.INTAKESTAFF);
        commonDenialsSteps(user);

        //Role - rapid response administrator
        user = new User(Roles.RRADMIN);
        commonDenialsSteps(user);

        //Role - everify administrator
        user = new User(Roles.EVERIFY);
        commonDenialsSteps(user);

        //Role - WIOA administrator
        user = new User(Roles.WIOA);
        commonDenialsSteps(user);

        //Role - WIOA administrator PLUS
        user = new User(Roles.WIOAPLUS);
        commonDenialsSteps(user);

        //Role - project code administrator
        user = new User(Roles.PCADMIN);
        commonDenialsSteps(user);

        //Role - DVOP veteram
        user = new User(Roles.DVOPVETERAN);
        commonDenialsSteps(user);




        //Role - executive
        user = new User(Roles.EXECUTIVE);
        commonDenialsSteps(user);

        //Role - LWDA Staff
        user = new User(Roles.LWDASTAFF);
        commonDenialsSteps(user);

        //Role - WIOA provider
        user = new User(Roles.WIOAPROVIDER);
        commonDenialsSteps(user);
        */
    }
}
