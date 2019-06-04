package edu.msstate.nsparc.wings.integration.forms.callIn;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
 * Call In edit form.
 * Created by a.vnuchko on 30.06.2016.
 */
public class CallInEditForm extends CallInBaseForm {

    private ComboBox cbResult = new ComboBox(By.id("callInResult"), "Call in Result");
    /**
     * Constructor of the form with specified locator
     */
    public CallInEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Call In Edit')]"), "Call In Detail");
    }


    /**
     * Edit result
     * @param result - new result
     */
    public void editResult(String result) {
        cbResult.select(result);
        clickButton(Buttons.Save);
    }
}
