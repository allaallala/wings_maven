package edu.msstate.nsparc.wings.integration.forms.participant;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Participant Profiles -> Create
 */
public class ParticipantCreationForm extends ParticipantBaseForm {

    // Page #1
    protected final TextBox txbSSN = new TextBox("id=ssn", "SSN");
    protected TextBox txbDateOfBirth = new TextBox("id=dateOfBirth", "Date of Birth");
    protected RadioButton rbUnitedStatesCitizenYes = new RadioButton(By.xpath("//input[@name='citizenshipStatus' and @value='UNITED_STATES_CITIZEN']"),
            "United States Citizen - Yes");

    protected Button btnSaveForLater = new Button("id=saveForLater", "Save and Finish Later");

    protected String warningText = "The previous screen has indicated that the participant is a veteran. Click Previous below to change.";
    protected Div dvWarningMessage = new Div(By.xpath("//td[@style[contains(.,'red')]]/strong"), "Warning message");

    protected String veteranStatus = "Eligible Veteran";
    protected String ineligibleVeteran = "Not an Eligible Veteran";
    protected String answerNo = "No";
    protected String driverLicense = "B";
    protected String ssi = "None";
    protected String certificate = "MySQL";

    // Page #2
    protected ComboBox cmbUSCitizen = new ComboBox("css=select[id='citizenshipStatus']", "US Citizen Status");
    protected RadioButton rbGenderMale = new RadioButton("//input[@name='gender' and @value='MALE']", "Gender - Male");
    protected RadioButton rbHispanicDisclose = new RadioButton("//input[@name='ethnHispanic' and @value='DO_NOT_WISH_TO_DISCLOSE']",
            "Hispanic/Latino");
    protected RadioButton rbNativeDisclose = new RadioButton("//input[@name='ethnNative' and @value='DO_NOT_WISH_TO_DISCLOSE']",
            "Native American/Alaskan Native");
    protected RadioButton rbAsianDisclose = new RadioButton("//input[@name='ethnAsian' and @value='DO_NOT_WISH_TO_DISCLOSE']",
            "Asian");
    protected RadioButton rbAfricanDisclose = new RadioButton("//input[@name='ethnAfricanAmer' and @value='DO_NOT_WISH_TO_DISCLOSE']",
            "African American");
    protected RadioButton rbHawaiDisclose = new RadioButton("//input[@name='ethnPacific' and @value='DO_NOT_WISH_TO_DISCLOSE']",
            "Native Hawaiian/Pacific Islander");
    protected RadioButton rbWhiteDisclose = new RadioButton("//input[@name='ethnWhite' and @value='DO_NOT_WISH_TO_DISCLOSE']",
            "White/Caucasian");
    protected RadioButton rbVeteranYes = new RadioButton("css=input[id='veteranDetail.isVeteran1']", "Is Veteran");
    protected RadioButton rbVeteranNo = new RadioButton("css=input[id='veteranDetail.isVeteran2']", "Not a Veteran");
    protected RadioButton rbNationalGuardYes = new RadioButton(By.xpath("//input[@id='veteranDetail.isNationalGuard1']"),
            "Member of the Mississippi National Guard");
    protected RadioButton rbNationalGuardNo = new RadioButton(By.xpath("//input[@id='veteranDetail.isNationalGuard2']"),
            "Not a member of the Mississippi National Guard");
    protected RadioButton rbDisabledNo = new RadioButton("//input[@name='isDisabled' and @value='NO']",
            "Not Disabled");
    protected RadioButton rbDoYouHaveJobNo = new RadioButton("//input[@name='employmentStatus' and @value='NOT_EMPLOYED']",
            "Do you have a job - No");
    protected RadioButton rbDriversLicenseYes = new RadioButton(By.id("haveDriversLicense1"),
            "Drivers License - Yes");
    protected RadioButton rbDriversLicenseNo = new RadioButton(By.id("haveDriversLicense2"),
            "Drivers License - No");
    protected static final String NOT_EMPLOYEED = "Not Employed";

