package framework.elements;

/**
 * Menu elements
 */
public class MenuItem extends BaseElement {

    protected static final String DELIMITER = " -> ";

    /**
     * Constructor
     */
    protected MenuItem() {
    }

    /**
     * Constructor
     * @param locator - locator of menu
     * @param names - name of menu
     */
    public MenuItem(final String locator, final String[] names) {
        super(locator, getName(names));
    }



    /**
     * Gets name of an element
     * @param names - massive of names
     * @return name
     */
    protected static String getName(final String[] names) {
        StringBuilder result = new StringBuilder(names[0]);
        for (int i = 1; i < names.length; i++) {
            result.append(DELIMITER);
            result.append(names[i]);
        }
        return result.toString();
    }

    @Override
    protected String getElementType() {
        return "menu item";
    }
}
