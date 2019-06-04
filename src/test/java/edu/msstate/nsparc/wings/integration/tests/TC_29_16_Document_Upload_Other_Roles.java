package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check document upload functionality for different user roles: trade administrator, rapid response administrator
 * everify administrator, WIOA administrator, WIOA administrator PLUS, project code administrator, DVOP veteran, LVER, executive.
 * Created by a.vnuchko on 13.09.2016.
 */

@TestCase(id = "WINGS-11148")
public class TC_29_16_Document_Upload_Other_Roles extends TC_29_17_Document_Upload_Admin_AD {
    String firstFile = "TestPdfOne.pdf";
    String secondFile = "TestPdfTwo.pdf";
    String thirdFile = "TestPdfThree.pdf";
    String quarterFile = "TestPdfFour.pdf";
    public void main() {

        init(); //Is used only for those roles, who can upload documents.
        User user = new User(Roles.TRADEDIRECTOR);
        docSteps(user, firstFile);

        user.setNewUser(Roles.RRADMIN);
        docSteps(user, firstFile);

        init();
        user.setNewUser(Roles.EVERIFY);
        docSteps(user, secondFile);

        init();
        user.setNewUser(Roles.WIOA);
        docSteps(user, thirdFile);

        init();
        user.setNewUser(Roles.WIOAPLUS);
        docSteps(user, thirdFile);

        user.setNewUser(Roles.PCADMIN);
        docSteps(user, thirdFile);

        init();
        user.setNewUser(Roles.DVOPVETERAN);
        docSteps(user, quarterFile);

        user.setNewUser(Roles.LVER);
        docSteps(user, quarterFile);

        user.setNewUser(Roles.EXECUTIVE);
        docSteps(user, quarterFile);
    }
}
