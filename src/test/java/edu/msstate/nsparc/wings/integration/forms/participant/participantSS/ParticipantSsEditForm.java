package edu.msstate.nsparc.wings.integration.forms.participant.participantSS;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.Address;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import webdriver.Browser;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Describes Participant S-S edit form.
 * Created by a.vnuchko on 31.10.2016.
 */
public class ParticipantSsEditForm extends ParticipantHomePage {
    //edit eligibility
    private String residentCitizenship = "Permanent Resident";
    private RadioButton rbResident = new RadioButton(By.id("citizenshipStatus1"), "I'm a Permanent Resident");
    private String ssnLinkPath = "//a[contains(.,'%1$s')]";
    private String majorPath = "//strong[@class='tt-highlight'][contains(.,'%1$s')]";
    private TextBox txbSsn = new TextBox(By.id("ssn"), "SSN");
    private TextBox txbDateBirth = new TextBox(By.id("dateOfBirth"), "Date of birth");

    //edit identification
    private String prefix = "Mr.";
    private String suffix = "Sr.";
    private String middleInit = "E";
    private String preferredName = "Unstoppable";
    private String femaleGender = "Female";
    private TextBox txbFirstName = new TextBox(By.id("firstName"), "Participant first name");
    private TextBox txbLastName = new TextBox(By.id("lastName"), "Participant last name");
    private ComboBox cbPrefix = new ComboBox(By.id("prefix"), "Prefix");
    private TextBox txbMiddleName = new TextBox(By.id("middleInitial"), "Middle name");
    private ComboBox cbSuffix = new ComboBox(By.id("suffix"), "Suffix");
    private TextBox txbPreferredName = new TextBox(By.id("preferredName"), "Preferred name");
    private RadioButton rbFemale = new RadioButton(By.id("gender1"), "Female");
    private RadioButton rbHispanic = new RadioButton(By.id("ethnHispanic0"), "Hispanic");
    private RadioButton rbAsian = new RadioButton(By.id("ethnAsian0"), "Asian");
    private RadioButton rbHawaiian = new RadioButton(By.id("ethnPacific0"), "Hawaiian");
    private RadioButton rbAlaskan = new RadioButton(By.id("ethnNative0"), "Alaskan");
    private RadioButton rbBlack = new RadioButton(By.id("ethnAfricanAmer0"), "Black");
    private RadioButton rbWhite = new RadioButton(By.id("ethnWhite0"), "White");

    //edit classification
    private String disabilityType = "";
    private String learningDisability = "Learning Disability";
    private String employmentStatus = "";
    private String employed = "Employed";
    String lnkAttribute = "data-original-title";
    private String questionPath = "//div[@class='question-title'][contains(.,'%1$s')]/following-sibling::div/a";
    private Link lnkVeteranStatus = new Link(By.xpath(String.format(questionPath, "Are you a veteran")),
            "Veteran status helper");
    private Link lnkGuardStatus = new Link(By.xpath(String.format(questionPath, "National Guard")),
            "Guard status helper");
    private RadioButton rbYesDisability = new RadioButton(By.id("isDisabled0"), "Yes disability");
    private CheckBox chbLearning = new CheckBox(By.id("disabilityCategories7"), "Disability Category");
    private RadioButton rbEmploymentYes = new RadioButton(By.id("employmentStatus1"), "Employment - yes");

    //edit accomplishments
    private String highGrade = "9th Grade";
    private String haveLicense = "No";
    private ComboBox cmbHighestGrade = new ComboBox(By.id("highestGrade"), "Highest grade completed");
    private RadioButton rbDriverLicenseNo = new RadioButton(By.id("haveDriversLicense2"), "Driver license - no");

