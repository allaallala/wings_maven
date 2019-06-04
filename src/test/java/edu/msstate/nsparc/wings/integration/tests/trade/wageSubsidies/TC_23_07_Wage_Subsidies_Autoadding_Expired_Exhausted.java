package edu.msstate.nsparc.wings.integration.tests.trade.wageSubsidies;

import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create ATAA/RTAA Enrollment with participant having reemployment, and reemployment date + 728 > current date.
 * Manage subsidies of ATAA/RTAA Enrollment and check its wage status and zeby all wage (between the start of qualifying reemployment
 * and expiration of benefits date) be presented in the manage wage subsidies table.
 * Created by a.vnuchko on 30.10.2015.
 */

@TestCase(id = "WINGS-11042")
public class TC_23_07_Wage_Subsidies_Autoadding_Expired_Exhausted extends TC_23_05_Wage_Subsidies_Autoadding_Current_Date {

    public void main() {


        info("Precondition: 1. Participant has one job which can be used as re-employment; "
                + "this job is stated though on last week (or later but current date should be before start re-employement date + 728 days\n"
                + "2. Create trade enrollment for this participant");
        AtaaRtaaEnrollment atrt = new AtaaRtaaEnrollment(PetitionType.ATAA_HIGH);
        atrt.setExhaustionDateRequired(true);
        makeActions(atrt);

        logResult("The Manage Wage Subsidies Screen Screen is shown. \n"
                + "Table 'Search Results' contains subsidies for every week between the start of qualifying re-employment and the expiration"
                + "of benefits date (last record is first Saturday after Deadline date). All records have status 'Not yet entered'");
        //new ManageWageSubsidiesForm().assertIsOpen();
    }
}
