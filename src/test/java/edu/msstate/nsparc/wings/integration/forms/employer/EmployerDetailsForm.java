package edu.msstate.nsparc.wings.integration.forms.employer;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Link;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Employer Profiles -> Search for Employer -> Open Employer record
 */
public class EmployerDetailsForm extends EmployerBaseForm {

    private TableCell tbcUsername = new TableCell(By.xpath("//form[@id='employer']//td[contains(.,'Unemployment Services System Username:')]"), "Unemployment username");
    private Link lnkTitle = new Link("css=span[class='float-left']", "Employer name");
    private TableCell tbcLocationAddress = new TableCell("//div[text()='Location Address' and @class='detail-label']/../../div | //b[text()='Location Address']/../../following-sibling::tr//td",
            "Location Address");
    private TableCell tbcEverifyAddress = new TableCell(By.xpath("//tr[@id='everifyInfo']/td[2]"), "Everify address info");
    private Button btnEditCompanyInformation = new Button("id=editCompanyInformation", "Edit Company Information");
    private Button btnEditContactInformation = new Button("id=editContactInformation", "Edit contact information");
    private Button btnEditEverifyInformation = new Button("id=editEverifyInformation", "Edit everify information");
    private Button btnExpandEverify = new Button(By.xpath("//a[@class='expand']"), "Expand everify information");
    private Button btnRemove = new Button(By.id("clearUsername"), "Remove username");
    private Button btnEditAccessMS = new Button(By.id("editAccessMs"), "Create ms account");
    private TableCell tbcCompanyName = new TableCell("//div[text()='Name:']/../../div", "Company Name");
    private TableCell tbcFEIN = new TableCell(By.xpath("//div[contains(.,'FEIN:')]"), "FEIN");
    private TableCell tbcEAN = new TableCell(By.xpath("//div[contains(.,'EAN:')]"), "EAN");
    private TableCell tbcNAICS = new TableCell(By.xpath("//div[contains(.,'NAICS:')]"), "NAICS");
    private TableCell tbcFederalContractor = new TableCell("//div[text()='Federal Contractor:']/../../div", "Federal Contractor");
    private TableCell tbcReHires = new TableCell("//div[text()='Re-hires:']/..", "Re-hires");
    private Span spnNotesCount = new Span("//span[@class='notesCount']", "Notes Count");
    private String regExp = "[^\\d]+";
    private String xTemplate = "XXXXX";

