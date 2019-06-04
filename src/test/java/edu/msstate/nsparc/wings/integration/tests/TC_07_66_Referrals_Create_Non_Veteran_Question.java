package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10694")
public class TC_07_66_Referrals_Create_Non_Veteran_Question extends TC_07_54_Referrals_Create_Veteran_Question {

    public void main() {

        info("Init test data");
        initData(Constants.TRUE, Constants.FALSE);
        JobOrderSteps.createJobOrder(jobOrder, Constants.TRUE, Constants.TRUE, Constants.TRUE);
        ParticipantSqlFunctions.setReleaseNonVeteranDate(jobOrder);

        mainSteps();
    }
}
