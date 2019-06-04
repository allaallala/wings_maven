package edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.common.TablePaginationForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Span;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Career Readiness Certifications -> Search
 */
public class CareerReadinessCertificationSearchForm extends CareerReadinessCertificationBaseForm {

    private TextBox tbDateAdministredFrom = new TextBox("id=minDateAdministered", "Date Administred From");
    private TextBox tbDateAdministredTo = new TextBox("id=maxDateAdministered", "Date Administred To");
    private Span spnFirstResult = new Span(By.xpath("//table[@id='results-table']/tbody/tr[1]//span"),"First search result in CRC");
    private Button btnSortDate = new Button("//a[contains(.,'Date Administered')]","data administred arrow");
    private Span spnExclamationMark = new Span("//img[@title='Result is Estimated']", "Yellow exclamation mark");
    private String spanXpath = SearchResultsForm.SEARCH_RESULT_XPATH_ROW + "//span";
    private String goldenRegion = "Golden Triangle Region";

    /**
     * Default constructor
     */
    public CareerReadinessCertificationSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Career Readiness Certification Search')]"), "Career Readiness Certification Search");
    }

    /**
     * Check, that yellow exclamation mark is present on the page or not.
     */
    public void checkExclamationPresent(Boolean present) {
        if (present) {
            spnExclamationMark.assertIsPresentAndVisible();
        } else {
            spnExclamationMark.assertIsNotPresent();
        }
    }
    /**
     * This method is used for search for Career Readiness Certification record
     * @param crc Information that will be used for search
     */
    public void performSearch(CareerReadinessCertification crc) {

        selectParticipant(crc.getParticipant());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for searching and opening Career Readiness Certification record
     * @param crc Information that will be used for search
     */
    public void performSearchAndOpen(CareerReadinessCertification crc) {
        performSearch(crc);
        openFirstSearchResult();
    }

    public enum SearchCrcCriteria { PARTICIPANT, DATEFROM, DATETO, CREATORSTAFF, SERVICECENTER }

    /**
     * Fill out Search CRC form by one (and only one) search criteria.
     * @param crc - career readiness certification
     * @param type - search criteria: participant, creator staff etc.
     */
    public void fillOneCriteria(CareerReadinessCertification crc,  SearchCrcCriteria type) {
        switch (type) {
            case PARTICIPANT:
                selectParticipant(crc.getParticipant().getFirstName(), crc.getParticipant().getLastName());
                break;
            case DATEFROM:
                tbDateAdministredFrom.type(crc.getDateAdministired());
                break;
            case DATETO:
                tbDateAdministredTo.type(crc.getDateAdministired());
                break;
            case CREATORSTAFF:
                selectStaff();
                break;
            case SERVICECENTER:
                clickAndWait(BaseButton.SERVICE_CENTER_LOOK_UP);
                ServiceSearchForm serviceSearchForm = new ServiceSearchForm(Constants.PARTICIPANT_SS);
                serviceSearchForm.inputServiceCenterName(goldenRegion);
                clickAndWait(BaseButton.SEARCH);
                serviceSearchForm.selectEmployerService();
                clickAndWait(BaseButton.RETURN);
                break;
            default:
                info("Try to enter some correct date");
                break;
        }

    }

    public void validateOneCriteria(CareerReadinessCertification crc) {
        String creatorStaff = "Auto Staff";
        int flag = 0;
        btnSortDate.clickAndWait();
        if (TablePaginationForm.isPresent()) {
            new TablePaginationForm().openLastPage();
        }

        SearchResultsForm resultsForm = new SearchResultsForm();
        for (int i = 1; i < Constants.RECORDS_ON_PAGE; i++) {
            TableCell tbcParticipantName = new TableCell(By.xpath(String.format(spanXpath, i, "2")), "Value of participant in the search result table");
            String dateAdministered = resultsForm.getRecordText(i, "3").trim();
            String staff = resultsForm.getRecordText(i, "5").trim();
            if (tbcParticipantName.getText().contains(crc.getParticipant().getFirstName())) {
                CustomAssertion.softAssertContains(tbcParticipantName.getText(),
                        crc.getParticipant().getFirstName() + " " + crc.getParticipant().getLastName(),
                        "Incorrect participant name");
                CustomAssertion.softAssertEquals(dateAdministered, crc.getDateAdministired(), "Incorrect date administred");
                CustomAssertion.softAssertEquals(staff, creatorStaff, "Incorrect creator staff");
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            fatal("The requested record was not found");
        }
        btnSortDate.clickAndWait();
        clickButton(Buttons.Clear);
    }

    /**
     * Open first search result for crc.
     */
    public void openSpanFirstResult() {
        spnFirstResult.clickAndWait();
    }
}
