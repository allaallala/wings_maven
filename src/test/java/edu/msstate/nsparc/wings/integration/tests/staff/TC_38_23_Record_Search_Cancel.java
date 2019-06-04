package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Input some fields to search an employer, cancel it and check, that home form is displayed.
 * Created by a.vnuchko on 17.03.2017.
 */

@TestCase(id = "WINGS-11262")
public class TC_38_23_Record_Search_Cancel extends BaseWingsTest {

    public void main(){
        Employer employer = new Employer(AccountUtils.getEmployerAccount());

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);

        logStep("Enter any data to all fields for searching->Cancel");
        EmployerSearchForm searchForm = new EmployerSearchForm();
        searchForm.inputCompanyName(employer.getCompanyName());
        searchForm.clickButton(Buttons.Cancel);
        //searchForm.areYouSure(Popup.Yes);

        logResult("Home Page is opened");
        new StaffHomeForm();
    }
}
