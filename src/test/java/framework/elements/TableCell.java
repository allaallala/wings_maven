package framework.elements;

import org.openqa.selenium.By;

/**
 * Element of the table
 */
public class TableCell extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of a table cell
     */
    public TableCell(final By locator) {
        super(locator);
    }

    /**
     * Constructor
     * @param locator - locator of a table cell
     * @param name - name of a table cell
     */
    public TableCell(final String locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of a table cell
     * @param name - name of a table cell
     */
    public TableCell(final By locator, final String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "tablecell";
    }
}
