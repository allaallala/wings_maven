package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create ataa/rtaa enrollment with several wage subsidies. Edit some subsidies and check record changes.
 * Created by a.vnuchko on 11.11.2015.
 */

@TestCase(id = "WINGS-11048")
public class TC_23_14_Wage_Subsidies_Change_Status_Button_Save extends TC_23_10_Wage_Subsidies_Search_Clear {
    String weekNotPayable = "0.00";
    String zeroWages = "0";
    String newWages = "30";

    public void main(){


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        String dateStart = CommonFunctions.getFirstWageWeek(atrt.getReemployment().getStartDate());
        String dateStartNext = CommonFunctions.getNextWageWeek(dateStart);

        ManageWageSubsidiesForm subsidiesPage = openManageWageSubsidiesForm(atrt, "other");

        logStep("Choose record with status 'Not yet entered' and click on the 'Edit' button");
        subsidiesPage.chooseFirstRecord();
        subsidiesPage.clickButton(Buttons.Edit);

        logStep("Change status to 'work' or any other where option 'Is this payable?' appears. Chose value Yes in option 'Is this payable?' and click on the button 'Save and Finish'");
        String weekAmount = subsidiesPage.choosePayableSave(WORK_STATUS, Constants.TRUE, Constants.TRUE);

        logResult("Status of edited record changes to Pending");
        subsidiesPage.selectStatus(STATE_PENDING);
        subsidiesPage.clickButton(Buttons.Search);
        subsidiesPage.validateWageRecords(dateStart, Constants.RANDOM_ONE, STATE_PENDING, atrt, weekAmount);

        logStep("Choose record with status 'Not yet entered' and click on the 'Edit' button ");
        subsidiesPage.clickButton(Buttons.Clear);
        subsidiesPage.chooseDefinedRecord(Constants.RANDOM_TWO);

        logStep("Change status to 'work' or any other where option 'Is this payable?' appears. Chose value No in option 'Is this payable?' and click on the button 'Save and finisht'");
        subsidiesPage.clickButton(Buttons.Edit);
        subsidiesPage.choosePayableSave(WORK_STATUS, Constants.FALSE, Constants.TRUE);

        logResult("Status of edited record changed to Not Payable");
        subsidiesPage.selectStatus(STATE_NOTPAYABLE);
        subsidiesPage.clickButton(Buttons.Search);

        atrt.getReemployment().setWages(zeroWages);
        subsidiesPage.validateWageRecords(dateStartNext, Constants.RANDOM_ONE, STATE_NOTPAYABLE, atrt, weekNotPayable);

        logStep("Choose record with status 'Pending' and click on the 'Edit' button");
        subsidiesPage.selectStatus(STATE_PENDING);
        subsidiesPage.clickButton(Buttons.Search);
        subsidiesPage.chooseFirstRecord();

        logStep("Change status to 'work' or any other where option 'Is this payable?' appears. Chose value No in option 'Is this payable?' and click on the button 'Save and Finish'");
        subsidiesPage.clickButton(Buttons.Edit);
        subsidiesPage.choosePayableSave(WORK_STATUS, Constants.FALSE, Constants.TRUE);

        logResult("Status of edited record changes to Not Payable");
        subsidiesPage.selectStatus(STATE_NOTPAYABLE);
        subsidiesPage.clickButton(Buttons.Search);
        subsidiesPage.validateWageRecords(dateStart, Constants.RANDOM_TWO, STATE_NOTPAYABLE, atrt, weekNotPayable);

        logStep("Choose record with status 'Not Payable' and click on the 'Edit' button");
        subsidiesPage.chooseDefinedRecord(Constants.RANDOM_TWO);

        logStep("Change status to 'work' or any other where option 'Is this payable?' appears. Chose value Yes in option 'Is this payable?' and click on the button 'Save and Finish'");
        subsidiesPage.clickButton(Buttons.Edit);
        subsidiesPage.choosePayableSave(WORK_STATUS, Constants.TRUE, Constants.TRUE);

        logResult("Status of edited record changes to Pending");
        subsidiesPage.selectStatus(STATE_PENDING);
        subsidiesPage.clickButton(Buttons.Search);
        atrt.getReemployment().setWages(newWages);
        subsidiesPage.validateWageRecords(dateStartNext, Constants.RANDOM_ONE , STATE_PENDING, atrt, weekAmount);
    }
}
