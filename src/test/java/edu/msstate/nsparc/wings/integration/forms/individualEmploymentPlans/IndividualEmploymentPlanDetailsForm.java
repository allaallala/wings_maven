package edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * This form is opened via Participants -> Individual Employment Plans -> Search for record -> Open record
 */
public class IndividualEmploymentPlanDetailsForm extends IndividualEmploymentPlanBaseForm {

    private static final String ASSESSMENT_LOCATOR = "//table[@id='assessmentResults-table']//tbody//tr";
    private static final String PLAN_STEP_LOCATOR = "//table[@id='planStepResults-table']//tbody//tr";

    private String xpathExp = "//*[@id='individualEmploymentPlanViewForm']//tr[contains(.,'%1$s')]/following-sibling::tr[1]";
    private String xpathPrint = "//form[@id='individualEmploymentPlan']//tr[%1$s]/%2$s";

    private TableCell tbcSecondaryCaseManager = new TableCell(By.xpath("//table[@id='caseManagerResults-table']//tbody//td"), "Name of the first secondary case manager");
    private TableCell tbcCreationDate = new TableCell(By.xpath("//td[contains(.,'Date Plan Created')]/following-sibling::td"), "Date Plan Created");
    private TableCell tbcBarriersDescription = new TableCell(By.xpath(String.format(xpathExp, "Barriers Description")), "Barriers Description");
    private TableCell tbcAptitudes = new TableCell(By.xpath(String.format(xpathExp, "Aptitudes")), "Aptitudes");
    private TableCell tbcAbilities = new TableCell(By.xpath(String.format(xpathExp, "Abilities")), "Abilities");
    private TableCell tbcPlanDescription = new TableCell(By.xpath(String.format(xpathExp, "Plan Description")), "Plan Description value");
    private TableCell tbcDateSigned = new TableCell(By.xpath("//table[@id='formsResults-table']//tbody/tr/td[3]"), "Date signed");
    private TableCell tbcAssmType = new TableCell(By.xpath(ASSESSMENT_LOCATOR + "//td[1]"), "Assessment type");
    private TableCell tbcFunctionalArea = new TableCell(By.xpath(ASSESSMENT_LOCATOR + "//td[2]"), "Assessment functional area");
    private TableCell tbcDateAdmin = new TableCell(By.xpath(ASSESSMENT_LOCATOR + "//td[3]"), "Assessment date admin.");

    private Button btnPreviewAssessment = new Button(By.xpath("//table[@id='assessmentResults-table']//td//img"), "Preview Assessment");
    private TextBox txbDataSigned = new TextBox("id=dateSigned1", "Data signed");
    private Button btnSave = new Button("//input[@value='Save']", "Save button");
    private Button btnPrint = new Button("id=printForm", "Button [Print]");
    private Button btnCreateAnother = new Button("id=createAnother", "Create another");

    //elements on the [Print] form.
    private TableCell tbcPrintPartFirstName = new TableCell(By.xpath(String.format(xpathPrint, "2", "/tr/td[1]")), "Participant's First Name");
    private TableCell tbcPrintPartLastName = new TableCell(By.xpath(String.format(xpathPrint, "2", "/tr/td[2]")), "Participant's Last Name");
    private TableCell tbcPrintPartSsn = new TableCell(By.xpath(String.format(xpathPrint, "2", "/tr/td[4]")), "Participant's Ssn");
    private TableCell tbcPrintPlanCreated = new TableCell(By.xpath(String.format(xpathPrint, "8", "/td[2]")), "Date Plan created");
    private TableCell tbcPrintPlanDescription = new TableCell(By.xpath(String.format(xpathPrint, "26", "/td[1]")), "Print Plan Description");
    private TableCell tbcPrintPlanStepFirst = new TableCell(By.xpath(String.format(xpathPrint, "31", "/td[1]")), "First Plan Step Description");
    private TableCell tbcPrintStepCompletionDateFirst = new TableCell(By.xpath(String.format(xpathPrint, "31", "/td[2]")), "First Plan Step Completion Date");

