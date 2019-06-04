package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.Div;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Data Utilities -> Participant Profile Merge
 */
public class ParticipantMergeForm extends BaseWingsForm {
    private String partialXpath = "/../../td[2]//input";
    private Div dvError = new Div(By.xpath("//font[@color='red']"), "Error on the merge form");
    private Div dvWarning = new Div(By.xpath("//font[@color='orange']"), "Warning on the merge form");
    private Button btnDiscardParticipantLookUp = new Button(By.xpath("//input[@target='participantMergeModel.discardParticipant']/following-sibling::button[1]"), "Discard Lookup");
    private Button btnKeepParticipantLookUp = new Button(By.xpath("//input[@target='participantMergeModel.keepParticipant']/following-sibling::button[1]"), "Keep Lookup");

    private ComboBox lstServiceEnrollments = new ComboBox("css=select#selectedDiscardEnrollments", "Service Enrollments");
    private Button btnAddServiceEnrollment = new Button(By.xpath("//select[@id='selectedDiscardEnrollments']" + partialXpath), "Add Service Enrollment");
    private Button btnRemoveServiceEnrollment = new Button(By.xpath("//input[@onclick[contains(.,'transferAllLeft')]]"), "Remove all service enrollments");

    private ComboBox lstEVerifys = new ComboBox(By.id("selectedDiscardEverifys"), "E-Verifys");
    private Button btnAddEVerify = new Button(By.xpath("//select[@id='selectedDiscardEverifys']" + partialXpath), "Add E-Verify");

    private ComboBox lstReferrals = new ComboBox(By.id("selectedDiscardReferrals"), "Referrals");
    private Button btnAddReferral = new Button(By.xpath("//select[@id='selectedDiscardReferrals']" + partialXpath), "Add Referral");

    private ComboBox lstAcademicHistory = new ComboBox(By.id("selectedDiscardAcademicRecords"), "Academic History");
    private Button btnAddAcademicHistory = new Button(By.xpath("//select[@id='selectedDiscardAcademicRecords']" + partialXpath), "Add Academic History");

    private ComboBox lstEmploymentHistory = new ComboBox(By.id("selectedDiscardPreviousJobs"), "Employment History");
    private Button btnAddEmployment = new Button(By.xpath("//select[@id='selectedDiscardPreviousJobs']" + partialXpath), "Add Employment");

    private ComboBox lstCallIn = new ComboBox(By.id("selectedDiscardCallins"), "Call-In");
    private Button btnAddCallIn = new Button(By.xpath("//select[@id='selectedDiscardCallins']" + partialXpath), "Add Call-In");

    private ComboBox lstAssessment = new ComboBox(By.id("selectedDiscardAssessments"), "Select assessment");
    private Button btnAddAssessment = new Button(By.xpath("//select[@id='selectedDiscardAssessments']" + partialXpath), "Add Assessment");

    private ComboBox lstWiaEnrollment = new ComboBox(By.id("selectedDiscardEnrollments"), "WIA enrollments list");
    String xpathSeList = "//select[@id='selectedDiscardEnrollments']/option[%1$d]";
    private Button btnAddAllWiaEnrollment = new Button(By.xpath("//select[@id='selectedDiscardWiaEnrollments']/../../td[2]//input[2]"), "Add All WIA Enrollment");
    private Button btnAddAllTradeEnrollment = new Button(By.xpath("//select[@id='selectedDiscardTradeEnrollments']/../../td[2]//input[2]"), "Add All Trade Enrollment");

    private ComboBox lstTradeEnrollments = new ComboBox("css=select[id='selectedDiscardTradeEnrollments']", "Trade Enrollments");
    private Button btnAddTradeEnrollment = new Button(By.xpath("//select[@id='selectedDiscardTradeEnrollments']" + partialXpath), "Add Trade Enrollment");

    private Button btnMerge = new Button("id=merge", "Merge");
    private Button btnFinish = new Button("id=finish", "Finish");
    private String discardEverify = "12345";
    private String academicHistory = "Auto School";
    private String employmentHistory = "Automation";
    private static final String errorTradeEnrl = "Both the selected participants are enrolled in Trade, "
            + "therefore this merge cannot be performed. Please contact your Trade Director.";
    private static final String errorParticipantSame = "Record to Discard and Record to Keep cannot be the same.";
    private static final String warningParticipantSsn = "The SSNs on these records do not match.";

