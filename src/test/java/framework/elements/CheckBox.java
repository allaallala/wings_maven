package framework.elements;

import org.openqa.selenium.By;

/**
 * Checkbox class
 */
public class CheckBox extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of the checkbox
     * @param name - name of the checkbox
     */
    public CheckBox(final String locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of the checkbox
     * @param name - name of the checkbox
     */
    public CheckBox(final By locator, final String name) {
        super(locator, name);
    }

    public void check() {
        check(true);
    }

    private void check(boolean state) {
        waitForIsElementPresent();
        info("Clicking radio-button");
        if (state && !element.isSelected()) {
            element.click();
        } else if (!state && element.isSelected()) {
            element.click();
        }
    }

    public boolean isChecked() {
        waitForIsElementPresent();
        return element.isSelected();
    }

    public void uncheck() {
        check(false);
    }

    @Override
    protected String getElementType() {
        return "check box";
    }
}
