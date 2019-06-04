package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ManageWageSubsidiesForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create ATAA-RTAA Enrollment with several Wage Subsidies, open manage subsidies form. Fill out some search criteria. Clear all fields. Check that data is disappeared.
 * Created by a.vnuchko on 30.10.2015.
 */

@TestCase(id = "WINGS-11044")
public class TC_23_10_Wage_Subsidies_Search_Clear extends BaseWingsTest {
    public static final String STATE_INITIAL = "Not Yet Entered";
    public static final String STATE_PENDING = "Pending";
    public static final String STATE_SENT = "Sent";
    public static final String STATE_NOTPAYABLE = "Not Payable";
    public static final String WORK_STATUS = "On Vacation";

    public void main() {
        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        ManageWageSubsidiesForm subsPage = openManageWageSubsidiesForm(atrt, "Other");

        logStep("Click the [Clear Form] button");
        subsPage.clickButton(Buttons.Clear);

        logResult("All the entries made in the search criteria fields are cleared");
        subsPage.checkFieldsCleared();
    }

    /**
     * Method includes some step for opening manage wage subsidies form
     * @param atrt - ataa/rtaa enrollment
     * @param type - Wage Subsidies or Wage Subsidies Payments
     * @return new Manage Wage Subsidies Form.
     */
    public ManageWageSubsidiesForm openManageWageSubsidiesForm(AtaaRtaaEnrollment atrt, String type) {
        info("Precondition: Create ATAA-RTAA Enrollment with several Wage Subsidies");
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(atrt, Roles.TRADEDIRECTOR);

        info("Search for created ataa/rtaa enrollment and open its participantSSDetails page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(atrt, Roles.TRADEDIRECTOR);

        logStep("Expand Wage Subsidies");
        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();

        if ("Payments".equals(type)) {
            detailsPage.openWagePaymentsForm();
        } else {
            detailsPage.openWageManage();

            logStep("Fill out some search criteria fields with any data");
            ManageWageSubsidiesForm subsPage = new ManageWageSubsidiesForm();
            subsPage.selectStatus(STATE_INITIAL);
        }
        return new ManageWageSubsidiesForm();
    }
}
