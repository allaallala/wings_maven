package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


/**
 * Open and fill out career readiness certification form, click [Cancel] button, but don't confirm it. Check, that form is displayed and contains data entered.
 * Created by a.vnuchko on 21.09.2015.
 */

@TestCase(id = "WINGS-10970")
public class TC_20_02_CRC_Create_Cancel_No extends BaseWingsTest {

    public void main(){


        info("Generate some data for CRC");
        CareerReadinessCertification crc = CRCSteps.generateCrcCommonData();

        CRCSteps.openFillCreationForm(crc, Roles.STAFF);

        logStep("Click the [Cancel] button");
        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.clickButton(Buttons.Cancel);

        logStep("Click [No] button in the pop up");
        creationPage.areYouSure(Popup.No);

        logResult("The Career Readiness Certification Creation Screen is shown. Fields contain the correct  information.");

        CustomAssertion.softAssertContains(new CareerReadinessCertificationCreationForm().getText(BaseWingsForm.BaseOtherElement.PARTICIPANT), crc.getParticipant().getFirstName(), "Incorrect participant first name");
        CustomAssertion.softAssertContains(creationPage.getDateAdminValue(), crc.getDateAdministired(), "Incorrect date administred");
        CustomAssertion.softAssertContains(creationPage.getSelectedMath(), crc.getAppliedMathematics().getFunctionalArea(), "Incorrect math functional area");
        CustomAssertion.softAssertContains(creationPage.getSelectedLoc(), crc.getLocatingInformation().getFunctionalArea(), "Incorrect loc functional area");
        CustomAssertion.softAssertContains(creationPage.getSelectedRead(), crc.getReadingForInformation().getFunctionalArea(), "Incorrect reading functional area");
    }
}
