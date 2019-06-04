package edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Individual Employment Plans -> Create
 */
public class IndividualEmploymentPlanCreationForm extends IndividualEmploymentPlanBaseForm {

    private String tableCellXpath = "//table[@id='assessmentResults-table']//tbody//tr[%1$s]";
    private String rbCellXpath = tableCellXpath + "/td/input";
    private String assmXpath = tableCellXpath + "/td[%2$d]";
    private Button btnAddAssessment = new Button("css=button[id='addAssessment']", "Add Assessment");
    private Button btnEditAssessment = new Button("id=editAssessment","Edit assessment");
    private Button btnEditDisabled = new Button(By.xpath("//button[@id='editAssessment'][@disabled='disabled']"),"Disabled [Edit]");
    private Button btnRemoveAssessment = new Button("id=removeAssessment","Remove assessment");
    private Button btnRemoveDisabled = new Button(By.xpath("//button[@id='removeAssessment'][@disabled='disabled']"),"Disabled [Remove]");
    private RadioButton rbFirstStep = new RadioButton("id=planStepSelectedRadio1","Radio button of the first step");

    public IndividualEmploymentPlanCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Individual Employment Plan Creation')]"), "Individual Employment Plan Creation");
    }

    public void fillOutCreationForm(User user, IndividualEmploymentPlan iep) {
        selectParticipantByUser(user, iep.getParticipant());
        if (iep.isNonTraditionalEmployment()) {
            rbTraditionalEmploymentYes.click();
        } else {
            rbTraditionalEmploymentNo.click();
        }
        txbBarriersDescription.type(iep.getBarriersDescription());
        txbAptitudes.type(iep.getAptitudes());
        txbAbilities.type(iep.getAbilities());
        for (Assessment assessment : iep.getAssessments()) {
            addAssessment(assessment);
        }
        txbPlanDescription.type(iep.getPlanDescription());

        addPlanSteps(iep);
    }

    public void clickTraditionalEmployment() {
        rbTraditionalEmploymentYes.click();
    }

    public void addAssessment(Assessment assessment) {
        btnAddAssessment.clickAndWait();
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), assessment);
        creationForm.addAssessmentIEP();
    }

    public void editAssessment(String funcArea,String tpType, String numberAssm) {
        RadioButton rbAssm = new RadioButton(By.xpath(String.format(rbCellXpath,numberAssm)),"Assessment type");
        rbAssm.click();
        btnEditAssessment.clickAndWait();
        AssessmentEditForm editPage = new AssessmentEditForm();
        editPage.editAssessment(funcArea, tpType);
        editPage.saveChanges();
    }

    public void removeChosenAssessment(String numberAssm) {
        RadioButton rbAssm = new RadioButton(By.xpath(String.format(rbCellXpath, numberAssm)),"Assessment type");
        rbAssm.click();
        btnRemoveAssessment.clickAndWait();
    }

    /**
     * Select first step
     */
    public void selectFirstStep() {
        rbFirstStep.click();
    }

    public enum AssessmentAction { ADD, EDIT, ADD_EDIT, REMOVE }

    /**
     * Validate assessment data or check if it was removed.
     * @param action Add/ Edit/ Remove
     * @param numberAssm - assessment number
     * @param assmType assessment type
     * @param functionalArea functional area
     * @param dateAdm date administred
     */
    public void validateAssessment(AssessmentAction action, String numberAssm, String assmType, String functionalArea, String dateAdm) {
        TableCell tbcAssmType = new TableCell(By.xpath(String.format(assmXpath, numberAssm, 2)),"Assessment type");
        TableCell tbcFunctionalArea = new TableCell(By.xpath(String.format(assmXpath, numberAssm, 3)),"Functional area");
        TableCell tbcDateAdministred = new TableCell(By.xpath(String.format(assmXpath, numberAssm, 4)),"Date administred");
        switch (action) {
            case ADD_EDIT:
                CustomAssertion.softAssertEquals(tbcAssmType.getText(), assmType, "Incorrect assessment type");
                CustomAssertion.softAssertEquals(tbcFunctionalArea.getText(), functionalArea,"Incorrect functional area");
                CustomAssertion.softAssertEquals(tbcDateAdministred.getText(), dateAdm,"Incorrect date administred");
                break;
            case REMOVE:
                tbcAssmType.assertIsNotPresent();
                break;
            default:
                info("Try to enter some correct action");
                break;
        }
    }

    public void addEditRemoveAssessment(AssessmentAction type, IndividualEmploymentPlan plan, Assessment assm,
                                        IndividualEmploymentPlanCreationForm form, String assmNumber, String funcArea, String tpType) {
        switch (type) {
            case ADD:
                form.addAssessment(assm);
                plan.getAssessments().add(assm);
                break;
            case EDIT:
                form.editAssessment(funcArea, tpType, assmNumber);
                break;
            case REMOVE:
                form.removeChosenAssessment(assmNumber);
                break;
            default:
                info("Try to enter some correct data to the parameter 'type'");
                break;
        }
    }

    public void checkEditRemoveDisabled() {
        btnEditDisabled.isPresent();
        btnRemoveDisabled.isPresent();
    }

    /**
     * Check, that first step is not present.
     */
    public void firstStepNotPresent() {
        rbFirstStep.assertIsNotPresent();
    }
}
