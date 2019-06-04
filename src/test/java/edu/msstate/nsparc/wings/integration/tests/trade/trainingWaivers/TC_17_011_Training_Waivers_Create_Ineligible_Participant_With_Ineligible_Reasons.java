package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.xray.info.TestCase;

/**
 * Created by a.vnuchko on 07.07.2015.
 * Creating of ineligible participant with ineligible reasons (Create Training Waiver:- with all marked check boxes)
 */

@TestCase(id = "WINGS-10904")
public class TC_17_011_Training_Waivers_Create_Ineligible_Participant_With_Ineligible_Reasons extends TC_17_01_Training_Waivers_Create_Ineligible_Participant_With_Ineligible_Reasons {

    @Override
    public void makeActions(String type) {
        String waiverType = "all";
        super.makeActions(waiverType);
    }
}
