package framework.elements;

import org.openqa.selenium.By;

/**
 * Textarea
 */
public class TextArea extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of a textarea
     * @param name - name of a textarea
     */
     public TextArea(final String locator, final String name) {
            super(locator, name);
        }
    public TextArea(final By locator, final String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "text area";
    }
}
