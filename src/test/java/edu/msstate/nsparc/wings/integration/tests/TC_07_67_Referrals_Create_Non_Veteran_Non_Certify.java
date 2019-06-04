package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;


// Author: d.poznyak
@TestCase(id = "WINGS-10695")
public class TC_07_67_Referrals_Create_Non_Veteran_Non_Certify extends TC_07_55_Referrals_Create_Veteran_Non_Certify {

    public void main() {

        info("Init test data");
        initData(Constants.TRUE, Constants.FALSE);
        JobOrderSteps.createJobOrder(jobOrder, Constants.TRUE, Constants.TRUE, Constants.FALSE);
        ParticipantSqlFunctions.setReleaseNonVeteranDate(jobOrder);

        mainSteps();
    }
}
