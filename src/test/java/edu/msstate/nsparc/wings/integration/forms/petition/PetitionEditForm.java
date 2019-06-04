package edu.msstate.nsparc.wings.integration.forms.petition;

import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Trade -> Petitions -> Search for record -> Open record -> Edit
 */
public class PetitionEditForm extends PetitionBaseForm {

    /**
     * Default constructor
     */
    public PetitionEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Petition Edit')]"), "Petition Edit");
    }

    /**
     * Filling out form fields with provided data
     * @param petition Object with Petition data
     */
    public void fillOutEditForm(Petition petition) {
        txaDepartment.type(petition.getDepartment());
        txaTypeOfWork.type(petition.getTypeOfWork());
        txbFileDate.type(petition.getFileDate());
        txbDecisionDate.type(petition.getDecisionDate());
        txbImpactDate.type(petition.getImpactDate());
    }
}
