package edu.msstate.nsparc.wings.integration.tests.trade.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.CRCSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create Career Readiness Certification, search it on the Search Form without entering any search criteria, check that result is correct.
 * Created by a.vnuchko on 25.09.2015.
 */

@TestCase(id = "WINGS-10980")
public class TC_20_13_CRC_Search_One_Criteria extends BaseWingsTest {

    public void main() {

        info("Precondition: Create some Career Readiness Certification");
        CareerReadinessCertification crc = new CareerReadinessCertification();
        User staff = new User(Roles.STAFF);
        CRCSteps.createCareerReadinessCertification(staff, crc);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);

        performSearchAndValidate(crc, CareerReadinessCertificationSearchForm.SearchCrcCriteria.PARTICIPANT);
        performSearchAndValidate(crc, CareerReadinessCertificationSearchForm.SearchCrcCriteria.DATEFROM);
        performSearchAndValidate(crc, CareerReadinessCertificationSearchForm.SearchCrcCriteria.DATETO);
        performSearchAndValidate(crc, CareerReadinessCertificationSearchForm.SearchCrcCriteria.CREATORSTAFF);
        performSearchAndValidate(crc, CareerReadinessCertificationSearchForm.SearchCrcCriteria.SERVICECENTER);

    }

    /**
     * Perform search by one criteria and check, that the search result is correct.
     *
     * @param crc  - individual employment plan
     * @param type - search criteria.
     */
    private void performSearchAndValidate(CareerReadinessCertification crc, CareerReadinessCertificationSearchForm.SearchCrcCriteria type) {
        logStep("Fill out any search criteria fields with the data of existing Career Readiness Certification (all the fields one by one)");
        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        searchPage.fillOneCriteria(crc, type);

        logStep("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        logResult("The suitable career readiness certifications are shown in the Search Results form");
        searchPage.validateOneCriteria(crc);
    }
}
