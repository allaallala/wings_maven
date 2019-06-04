package edu.msstate.nsparc.wings.integration.forms.petition;

import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Trade -> Petitions -> Create
 */
public class PetitionCreationForm extends PetitionBaseForm {

    private RadioButton rbInState = new RadioButton("css=input[id='isInState1']", "In-State Petition");
    private RadioButton rbOutOfState = new RadioButton("css=input[id='isInState2']", "Out-of-State Petition");
    private TextBox txbFein = new TextBox("css=input[id='federalId']", "FEIN_LENGTH");
    private TextBox txbCompanyName = new TextBox("css=input[id='companyName']", "Company Name");
    private Button btnRetrieveEmployerInformation = new Button("css=button[id='lookup']", "Retrieve Employer Information");
    private RadioButton rbAtaaCertified = new RadioButton("css=input[id='isATAACertified1']", "ATAA Certified");

    /**
     * Default constructor
     */
    public PetitionCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Petition Creation')]"), "Petition Creation");
    }

    /**
     * Entering Petition number and navigating to Employer creationg screen
     * @param petition Object with Petition data
     */
    public void startCreatingOutOfState(Petition petition) {
        rbOutOfState.click();
        txbPetitionNumber.type(petition.getNumber());
        txbCompanyName.type(petition.getEmployer().getCompanyName());
        btnRetrieveEmployerInformation.clickAndWait();
    }

    /**
     * Filling out form fields with provided data
     * @param petition Object with Petition data
     */
    public void fillOutCreationForm(Petition petition) {
        if (!petition.isOutOfState()) {
            rbInState.click();
            txbPetitionNumber.type(petition.getNumber());
            txbFein.type(petition.getEmployer().getFein());
            btnRetrieveEmployerInformation.clickAndWait();
        }

        BaseOtherElement.ERROR_MESSAGE.getElement().assertIsNotPresent();

        switch (petition.getStatus()) {
            case CERTIFIED:
                rbCertified.click();
                break;
            case DENIED:
                rbDenied.click();
                break;
            case TERMINATED:
                rbTerminated.click();
                break;
            default:
                break;
        }

        txaDepartment.type(petition.getDepartment());
        txaTypeOfWork.type(petition.getTypeOfWork());
        txbFileDate.type(petition.getFileDate());
        txbDecisionDate.type(petition.getDecisionDate());
        txbImpactDate.type(petition.getImpactDate());

        if (rbAtaaCertified.isPresent()) {
            rbAtaaCertified.click();
        }
    }
}
