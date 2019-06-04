package edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSsCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;

public class ScreeningQuestionsSsForm extends JobOrderCreationForm {
    private ComboBox cmbQuestionType = new ComboBox("css=select[id='tempScreeningQuestion.questionType']", "Question Type");
    private RadioButton rbQuestionGradedYes = new RadioButton("css=input[id='tempScreeningQuestion.isGraded1']", "Question Graded - Yes");
    private TextBox txbQuestion = new TextBox("id=tempScreeningQuestion.question", "Question");
    private TextBox txbCorrectAnswer = new TextBox("id=tempScreeningQuestion.correctAnswer", "Correct Answer");
    private TextBox txbIncorrectAnswer1 = new TextBox("id=tempScreeningQuestion.incorrectAnswerOne", "Incorrect Answer 1");
    private TextBox txbIncorrectAnswer2 = new TextBox("id=tempScreeningQuestion.incorrectAnswerTwo", "Incorrect Answer 2");
    private TextBox txbIncorrectAnswer3 = new TextBox("id=tempScreeningQuestion.incorrectAnswerThree", "Incorrect Answer 3");
    private Button btnAdd = new Button(By.xpath("//input[@id='addQuestion'] | //button[@id='addQuestion']"), "Add");
    private TableCell tbcQuestionDetails = new TableCell(By.xpath("//table[@id='job-matches-table']//tbody"), "Answers");

    public ScreeningQuestionsSsForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Create Opening: Screening Questions')]"), "Create Opening: Screening Questions");
    }

    public void addGradedQuestions(String question) {
        cmbQuestionType.select(questionType);
        txbQuestion.type(question);
        btnAdd.clickAndWait();
        txbCorrectAnswer.type(correctAnswer);
        rbQuestionGradedYes.click();
        String incorrectAnswer1 = CommonFunctions.getRandomLiteralCode(20);
        txbIncorrectAnswer1.type(incorrectAnswer1);
        String incorrectAnswer2 = CommonFunctions.getRandomLiteralCode(20);
        txbIncorrectAnswer2.type(incorrectAnswer2);
        String incorrectAnswer3 = CommonFunctions.getRandomLiteralCode(20);
        txbIncorrectAnswer3.type(incorrectAnswer3);
        btnAdd.clickAndWait();

        checkField(tbcQuestionDetails, question, Constants.FALSE);
    }
}
