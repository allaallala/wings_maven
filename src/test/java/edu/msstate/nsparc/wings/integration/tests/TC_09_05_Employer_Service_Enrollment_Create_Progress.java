package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentCreationForm;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10717")
public class TC_09_05_Employer_Service_Enrollment_Create_Progress extends TC_09_01_Employer_Service_Enrollment_Create {

    public void main() {
        EmployerEnrollmentCreationForm creationForm = repeatedFirstSteps(false);

        logStep("Select Ended - No status");
        creationForm.chooseEndedService(false);

        repeatedLastSteps(creationForm);
    }
}
