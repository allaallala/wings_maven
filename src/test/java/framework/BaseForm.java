package framework;

import framework.elements.Div;
import org.openqa.selenium.By;
import webdriver.Browser;

import java.util.Date;

/**
 * Base class for any forms
 */
public abstract class BaseForm extends BaseEntity {

    protected String name;

    /**
     * name="titleLocator" - locator of the form
     */
    private By titleLocator; // detect form opening locator
    /**
     * name="title"
     */
    protected String title; // title of a form

    /**
     * Constructor. Return form by title locator and title
     * @param titleLocator - title locator
     * @param title - title
     */
    protected BaseForm(final By titleLocator, final String title) {
        init(titleLocator, title);
        assertIsOpen();
    }

    /**
     * Constructor
     * @param url - url to navigate
     * @param titleLocator - locator of the form
     * @param title - title of the form
     */
    protected BaseForm(final String url, final By titleLocator, final String title) {
        init(titleLocator, title);
        Browser.getInstance().navigate(url);
        //assertIsOpen();
    }

    /**
     * Gets locator name by title locator and title
     * @param titleLocator - title locator
     * @param title - title
     */
    private void init(final By titleLocator, final String title) {
        this.titleLocator = titleLocator;
        this.title = title;
        this.name = String.format("'%1$s' form", this.title);
    }

    /**
     * Asserts, that form is opened.
     */
    public void assertIsOpen() {
        long before = new Date().getTime();
        Div divForm = new Div(titleLocator, title);
        try {
            divForm.waitForIsElementPresent();
            long openTime = new Date().getTime() - before;
            info(String.format("The form '%1$s' appears in '%2$s' milliseconds", title, openTime));

        } catch (Throwable e) {
            BaseEntity.getLogger().info(e);
            fatal(String.format("The form '%1$s' is not appeared", title));
        }
    }

    @Override
    protected String formatLogMessage(final String message) {
        return String.format("%1$s %2$s %3$s", name, "|", message);
    }

    /**
     * Gets number of elements with specified locator present on the page.
     * @param locator - locator of the element
     * @return - the quantity of the elements with defined locator.
     */
    protected Integer getNumberOfElementsPresent(By locator) {
        return Browser.getDriver().findElements(locator).size();
    }
}
