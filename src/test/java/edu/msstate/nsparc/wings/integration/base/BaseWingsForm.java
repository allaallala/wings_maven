package edu.msstate.nsparc.wings.integration.base;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.StaffSearchForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.ContactInformationForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.common.TablePaginationForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionSearchForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreasSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import framework.AccountUtils;
import framework.BaseForm;
import framework.CommonFunctions;
import framework.Logger;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

import java.io.File;

public abstract class BaseWingsForm extends BaseForm {

    private Div dvInternalServerError = new Div(By.xpath("//div[@id='heading-title'][contains(.,'500 - Internal Server Error')]"), "Internal server error");
    private final String error = "error";

    private String PARTICIPANT = "Participant";
    private String WIA_ENROLLMENT = "WIOA Enrollment";
    private String TRADE_ENROLLMENT = "Trade Enrollment";
    private String PETITION = "Petition";

    // Service Enrollments pages
    private RadioButton rbScheduledYes = new RadioButton("id=isScheduled1", "Scheduled Service - Yes");
    private RadioButton rbScheduledNo = new RadioButton("id=isScheduled2", "Scheduled Service - No");
    private RadioButton rbServiceEndedYes = new RadioButton("id=isEnded1", "Service Ended - Yes");
    private RadioButton rbServiceEndedNo = new RadioButton("id=isEnded2", "Service Ended - No");
    private TextBox txbDateResult = new TextBox("id=dateResult", "Date Result");
    private ComboBox cmbResult = new ComboBox("id=result", "Result");

    // Contextual Information Pane

    private Div divContextualPaneInformation = new Div("css=div[id='contextualInfoPane']", "Contextual Information Pane Container");
    private Div divPopupCancel = new Div(By.xpath("//table[@class='popup-menu']//h1[contains(.,'Are you sure')] | //div[@class='modal-content']//p[contains(.,'Are you sure')]"), "Cancel Popup");

    // Periods
    private ComboBox cmbEmploymentStatus = new ComboBox(By.xpath("//select[@name[contains(.,'employmentStatus')]]"), "Employment Status");
    private ComboBox cmbUIStatus = new ComboBox(By.xpath("//table[@id='new-periods-table']//td[2]//select[2]"), "UI Status");
    private ComboBox cmbHighestGradeCompletedStatus = new ComboBox(By.xpath("//table[@id='new-periods-table']//td[3]//select[1] | //select[@id='highestGrade']"), "Highest Grade Completed");
    private ComboBox cmbEducationStatus = new ComboBox(By.xpath("//table[@id='new-periods-table']//td[3]//select[2] "
            + "| //select[@id='educationStatus'] | //select[@id='highestGrade'] "), "Education Status");

    // Common Text Boxes
    private TextBox txbApplicationDate = new TextBox(By.xpath("//input[@id='applicationDate'] | //input[@id='dateApplication'] | "
            + "//input[@id='dateATAARTAAApplication'] | //input[@id='dateTAAApplication']"), "Application Date");
    private TextBox txbNaicsLook = new TextBox(By.xpath("//span[@id='naicsLookup']//input[@class[contains(.,'power-lookup')]]"), "Naics lookup field");
    private String selectedRecord = "//li[@role='menuitem']/a[contains(.,'%1$s')]";

    public BaseWingsForm(By locator, String titleLocator) {
        super(locator, titleLocator);
    }

    protected BaseWingsForm(String url, By locator, String title) {
        super(url, locator, title);
    }