    /**
     * Default constructor
     */
    public IndividualEmploymentPlanDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Individual Employment Plan Detail')]"), "Individual Employment Plan Details");
    }

    /**
     * Validating displayed Individual Employment Plan information
     *
     * @param iep Object with Individual Employment Plan information
     */
    public void validateInformation(IndividualEmploymentPlan iep) {
        CustomAssertion.softAssertEquals(tbcCreationDate.getText(), iep.getCreationDate(), "Incorrect IEP creation date");
        CustomAssertion.softAssertEquals(tbcBarriersDescription.getText(), iep.getBarriersDescription(), "Incorrect barriers description");
        CustomAssertion.softAssertEquals(tbcAptitudes.getText(), iep.getAptitudes(), "Incorrect IEP aptitudes");
        CustomAssertion.softAssertEquals(tbcAbilities.getText(), iep.getAbilities(), "Incorrect IEP abilities");
        CustomAssertion.softAssertEquals(tbcPlanDescription.getText(), iep.getPlanDescription(), "Incorrect IEP plan description");
        assertEquals(iep.getAssessments().size(), getNumberOfAssessments(), "Not all Assessments are displayed");
        assertEquals(iep.getPlanSteps().size(), getNumberOfPlanSteps(), "Not all Plan Steps are displayed");
        info("Information succefully checked.");
    }

    /**
     * Check data on the [Print] form.
     *
     * @param iep - Individual Employment Plan.
     */
    public void validatePrintContent(IndividualEmploymentPlan iep) {
        CustomAssertion.softAssertEquals(tbcPrintPartFirstName.getText(), iep.getParticipant().getFirstName(),
                "Print: Incorrect participant first name");
        CustomAssertion.softAssertEquals(tbcPrintPartLastName.getText(), iep.getParticipant().getLastName(),
                "Print: Incorrect participant last name");
        CustomAssertion.softAssertEquals(tbcPrintPartSsn.getText(), iep.getParticipant().getSsn(),
                "Print: Incorrect ssn");
        CustomAssertion.softAssertEquals(tbcPrintPlanCreated.getText(), iep.getCreationDate(),
                "Print: Incorrect plan creation date");
        CustomAssertion.softAssertEquals(tbcPrintPlanDescription.getText(), iep.getPlanDescription(),
                "Print: Incorrect plan description");
        CustomAssertion.softAssertEquals(tbcPrintPlanStepFirst.getText(), iep.getPlanSteps().get(0).getStepInformation(),
                "Print: Incorrect first plan step");
        CustomAssertion.softAssertEquals(tbcPrintStepCompletionDateFirst.getText(), iep.getPlanSteps().get(0).getCompletionDate(),
                "Print: Incorrect first step completion date");
    }

    /**
     * Returns number of displayed Assessments
     *
     * @return Number of Assessments
     */
    private int getNumberOfAssessments() {
        return getNumberOfElementsPresent(By.xpath(ASSESSMENT_LOCATOR));
    }

    /**
     * Returns number of displayed Plan Steps
     *
     * @return Number of Plan Steps
     */
    public int getNumberOfPlanSteps() {
        return getNumberOfElementsPresent(By.xpath(PLAN_STEP_LOCATOR));
    }

    /**
     * Check elements and buttons are present on the page (based on user permissions)
     *
     * @param user - current user
     */
    public void checkElementsPresent(User user, String date, IndividualEmploymentPlan iep) {
        //(!) Check [Edit], [Audit] are present (or not) on the page.
        checkButtonsPresent(user.getIEP().getIepViewEditButton(), user.getIEP().getIepViewAudit());

        //(!) Check, that [Data signed] is present (or not) on the page
        divideMessage("Check [Date Signed] present");
        checkSignedPresent(user.getIEP().getIepViewFormSign(), date);

        //(!) Check [Print] button is present or not on the page.
        divideMessage("Print IEP Form");
        if (user.getIEP().getIepViewPrint()) {
            btnPrint.clickAndWait();
            validatePrintContent(iep);
            clickButton(Buttons.Done);
        } else {
            btnPrint.assertIsNotPresent();
        }
    }

    /**
     * Check information about last step added.
     *
     * @param planStep - plan step, that we added before.
     * @param count    - quantity of steps
     * @param state    - if is required to check step
     */
    public void validateLastStep(IndividualEmploymentPlan.PlanStep planStep, Integer count, boolean state) {
        String planStepDescription = PLAN_STEP_LOCATOR + "[%1$d]/td[1]";
        String planStepDate = PLAN_STEP_LOCATOR + "[%1$d]/td[2]";
        TableCell tbcPlanStep = new TableCell(By.xpath(String.format(planStepDescription, count)), "First Plan Step Description");
        TableCell tbcPlanStepDate = new TableCell(By.xpath(String.format(planStepDate, count)), "First Plan Step Date");
        if (state) {
            checkField(tbcPlanStep, planStep.getStepInformation(), Constants.TRUE);
            checkField(tbcPlanStepDate, planStep.getCompletionDate(), Constants.TRUE);
        } else {
            tbcPlanStep.assertIsNotPresent();
            tbcPlanStepDate.assertIsNotPresent();
        }
    }

    /**
     * Validate assessment data on the form
     *
     * @param plan - individual employment plan
     */
    public void validateAssessmentData(IndividualEmploymentPlan plan) {
        CustomAssertion.softAssertEquals(tbcAssmType.getText(), plan.getAssessments().get(0).getType(),
                "Incorrect type of assessment");
        CustomAssertion.softAssertEquals(tbcFunctionalArea.getText(), plan.getAssessments().get(0).getFunctionalArea(),
                "Incorrect functional area of assessment");
        CustomAssertion.softAssertEquals(tbcDateAdmin.getText(), plan.getAssessments().get(0).getDateAdministered(),
                "Incorrect date administration of assessment");
    }

    /**
     * Choose radiobutton of existed step.
     */
    public void chooseDefinedStep() {
        RadioButton rbDefinedStep = new RadioButton(String.format("css=input[id='planStepSelectedRadio%1$d']", getNumberOfPlanSteps()), "Radio Button of chosen step");
        rbDefinedStep.click();
    }

    /**
     * Input date signed
     *
     * @param date - date
     */
    public void inputDataSigned(String date) {
        txbDataSigned.type(date);
    }

    /**
     * Create another individual employment plan
     */
    public void createAnotherIep() {
        btnCreateAnother.clickAndWait();
        IndividualEmploymentPlanCreationForm creationPage = new IndividualEmploymentPlanCreationForm();
        creationPage.clickTraditionalEmployment();
        creationPage.clickButton(Buttons.Create);
    }

    /**
     * Edit individual employment plan
     *
     * @param plan - individual employment plan
     * @param date - date to edit.
     */
    public void editIEP(IndividualEmploymentPlan plan, String date) {
        clickButton(Buttons.Edit);
        IndividualEmploymentPlanEditForm editPage = new IndividualEmploymentPlanEditForm();
        plan.setCreationDate(date);
        editPage.editSomeData(date);
        editPage.clickButton(Buttons.Save);
        validateInformation(plan);
    }


    public void checkSignedPresent(Boolean present, String date) {
        if (present) {
            txbDataSigned.assertIsPresent();
            inputDataSigned(date);
            btnSave.click();
            click(BaseButton.ARE_YOU_SURE_YES);
            BaseButton.ARE_YOU_SURE_YES.getButton().waitForNotVisible();
            assertTrue(getDataSignedText().contains(date));
            info("Field 'Data signed' is present");
        } else {
            txbDataSigned.assertIsNotPresent();
            info("Field 'Data signed' is not present");
        }
    }

    public void openPrintForm() {
        btnPrint.clickAndWait();
    }

    public void saveChanges() {
        btnSave.click();
    }

    public void previewAssessment() {
        btnPreviewAssessment.click();
    }

    public String getDataSignedText() {
        return tbcDateSigned.getText();
    }

    public String getSecondaryManagerText() {
        return tbcSecondaryCaseManager.getText();
    }
}
