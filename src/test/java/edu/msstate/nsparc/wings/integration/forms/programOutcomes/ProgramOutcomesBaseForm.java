package edu.msstate.nsparc.wings.integration.forms.programOutcomes;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Link;
import org.openqa.selenium.By;

/**
 * This is the base form for Program Outcomes forms
 */
public class ProgramOutcomesBaseForm extends BaseWingsForm {

    private String particialXpath = "css=a[href='%1$s']";
    private Link lnkFirstQuarter = new Link(String.format(particialXpath, "#firstQuarter"), "First Quarter");
    private Link lnkSecondQuarter = new Link(String.format(particialXpath, "#secondQuarter"), "Second Quarter");
    private Link lnkThirdQuarter = new Link(String.format(particialXpath, "#thirdQuarter"), "Second Quarter");
    private Link lnkFourthQuarter = new Link(String.format(particialXpath, "#fourthQuarter"), "Third Quarter");

    /**
     * Constructor of the form with specified locator
     * @param locator - locator of the form
     * @param formTitle - title of the page.
     */
    public ProgramOutcomesBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Click fourth quarter
     */
    public void clickFourthQuarter(){
        lnkFourthQuarter.click();
    }

    /**
     * Get first quarter text
     * @return first quarter text
     */
    public String getFirstQuarterText(){
        return lnkFirstQuarter.getText();
    }

    /**
     * Get second quarter text
     * @return second quarter text
     */
    public String getSecondQuarterText(){
        return lnkSecondQuarter.getText();
    }

    /**
     * Get third quarter text
     * @return third quarter text
     */
    public String getThirdQuarterText(){
        return lnkThirdQuarter.getText();
    }

    /**
     * Get fourth quarter text
     * @return fourth quarter text
     */
    public String getFourthQuarterText(){
        return lnkFourthQuarter.getText();
    }
}
