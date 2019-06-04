package edu.msstate.nsparc.wings.integration.tests.callIn;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.callIn.CallInCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10675")
public class TC_07_09_Create_Call_In_Veteran_Not_Driver extends TC_07_01_Create_Call_In {

    String city = "Bude";
    String zip = "39630";
    String lineOne = "Main St.";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        participant.getAddress().setCity(city);
        participant.getAddress().setZipCode(zip);
        participant.getAddress().setLineOne(lineOne);
        ParticipantCreationSteps.createVeteranParticipantNotDriver(participant);
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        jobOrder.setEmployer(EmployerSteps.setEmployerValidAddress(jobOrder.getEmployer()));
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_CALL_INS, Popup.Create);

        logStep("Select 'Only Veterans' from radio buttons Show");
        CallInCreationForm callInCreationForm = new CallInCreationForm();
        callInCreationForm.selectJobOrder(jobOrder);
        callInCreationForm.selectVeterans(Constants.TRUE);

        logStep("Participants not meeting Drivers License Requirements ->Search");
        callInCreationForm.selectDriverLicenseRequirement(Constants.TRUE);
        callInCreationForm.clickButton(Buttons.Search);

        repeatedSteps(jobOrder);
    }
}
