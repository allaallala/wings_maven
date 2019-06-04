package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, add education record with currently attending "No", save changes. Check, that changes are applied.
 * Created by a.vnuchko on 14.11.2016.
 */

@TestCase(id = "WINGS-11170")
public class TC_31_21_EP_Education_Add_Attending_No extends TC_31_20_EP_Education_Add_Attending_Yes {

    public void main(){
        mainSteps(Constants.FALSE);
    }
}
