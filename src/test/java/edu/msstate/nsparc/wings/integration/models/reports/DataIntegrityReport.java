package edu.msstate.nsparc.wings.integration.models.reports;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Describes data integrity report entity
 * Created by a.vnuchko on 20.10.2016.
 */
public class DataIntegrityReport {

    private Boolean dgAtaaReport;
    private Boolean dgEmployerReport;
    private Boolean dgJobOrderReport;
    private Boolean dgLdap;
    private Boolean dgParticipantReport;
    private Boolean dgReferralReport;
    private Boolean dgTradeEnrlReport;
    private Boolean dgTrainingWaiverReport;
    private Boolean dgWiaReport;
    private static final String ATAA_REPORT = "repIntegrityAtaa";
    private static final String EMPLOYER_REPORT = "repIntegrityEmployer";
    private static final String JOB_ORDER_REPORT = "repIntegrityOrders";
    private static final String LDAP = "repIntegrityLdap";
    private static final String PARTICIPANT_REPORT = "repIntegrityPartip";
    private static final String REFERRAL_REPORT = "repIntegrityRef";
    private static final String TRADE_ENROLLMENT_REPORT = "repIntegrityTrade";
    private static final String TRAINING_WAIVER_REPORT = "repIntegrityTrWaiver";
    private static final String WIA_REPORT = "repIntegrityWia";

    /**
     * Constructor for the data integrity report
     * @param role - current user role
     */
    public DataIntegrityReport(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        dgAtaaReport = Boolean.valueOf(prop.getProperty(ATAA_REPORT));
        dgEmployerReport = Boolean.valueOf(prop.getProperty(EMPLOYER_REPORT));
        dgJobOrderReport = Boolean.valueOf(prop.getProperty(JOB_ORDER_REPORT));
        dgLdap = Boolean.valueOf(prop.getProperty(LDAP));
        dgParticipantReport = Boolean.valueOf(prop.getProperty(PARTICIPANT_REPORT));
        dgReferralReport = Boolean.valueOf(prop.getProperty(REFERRAL_REPORT));
        dgTradeEnrlReport = Boolean.valueOf(prop.getProperty(TRADE_ENROLLMENT_REPORT));
        dgTrainingWaiverReport = Boolean.valueOf(prop.getProperty(TRAINING_WAIVER_REPORT));
        dgWiaReport = Boolean.valueOf(prop.getProperty(WIA_REPORT));
    }

    public Boolean getDgEmployerReport() {
        return dgEmployerReport;
    }

    public Boolean getDgJobOrderReport() {
        return dgJobOrderReport;
    }

    public Boolean getDgParticipantReport() {
        return dgParticipantReport;
    }

    public Boolean getDgReferralReport() {
        return dgReferralReport;
    }

    public Boolean getDgWiaReport() {
        return dgWiaReport;
    }
}
