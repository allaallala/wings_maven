package framework.elements;

import org.openqa.selenium.By;

/**
 * Div
 */
public class Div extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of the div
     */
    public Div(final By locator) {
        super(locator);
    }

    /**
     * Constructor of the div
     * @param locator - locator div
     * @param name - name div
     */
    public Div(final By locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor for migrating from Selenium RC -> Selenium WebDriver
     * @param locator - locator div
     * @param name - name div
     */
    public Div(final String locator, final String name){
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "div";
    }
}
