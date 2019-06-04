package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create ATAA-RTAA Enrollment with several Wage Subsidies, open manage subsidies form. Choose any subsidie and make this payable. Choose next one and make it non-payable.
 * Check, that changes are saved.
 * Created by a.vnuchko on 30.10.2015.
 */

@TestCase(id = "WINGS-11050")
public class TC_23_16_Wage_Subsidies_Is_This_Payable extends TC_23_10_Wage_Subsidies_Search_Clear {

    public void main(){
        String zeroWages = "0";
        String manageType = "Other";
        String weekAmountZero = "0.00";

        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, manageType);

        subsPage.chooseFirstRecord();
        subsPage.clickButton(Buttons.Edit);

        logStep("Choose work status, payable, save changes and move to the next week ");
        String weekAmount = subsPage.getWeekSubsidiesAmount();
        subsPage.choosePayableSave(WORK_STATUS, Constants.TRUE, Constants.FALSE);
        subsPage.choosePayableSave(WORK_STATUS, Constants.FALSE, Constants.FALSE);

        logStep("Click [Cancel] button");
        subsPage.clickButton(Buttons.Cancel);
        subsPage.areYouSure(Popup.Yes);

        logResult("The Manage Wage Subsidies Screen is shown. The changes are saved");
        subsPage.selectStatus(STATE_PENDING);
        subsPage.clickButton(Buttons.Search);
        String dateStart = CommonFunctions.getFirstWageWeek(atrt.getReemployment().getStartDate());
        subsPage.validateWageRecords(dateStart, Constants.RANDOM_ONE, STATE_PENDING, atrt, weekAmount);

        subsPage.selectStatus(STATE_NOTPAYABLE);
        subsPage.clickButton(Buttons.Search);
        atrt.getReemployment().setWages(zeroWages);
        subsPage.validateWageRecords(CommonFunctions.getNextWageWeek(dateStart), Constants.RANDOM_ONE, STATE_NOTPAYABLE,
                atrt, weekAmountZero);
    }
}
