package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Login to the system, choose one of the location from the list(user has at least two) and check, that it's displayed
 * correctly
 * Created by a.vnuchko on 01.03.2017.
 */

@TestCase(id = "WINGS-11243")
public class TC_38_02_Several_Locations extends BaseWingsTest {

    public void main(){
        String workPlace = "Hinds County";
        logStep("Login to the  WINGS");
        LoginForm loginPage = new LoginForm();
        loginPage.login(AccountUtils.getAdminAccount1(), AccountUtils.getAdminAccount1());

        logStep("Select one of the several locations and click [Continue]");
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        staffLocationForm.selectWorkplace(workPlace);

        logResult("Selected location is opened");
        staffLocationForm.validateWorkPlace(workPlace);
    }
}
