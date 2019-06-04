package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.LdapUserWebReport;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that LDAP search works correctly
 * Created by a.vnuchko on 05.04.2017.
 */

@TestCase(id = "WINGS-11265")
public class TC_38_26_LDAP_Search extends BaseWingsTest {

    public void main(){
        String userName = "adminNovember";
        logStep("Login to the System");
        BaseWingsSteps.logInAs(Roles.ADMIN);

        logStep("Reports->Data integrity->LDAP Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_LDAP_SEARCH);

        logStep("Enter correct Access MS account (try all user types)->Search");
        LdapUserWebReport webPage = new LdapUserWebReport();
        webPage.searchForUssu(userName);

        logResult("The information about user is shown");
        webPage.validateSearchedUser(userName);
    }
}

