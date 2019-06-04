package framework.elements;

import org.openqa.selenium.By;

/**
 * H1 header
 */
public class H1 extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of h1
     */
    public H1 (final By locator) {
        super(locator);
    }

    /**
    * Constructor
    * @param locator - locator of H1
    * @param name - name of H1
    */
    public H1 (final String locator, final String name) {
        super(locator, name);
    }
    public H1 (final By locator, final String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "h1";
    }
}
