package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceEditForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Edit some data of the chosen service but do not save it (click Cancel). Check,that changes are not applied.
 * Created by a.vnuchko on 05.04.2017.
 */

@TestCase(id = "WINGS-11266")
public class TC_38_27_Services_Edit_Result_Cancel extends BaseWingsTest {
    private String titleService = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);

    public void main(){
        ServiceSteps.createService(Roles.ADMIN, titleService, Constants.FALSE, Constants.FALSE);

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_SERVICES, Popup.Search);
        ServiceSearchForm searchForm = new ServiceSearchForm();

        logStep("Fill in filters that help you to find all services you need");
        searchForm.performSearchAndOpen(titleService);

        logStep("Open one of them->Edit");
        ServiceDetailsForm detailsPage = new ServiceDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Enter some data to the fields");
        ServiceEditForm editPage = new ServiceEditForm();
        editPage.inputServiceName("Uskudara");

        logStep("Cancel");
        editPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);

        logResult("The previous page is opened");
        detailsPage.validateServiceTitle(titleService);
    }
}
