package edu.msstate.nsparc.wings.integration.forms.trainingWaiver;

import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Training Waivers -> Search for record -> Open record -> Edit
 */
public class TrainingWaiverEditForm extends TrainingWaiverBaseForm {

    private TextBox txbRenewalsRemaining = new TextBox("id=numRenewalsRemaining", "Number of renewals remaining");
    private TableCell tbcLatesPossibleIssueDate = new TableCell("//td[contains(text(),'Latest Possible Issue Date:')]/following-sibling::td", "Latest Possible Issue Date");

    /**
     * Default constructor
     */
    public TrainingWaiverEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Waiver Edit')]"), "Training Waiver Edit");
    }

    /**
     * Filling out form fields with provided data
     *
     * @param waiver Object with Waiver data
     */
    public void fillOutEditForm(TrainingWaiver waiver) {
        selectWaiverReason(waiver.getWaiverReason());
    }

    /**
     * This method is used for handling "Participation Period Recalculation" page that might be opened after edit
     */
    public void finishEditing() {
        clickAndWait(BaseButton.SAVE_CHANGES);
        passParticipationRecalculationPage();
    }

    /**
     * Select waiver reason from drop-down
     *
     * @param reason Waiver Reason from drop-down
     */
    public void editWaiverReason(String reason) {
        selectWaiverReason(reason);
    }

    /**
     * Edit max number of allowed renewals
     *
     * @param newNumber - new number of allowed renewals
     */
    public void changeRenewalRemaining(String newNumber) {
        txbRenewalsRemaining.type(newNumber);
    }

    /**
     * Get possible date text
     *
     * @return possible date text on the page.
     */
    public String getPossibleDateText() {
        return tbcLatesPossibleIssueDate.getText();
    }
}
