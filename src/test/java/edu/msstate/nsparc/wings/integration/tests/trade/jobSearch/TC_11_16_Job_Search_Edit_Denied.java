package edu.msstate.nsparc.wings.integration.tests.trade.jobSearch;

import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create some job searches, edit status to denied and save changes. Check it.
 * Created by a.vnuchko on 28.01.2016.
 */

@TestCase(id = "WINGS-10747")
public class TC_11_16_Job_Search_Edit_Denied extends TC_11_15_Job_Search_Edit_Approved {

    public void main(){
        editJobSearchSave(false);
    }
}