    /**
     * Default constructor
     */
    public ParticipantMergeForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Participant Merge')]"), "Participant Merge");
    }

    /**
     * This method is used for searching and selecting Participant for Discard in look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectDiscardParticipant(Participant participant) {
        btnDiscardParticipantLookUp.clickAndWait();
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearchAndSelect(participant);
    }

    /**
     * This method is used for searching and selecting Participant for Keep in look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectKeepParticipant(Participant participant) {
        btnKeepParticipantLookUp.clickAndWait();
        ParticipantSearchForm searchForm = new ParticipantSearchForm();
        searchForm.performSearchAndSelect(participant);
    }

    /**
     * This function handles the possible occurrence of Participant Period Recalculation form
     */
    public void completeMerging() {
        btnMerge.clickAndWait();
        btnFinish.clickAndWait();
        passParticipationRecalculationPage();
        BaseButton.SAVE_CHANGES.getButton().assertIsNotPresent();
    }

    /**
     * move service enrollment from discard to keep participant
     *
     * @param serviceName - name of the service
     */
    public void fromDiscardToParticipant(String serviceName) {
        info("Move Service Enrollment from discard to keep participant");
        lstServiceEnrollments.select(serviceName);
        btnAddServiceEnrollment.click();
    }

    /**
     * Add e-verify
     */
    public void addVerify() {
        info("E-Verify");
        lstEVerifys.select(discardEverify);
        btnAddEVerify.click();
    }

    /**
     * Add assessment to merge
     *
     * @param assessment - assessment
     */
    public void addAssessment(Assessment assessment) {
        lstAssessment.select(assessment.getType() + " - " + assessment.getFunctionalArea());
        btnAddAssessment.clickAndWait();
    }

    /**
     * Select some parameters for merging (Referral)
     *
     * @param jobOrder - jobOrder
     */
    public void selectParametersMergeReferral(JobOrder jobOrder) {
        lstReferrals.select(jobOrder.getJobTitle());
        btnAddReferral.click();
    }

    /**
     * Add academic history for 'Auto school'
     */
    public void addAcademicHistoryAuto() {
        info("Academic");
        lstAcademicHistory.select(academicHistory);
        btnAddAcademicHistory.click();
    }

    /**
     * Add employment to participant - select 'Automation' employment history
     */
    public void addEmployment() {
        info("Employment");
        lstEmploymentHistory.select(employmentHistory);
        btnAddEmployment.click();
    }

    /**
     * Add Trade enrollment to participant
     */
    public void addTradeEnrollment(TradeEnrollment tradeEnrollment) {
        lstTradeEnrollments.select(tradeEnrollment.getFullTradeEnrollmentName());
        btnAddTradeEnrollment.click();
    }

    /**
     * Validate, that service enrollment list has no WIA values.
     */
    public void validateSeHasNoValues(String valueCheck) {
        int i = 1;
        Div divOptionSe = new Div(By.xpath(String.format(xpathSeList, i)), "SE option list");
        while (divOptionSe.isPresent()) {
            CustomAssertion.softNotTrue("Service Enrollment List has specified values",
                    divOptionSe.getText().contains(valueCheck));
            i++;
            divOptionSe = new Div(By.xpath(String.format(xpathSeList, i)), "SE option list");
        }
    }

    /**
     * Add all WIA enrollments from discard to keep participant
     */
    public void addAllWiaEnrollment() {
        btnAddAllWiaEnrollment.click();
    }

    /**
     * All all Trade enrollments from discard to keep participant
     */
    public void addAllTradeEnrollment() {
        btnAddAllTradeEnrollment.click();
    }

    /**
     * Remove service enrollments from keep.
     */
    public void removeServiceKeep() {
        btnRemoveServiceEnrollment.clickAndWait();
    }

    /**
     * Add call-in from discard to keep participant.
     */
    public void addCallIn(String callIn) {
        lstCallIn.select(callIn);
        btnAddCallIn.click();
    }

    /**
     * Check error text appeared on trying to merge records.
     *
     * @param mergeType - merge type (see enum ErrorMergeType for the information)
     */
    public void validateErrorMerge(ErrorMergeType mergeType) {
        switch (mergeType) {
            case SAME_PARTICIPANT:
                CustomAssertion.softTrue("The same participant error message is not correct or not displayed",
                        dvError.getText().equals(errorParticipantSame));
                break;
            case BOTH_TRADE_ENROLLMENT:
                CustomAssertion.softTrue("The same participant error message is not correct or not displayed",
                        dvError.getText().equals(errorTradeEnrl));
                break;
            case DIFFERENT_SSN:
                CustomAssertion.softTrue("The differents ssn participant warning message is not correct or not displayed",
                        dvWarning.getText().equals(warningParticipantSsn));
                break;
            default:
                info("MergeType is not recognized, try to enter the correct one.");
                break;
        }
    }

    /**
     * Error merging type
     */
    public enum ErrorMergeType {
        SAME_PARTICIPANT, BOTH_TRADE_ENROLLMENT, DIFFERENT_SSN
    }
}
