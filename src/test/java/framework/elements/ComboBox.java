package framework.elements;

import framework.Logger;
import framework.utils.SmartWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * Combobox
 */
public class ComboBox extends BaseElement {

    /**
     * Constructor
     * @param locator - locator of the combobox
     * @param name - name of the combobox
     */
    public ComboBox(final String locator, final String name) {
        super(locator, name);
    }

    /**
     * Constructor
     * @param locator - locator of the combobox
     * @param name - name of the combobox
     */
    public ComboBox(final By locator, final String name) {
        super(locator, name);
    }

    @Override
    protected String getElementType() {
        return "drop-down list";
    }

    /**
     * Gets selected option of the cmb
     * @return - selected label
     */
    public String getSelectedLabel() {
        //DIV_LOADING.waitForNotVisible();
        waitForIsElementPresent();
        Select label = new Select(element);
        label.getFirstSelectedOption();
        return label.getFirstSelectedOption().getText();
    }

    /**
     * Seleced specified option of the combobox.
     * @param optionLocator - option locator
     */
    public void select(String optionLocator) {
        DIV_LOADING.waitForNotVisible();
        waitForIsElementPresent();
        info(String.format("Select '%1$s'", optionLocator));
        new Select(getElement()).selectByVisibleText(optionLocator);
    }

    public void selectByIndex(int index) {
        DIV_LOADING.waitForNotVisible();
        info(String.format("Select '%1$s'", index));
        Assert.assertTrue(selectAndWait(index), String.format("Element number '%1$s' is not selected in the drop-down", index));
    }

    /**
     * Select first option in the cmb
     */
    public void selectFirst() {
        final int index = 1;
        DIV_LOADING.waitForNotVisible();
        waitForIsElementPresent();
        info("Select first option in the combobox");
        Assert.assertTrue(selectAndWait(index), String.format("Element number '%1$s' is not selected in the drop-down", index));
    }

    public void selectZero() {
        final int index = 0;
        DIV_LOADING.waitForNotVisible();
        waitForIsElementPresent();
        info("Select first option in the combobox");
        Assert.assertTrue(selectAndWait(index), String.format("Element number '%1$s' is not selected in the drop-down", index));
    }

    /**
     * This method is used for checking if string is presented in drop down options
     * @param value String value that will be looked up in drop down
     * @return True if value is found in drop down
     */
    public boolean checkValue(String value) {
        DIV_LOADING.waitForNotVisible();
        waitForIsElementPresent();
        Select label = new Select(element);
        List<WebElement> webElementList = label.getAllSelectedOptions();
        for (WebElement elem : webElementList) {
            if (elem.getText().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    private boolean selectAndWait(Object obj) {
        ExpectedCondition<Boolean> condition = driver -> {
            try {
                if (obj instanceof Integer) {
                    int index = (int) obj;
                    new Select(element).selectByIndex(index);
                    return isElementSelectedByIndex(index);
                } else {
                    String text = (String) obj;
                    new Select(element).selectByVisibleText(text);
                    return isElementSelectedByText(text);
                }
            } catch (Exception | AssertionError e) {
                Logger.getInstance().debug(e.getMessage());
                return false;
            }
        };
        return SmartWait.waitForTrue(condition, Long.parseLong("150"), Long.parseLong("2500"));
    }

    public int getNumberOfItems() {
        waitForIsElementPresent();
        Select label = new Select(element);
        List<WebElement> webElementList = label.getOptions();
        return webElementList.size();
    }

    private boolean isElementSelectedByIndex(int index){
        return getSelectedLabel().trim().equalsIgnoreCase(getListOfItemsFromComboBox().get(index).trim());
    }

    private boolean isElementSelectedByText(String text){
        String actualValue = getSelectedLabel().replaceAll("\\s+", " ").trim();
        String expectedValue = text.replaceAll("\\s+", " ").trim();
        return actualValue.equalsIgnoreCase(expectedValue);
    }

    private ArrayList<String> getListOfItemsFromComboBox(){
        waitForIsElementPresent();

        ArrayList<String> listWithValues = new ArrayList<>();
        List<WebElement> myList = new Select(element).getOptions();

        for (WebElement list : myList) {
            String value = list.getText();
            listWithValues.add(value);
        }
        return listWithValues;
    }
}