    //edit contact information
    private String phoneEdit = "(123) 555-1234";
    private String emailEdit = "straus@wings.com";
    private String matchingJob = "Any Distance Away";
    private CheckBox chbTextMessage = new CheckBox(By.id("canContactBySms1"), "Text message");
    private RadioButton rbEmployerSentInvite = new RadioButton(By.id("employerCanInvite1"), "Employer can invite");
    private CheckBox chbJobMatchesRightAway = new CheckBox(By.id("sendRealtimeJobMessage1"),
            "Job matching right away");
    private TextBox txbResidenceLineOne = new TextBox(By.id("contactInformation.addressByResidenceAddr.lineOne"),
            "Line one of the residence address");
    private TextBox txbResidenceLineTwo = new TextBox(By.id("contactInformation.addressByResidenceAddr.lineTwo"),
            "Line two of the residence address");
    private TextBox txbResidenceCity = new TextBox(By.id("contactInformation.addressByResidenceAddr.city"),
            "City of the residence address");
    private TextBox txbResidenceZip = new TextBox(By.id("contactInformation.addressByResidenceAddr.zipcode"),
            "Zip code of the residence address");
    private ComboBox cmbResidenceState = new ComboBox(By.id("contactInformation.addressByResidenceAddr.state"),
            "State of the residence address");
    private ComboBox cmbResidenceCounty = new ComboBox(By.id("contactInformation.addressByResidenceAddr.county"),
            "County of the residence address");
    private ComboBox cmbResidenceCountry = new ComboBox(By.id("contactInformation.addressByResidenceAddr.country"),
            "Country of the residence address");
    private TextBox txbPrimaryPhone = new TextBox(By.id("contactInformation.primaryPhone"), "Primary phone");
    private TextBox txbEmail = new TextBox(By.id("contactInformation.emailAddress"), "Email");
    private TextBox txbConfirmEmail = new TextBox(By.id("contactInformation.confirmEmailAddress"), "Confirm email");
    private Button btnCopyAddress = new Button(By.id("copyAddressAcross"), "Copy address");
    private ComboBox cmbMatchingJobs = new ComboBox(By.id("participantSupplemental.jobNotificationDistance"),
            "Matching jobs");
    private RadioButton rbJobMatchesText = new RadioButton(By.id("preferredNotificationMethod0"),
            "Notify about job matchs with text");
    private TextBox txbMailingLineOne = new TextBox(By.id("contactInformation.addressByMailingAddr.lineOne"),
            "Line one of the mailing address");
    private TextBox txbMailingLineTwo = new TextBox(By.id("contactInformation.addressByMailingAddr.lineTwo"),
            "Line two of the mailing address");
    private TextBox txbMailingCity = new TextBox(By.id("contactInformation.addressByMailingAddr.city"),
            "City of the mailing address");
    private TextBox txbMailingZip = new TextBox(By.id("contactInformation.addressByMailingAddr.zipcode"),
            "Zip code of the mailing address");
    private ComboBox cmbMailingState = new ComboBox(By.id("contactInformation.addressByMailingAddr.state"),
            "State of the mailing address");
    private ComboBox cmbMailingCounty = new ComboBox(By.id("contactInformation.addressByMailingAddr.county"),
            "County of the mailing address");
    private ComboBox cmbMailingCountry = new ComboBox(By.id("contactInformation.addressByMailingAddr.country"),
            "Country of the mailing address");
    private TextBox txbAlternatePhone = new TextBox(By.id("contactInformation.secondaryPhone"), "Alternate phone");
    private TextBox txbFaxNumber = new TextBox(By.id("contactInformation.faxNumber"), "Fax number");
    private TextBox txbPreferredTextNumber = new TextBox(By.id("preferredSmsNumber"), "Preferred sms number");

    //edit employment preferences
    private TextBox txbHoursPerWeek = new TextBox(By.id("jobInterest.hoursPerWeek"), "Hours per week");
    private ComboBox cmbDesiredSalary = new ComboBox(By.id("jobInterest.desiredSalary"), "Desired salary");
    private TextBox txbDistanceRelocate = new TextBox(By.id("jobInterest.maxDistRelocation"), "Distance to relocate");
    private TextBox txbDistanceCommute = new TextBox(By.id("jobInterest.maxDistCommute"), "Distance to commute");

    //edit military information
    private String branchPath = "//label[contains(.,'%1$s')]/input";
    private ComboBox cmbDischargeType = new ComboBox(By.id("tmpMilitaryRecord.militaryDischargeType"), "Discharge Type");
    private TextBox txbServiceBegin = new TextBox(By.id("tmpMilitaryRecord.dateMilitaryBegin"), "Military Service Begin Date");
    private TextBox txbServiceEnd = new TextBox(By.id("tmpMilitaryRecord.dateMilitaryEnd"), "Military Service End Date");

