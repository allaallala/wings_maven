package edu.msstate.nsparc.wings.integration.forms.petition;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Trade -> Petitions -> Search
 */
public class AffectedEmployeesForm extends PetitionBaseForm {

    private TextBox txbFirstName = new TextBox("css=input[id='firstName']", "First Name");
    private TextBox txbLastName = new TextBox("css=input[id='lastName']", "Last Name");
    private TextBox txbSsn = new TextBox("css=input[id='ssn']", "SSN");
    private Button btnAdd = new Button("//button[text()='Add']", "Add");
    private TableCell tbcEmployeeInformation = new TableCell(By.xpath("//table[@id='results-table']//tbody//tr"), "Employee Information");

    /**
     * Default constructor
     */
    public AffectedEmployeesForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Manage Affected Employees')]"), "Manage Affected Employees");
    }

    /**
     * Searching for Employee
     * @param participant Employee participantSSDetails
     */
    public void performSearch(Participant participant) {
        txbFirstName.type(participant.getFirstName());
        txbLastName.type(participant.getLastName());
        txbSsn.type(participant.getSsn());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for validating first search result
     * @param participant Expected information
     */
    public void validateEmployeeInformation(Participant participant) {
        CustomAssertion.softAssertContains(tbcEmployeeInformation.getText(), participant.getFirstName(), "Incorrect participant first name");
        CustomAssertion.softAssertContains(tbcEmployeeInformation.getText(), participant.getLastName(), "Incorrect participant last name");
        CustomAssertion.softAssertContains(tbcEmployeeInformation.getText(), participant.getSsn(), "Incorrect participant ssn");
    }

    /**
     * Add employee to list.
     */
    public void addEmployeeToList(){
        btnAdd.clickAndWait();
    }
}
