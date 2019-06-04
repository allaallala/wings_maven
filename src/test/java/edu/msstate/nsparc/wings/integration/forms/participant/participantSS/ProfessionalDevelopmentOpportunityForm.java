package edu.msstate.nsparc.wings.integration.forms.participant.participantSS;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * Describies professional development opportunity form.
 * Created by a.vnuchko on 05.01.2017.
 */
public class ProfessionalDevelopmentOpportunityForm extends ParticipantHomePage {
    private static final String DEFAULT_ADDRESS = "Starkville, Mississippi 22000";

    private TextBox tbPdoTitle = new TextBox(By.name("keyword"), "PDO title");
    private TextBox tbPdoId = new TextBox(By.name("searchId"), "PDO search ID");
    private String pdoTablePath = "//*[@id='pdo-matches-table']//tbody//tr[%1$d]//td[%2$d]";
    private Link lnkTitle = new Link(By.xpath("//th[contains(.,'Title')]"), "Title");
    private Link lnkPosted = new Link(By.xpath("//th[contains(.,'Posted')]"), "Posted");
    private TableCell tbcTitle = new TableCell(By.xpath("//*[@id='pdo-matches-table']//tbody//tr//td//a"), "PDO title on the page");
    private Link lnkFirst = new Link(By.xpath("//span[contains(.,'«')]"), "First Page");
    private Link lnkSecond = new Link(By.xpath("//a[.='2']"), "Second Page");
    private Link lnkNext = new Link(By.xpath("//a[.='Next']"), "Next Page");
    private Link lnkPrevious = new Link(By.xpath("//a[.='Prev']"), "Previous Page");
    private Link lnkLast = new Link(By.xpath("//span[contains(.,'»')]"), "Last Page");
    private String regExp = "\\(\\d{1,}";
    private String questiongFaqPath = "//a[@href='%1$s']";
    private String answerFaqPath = "//div[@id='%1$s']/div";
    private Div dvFaqFirstQuestion = new Div(By.xpath(String.format(questiongFaqPath, "#collapseOne")), "FAQ first question");
    private Div dvFaqFirstAnswer = new Div(By.xpath(String.format(answerFaqPath, "collapseOne")), "FAQ first answer");
    private Div dvFaqSecondQuestion = new Div(By.xpath(String.format(questiongFaqPath, "#collapseTwo")), "FAQ second question");
    private Div dvFaqSecondAnswer = new Div(By.xpath(String.format(answerFaqPath, "collapseTwo")), "FAQ second answer");
    private Div dvFaqThirdQuestion = new Div(By.xpath(String.format(questiongFaqPath, "#collapseThree")), "FAQ third question");
    private Div dvFaqThirdAnswer = new Div(By.xpath(String.format(answerFaqPath, "collapseThree")), "FAQ third answer");
    private static String faqFirstQuestion = "What are Professional Development Opportunities (PDOs)?";
    private static String faqFirstAnswer = "PDOs are not job openings. "
            + "A PDO allows you to:\na. learn about a training or career development opportunity\nb. apply to be considered for the opportunity";
    private static String faqSecondQuestion = "What Happens if I Apply to a PDO?";
    private static String faqSecondAnswer = "If you apply for a PDO, you become part of a pool of people who will be "
            + "contacted by a training provider and considered for the program.";
    private static String faqThirdQuestion = "Who Pays for the Training?";
    private static String faqThirdAnswer = "Costs, funding, and scholarship opportunities vary from PDO to PDO. See the"
            + " individual PDO listing for details of the training.";
    private String xpathHeadTitle = "//h3[contains(.,'%1$s')]";
    private Button btnApplyNow = new Button(By.id("apply"), "Apply Now");
    private TextBox txbInitials = new TextBox(By.id("initials"), "Participant initials");
    private Button btnConfirm = new Button(By.id("submitApplication"), "Confirm");
    private Div dvHeader = new Div(By.id("page-heading"), "Submitted HEADER");
    private static final String HEADER = "Your Application has been Submitted";
    private Button btnReturnDetails = new Button(By.id("return"), "Return to Details button");
    private Button btnReturnSearch = new Button(By.id("previous"), "Return to Search Details button");
    private Div dvApplied = new Div(By.xpath("//h2[contains(.,'Applied')]"), "Applied");
    private Div dvAppliedText = new Div(By.xpath("//div[@class='panel-body'][contains(.,'Your application')]"), "Your application is pending");
    private static final String APPLIED_TEXT = "Your application is\nPending";
    private Div dvSorry = new Div(By.xpath("//h2[contains(.,'So Sorry')]"), "So Sorry");
    private Div dvSorryText = new Div(By.xpath("//div[@id='gap-aggregate']//p[contains(.,'You cannot apply for this opportunity, because the deadline has passed.')]"), "Sorry text");
    private Div dvComparison = new Div(By.xpath("//div[contains(.,'COMPARISON DETAILS')]"), "Comparison participantSSDetails");
    private Link lnkUpdateProfile = new Link(By.xpath("//a[.='Update Education Profile']"), "Update Education Profile");
    private Div dvEduText = new Div(By.xpath("//h2[contains(.,'Education')]/following-sibling::p"), "Education text");
    private static final String nothing = "Nothing found to display.";
    private final TableCell tbcNoSearchResults = new TableCell(By.xpath(String.format("//td[contains(.,'%1$s')]", nothing)), nothing);

