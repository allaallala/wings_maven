package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ReferralSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Create some referrals and check search using different field (except using participant - checked in other tests)
 * Created by a.vnuchko on 13.03.2017.
 */

@TestCase(id = "WINGS-11253")
public class TC_38_13_Referrals_Search_Different_Fields extends BaseWingsTest {

    public void main() {
        String result = "Unresulted";
        String serviceCenter = "Golden Triangle Region";
        String date = CommonFunctions.getCurrentDate();
        info("There is a referral in the system");
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        User admin = new User(Roles.ADMIN);
        ReferralSteps.createReferral(participant, jobOrder, admin);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Search);
        ReferralSearchForm searchForm = new ReferralSearchForm();
        searchForm.performSearchJobOrder(jobOrder);
        searchForm.clickButton(Buttons.Search);
        commonSteps(participant, jobOrder, result, date);

        searchForm.selectResult(result);
        searchForm.clickButton(Buttons.Search);
        commonSteps(participant, jobOrder, result, date);

        searchForm.inputCreationDateRange(date);
        searchForm.clickButton(Buttons.Search);
        commonSteps(participant, jobOrder, result, date);

        searchForm.selectOneStopService(serviceCenter);
        searchForm.clickButton(Buttons.Search);
        commonSteps(participant, jobOrder, result, date);
    }

    private void commonSteps(Participant participant, JobOrder order, String result, String date) {
        ReferralSearchForm searchForm = new ReferralSearchForm();
        searchForm.clickButton(Buttons.Search);
        searchForm.validateSearchResult(participant, order, result, date);
        searchForm.clearSomeFields();
    }
}
