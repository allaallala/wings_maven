package framework.elements;

import framework.BaseEntity;
import framework.CommonFunctions;
import framework.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import webdriver.Browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Base class for any elements
 */
public abstract class BaseElement extends BaseEntity {

    private static final String LINK = "link=";
    private static final String ID = "id=";
    private static final String CSS = "css=";
    private static final int TIMEOUT_WAIT_0 = 0;
    private Integer conditionTimeOut = Integer.valueOf(Browser.getTimeoutForCondition());
    public static final Div DIV_LOADING = new Div(By.xpath("//img[@id='loading-indicator'] | //h1[text()='Loading...']"), "Loading image");

    /**
     * @uml.property name="name"
     */
    protected String name;
    /**
     * @uml.property name="locator"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    protected By locator;
    /**
     * @uml.property name="element"
     * @uml.associationEnd
     */
    protected RemoteWebElement element;

    /**
     * The simple constructor, name will be extracted
     *
     * @param loc By Locator
     */
    protected BaseElement(final By loc) {
        locator = loc;
    }

    /**
     * The main constructor
     *
     * @param loc    By Locator
     * @param nameOf Output in logs
     */
    protected BaseElement(final By loc, final String nameOf) {
        locator = loc;
        name = nameOf;
    }

    /**
     * Constructor
     */
    protected BaseElement() {
    }

    /**
     * easy adapting from Selenium RC locators. CSS, ID, LINK
     *
     * @param stringLocator String locator
     * @param nameOfElement Name
     */
    protected BaseElement(String stringLocator, final String nameOfElement) {
        String clearLoc;
        if (stringLocator.contains(CSS)) {
            clearLoc = stringLocator.replaceFirst(CSS, "");
            locator = By.cssSelector(clearLoc);
            name = nameOfElement;
        } else if (stringLocator.contains(ID)) {
            clearLoc = stringLocator.replaceFirst(ID, "");
            locator = By.id(clearLoc);
            name = nameOfElement;
        } else if (stringLocator.contains(LINK)) {
            clearLoc = stringLocator.replaceFirst(LINK, "");
            locator = By.linkText(clearLoc);
            name = nameOfElement;
        } else {
            locator = By.xpath(stringLocator);
            name = nameOfElement;
        }
    }

    /**
     * @return RemoteWebElement
     * @uml.property name="element"
     */
    public RemoteWebElement getElement() {
        waitForIsElementPresent();
        return element;
    }

    /**
     * @param elementToSet RemoteWebElement
     * @uml.property name="element"
     */
    public void setElement(final RemoteWebElement elementToSet) {
        element = elementToSet;
    }

    /**
     * @return Locator
     * @uml.property name="locator"
     */
    public By getLocator() {
        return locator;
    }

    /**
     * The implementation of an abstract method for logging of BaseEntity
     *
     * @param message Message to display in the log
     * @return Formatted message (containing the name and type of item)
     */
    protected String formatLogMsg(final String message) {
        return String.format("%1$s '%2$s' %3$s %4$s", getElementType(), name, Logger.LOG_DELIMITER, message);
    }

    /**
     * Gets element type
     *
     * @return type of the element
     */
    protected abstract String getElementType();

    /**
     * Wait for element is present.
     */
    public void waitForIsElementPresent() {
        if (!isPresent(Integer.parseInt(Browser.getTimeoutForCondition()))) {
            Logger.getInstance().warn("=========================Additional Logs Start========================\n");
            Logger.getInstance().warn(new Div(By.xpath("//body"), "Page Text").getText());
            Logger.getInstance().warn("\n==========================Additional Logs End=========================\n");
            fatal("Element '" + name + "' with locator: '" + locator + "' is not displayed on the page");
        }
    }

    public void waitForIsElementNotPresent() {
        isNotPresent(Integer.valueOf(Browser.getTimeoutForCondition()));
        boolean isVisible;
        try {
            isVisible = element.isDisplayed();
        } catch (Exception | AssertionError ex) {
            BaseEntity.getLogger().debug(this, ex);
            isVisible = false;
        }
        Assert.assertFalse(isVisible, formatLogMsg(("Element is present")));
    }

