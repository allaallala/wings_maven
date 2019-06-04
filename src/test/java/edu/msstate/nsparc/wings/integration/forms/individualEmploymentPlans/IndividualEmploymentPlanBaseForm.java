package edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextArea;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Individual Employment Plan forms
 */
public class IndividualEmploymentPlanBaseForm extends BaseWingsForm {

    protected RadioButton rbTraditionalEmploymentYes = new RadioButton("css=input[id='nonTraditionalEmployment1']", "Non-Traditional Employment - Yes");
    protected RadioButton rbTraditionalEmploymentNo = new RadioButton("css=input[id='nonTraditionalEmployment2']", "Non-Traditional Employment - No");
    protected TextArea txbBarriersDescription = new TextArea("css=textarea[id='barriersDescription']", "Barriers Description");
    protected TextArea txbAptitudes = new TextArea("css=textarea[id='aptitudes']", "Aptitudes");
    protected TextArea txbAbilities = new TextArea("css=textarea[id='abilities']", "Abilities");
    protected TextArea txbPlanDescription = new TextArea("css=textarea[id='planDescription']", "Plan Description");
    private RadioButton rbFirstAssessment = new RadioButton("id=assessmentSelectedRadio1","First Assessment's radio button");

    // Plan Steps
    private TextBox txbPlanStepInformation = new TextBox("css=input[id='tmpStep.step']", "Plan Step information");
    private TextBox txbPlanStepCompletionDate = new TextBox("css=input[id='tmpStep.dateAnticipatedCompletion']", "Plan Step Completion Date");
    private Button btnAddPlanStep = new Button("css=button[id='addStep']", "Add Plan Step");
    private RadioButton rbPlanStepSelection = new RadioButton("css=input[id='planStepSelectedRadio1']", "First Plan Step ");
    private Button btnRemoveStep = new Button("css=button[id='removeStep']", "Remove Step");
    private Button btnRemoveStepDisabled = new Button(By.xpath("//button[@id='removeStep'][@disabled='disabled']"),"Disabled button [Remove] step");
    private RadioButton rbFirstCaseManager = new RadioButton("id=caseManagerSelectedRadio1", "Radio button of the first case manager");
    private Button btnRemoveCaseManager = new Button("id=removeCaseManager","Remove case manager");
    private Button btnRemoveCaseManagerDisabled = new Button(By.xpath("//button[@id='removeCaseManager'][@disabled='disabled']"),"Remove case manager disabled");

    public IndividualEmploymentPlanBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    public void addPlanSteps(IndividualEmploymentPlan iep) {
        for (IndividualEmploymentPlan.PlanStep planStep : iep.getPlanSteps()) {
            addSinglePlanStep(planStep);
        }
    }

    public void addSinglePlanStep(IndividualEmploymentPlan.PlanStep planStep) {
        txbPlanStepInformation.type(planStep.getStepInformation());
        txbPlanStepCompletionDate.type(planStep.getCompletionDate());
        btnAddPlanStep.clickAndWait();
    }

    public void removeExistingPlanSteps() {
        while (true) {
            if (rbPlanStepSelection.isPresent()) {
                rbPlanStepSelection.click();
                btnRemoveStep.clickAndWait();
            } else {
                break;
            }
        }
    }

    public void chooseFirstAssessment() {
        rbFirstAssessment.click();
    }

    public void chooseFirstCaseManager() {
        rbFirstCaseManager.click();
    }

    public void removeCaseManager() {
        btnRemoveCaseManager.clickAndWait();
    }

    public void removeStep() {
        btnRemoveStep.clickAndWait();
    }

    public void checkFirstAssmNotPresent() {
        rbFirstAssessment.assertIsNotPresent();
    }

    public void chooseFirstPlanStep() {
        rbPlanStepSelection.click();
        btnRemoveStep.isPresent();
    }

    public void checkRemoveDisabledPresent() {
        btnRemoveStepDisabled.isPresent();
    }

    public void checkRemoveManagerDisabled() {
        btnRemoveCaseManagerDisabled.assertIsPresentAndVisible();
    }
}
