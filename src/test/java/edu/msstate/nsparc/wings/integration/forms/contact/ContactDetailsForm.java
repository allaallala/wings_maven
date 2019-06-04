package edu.msstate.nsparc.wings.integration.forms.contact;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Wagner-Peyser -> Contacts -> Search for Contact -> Open Contact
 */
public class ContactDetailsForm extends ContactBaseForm {

    private String xpathExpression = "//td[contains(.,'%1$s')]/following-sibling::td";
    private TableCell tbcEmployerLabel = new TableCell(By.xpath("//td[contains(.,'Employer')]/following-sibling::td/span"), "Employer Name");
    private TableCell tbcContactMethodLabel = new TableCell(By.xpath(String.format(xpathExpression,"Contact Method")), "Contact Method");
    private TableCell tbcContactDateLabel = new TableCell(By.xpath(String.format(xpathExpression,"Contact Date")), "Contact Date");
    private TableCell tbcJobDevelopmentLabel = new TableCell(By.xpath(String.format(xpathExpression,"Job Development")), "Job Development");
    private Link lnkPermalink = new Link("css=label[id='permalinkLink'] a", "Permalink");
    private TableCell tbcPermalink = new TableCell("css=table[id='permalink'] a","Pemalink");
    private Link lnkClose = new Link("css=img[title='Close Permalink']", "Close permalink popup");
    private TableCell tbcDescription = new TableCell(By.xpath("//tr[contains(.,'Description')]/following-sibling::tr[1]"), "Description");
    private TableCell tbcResult = new TableCell(By.xpath("//tr[10][contains(.,'Result')]/following-sibling::tr[1]/td"), "Result");

    public ContactDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Contact Detail')]"),"Contact Detail");
    }

    public String[] getContactDetails() {
        return new String[] {tbcEmployerLabel.getText(),tbcContactMethodLabel.getText(), tbcContactDateLabel.getText(),tbcJobDevelopmentLabel.getText()};
    }

    public String getPermalinkAndCloseIt() {
        lnkPermalink.click();
        String permalink = tbcPermalink.getText();
        lnkClose.click();
        return permalink;
    }

    public String getEmployerLabelText() {
        return tbcEmployerLabel.getText();
    }

    public String getContactMethodLabelText() {
        return tbcContactMethodLabel.getText();
    }

    public void validateDataChanges(String contactMethod, String description, String result) {
        CustomAssertion.softAssertEquals(tbcContactMethodLabel.getText(),contactMethod,"Assert Contact Method Failed");
        CustomAssertion.softAssertEquals(tbcContactDateLabel.getText(), CommonFunctions.getYesterdayDate(),"Assert Contact Date Failed");
        CustomAssertion.softAssertEquals(tbcJobDevelopmentLabel.getText(), Constants.NO_ANSWER,"Assert Job Development Failed");
        CustomAssertion.softAssertEquals(tbcDescription.getText(), description, "Assert Description Failed");
        CustomAssertion.softAssertEquals(tbcResult.getText(), result,"Assert Result Failed");
    }

    public void validateBaseParameters(Employer employer, String contactMethod, String contactDate) {
        CustomAssertion.softAssertEquals(tbcEmployerLabel.getText(), employer.getCompanyName(), "Incorrect employer company");
        CustomAssertion.softAssertEquals(tbcContactMethodLabel.getText(), contactMethod, "Incorrect employer company");
        CustomAssertion.softAssertEquals(tbcContactDateLabel.getText(), contactDate, "Incorrect employer company");
        CustomAssertion.softAssertEquals(tbcJobDevelopmentLabel.getText(), Constants.YES_ANSWER, "Incorrect job development label");
    }
}