    // Page #3
    protected String distance = "Any Distance Away";
    protected CheckBox chkEmail = new CheckBox(By.id("canContactByEmail1"), "Contact by EMail");
    protected CheckBox chkPhone = new CheckBox(By.id("canContactByPhone1"), "Contact by Phone");
    protected CheckBox chkAgreeEmail = new CheckBox("css=input[id='contactInformation.emailAcknowledgement1']", "Agree with Email notifications");
    protected RadioButton rbJobVites = new RadioButton(By.id("employerCanInvite1"), "Job Vites allowed - Yes");
    protected RadioButton rbSameAddressYes = new RadioButton("css=input[id='contactInformation.addressSame1']", "Mailing same to Location");
    protected Button btnCopyAddress = new Button(By.id("copyAddressAcross"), "Copy Address");
    protected TextBox txbPhone = new TextBox(By.id("contactInformation.primaryPhone"), "Primary phone");
    protected TextBox txbEmailConfirm = new TextBox("css=input[id='contactInformation.confirmEmailAddress']", "Confirm EMail");
    protected H1 h1EmailVerification = new H1("css=h1:contains('Email Verification')", "Email Verification");
    protected TextBox tbCopiedAddress = new TextBox(By.id("contactInformation.addressByMailingAddr.lineOne"), "Copied address line one");
    protected TextBox tbCopiedCity = new TextBox(By.id("contactInformation.addressByMailingAddr.city"), "Copied address city");
    protected ComboBox cbCopiedState = new ComboBox(By.id("contactInformation.addressByMailingAddr.state"), "Copied address state");
    protected TextBox tbCopiedZip = new TextBox(By.id("contactInformation.addressByMailingAddr.zipcode"), "Copied address zip code");
    protected ComboBox cbLocationCounty = new ComboBox(By.id("contactInformation.addressByMailingAddr.county"), "Copied address location county");
    protected Button btnApplyState = new Button(By.xpath("//input[@field='contactInformation.addressByResidenceAddr.state']"), "Apply state");

    //Employment Preferences
    protected TextBox txbHoursPerWeek = new TextBox(By.id("jobInterest.hoursPerWeek"), "Hours per week");
    protected TextBox txbWillingRelocate = new TextBox(By.id("jobInterest.maxDistRelocation"), "Max distance relocation");
    protected TextBox txbWillingCommute = new TextBox(By.id("jobInterest.maxDistCommute"), "Max distance commute");

