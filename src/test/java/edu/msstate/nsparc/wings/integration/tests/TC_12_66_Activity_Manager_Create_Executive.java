package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create Executive user and check, that it's created succefully.
 * Created by a.vnuchko on 06.04.2017.
 */

@TestCase(id = "WINGS-10799")
public class TC_12_66_Activity_Manager_Create_Executive extends TC_12_62_Activity_Manager_Create_Lver {

    public void main(){
        String userType = "Executive (View Only)";
        protectedSteps(userType);
    }
}
