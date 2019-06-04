package edu.msstate.nsparc.wings.integration.tests.trade.notes;

import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant participantSSDetails information, click the [Notes] button and click [Print] button, check that correct version is printed.
 * Created by a.vnuchko on 18.11.2015.
 */

@TestCase(id = "WINGS-10942")
public class TC_18_20_Notes_Popup_Print extends TC_18_19_Notes_Popup_Printable_Version {

    public void main(){
        openPrintableVersion();

        logResult("The Print Notes Screen is shown. Correct Printable version should be printed");
    }

}
