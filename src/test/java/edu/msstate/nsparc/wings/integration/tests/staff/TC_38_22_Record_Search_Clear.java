package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Input some fields to search an employer, clear it and check, that all fields are empty.
 * Created by a.vnuchko on 17.03.2017.
 */

@TestCase(id = "WINGS-11261")
public class TC_38_22_Record_Search_Clear extends BaseWingsTest {
    String county = "Bolivar";
    String naics = "Construction";
    String fein = "123214";

    public void main(){
        Employer employer = new Employer(AccountUtils.getEmployerAccount());

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);

        logStep("Enter any data to all fields for searching->Clear Form");
        EmployerSearchForm searchPage = new EmployerSearchForm();
        searchPage.inputCompanyName(employer.getCompanyName());
        searchPage.inputCityZip(employer.getAddress().getCity(), employer.getAddress().getZipCode());
        searchPage.inputFein(fein);
        searchPage.selectCounty(county);
        searchPage.selectNaics(naics);

        searchPage.clickButton(Buttons.Clear);

        logResult("All fields are empty");
        searchPage.validateFieldsCleared();
    }
}
