package edu.msstate.nsparc.wings.integration.forms.menu;

import framework.elements.Link;
import framework.elements.MenuItem;
import org.openqa.selenium.By;

/**
 * Describes menu blocks of the application
 */
public class WingsMenuItem extends MenuItem {

    private String wingsMenuHeader;
    private String[] wingsMenuNames;

    /*public Menu MENU_PARTICIPANTS = new Menu("Participants", "Participants");
    public Menu MENU_EMPLOYERS = new Menu("Employers", "Employers");
    public Menu MENU_REPORTS = new Menu("Reports", "Reports");
    public Menu MENU_ADVANCED = new Menu("Advanced", "Advanced");*/

    public enum TopMenu {
        MENU_PARTICIPANTS("Participants", "Participants"),
        MENU_EMPLOYERS("Employers", "Employers"),
        MENU_REPORTS("Reports", "Reports"),
        MENU_ADVANCED("Advanced", "Advanced");

        private String menuName, menuId;

        TopMenu(String menuN, String menuID) {
            this.menuName = menuN;
            this.menuId = menuID;
        }

        private String getMenuId() {
            return menuId;
        }

        private String getMenuName() {
            return menuName;
        }
    }

    private String getWingsMenuHeader() {
        return wingsMenuHeader;
    }
    public String[] getWingsMenuNames() {
        return wingsMenuNames;
    }



    private String navMainMenuLocator = "//li[@class='navMenuHeader']/a[contains(.,'%1$s')]";
    private String menuName = "//ul/li/a[contains(text(),'%1$s')]/..//ul//a[contains(text(),'%2$s')]";

    private String navHeaderLocator = "//li[@class='navMenuHeader']//ul//a[contains(.,'%1$s')]";
    private String doubleWordMenu = "/..//ul//a[contains(text(),'%1$s')]";

    /**
     * Set locator and name to a value of existing menu item on the page
     * @param menu - object menu
     * @param names - menu name
     */
    public WingsMenuItem(TopMenu menu, String[] names) {
        wingsMenuHeader = menu.getMenuName();
        wingsMenuNames = names;
        this.locator = By.xpath(getMenuItemLocator(menu, names));
        this.name = menu.getMenuName().concat(DELIMITER).concat(getName(names));
    }

    public WingsMenuItem() {
    }

    /**
     * Gets lotator of a menu item
     * @param menu - menu object
     * @param names - name of menu
     * @return locator of a chosen menu item
     */
    private String getMenuItemLocator(TopMenu menu, final String[] names) {
        String result = String.format(menuName, menu.getMenuId(), names[0]);
        if (names.length == 2) {
            result += String.format(doubleWordMenu, names[1]);
        }
        return result;
    }

    public void clickMenu(WingsMenuItem item) {
        int i = 0;
        Link lnkSpecificMenu;
        Link lnkMainMenu = new Link(By.xpath(String.format(navMainMenuLocator,item.getWingsMenuHeader())),"Menu Header");
        lnkMainMenu.hover(); //hover the main menu

        do {
            lnkSpecificMenu = new Link(By.xpath(String.format(navHeaderLocator,item.getWingsMenuNames()[i])),
                    item.getWingsMenuNames()[i]);
            lnkSpecificMenu.hover(); //If the menu isn't located in the first list, go to the sublist.
            i++;
        } while (item.getWingsMenuNames().length > i);
        lnkSpecificMenu.clickAndWait(); //Click the required menu in the list
    }

    /**
     * Check, that chosen menu is not present on the page
     * @param item - menu item
     * @param startNumber - number in the massive (menu number, that shouldn't be present on the page)
     */
    public void menuNotPresent(WingsMenuItem item, Integer startNumber) {
        Link lnkIntermediateMenu, lnkSpecificMenu;
        Link lnkMainMenu = new Link(By.xpath(String.format(navMainMenuLocator,item.getWingsMenuHeader())),"Menu Header");
        lnkMainMenu.hover();
        if (startNumber != 0) {
            int i = 0;
            do {
                lnkIntermediateMenu = new Link(By.xpath(String.format(navHeaderLocator,item.getWingsMenuNames()[i])),
                        item.getWingsMenuNames()[i]);
                lnkIntermediateMenu.hover(); //If the menu isn't located in the first list, go to the sublist.
                i++;
            } while (i < startNumber);
        }

        lnkSpecificMenu = new Link(By.xpath(String.format(navHeaderLocator,item.getWingsMenuNames()[startNumber])),
                item.getWingsMenuNames()[startNumber]);
        lnkSpecificMenu.assertIsNotPresent();
    }
}
