package edu.msstate.nsparc.wings.integration.forms.employer;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.NaicsSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.ContactInformationForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.elements.*;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Employer Profiles -> Create
 */
public class EmployerCreationForm extends EmployerBaseForm {
    private final String fein = "11-2222222";
    private final String naics = "Testing Laboratories";
    // Page #1
    private Button btnNaicsLookup = new Button("css=span[id='naicsLookup'] button", "NAICS lookup");
    private TextBox txbMSAccess = new TextBox("id=tmpUsername", "MS Username");
    private Span spnAccountError = new Span("css=span[id='tmpUsername.errors']", "MS Account Error");

    // Page #2
    private TextBox txbCompanyName = new TextBox("id=employerName", "Company Name");
    private TextBox txbFEIN = new TextBox("id=federalId", "FEIN_LENGTH");
    private TextBox txbEan = new TextBox("css=input[id='stateId']", "EAN");
    private RadioButton rbFederalContractorNo = new RadioButton("css=input[id='hasFedContrListing2']", "Federal Contractor - No");

    // Page #3
    private RadioButton rbSameAddressYes = new RadioButton("css=input[id='contactInformationByEmployerContactInfo.addressSame1']",
            "Mailing Address the same as Location Address");
    private Button btnCopyAddress = new Button("css=button[id='copyAddressAcross']",
            "Copy Address");
    private TextBox txbContactPhoneNumber = new TextBox("css=input[id='contactInformationByEmployerContactInfo.primaryPhone']",
            "Contact Phone Number");
    private Button btnApplyState = new Button(By.xpath("//input[@field='contactInformationByEmployerContactInfo.addressByMailingAddr.state']"),
            "Apply state");

    // Page #4
    private Button btnInsertLocationAddress = new Button("css=button[id='copyLocationAddress']", "Insert Location Address");
    private RadioButton rbEVerifyForLocation = new RadioButton(By.id("chooseAddress1"), "E-Verify Address - Location");

    //Address
    private TextBox txbCopiedLineOne = new TextBox(By.id("contactInformationByEmployerContactInfo.addressByMailingAddr.lineOne"), "Copied line one");

    //Popup
    private Div divPopupCancel = new Div(By.xpath("//div[contains(.,'Are you sure')]"), "Cancel popup");

    /**
     * Default constructor
     */
    public EmployerCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Employer Creation')]"), "Employer Creation");
    }

    /**
     * Creation form for employer SS
     * @param employerSs - employer ss
     */
    public EmployerCreationForm(String employerSs) {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Company Information')]"), String.format("Creation form for %1$s", employerSs));
    }


    /**
     * This method is used for selecting NAICS code using full search
     * @param title Title of the requested NAICS code
     */
    public void selectNaics(String title) {
        btnNaicsLookup.clickAndWait();
        new NaicsSearchForm().selectNaicsCode(title);
    }

    /**
     * This method is used to create standard Employer record in WINGS
     * @param employer Employer object with data that will be used during creation
     */
    public void createEmployer(Employer employer) {
        if (!employer.isOutOfStateEmployer()) {
            // account information
            txbMSAccess.type(employer.getEmployerAccount());
            clickButton(Buttons.Continue);
        }

        // basic information
        txbCompanyName.type(employer.getCompanyName());
        inputNaicsTemp(employer.getNaics());
        //selectNaics(employer.getNaics());
        if (!employer.isOutOfStateEmployer()) {
            txbFEIN.type(employer.getFein());
            txbEan.type(employer.getEan());
            rbFederalContractorNo.click();
        }
        clickButton(Buttons.Continue);

        // location information
        inputLocationCityZip(employer.getAddress().getLineOne(), employer.getAddress().getCity(), employer.getAddress().getZipCode());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        selectLocationState(employer.getAddress().getState());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        selectLocation(employer.getAddress().getCounty());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        rbSameAddressYes.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        clickButton(Buttons.Continue);

        rbEVerifyForLocation.click();

        clickAndWait(BaseButton.CREATE);

        if (!employer.isOutOfStateEmployer()) {
            clickAndWait(BaseButton.DONE);

        }
    }

    /**
     * This method is used to create standard employer using self-services forms
     * @param employer Employer object with data that will be used during creation
     */
    public void createEmployerSelfServices(Employer employer) {
        // basic information
        inputCompanyNameFein(employer.getCompanyName(), fein);
        inputSelectNaics(naics);

        clickButton(Buttons.Continue);

        // location information
        ContactInformationForm infoForm = new ContactInformationForm();
        infoForm.inputLocationCityZip(employer.getAddress().getLineOne(), employer.getAddress().getLineOne(), employer.getAddress().getCity(),
                employer.getAddress().getZipCode());
        infoForm.selectLocationState(employer.getAddress().getState());
        if (btnApplyState.isPresent()) {
            btnApplyState.clickAndWait();
            infoForm = new ContactInformationForm();
        }
        infoForm.selectLocationCounty(employer.getAddress().getCounty());
        infoForm.clickLocationCounty();

        btnCopyAddress.clickAndWait();
        if (!txbCopiedLineOne.getText().contains(employer.getAddress().getLineOne())) {
            btnCopyAddress.clickAndWait();
        }


        txbContactPhoneNumber.type(employer.getTelephoneNumber());
        clickButton(Buttons.Continue);

        btnInsertLocationAddress.clickAndWait();

        // finish creation
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    /**
     * Fill in some pages (company information, location)
     * @param employer - employer
     */
    public void fillInPagesUptoLocation(Employer employer) {
        txbMSAccess.type(employer.getEmployerAccount());
        clickButton(Buttons.Continue);
        // basic information
        txbCompanyName.type(employer.getCompanyName());
        selectNaics(employer.getNaics());
        txbFEIN.type(employer.getFein());
        txbEan.type(employer.getEan());
        rbFederalContractorNo.click();
        clickButton(Buttons.Continue);
        inputLocationCityZip(employer.getAddress().getLineOne(), employer.getAddress().getCity(), employer.getAddress().getZipCode());

    }

    /**
     * Input company name and fein number
     * @param companyName - company name
     * @param feinNumber - fein number
     */
    public void inputCompanyNameFein(String companyName, String feinNumber) {
        txbCompanyName.type(companyName);
        txbFEIN.type(feinNumber);
        rbFederalContractorNo.click();
    }

    /**
     * Choose 'yes' on 'no' in the pop-up.
     * @param type - yes/no
     */
    public void areYouSure(Popup type) {
        divPopupCancel.waitForIsElementPresent();
        if (type.getValue().contains(Constants.YES_ANSWER)) {
            clickAndWait(BaseButton.ARE_YOU_SURE_YES);
            divPopupCancel.waitForIsElementNotPresent();
        } else {
            clickAndWait(BaseButton.ARE_YOU_SURE_NO);
        }
    }

    /**
     * Check, that company text box is present and visible.
     * @return is element present or not.
     */
    public Boolean companyNamePresent() {
        return txbCompanyName.isPresent();
    }

    /**
     * Check, that warning message is present and visible.
     * @return is element present or not.
     */
    public Boolean warnMessagePresent() {
       return spnAccountError.isPresent();
    }
}
