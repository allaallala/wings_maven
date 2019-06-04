package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


/**
 * Create some career readiness certification, set assessment's scores connected with this crc to incorrect values (like 5). Check,
 * that on searching for this CRC, warning message is displayed.
 * Created by a.vnuchko on 16.12.2015.
 */

@TestCase(id = "WINGS-10991")
public class TC_20_25_CRC_Edit_Old_Assessments_Scores_Preview extends BaseWingsTest {
    private static final Integer SCORE = 5;
    private static final String WARNING_MESSAGE = "The score for one or more of the associated WorkKeys assessments has been changed since this Career Readiness Certification was created. Please Edit this Career Readiness Certification in order for the record to be updated.";
    private CareerReadinessCertification crc;

    public void main() {


        info("Precondition: Create some Career Readiness Certification");
        crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        info("Change Assessments (used in the CRC) scores to incorrect, e.g. set it to 5");
        ParticipantSqlFunctions.setAssessmentScores(SCORE, crc.getParticipant().getFirstName());

        logStep("Search for created crc");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);

        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        searchPage.performSearch(crc);

        logResult("Verify that a yellow triangle dispalyed near the record in the list of search results");
        searchPage.checkExclamationPresent(Constants.TRUE);

        searchPage.openFirstSearchResult();

        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        info(detailsPage.getWarningMessage());
        Assert.assertEquals("Incorrect warning text on the page", WARNING_MESSAGE, detailsPage.getWarningMessage());
    }

    /**
     * Get career readiness certification
     *
     * @return crc.
     */
    public CareerReadinessCertification getCrc() {
        return crc;
    }
}
