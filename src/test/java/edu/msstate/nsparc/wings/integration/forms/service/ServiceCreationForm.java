package edu.msstate.nsparc.wings.integration.forms.service;

import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Services -> Create
 */
public class ServiceCreationForm extends ServiceBaseForm {


    private ComboBox cmbLevel = new ComboBox(By.id("serviceLevel"), "Level");
    private TextBox txbDateBegin = new TextBox(By.id("dateBegin"), "Service Begin Date");
    private RadioButton rbNoFee = new RadioButton(By.id("serviceFee2"), "No Fee For Service");
    private RadioButton rbResetExitDateYes = new RadioButton(By.id("resetsExitDate1"), "Reset Exit Date - Yes");
    private RadioButton rbResetExitDateNo = new RadioButton(By.id("resetsExitDate2"), "Reset Exit Date - No");
    private RadioButton rbVeteranServiceAll = new RadioButton("//input[@name='veteranService' and @value='YES']", "Veteran Service - All");
    private ComboBox cmbQuestionType = new ComboBox(By.id("tempServiceQuestion.questionType"), "Question Type");
    private TextBox txbQuestion = new TextBox(By.id("tempServiceQuestion.question"), "Question");
    private Button btnAddQuestion = new Button(By.id("addQuestion"), "Add Question");
    private String questionType = "Open Answer";

    /**
     * Default constructor
     */
    public ServiceCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Service Creation')]"), "Service Creation");
    }


    /**
     * This method is used for adding custom question to Service with "Open Answer" type
     *
     * @param question Question text
     */
    public void addOpenAnswerQuestion(String question) {
        cmbQuestionType.select(questionType);
        txbQuestion.type(question);
        btnAddQuestion.clickAndWait();
    }

    /**
     * Select level
     *
     * @param value - value to be selected
     */
    public void selectLevel(String value) {
        cmbLevel.select(value);
    }

    /**
     * Input begin date
     *
     * @param date - begin date
     */
    public void inputBeginDate(String date) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbDateBegin.type(date);
    }

    /**
     * Click veteran service all radiobutton.
     */
    public void clickVeteranServiceAll() {
        rbVeteranServiceAll.click();
    }

    /**
     * Select reset exit date yes or no.
     *
     * @param resetExitDate - reset exit date.
     */
    public void selectResetExitDate(Boolean resetExitDate) {
        if (resetExitDate) {
            rbResetExitDateYes.click();
        } else {
            rbResetExitDateNo.click();
        }
    }

    /**
     * Click No fee radio button.
     */
    public void clickNoFee() {
        rbNoFee.click();
    }
}
