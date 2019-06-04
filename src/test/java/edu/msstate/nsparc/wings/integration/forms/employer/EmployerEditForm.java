package edu.msstate.nsparc.wings.integration.forms.employer;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.CommonFunctions;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Employer Profiles -> Search for Employer -> Open Employer -> Click on Edit
 */
public class EmployerEditForm extends EmployerBaseForm {

    private TextBox txbCompanyName = new TextBox("css=input#employerName","Company Name");
    private TextBox txbFEIN = new TextBox("css=input#federalId","FEIN_LENGTH");
    private TextBox txbEAN = new TextBox("css=input#stateId","EAN");
    private TextBox txbCity = new TextBox(By.id("contactInformationByEmployerContactInfo.addressByResidenceAddr.city"), "City");
    private TextBox txbEverifyLineOne = new TextBox(By.id("everifyAddress.lineOne"), "Everify line one");
    private RadioButton rbPreviousHiredIndividualsYes = new RadioButton("css=input#doesRehire1","Employer re-hire previously hired individuals - Yes");
    private RadioButton rbFederalContractorYes = new RadioButton("css=input#hasFedContrListing1","Is the employer a federal contractor - Yes");
    private RadioButton rbFederalContractorNo = new RadioButton("css=input#hasFedContrListing2","Is the employer a federal contractor - No");
    private TextBox txbSystemUsername = new TextBox(By.id("tmpUsername"), "Username");

    /**
     * Default constructor
     */
    public EmployerEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Employer Edit')]"), "Employer Edit");
    }

    public EmployerEditForm(String employer) {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Editing Company Information')]"), "Employer Edit for employer S-S");
    }
    /**
     * This method is used to change company name and then save changes
     * @return New company name
     */
    public String editCompanyNameAndSaveChanges() {
        String editedCompanyName = String.format("Edited company %1$s", CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH));
        txbCompanyName.clear();
        txbCompanyName.type(editedCompanyName);
        rbPreviousHiredIndividualsYes.click();
        clickAndWait(BaseButton.SAVE_CHANGES);
        return editedCompanyName;
    }

    /**
     * This method is used to change Federal Contractor status
     * @param isFederalContractor New federal contractor status
     */
    public void editFederalContractor (boolean isFederalContractor) {
        if (isFederalContractor) {
            rbFederalContractorYes.click();
        } else {
            rbFederalContractorNo.click();
        }
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    /**
     * Editing all fields on Company Information page
     * @param employer Object with new Employer data
     */
    public void editFullCompanyInformation(Employer employer) {
        // Implementing a workaround because of WINGS-6335
        inputSelectNaics(employer.getNaics());

        txbCompanyName.type(employer.getCompanyName());
        txbEAN.type(employer.getEan());
        txbFEIN.type(employer.getFein());

        rbFederalContractorYes.click();
        rbPreviousHiredIndividualsYes.click();

        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    /**
     * Edit company name and FEIN
     * @param employer - employer
     */
    public void editCompanyFeinEan(Employer employer) {
        String newCompanyName = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        String newFein = CommonFunctions.getRandomIntegerNumber(Constants.FEIN_LENGTH);
        String newEan = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        txbCompanyName.type(newCompanyName);
        txbFEIN.type(newFein);
        txbEAN.type(newEan);
        employer.setCompanyName(newCompanyName);
        employer.setFein(newFein);
        employer.setEan(newEan);
    }

    /**
     * Edit city in the address
     * @param employer - employer
     */
    public void editCity(Employer employer) {
        String newCity = "Viller boucage";
        txbCity.type(newCity);
        employer.getAddress().setCity(newCity);
    }

    /**
     * Edit everify line one
     * @param employer - employer
     */
    public void editEverifyLineOne(Employer employer) {
        String newLineOne = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        employer.getEverifyAddress().setLineOne(newLineOne);
        txbEverifyLineOne.type(newLineOne);
    }

    /**
     * Input username
     * @param employer - employer
     */
    public void inputUsername(Employer employer) {
        txbSystemUsername.type(employer.getEmployerAccount());
    }
}
