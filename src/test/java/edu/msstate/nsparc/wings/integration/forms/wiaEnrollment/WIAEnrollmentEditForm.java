package edu.msstate.nsparc.wings.integration.forms.wiaEnrollment;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.CommonFunctions;
import framework.elements.Button;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> WIA Enrollments -> Search for record -> Open record ->
 * Click on 'Edit' button
 */
public class WIAEnrollmentEditForm extends WIAEnrollmentBaseForm {

    private Button btnGoToYouthInfo = new Button("id=toYouthInfo", "Go to Youth Information");
    private Button btnPrevious = new Button("id=previous", "Previous");

    /**
     * Default constructor
     */
    public WIAEnrollmentEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIA Enrollment Edit')]"), "Wia Enrollment Edit");
    }

    /**
     * Click [Previous] button.
     */
    public void clickPrevious() {
        btnPrevious.clickAndWait();
    }

    /**
     * Go to youth information
     */
    public void goYouthInfo() {
        btnGoToYouthInfo.clickAndWait();
    }

    /**
     * This method is used for filling Basic Information with provided data
     *
     * @param applicationDate - application date
     * @param contactPerson   - contact person
     * @param contactPhone    - contact phone
     * @param relation        - relation.
     */
    public void fillWIAEnrollmentBasicInformation(String applicationDate, String contactPerson, String contactPhone, String relation) {
        inputApplicationDate(applicationDate);
        inputContactPerson(contactPerson);
        txbContactPhone.type(contactPhone);
        inputParticipantRelation(relation);
    }

    /**
     * This method is used for filling Program Information with provided data
     *
     * @param numberFamily - number in family
     * @param wages        - program wages
     * @param familyIncome - annual family income.
     */
    public void fillWIAEnrollmentProgramInformation(String numberFamily, String wages, String familyIncome) {
        inputNumberFamily(numberFamily);
        inputProgramWages(wages);
        inputAnnualFamilyIncome(familyIncome);
    }

    /**
     * This method is used for changing participant type and WIB
     *
     * @param participantType New participant type
     * @param wib             New wib
     */
    public void changeParticipantTypeAndWIB(String participantType, String wib) {
        selectParticipantType(participantType);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbWIB.select(wib);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }

    /**
     * This method is used for changing Youth Information of WIA Enrollment
     *
     * @param educationalStatus New participant educational status
     * @param toolUsed          New tool used for determine Basic Skills
     * @param youth             Indicates if participant is youth
     */
    public void fillWIAEnrollmentYouthInformation(String educationalStatus, String toolUsed, boolean youth) {
        if (youth) {
            fillBasicYouthInformation(educationalStatus);

            rbBasicSkillsDeficientNo.click();
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
            cmbToolUsed.select(toolUsed);
            txbNewTool.waitForIsElementPresent();
            txbNewTool.type(CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH));
        }
    }

    /**
     * Edit basic information data.
     *
     * @param applicationDate - application date
     * @param contactPerson   - contact person
     * @param contactPhone    - contact phone
     * @param relation        - participant relation
     */
    public void editBasicInfo(String applicationDate, String contactPerson, String contactPhone, String relation) {
        fillWIAEnrollmentBasicInformation(applicationDate, contactPerson, contactPhone, relation);
        clickButton(Buttons.Save);
    }

    /**
     * Edit program information
     *
     * @param numberFamily - number of the family members
     * @param wages        - annual programe wages
     * @param familyIncome - annual family income
     */
    public void editProgramInfo(String numberFamily, String wages, String familyIncome) {
        fillWIAEnrollmentProgramInformation(numberFamily, wages, familyIncome);
        clickButton(Buttons.Save);
    }
}
