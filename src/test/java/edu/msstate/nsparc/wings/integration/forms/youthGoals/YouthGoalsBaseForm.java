package edu.msstate.nsparc.wings.integration.forms.youthGoals;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Youth Goals forms
 */
public class YouthGoalsBaseForm extends BaseWingsForm {

    private ComboBox cmbGoalType = new ComboBox("css=select#goalType", "Goal Type");

    /**
     * Constructor of the form with defined locator
     *
     * @param locator   - locator of the form
     * @param formTitle - title of the form.
     */
    public YouthGoalsBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Select goal type
     *
     * @param goalType - goal type
     */
    public void selectGoalType(String goalType) {
        cmbGoalType.select(goalType);
    }
}
