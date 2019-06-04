package framework.elements;

import org.openqa.selenium.By;

/**
 * Embed
 */
public class Embed extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of the element
     */
    public Embed (final By locator) {
       super(locator);
    }

    public Embed(final String locator, String name){
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "embed";
    }

}
