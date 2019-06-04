package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifyEditForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EverifySteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Create some participant with e-verify numbers. Search for that participant in e-verify. Try to edit case number in
 * order to make status date more than current date. Check, that warning message is displayed.
 * Created by User on 14.03.2017.
 */

@TestCase(id = "WINGS-11259")
public class TC_38_20_Everify_Edit_Sdate_Less_Current extends BaseWingsTest {
    private static final String CASE_NUMBER = "12345";
    private String date = CommonFunctions.getFutureDate(1);

    public void main() {
        info("E-Verified Participant");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        Everify everify = EverifySteps.eVerifyParticipant(participant, new User(Roles.STAFF));


        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY, Popup.Search);

        logStep("Search and open chosen participant");
        EverifySearchForm searchPage = new EverifySearchForm();
        searchPage.performSearchAndOpen(participant);

        logStep("Click [Edit]");
        EverifyDetailsForm detailsPage = new EverifyDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Select status date>current date");
        EverifyEditForm editPage = new EverifyEditForm();
        everify.setStatusDate(date);
        editPage.editStatusDate(date);

        logStep("Save Changes");
        editPage.clickButton(Buttons.Save);

        logResult("Warning message appears");
        editPage.validateErrorStatusDate();
    }

}
