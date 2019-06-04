package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create Veteran Representative DVOP user and check, that it's created succefully.
 * Created by a.vnuchko on 06.04.2017.
 */

@TestCase(id = "WINGS-10796")
public class TC_12_63_Activity_Manager_Create_DVOP extends TC_12_62_Activity_Manager_Create_Lver {

    public void main(){
        String userType = "Veteran Representative (DVOP)";
        protectedSteps(userType);
    }

}
