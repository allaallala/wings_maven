package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create some relocations, edit it - change status to approved. Check it.
 * Created by a.vnuchko on 28.01.2016.
 */

@TestCase(id = "WINGS-10782")

public class TC_12_18_Relocation_Edit_Approved extends TC_12_09_Relocation_Edit_Denied_Status {

    public void main(){
        createRelocationEditStatus(Constants.TRUE);
    }
}
