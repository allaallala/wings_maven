package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import org.testng.Assert;


@TestCase(id = "WINGS-10657")
public class TC_06_09_Youth_Assessment_Pre_Test_Edit extends BaseWingsTest {

    //Assessments
    private String dateAdministred = CommonFunctions.getYesterdayDate();
    private String dateAdministredThird = CommonFunctions.getDaysAgoDate(2);
    private String type = NEW_ASSESSMENT_TYPE;
    private String assessmentProgram = "WIA";
    private String thirdType = "CASAS";
    private String score = "85";
    private String secondScore = "80";
    private String thirdScode = "81";
    private String functionalArea = "Writing";
    private String secondFA = "Reading/Reading for Information";
    private String thirdFA = "Mathematics";

    private static final String NEW_ASSESSMENT_TYPE = "WorkKeys";
    private static final String NEW_FUNCTIONAL_AREA = "Mathematics";


    //Bug WINGS-10132 , sub-task WINGS-3111
    public void main() {

        info("Creating Youth WIA Enrollment");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        info("Creating 3 Youth Assessments");
        Assessment firstAssessment = new Assessment(participant, assessmentProgram, dateAdministred, type, score, functionalArea);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, firstAssessment);
        Assessment secondAssessment = new Assessment(participant, assessmentProgram, dateAdministred, type, secondScore, secondFA);
        AssessmentSteps.createAssessment(staff, secondAssessment);
        Assessment thirdAssessment = new Assessment(participant, assessmentProgram, dateAdministredThird, thirdType, thirdScode, thirdFA);
        AssessmentSteps.createAssessment(staff, thirdAssessment);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);

        logStep("Search for 3rd Youth Assessment");
        AssessmentSearchForm searchForm = new AssessmentSearchForm();
        searchForm.performSearch(thirdAssessment);

        logStep("Check that Assessment was found");
        searchForm.yesSearchResult();

        logStep("Open Youth Assessment");
        searchForm.openFirstSearchResult();
        AssessmentDetailsForm detailsForm = new AssessmentDetailsForm();

        logStep("Click on Edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Change Assessment type to WorkKeys");
        AssessmentEditForm editForm = new AssessmentEditForm();
        thirdAssessment.setType(NEW_ASSESSMENT_TYPE);
        thirdAssessment.setFunctionalArea(NEW_FUNCTIONAL_AREA);
        editForm.selectType(thirdAssessment.getType());

        logStep("Check that Total Mathematics is populated in Functional Area");
        Assert.assertTrue(editForm.getFunctionalText().contains(thirdAssessment.getFunctionalArea()));

        logStep("Click on Save Changes");
        editForm.saveChanges();

        logStep("Check Functional Area value");
        detailsForm.validateAssessmentInformation(thirdAssessment);
    }
}
