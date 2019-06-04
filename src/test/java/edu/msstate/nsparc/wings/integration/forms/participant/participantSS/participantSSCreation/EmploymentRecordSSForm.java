package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.EmploymentRecord;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import webdriver.Browser;
import framework.CommonFunctions;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.Link;
import framework.elements.TextBox;
import org.openqa.selenium.By;

public class EmploymentRecordSSForm extends ParticipantCreationForm {
    private Button btnFinish = new Button(By.xpath("//button[@title='Finish']"), "Finish");

    public EmploymentRecordSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Employment Record')]"), "Participant S-S Creation: Employment Record");
    }

    /**
     * Start typing osoc title and try to click the suggested osoc.
     *
     * @param osoc  - osoc to type
     * @param txbOs - specific text box, that is used input OSOC.
     */
    private void addWaitOsoc(String osoc, TextBox txbOs) {
        int waitSec = 0;
        Link lnkStrongOsoc = new Link(By.xpath(String.format("//strong[contains(.,'%1$s')]", osoc)), "Osoc link");
        Div dvAddedOsoc = new Div(By.xpath(String.format("//li[contains(.,'%1$s')]", osoc)), "Added osoc");
        while (!lnkStrongOsoc.isPresent() && waitSec < 20) {
            txbOs.type(osoc);
            lnkStrongOsoc.moveMouse();
            lnkStrongOsoc.clickJs();

            if (dvAddedOsoc.isPresent()) {
                break;
            }
        }
    }
    /**
     * Fill in employment self service page
     *
     * @param participant - participant
     */
    public void fillEmploymentSelfServicePage(Participant participant) {
        int waitSec = 0;
        EmploymentRecord employmentRecord = new EmploymentRecord();
        employmentRecord.setEmployerName(participant.getEmployerName());
        employmentRecord.setJobTitle(participant.getJobTitle());
        employmentRecord.setDateStartWorking(CommonFunctions.getYesterdayDate());
        employmentRecord.setHoursWeek(Constants.HOURS_MILES.toString());
        employmentRecord.setDateEndWorking(CommonFunctions.getCurrentDate());
        employmentRecord.setMoneyEarn(Constants.RECORDS_ON_PAGE.toString());
        employmentRecord.setSpecialLicense(false);
        employmentRecord.setReasonForLeaving(reasonLeaving);
        employmentRecord.setLineOne(participant.getAddress().getLineOne());
        employmentRecord.setLineTwo(participant.getAddress().getLineOne());
        employmentRecord.setEmployerCity(participant.getAddress().getCity());
        employmentRecord.setEmployerState(participant.getAddress().getState());
        employmentRecord.setEmployerZipCode(participant.getAddress().getZipCode());
        employmentRecord.setJobDescription(participant.getAddress().getLineOne());
        employmentRecord.setOsocCode(participant.getOsoc());
        employmentRecord.setPeriod("Hour");
        participant.setEmploymentRecord(employmentRecord);

        txbEmployerName.type(employmentRecord.getEmployerName());
        txbJobTitle.type(employmentRecord.getJobTitle());
        txbDateStartWorking.type(employmentRecord.getDateStartWorking());
        txbHoursWeek.type(employmentRecord.getHoursWeek());
        txbDateEndWorking.type(employmentRecord.getDateEndWorking());
        txbMoneyEarn.type(employmentRecord.getMoneyEarn());
        rbSpecialLicense.click();
        cmbReasonLeaving.select(employmentRecord.getReasonForLeaving());
        txbLineOne.type(employmentRecord.getLineOne());
        txbLineTwo.type(employmentRecord.getLineTwo());
        txbEmployerCity.type(employmentRecord.getEmployerCity());
        cmbEmployerAddress.select(participant.getAddress().getState());
        txbEmployerZip.type(employmentRecord.getEmployerZipCode());

        addWaitOsoc(employmentRecord.getOsocCode(), txbOsoc);
        txaJobDescription.type(employmentRecord.getJobDescription());

        btnEditAddedRecord.isPresent();
        btnAddEmploymentRecord.clickAndWait();
    }

    public void fillEmploymentSelfServicePageAndSave(Participant participant) {
        fillEmploymentSelfServicePage(participant);
        clickSave();
    }

    public void clickSave() {
        btnFinish.clickAndWait();
    }
}
