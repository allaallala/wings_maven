package edu.msstate.nsparc.wings.integration.forms.employer;

import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.customassertions.CustomAssertion;
import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Employer Profiles -> Search
 */
public class EmployerSearchForm extends EmployerBaseForm {

    private TextBox txbCompanyName = new TextBox("id=employerName", "Company Name");
    private TextBox txbZipCode = new TextBox("id=contactInformationByEmployerContactInfo.addressByResidenceAddr.zipcode", "Zip Code");
    private TextBox txbAddressCity = new TextBox("id=contactInformationByEmployerContactInfo.addressByResidenceAddr.city", "City");
    private TextBox txbFein = new TextBox(By.id("federalId"), "FEIN");
    private ComboBox cmbCounty = new ComboBox(By.id("contactInformationByEmployerContactInfo.addressByResidenceAddr.county"), "County");
    private ComboBox cmbNaics = new ComboBox(By.id("naicsCategory"), "Naics");
    private String employerName = "//table[@id='results-table']//td/a";
    private String xpCompName = "//a[contains(.,'%1$s')]";

    private Link lnkEmployerName = new Link(By.xpath(employerName), "Employer Name");

    public EmployerSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Employer Search')]"), "Employer Search");
    }

    public void performSearch(String companyName, String zipCode, String city) {
        inputCompanyName(companyName);
        inputCityZip(city, zipCode);
        search();
    }

    public void performSearch(String companyName) {
        inputCompanyName(companyName);
        search();
    }

    public void performSearch(Employer employer) {
        performSearch(employer.getCompanyName(), employer.getAddress().getZipCode(), employer.getAddress().getCity());
        checkSearchResult(employer.getCompanyName());
    }

    private void checkSearchResult(String companyName) {
        info("Checking, that Employer was found");
        Link lnkCompanyName = new Link(By.xpath(String.format(xpCompName, companyName)), "Company name Link");
        CustomAssertion.softTrue("Company wasn't found", lnkCompanyName.isPresent());
        info("Search result: OK");
    }

    public void searchAndReturnEmployer(String companyName, String zipCode, String city) {
        performSearch(companyName, zipCode, city);
        checkSearchResult(companyName);
        selectResultAndReturn();
    }

    public void performSearchAndSelect(Employer employer) {
        performSearch(employer);
        selectResultAndReturn();
    }

    public String clickEmployerName() {
        String nameEmployer = lnkEmployerName.getText();
        openFirstSearchResult();
        return nameEmployer;
    }

    public String selectAndReturnEmployerResult(String companyName) {
        inputCompanyName(companyName);
        searchAndSelectResult();
        String result = getEmployerNameText();
        returnFromResults();
        return result;
    }

    public void inputCompanyName(String companyName) {
        txbCompanyName.type(companyName);
    }

    public void inputCityZip(String city, String zip) {
        txbAddressCity.type(city);
        txbZipCode.type(zip);
    }

    public void inputFein(String fein) {
        txbFein.type(fein);
    }

    public void selectCounty(String county) {
        cmbCounty.select(county);
    }

    public void selectNaics(String naics) {
        cmbNaics.select(naics);
    }

    public String getEmployerNameText() {
        return lnkEmployerName.getText();
    }

    public void validateFieldsCleared() {
        BaseButton.SERVICE_CENTER_LOOK_UP.getButton().waitForIsElementPresent();

        CustomAssertion.softTrue("Company name is not cleared", txbCompanyName.getText().equals(""));
        CustomAssertion.softTrue("FEIN is not cleared", txbFein.getText().equals(""));
        CustomAssertion.softTrue("City is not cleared", txbAddressCity.getText().equals(""));
        CustomAssertion.softTrue("County is not cleared", cmbCounty.getSelectedLabel().contains("Any"));
        CustomAssertion.softTrue("Zip is not cleared", txbZipCode.getText().equals(""));
        CustomAssertion.softTrue("NAICS is not cleared", cmbNaics.getSelectedLabel().contains("Any"));
    }
}
