package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyEditForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10679")
public class TC_07_111_Everify_Edit_Number extends BaseWingsTest {

    private static final String CASE_NUMBER = "12345";
    private static final String NEW_CASE_NUMBER = "54321";
    private static final String FIRST_DOCUMENT_TYPE = "School ID Card";
    private static final String SECOND_DOCUMENT_TYPE = "Certification of Birth Abroad";
    private static final String DOCUMENT_NUMBER = "123";

    public void main() {


        info("For E-Verify edit we need:");
        info("Verified Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        EverifySearchForm everify = EverifySteps.openEverifySearchPage(participant, Roles.ADMIN, CASE_NUMBER);

        logStep("Open one of the e-verify(Case number)->Edit");
        everify.openFirstSearchResult();
        EverifyDetailsForm everifyDetailsForm = new EverifyDetailsForm();
        everifyDetailsForm.clickButton(Buttons.Edit);

        logStep("Change Case Number");
        EverifyEditForm everifyEditForm = new EverifyEditForm();
        everifyEditForm.inputCaseNumber(NEW_CASE_NUMBER);
        everifyEditForm.inputFirstSecondDocumentNumbers(FIRST_DOCUMENT_TYPE, DOCUMENT_NUMBER, SECOND_DOCUMENT_TYPE, DOCUMENT_NUMBER);

        logStep("Save changes");
        everifyEditForm.clickButton(Buttons.Save);

        logStep("Validate, that Case Number is changed");
        everifyDetailsForm = new EverifyDetailsForm();
        assertEquals("Case Number assert fail", NEW_CASE_NUMBER, everifyDetailsForm.getCaseNumberText());
        everifyDetailsForm.clickButton(Buttons.Done);
        BaseNavigationSteps.logout();
        logEnd();
    }
}
