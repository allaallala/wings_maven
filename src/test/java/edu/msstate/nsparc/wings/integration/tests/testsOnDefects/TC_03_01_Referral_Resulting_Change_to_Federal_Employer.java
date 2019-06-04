package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_07_90_Referral_Resulting;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10556")
public class TC_03_01_Referral_Resulting_Change_to_Federal_Employer extends BaseWingsTest {

    private static final String REFERRAL_RESULT = "Hired";


    public void main() {

        /*
         * 1. Create participant
         * 2. Create non-federal employer
         * 3. Create job order
         * 4. Apply to job order
         * 5. Change employer to federal contractor
         * 6. Result referral
         */
        logStep("Create participant");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();

        logStep("Create non-feneral employer");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);

        logStep("Create job order");
        String jobID = JobOrderSteps.createJobOrder(jobOrder, true, true);

        logStep("Apply for job order");
        jobOrder.setJobID(jobID);
        JobOrderSteps.applyForJobOrder(participant, jobOrder);

        logStep("Edit employer: change to federal contractor");
        EmployerSteps.editFederalContractor(jobOrder.getEmployer());

        logStep("Result referral");
        TC_07_90_Referral_Resulting referralResulting = new TC_07_90_Referral_Resulting();
        referralResulting.resultReferralAndCheck(participant, REFERRAL_RESULT, CommonFunctions.getCurrentDate());

    }

}
