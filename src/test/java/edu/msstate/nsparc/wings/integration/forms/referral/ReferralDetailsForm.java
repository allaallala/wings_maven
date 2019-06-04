package edu.msstate.nsparc.wings.integration.forms.referral;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via
 * Participants -> Wagner-Peyser -> Referrals -> Search for record -> Open it
 */
public class ReferralDetailsForm extends ReferralBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcInstructions = new TableCell(By.xpath("//form[@id='referral']//tr[contains(.,'Instructions')]/following-sibling::tr/td"), "Instructions");
    private Button btnPrint = new Button("id=print", "Print");
    private Button btnDelete = new Button(By.xpath("//input[@value='Delete']"), "Delete");
    private Span spnNotesCount = new Span("//span[@class='notesCount']", "Notes Count");
    private TableCell tbcResult = new TableCell(By.xpath(String.format(detailPath, "Result:")), "Result");
    private TableCell tbcResultDate = new TableCell(By.xpath(String.format(detailPath, "Result Date:")), "Result Date");
    private Span spParticipant = new Span(By.xpath("//span[@modelclass='Participant']"), "Participant");
    private Span spJobOrder = new Span(By.xpath("//span[contains(@modelclass,'JobOrder')]"), "Job order");
    private TableCell tbcCreationDate = new TableCell(By.xpath(String.format(detailPath, "Creation Date")), "Creation Date");
    private TableCell tbcJobDevelopment = new TableCell(By.xpath(String.format(detailPath, "Job Development")), "Job Development");
    private TableCell tbcAddInfo = new TableCell(By.xpath(String.format(detailPath, "Additional Information")), "Additional Information");

    /**
     * Default constructor
     */
    public ReferralDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Detail')]"), "Referral Detail");
    }

    /**
     * Validate information on the referral participantSSDetails form
     *
     * @param participant    - participant
     * @param jb             - job order
     * @param date           - current date.
     * @param referralResult - result referral
     */
    public void validateInformation(Participant participant, JobOrder jb, String date, String referralResult) {
        CustomAssertion.softTrue("Incorrect participant information", spParticipant.getText().contains(participant.getFirstName() + " " + participant.getLastName()));
        CustomAssertion.softAssertContains(spJobOrder.getText(), jb.getJobTitle(), "Incorrect job order title");
        CustomAssertion.softAssertContains(spJobOrder.getText(), jb.getEmployer().getCompanyName(), "Incorrect job order company name");
        CustomAssertion.softAssertContains(tbcCreationDate.getText(), date, "Incorrect creation date");
        CustomAssertion.softAssertEquals(tbcResult.getText(), referralResult, "Incorrect referral result");
    }

    /**
     * Validate some information on the referral participantSSDetails page
     *
     * @param jobDevelopment - true, if yes
     * @param result         - result
     * @param resultDate     - result date
     * @param addInfo        - additional information
     */
    public void validateJoResultAddInfo(Boolean jobDevelopment, String result, String resultDate, String addInfo) {
        if (jobDevelopment) {
            CustomAssertion.softTrue("Incorrect job development text", tbcJobDevelopment.getText().equals(Constants.YES_ANSWER));
        }
        CustomAssertion.softTrue("Incorrect result text", tbcResult.getText().equals(result));
        CustomAssertion.softTrue("Incorrect result date text", tbcResultDate.getText().equals(resultDate));
        CustomAssertion.softTrue("Incorrect additional information text", tbcAddInfo.getText().equals(addInfo));
    }

    /**
     * Print
     */
    public void print() {
        btnPrint.clickAndWait();
    }

    /**
     * Get participant instructions.
     *
     * @return participant instructions.
     */
    public String getParticipantInstructions() {
        return tbcInstructions.getText();
    }

    /**
     * Check result date is present and visible.
     *
     * @return true if result date field is present and visible.
     */
    public Boolean checkResultDatePresent() {
        return tbcResultDate.isPresent();
    }

    /**
     * Get result text
     *
     * @return result text.
     */
    public String getResult() {
        return tbcResult.getText().trim();
    }

    /**
     * Get notes count
     *
     * @return notes count
     */
    public String getNoteCount() {
        return spnNotesCount.getText();
    }

    /**
     * Check referral buttons
     *
     * @param user - current user
     */
    public void checkReferralButtons(User user) {
        checkButtonsPresent(user.getReferral().getReferralsEdit(), user.getReferral().getAuditButton());
        divideMessage("Check delete button");
        if (user.getReferral().getReferralsDelete()) {
            btnDelete.assertIsPresent();
        } else {
            btnDelete.assertIsNotPresent();
        }
        divideMessage("Check print button");
        if (user.getReferral().getReferralsPrint()) {
            btnPrint.assertIsPresent();
        } else {
            btnPrint.assertIsNotPresent();
        }
        divideMessage("Check view/download docs");
        if (user.getReferral().getReferralsViewDocs()) {
            //TODO View docs is not ready
        }
    }
}