    //edit employer information
    private String toolsXpath = "//a/label[contains(.,'%1$s')]";
    private String employer = "aiCompany - 3/28/2017";
    private RadioButton rbCurrentlyWorkingYes = new RadioButton(By.id("tmpPreviousJob.currentEmployer1"),
            "Currently working - YES");
    private RadioButton rbCurrentlyWorkingNo = new RadioButton(By.id("tmpPreviousJob.currentEmployer2"),
            "Currently working - NO");
    private TextBox txbEmployer = new TextBox(By.id("tmpPreviousJob.employer"), "Employer");
    private TextBox txbJobTitle = new TextBox(By.id("tmpPreviousJob.jobTitle"), "Job Title");
    private TextBox txbStartWorkingDate = new TextBox(By.id("tmpPreviousJob.dateStart"), "Date start working");
    private TextBox txbEndWorkingDate = new TextBox(By.id("tmpPreviousJob.dateEnd"), "Date end working");
    private TextBox txbWorkingCity = new TextBox(By.id("tmpPreviousJob.address.city"), "Working City");
    private ComboBox cmbWorkingState = new ComboBox(By.id("tmpPreviousJob.address.state"), "Working State");
    private TextBox txbJobPerformed = new TextBox(By.id("tmpPreviousJob.osocTitle"), "Jobs Performed");
    private RadioButton rbRreYes = new RadioButton(By.id("tmpPreviousJob.receivedRapidResponse1"),
            "Did you receive rapid response event - YES");
    private RadioButton rbRreNo = new RadioButton(By.id("tmpPreviousJob.receivedRapidResponse2"),
            "Did you receive rapid response event - NO");
    private TextBox txbRreEmployer = new TextBox(By.id("tmpPreviousJob.rapidResponseName"),
            "Rapid Response Event employer");
    private ComboBox cmbReasonLeaving = new ComboBox(By.id("tmpPreviousJob.reasonLeave"), "Reason leaving");
    private ComboBox cmbToolsList = new ComboBox(By.xpath("//div[@class='btn-group']/button"), "Tool used list");
    private TextBox tbJobDuties = new TextBox(By.id("tmpPreviousJob.duties"), "Job Duties");

    public ParticipantSsEditForm(String headTitle) {
        super(By.xpath(String.format("//div[@id='heading-title'][contains(.,'%1$s')]", headTitle)),
                "Participant SS Edit Form");
    }

    public void editEligibility(Participant participant, String newSsn, String newBirthdate) {
        Link ssnLink = new Link(By.xpath(String.format(ssnLinkPath, participant.getSsn().substring(5, 9))),
                "SSN link to edit");
        ssnLink.click();
        txbSsn.type(newSsn);
        rbResident.click();
        if (rbResident.isSelected()) {
            participant.setCitizenship(residentCitizenship);
        }
        txbDateBirth.type(newBirthdate);
        participant.setSsn(newSsn);
        participant.getParticipantBioInfo().setDateOfBirth(newBirthdate);
    }

    public void editAllIdentification(Participant participant, String newFirstName, String newLastName) {
        divideMessage("Change all parameters available");
        cbPrefix.select(prefix);
        inputFirstLastName(newFirstName, newLastName);
        txbMiddleName.type(middleInit);
        cbSuffix.select(suffix);
        txbPreferredName.type(preferredName);
        rbFemale.click();
        if (rbFemale.isSelected()) {
            participant.getParticipantBioInfo().setGender(femaleGender);
        }
        rbHispanic.click();
        rbAsian.click();
        rbHawaiian.click();
        rbAlaskan.click();
        rbBlack.click();
        rbWhite.click();
        participant.getParticipantBioInfo().setPrefix(prefix);
        participant.getParticipantBioInfo().setSuffix(suffix);
        participant.getParticipantBioInfo().setPreferredName(preferredName);
        participant.setFirstName(newFirstName);
        participant.setLastName(newLastName);
        participant.getParticipantNations().setAllNations();
    }

    public String[] editPossibleClassification() {
        rbYesDisability.clickAndWait();
        chbLearning.click();
        if (chbLearning.isSelected()) {
            disabilityType = learningDisability;
        }
        clickEmployment();
        if (rbEmploymentYes.isSelected()) {
            employmentStatus = employed;
        }

        return new String[]{disabilityType, employmentStatus};
    }

    public String[] editAccomplishments() {
        cmbHighestGrade.select(highGrade);
        rbDriverLicenseNo.click();
        return new String[]{highGrade, haveLicense};
    }

    public void clickCopyButton() {
        btnCopyAddress.clickAndWait();
    }

    public void editContactInformation(Participant partip) {
        partip.setNewAddress(new Address());
        partip.setPrimaryPhone(phoneEdit);
        partip.setEmail(emailEdit);
        chbTextMessage.click();
        rbEmployerSentInvite.click();
        chbJobMatchesRightAway.click();
        editResidenceAddress(partip);
        txbPrimaryPhone.type(partip.getPrimaryPhone());
        txbEmail.type(partip.getEmail());
        txbConfirmEmail.type(partip.getEmail());
        cmbMatchingJobs.select(matchingJob);
        rbJobMatchesText.click();
        editMailingAddress(partip);
        txbAlternatePhone.type(partip.getPrimaryPhone());
        txbFaxNumber.type(partip.getPrimaryPhone());
        txbPreferredTextNumber.type(partip.getPrimaryPhone());
    }

