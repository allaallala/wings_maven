package edu.msstate.nsparc.wings.integration.forms.home;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import framework.Logger;
import framework.customassertions.CustomAssertion;
import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
To open this form you need to log in as Staff
 */
public class StaffLocationForm extends BaseWingsForm {

    private ComboBox cmbEligibleWorkplaces = new ComboBox("id=selectedOffice", "Eligible Workplaces");
    private Link lnkParticipantsMenuHeader = new Link("//li[1]/a[text()='Participants']", "Participant menu header");
    private Link lnkEmployersMenuHeader = new Link("//li[2]/a[text()='Employers']", "Employers menu header");
    private Link lnkReportsMenuHeader = new Link("//li[3]/a[text()='Reports']", "Report menu header");
    private Link lnkAdvancedMenuHeader = new Link("//li[4]/a[text()='Advanced']", "Advance menu header");
    private TableCell tbcFirstAndLastNameLabel = new TableCell(By.xpath("//table[@id='contentContainer']//strong"), "User Name");

    public StaffLocationForm() {
        super(By.xpath("//select[@id='selectedOffice']"), "Staff Location");
    }

    public String getFirstLastNameText() {
        return tbcFirstAndLastNameLabel.getText();
    }

    private boolean validateFirstAndLastName() {
        //get user names from Location form
        String userName = getFirstLastNameText();
        // get user names from db
        String userNameFromDB = ParticipantSqlFunctions.getUserFirstAndLastNameFromDB();
        if (userName == null) {
            Logger.getInstance().info("User Name was not found on page!");
            return false;
        } else {
            return userName.equals(userNameFromDB);
        }
    }

    public void validateHeaderData() {
        CustomAssertion.softTrue("Assert First and Last Name in Header", validateFirstAndLastName());
        CustomAssertion.softTrue("Assert Participant Menu Header Failed", lnkParticipantsMenuHeader.isPresent());
        CustomAssertion.softTrue("Assert Employers Menu Header Failed", lnkEmployersMenuHeader.isPresent());
        CustomAssertion.softTrue("Assert Reports Menu Header Failed", lnkReportsMenuHeader.isPresent());
        CustomAssertion.softTrue("Assert Advanced Menu Header Failed", lnkAdvancedMenuHeader.isPresent());
    }

    public String getEligibleWorkplace() {
        return cmbEligibleWorkplaces.getText();
    }

    public void selectWorkplace(String workPlace) {
        cmbEligibleWorkplaces.select(workPlace);
        clickButton(Buttons.Continue);
    }

    public void validateWorkPlace(String workPlace) {
        Link lnkWorkPlace = new Link(By.xpath(String.format("//strong[contains(.,'%1$s')]",workPlace)), "Workplace of the logged user");
        CustomAssertion.softTrue("Incorrect workplace of the user", lnkWorkPlace.isPresent());
    }
}
