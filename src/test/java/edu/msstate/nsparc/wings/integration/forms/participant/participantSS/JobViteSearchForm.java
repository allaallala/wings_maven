package edu.msstate.nsparc.wings.integration.forms.participant.participantSS;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.*;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Created by a.korotkin on 4/17/2017.
 */
public class JobViteSearchForm extends BaseWingsForm {

    private TextBox txbJobTitle = new TextBox(By.xpath("//input[@id='jobOrder.jobTitle']"), "Job Title");
    private Button btnSearch = new Button(By.xpath("//button[@id='search']"), "Search");
    private TableCell tbcFirstItem = new TableCell(By.xpath("//tr[@class ='dt-row-odd']//td[1]//a"), "First Item");
    private Link lnkSortByTitle = new Link(By.xpath("//table[@id='job-matches-table']//a[text()[contains(.,'Job Title')]]"), "Job Title");
    private Link lnkSortByEmployer = new Link(By.xpath("//table[@id='job-matches-table']//a[text()[contains(.,'Employer')]]"), "Employer");
    private Div divPagination = new Div(By.xpath("//ul[@class='pagination pull-right']"), "Pagination");

    private String itemLocator = "//table//tr[%1$s]//td[%2$s]//a";

    public JobViteSearchForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Search Job-Vites')]"), "Job-Vite Search");
    }

    public void checkEmployerSorting(ArrayList list) {
        int i = 1;
        TableCell tbcJobVite = new TableCell(By.xpath(String.format(itemLocator, i, 2)), "Job-Vite");
        while (tbcJobVite.isPresent()) {
            softTrue("Incorrect order on the item #" + i, tbcJobVite.getText().contains(list.get(i - 1).toString()));
            i++;
            tbcJobVite = new TableCell(By.xpath(String.format(itemLocator, i, 2)), "Job-Vite");
        }
    }

    public void searchEachJob(String[] jobTitles) {
        for (String jobTitle : jobTitles) {
            txbJobTitle.type(jobTitle);
            btnSearch.clickAndWait();
            softTrue("Another job is displayed after searching", tbcFirstItem.getText().contains(jobTitle));
        }
    }

    public void checkTitleSorting(ArrayList list) {
        int i = 1;
        TableCell tbcJobVite = new TableCell(By.xpath(String.format(itemLocator, i, 1)), "Job-Vite");
        while (tbcJobVite.isPresent()) {
            softTrue("Incorrect order on the item #" + i, tbcJobVite.getText().contains(list.get(i - 1).toString()));
            i++;
            tbcJobVite = new TableCell(By.xpath(String.format(itemLocator, i, 1)), "Job-Vite");
        }
    }

    public void checkAllJobVites(String[] jobTitles) {
        TableCell tbcJobVite = new TableCell(By.xpath(String.format(itemLocator, 1, 1)), "Job-Vite");
        boolean itemExists = false;
        for (String jobTitle : jobTitles) {
            int j = 1;
            while (tbcJobVite.isPresent() && j <= 10) {
                if (tbcJobVite.getText().contains(jobTitle)) {
                    itemExists = true;
                    break;
                }
                j++;
                tbcJobVite = new TableCell(By.xpath(String.format(itemLocator, j, 1)), "Job-Vite");
            }
            softTrue("Item is not found:" + jobTitle, itemExists);
            itemExists = false;
        }
    }

    public void checkPaginationExists() {
        softTrue("Pagination is not displayed", divPagination.isPresent());
    }

    public void clickSortByTitle() {
        lnkSortByTitle.clickAndWait();
    }

    public void clickSortByEmployer() {
        lnkSortByEmployer.clickAndWait();
    }
}
