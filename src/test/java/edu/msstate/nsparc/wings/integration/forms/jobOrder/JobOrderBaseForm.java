package edu.msstate.nsparc.wings.integration.forms.jobOrder;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Job Order forms
 */
public class JobOrderBaseForm extends BaseWingsForm {

    protected TextBox txbDateHold = new TextBox("id=dateHoldRelease", "Date hold release");
    protected RadioButton rbJobDevelopmentNo = new RadioButton("css=input[id='isJobDevelopment2']", "Job Development - No");
    protected RadioButton rbAllowOnlineYes = new RadioButton("css=input[id='allowOnlineApplications1']", "Allow Online Applications - Yes");
    protected RadioButton rbAllowOnlineNo = new RadioButton("css=input[id='allowOnlineApplications2']", "Allow Online Applications - No");
    protected RadioButton rbOverrideNonVeteranDate = new RadioButton("css=input[id='overwriteNonVetDate1']", "Override non-veteran release date");
    protected TextBox txbDateNonVetRelease = new TextBox("css=input[id='dateNonVetRelease']", "Date Non Vet Release");
    protected Button btnRestrictStaffLookup = new Button(By.xpath("//input[@value='Staff']/following-sibling::button[1]"), "Restrict staff member");
    private TableCell tbcStaffLookUp = new TableCell(By.xpath("//span[@id='staffLookup']"), "Staff look up");
    private Button btnApply = new Button("id=apply", "Apply");
    private TextBox txbInitials = new TextBox("css=input#initials", "Initials");
    private Button btnConfirm = new Button("id=confirm", "Confirm");

    /**
     * Constructor of the form
     *
     * @param locator   - defined locator
     * @param formTitle - title of the form.
     */
    public JobOrderBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    public void validateStaffInfo(JobOrder order) {
        info(tbcStaffLookUp.getText().trim());
        CustomAssertion.softTrue("Order is not locked and doesn't contain restricted staff",
                tbcStaffLookUp.getText().trim().contains(order.getRestrictedFirstName()));
    }

    public void inputDateHold(String dateHold) {
        txbDateHold.type(dateHold);
    }

    /**
     * Click job development - no radio button.
     */
    public void clickJobDevelopmentNo() {
        rbJobDevelopmentNo.click();
    }

    public void clickAllowOnline(Boolean allowOnline) {
        if (allowOnline) {
            rbAllowOnlineYes.click();
        } else {
            rbAllowOnlineNo.click();
        }
    }

    public void clickOverrideNonVeteran() {
        rbOverrideNonVeteranDate.click();
    }

    public void inputNonVeteranReleaseDate(String date) {
        txbDateNonVetRelease.type(date);
    }

    public void apply() {
        btnApply.clickAndWait();
    }

    public void inputInitialsConfirm(String initials) {
        txbInitials.type(initials);
        btnConfirm.clickAndWait();
    }

    public void checkApplyNotPresent() {
        btnApply.assertIsNotPresent();
    }
}
