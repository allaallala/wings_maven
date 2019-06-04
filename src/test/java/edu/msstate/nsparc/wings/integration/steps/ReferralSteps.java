package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralCreationForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralEditForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;

public class ReferralSteps extends BaseNavigationSteps {

    public static void createReferral(Participant participant, JobOrder jobOrder, User user) {
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, user);
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Create);
        ReferralCreationForm referralCreationForm = new ReferralCreationForm();
        referralCreationForm.selectParticipantByRole(user, participant);
        referralCreationForm.selectJobOrder(jobOrder);
        referralCreationForm.clickAgree();
        referralCreationForm.inputCreationDate(CommonFunctions.getCurrentDate());
        referralCreationForm.createReferral();
        referralCreationForm.clickButton(Buttons.Done);
        logout();
    }

    public static void createReferralWithYesterdayJobOpenDate(Participant participant, JobOrder jobOrder, User user) {
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        EverifySteps.eVerifyParticipant(participant, user);
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());
        jobOrder.setOpenDate(CommonFunctions.getYesterdayDate());
        JobOrderSteps.editJobOrderOpenDate(jobOrder);
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Create);
        ReferralCreationForm referralCreationForm = new ReferralCreationForm();
        referralCreationForm.selectParticipantByUser(user, participant);
        referralCreationForm.selectJobOrder(jobOrder);
        referralCreationForm.clickAgree();
        referralCreationForm.inputCreationDate(CommonFunctions.getCurrentDate());
        referralCreationForm.createReferral();
        referralCreationForm.clickButton(Buttons.Done);
        logout();
    }

    public static void createReferralWithSettingID(Participant participant, JobOrder jobOrder, Roles role) {
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        String jobID = JobOrderSteps.createJobOrder(jobOrder, true, false);
        jobOrder.setJobID(jobID);
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Create);
        ReferralCreationForm referralCreationForm = new ReferralCreationForm();
        referralCreationForm.selectParticipant(participant);
        referralCreationForm.selectJobOrder(jobOrder);
        referralCreationForm.clickAgree();
        referralCreationForm.inputCreationDate(CommonFunctions.getCurrentDate());
        referralCreationForm.createReferral();
        referralCreationForm.clickButton(Buttons.Done);
        logout();
    }

    public static void createReferralSameParticipant(Participant participant, JobOrder jobOrder, Roles role) {
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder.getEmployer(), jobOrder.getJobTitle());
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Create);
        ReferralCreationForm referralCreationForm = new ReferralCreationForm();
        referralCreationForm.selectParticipant(participant);
        referralCreationForm.selectJobOrder(jobOrder);
        referralCreationForm.clickAgree();
        referralCreationForm.inputCreationDate(CommonFunctions.getCurrentDate());
        referralCreationForm.createReferral();
        referralCreationForm.clickButton(Buttons.Done);
        logout();
    }

    public static void searchReferral(Participant participant) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Search);
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);
        CustomAssertion.softAssertEquals(referralSearchForm.getFirstName(), participant.getFirstName(),"Participant First Name assert fail");
        CustomAssertion.softAssertEquals(referralSearchForm.getLastName(), participant.getLastName(),"Participant Last Name assert fail");
        CustomAssertion.softAssertEquals(referralSearchForm.getSSNFromTitle(), participant.getSsn().substring(5), "Participant SSN assert fail");
        logout();
    }

    public static void searchReferralAndOpen(Participant participant, Employer employer) {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS, Popup.Search);
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);
        referralSearchForm.selectJobOrder(employer);
        referralSearchForm.clickButton(Buttons.Search);
        referralSearchForm.openFirstSearchResult();
    }

    public static void openReferralEditForm(Participant participant) {
        ReferralSearchForm referralSearchForm = new ReferralSearchForm();
        referralSearchForm.performSearch(participant);
        referralSearchForm.openFirstSearchResult();
        ReferralDetailsForm detailsPage = new ReferralDetailsForm();
        detailsPage.clickButton(Buttons.Edit);
    }

    public static void setUnresultedReferralCreationDate(Participant participant) {
        String creationDate = "06/09/2009";
        BaseWingsSteps.logInAs(Roles.STAFF);
        navigateToReferralSearch();
        ReferralSteps.openReferralEditForm(participant);
        ReferralEditForm referralEditForm = new ReferralEditForm();
        referralEditForm.inputCreationDate(creationDate);
        referralEditForm.clickButton(Buttons.Save);
        referralEditForm.clickButton(Buttons.Create);
        logout();
    }
}
