package edu.msstate.nsparc.wings.integration.forms.workforceArea;

import org.openqa.selenium.By;
import static edu.msstate.nsparc.wings.integration.constants.Constants.WIOA;

/**
 * This form is opened via Advanced -> Workforce Area -> Search
 */
public class WorkforceAreasSearchForm extends WorkforceAreaBaseForm {

    /**
     * Default constructor
     */
    public WorkforceAreasSearchForm() {
        super(By.xpath(String.format("//span[@id='breadCrumb'][contains(.,'%1$s Search')]", WIOA.toUpperCase())), "Workforce Area Search");
    }

    /**
     * Searching and returning specified Workforce Area
     *
     * @param area Name of the wanted Area
     */
    public void performSearchAndSelect(String area) {
        inputWorkforceArea(area);
        searchSelectResultAndReturn();
    }

    /**
     * Search for the specic workforce area (simple)
     *
     * @param area - area to search.
     */
    public void performSimpleSearch(String area) {
        inputWorkforceArea(area);
        search();
        openFirstSearchResult();
    }
}