    /**
     * Default constructor
     */
    public EmployerDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Employer Detail')]"), "Employer Detail");
    }

    public EmployerDetailsForm(String companyName) {
        super(By.xpath(String.format("//div[@id='user-name'][contains(.,'%1$s')]", companyName)), "Employer Detail");
    }
    /**
     * This method is used to get Employer name from label at the top of form
     * @return Employer name
     */
    public String getEmployerName() {
        String text = lnkTitle.getText();
        String employerName = CommonFunctions.regexGetMatch(text, regExp);
        return employerName.trim();
    }

    /**
     * Open [Employer Edit] form
     * @return new [Employer Edit] form
     */
    public EmployerEditForm openEmployerEditForm() {
        clickEditCompany();
        return new EmployerEditForm();
    }

    /**
     * Validate employer data
     * @param employer - employer to check
     */
    public void validateData(Employer employer) {
        CustomAssertion.softTrue("Incorrect employer account", tbcUsername.getText().contains(employer.getEmployerAccount()));
        //Company information block
        CustomAssertion.softAssertContains(tbcFEIN.getText(), xTemplate  + employer.getFein().substring(5,8), "Incorrect FEIN");
        CustomAssertion.softAssertContains(tbcEAN.getText(), xTemplate + employer.getEan().substring(6, 9), "Incorrect EAN");
        CustomAssertion.softTrue("Incorrect NAICS", tbcNAICS.getText().contains(employer.getNaics()));
        //Location block
        CustomAssertion.softTrue("Incorrect line one", tbcLocationAddress.getText().contains(employer.getAddress().getLineOne()));
        CustomAssertion.softTrue("Incorrect city, state or zip", tbcLocationAddress.getText().contains(employer.getAddress().getCity() + ", "
            + employer.getAddress().getState() + " " + employer.getAddress().getZipCode()));
        CustomAssertion.softTrue("Incorrect county and country", tbcLocationAddress.getText().contains(employer.getAddress().getCounty()
                + " County United States"));
        //Everify block
        if (btnExpandEverify.isPresent()) {
            btnExpandEverify.click();
        }
        CustomAssertion.softTrue("Incorrect line one", tbcEverifyAddress.getText().contains(employer.getEverifyAddress().getLineOne()));
        CustomAssertion.softTrue("Incorrect city, state or zip", tbcEverifyAddress.getText().contains(employer.getEverifyAddress().getCity() + ", "
                + employer.getAddress().getState() + " " + employer.getAddress().getZipCode()));
        CustomAssertion.softTrue("Incorrect county and country", tbcEverifyAddress.getText().contains(employer.getEverifyAddress().getCounty()
                + " County United States"));
    }

    /**
     * Check some buttons present (or not) on the participantSSDetails page.
     * @param user - current user.
     * @param ifTide - if employer tide to the petition
     * @param neutral - if employer doesn't care if he is tied or not.
     */
    public void checkEmployerButtons(User user, Boolean ifTide, Boolean neutral) {
        if (ifTide || neutral) {
            ifButton(user.getEmployer().getEmployerEditCompanyInformation(), btnEditCompanyInformation);
            ifButton(user.getEmployer().getEmployerEditContactInformation(), btnEditContactInformation);
            ifButton(user.getEmployer().getEmployerEditEverifyInformation(), btnEditEverifyInformation);
        } else {
            ifButton(Constants.FALSE, btnEditCompanyInformation);
            ifButton(Constants.FALSE, btnEditContactInformation);
            ifButton(Constants.FALSE, btnEditEverifyInformation);
        }
    }

    /**
     * Remove username
     */
    public void removeUsername() {
        btnRemove.clickAndWait();
        CustomAssertion.softTrue("Username is not reset", tbcUsername.getText().contains("None Provided"));
    }

    /**
     * Create username for an employer
     * @param employer - employer
     */
    public void createUsername(Employer employer) {
        btnEditAccessMS.clickAndWait();
        EmployerEditForm editPage = new EmployerEditForm();
        editPage.inputUsername(employer);
    }

    /**
     * Click [Edit] for company information
     */
    public void clickEditCompany() {
        btnEditCompanyInformation.clickAndWait();
    }

    /**
     * Click [Edit] for contact information
     */
    public void clickEditContact() {
        btnEditContactInformation.clickAndWait();
    }

    /**
     * Click [Edit] for everify information
     */
    public void clickEditEverify() {
        btnEditEverifyInformation.clickAndWait();
    }
    /**
     * Get location address text on search table
     * @return location address text.
     */
    public String getLocationAddressText() {
        return tbcLocationAddress.getText();
    }
    /**
     * Get company name text on search table
     * @return company name text.
     */
    public String getCompanyNameText() {
        return tbcCompanyName.getText().trim();
    }
    /**
     * Get FEIN text on search table
     * @return FEIN text.
     */
    public String getFeinText() {
        return tbcFEIN.getText().trim();
    }
    /**
     * Get EAN text on search table
     * @return EAN text.
     */
    public String getEanText() {
        return tbcEAN.getText().trim();
    }
    /**
     * Get NAICS text on search table
     * @return NAICS text.
     */
    public String getNaicsText() {
        return tbcNAICS.getText().trim();
    }
    /**
     * Get Federal contractor text on search table
     * @return Federal contractor text.
     */
    public String getFederalContractorText() {
        return tbcFederalContractor.getText().trim();
    }
    /**
     * Get Rehires text on search table
     * @return rehires text.
     */
    public String getRehiresText() {
        return tbcReHires.getText().trim();
    }

    /**
     * Get notes count on the page
     * @return notes count.
     */
    public String getNotesCount() {
        return spnNotesCount.getText();
    }
}
