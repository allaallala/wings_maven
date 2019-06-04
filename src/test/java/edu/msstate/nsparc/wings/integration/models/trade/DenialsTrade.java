package edu.msstate.nsparc.wings.integration.models.trade;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Denials for trade (represents denial/appeal in the trade enrollment)
 * Created by a.vnuchko on 31.08.2016.
 */
public class DenialsTrade {
    //User permissions
    private Boolean dftCreateDenial;
    private Boolean dftEditDenial;
    private Boolean dftAppealDenial;
    private Boolean dftEditDenialAppeal;
    private Boolean denialsView;
    private static final String DFT_CREATE_DENIAL = "dftCreateDenial";
    private static final String DFT_EDIT_DENIAL = "dftEditDenial";
    private static final String DFT_APPEAL_DENIAL = "dftAppealDenial";
    private static final String DFT_EDIT_DENIAL_APPEAL = "dftEditDenialAppeal";
    private static final String DFT_DENIAL_VIEW = "denialsView";

    /**
     * Constructor with specific role
     * @param role - current user role
     */
    public DenialsTrade(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        dftCreateDenial = Boolean.valueOf(prop.getProperty(DFT_CREATE_DENIAL));
        dftEditDenial = Boolean.valueOf(prop.getProperty(DFT_EDIT_DENIAL));
        dftAppealDenial = Boolean.valueOf(prop.getProperty(DFT_APPEAL_DENIAL));
        dftEditDenialAppeal = Boolean.valueOf(prop.getProperty(DFT_EDIT_DENIAL_APPEAL));
        denialsView = Boolean.valueOf(prop.getProperty(DFT_DENIAL_VIEW));
    }

    public Boolean getDftCreateDenial() {
        return dftCreateDenial;
    }

    public Boolean getDftEditDenial() {
        return dftEditDenial;
    }

    public Boolean getDftAppealDenial() {
        return dftAppealDenial;
    }

    public Boolean getDftEditDenialAppeal() {
        return dftEditDenialAppeal;
    }

    public Boolean getDenialsView() {
        return denialsView;
    }
}
