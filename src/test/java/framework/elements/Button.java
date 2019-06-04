package framework.elements;

import org.openqa.selenium.By;

/**
 * Button
 */
public class Button extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of the element
     * @param name - name of the element
     */
    public Button(final String locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of the element
     * @param name - name of the element
     */
    public Button(final By locator, final String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "button";
    }
}
