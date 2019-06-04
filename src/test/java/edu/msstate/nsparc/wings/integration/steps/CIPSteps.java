package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.everify.EverifySearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobSearch.JobSearchSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralResultForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralSearchForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingSearchForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.forms.wiaEnrollment.WIAEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.forms.youthGoals.YouthGoalsSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import framework.Logger;

/**
 * This class is used for storing methods needed for checking contextual information pane.
 * Created by a.vnuchko on 29.09.2015.
 */
public class CIPSteps extends BaseWingsSteps {

    /**
     * Validate data in the contextual information pane.
     * @param module - wia/ wp/ trade
     * @param form - search form.
     * @param participant - participant
     * @param wia - WIA entity used for wia checkings
     * @param trEnrl - Trade Enrollment entity used for trade enrollment checkings.
     */
    private static void ifSearchRequired(String module, BaseWingsForm form, Participant participant, WIAEnrollment wia, TradeEnrollment trEnrl) {
        form.selectParticipant(participant);
        form.validateWagnerPeyserContextualInformationPane(participant);
        form.clickButton(Buttons.Search);
        form.openFirstSearchResult();
        if (Constants.WIOA.equals(module)) {
            form.validateWiaContextualInformationPane(wia);
        } else if (Constants.WP.equals(module)) {
            form.validateWagnerPeyserContextualInformationPane(participant);
        } else {
            form.validateTradeContextualInformationPane(trEnrl);
        }
    }
    /**
     * This method is used for validating contextual information pane.
     * @param type - all types of WIA, Trade, Wagner-Peyser module.
     * @param module - WIA, Trade, Wagner-Peyser.
     * @param participant - any participant
     * @param wia - entity of WIA
     * @param trEnrl - entiry of Trade Enrollment
     */
    public static void validateCIP(String type, String module, Participant participant, WIAEnrollment wia, TradeEnrollment trEnrl) {
        Logger.getInstance().info("Log in WINGS as Staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        Logger.getInstance().info("Validate that Info Pane is displayed on all participantSSDetails pages from " + module + " - " + type);
        switch (module) {
            case Constants.WIOA:
                validateCIPWIA(type, participant, wia, trEnrl);
                break;
            case Constants.WP:
                validateCIPWP(type, participant, wia, trEnrl);
                break;
            default:
                validateCIPTrade(type, participant, wia, trEnrl);
                break;
        }
        BaseNavigationSteps.logout();
    }

    /**
     * Validate contextual information pane for the Wagner-peyser block
     * @param type - the module name
     * @param participant - participant
     * @param wia - WIA Enrollment
     * @param trEnrl - Trade Enrollment
     */
    public static void validateCIPWP(String type, Participant participant, WIAEnrollment wia, TradeEnrollment trEnrl) {
        String module = Constants.WP;
        switch (type) {
            case Constants.EVERIFY:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_E_VERIFY);
                BaseWingsSteps.popClick(Popup.Search);
                EverifySearchForm eviry = new EverifySearchForm();
                ifSearchRequired(module, eviry, participant, wia, trEnrl);
                break;
            case Constants.REFERRALS:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRALS);
                BaseWingsSteps.popClick(Popup.Search);
                ReferralSearchForm reff = new ReferralSearchForm();
                ifSearchRequired(module, reff, participant, wia, trEnrl);
                break;
            case Constants.REFERRAL_RESULT:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WP_REFERRAL_RESULTING);
                ReferralResultForm resultPage = new ReferralResultForm();
                ifSearchRequired(module, resultPage, participant, wia, trEnrl);
                break;
            case Constants.PARTP_SERVICE_ENRL:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
                BaseWingsSteps.popClick(Popup.Search);
                ParticipantEnrollmentSearchForm enrlPage = new ParticipantEnrollmentSearchForm();
                ifSearchRequired(module, enrlPage, participant, wia, trEnrl);
                break;
            case Constants.IEP:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS);
                BaseWingsSteps.popClick(Popup.Search);
                IndividualEmploymentPlanSearchForm iepPage = new IndividualEmploymentPlanSearchForm();
                ifSearchRequired(module, iepPage, participant, wia, trEnrl);
                break;
            case Constants.ASSESSMENT_WP:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);
                BaseWingsSteps.popClick(Popup.Search);
                AssessmentSearchForm assmWpPage = new AssessmentSearchForm();
                ifSearchRequired(module, assmWpPage, participant, wia, trEnrl);
                break;
            default:
                Logger.getInstance().info("Try to input correct Wagner-Peyser type");
                break;
        }
    }

    /**
     * Validate contextual information pane for the WIA block
     * @param type - the module name
     * @param participant - participant
     * @param wia - WIA Enrollment
     * @param trEnrl - Trade Enrollment
     */
    public static void validateCIPWIA(String type, Participant participant, WIAEnrollment wia, TradeEnrollment trEnrl) {
        String module = Constants.WIOA;
        switch (type) {
            case Constants.WIA_ENROLLMENT:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_ENROLLMENT);
                BaseWingsSteps.popClick(Popup.Search);
                WIAEnrollmentSearchForm wiaPage = new WIAEnrollmentSearchForm();
                ifSearchRequired(module, wiaPage, participant, wia, trEnrl);
                break;
            case Constants.WIA_TRAINING:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
                BaseWingsSteps.popClick(Popup.Search);
                WIATrainingSearchForm wiaTraining = new WIATrainingSearchForm();
                ifSearchRequired(module, wiaTraining, participant, wia, trEnrl);
                break;
            case Constants.YOUTH_GOALS:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_YOUTH_GOALS);
                BaseWingsSteps.popClick(Popup.Search);
                YouthGoalsSearchForm youthPage = new YouthGoalsSearchForm();
                ifSearchRequired(module, youthPage, participant, wia, trEnrl);
                break;
            case Constants.ASSESSMENT_WIA:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);
                BaseWingsSteps.popClick(Popup.Search);
                AssessmentSearchForm assmWiaPage = new AssessmentSearchForm();
                ifSearchRequired(module, assmWiaPage, participant, wia, trEnrl);
                break;
            default:
                Logger.getInstance().info("Try to input correct WIA type");
                break;
        }
    }

    /**
     * Validate contextual information pane for the Trade block
     * @param type - the module name
     * @param participant - participant
     * @param wia - WIA Enrollment
     * @param trEnrl - Trade Enrollment
     */
    public static void validateCIPTrade(String type, Participant participant, WIAEnrollment wia, TradeEnrollment trEnrl) {
        String module = Constants.TRADE;
        switch (type) {
            case Constants.TRADE_ENROLLMENTS:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS);
                BaseWingsSteps.popClick(Popup.Search);
                TradeEnrollmentSearchForm trPage = new TradeEnrollmentSearchForm();
                ifSearchRequired(module, trPage, participant, wia, trEnrl);
                break;
            case Constants.ATRT_ENRLMS:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS);
                BaseWingsSteps.popClick(Popup.Search);
                AtaaRtaaEnrollmentSearchForm atrtPage = new AtaaRtaaEnrollmentSearchForm();
                ifSearchRequired(module, atrtPage, participant, wia, trEnrl);
                break;
            case Constants.TRADE_TRAINING:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_TRAINING);
                BaseWingsSteps.popClick(Popup.Search);
                TradeTrainingSearchForm searchPage = new TradeTrainingSearchForm();
                ifSearchRequired(module, searchPage, participant, wia, trEnrl);
                break;
            case Constants.TRAINING_WAIVERS:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS);
                BaseWingsSteps.popClick(Popup.Search);
                TrainingWaiverSearchForm twPage = new TrainingWaiverSearchForm();
                ifSearchRequired(module, twPage, participant, wia, trEnrl);
                break;
            case Constants.JOB_SEARCH:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_JOB_SEARCH);
                BaseWingsSteps.popClick(Popup.Search);
                JobSearchSearchForm jbPage = new JobSearchSearchForm();
                ifSearchRequired(module, jbPage, participant, wia, trEnrl);
                break;
            case Constants.RELOCATION:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);
                BaseWingsSteps.popClick(Popup.Search);
                RelocationSearchForm relPage = new RelocationSearchForm();
                ifSearchRequired(module, relPage, participant, wia, trEnrl);
                break;
            case Constants.ASSESSMENT_TRADE:
                new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);
                BaseWingsSteps.popClick(Popup.Search);
                AssessmentSearchForm asmPage = new AssessmentSearchForm();
                asmPage.validateWPTradeCip(participant, trEnrl);
                break;
            default:
                Logger.getInstance().info("Try to input correct trade type");
                break;
        }
    }
}
