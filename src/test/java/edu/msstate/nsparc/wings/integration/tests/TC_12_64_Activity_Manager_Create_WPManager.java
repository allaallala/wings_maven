package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create Wagner-Peyser Manager user and check, that it's created succefully
 * Created by a.vnuchko on 06.04.2017.
 */

@TestCase(id = "WINGS-10797")
public class TC_12_64_Activity_Manager_Create_WPManager extends TC_12_62_Activity_Manager_Create_Lver {

    public void main(){
        String userType = "MDES Manager";
        protectedSteps(userType);
    }
}
