package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10868")
public class TC_15_21_ATAA_RTAA_Change_Status_To_Ineligible extends BaseWingsTest {

    private static final String DETERMINATION_DATE_ERROR = "ATAA/RTAA Eligibility Determination Date is required.";

    public void main() {

        AtaaRtaaEnrollment enrollment = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        TradeEnrollmentSteps.createAtaaRtaaWithNoStatus(enrollment, Roles.TRADEDIRECTOR);

        logStep("Log in as Trade Director and open ATAA/RTAA Enrollment Edit page");
        AtaaRtaaEnrollmentSteps.openAtaaRtaaEditPage(enrollment, Roles.TRADEDIRECTOR);

        logStep("Change status to Ineligible, don't enter any value in the Eligibility Determination date");
        AtaaRtaaEnrollmentEditForm editForm = new AtaaRtaaEnrollmentEditForm();
        enrollment.setEligible(Constants.FALSE);
        editForm.selectStatus(enrollment);

        logStep("Click 'Save Changes' and check validation error is displayed");
        editForm.clickButton(Buttons.Save);
        assertEquals("Validation error message assertion failed!", DETERMINATION_DATE_ERROR, editForm.getDeterminationDateError());
    }
}