    public void scrollTo() {
        String str = "window.scrollTo(0, %1$d)";
        waitForIsElementPresent();
        JavascriptExecutor jse = Browser.getDriver();
        jse.executeScript(String.format(str, getElement().getLocation().y));
    }

    /**
     * Clicks on the element
     */
    public void click() {
        DIV_LOADING.waitForNotVisible();
        waitForIsElementPresent();
        info("Click the element");
        Browser.getDriver().getMouse().mouseMove(element.getCoordinates());
        Browser.getDriver();
        ((JavascriptExecutor) Browser.getDriver()).executeScript("arguments[0].style.border='3px solid red'", element);
        element.click();
    }

    public void clickLogin() {
        waitForIsElementPresent();
        info("Click the element");
        Browser.getInstance().getDriver().getMouse().mouseMove(element.getCoordinates());
        if (Browser.getInstance().getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) Browser.getInstance().getDriver()).executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
        Browser.getInstance().waitForPageToLoad();
    }

    /**
     * Click on the element and wait page to load.
     */
    public void clickAndWait() {
        click();
        Browser.getInstance().waitForPageToLoad();
    }

    /**
     * Wait until element is clickable.
     */
    public void waitForElementClickable() {
        new WebDriverWait(Browser.getDriver(), Long.parseLong(Browser.getTimeoutForCondition())).until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until element is clickable and clicks on an item and waits for the page is loaded
     */
    public void waitClickAndWait() {
        waitForElementClickable();
        click();
        getBrowser().waitForPageToLoad();
    }

    public void doubleClick() {
        Actions act = new Actions(Browser.getInstance().getDriver());
        act.doubleClick();
    }

    public void submitByEnter() {
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Enter the text in the box
     *
     * @param value text
     */
    public void type(final String value) {
        DIV_LOADING.waitForNotVisible();
        typeLogin(value);
    }

    /**
     * Enter the text in the box
     *
     * @param value text
     */
    public void typeLogin(final String value) {
        waitForIsElementPresent();
        element.clear();
        hover();
        info(String.format("Typing text '%1$s'", value));
        element.sendKeys(value);
    }

    public ArrayList<WebElement> getAllElements() {
        return (ArrayList<WebElement>) Browser.getDriver().findElements(locator);
    }

    /**
     * Triggering 'Hover' event for element
     */
    public void hover() {
        int count = 0;
        while (count < 4) {
            try {
                waitForIsElementPresent();
                Actions actions = new Actions(Browser.getDriver());
                actions.moveToElement(getElement());
                actions.perform();
                count = count + 4;
            } catch (StaleElementReferenceException e) {
                BaseEntity.getLogger().info(e);
                count = count + 1;
            }
        }
    }

    public void moveMouse() {
        Coordinates coordinates = getElement().getCoordinates();
        info(String.format("Moving mouse to text '%1$s'", coordinates));
        Browser.getDriver().getMouse().mouseMove(coordinates);
    }

    public void clickJs() {
        Browser.getDriver().executeScript("arguments[0].click();", getElement());
        Browser.getInstance().waitForPageToLoad();
    }

    public boolean isSelected() {
        waitForIsElementPresent();
        return element.isSelected();
    }

    /**
     * Gets name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the item text (inner text).
     *
     * @return Text of element
     */
    public String getText() {
        waitForIsElementPresent();
        return element.getText();
    }

    /**
     * Gets value of the element
     *
     * @return value
     */
    public String getValue() {
        assertIsPresent();
        return element.getAttribute("value");
    }

    /**
     * Get value of the parameter 'href'
     *
     * @return href value
     */
    public String getHref() {
        assertIsPresent();
        return element.getAttribute("href");
    }

    /**
     * Check for is element present on the page.
     *
     * @return Is element present
     */
    public boolean isPresent() {
        return isPresent(TIMEOUT_WAIT_0);
    }

    /**
     * Check for is element present on the page.
     *
     * @return Is element present
     */
    public boolean isPresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), timeout);
        Browser.getDriver().manage().timeouts().implicitlyWait(TIMEOUT_WAIT_0, TimeUnit.SECONDS);
        try {
            if (wait.until((ExpectedCondition<Boolean>) this::elementDisplayed)) {
                return true;
            }
        } catch (Exception e) {
            BaseEntity.getLogger().debug(e);
        } finally {
            Browser.getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        }
        try {
            element = (RemoteWebElement) Browser.getDriver().findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            BaseEntity.getLogger().debug(e);
        }
        return false;
    }

    /**
     * Check for is element present on the page.
     *
     * @return Is element present
     */
    public boolean isNotPresent(int timeout) {
        WebDriverWait wait = new WebDriverWait(Browser.getDriver(), timeout);
        Browser.getDriver().manage().timeouts().implicitlyWait(TIMEOUT_WAIT_0, TimeUnit.SECONDS);
        try {
            wait.until((ExpectedCondition<Boolean>) this::elementNotDisplayed);
        } catch (Exception e) {
            return false;
        }
        try {
            Browser.getDriver().manage().timeouts().implicitlyWait(conditionTimeOut, TimeUnit.SECONDS);
            return elementNotDisplayed(Browser.getDriver());
        } catch (Exception e) {
            BaseEntity.getLogger().error(e);
        }
        return false;
    }

    /**
     * If web element is displayed
     *
     * @param driver - web driver
     * @return - web driver.
     */
    private Boolean elementNotDisplayed(final WebDriver driver) {
        try {
            List<WebElement> list = driver.findElements(locator);
            if (list.size() == 0) {
                return true;
            }
            for (WebElement el : list) {
                if (!el.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            BaseEntity.getLogger().error(e);
            return false;
        }
    }

    /**
     * If web element is displayed
     *
     * @param driver - web driver
     * @return - web driver.
     */
    private Boolean elementDisplayed(final WebDriver driver) {
        try {
            List<WebElement> list = driver.findElements(locator);
            for (WebElement el : list) {
                if (el.isDisplayed()) {
                    element = (RemoteWebElement) el;
                    return true;
                }
            }
        } catch (Exception e) {
            BaseEntity.getLogger().debug(e);
        }
        return false;
    }

    public void assertIsPresent() {
        if (!isPresent()) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            listWithScreenshots.get().add(screenshot);
            fatal(name + " doesn't exist: locator=" + locator + "\n" + String.format("Screenshot: %1$s", screenshot.getName()));
        }
    }

    public void assertIsNotPresent() {
        if (isPresent()) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            listWithScreenshots.get().add(screenshot);
            fatal(name + " is present: locator=" + locator + "\n" + String.format("Screenshot: %1$s", screenshot.getName()));
        }
    }

    /**
     * Fail, if element present, but only after the test.
     */
    public void softIsNotPresent() {
        if (isPresent(TIMEOUT_WAIT_0)) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            listWithScreenshots.get().add(screenshot);
            getErrorsList().add(name + " exist: locator=" + locator +"\n"+ String.format("Screenshot: %1$s", screenshot.getName()));
        }
    }

    /**
     * Fail, if element is not present, but after test.
     */
    public void softPresent() {
        if (!isPresent()) {
            File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
            listWithScreenshots.get().add(screenshot);
            getErrorsList().add(name + " doesn't exist: locator=" + locator +"\n"+ String.format(" Screenshot: %1$s", screenshot.getName()));
        }
    }

    @Override
    protected String formatLogMessage(final String message) {
        return String.format("'%1$s' %2$s %3$s %4$s", name, getElementType(), "|", message);
    }

    /**
     * This method is used for waiting until element is not displayed anymore
     */
    public void waitForNotVisible() {
        (new WebDriverWait(Browser.getInstance().getDriver(), conditionTimeOut)).until(
                ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void assertIsPresentAndVisible() {
        if (!isPresent()) {
            fatal("Element is not present on the page");
        }
    }

    /**
     * Check if element is editable
     *
     * @return true/false
     */
    public boolean isEditable() {
        waitForIsElementPresent();
        return element.isEnabled();
    }

    /**
     * Gets attribute value of the element.
     *
     * @param attr Attribute name
     * @return Attribute value
     */
    public String getAttribute(final String attr) {
        waitForIsElementPresent();
        return getBrowser().getDriver().findElement(locator).getAttribute(attr);
    }
}
