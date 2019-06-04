package edu.msstate.nsparc.wings.integration.forms.wiaTraining;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> WIA Training -> Search for record -> Open record
 */
public class WIATrainingDetailsForm extends WIATrainingBaseForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcFirstDayOfTraining = new TableCell(String.format(detailPath, "First Day of Training:"), "First day of training");
    private TableCell tbcDayOfAnticipatedCompletion = new TableCell(String.format(detailPath, "Date Anticipated Completion:"), "Day od anticipated completion");
    private TableCell tbcTrainingType = new TableCell(String.format(detailPath, "Training Type:"), "Training type");
    private TableCell tbcParticipant = new TableCell(String.format(detailPath, "Participant:"), "Participant");
    private TableCell tbcTrainingProvider = new TableCell(String.format(detailPath, "Training Provider:"), "Training provider");
    private TableCell tbcTrainingResult = new TableCell(String.format(detailPath, "Result:"), "Training result");
    private TableCell tbcLastDayOfTraining = new TableCell(String.format(detailPath, "Last Day of Training:"), "Last day of training");
    private Button btnDelete = new Button("//input[@title='Delete']", "Delete button");

    /**
     * Default counstructor
     */
    public WIATrainingDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Training Detail')]"), "Training Enrollment Detail");
    }

    /**
     * Create new WIA training after clicking on [Create Another] button
     *
     * @param partip        - participant
     * @param trainingType  - training type
     * @param dayTraining   - first day of training or application date
     * @param dayCompletion - date anticipated completion
     */
    public void createAnotherWiaTraining(Participant partip, String trainingType, String dayTraining, String dayCompletion) {
        clickAndWait(BaseButton.CREATE_ANOTHER);
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);

        divideMessage("Click [Create] button and confirm it");
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();

        divideMessage("Check WIA training participantSSDetails");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.validateWIATrainingDetails(partip, trainingType, dayTraining, dayCompletion);
    }

    /**
     * This method is used for checking if WIA Training participantSSDetails matches expected values
     *
     * @param participant      - participant to check
     * @param trainingType     - training type
     * @param firstDayTraining - first day of training and application date
     * @param dayCompletion    - date anticipated completion
     */
    public void validateWIATrainingDetails(Participant participant, String trainingType, String firstDayTraining, String dayCompletion) {
        CustomAssertion.softTrue("Incorrect participant first name", tbcParticipant.getText().contains(participant.getFirstName()));
        CustomAssertion.softTrue("Incorrect participant last name", tbcParticipant.getText().contains(participant.getLastName()));
        CustomAssertion.softTrue("Incorrect training type", tbcTrainingType.getText().contains(trainingType));
        CustomAssertion.softTrue("Incorrect first day of training", tbcFirstDayOfTraining.getText().equals(firstDayTraining));
        CustomAssertion.softTrue("Incorrect day of anticipated completion", tbcDayOfAnticipatedCompletion.getText().equals(dayCompletion));
    }

    /**
     * This method is used for checking if WIA Training participantSSDetails matches expected values
     *
     * @param trainingType         - training type
     * @param dayTraining          - first day of training
     * @param participantName      - participant name
     * @param trainingProviderName - training provider name
     * @return - true, if expected data matches actual
     */
    public boolean validateWIATrainingDetails(String trainingType, String dayTraining, String participantName, String trainingProviderName) {
        return tbcTrainingType.getText().contains(trainingType) &&
                tbcFirstDayOfTraining.getText().equals(dayTraining) &&
                tbcParticipant.getText().contains(participantName) &&
                tbcTrainingProvider.getText().contains(trainingProviderName);
    }

    /**
     * Check WIA Training data after editing
     *
     * @param trainingType  - training type
     * @param dayTraining   - first day of training
     * @param dayCompletion - day anticipated completion.
     */
    public void validateWIATraining(String trainingType, String dayTraining, String dayCompletion) {
        CustomAssertion.softAssertContains(tbcTrainingType.getText(), trainingType, "Incorrect training result");
        CustomAssertion.softAssertContains(tbcFirstDayOfTraining.getText(), dayTraining, "Incorrect last day of training");
        CustomAssertion.softAssertContains(tbcDayOfAnticipatedCompletion.getText(), dayCompletion, "Incorrect day completion");
    }

    /**
     * This method is used for checking if WIA Training participantSSDetails matches expected values
     *
     * @param trainingResult  - expected training result
     * @param lastDayTraining - the last day of training
     */
    public void validateEditedWIATrainingDetails(String trainingResult, String lastDayTraining) {
        CustomAssertion.softAssertContains(tbcTrainingResult.getText(), trainingResult, "Incorrect training result");
        CustomAssertion.softAssertContains(tbcLastDayOfTraining.getText(), lastDayTraining, "Incorrect last day of training");
    }

    /**
     * This method is used for getting Training type, First day of training and Participants first name from form
     *
     * @return string array with requested values
     */
    public String[] getWIATrainingDetails() {
        return new String[]{
                tbcTrainingType.getText(),
                tbcFirstDayOfTraining.getText(),
                CommonFunctions.regexGetMatch(tbcParticipant.getText(), "\\w+")
        };
    }

    /**
     * Check, that buttons is present (or not) on the page
     *
     * @param user - current user
     */
    public void checkButtonsAuditDelete(User user) {
        checkButtonsPresent(user.getWiaTrain().getWiaTrainingViewEdit(), user.getWiaTrain().getWiaTrainingViewAudit());
        divideMessage("Delete");
        ifButton(user.getWiaTrain().getWiaTrainingViewDelete(), btnDelete);

        //(!) If [Delete] presents on the page, we should check delete functionality (the form and pop-up).
        if (user.getWiaTrain().getWiaTrainingViewDelete()) {
            btnDelete.click();
            areYouSure(Popup.No);
        }
    }

    /**
     * Edit WIA training
     *
     * @param trainingType     - training type
     * @param firstDayTraining - first day of training
     * @param dayCompletion    - day anticipated completion.
     */
    public void editWiaTraining(String trainingType, String firstDayTraining, String dayCompletion) {
        clickButton(Buttons.Edit);
        WIATrainingEditForm editPage = new WIATrainingEditForm();
        editPage.editWIATraining(trainingType, firstDayTraining, dayCompletion);
        editPage.clickButton(Buttons.Save);
        editPage.passParticipationRecalculationPage();
        validateWIATraining(trainingType, firstDayTraining, dayCompletion);
    }
}
