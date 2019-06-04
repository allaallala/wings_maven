package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentSearchForm;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10716")
public class TC_09_04_Employer_Service_Enrollment_Create_Schedule extends TC_09_01_Employer_Service_Enrollment_Create {
    String time = "12:24 AM";

    public void main() {
        EmployerEnrollmentCreationForm creationForm = repeatedFirstSteps(Constants.TRUE);

        creationForm.inputScheduledDateTime(SCHEDULED_DATE, time);

        logStep("Select Ended - No status");
        creationForm.chooseEndedService(Constants.FALSE);

        logStep("Search for created enrollment");
        EmployerEnrollmentSearchForm searchForm = repeatedLastSteps(creationForm);
        searchForm.checkScheduledDate(SCHEDULED_DATE);
    }
}
