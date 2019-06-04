package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceEditForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import java.sql.SQLException;


@TestCase(id = "WINGS-10779")
public class TC_12_15_Services_Edit_Result extends BaseWingsTest {

    private static final Integer RANDOM_LENGTH = 5000;
    String serviceTitle  = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    String serviceEndDate = CommonFunctions.getTomorrowDate();
    String category = "Training";
    String description = CommonFunctions.getRandomLiteralCode(RANDOM_LENGTH);

    private String title = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);

    private String [] originalDetails = new String [Constants.ORIGINAL_DETAILS_LENGTH];

    public void main() throws SQLException {
        ServiceSteps.createService(Roles.ADMIN, title, Constants.FALSE, Constants.FALSE);

        editService(title);

        //revert changes and validate
        ServiceDetailsForm serviceDetailsForm = new ServiceDetailsForm();
        serviceDetailsForm.clickButton(Buttons.Edit);

        logStep("Revert changes");
        ServiceEditForm serviceEditForm = new ServiceEditForm();
        serviceEditForm.editServiceDetails(originalDetails[0], originalDetails[1], originalDetails[2], originalDetails[3]);
        serviceEditForm.clickButton(Buttons.Continue);
        serviceEditForm.clickButton(Buttons.Save);

        serviceDetailsForm.validateDetails(originalDetails[0], originalDetails[1], originalDetails[2], originalDetails[3]);

    }

    /**
     * Edit service
     * @param titleService title of service
     */
    public void editService (String titleService) {
        TC_12_08_Services_Search servicesSearch = new TC_12_08_Services_Search();
        servicesSearch.performSearch(titleService);

        logStep("Click to view participantSSDetails");
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm();
        serviceSearchForm.openFirstSearchResult();

        logStep("Press 'Edit' button");
        ServiceDetailsForm serviceDetailsForm = new ServiceDetailsForm();
        serviceDetailsForm.clickButton(Buttons.Edit);

        logStep("Edit all participantSSDetails and save changes");
        ServiceEditForm serviceEditForm = new ServiceEditForm();

        originalDetails = serviceEditForm.getValues();

        serviceEditForm.editServiceDetails(serviceTitle, serviceEndDate, category, description);
        serviceEditForm.clickButton(Buttons.Continue);
        serviceEditForm.clickButton(Buttons.Save);

        logStep("Validate changes participantSSDetails");
        serviceDetailsForm = new ServiceDetailsForm();
        serviceDetailsForm.validateDetails(serviceTitle, serviceEndDate, category, description);
    }

}
