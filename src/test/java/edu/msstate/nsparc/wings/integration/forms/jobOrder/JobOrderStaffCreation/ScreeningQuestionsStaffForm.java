package edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

public class ScreeningQuestionsStaffForm extends JobOrderCreationForm {
    private ComboBox cmbQuestionType = new ComboBox("css=select[id='tempScreeningQuestion.questionType']", "Question Type");
    private ComboBox cmbGradeQuestion = new ComboBox("css=select[id='tempScreeningQuestion.isGraded']", "Grade this Question");
    private TextBox txbQuestion = new TextBox("id=tempScreeningQuestion.question", "Question");
    private TextBox txbCorrectAnswer = new TextBox("id=tempScreeningQuestion.correctAnswer", "Correct Answer");
    private TextBox txbIncorrectAnswer1 = new TextBox("id=tempScreeningQuestion.incorrectAnswerOne", "Incorrect Answer 1");
    private TextBox txbIncorrectAnswer2 = new TextBox("id=tempScreeningQuestion.incorrectAnswerTwo", "Incorrect Answer 2");
    private TextBox txbIncorrectAnswer3 = new TextBox("id=tempScreeningQuestion.incorrectAnswerThree", "Incorrect Answer 3");
    private Button btnAdd = new Button(By.xpath("//input[@id='addQuestion'] | //button[@id='addQuestion']"), "Add");

    public ScreeningQuestionsStaffForm() {
        super(By.xpath("//h1[contains(.,'Job Order Creation: Screening Questions')]"), "Job Order Creation: Screening Questions");
    }

    public void passQuestionBlock(JobOrder jobOrder, boolean question) {
        if (question) {
            cmbQuestionType.select(questionType);
            if (cmbGradeQuestion.isPresent()) {
                cmbGradeQuestion.select(yesAnswer);
            }
            txbQuestion.type(jobOrder.getQuestion().getText());
            txbCorrectAnswer.type(jobOrder.getQuestion().getCorrectAnswer());
            txbIncorrectAnswer1.type(CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH));
            txbIncorrectAnswer2.type(CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH));
            txbIncorrectAnswer3.type(CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH));
            btnAdd.clickAndWait();
        }
    }

    public void passQuestionBlockAndCreate(JobOrder jobOrder, boolean question) {
        passQuestionBlock(jobOrder, question);
        clickButton(Buttons.Continue);
    }
}
