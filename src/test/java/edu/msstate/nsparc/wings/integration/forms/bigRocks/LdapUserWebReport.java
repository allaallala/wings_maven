package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.customassertions.CustomAssertion;
import framework.elements.Div;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * Describes LDAP User Webreport Form.
 * Created by a.vnuchko on 05.04.2017.
 */
public class LdapUserWebReport extends BaseWingsForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";
    private TextBox txbUssu = new TextBox(By.id("username"), "Unemployment service system username");
    private Div dvAccessUserId = new Div(By.xpath(String.format(detailPath, "accessuserid:")), "Access User Id");
    private Div dvAccessRole = new Div(By.xpath(String.format(detailPath, "accessrolelist:")), "Access Role List");
    private Div dvObjectClass = new Div(By.xpath(String.format(detailPath, "objectClass:")), "Object Class");
    private static final String defaultClaim = "admin, claimstaker";
    private static final String objectClass = "accessuser, top";

    /**
     * Default constructor
     */
    public LdapUserWebReport() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Ldap User Webreport')]"), "LDAP User webreport form");
    }

    /**
     * Search for unemployment service system username
     * @param username - username
     */
    public void searchForUssu(String username) {
        txbUssu.type(username);
        clickButton(Buttons.Search);
    }

    /**
     * Validate searched user
     * @param username - username to search
     */
    public void validateSearchedUser(String username) {
        CustomAssertion.softAssertEquals(dvAccessUserId.getText(), username, "Incorrect Access User Id");
        CustomAssertion.softAssertEquals(dvAccessRole.getText(), defaultClaim, "Incorrect Access Role List");
        CustomAssertion.softAssertEquals(dvObjectClass.getText(), objectClass, "Incorrect Object Class");
    }
}
