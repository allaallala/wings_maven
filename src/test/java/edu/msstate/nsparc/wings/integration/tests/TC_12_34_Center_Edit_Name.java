package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterCreateForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.administrative.ServiceCenters;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create a service center, open its participantSSDetails page and edit its name. Check, that changes are applied.
 * Created by a.vnuchko on 06.04.2017.
 */

@TestCase(id = "WINGS-10791")
public class TC_12_34_Center_Edit_Name extends BaseWingsTest {

    public void main() {
        String newName = CommonFunctions.getRandomAlphanumericalCode(Constants.CONTACT_LENGTH);
        info("Create new service center.");
        ServiceCenters serviceCenter = new ServiceCenters(Roles.ADMIN);
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_CENTERS, Popup.Create);
        CenterCreateForm creationPage = new CenterCreateForm();
        creationPage.fillCreationForm(serviceCenter);
        BaseNavigationSteps.logout();

        logStep("Login to the system and open the service center search form");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_CENTERS, Popup.Search);

        logStep("Fill in filters that help you to find all centers you need");
        CenterSearchForm searchPage = new CenterSearchForm();
        searchPage.performSearch(serviceCenter.getCenterName());

        logStep("Open one of them->Edit");
        searchPage.openFirstSearchResult();
        CenterDetailsForm detailsPage = new CenterDetailsForm();
        detailsPage.clickButton(Buttons.Edit);

        logStep("Edit center name");
        CenterEditForm editPage = new CenterEditForm();
        editPage.editName(newName);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);
        serviceCenter.setCenterName(newName);

        logResult("All changes are saved");
        detailsPage.validateData(serviceCenter);
    }
}
