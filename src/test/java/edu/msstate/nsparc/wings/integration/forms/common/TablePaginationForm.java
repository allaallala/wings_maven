package edu.msstate.nsparc.wings.integration.forms.common;

import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import framework.BaseForm;
import framework.CommonFunctions;
import framework.elements.Link;
import org.openqa.selenium.By;

public class TablePaginationForm extends BaseForm {
    private static final Link LNK_PAGES_QUANTITY = new Link("css=span[class='tablePageLinks']", "Pages Quantity");
    private final String regPayNumber = "\\s(\\d+)";

    private final Link lnkNextPage = new Link(By.xpath("//a[contains(.,'Next')]"), "Next page");
    private final Link lnkPrevPage = new Link(By.xpath("//a[contains(.,'Prev')]"), "Prev page");
    private final Link lnkLastPage = new Link("//a[contains(.,'Last')]","Last page");

    public TablePaginationForm() {
        super(LNK_PAGES_QUANTITY.getLocator(), "Table Pagination links");
    }

    public static boolean isPresent() {
        return LNK_PAGES_QUANTITY.isPresent();
    }

    public int getPagesCount() {
        return CommonFunctions.regexGetNumberMatchGroup(LNK_PAGES_QUANTITY.getText(), regPayNumber);
    }

    public int getLastPage() {
        return BaseNavigationSteps.getLastPage(LNK_PAGES_QUANTITY);
    }

    public void openNextPage() {
        lnkNextPage.clickAndWait();
    }

    public void openPreviousPage() {
        lnkPrevPage.clickAndWait();
    }

    public void openLastPage() {
        lnkLastPage.clickAndWait();
    }
}
