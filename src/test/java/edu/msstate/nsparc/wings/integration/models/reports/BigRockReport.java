package edu.msstate.nsparc.wings.integration.models.reports;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Describes a big rock report class. It is used for checking user roles.
 * Created by a.vnuchko on 19.10.2016.
 */
public class BigRockReport {

    private Boolean bgReport;
    private static final String BG_REPORT = "repBigRock";

    /**
     * Main constructor
     * @param role - new user role.
     */
    public BigRockReport(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        bgReport = Boolean.valueOf(prop.getProperty(BG_REPORT));
    }

    public Boolean getCreate() {
        return bgReport;
    }
}
