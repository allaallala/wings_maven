package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralResultForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Create some referral that belong to a chosen participant. Go to the Referral Resulting menu. Fill in some fields
 * then clear form. Check, that all fields are cleared.
 * Created by a.vnuchko on 13.03.2017.
 */

@TestCase(id = "WINGS-11255")
public class TC_38_16_Referral_Resulting_Search_Clear extends BaseWingsTest {
    protected String serviceCenter = "Golden Triangle Region";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User admin = new User(Roles.ADMIN);
        ReferralSteps.createReferral(participant, jobOrder, admin);

        logStep("Wagner-Peyser->Referral Resulting");
        BaseWingsSteps.logInAs(Roles.ADMIN);
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRAL_RESULTING);

        logStep("Select participant");
        ReferralResultForm resultForm = new ReferralResultForm();
        resultForm.performSearch(participant);

        logStep("Fill in other fields with some data->Clear form");
        resultForm.performSearch(jobOrder);
        resultForm.selectOneStopService(serviceCenter);
        resultForm.inputLastNameBegin(participant.getLastName());
        resultForm.inputCreationDateRange(CommonFunctions.getCurrentDate());
        resultForm.clickButton(Buttons.Clear);

        logResult("All fields are empty");
        resultForm.validateFieldsCleared();
    }


}
