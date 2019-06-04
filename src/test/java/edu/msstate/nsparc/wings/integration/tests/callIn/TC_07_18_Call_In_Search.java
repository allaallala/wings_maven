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
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10683")
public class TC_07_18_Call_In_Search extends BaseWingsTest {

    private String jobTitle = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
    private String callInMethod = "Sent a Letter";
    private String[] jobDetails;
    private String participantName;


    public void main() {


        info("For Call-in searching we need:");
        info("Employer");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        info("Job Order");
        JobOrderSteps.createJobOrder(employer, jobTitle);
        info("Call-in");
        createCallIn(employer);

        logStep("Wagner-Peyser->Call-ins");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS);

        logStep("Search");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Select participant you need->Search");
        CallInSearchForm callInSearchForm = new CallInSearchForm();
        callInSearchForm.clickJobOrderLookUp();
        callInSearchForm.selectAndReturnJobOrder(employer.getCompanyName(), employer.getAddress().getCity(), employer.getAddress().getZipCode(), jobTitle);
        callInSearchForm.selectParticipant(participantName);
        callInSearchForm.clickButton(Buttons.Search);

        logStep("Created call-in for selected participant is shown");

        String contactCheck = String.format("%1$s on %2$s", callInMethod.replace(" a ", " "), CommonFunctions.getCurrentDate());
        callInSearchForm.checkCallInData(contactCheck, participantName, callInMethod);
        assertTrue("Assert Job Details failed", callInSearchForm.getJobOrderTitle().contains(jobDetails[1]));
        info("Check: OK");

        BaseNavigationSteps.logout();
        logEnd();
    }

    /**
     * Create call in
     * @param employer - employer
     */
    private void createCallIn(Employer employer) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);

        CallInCreationForm callInCreationForm = new CallInCreationForm();
        callInCreationForm.clickJobOrderLookUpButton();
        jobDetails = callInCreationForm.selectAndReturnJobOrder(employer.getCompanyName(), employer.getAddress().getCity(), employer.getAddress().getZipCode(), jobTitle);

        callInCreationForm.selectRbAll();
        callInCreationForm.uncheckAcademicRequirements();
        callInCreationForm.selectDriverLicenseRequirement(Constants.FALSE);
        callInCreationForm.clickButton(Buttons.Search);

        callInCreationForm.selectCallIn(callInMethod);
        participantName = new SearchResultsForm().getFirstSearchResultLinkText();

        callInCreationForm.process();
    }
}
