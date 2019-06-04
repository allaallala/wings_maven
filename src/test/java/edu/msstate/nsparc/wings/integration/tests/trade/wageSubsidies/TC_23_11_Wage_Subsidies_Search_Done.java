package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create ATAA-RTAA Enrollment with several Wage Subsidies, open manage subsidies form. Fill out some search criteria. Click [Done]. Check that Seach form is appeared.
 * Created by a.vnuchko on 30.10.2015.
 */

@TestCase(id = "WINGS-11045")
public class TC_23_11_Wage_Subsidies_Search_Done extends TC_23_10_Wage_Subsidies_Search_Clear {
    String wageType = "Other";

    public void main(){


        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, wageType);

        logStep("Click the [Done] button");
        subsPage.cancel();

        logResult("The ATAA-RTAA Enrollment Detail Screen is shown");
        //new AtaaRtaaEnrollmentDetailsForm().assertIsOpen();
    }
}
