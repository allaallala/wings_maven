package edu.msstate.nsparc.wings.integration.tests.cipCollapse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.trade.JobSearch;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.steps.CIPSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check information in contextual information pane by opening participantSSDetails and search pages of job search module of Trade.
 * Created by a.vnuchko on 07.12.2015.
 */

@TestCase(id = "WINGS-11027")
public class TC_22_03_05_Trade_Job_Seach_Details_Search_Pages extends BaseWingsTest {
    String type = "JobSearch";

    public void main() {
        JobSearch jbSearch = new JobSearch();
        TradeEnrollmentSteps.createJobSearch(jbSearch, Roles.ADMIN);
        WIAEnrollment wia = new WIAEnrollment(jbSearch.getTradeEnrollment().getParticipant());

        CIPSteps.validateCIP(type, Constants.TRADE, jbSearch.getTradeEnrollment().getParticipant(), wia, jbSearch.getTradeEnrollment());

    }
}