    //Warn text messages and locators
    protected static final String SELECTION_REQUIRED = "is required.";
    protected static final String HIGHEST_GRADE = "Highest Grade Completed is required.";
    protected static final String DATE_BIRTH = "Date Of Birth is required.";
    protected static final String FIRST_NAME = "First Name is required.";
    protected static final String LAST_NAME = "Last Name is required.";
    protected static final String LINE_ONE = "Line One is required.";
    protected static final String CITY = "City is required.";
    protected static final String STATE = "State is required.";
    protected static final String ZIP = "ZIP Code is required.";
    private String rbWarnPath = "//div[@class='question-title'][contains(.,'%1$s')]/following-sibling::div//i";
    private String inputWarnPath = "//i[contains(.,'%1$s')]";
    protected Button btnEditAddedRecord = new Button(By.xpath("//a[@href='#editModal1']"), "Edit added record");
    protected Div dvTextCitizenWarn = new Div(By.xpath("//span[@class='help-block']//i"), "Citizen error");
    protected Div dvDateBirthWarn = new Div(By.xpath(String.format(inputWarnPath, DATE_BIRTH)), "Date birth error");
    protected Div dvFirstNameWarn = new Div(By.xpath(String.format(inputWarnPath, FIRST_NAME)), "First Name error");
    protected Div dvLastNameWarn = new Div(By.xpath(String.format(inputWarnPath, LAST_NAME)), "Last Name error");
    protected Div dvGenderWarn = new Div(By.xpath(String.format(rbWarnPath, "gender")), "Gender error");
    protected Div dvLatinoWarn = new Div(By.xpath(String.format(rbWarnPath, "Hispanic/Latino")), "Latino error");
    protected Div dvAsianWarn = new Div(By.xpath(String.format(rbWarnPath, "Asian")), "Asian error");
    protected Div dvHawaiianWarn = new Div(By.xpath(String.format(rbWarnPath, "Hawaiian")), "Hawaiian error");
    protected Div dvAlaskanWarn = new Div(By.xpath(String.format(rbWarnPath, "Alaskan")), "Alaskan error");
    protected Div dvBlackWarn = new Div(By.xpath(String.format(rbWarnPath, "Black")), "Black error");
    protected Div dvCaucasianWarn = new Div(By.xpath(String.format(rbWarnPath, "Caucasian")), "Veteran error");
    protected Div dvVeteranWarn = new Div(By.xpath(String.format(rbWarnPath, "veteran")), "White error");
    protected Div dvHighestGrade = new Div(By.xpath("//span[@class='help-block']//i"), "Highest Grade error");
    protected Div dvNationalWarn = new Div(By.xpath(String.format(rbWarnPath, "National")), "National Guard error");
    protected Div dvDisabilityWarn = new Div(By.xpath(String.format(rbWarnPath, "disability")), "Disability error");
    protected Div dvJobWarn = new Div(By.xpath(String.format(rbWarnPath, "job")), "Job error");
    protected Div dvResLineOneWarn = new Div(By.xpath(String.format(inputWarnPath, "Line One is required.")), "Residence line one error");
    protected Div dvResCityWarn = new Div(By.xpath(String.format(inputWarnPath, "City is required.")), "Residence city error");
    protected Div dvResStateWarn = new Div(By.xpath(String.format(inputWarnPath, "State is required.")), "Residence state error");
    protected Div dvResZipWarn = new Div(By.xpath(String.format(inputWarnPath, "ZIP Code is required.")), "Residence zip error");
    protected Div dvMailLineOneWarn = new Div(By.xpath(String.format(inputWarnPath, "Line One is required.")), "Mailing line one error");
    protected Div dvMailCityWarn = new Div(By.xpath(String.format(inputWarnPath, "City is required.")), "Mailing city error");
    protected Div dvMailStateWarn = new Div(By.xpath(String.format(inputWarnPath, "State is required.")), "Mailing state error");
    protected Div dvMailZipWarn = new Div(By.xpath(String.format(inputWarnPath, "ZIP Code is required.")), "Mailing zip error");
    protected Button btnApply = new Button(By.xpath("//input[@title='Apply']"), "Apply");

    //Military page self service
    protected RadioButton rbMarineCorps = new RadioButton(By.id("tmpMilitaryRecord.militaryBranch3"), "Marine Corps");
    protected TextBox tbMilitaryDateBegin = new TextBox(By.id("tmpMilitaryRecord.dateMilitaryBegin"), "Military date begin");
    protected Button btnAddMilitary = new Button(By.id("addMilitary"), "Add military record");

    //Education page self service
    protected static String participantGPA = "3.25";
    protected ComboBox cmbState = new ComboBox(By.id("tmpAcademicRecord.schoolAddress.state"), "School address state");
    protected TextBox txbSchoolName = new TextBox(By.id("tmpAcademicRecord.schoolName"), "School issuer's name");
    protected TextBox txbSchoolCity = new TextBox(By.id("tmpAcademicRecord.schoolAddress.city"), "School's city");
    protected TextBox txbZip = new TextBox(By.id("tmpAcademicRecord.schoolAddress.zipcode"), "Zip code");
    protected ComboBox cmbHighestGrade = new ComboBox(By.id("highestGrade"), "Highest Grade");
    protected TextBox txbDateSchoolStart = new TextBox(By.id("tmpAcademicRecord.dateStart"), "Date School start");
    protected TextBox txbDateGraduated = new TextBox(By.id("tmpAcademicRecord.dateEnd"), "Date graduated school");
    protected ComboBox cmbDegree = new ComboBox(By.id("tmpAcademicRecord.degreeType"), "Certificate / degree type");
    protected TextBox txbGpa = new TextBox(By.id("tmpAcademicRecord.gpa"), "Valid GPA");
    protected Button btnAddAcademicRecord = new Button(By.id("addEducation"), "Add academic record");

