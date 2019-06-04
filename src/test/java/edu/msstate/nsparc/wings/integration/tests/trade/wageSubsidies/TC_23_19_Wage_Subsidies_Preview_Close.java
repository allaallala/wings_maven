package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create ATAA-RTAA Enrollment with several Wage Subsidies, open manage subsidies form. Search for wage weeks, choose one and click on the ending date
 * validate date on the preview form and close it. Check, that required form is opened.
 * Created by a.vnuchko on 04.11.2015.
 */

@TestCase(id = "WINGS-11052")
public class TC_23_19_Wage_Subsidies_Preview_Close extends TC_23_18_Wage_Subsidies_Preview {

    public void main(){


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);

        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, wageType);

        openPreview(atrt, subsPage);

        logStep("Click Close Preview");
        subsPage.closePreview();

        logResult("The Manage Wage Subsidies Screen is shown");
        new ManageWageSubsidiesForm();
    }
}