    public enum BaseButton {
        PETITION_LOOK_UP(By.xpath("//input[@value='ActivePetition']/following-sibling::button")),
        INACTIVE_PETITION(By.xpath("//input[@value='Petition']/following-sibling::button")),
        TRAINING_COURSE_LOOK_UP(By.xpath("//span[@id='trainingCourseLookup']//button")),
        STAFF_LOOK_UP(By.xpath("//span[@id='stafflookup']//button | " + "//span[@id='staffLookup']//button")),
        SERVICE_LOOK_UP(By.xpath("//span[@id='serviceLookup']//button | //span[@id='servicelookup']//button")),
        SERVICE_CENTER_LOOK_UP(By.xpath("//span[@id='jcLookup']//button | //span[@id='jclookup']//button")),
        SECONDARY_CASE_MANAGER_LOOK_UP(By.xpath("//span[@id='secondaryCaseManagerlookup']//button | //span[@id='caseManagerLookup']//button")),
        PARTICIPANT_LOOK_UP(By.xpath("//input[@value='Participant']/following-sibling::button")),
        EMPLOYER_LOOK_UP(By.xpath("//span[@id='employerlookup']//button | "
                + "//span[@id='employerLookup']//button | //span[@id='employerlookupspan']//button | "
                + "//div[@id='employerlookUp']//button | //div[@id='employerlookup']//button")),
        JOB_ORDER_LOOK_UP(By.xpath("//span[@id='joLookup']//button[1]")),
        WORK_FORCE_AREA_LOOK_UP(By.xpath("//span[@id='lwiaLookup']//button")),
        CONTINUE(By.xpath("//button[text()='Continue'] | //button[@id='continue'] | //button[@title='Continue']")),
        ADD(By.xpath("//button[text()='Add'] | //button[@id='Add'] | //button[@title='Add']")),
        CREATE(By.xpath("//input[@id='create'] | //button[@id='create'] | //button[text()='Create']")),
        CANCEL(By.xpath("//a[text()='Cancel'] | //input[@value='Cancel'] | //button[@value='Cancel'] |"
                + " //input[text()='Cancel'] | //button[text()='Cancel'] | //button[@id='cancel']")),
        ARE_YOU_SURE_YES(By.xpath("//img[@alt='Yes'] | //a[text()='Yes'] | //a[contains(.,'Yes')]")),
        ARE_YOU_SURE_NO(By.xpath("//img[@alt='No'] | //a[text()='No'] | //button[text()='No']")),
        CREATE_ANOTHER(By.id("createAnother")),
        CLEAR_FORM(By.id("reset")),
        SEARCH(By.id("search")),
        RETURN(By.xpath("//input[@id='return'] | //button[@id='return']")),
        AUDIT(By.id("audit")),
        DONE(By.id("done")),
        EDIT(By.xpath("//button[@id='edit'] | //button[text()='Edit'] | //input[@id='edit']")),
        OPEN_PREVIEW_PAGE(By.xpath("//img[@alt='preview']")),
        CLOSE_PREVIEW_PAGE(By.id("closePreview")),
        EDIT_BASIC_INFORMATION(By.id("editBasicInformation")),
        SAVE_CHANGES(By.xpath("//input[text()='Save Changes'] | //button[text()='Save Changes'] |"
                + " //button[@id='save'] | //button[text()='Finish'] | //button[@title='Save Changes'] "
                + "| //input[@value='Save Changes'] | //button[@id='saveChanges'] | //input[@value='Save Changes'] | //button[@id='update']")),

        OPEN_CONTEXTUAL_PANE(By.xpath("span[class()='contextualInfoPaneExpand']")),
        COLLAPSE_CONTEXTUAL_PANE(By.xpath("//span[@class='contextualInfoPaneContract']"));

        By locator;

        BaseButton(By locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return locator;
        }

        public Button getButton() {
            return new Button(locator, name());
        }
    }

    public enum BaseOtherElement {
        PARTICIPANT(By.xpath("//a[@class='powerLookupRemoveButton']/following-sibling::span")),
        ERROR_MESSAGE(By.xpath("//span[@class='error']")),
        LOADING(By.xpath("//img[@id='loading-indicator'] | //h1[text()='Loading...']"));


        By locator;

        BaseOtherElement(By locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return locator;
        }

        public Div getElement() {
            return new Div(locator, name());
        }
    }

