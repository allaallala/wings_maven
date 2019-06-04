package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check functionality of the career readiness certification for administrator,area director and DVOP veteran roles.
 * Created by a.vnuchko on 09.02.2016.
 */

@TestCase(id = "WINGS-11062")
public class TC_24_04_CRC_Role_Admin_AD_DVOPVet extends BaseWingsTest {
    protected CareerReadinessCertification crc;

    public void main() {

        //Role administrator
        User user = new User(Roles.ADMIN);
        commonCrcSteps(user);

        //Role area director
        user.setNewUser(Roles.RRADMIN);
        commonCrcSteps(user);

        //Role DVOP Veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonCrcSteps(user);
    }

    /**
     * Common steps for checking user permissions in the Career Readiness Certification module
     *
     * @param user - current user.
     */
    protected void commonCrcSteps(User user) {
        logStep("If user can create career readiness certification - it should be created");
        //(!)Check, if user can create career readiness certification.
        if (user.getCRC().getCrcCreate()) {
            AccountUtils.init();
            crc = new CareerReadinessCertification(user.getRole());
            CRCSteps.createCareerReadinessCertification(user, crc);
        }
        checkOtherFunctionality(user);
    }

    /**
     * Check other functionality of the career readiness certification
     *
     * @param user - current user
     */
    private void checkOtherFunctionality(User user) {
        BaseWingsSteps.logInAs(user.getRole());

        if (user.getCRC().getCrcView()) {
            logStep("Check, if possible to view Career Readiness Certification");
            divideMessage("Navigate to " + WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS);

            //(!) If user can create CRC -> he has to confirm pop-up.
            if (user.getCRC().getCrcCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
            searchPage.performSearch(crc);
            searchPage.openFirstSearchResult();
            CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();

            //(!) Check required buttons [Audit] and [Edit] on the page.
            logStep("Check, if buttons [Audit] and [Edit] are present and clickable on the page.");
            detailsPage.checkButtonsPresent(user);

            //(!) Check edit of career readiness certification.
            logStep("Check edit functionality");
            if (user.getCRC().getCrcEdit()) {
                detailsPage.editCareerReadinessCertification(CommonFunctions.getDaysAgoDate(2), crc);
            }
        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Constants.ZERO);
        }
        BaseNavigationSteps.logout();
    }
}
