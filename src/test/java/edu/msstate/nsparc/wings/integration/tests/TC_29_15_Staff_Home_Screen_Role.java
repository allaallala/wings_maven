package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check the Staff Home Screen form for different user roles.
 * Created by a.vnuchko on 13.09.2016.
 */

@TestCase(id = "WINGS-11147")
public class TC_29_15_Staff_Home_Screen_Role extends BaseWingsTest {

    public void main(){

        //Role - administrator
        User user = new User(Roles.ADMIN);
        checkHomeScreenFunctionality(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        checkHomeScreenFunctionality(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        checkHomeScreenFunctionality(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        checkHomeScreenFunctionality(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        checkHomeScreenFunctionality(user);


        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        checkHomeScreenFunctionality(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        checkHomeScreenFunctionality(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        checkHomeScreenFunctionality(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        checkHomeScreenFunctionality(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        checkHomeScreenFunctionality(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        checkHomeScreenFunctionality(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        checkHomeScreenFunctionality(user);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        checkHomeScreenFunctionality(user);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        checkHomeScreenFunctionality(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        checkHomeScreenFunctionality(user);
    }
    private void checkHomeScreenFunctionality(User user){
        BaseWingsSteps.logInAs(user.getRole());

        StaffHomeForm homePage = new StaffHomeForm();
        logStep("Check, if user can see the Service Centers Unresulted Referrals section");
        homePage.checkServiceUnresultedReferrals(user.getStHome().getServiceUnresultedReferrals());

        logStep("Check, if user can see the Staff Unresulted Referrals section");
        homePage.checkStaffUnresultedReferrals(user.getStHome().getStaffUnresultedReferrals());

        logStep("Check Approve/Reject Uploaded documents");
        //TODO Approve/Reject documents is not ready.
        BaseNavigationSteps.logout();
    }
}