    public enum BaseComboBox {
        RESULT(By.id("result")),
        EMPLOYMENT_STATUS(By.xpath("//select[@name[contains(.,'employmentStatus')]]")),
        UI_STATUS(By.xpath("//select[@name[contains(.,'employmentStatus')]]")),
        HIGHEST_GRADE_COMPLETED_STATUS(By.xpath("//table[@id='new-periods-table']//td[3]//select[1] | //select[@id='highestGrade']")),
        EDUCATION_STATUS(By.xpath("//table[@id='new-periods-table']//td[3]//select[2] "
                + "| //select[@id='educationStatus'] | //select[@id='highestGrade'] "));

        By locator;

        BaseComboBox(By locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return locator;
        }

        public ComboBox getElement() {
            return new ComboBox(locator, name());
        }
    }

    public void select(BaseComboBox baseComboBox, String value) {
        new ComboBox(baseComboBox.getLocator(), baseComboBox.name()).select(value);
    }

    public enum BaseTextBox {
        PARTICIPANT(By.xpath("//span[@id='participantLookup']//input[2]")),
        PETITION(By.xpath("//span[@id='petitionLookup']//input[2]")),
        SERVICE_CENTER(By.xpath("//span[@id='jclookup']//input[2]"));

        By locator;

        BaseTextBox(By locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return locator;
        }

        public String getText() {
            return new TextBox(locator, name()).getText();
        }

    }

    public void clickMenu(WingsTopMenu.WingsStaffMenuItem item) {
        new WingsMenuItem().clickMenu(item.getMenuItem());
    }

    public void menuNotPresent(WingsTopMenu.WingsStaffMenuItem item, Integer startNumber) {
        new WingsMenuItem().menuNotPresent(item.getMenuItem(), startNumber);
    }

    public boolean isPresent(BaseButton baseButton) {
       return new Button(baseButton.getLocator(), baseButton.name()).isPresent();
    }

    public void click(BaseButton baseButton) {
        new Button(baseButton.getLocator(), baseButton.name()).click();
    }

    public String getText(BaseTextBox baseTextBox) {
        return new TextBox(baseTextBox.getLocator(), baseTextBox.name()).getText();
    }

    public String getText(BaseOtherElement baseOtherElement) {
        return new TextBox(baseOtherElement.getLocator(), baseOtherElement.name()).getText();
    }

    public boolean isButtonPresent(BaseButton baseButton) {
        return new Button(baseButton.getLocator(), baseButton.name()).isPresent();
    }

    public void waitForNotVisible(BaseOtherElement baseOtherElement) {
        baseOtherElement.getElement().waitForNotVisible();
    }

    public void assertIsNotPresent(BaseButton baseButton) {
        new Button(baseButton.getLocator(), baseButton.name()).assertIsNotPresent();
    }

    public void assertIsPresent(BaseButton baseButton) {
        new Button(baseButton.getLocator(), baseButton.name()).assertIsPresent();
    }


    protected void waitDivLoading() {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }

    public void ifButton(Boolean present, Button specifiedButton) {
        if (present) {
            specifiedButton.assertIsPresentAndVisible();
            info(specifiedButton.getText() + " is present");
        } else {
            specifiedButton.assertIsNotPresent();
            info("Button is not present");
        }
    }

    public void checkInternalError() {
        if (dvInternalServerError.isPresent()) {
            CustomAssertion.softTrue("500 internal server error has appeared on the page", false);
        }
    }

    protected void selectGradeCompletedStatus(String grade) {
        cmbHighestGradeCompletedStatus.select(grade);
    }

    public void clickAndWait(BaseButton baseButton) {
        new Button(baseButton.getLocator(), baseButton.name()).clickAndWait();
    }

    public void waitClickAndWait(BaseButton baseButton) {
        new Button(baseButton.getLocator(), baseButton.name()).waitClickAndWait();
    }


    public void audit() {
        waitClickAndWait(BaseButton.AUDIT);
        waitClickAndWait(BaseButton.DONE);
    }

    public void checkButtonsPresent(Boolean btnEdit, Boolean audit) {
        divideMessage("Edit");
        ifButton(btnEdit, BaseButton.EDIT.getButton());
        divideMessage("Audit");
        ifButton(audit,  BaseButton.AUDIT.getButton());
        if (audit) {
            audit();
        }
    }

