package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create ATAA-RTAA Enrollment with several Wage Subsidies, open manage subsidies form. Search for wage weeks, choose one and click on the ending date
 * Validate date on the preview form.
 * Created by a.vnuchko on 03.11.2015.
 */

@TestCase(id = "WINGS-11051")
public class TC_23_18_Wage_Subsidies_Preview extends TC_23_10_Wage_Subsidies_Search_Clear {
    String wageType = "Other";

    public void main(){

        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, wageType);

        openPreview(atrt,subsPage);

        logResult("A View Wage Subsidy Screen is shown in the pop-up. All the fields are pre-filled with the actual correct data");
        subsPage.validatePreview(atrt, WORK_STATUS);
    }

    /**
     * Open preview form for chosen wage week.
     * @param atrt - ataa/rtaa enrollment
     */
    public void openPreview(AtaaRtaaEnrollment atrt, ManageWageSubsidiesForm subsPage){

        logStep("Click the [Search] button");
        subsPage.clickButton(Buttons.Search);

        logStep("Choose one week and make it pending");
        subsPage.chooseFirstRecord();
        subsPage.clickButton(Buttons.Edit);
        subsPage.choosePayableSave(WORK_STATUS, Constants.TRUE, Constants.FALSE);

        subsPage.clickButton(Buttons.Cancel);
        subsPage.areYouSure(Popup.Yes);

        subsPage.selectStatus(STATE_PENDING);
        subsPage.clickButton(Buttons.Search);

        logStep("Click a week ending date of any Wage Subsidy in the 'Search Results' table");
        subsPage.openFirstPreview();

        logStep("A View Wage Subsidy Screen is shown in the pop-up. All the fields are pre-filled with the actual correct data");
        subsPage.validatePreview(atrt, WORK_STATUS);
    }
}
