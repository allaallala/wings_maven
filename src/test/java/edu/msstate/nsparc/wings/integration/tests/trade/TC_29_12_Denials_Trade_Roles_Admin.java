package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Check functionality denials for trade for an administrator.
 * Created by a.vnuchko on 31.08.2016.
 */

@TestCase(id = "WINGS-11144")
public class TC_29_12_Denials_Trade_Roles_Admin extends BaseWingsTest {
    TradeEnrollment trenrl;

    public void main() {
        divideMessage("Preconditions: create trade enrollment");
        trenrl = new TradeEnrollment();

        TradeEnrollmentSteps.createTradeEnrollment(trenrl, Roles.TRADEDIRECTOR);

        User user = new User(Roles.ADMIN);
        commonDenialsSteps(user);
    }

    /**
     * Common steps for checking the following functionality: create a denial, edit a denial, appeal a denial, edit a denial appeal, view a denial/appeal
     * @param user - current user.
     */
    protected void commonDenialsSteps(User user){
        BaseWingsSteps.logInAs(user.getRole());

        if(user.getTradeEnrollment().getTeView()) {
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);

            //If user has permissions to create trade enrollment, it's required confirm pop-up.
            if(user.getTradeEnrollment().getTeCreate()){
                BaseWingsSteps.popClick(Popup.Search);
            }
            TradeEnrollmentSearchForm searchPage = new TradeEnrollmentSearchForm();
            searchPage.performSearchAndOpen(trenrl);
            TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();

            //(!) Create a denial
            if(user.getDenTr().getDftCreateDenial()){
                logStep("Create a denial");
                trenrl.setEligible(Constants.FALSE);
                detailsForm.clickButton(Buttons.Edit);
                TradeEnrollmentEditForm editPage = new TradeEnrollmentEditForm();
                editPage.changeEligibilityStatus(trenrl);
                detailsForm.validateDenialsFirst(trenrl);
            }

            //(!) Appeal a denial
            if(user.getDenTr().getDftAppealDenial()){
                logStep("Appeal a denial");
                detailsForm.clickAddAppeal();
                detailsForm.addAppeal(trenrl);
                detailsForm.validateAppeal(trenrl);
            }

            //(!) Edit a denial appeal
            if(user.getDenTr().getDftEditDenialAppeal()){
                logStep("Edit a denial appeal");
                trenrl.setAppealNumber(CommonFunctions.getRandomIntegerNumber(3));
                detailsForm.clickEditAppeal();
                detailsForm.addAppeal(trenrl);
                detailsForm.validateAppeal(trenrl);
            }

            //(!) Edit a denial
            if(user.getDenTr().getDftEditDenial()){
                logStep("Edit a denial");
                detailsForm.editDenial(CommonFunctions.getCurrentDate());
                detailsForm.clickButton(Buttons.Save);
                detailsForm.expandDenials();
                detailsForm.validateDenials(Constants.TRUE);
            }

            //(!) View a denial
            if(user.getDenTr().getDenialsView()){
                logStep("View denial/appeal");
                detailsForm.expandDenials();
                detailsForm.validateDenialsFirst(trenrl);
                detailsForm.validateDenials(Constants.TRUE);
            }
        }
        BaseNavigationSteps.logout();
    }
}
