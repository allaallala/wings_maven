package edu.msstate.nsparc.wings.integration.forms.youthGoals;

import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.Button;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> Youth Goals -> Search
 */
public class YouthGoalsSearchForm extends YouthGoalsBaseForm {
    private Button btnParticipantYouthSearchLookUp = new Button("css=table[id='search-fields'] button", "Participant LookUp");
    private Link lnkParticipantName = new Link("css=table#results-table > tbody a", "Participant Name");
    private TableCell tbcDateSet = new TableCell("css=table#results-table td:nth-child(4)", "Date Goal Set");
    private TableCell tbcGoalType = new TableCell("css=table#results-table td:nth-child(5)", "Goal Type");

    /**
     * Constructor
     */
    public YouthGoalsSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Youth Goal Search')]"), "Youth Goal Search");
    }

    /**
     * This method is used for Youth Goals searching by Participant
     *
     * @param participant Participant that will be used for search
     */
    public void performSearch(Participant participant) {
        selectParticipant(participant);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for selecting Participant from look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        btnParticipantYouthSearchLookUp.clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    /**
     * Check participant name present on the page
     *
     * @return - true, if paricipant name link present
     */
    public Boolean checkParticipantNamePresent() {
        return lnkParticipantName.isPresent();
    }

    /**
     * Get goal type text
     *
     * @return goal type text
     */
    public String getGoalTypeText() {
        return tbcGoalType.getText();
    }

    /**
     * Get date set text
     *
     * @return date set text.
     */
    public String getDateSetText() {
        return tbcDateSet.getText();
    }

    /**
     * Check, that elements date set and goal type are present on the page
     *
     * @return - true, if present
     */
    public Boolean dateSetGoalTypePresent() {
        return tbcDateSet.isPresent() && tbcGoalType.isPresent();
    }
}
