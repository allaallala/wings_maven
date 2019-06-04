package framework.elements;
import org.openqa.selenium.By;

/**
 * Radio button
 */
public class RadioButton extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of a radio button
     * @param name - name of radio button
     */
    public RadioButton(final String locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of a radio button
     * @param name - name of radio button
     */
    public RadioButton(final By locator, final String name) {
        super(locator, name);
    }

    public void check() {
        check(true);
    }

    /**
     * Set value
     * @param state value (true/false)
     */
    private void check(boolean state) {
       waitForIsElementPresent();
        info("Clicking radio-button");
        if (state && !element.isSelected()) {
            element.click();
        } else if (!state && element.isSelected()) {
            element.click();
        }
    }

    /**
     * Get the value of the radio button (true / false)
     */
    public boolean isChecked() {
        waitForIsElementPresent();
        return element.isSelected();
    }

    /**
     * Set the radio button to false
     */
    public void uncheck() {
        check(false);
    }

    @Override
    protected String getElementType() {
        return "radio button";
    }
}
