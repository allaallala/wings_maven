package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10597")
public class TC_04_10_WIA_Enrollment_Convert_Adult_To_Youth extends BaseWingsTest {

    private static final String EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    private static final String TOOL_USED = "Other, Not Listed";
    private static final String EDITED_EDUCATIONAL_STATUS = "In school, high school or less";


    //sub-task WINGS-2665
    //sub-task WINGS-2595
    //bug WINGS-10130
    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, false);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT, Popup.Search);

        logStep("Search for Adult Enrollment");
        WIAEnrollmentSearchForm wiaEnrollmentSearchForm = new WIAEnrollmentSearchForm();
        wiaEnrollmentSearchForm.performSearch(participant);

        logStep("Open Enrollment");
        wiaEnrollmentSearchForm.openFirstSearchResult();
        WIAEnrollmentDetailsForm wiaEnrollmentDetailsForm = new WIAEnrollmentDetailsForm();

        logStep("Press 'Edit' button for Basic Information section");
        wiaEnrollmentDetailsForm.clickAndWait(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION);
        WIAEnrollmentEditForm wiaEnrollmentEditForm = new WIAEnrollmentEditForm();

        logStep("Change participant type to Youth");
        wiaEnrollmentEditForm.selectParticipantType("Youth");

        logStep("Press 'Go to Youth Information' button");
        wiaEnrollmentEditForm.goYouthInfo();

        logStep("Fill Youth Information form");
        wiaEnrollmentEditForm.fillWIAEnrollmentYouthInformation(EDUCATIONAL_STATUS, TOOL_USED, true);

        logStep("Save changes");
        wiaEnrollmentEditForm.clickButton(Buttons.Save);

        logStep("Validate that 'WIA Enrollment Detail' form is displayed");
        assertTrue("'WIA Enrollment Detail' form is not displayed", wiaEnrollmentDetailsForm.isButtonPresent(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION));

        logStep("Search for edited WIA Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);
        wiaEnrollmentSearchForm.performSearch(participant);
        wiaEnrollmentSearchForm.openFirstSearchResult();

        logStep("Try to edit Youth Information");
        wiaEnrollmentDetailsForm.editYouthInformation();

        logStep("Try to change 'Educational Status' selection");
        wiaEnrollmentEditForm.selectEducationStatus(EDITED_EDUCATIONAL_STATUS);

        logStep("Try to change Basic Skills Deficient Tool Used");
        wiaEnrollmentEditForm.selectFirstToolUsed();

        logStep("Save changes");
        wiaEnrollmentEditForm.clickButton(Buttons.Save);

        logStep("Validate that 'WIA Enrollment Detail' form is displayed");
        assertTrue("'WIA Enrollment Detail' form is not displayed", wiaEnrollmentDetailsForm.isButtonPresent(BaseWingsForm.BaseButton.EDIT_BASIC_INFORMATION));

        logEnd();
    }
}
