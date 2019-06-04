package edu.msstate.nsparc.wings.integration.tests.participant.welcomePage;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Checking the My Account link/button for logged and logged out users
 * Created by a.korotkin on 09.11.2016.
 */

@TestCase(id = "WINGS-11178")
public class TC_32_03_WP_My_Account extends BaseWingsTest {
    private String titleOfPage = "Online Services";
    private String url = "urlLoginPage";

    public void main(){
        //TODO login/logout tests should be updated
        /*logStep("Check 'My Account'");
        LoginForm loginForm = new LoginForm();
        loginForm.clickMyAccountLinkEmployer();
        softAssertEquals(loginForm.getNameOfLoginPage(), titleOfPage, "Incorrect page is displayed");
        loginForm.clickMDESLogo();
        loginForm.clickMyAccountLinkCandidate();
        softAssertEquals(loginForm.getNameOfLoginPage(), titleOfPage, "Incorrect page is displayed");
        loginForm.clickMDESLogo();
        loginForm.clickMyAccountButton();
        softAssertEquals(loginForm.getNameOfLoginPage(), titleOfPage, "Incorrect page is displayed");

        logStep("Create Participant and log in to the system");
        Participant participant = precondition();
        loginForm.loginParticipant();

        logStep("Check 'My Account' for logged users");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        navigate(global.getProperty(url));
        loginForm.clickMyAccountLinkEmployer();
        softAssertEquals(participantHomePage.getParticipantName(), participant.getFullName(), "Incorrect page is displayed");
        navigate(global.getProperty(url));
        loginForm.clickMyAccountLinkCandidate();
        softAssertEquals(participantHomePage.getParticipantName(), participant.getFullName(), "Incorrect page is displayed");
        navigate(global.getProperty(url));
        loginForm.clickMyAccountButton();
        softAssertEquals(participantHomePage.getParticipantName(), participant.getFullName(), "Incorrect page is displayed");*/
    }

    /*protected Participant precondition(){
        info("Create self participant to work with");
        BaseNavigationFunctions.loginParticipant();

        Participant participant = new Participant();
        ParticipantCreationForm creationForm = new ParticipantCreationForm(Constants.PARTICIPANT_SS);
        creationForm.createCompleteSSParticipant(participant);
        return participant;
    }*/
}
