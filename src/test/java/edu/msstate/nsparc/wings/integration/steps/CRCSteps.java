package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationCreationForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationEditForm;
import edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification.CareerReadinessCertificationSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import framework.Logger;
import framework.customassertions.CustomAssertion;

/**
 * This class contains some methods which describe common steps for Career readiness certification.
 * Created by a.vnuchko on 15.09.2015.
 */
public class CRCSteps extends BaseWingsSteps {
    /**
     * This method is used for opening and filling out creation form of a career readiness certifications
     * 1. Log into the system as Staff
     * 2. Participants -> Career Readiness Certifications
     * 3. Choose "Create"  on the pop up
     * 4. Fill out the required fields with valid data
     * @param crc - career readiness certification
     */
    public static void openFillCreationForm(CareerReadinessCertification crc, Roles role) {
        openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Create);

        Logger.getInstance().info("Fill out the required fields with valid data");
        CareerReadinessCertificationCreationForm crcCreation = new CareerReadinessCertificationCreationForm();
        crcCreation.fillCRCInformation(crc);
    }

    /**
     * This method is used for opening detailed participant form of a career readiness certification.
     * 1. Log into the system as Staff
     * 2. Participants -> Career Readiness Certifications
     * 3. Choose "Search"  on the pop up
     * 4. Fill out any search criteria field with the data of existing Career Readiness Certification
     * 5. Click the [Search] button
     * 6. Click the 'Participant's name' of any Career Readiness Certification shown in the Search Results
     * @param crc - career readiness certification.
     */
    public static void openDetailedParticipantPageCRC(CareerReadinessCertification crc) {
        openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Search);

        Logger.getInstance().info("Fill out any search criteria field with the data of existing Career Readiness Certification");
        CareerReadinessCertificationSearchForm searchPage = new CareerReadinessCertificationSearchForm();
        searchPage.selectParticipant(crc.getParticipant());

        Logger.getInstance().info("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        Logger.getInstance().info("Click the 'Participant's name' of any Career Readiness Certification shown in the Search Results");
        searchPage.openSpanFirstResult();
    }

    /**
     * This method is used for editing date administred and save changes
     * @param crc - Career Readiness Certification
     * @param date - date administred.
     */
    public static void editCorrectDateAdministred(CareerReadinessCertification crc, String date) {
        Logger.getInstance().info("Edit the Date Administered with correct value: - Not more than a year before the original creation date, - Not in the future");
        CareerReadinessCertificationDetailsForm detailsPage = new CareerReadinessCertificationDetailsForm();
        detailsPage.clickButton(Buttons.Edit);
        CareerReadinessCertificationEditForm editPage = new CareerReadinessCertificationEditForm();
        editPage.inputDataAdministered(date);

        Logger.getInstance().info("Save changes");
        detailsPage.clickButton(Buttons.Save);

        crc.setDateAdministired(date);
    }

    /**
     * This method is used to connect CRC with qualifying Assessments.
     * @return new Career Readiness Certification.
     */
    public static CareerReadinessCertification generateCrcCommonData() {
        CareerReadinessCertification crc = new CareerReadinessCertification();
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Constants.TRUE, Constants.FALSE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, crc.getAppliedMathematics());
        AssessmentSteps.createAssessment(staff, crc.getLocatingInformation());
        AssessmentSteps.createAssessment(staff, crc.getReadingForInformation());
        return crc;
    }

    /**
     * Open participant search page from Participant Servoce Enrollment.
     * 1. Participants -> Participant Service Enrollment
     * 2. Choose "Search"  on the pop up
     * 3. Find a participant for which the CRC record was created
     * 4. Verify that 'Career Readiness Certification' service was created/changed for the participant
     * @param program - program (trade, wia, w-p)
     * @param crc - carre readiness certification
     * @param serviceName - name of the service.
     * @param staffMember - name of the staffMember
     */
    public static void findPartipEnrollment(String program, CareerReadinessCertification crc, String serviceName, String staffMember) {
        Logger.getInstance().info("Go to Participants -> Participant Service Enrollment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);

        Logger.getInstance().info("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        Logger.getInstance().info("Find a participant for which the CRC record was created");
        ParticipantEnrollmentSearchForm searchPage = new ParticipantEnrollmentSearchForm();
        searchPage.selectProgram(program);
        searchPage.performSearch(crc.getParticipant(), serviceName);

        Logger.getInstance().info("Verify that 'Career Readiness Certification' service was created/changed for the participant");
        SearchResultsForm resultsForm = new SearchResultsForm();
        CustomAssertion.softAssertContains(resultsForm.getFirstRowRecordText(3), serviceName, "Incorrect service name");
        CustomAssertion.softAssertContains(resultsForm.getFirstRowRecordText(4), crc.getDateAdministired(), "Incorrect date administred");
        CustomAssertion.softAssertContains(resultsForm.getFirstRowRecordText(5), crc.getDateAdministired(), "Incorrect date");
        CustomAssertion.softAssertContains(resultsForm.getFirstRowRecordText(6), staffMember, "Incorrect staff member");
    }

    /**
     * This method is used for creation new Career Readiness Certification.
     * @param crc - career readiness certification.
     * @param user - user role
     */
    public static void createCareerReadinessCertification(User user, CareerReadinessCertification crc) {
        ParticipantCreationSteps.createParticipantDriver(crc.getParticipant(), Constants.TRUE, Constants.FALSE);
        if ("WIA".equals(crc.getCrcProgram())) {
            WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), crc.getParticipant(), Constants.FALSE, Constants.FALSE);
        }
        AssessmentSteps.createAssessment(user, crc.getAppliedMathematics());
        AssessmentSteps.createAssessment(user, crc.getLocatingInformation());
        AssessmentSteps.createAssessment(user, crc.getReadingForInformation());

        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_CAREER_READINESS_CERTIFICATIONS, Popup.Create);

        CareerReadinessCertificationCreationForm creationPage = new CareerReadinessCertificationCreationForm();
        creationPage.fillCRCInformation(crc);
        creationPage.finishCreating();

        BaseNavigationSteps.logout();
    }
}
