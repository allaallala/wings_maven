package edu.msstate.nsparc.wings.integration.tests.trade.ataaRtaaEnrollments;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AtaaRtaaEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create ATAA-RTAA Enrollment with a Re-Employment job, open its preview, close it and check, that Enrollment Details Screen is shown.
 * Created by a.vnuchko on 22.10.2015.
 */

@TestCase(id = "WINGS-10870")
public class TC_15_23_ATAA_RTAA_Reemployment_Preview extends BaseWingsTest {

    public void main() {
        Integer agoDate = 45;
        info("Precondition: Create ATAA-RTAA Enrollment with a Re-Employment Job");
        AtaaRtaaEnrollment atrta = new AtaaRtaaEnrollment(PetitionType.RTAA);
        atrta.setUiExhaustionDate(CommonFunctions.getDaysAgoDate(agoDate));
        TradeEnrollmentSteps.createAtaaRtaaEnrollment(atrta, Roles.TRADEDIRECTOR);

        AtaaRtaaEnrollmentSteps.openAtaaRtaaEnrollmentDetailPage(atrta, Roles.STAFF);

        logStep("Select any Re-Employment Job  in the 'Qualifying Re-Employment' table");
        AtaaRtaaEnrollmentDetailsForm detailsPage = new AtaaRtaaEnrollmentDetailsForm();
        detailsPage.openPreview();

        logStep("Click Close Preview");
        detailsPage.click(BaseWingsForm.BaseButton.CLOSE_PREVIEW_PAGE);

        logResult("The ATAA-RTAA Enrollment Detail Screen is shown");
        //new AtaaRtaaEnrollmentDetailsForm().assertIsOpen();
    }
}