    public void editResidenceAddress(Participant partip) {
        txbResidenceLineOne.type(partip.getAddress().getLineOne());
        txbResidenceLineTwo.type(partip.getAddress().getLineOne());
        txbResidenceCity.type(partip.getAddress().getCity());
        txbResidenceZip.type(partip.getAddress().getZipCode());
        cmbResidenceState.select(partip.getAddress().getState());
        cmbResidenceCounty.select(partip.getAddress().getCounty());
        cmbResidenceCountry.select(partip.getAddress().getCountry());
    }

    public void editMailingAddress(Participant partip) {
        txbMailingLineOne.type(partip.getAddress().getLineOne());
        txbMailingLineTwo.type(partip.getAddress().getLineOne());
        txbMailingCity.type(partip.getAddress().getCity());
        txbMailingZip.type(partip.getAddress().getZipCode());
        cmbMailingState.select(partip.getAddress().getState());
        cmbMailingCounty.select(partip.getAddress().getCounty());
        cmbMailingCountry.select(partip.getAddress().getCountry());
    }

    public void editEmploymentPreferences(String hoursWeek, String desiredSalary,
                                          String distanceRelocate, String distanceCommute) {
        txbHoursPerWeek.type(hoursWeek);
        cmbDesiredSalary.select(desiredSalary);
        txbDistanceRelocate.type(distanceRelocate);
        txbDistanceCommute.type(distanceCommute);
    }

    public void addMilitaryRecord(String militaryType, String dischargeType,
                                  String serviceStart, String serviceEnd) {
        clickMilitaryBranch(militaryType);
        selectDischargeType(dischargeType);
        txbServiceBegin.type(serviceStart);
        txbServiceEnd.type(serviceEnd);
    }

    public void selectDischargeType(String dischargeType) {
        cmbDischargeType.select(dischargeType);
    }

    public void clickMilitaryBranch(String militaryType) {
        new RadioButton(By.xpath(String.format(branchPath, militaryType)), "military branch").click();
    }

    public void inputServiceEnd(String serviceEnd) {
        txbServiceEnd.type(serviceEnd);
    }

    public void inputFirstLastName(String newFirstName, String newLastName) {
        txbFirstName.type(newFirstName);
        txbLastName.type(newLastName);
    }

    public void addEmploymentRecord(Boolean currentlyWorking, Participant participant, String startDate,
                                    String endDate, String reasonLeaving, Boolean receiveRRE) {
        if (receiveRRE) {
            info("Fill Rapid Response information");
            rbRreYes.clickAndWait();
            txbRreEmployer.type(employer);
        } else {
            rbRreNo.clickAndWait();
        }
        if (currentlyWorking) {
            rbCurrentlyWorkingYes.click();
        } else {
            rbCurrentlyWorkingNo.click();
            txbEndWorkingDate.type(endDate);
            cmbReasonLeaving.select(reasonLeaving);
        }
        txbEmployer.type(participant.getFirstName());
        txbJobTitle.type(participant.getJobTitle());
        txbStartWorkingDate.type(startDate);
        txbWorkingCity.type(participant.getAddress().getCity());
        cmbWorkingState.select(participant.getAddress().getState());
    }

    public void inputJobTools(Participant participant, Boolean tools, String toolsUsed) {
        Div dvStrong = new Div(By.xpath(String.format(majorPath, participant.getOsoc())), "Osoc");
        tbJobDuties.click();
        while (!dvStrong.isPresent()) {
            txbJobPerformed.type(participant.getOsoc());

        }
        Browser.getDriver().getMouse().mouseMove(dvStrong.getElement().getCoordinates());
        JavascriptExecutor jse = Browser.getDriver();
        jse.executeScript("arguments[0].click();", dvStrong.getElement());
        if (tools) {
            Link lnkToolsElement = new Link(By.xpath(String.format(toolsXpath, toolsUsed)), "Tools in the list");
            cmbToolsList.click();
            lnkToolsElement.click();
        }
    }

    public void editDateBirth(String newBirthdate) {
        txbDateBirth.type(newBirthdate);
    }

    public void clickEmployment() {
        rbEmploymentYes.click();
    }

    public void checkVeteranGuardMessageText(String expectedVeteranText, String expectedGuardText) {
        lnkVeteranStatus.click();
        CustomAssertion.softTrue("Incorrect veteran warning",
                lnkVeteranStatus.getElement().getAttribute(lnkAttribute).contains(expectedVeteranText));
        lnkGuardStatus.click();
        CustomAssertion.softTrue("Incorrect veteran warning",
                lnkGuardStatus.getElement().getAttribute(lnkAttribute).contains(expectedGuardText));
    }
}
