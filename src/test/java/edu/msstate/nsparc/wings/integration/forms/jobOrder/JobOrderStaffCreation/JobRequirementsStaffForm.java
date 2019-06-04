package edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.elements.*;
import org.openqa.selenium.By;

public class JobRequirementsStaffForm extends JobOrderCreationForm {

    private TextBox txbHoursPerWeek = new TextBox("id=hoursPerWeek", "Hours per Week");
    private RadioButton rbScheduleHoursPartTime = new RadioButton("//input[@name='scheduleHours' and @value='PART_TIME']", "Schedule Hours - Part-Time");
    private RadioButton rbScheduleTermPermanent = new RadioButton("//input[@name='scheduleTerm' and @value='PERMANENT']", "Schedule Term - Permanent");
    private CheckBox chkShiftDays = new CheckBox("id=shiftDay1", "Shift by Days");
    private CheckBox chkWorkMonday = new CheckBox("id=workdayM1", "Work on Monday");
    private RadioButton rbDriversLicenseRequired = new RadioButton("css=input#needDriversLicense1", "Driver's License Required");
    private ComboBox cmbDriversLicenseClass = new ComboBox("id=driversLicense.dlClass", "Drivers License Required Class");
    private RadioButton rbDriversLicenseClassB = new RadioButton("id=driversLicense.dlClass3", "Drivers License - Class B");
    private ComboBox cmbEducationLevelRequired = new ComboBox("css=select[id='reqsEduLevel']", "Education Level Required");
    private Button btnSkills = new Button(By.xpath("//button[contains(@class,'multiselect')]"), "Skills button");
    private CheckBox chkRequiredToApplyEducationLevel = new CheckBox("id=blockerEdLevel1", "Required to apply online");
    private CheckBox chbEduOnline = new CheckBox(By.id("blockerEdLevel1"), "Education apply online");
    private CheckBox chbSkillsOnline = new CheckBox(By.id("blockerSkills1"), "Skills apply online");
    private CheckBox chbDrivOnline = new CheckBox(By.id("blockerDl1"), "Driver's license apply online");
    private CheckBox chbLicenseOnline = new CheckBox(By.id("blockerDlClass1"), "License category apply online");
    private Button btnDriverApply = new Button(By.xpath("//input[@value='Apply'][@field='needDriversLicense']"), "Apply");

    public JobRequirementsStaffForm() {
        super(By.xpath("//h1[contains(.,'Job Order Creation: Job Requirements')]"), "Job Order Creation: Job Requirements");
    }

    public void fillSecondPage() {
        passWorkingHoursFields();
        clickButton(Buttons.Continue);
    }

    public void passWorkingHoursFieldsAndSave(JobOrder jobOrder) {
        txbHoursPerWeek.type(quantity);
        rbScheduleHoursPartTime.click();
        rbScheduleTermPermanent.click();
        chkShiftDays.click();
        chkWorkMonday.click();
        if (jobOrder.getIfAcademicRequired()) {
            cmbEducationLevelRequired.select(jobOrder.getRequiredAcademic());
            if (jobOrder.getRequiredApplyOnline()) {
                chkRequiredToApplyEducationLevel.click();
            }
        }
        clickButton(Buttons.Continue);
    }

    public void passWorkingHoursFields() {
        txbHoursPerWeek.type(quantity);
        rbScheduleHoursPartTime.click();
        rbScheduleTermPermanent.click();
        chkShiftDays.click();
        chkWorkMonday.click();
    }

    public void fillHoursDriver() {
        new JobRequirementsStaffForm().passWorkingHoursFields();
        rbDriversLicenseRequired.click();
        waitDivLoading();
        cmbDriversLicenseClass.select(driverLicenseClass);
        cmbEducationLevelRequired.select(BACHELOR_DEGREE);
        clickButton(Buttons.Continue);
        clickButton(Buttons.Continue);
        clickAllowOnline(Constants.TRUE);
        clickJobDevelopmentNo();
    }

    public void fillSecondPageWithBlockers() {
        final String wokRanges = "Wok ranges";
        Button btnItem = new Button(By.xpath(String.format(skillsItemLocator, wokRanges)), "Skill item");
        clickButton(Buttons.Continue);
        cmbEducationLevelRequired.select(BACHELOR_DEGREE);
        rbDriversLicenseRequired.click();
        if (btnDriverApply.isPresent()) {
            btnDriverApply.click();
        }
        rbDriversLicenseClassB.click();
        btnSkills.click();
        btnItem.waitForIsElementPresent();
        btnItem.click();
        chbEduOnline.click();
        chbSkillsOnline.click();
        chbDrivOnline.click();
        chbLicenseOnline.click();
        clickButton(Buttons.Continue);
    }
}