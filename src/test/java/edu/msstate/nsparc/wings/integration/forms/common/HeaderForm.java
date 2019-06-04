package edu.msstate.nsparc.wings.integration.forms.common;

import framework.BaseForm;
import framework.elements.Link;
import org.openqa.selenium.By;

public class HeaderForm extends BaseForm {

    private Link LOGOUT_LINK = new Link("//a[text()='Logout'] | //a[text()='Log Out'] | //a[text()[contains(.,'Logout')]] | "
            + "//a[text()[contains(.,'Log Out')]]", "Logout");
    private Link HOME_LINK = new Link(By.xpath("//a[contains(.,'Home')]"), "Home");

    public HeaderForm() {
        super(By.xpath("//div[contains(.,'Home')]"), "Header form");
    }

    public void logOut(){
        LOGOUT_LINK.clickAndWait();
    }

    public  void goHome(){
        HOME_LINK.clickAndWait();
    }
}
