package edu.msstate.nsparc.wings.integration.models.administrative;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * This class is used for checking [Staff Home Screen]
 * Created by a.vnuchko on 13.09.2016.
 */
public class StaffHome {
    //User permissions
    private Boolean shcServiceUnresulted;
    private Boolean shsStaffUnresulted;
    private Boolean shsApproveReject;
    private static final String SHC_SERVICE_UNRESULTED = "shcServiceUnresulted";
    private static final String SHC_STAFF_UNRESULTED = "shsStaffUnresulted";
    private static final String SHC_APPROVE_REJECT = "shsApproveReject";

    public StaffHome(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        shcServiceUnresulted = Boolean.valueOf(prop.getProperty(SHC_SERVICE_UNRESULTED));
        shsStaffUnresulted = Boolean.valueOf(prop.getProperty(SHC_STAFF_UNRESULTED));
        shsApproveReject = Boolean.valueOf(prop.getProperty(SHC_APPROVE_REJECT));
    }

    public Boolean getServiceUnresultedReferrals() {
        return shcServiceUnresulted;
    }

    public Boolean getStaffUnresultedReferrals() {
        return shsStaffUnresulted;
    }
}
