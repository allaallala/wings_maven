package edu.msstate.nsparc.wings.integration.tests.callIn;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInCreationForm;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10409")
public class TC_07_01_Create_Call_In extends BaseWingsTest {

    private static final String CALL_IN_METHOD = "Send Email";
    private static final String CONTACT_METHOD = "Email";
    private static final String CALL_IN_RESULT = "Email Sent";
    static final String DISTANCE = "50 miles";
    private static final String city = "Bude";
    private static final String zipCode = "39630";
    private static final String lineOne = "Main St.";


    public void main() {
        divideMessage("Initialize required test data");
        JobOrder jobOrder = initData(true, false);
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);

        logStep("Select job order");
        CallInCreationForm callInCreationForm = new CallInCreationForm();
        callInCreationForm.selectJobOrder(jobOrder);

        logStep("Click rbALl");
        callInCreationForm.selectRbAll();
        callInCreationForm.clickButton(Buttons.Search);

        repeatedSteps(jobOrder);
    }

    /**
     * Initialize some objects for a test
     *
     * @param veteranParticipant - veteran participant
     * @param driver             - if participant is driver
     * @return - created jobOrder.
     */
    protected JobOrder initData(Boolean veteranParticipant, Boolean driver) {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        participant.getAddress().setCity(city);
        participant.getAddress().setZipCode(zipCode);
        participant.getAddress().setLineOne(lineOne);
        if (veteranParticipant) {
            ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        } else {
            ParticipantCreationSteps.createRegularParticipant(participant, driver);
        }
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        jobOrder.setEmployer(EmployerSteps.setEmployerValidAddress(jobOrder.getEmployer()));
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());
        return jobOrder;
    }

    /**
     * Repeated steps for several tests
     *
     * @param jobOrder - job order
     */
    protected void repeatedSteps(JobOrder jobOrder) {
        CallInCreationForm callInCreationForm = new CallInCreationForm();
        logStep("Select 'Sent Email' from dropdown in qualified participants");
        callInCreationForm.selectCallIn(CALL_IN_METHOD);

        logStep("Process call in");
        String participantName = new SearchResultsForm().getFirstSearchResultLinkText();
        callInCreationForm.process();

        logStep("Click 'Participant'->'Wagner-Peyser'->'Call-ins'");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS);

        logStep("Click [Search] in the pop-up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Select job order");
        CallInSearchForm searchForm = new CallInSearchForm();
        searchForm.selectJobOrder(jobOrder);

        logStep("Click [Search] button");
        searchForm.clickButton(Buttons.Search);

        logStep("Verify Call-in data");
        String contactCheck = String.format("%1$s on %2$s", CONTACT_METHOD, CommonFunctions.getCurrentDate());
        assertTrue("Assert Job Title failed", searchForm.getJobOrderTitle().contains(jobOrder.getJobTitle()));
        searchForm.checkCallInData(contactCheck, participantName, CALL_IN_RESULT);
    }
}