    public void clickButton(Buttons buttonType) {
        switch (buttonType) {
            case Edit:
                BaseButton.EDIT.getButton().clickAndWait();
                break;
            case Create:
                BaseButton.CREATE.getButton().clickAndWait();
                break;
            case Save:
                scrollDown();
                BaseButton.SAVE_CHANGES.getButton().waitClickAndWait();
                break;
            case Done:
                BaseButton.DONE.getButton().clickAndWait();
                break;
            case Cancel:
                BaseButton.CANCEL.getButton().click();
                break;
            case Search:
                BaseButton.SEARCH.getButton().clickAndWait();
                break;
            case Clear:
                BaseButton.CLEAR_FORM.getButton().clickAndWait();
                break;
            case CreateAnother:
                BaseButton.CREATE_ANOTHER.getButton().clickAndWait();
                break;
            case Continue:
                BaseButton.CONTINUE.getButton().clickAndWait();
                break;
            case Add:
                BaseButton.ADD.getButton().clickAndWait();
                break;
            default:
                break;
        }
    }

    public String getSearchedCount() {
        return new SearchResultsForm().getSearchedCount();
    }

    public void checkField(framework.elements.BaseElement element, String expectedValue, boolean strict) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (!expectedValue.isEmpty()) {
            if (strict) {
                if (!expectedValue.equals(element.getText().trim())) {
                    info(String.format("Expected: %1$s, actual: %2$s", expectedValue, element.getText().trim()));
                    File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
                    Logger.getInstance().warn(String.format("Screenshot: %1$s", screenshot.getName()));
                    listWithScreenshots.get().add(screenshot);
                    fatal(String.format("Check for element '%1$s' failed", element.getName()));
                }
            } else {
                if (!element.getText().trim().contains(expectedValue)) {
                    info(String.format("Expected: %1$s, actual: %2$s", expectedValue, element.getText().trim()));
                    File screenshot = CommonFunctions.captureScreenshot("screenshot".concat(CommonFunctions.getTimestamp()));
                    Logger.getInstance().warn(String.format("Screenshot: %1$s", screenshot.getName()));
                    listWithScreenshots.get().add(screenshot);
                    fatal(String.format("Check for element '%1$s' failed", element.getName()));
                }
            }
        }
    }

    public void selectLocation(String locationCounty) {
        new ContactInformationForm().selectLocationCounty(locationCounty);
    }

    public void checkLocationCountyPresent(Boolean present) {
        new ContactInformationForm().checkLocationCountyPresenceState(present);
    }

    public void inputApplicationDate(String applicationDate) {
        txbApplicationDate.type(applicationDate);
    }

    protected void inputLocationCityZip(String locationOne, String city, String zipCode) {
        new ContactInformationForm().inputLocationCityZip(locationOne, locationOne, city, zipCode);
    }

    public void noSearchResults() {
        new SearchResultsForm().assertSearchResultsPresenceState(false);
    }

    public void yesSearchResult() {
        new SearchResultsForm().assertSearchResultsPresenceState(true);
    }

    public void openFirstSearchResult() {
        new SearchResultsForm().openFirstSearchResult();
    }

    public Boolean isFirstSearchResultPresent() {
        return SearchResultsForm.isFirstSearchResultPresent();
    }

    public void selectResult(String optionValue) {
        cmbResult.select(optionValue);
    }

    public void selectEducationStatus(String educationalStatus) {
        cmbEducationStatus.select(educationalStatus);
    }

    public void selectLocationState(String locationState) {
        new ContactInformationForm().selectLocationState(locationState);
    }

    public void areYouSure(Popup type) {
        divPopupCancel.waitForIsElementPresent();
        if (type.getValue().contains(Constants.YES_ANSWER)) {
            BaseButton.ARE_YOU_SURE_YES.getButton().click();
            divPopupCancel.waitForIsElementNotPresent();
        } else {
            BaseButton.ARE_YOU_SURE_NO.getButton().click();
        }
    }

    public void chooseScheduledService(Boolean state) {
        if (state) {
            rbScheduledYes.click();
        } else {
            rbScheduledNo.click();
        }
    }

    public void chooseEndedService(Boolean state) {
        if (state) {
            rbServiceEndedYes.click();
        } else {
            rbServiceEndedNo.click();
        }
    }
    public void inputDateResult(String dateResult) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbDateResult.type(dateResult);
    }

    protected void selectStaff(Staff staff) {
        clickAndWait(BaseButton.STAFF_LOOK_UP);
        new ActivityManagerSearchForm().performSearchAndSelect(staff);
    }

    public void selectStaff() {
        clickAndWait(BaseButton.STAFF_LOOK_UP);
        new ActivityManagerSearchForm().performSearchAndReturn(AccountUtils.getStaffAccount());
    }

    public void selectPetition(Petition petition) {
        clickAndWait(BaseButton.PETITION_LOOK_UP);
        PetitionSearchForm searchForm = new PetitionSearchForm();
        searchForm.performSearchAndSelect(petition);
    }

    public void selectInactivePetition(Petition petition) {
        clickAndWait(BaseButton.INACTIVE_PETITION);
        PetitionSearchForm searchForm = new PetitionSearchForm();
        searchForm.performSearchAndSelect(petition);
    }

    public void selectCourse(String name) {
        clickAndWait(BaseButton.TRAINING_COURSE_LOOK_UP);
        TrainingCourseSearchForm courseSearch = new TrainingCourseSearchForm();
        courseSearch.performSearchAndReturn(name);
        clickAndWait(BaseButton.SEARCH);
    }

    public void selectService(String name) {
        clickAndWait(BaseButton.SERVICE_CENTER_LOOK_UP);
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm();
        serviceSearchForm.inputServiceCenterResult(name);
    }

    public void selectOneStopService(String name) {
        clickAndWait(BaseButton.SERVICE_CENTER_LOOK_UP);
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm(Constants.PARTICIPANT_SS);
        serviceSearchForm.inputServiceCenterResult(name);
    }

    public void selectParticipant(Participant participant) {
        selectParticipant(participant.getFirstName(), participant.getLastName());
    }

    public void selectParticipantByUser(User user, Participant participant) {
        if (user.getRole().equals(Roles.WIOAPROVIDER) || user.getRole().equals(Roles.LWDASTAFF) || user.getRole().equals(Roles.WIOAPLUS)) {
            clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
            ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
            participantSearchForm.performSearchAndOpenByUser(user, participant);

        } else selectParticipant(participant.getFirstName(), participant.getLastName());
    }

    public void selectSecondaryCaseManager(String firstName, String lastName) {
        clickAndWait(BaseButton.SECONDARY_CASE_MANAGER_LOOK_UP);
        StaffSearchForm staffSearch = new StaffSearchForm();
        staffSearch.performSearchAndSelect(firstName, lastName);
    }

    public void selectParticipant(String firstName, String lastName) {
        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(firstName, lastName);
    }

    public void selectEmployer(Employer employer) {
        info("Selecting Employer");
        clickAndWait(BaseButton.EMPLOYER_LOOK_UP);
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearchAndSelect(employer);
    }

    protected void searchSelectResultAndReturn() {
        search();
        selectResultAndReturn();
    }

    protected void selectResultAndReturn() {
        new SearchResultsForm().openSelectedResult();
        returnFromResults();
    }

    protected void searchAndSelectResult() {
        search();
        new SearchResultsForm().openSelectedResult();
    }

    protected void search() {
        clickAndWait(BaseButton.SEARCH);
    }

    protected void returnFromResults() {
        clickAndWait(BaseButton.RETURN);
    }

    protected void selectWorkforceArea(String area) {
        info("Selecting Workforce Area");
        clickAndWait(BaseButton.WORK_FORCE_AREA_LOOK_UP);
        WorkforceAreasSearchForm searchForm = new WorkforceAreasSearchForm();
        searchForm.performSearchAndSelect(area);
    }

    protected void inputNaicsTemp(String naics) {
        Button btnSelectedRecord = new Button(By.xpath(String.format(selectedRecord, naics)), "Selected Naics record");
        txbNaicsLook.type(naics);
        btnSelectedRecord.click();
    }

    public void clickCancelAndConfirm() {
        clickAndWait(BaseButton.CANCEL);
        clickAndWait(BaseButton.ARE_YOU_SURE_YES);
    }

    public void validateWagnerPeyserContextualInformationPane(Participant participant) {
        Button btnOpenContextualPane = BaseButton.OPEN_CONTEXTUAL_PANE.getButton();
        if (btnOpenContextualPane.isPresent()) {
            btnOpenContextualPane.click();
            btnOpenContextualPane.waitForNotVisible();
        }
        checkField(divContextualPaneInformation, PARTICIPANT, Constants.FALSE);
        checkField(divContextualPaneInformation, participant.getFirstName(), Constants.FALSE);
        checkField(divContextualPaneInformation, participant.getLastName(),Constants.FALSE);
        checkField(divContextualPaneInformation, participant.getHiddenSsn(), Constants.FALSE);
        checkField(divContextualPaneInformation, participant.getParticipantBioInfo().getDateOfBirth(), Constants.FALSE);
    }

    public void validateTradeContextualInformationPane(TradeEnrollment tradeEnrollment) {
        validateWagnerPeyserContextualInformationPane(tradeEnrollment.getParticipant());
        checkField(divContextualPaneInformation, TRADE_ENROLLMENT, Constants.FALSE);
        checkField(divContextualPaneInformation, tradeEnrollment.getSeparationJob().getEndDate(), Constants.FALSE);
        checkField(divContextualPaneInformation, tradeEnrollment.getApplicationDate(), Constants.FALSE);
        checkField(divContextualPaneInformation, PETITION, Constants.FALSE);
        checkField(divContextualPaneInformation, tradeEnrollment.getPetition().getNumber(), Constants.FALSE);
        checkField(divContextualPaneInformation, tradeEnrollment.getPetition().getEmployer().getCompanyName(), Constants.FALSE);
        checkField(divContextualPaneInformation, tradeEnrollment.getPetition().getCertificationDate(), Constants.FALSE);
    }

    public void passParticipationRecalculationPage() {
        if (cmbEmploymentStatus.isPresent()) {
            cmbEmploymentStatus.selectFirst();
            cmbUIStatus.selectFirst();
            cmbHighestGradeCompletedStatus.selectFirst();
            cmbEducationStatus.selectFirst();
            clickAndWait(BaseButton.SAVE_CHANGES);
        }
    }

    public void selectAnyAvailableServiceCenter() {
        clickAndWait(BaseButton.SERVICE_CENTER_LOOK_UP);
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm(Constants.PARTICIPANT);
        search();
        serviceSearchForm.selectEmployerService();
        returnFromResults();
    }

    public void validateWiaContextualInformationPane(WIAEnrollment wiaEnrollment) {
        validateWagnerPeyserContextualInformationPane(wiaEnrollment.getParticipant());
        checkField(divContextualPaneInformation, WIA_ENROLLMENT, Constants.FALSE);
        checkField(divContextualPaneInformation, wiaEnrollment.getEligibilityDate(), Constants.FALSE);
        checkField(divContextualPaneInformation, wiaEnrollment.getFundingStream(), Constants.FALSE);
        checkField(divContextualPaneInformation, wiaEnrollment.getWIB(), Constants.FALSE);
        checkField(divContextualPaneInformation, wiaEnrollment.getParticipant().getType().toString(), Constants.FALSE);
    }

    public boolean isEmployerLookupPresent() {
        return BaseButton.EMPLOYER_LOOK_UP.getButton().isPresent();
    }

    public void validateSearchResults(String searchedName) {
        if (TablePaginationForm.isPresent()) {
            TablePaginationForm paginationForm = new TablePaginationForm();
            int pageNumber = paginationForm.getPagesCount();
            for (int i = 1; i <= pageNumber; i++) {
                new SearchResultsForm().validateSearchResultsOnOnePage(searchedName);
                new TablePaginationForm().openNextPage();
            }
        } else {
            new SearchResultsForm().validateSearchResultsOnOnePage(searchedName);
        }
    }
}