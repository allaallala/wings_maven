package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10766")
public class TC_12_04_Relocation_Clear_Search_Form extends BaseWingsTest {

    private static final String EMPTY_FIELD = "";


    public void main() {

        Relocation relocation = new Relocation();

        logStep("Log in as Trade Director and open search Relocation page");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Search);

        logStep("Fill in search criteria fields");
        RelocationSearchForm relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.selectAnyAvailableParticipant();
        relocationSearchForm.selectAnyAvailablePetition();
        relocationSearchForm.inputEmployerName(relocation.getEmployerName());
        relocationSearchForm.inputDate(relocation);
        relocationSearchForm.selectAnyAvailableServiceCenter();

        logStep("Click the [Clear Form] button");
        relocationSearchForm.clickButton(Buttons.Clear);

        logStep("Check that parameters are set to default");
        CustomAssertion.softTrue("Participant was not cleared!", relocationSearchForm.getText(BaseWingsForm.BaseTextBox.PARTICIPANT).contains(EMPTY_FIELD));
        CustomAssertion.softTrue("Petition was not cleared!", relocationSearchForm.getText(BaseWingsForm.BaseTextBox.PETITION).contains(EMPTY_FIELD));
        CustomAssertion.softTrue("Employer Name was not cleared!", relocationSearchForm.getEmployerName().contains(EMPTY_FIELD));
        relocationSearchForm.checkDate(EMPTY_FIELD);
        CustomAssertion.softTrue("Service Center was not cleared!", relocationSearchForm.getText(BaseWingsForm.BaseTextBox.PARTICIPANT).contains(EMPTY_FIELD));
    }
}
