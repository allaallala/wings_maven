package framework.elements;

import org.openqa.selenium.By;

/**
 * Link
 */
public class Link extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of a link
     * @param name - name of a link
     */
    public Link(final String locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of a link
     * @param name - name of a link
     */
    public Link(final By locator, final String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "hyperlink";
    }
}
