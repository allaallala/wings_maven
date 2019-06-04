package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * There is a user with created Access MS account, start creating an employer, fill in some fields, then click [Cancel]
 * Check, thar record is not created.
 * Created by a.vnuchko on 17.03.2017.
 */

@TestCase(id = "WINGS-11260")
public class TC_38_21_Record_Create_Cancel extends BaseWingsTest {

    public void main(){
        Employer employer = new Employer(AccountUtils.getEmployerAccount());

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Create);

        logStep("Enter Company name and fill in some fields");
        EmployerCreationForm creationPage = new EmployerCreationForm();
        creationPage.fillInPagesUptoLocation(employer);

        logStep("Select Cancel Button");
        creationPage.clickButton(Buttons.Cancel);
        creationPage.areYouSure(Popup.Yes);

        logResult("The record is not created");
        BaseNavigationSteps.logout();
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);

        EmployerSearchForm searchPage = new EmployerSearchForm();
        searchPage.performSearch(employer.getCompanyName());
        searchPage.noSearchResults();
    }
}
