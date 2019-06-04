package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.InterviewDateForm;
import edu.msstate.nsparc.wings.integration.forms.UpdateStatusForm;
import edu.msstate.nsparc.wings.integration.forms.ViewApplicantsForm;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10600")
public class TC_04_12_View_Applicants_Edit_Date_Status extends TC_04_10_View_Applicants_Job_Order_Details {

    private static final String INTERVIEW_TIME = "9:30 AM";
    private static final String NO_SHOW_STATUS = "No Show";

    public void main () {
        info("We need to create Job Order first");
        JobOrder jobOrder = JobOrderSteps.createJobOrderForApplyOnline();

        logStep("Enter some parameters for searching->Search");
        ViewApplicantsForm applicantsForm = searchForJobOrder(jobOrder);

        logStep("Interview Date/Time");
        applicantsForm.checkInternalError();
        applicantsForm.openInterviewDataForm();

        logStep("Edit date");
        InterviewDateForm interviewDateForm = new InterviewDateForm();
        interviewDateForm.inputDate(CommonFunctions.getTomorrowDate());
        interviewDateForm.inputTime(INTERVIEW_TIME);
        interviewDateForm.clickButton(Buttons.Save);

        logStep("Validate changes are saved");
        String[] data = applicantsForm.getDataText();
        CustomAssertion.softTrue("Date not valid",data[0].contains(CommonFunctions.getTomorrowDate()));
        CustomAssertion.softTrue("Interview time not valid", data[0].contains(INTERVIEW_TIME));

        logStep("Update Status");
        applicantsForm.openUpdateStatusForm();

        logStep("Change Status");
        UpdateStatusForm updateStatusForm = new UpdateStatusForm();
        updateStatusForm.selectResultRef(NO_SHOW_STATUS);
        updateStatusForm.clickButton(Buttons.Save); //ISSUE WINGS-9819

        logStep("Validate changes are saved");
        data = applicantsForm.getDataText();
        CustomAssertion.softTrue("Incorrect status", data[1].contains(NO_SHOW_STATUS));
    }
}
