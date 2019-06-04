package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.korotkin on 8/10/2015.
 */

@TestCase(id = "WINGS-10952")
public class TC_19_05_02_Assessment_Trade_Create_With_All_Educational_Levels_And_Categories extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        String type = "SPL";
        String category = "ESL";
        String[] educationalLevel = {"Low Intermediate Basic Education/High Intermediate ESL (4-5.9)", "High Intermediate Basic Education/Advanced ESL (6-8.9)",
                "Low Adult Secondary Education/Exit ESL (9-10.9)", "High Adult Secondary Education (11-12)"};
        String[] functionalArea = {"Writing", "Language", "Speaking"};
        logStep("Creating Trade Enrollment for Assessment");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        logStep("Creating the fifth Assessment");

        assessment.setType(type);
        assessment.setCategory(category);
        assessment.setEducationalLevel(educationalLevel[0]);
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), assessment);
        creationForm.clickButton(Buttons.Create);
        AssessmentDetailsForm assessmentDetailsForm = new AssessmentDetailsForm();
        assessmentDetailsForm.validateAssessmentInformationWithCategoryAndEducational(assessment);
        assessment.setParticipantPrePopulated(true);

        logStep("Creating Assessments by using Create Another button from Assessment Details (3 more Assessments)");

        for (int i = 0; i < 3; i++) {
            switch (i) {

                case 0:
                    assessment.setFunctionalArea(functionalArea[0]);
                    assessment.setEducationalLevel(educationalLevel[1]);
                    break;
                case 1:
                    assessment.setFunctionalArea(functionalArea[1]);
                    assessment.setEducationalLevel(educationalLevel[2]);
                    break;
                case 2:
                    assessment.setFunctionalArea(functionalArea[2]);
                    assessment.setEducationalLevel(educationalLevel[3]);
                    break;
                default:
                    break;

            }

            creationForm.clickButton(Buttons.CreateAnother);
            creationForm.fillAssessmentInformation(new User(Roles.STAFF), assessment);//TODO https://jira.nsparc.msstate.edu/browse/WINGS-9092
            creationForm.clickButton(Buttons.Create);
            assessmentDetailsForm.validateAssessmentInformationWithCategoryAndEducational(assessment);

        }

    }
}
