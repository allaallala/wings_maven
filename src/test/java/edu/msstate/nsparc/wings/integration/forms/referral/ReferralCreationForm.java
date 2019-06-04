package edu.msstate.nsparc.wings.integration.forms.referral;

import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Wagner-Peyser -> Referrals -> Create
 */
public class ReferralCreationForm extends ReferralBaseForm {

    private CheckBox chkAgree = new CheckBox("id=agreeToReq1", "Agree checkbox");
    private TextBox txbCreationDate = new TextBox("css=input#dateCreation", "Creation Date");
    private RadioButton rbJobDevelopmentNo = new RadioButton("css=input[id='isJobDevelopment2']", "Job Development - No");
    private Button btnCreateReferral = new Button("id=createReferral", "Create");

    private Span spnParticipantError = new Span("css=span[id='participant.errors']", "Participant error");
    private Span spnAgreementError = new Span("css=span[id='agreeToReq.errors']", "Agreement error");

    private RadioButton rbAnswer = new RadioButton("css=input[type=radio]", "Answer");
    private TextArea txbDisclaimerText = new TextArea("css=pre[class='disclaimerText']", "Disclaimer Text");
    private TextBox txbInitials = new TextBox("css=input#initials", "Initials");

    /**
     * Default constructor
     */
    public ReferralCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Creation')]"), "Referral Creation");
    }

    /**
     * This method is used for searching and selecting Job Order from look-up
     *
     * @param jobOrder Job Order that will be selected
     */
    public void selectJobOrder(JobOrder jobOrder) {
        clickAndWait(BaseButton.JOB_ORDER_LOOK_UP);
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearchAndReturn(jobOrder);
    }

    /**
     * This method is used for searching and selecting participant from look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    public void selectParticipantByRole(User user, Participant participant) {

        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpenByUser(user, participant);
        //participantSearchForm.performSearchAndSelect(participant);
    }

    /**
     * This method is used for clicking on Agree checkbox and waiting for Creation Date field
     */
    public void clickAgree() {
        chkAgree.click();
        waitDivLoading();
    }

    /**
     * Click answer radio button.
     */
    public void clickAnswer() {
        rbAnswer.click();
    }

    /**
     * Input creation date
     *
     * @param date - date creation
     */
    public void inputCreationDate(String date) {
        txbCreationDate.type(date);
        rbJobDevelopmentNo.click();
    }

    /**
     * Input initials.
     *
     * @param initials - initials
     */
    public void inputInitials(String initials) {
        txbInitials.type(initials);
    }

    /**
     * Create referral.
     */
    public void createReferral() {
        btnCreateReferral.clickAndWait();
    }

    /**
     * Get disclaimer text
     *
     * @return disclaimer text.
     */
    public String getDisclaimerText() {
        return txbDisclaimerText.getText();
    }

    /**
     * Get agreement error text on the page
     *
     * @return - agreement error text
     */
    public String getAgreementErrorText() {
        return spnAgreementError.getText();
    }

    /**
     * Get participant error text on the page
     *
     * @return - participant error text.
     */
    public String getParticipantErrorText() {
        return spnParticipantError.getText();
    }
}