    private String pageLocator = "//a[.='%1$s']";

    public ProfessionalDevelopmentOpportunityForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Professional Development Opportunities')]"), "Proffessional Development Opportunities form");
    }

    /**
     * Search for PDO using only PDO title and provider name
     *
     * @param titleProvider - title of the PDO or its provider name
     */
    public void searchTitleProvider(String titleProvider) {
        tbPdoTitle.type(titleProvider);
        btnSearch.clickAndWait();
    }

    /**
     * Search for PDO using only its ID
     *
     * @param id - PDO id.
     */
    public void searchId(String id) {
        tbPdoId.type(id);
        btnSearch.clickAndWait();
    }

    /**
     * Search for PDO ID and title/provider
     *
     * @param pdoId    - pdo ID
     * @param pdoTitle - pdo provider/title
     */
    public void searchIdTitleProvider(String pdoId, String pdoTitle) {
        tbPdoId.type(pdoId);
        tbPdoTitle.type(pdoTitle);
        btnSearch.clickAndWait();
    }

    /**
     * Check FAQ text on the PDO page.
     */
    public void checkFaqText() {
        CustomAssertion.softAssertContains( dvFaqFirstQuestion.getText().trim(), faqFirstQuestion, "Incorrect FAQ first question text");
        CustomAssertion.softAssertContains(dvFaqFirstAnswer.getText().trim(), faqFirstAnswer, "Incorrect FAQ first answer text");
        dvFaqSecondQuestion.click();
        CustomAssertion.softAssertContains(dvFaqSecondQuestion.getText().trim(), faqSecondQuestion, "Incorrect FAQ second question text");
        CustomAssertion.softAssertContains(dvFaqSecondAnswer.getText().trim(), faqSecondAnswer, "Incorrect FAQ second answer text");
        dvFaqThirdQuestion.click();
        CustomAssertion.softAssertContains(dvFaqThirdQuestion.getText().trim(), faqThirdQuestion, "Incorrect FAQ third question text");

        CustomAssertion.softAssertContains(dvFaqThirdAnswer.getText().trim(), faqThirdAnswer, "Incorrect FAQ third answer text");
    }

    /**
     * Validate data in the title search field
     *
     * @param title - title of the PDO
     */
    public void validateDataTitleField(String title) {
        CustomAssertion.softAssertContains(tbPdoTitle.getValue(), title, "Incorrect PDO title in the search field");

    }

    /**
     * Get information about your PDO by title
     */
    public String[] getDataFromDB(String title) {
        String providerName = AdvancedSqlFunctions.getPdoProviderCreatedDate(title)[0];
        String dateCreated = AdvancedSqlFunctions.getPdoProviderCreatedDate(title)[1];
        String formatDate = CommonFunctions.changeDateFormat(dateCreated, Constants.DB_FORMAT, Constants.BASE_FORMAT);
        return new String[]{providerName, formatDate};
    }

    /**
     * Validate PDO Data on the form
     *
     * @param title - pdo title
     */
    public void validatePdoData(String title, String[] dataFromDb, Integer rowNumber) {
        TableCell tbcTitle = new TableCell(By.xpath(String.format(pdoTablePath, rowNumber, 1)), "PDO title on the page");
        TableCell tbcProvider = new TableCell(By.xpath(String.format(pdoTablePath, rowNumber, 2)), "PDO provider on the page");
        TableCell tbcLocation = new TableCell(By.xpath(String.format(pdoTablePath, rowNumber, 3)), "PDO location on the page");
        TableCell tbcPosted = new TableCell(By.xpath(String.format(pdoTablePath, rowNumber, 4)), "PDO posted on the page");

        CustomAssertion.softAssertContains(tbcTitle.getText(), title, "Incorrect PDO title");
        CustomAssertion.softAssertContains(tbcProvider.getText(), dataFromDb[0], "Incorrect PDO provider");
        CustomAssertion.softAssertContains(tbcLocation.getText(), DEFAULT_ADDRESS, "Incorrect PDO location");
        CustomAssertion.softAssertContains(tbcPosted.getText(), dataFromDb[1], "Incorrect PDO posted date");
    }

    /**
     * Check pagination on the PDO
     *
     * @param title - title of the PDO.
     */
    public void checkPagination(String title) {
        TableCell tbcLastPageTitle = new TableCell(By.xpath(String.format(pdoTablePath, 10, 1)), "PDO title on the page");
        info(tbcLastPageTitle.getText());
        int nextId = Integer.parseInt(CommonFunctions.regexGetMatch(tbcLastPageTitle.getText(), regExp).replace("(", "")) + 1;
        lnkSecond.clickAndWait();
        CustomAssertion.softAssertContains(tbcTitle.getText(), String.valueOf(nextId), "Incorrect PDO title");
        lnkFirst.clickAndWait();
        lnkLast.clickAndWait();
        validateRecords(title);
    }

    /**
     * Validate records title on the page
     *
     * @param title - title
     */
    private void validateRecords(String title) {
        boolean contains = false;
        for (int i = 1; i < 11; i++) {
            TableCell tbcNumberTitle = new TableCell(By.xpath(String.format(pdoTablePath, i, 1)), "PDO title on the page");
            if (tbcNumberTitle.getText().contains(title)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            CustomAssertion.softTrue("Requested PDO has not been found on the page", false);
        }
    }

    /**
     * Validate records on the page, paying attention that might be more than 1 result page
     *
     * @param title      - PDO title
     * @param dataFromDB - data from DB.
     */
    public void validateRecordsData(String title, String[] dataFromDB) {
        boolean contains = false;
        //If the last page is present, it's necessary to check this page at first.
        if (lnkLast.isPresent()) {
            lnkLast.clickAndWait();
            for (int i = 1; i <= countRecordsOnPage(); i++) {
                TableCell tbcNumberTitle = new TableCell(By.xpath(String.format(pdoTablePath, i, 1)), "PDO title on the page");
                if (tbcNumberTitle.getText().contains(title)) {
                    contains = true;
                    validatePdoData(title, dataFromDB, i);
                    break;
                }
            }
        }
        //If the record wasn't found on the last page, it's necessary to find it on the previous page.
        if (!contains) {
            lnkPrevious.clickAndWait();
            validateRecords(title);
        }
    }

    /**
     * Count records on the page.
     *
     * @return records count.
     */
    private Integer countRecordsOnPage() {
        int i = 1;
        int count = 0;
        TableCell tbcNumberTitle;
        do {
            count += 1;
            i++;
            tbcNumberTitle = new TableCell(By.xpath(String.format(pdoTablePath, i, 1)), "PDO title on the page");
        } while (tbcNumberTitle.isPresent());

        return count;
    }


    /**
     * Sort by record title
     *
     * @param title - title to be present
     */
    public void sortTitle(String title) {
        lnkTitle.clickAndWait();
        lnkLast.clickAndWait();
        validateRecords(title);
    }

    /**
     * Sort by record title
     *
     * @param title - title to be present
     */
    public void sortPosted(String title) {
        lnkPosted.clickAndWait();
        lnkLast.clickAndWait();
        validateRecords(title);
    }

    /**
     * Click the record in the table
     *
     * @param title - title of the PDO record
     */
    public void clickTitle(String title) {
        tbcTitle.clickAndWait();
        new Div(By.xpath(String.format(xpathHeadTitle, title)), "Title of the page").softPresent();
    }

    /**
     * Click [Return to Search Details]
     */
    public void clickReturnSearch() {
        btnReturnSearch.clickAndWait();
    }

    /**
     * Click the PDO record and then click [Apply Now]
     *
     * @param title - title of the chose PDO.
     */
    public void applyForPdo(String title) {
        clickTitle(title);
        btnApplyNow.scrollTo();
        btnApplyNow.clickAndWait();
    }

    /**
     * Input initials and click [Continue]
     *
     * @param partip - participant
     */
    public void inputInitialsContinue(Participant partip) {
        txbInitials.type(partip.getFirstName().substring(0, 1) + partip.getLastName().substring(0, 1));
        clickButton(Buttons.Continue);
    }

    /**
     * Input initials and click [Confirm]
     *
     * @param partip - participant
     */
    public void inputInitialsConfirm(Participant partip) {
        txbInitials.type(partip.getFirstName().substring(0, 1) + partip.getLastName().substring(0, 1));
        btnConfirm.clickAndWait();
    }

    /**
     * Check text after applying it.
     */
    public void checkApplyTextReturn() {
        CustomAssertion.softTrue("Incorrect text in the HEADER after submitting", dvHeader.getText().contains(HEADER));
        btnReturnDetails.clickAndWait();
        dvApplied.softPresent();
        CustomAssertion.softTrue("Incorrect applied text", dvAppliedText.getText().contains(APPLIED_TEXT));
        clickReturnSearch();
    }

    public void checkSearchResult(Boolean isResultPresent) {
        if (isResultPresent) {
            tbcNoSearchResults.assertIsNotPresent();
        } else {
            tbcNoSearchResults.assertIsPresentAndVisible();
        }
    }

    /**
     * Check, that it's impossible to apply for finished PDO.
     */
    public void unableApply() {
        CustomAssertion.softTrue("Sorry head is not present", dvSorry.isPresent());
        CustomAssertion.softTrue("Sorry message is not present", dvSorryText.isPresent());
    }

    /**
     * Check, that [Apply Now] is not present.
     */
    public void applyNotPresent() {
        btnApplyNow.softIsNotPresent();
    }

    /**
     * Check comparison participantSSDetails block
     */
    public void checkComparisonDetails() {
        dvComparison.softPresent();
    }

    /**
     * Click [Update Education Profile]
     */
    public void clickUpdateProfileLink() {
        lnkUpdateProfile.clickAndWait();
    }

    /**
     * Gets education text on the page
     *
     * @return - text in the comparison participantSSDetails about education.
     */
    public String getEduText() {
        return dvEduText.getText();
    }
}
