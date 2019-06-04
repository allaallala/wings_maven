package edu.msstate.nsparc.wings.integration.forms.youthGoals;

import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> Youth Goals -> Create
 */
public class YouthGoalsCreationForm extends YouthGoalsBaseForm {
    private Button btnParticipantYouthCreateLookUp = new Button(By.xpath("//input[@value='Participant']/following-sibling::button[1]"), "Participant Look Up");
    private ComboBox cmbEnrollment = new ComboBox(By.id("selectedWiaEnrollmentId"), "WIA Enrollment");
    private TextBox txbDateSet = new TextBox("css=input#dateSet", "Date Goal Set");
    private TableCell tbcWIAEnrollment = new TableCell(By.xpath("//div[@id='formTable']//tbody/tr[3]/td[2]"), "WIA Enrollment table cell");

    private Button btnYouthProviderLookup = new Button(By.xpath("//input[@value='ActiveYouthProvider']/following-sibling::button[1]"), "Youth Provider Lookup");

    /**
     * Default constructor
     */
    public YouthGoalsCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Youth Goal Creation')]"), "Youth Goal Creation");
    }

    /**
     * This method is used for selecting Participant from look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        btnParticipantYouthCreateLookUp.clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    /**
     * This method is used for selecting Youth Provider
     *
     * @param name Provider Name
     */
    public void selectYouthProvider(String name) {
        clickYouthProvider();
        CenterSearchForm centerSearchForm = new CenterSearchForm();
        centerSearchForm.selectAndReturnCenter(name);
    }

    /**
     * Select first enrollment
     */
    public void selectFirstEnrollment() {
        cmbEnrollment.selectFirst();
    }

    /**
     * Click youth provider
     */
    public void clickYouthProvider() {
        btnYouthProviderLookup.clickAndWait();
    }

    /**
     * Input date set
     *
     * @param dateSet - date set
     */
    public void inputDateSet(String dateSet) {
        txbDateSet.waitForIsElementPresent();
        txbDateSet.type(dateSet);
    }

    /**
     * Get WIA enrollment text on the page
     *
     * @return WIA enrollment text
     */
    public String getWiaEnrollmentText() {
        return tbcWIAEnrollment.getText().trim();
    }
}
