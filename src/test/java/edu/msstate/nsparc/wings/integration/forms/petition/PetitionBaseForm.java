package edu.msstate.nsparc.wings.integration.forms.petition;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.RadioButton;
import framework.elements.TextArea;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Petition forms
 */
public class PetitionBaseForm extends BaseWingsForm {

    protected TextBox txbPetitionNumber = new TextBox("css=input[id='petitionNumber']", "Petition Number");
    protected RadioButton rbCertified = new RadioButton("//input[@name='petitionStatus' and @value='CERTIFIED']", "Status - Certified");
    protected RadioButton rbDenied = new RadioButton("//input[@name='petitionStatus' and @value='DENIED']", "Status - Denied");
    protected RadioButton rbTerminated = new RadioButton("//input[@name='petitionStatus' and @value='TERMINATED']", "Status - Terminated");
    protected TextBox txbFileDate = new TextBox("css=input[id='dateFile']", "File Date");
    // Also may be displayed as Certification date
    protected TextBox txbDecisionDate = new TextBox("css=input[id='dateDecision']", "Decision Date");
    protected TextBox txbImpactDate = new TextBox("css=input[id='dateImpact']", "Impact Date");
    protected TextArea txaDepartment = new TextArea("css=textarea[id='department']", "Department");
    protected TextArea txaTypeOfWork = new TextArea("css=textarea[id='typeOfWork']", "Type Of Work");

    /**
     * Constructor of the form with defined locator
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    public PetitionBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }
}
