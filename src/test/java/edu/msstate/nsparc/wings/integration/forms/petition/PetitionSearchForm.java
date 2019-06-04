package edu.msstate.nsparc.wings.integration.forms.petition;

import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/*
This form is opened via Employers -> Trade -> Petitions -> Search
 */
public class PetitionSearchForm extends PetitionBaseForm {

    private TextBox txbPetitionNumber = new TextBox("css=input[id='petitionNumber']", "Petition Number");

    /**
     * Default constructor
     */
    public PetitionSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Petition Search')]"), "Petition Search");
    }

    /**
     * Searching for the record
     * @param petition Petition participantSSDetails
     */
    public void performSearch(Petition petition) {
        selectEmployer(petition.getEmployer());
        txbPetitionNumber.type(petition.getNumber());
        search();
    }

    /**
     * Searching for the record and opening it
     * @param petition Petition participantSSDetails
     */
    public void performSearchAndOpen(Petition petition) {
        performSearch(petition);
        openFirstSearchResult();
    }

    /**
     * This method is used for search for Petition from look-ups
     * @param petition Petition participantSSDetails
     */
    public void performSearchAndSelect(Petition petition) {
        performSearch(petition);
        selectResultAndReturn();
    }
}