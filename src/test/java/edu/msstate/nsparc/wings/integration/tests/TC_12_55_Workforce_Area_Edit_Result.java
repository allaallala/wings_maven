package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreaDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreaEditForm;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreasSearchForm;
import edu.msstate.nsparc.wings.integration.models.administrative.LWIA;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10794")
public class TC_12_55_Workforce_Area_Edit_Result extends BaseWingsTest {

    public void main () {
        TC_12_47_Workforce_Area_Search workforceAreaSearch = new TC_12_47_Workforce_Area_Search();
        workforceAreaSearch.performSearch();

        LWIA lwia = new LWIA(Roles.ADMIN);

        logStep("Open Area Details form");
        WorkforceAreasSearchForm workforceAreasSearchForm = new WorkforceAreasSearchForm();
        workforceAreasSearchForm.openFirstSearchResult();

        logStep("Click [Edit] button");
        WorkforceAreaDetailsForm workforceAreaDetailsForm = new WorkforceAreaDetailsForm();
        workforceAreaDetailsForm.clickButton(Buttons.Edit);

        logStep("Edit workforce area");
        WorkforceAreaEditForm workforceAreaEditForm = new WorkforceAreaEditForm();
        workforceAreaEditForm.editWorkforceAreaDetails(lwia);
        workforceAreaEditForm.clickButton(Buttons.Save);
    }
}
