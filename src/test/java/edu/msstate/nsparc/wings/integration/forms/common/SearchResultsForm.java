package edu.msstate.nsparc.wings.integration.forms.common;

import framework.BaseForm;
import framework.elements.Link;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertTrue;

public class SearchResultsForm extends BaseForm {
    private static final String TABLE_XPATH = "//table[contains(@id,'esults-table')]";
    private static final String SEARCH_RESULT_LINK_XPATH = TABLE_XPATH + "//tbody//tr[%1$d]//td[%2$d]//a";
    private static final String nothing = "Nothing found to display.";

    private static final Link LNK_FIRST_SEARCH_RESULT = new Link(By.xpath(TABLE_XPATH + "//tbody//a"), "First Search Result");
    private final By rowsFound = By.xpath(TABLE_XPATH + "/tbody/tr");

    private final Link lnkPetitionOfFirstSearchResult = new Link(By.xpath(TABLE_XPATH + "//td[3]/a"), "First Search Result - Petition Link");
    private final RadioButton rbSelectedId = new RadioButton(By.xpath(TABLE_XPATH + "//td/input"), "Selected search result");

    private final TableCell tbcSearchResultsTable = new TableCell(By.xpath(TABLE_XPATH + "//tbody"), "Search result");
    private final TableCell tbcNoSearchResults = new TableCell(By.xpath("//tr[@class='empty'] | "
            + String.format("//td[contains(.,'%1$s')]", nothing)), nothing);
    private final TableCell tbcTableContent = new TableCell("css=table[id='results-table'] tbody td", "Table Content");
    private TableCell lblCountRecordsSearchPage = new TableCell(By.xpath("//span[@class='pagebanner']"),"Quantity of the records on the search page");

    public static final String SEARCH_RESULT_XPATH_CELL = TABLE_XPATH + "/tbody/tr[%1$d]/td[%2$s]";
    public static final String SEARCH_RESULT_XPATH_ROW = TABLE_XPATH + "/tbody/tr[%1$d]";

    public SearchResultsForm() {
        super(By.xpath(TABLE_XPATH), "Search Results table");
    }

    public static Boolean isFirstSearchResultPresent() {
        return LNK_FIRST_SEARCH_RESULT.isPresent();
    }

    /**
     * This method is used for validation search results on page
     * @param itemToSearch Expected entity (could be: company name, Center name, Service Title, Workforce Area)
     */
    public void validateSearchResultsOnOnePage(String itemToSearch) {
        if (isNothingResult()) {
            info("Nothing found to display, try to change search title to find more records");
        } else {
            // get total amount of search results table rows
            int pageResultsCount = getResultRowsCount();
            String valuableColumn = "2";
            // validate data for each row
            for (int j = 1; j <= pageResultsCount; j++) {
                assertTrue("Item found assertion failed!",
                        getRecordText(j, valuableColumn).toLowerCase().contains(itemToSearch.toLowerCase()));
            }
        }
    }

    public void openSelectedResult() {
        rbSelectedId.clickAndWait();
    }

    public void openPetitionOfFirstResult() {
        lnkPetitionOfFirstSearchResult.clickAndWait();
    }

    public String getPetitionOfFirstResultText() {
        return lnkPetitionOfFirstSearchResult.getText();
    }

    public Integer getResultRowsCount() {
        return getNumberOfElementsPresent(rowsFound);
    }

    public String getSearchedCount() {
        return lblCountRecordsSearchPage.getText();
    }

    public boolean isNothingResult() {
        return tbcSearchResultsTable.getText().contains(nothing);
    }

    public String getFirstRowRecordText(int columnNumber) {
        String column = String.valueOf(columnNumber);
        return getFirstRowRecordText(column);
    }

    public String getFirstRowRecordText(final String columnParameter) {
        int valuableRow = 1;
        return getSearchResultCell(valuableRow, columnParameter).getText();
    }

    public String getRecordText(int row, final String columnParameter) {
        return getSearchResultCell(row, columnParameter).getText();
    }

    public String getTableContentText() {
        return tbcTableContent.getText();
    }

    public void assertSearchResultsPresenceState(Boolean arePresent) {
        if (arePresent) {
            tbcNoSearchResults.assertIsNotPresent();
        } else {
            tbcNoSearchResults.assertIsPresentAndVisible();
        }
    }

    public void assertFirstSearchResultLinkPresent() {
        LNK_FIRST_SEARCH_RESULT.assertIsPresent();
    }

    public void openFirstSearchResult() {
        LNK_FIRST_SEARCH_RESULT.clickAndWait();
    }

    public String getFirstSearchResultLinkText() {
        return LNK_FIRST_SEARCH_RESULT.getText();
    }

    public void openSearchResult(int resultNumber) {
        getSearchResultLink(resultNumber).clickAndWait();
    }

    private Link getSearchResultLink(int resultNumber) {
        final int valuableColumn = 1;
        return new Link(
                By.xpath(String.format(SEARCH_RESULT_LINK_XPATH, resultNumber, valuableColumn)),
                String.format("#%1$d search result", resultNumber));
    }

    private TableCell getSearchResultCell(int row, final String columnParameter) {
        String xPath = String.format(SEARCH_RESULT_XPATH_CELL, row, columnParameter);
        return new TableCell(
                By.xpath(xPath),
                String.format("Result Table value in the %1$d row, %2$s column", row, columnParameter));
    }

    private TableCell getSearchResultRow(int row) {
        String xPath = String.format(SEARCH_RESULT_XPATH_ROW, row);
        return new TableCell(
                By.xpath(xPath),
                String.format("Result Table value in the %1$d row", row));
    }
}
