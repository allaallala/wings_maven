package edu.msstate.nsparc.wings.integration.forms.petition;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Trade -> Petition -> Search for record -> Open record
 */
public class PetitionDetailsForm extends PetitionBaseForm {

    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcEmployer = new TableCell(By.xpath("//td[contains(.,'Employer')]/following-sibling::td/span"), "Employer");
    private TableCell tbcPetitionNumber = new TableCell(By.xpath(String.format(detailPath, "Petition Number")), "Petition Number");
    private TableCell tbcStatus = new TableCell(By.xpath(String.format(detailPath, "Status:")), "Status");
    private TableCell tbcLocation = new TableCell(By.xpath("//form[@id='command']//tr[contains(.,'Location')]/following-sibling::tr[1]/td"), "Location address");
    private TableCell tbcDepartment = new TableCell(By.xpath(String.format(detailPath, "Department:")), "Department");
    private TableCell tbcTypeOfWork = new TableCell(By.xpath(String.format(detailPath, "Type of Work:")), "Type of Work");
    private TableCell tbcFileDate = new TableCell(By.xpath(String.format(detailPath, "File Date:")), "File Date");
    private TableCell tbcCertificationDate = new TableCell(By.xpath(String.format(detailPath, "Certification Date")), "Certification Date");
    private TableCell tbcImpactDate = new TableCell(By.xpath(String.format(detailPath, "Impact Date")), "Impact Date");
    private TableCell tbcExpirationDate = new TableCell(By.xpath(String.format(detailPath, "Expiration Date")), "Expiration Date");
    private Button btnManageAffectedEmployees = new Button("css=button[id='manageAffectedEmployees']", "Manage Affected Employees");

    /**
     * Default constructor
     */
    public PetitionDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Petition Detail')]"), "Petition Detail");
    }

    /**
     * Validating displayed Petition information
     * @param petition Object with Petition information
     */
    public void validateInformation(Petition petition) {
        checkField(tbcEmployer, petition.getEmployer().getCompanyName(), Constants.TRUE);
        checkField(tbcPetitionNumber, petition.getNumber(), Constants.TRUE);
        switch (petition.getStatus()) {
            case CERTIFIED:
                checkField(tbcCertificationDate, petition.getDecisionDate(), Constants.TRUE);
                break;
            case DENIED:
                //
                break;
            case TERMINATED:
                //
                break;
            default:
                break;
        }
        checkField(tbcImpactDate, petition.getImpactDate(), Constants.TRUE);
    }

    /**
     * Validate all data for petition
     * @param petit - petition
     */
    public void validateAllData(Petition petit) {
        Integer days = 730;
        //common
        CustomAssertion.softAssertEquals(tbcEmployer.getText(), petit.getEmployer().getCompanyName(), "Incorrect employer");
        CustomAssertion.softAssertEquals(tbcPetitionNumber.getText(), petit.getNumber(), "Incorrect petition number");
        CustomAssertion.softAssertEquals(tbcStatus.getText(), petit.getStatus().toString(), "Incorrect status");
        //location
        CustomAssertion.softAssertContains(tbcLocation.getText(), petit.getEmployer().getAddress().getLineOne(), "Incorrect line one");
        CustomAssertion.softAssertContains(tbcLocation.getText(), petit.getEmployer().getAddress().getCity() + ", "
                + petit.getEmployer().getAddress().getState() + " " + petit.getEmployer().getAddress().getZipCode(), "Incorrect city, state, zip code");
        CustomAssertion.softAssertContains(tbcLocation.getText(), petit.getEmployer().getAddress().getCounty() + " County United States", "Incorrect county, country");
        //other
        CustomAssertion.softAssertEquals(tbcDepartment.getText(), petit.getDepartment(), "Incorrect department");
        CustomAssertion.softAssertEquals(tbcTypeOfWork.getText(), petit.getTypeOfWork(), "Incorrect type of work");
        CustomAssertion.softAssertEquals(tbcFileDate.getText(), petit.getFileDate(), "Incorrect file date");
        CustomAssertion.softAssertEquals(tbcCertificationDate.getText(), petit.getDecisionDate(), "Incorrect certification date");
        CustomAssertion.softAssertEquals(tbcImpactDate.getText(), petit.getImpactDate(), "Incorrect impact date");
        CustomAssertion.softAssertEquals(tbcExpirationDate.getText(), CommonFunctions.getDaysNextDate(petit.getFileDate(), days), "Incorrect expiration date");
    }

    /**
     * Manage affected employees
     */
    public void manageAffectedEmployees(){
        btnManageAffectedEmployees.clickAndWait();
    }
}
