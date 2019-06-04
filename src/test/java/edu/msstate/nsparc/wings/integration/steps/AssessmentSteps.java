package edu.msstate.nsparc.wings.integration.steps;


import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import framework.Logger;

/**
 * This class contains some methods which describe common steps for Assessments.
 * Created by a.korotkin
 * modified by a.vnuchko
 */
public class AssessmentSteps extends BaseWingsSteps {

    /**
     * This method is used for opening and filling out creation form of the Assessment.
     * 1. Log into the system as Staff
     * 2. Participants -> Assessments
     * 3. Choose "Create"  on the pop up
     * 4. Fill out the required fields with valid data
     * @param assm - assessment
     * @return new assessment creation form
     */
    public static AssessmentCreationForm openFillCreationForm(Assessment assm) {
        openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        Logger.getInstance().info("Fill out the required fields with valid data");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), assm);

        return new AssessmentCreationForm();
    }
    /**
     * 1. Log into the system as Staff
     * 2. Participants -> Assessments
     * 3. Choose "Search" on the pop up
     * 4. Fill out any search criteria field with the data of existing Assessment
     * @param assm - assessment
     * @return new assessment search form
     */
    public static AssessmentSearchForm openFillSearchForm(Assessment assm) {
        openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Search);

        Logger.getInstance().info("Fill out some search criteria fields with any data");
        AssessmentSearchForm searchPage = new AssessmentSearchForm();
        searchPage.fillSearchForm(assm);

        return new AssessmentSearchForm();
    }
    /**
     * 1. Log into the system as Staff
     * 2. Participants -> Assessments
     * 3. Choose "Search" on the pop up
     * 4. Fill out any search criteria field with the data of existing Assessment
     * 5. Click the [Search] button
     * 6. Click the "Participant's name" of any Assessment shown in the Search Results
     * 7. Click the [Edit] button
     * @param assm - assessment
     * @param number - first, second or third record.
     * @return new assessment edit form.
     */
    public static AssessmentEditForm openEditAssessment(Assessment assm, Integer number) {
        AssessmentSearchForm searchPage = openFillSearchForm(assm);

        Logger.getInstance().info("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        Logger.getInstance().info("Click the 'Participant's name' of any Assessment shown in the Search Results");
        new SearchResultsForm().openSearchResult(number);
        Logger.getInstance().info("Click the [Edit] button");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.clickButton(Buttons.Edit);
        return new AssessmentEditForm();
    }

    public static void createAssessment(User user, Assessment assessment) {
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(user, assessment);
        creationForm.clickButton(Buttons.Create);
        creationForm.clickButton(Buttons.Done);

        BaseNavigationSteps.logout();
    }
}
