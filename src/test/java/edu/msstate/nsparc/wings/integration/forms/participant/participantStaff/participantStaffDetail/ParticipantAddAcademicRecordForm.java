package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.CIPSearch;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantBaseForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static org.testng.AssertJUnit.assertTrue;

/**
 * This form is opened from Participant Details form by clicking on 'Add Academic Record' button
 */
public class ParticipantAddAcademicRecordForm extends ParticipantBaseForm {

    private TextBox txbSchoolName = new TextBox("id=tmpAcademicRecord.schoolName", "School name");
    private TextBox txbCity = new TextBox(By.id("tmpAcademicRecord.schoolAddress.city"), "Address - City");
    private ComboBox cmbState = new ComboBox("css=select[id='tmpAcademicRecord.schoolAddress.state']", "Address - State");
    private ComboBox cmbDegree = new ComboBox(By.xpath("//select[@id='tmpAcademicRecord.degreeType'] "
            + "| //select[@id='tmpAcademicRecord.degreeType']"), "Degree Type");
    private TextBox txbMajor = new TextBox("id=tmpAcademicRecord.major", "Major");
    private TextBox txbDateStarted = new TextBox("css=input[id='tmpAcademicRecord.dateStart']", "Date Started School");
    private TextBox txbExpirationDate = new TextBox(By.id("tmpAcademicRecord.dateExpire"), "Expiration date");
    private RadioButton rbCompletedProgramYes = new RadioButton("css=input[id='tmpAcademicRecord.completedDegree1']", "Completed Program - Yes");
    private RadioButton rbCompletedProgramNo = new RadioButton("css=input[id='tmpAcademicRecord.completedDegree2']", "Completed Program - No");
    private TextBox txbDateLeft = new TextBox("css=input[id='tmpAcademicRecord.dateEnd']", "Date Left School");
    private RadioButton rbInSchoolYes = new RadioButton("css=input[id='tmpAcademicRecord.inSchool1']", "In School - Yes");
    private RadioButton rbInSchoolNo = new RadioButton("css=input[id='tmpAcademicRecord.inSchool2']", "In School - No");
    private Div divAcademicRecordForm = new Div("css=div[id='addNewSchoolForm']", "New Academic Record Form");
    private Button btnLookupMajor = new Button(By.xpath("//span[@id='cipLookup']//button"), "Major/Certificate/License");
    private Button btnRemoveAddedMajor = new Button("//a[@class='powerLookupRemoveButton']", "Remove Major");
    private TextBox txbZip = new TextBox(By.id("tmpAcademicRecord.schoolAddress.zipcode"), "Zip");
    private TextBox txbGpa = new TextBox(By.id("tmpAcademicRecord.gpa"), "GPA");
    private Button btnApply = new Button(By.xpath("//input[@value='Apply']"), "Apply");

    private String major = "Mechatronics";
    private String school = "Auto School";
    private String state = "Mississippi";

    public ParticipantAddAcademicRecordForm() {
        super(By.xpath("//h1[contains(.,'Academic Record')] | //div[@id='heading-title'][contains(.,'Academic Record')]"), "Add New Academic Record");
    }

    public ParticipantAddAcademicRecordForm(String participantSS) {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Academic Record')]"), "Add academic record form for " + participantSS);
    }

    public void checkDateLeft(Boolean wait) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (wait) {
            txbDateLeft.waitForIsElementPresent();
        } else {
            txbDateLeft.waitForNotVisible();
        }
    }

    public void addRecord(String date, String degreeType, boolean isParticipantSS) {
        final String degree = "High School Diploma";
        txbSchoolName.type(school);
        txbCity.type(CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH));
        cmbState.select(state);
        cmbDegree.select(degreeType);
        Browser.getInstance().waitForPageToLoad();
        txbDateStarted.type(date);
        rbCompletedProgramYes.click();
        txbDateLeft.type(date);
        if (!degreeType.equals(degree)) {
            addRecordCore(isParticipantSS);
        }
    }

    private void addRecordCore(boolean isParticipantSS) {
        if (isParticipantSS) {
            proceedMajorField(major);
        } else {
            selectMajor();
        }
    }

    public void proceedMajorField(String certMajor) {
        int waitSec = 0;
        Div dAddCert = new Div(By.xpath(String.format("//div[@id='cipCodes'][contains(.,'%1$s')]", certMajor)), "Added Major");
        Link lnkCert = new Link(By.xpath(String.format("//strong[contains(.,'%1$s')]", certMajor)), "Certification chosen");
        while (!lnkCert.isPresent() && waitSec < 20) {
            txbMajor.type(certMajor);
            Browser.getDriver().getMouse().mouseMove(lnkCert.getElement().getCoordinates());
            JavascriptExecutor jse = Browser.getDriver();
            jse.executeScript("arguments[0].click();", lnkCert.getElement());

            if (dAddCert.isPresent()) {
                break;
            }
        }
    }

    private void selectMajor() {

        btnLookupMajor.waitClickAndWait();
        CIPSearch cipSearch = new CIPSearch();
        cipSearch.selectSpecificMajor(major);
        assertTrue("Major Title was not added!", btnRemoveAddedMajor.isPresent());
    }

    public void selectParticipantSchool(Boolean inSchool) {
        if (inSchool) {
            rbInSchoolYes.click();
        } else {
            rbInSchoolNo.click();
        }
    }

    public void selectDegree(String degree) {
        cmbDegree.select(degree);
        if (btnApply.isPresent()) {
            btnApply.clickAndWait();
        }
    }

    public void inputGpa(String gpa) {
        txbGpa.type(gpa);
    }

    public void selectCompletedProgram(Boolean completedProgram) {
        if (completedProgram) {
            rbCompletedProgramYes.click();
        } else {
            rbCompletedProgramNo.click();
        }
    }

    public void fillOtherEducationFields(Participant partip, String dateStart, String dateEnd) {
        txbSchoolName.type(partip.getFirstName());
        txbCity.type(partip.getAddress().getCity());
        cmbState.select(partip.getAddress().getState());
        txbZip.type(partip.getAddress().getZipCode());
        txbDateStarted.type(dateStart);
        if (txbExpirationDate.isPresent()) {
            txbExpirationDate.type(dateEnd);
        }
        if (txbDateLeft.isPresent()) {
            txbDateLeft.type(dateEnd);
        }
    }

    public String getTextAcademicRecordForm() {
        return divAcademicRecordForm.getText().trim();
    }
}
