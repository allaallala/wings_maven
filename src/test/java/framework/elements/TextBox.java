package framework.elements;

import org.openqa.selenium.By;

/**
 * Textbox
 */
public class TextBox extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of the textbox
     * @param name - name of the textbox
     */
    public TextBox(final String locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of the textbox
     * @param name - name of the textbox
     */
    public TextBox(final By locator, final String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "text box";
    }

    /**
     * Clear text in the field.
     */
    public void clear(){
        waitForIsElementPresent();
        info("Clear text in the element");
        element.clear();
    }
}

