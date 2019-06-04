package framework.elements;

import org.openqa.selenium.By;

/**
 * Span
 */
public class Span extends BaseElement {

    /**
     * Constructor
     * @param locator locator of a span
     */
    public Span (final By locator) {
        super(locator);
    }

    /**
     * Constructor
     * @param locator - locator of a span
     * @param name - name of a span
     */
    public Span (final String locator, String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of a span
     * @param name - name of a span
     */
    public Span (final By locator, String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "span";
    }
}
