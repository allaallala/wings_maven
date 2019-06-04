package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import org.testng.Assert;

/**
 * Create ataa/rtaa enrpllment with several wage subsidies. Edit some records.
 * Check, that button "save and go next" works as "save and finish" for the last record.
 * Created by a.vnuchko on 10.11.2015.
 */

@TestCase(id = "WINGS-11046")
public class TC_23_12_Wage_Subsidies_Edit_Last_Record extends TC_23_10_Wage_Subsidies_Search_Clear {
    String type = "other";

    public void main() {


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsidiesPage = openManageWageSubsidiesForm(atrt, type);

        Integer countWageSubsidies = ParticipantSqlFunctions.getCountWageSubsidies(atrt.getParticipant().getFirstName());

        logStep("Choose any subsidy record");
        subsidiesPage.chooseDefinedRecord(countWageSubsidies - 1);

        logStep("Click [Edit] button");
        subsidiesPage.clickButton(Buttons.Edit);

        logStep("Edit any parameter");
        subsidiesPage.choosePayableSave(WORK_STATUS, Constants.TRUE, Constants.FALSE);
        subsidiesPage.choosePayableSave(WORK_STATUS, Constants.TRUE, Constants.TRUE);

        logResult("For this last subsidy clicking on the button 'Save and go next' works as the same as clicking on the button 'Save and Finish'"
                + "Form 'Manage Wages Subsidies' is shown. Check filtration of edited record.");

        subsidiesPage.selectStatus(STATE_PENDING);
        subsidiesPage.clickButton(Buttons.Search);

        String recordsCount = CommonFunctions.regexGetMatch(subsidiesPage.getSearchedCount(), Constants.COUNT_REGEX);
        Assert.assertEquals("Incorrect quantity of payment records on the page", Constants.RANDOM_TWO.toString(), recordsCount);
    }
}
