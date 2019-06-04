package edu.msstate.nsparc.wings.integration.models.wagnerPeyser;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Referrals class
 * Created by a.korotkin on 03.06.2016.
 */
public class Referrals {
    private Boolean referralsCreate;
    private Boolean referralsView;
    private Boolean referralsViewAuditButton;
    private Boolean referralsViewDeleteButton;
    private Boolean referralsViewPrint;
    private Boolean referralsViewDocs;
    private Boolean referralsEdit;
    private Boolean referralsResultEdit;
    private static final String REFERRAL_CREATE = "referralCreate";
    private static final String REFERRAL_VIEW = "referralView";
    private static final String REFERRAL_VIEW_AUDIT = "referralViewAuditButton";
    private static final String REFERRAL_VIEW_DELETE = "referralViewDeleteButton";
    private static final String REFERRAL_VIEW_PRINT = "referralViewPrint";
    private static final String REFERRAL_VIEW_DOCS = "referralViewDocs";
    private static final String REFERRAL_EDIT = "referralEdit";
    private static final String REFERRAL_RESULT_EDIT = "referralResultEdit";

    /**
     * Constructor
     * @param role - user role
     */
    public Referrals(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));  //String.format(BaseEntity.ROLE_PATH, role.toString())
        referralsCreate = prop.getBooleanProperty(REFERRAL_CREATE);
        referralsView = prop.getBooleanProperty(REFERRAL_VIEW);
        referralsViewAuditButton = prop.getBooleanProperty(REFERRAL_VIEW_AUDIT);
        referralsViewDeleteButton = prop.getBooleanProperty(REFERRAL_VIEW_DELETE);
        referralsViewPrint = prop.getBooleanProperty(REFERRAL_VIEW_PRINT);
        referralsViewDocs = prop.getBooleanProperty(REFERRAL_VIEW_DOCS);
        referralsEdit = prop.getBooleanProperty(REFERRAL_EDIT);
        referralsResultEdit = prop.getBooleanProperty(REFERRAL_RESULT_EDIT);
    }

    public Boolean getReferralsCreate() {
        return referralsCreate;
    }

    public Boolean getAuditButton() {
        return referralsViewAuditButton;
    }

    public Boolean getReferralsView() {
        return referralsView;
    }

    public Boolean getReferralsEdit() {
        return referralsEdit;
    }

    public Boolean getReferralsResultEdit() {
        return referralsResultEdit;
    }

    public Boolean getReferralsPrint() {
        return referralsViewPrint;
    }

    public Boolean getReferralsDelete() {
        return referralsViewDeleteButton;
    }

    public Boolean getReferralsViewDocs() {
        return referralsViewDocs;
    }
}