    //Employment page self service
    protected TextBox txbEmployerName = new TextBox(By.id("tmpPreviousJob.employer"), "EmployerName");
    protected TextBox txbJobTitle = new TextBox(By.id("tmpPreviousJob.jobTitle"), "Employer job title");
    protected TextBox txbDateStartWorking = new TextBox(By.id("tmpPreviousJob.dateStart"), "Date start working");
    protected TextBox txbHoursWeek = new TextBox(By.id("tmpPreviousJob.hoursPerWeek"), "Hours per week");
    protected TextBox txbDateEndWorking = new TextBox(By.id("tmpPreviousJob.dateEnd"), "Date end working");
    protected TextBox txbMoneyEarn = new TextBox(By.id("tmpPreviousJob.wage"), "Money you earn");
    protected RadioButton rbSpecialLicense = new RadioButton(By.id("tmpPreviousJob.hadLicense2"), "Special License - No");
    protected ComboBox cmbReasonLeaving = new ComboBox(By.id("tmpPreviousJob.reasonLeave"), "Reason for leaving");
    protected String reasonLeaving = "Career Change";
    protected TextBox txbLineOne = new TextBox(By.id("tmpPreviousJob.address.lineOne"), "Address Line One");
    protected TextBox txbLineTwo = new TextBox(By.id("tmpPreviousJob.address.lineTwo"), "Address Line Two");
    protected TextBox txbEmployerCity = new TextBox(By.id("tmpPreviousJob.address.city"), "Employer address city");
    protected ComboBox cmbEmployerAddress = new ComboBox(By.id("tmpPreviousJob.address.state"), "Employer address state");
    protected TextBox txbEmployerZip = new TextBox(By.id("tmpPreviousJob.address.zipcode"), "Employer Zip Code");
    protected TextBox txbOsoc = new TextBox(By.id("tmpPreviousJob.osocTitle"), "Osoc title");
    protected TextArea txaJobDescription = new TextArea(By.id("tmpPreviousJob.duties"), "Job Description");
    protected Button btnAddEmploymentRecord = new Button(By.id("addEmployment"), "Add employment record");

    // Page #4
    protected RadioButton rbHomelessRunawayNo = new RadioButton("css=input[id='participantSupplemental.isHomelessRunaway2']",
            "Homeless Runaway");
    protected RadioButton rbOffenderNo = new RadioButton("css=input[id='isOffender2']",
            "Offender");
    protected RadioButton rbSelectiveService = new RadioButton("css=input#isSelectiveService1",
            "Selective Service:");
    protected ComboBox cmbSSI = new ComboBox("id=participantProgram.recvSsi", "Received SSI");
    protected RadioButton rbFarmWorkerNo = new RadioButton("css=input[id='participantProgram.recvNatFarmJob2']",
            "Received National Farm Worker Job Program Services");
    protected RadioButton rbJobCorpsNo = new RadioButton("css=input[id='participantProgram.recvJobCorps2']",
            "Received Job Corps services");
    protected RadioButton rbIndianNo = new RadioButton("css=input[id='participantProgram.recvNative2']",
            "Received Indian/Native American Program services");
    protected RadioButton rbYouthNo = new RadioButton("css=input[id='participantProgram.recvYouthBuildHud2']",
            "Received Youth Build services");
    protected RadioButton rbTitleVNo = new RadioButton("css=input[id='participantProgram.recvTitleV2']",
            "Received Title V Older Worker Program services - No");

    //Popup
    private Div divPopupCancel = new Div(By.xpath("//div[contains(.,'Are you sure')]"), "Cancel popup");

    /**
     * Default constructor
     */
    public ParticipantCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Participant Creation')]"), "Participant Creation");
    }

    public ParticipantCreationForm(By locator, String formTitle) {
        super(locator, formTitle);
    }


    public ParticipantCreationForm(String participantSs) {
        super(By.xpath("//div[@id='heading-title'][.='Eligibility']"), String.format("The initial form for %1$s", participantSs));
    }

    public void saveForLater() {
        btnSaveForLater.clickAndWait();
    }

    public void clickContinue() {
        clickButton(Buttons.Continue);
    }

    public void validateErrorMessage() {
        CustomAssertion.softTrue("Incorrect warning text", dvWarningMessage.getText().contains(warningText));
    }

    protected void checkWarnText(String expectedWarnText, BaseElement el) {
        CustomAssertion.softAssertContains(el.getText(), expectedWarnText, "Incorrect error text");
    }

    public void checkSsn() {
        txbSSN.assertIsPresentAndVisible();
    }
}
