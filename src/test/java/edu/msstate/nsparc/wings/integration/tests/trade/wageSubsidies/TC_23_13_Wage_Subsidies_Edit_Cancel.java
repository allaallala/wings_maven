package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;


/**
 * Create ATAA-RTAA Enrollment with several Wage Subsidies, open manage subsidies form. Choose any subsidie and edit some data. Cancel edition.
 * Created by a.vnuchko.
 */

@TestCase(id = "WINGS-11047")
public class TC_23_13_Wage_Subsidies_Edit_Cancel extends TC_23_10_Wage_Subsidies_Search_Clear {
    private static final String STATE_CHANGED = "Family Care";
    String wageType = "Other";


    public void main(){


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, wageType);

        logStep("Click the [Search] button");
        subsPage.clickButton(Buttons.Search);

        logStep("Select any Wage Subsidy");
        subsPage.chooseFirstRecord();

        logStep("Click [Edit] button");
        subsPage.clickButton(Buttons.Edit);

        logStep("Edit any parameters");
        subsPage.editStatus(STATE_CHANGED);

        logStep("Click the [Cancel] button");
        subsPage.clickButton(Buttons.Cancel);
        subsPage.areYouSure(Popup.Yes);
        Browser.getInstance().waitForPageToLoad();

        logResult("The Manage Wage Subsidies Screen is shown, the changes are not saved. Status for record isn't changed");
        subsPage.validateFirstStatus(STATE_INITIAL);
    }
}
