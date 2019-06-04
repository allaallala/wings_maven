package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanCreationForm;
import edu.msstate.nsparc.wings.integration.forms.individualEmploymentPlans.IndividualEmploymentPlanSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.IndividualEmploymentPlan;
import framework.Logger;

/**
 * This class is used for storing some methods with default steps for individual employment plans.
 * Created by a.vnuchko on 07.09.2015.
 */
public class IEPSteps extends BaseWingsSteps {

    /**
     * Log into the system, open and fill out creation form.
     * 1. Log into the system as Staff
     * 2. Participants -> Individual Employment Plan
     * 3. Choose 'Create' on the pop up
     * 4. Fill out the required fields with valid data
     * @param plan - new Individual employment plan
     * @return individual employment plan creation page.
     */
    public static IndividualEmploymentPlanCreationForm openFillOutCreationForm(User user, IndividualEmploymentPlan plan) {
        openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Create);

        Logger.getInstance().info("Fill out the required fields with valid data and Input IEP Creation date in Past-Date (Includes today)");
        IndividualEmploymentPlanCreationForm creationPage = new IndividualEmploymentPlanCreationForm();
        creationPage.fillOutCreationForm(user, plan);

        return creationPage;
    }

    /**
     * Log into the system, find individual employment plan and open its participantSSDetails page
     * 1. Log into the system as Staff
     * 2. Participants -> Individual Employment Plan
     * 3. Choose 'Search' on the pop up
     * 4. Fill out any search criteria field with the data of existing Individual Employment Plan
     * 5. Click the [Search] button
     * 6. Click the 'Participants Name' of any Individual Employment Plan shown in the Search Results
     * @param plan - individual employment plan for searching
     */
    public static void openSearchedParticipantDetails(User user, IndividualEmploymentPlan plan) {
        openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.P_INDIVIDUAL_EMPLOYMENT_PLANS, Popup.Search);

        Logger.getInstance().info("Fill out any search criteria field with the data of existing Individual Employment Plan");
        IndividualEmploymentPlanSearchForm searchPage = new IndividualEmploymentPlanSearchForm();
        searchPage.selectParticipantByUser(user, plan.getParticipant());

        Logger.getInstance().info("Click the [Search] button");
        searchPage.clickButton(Buttons.Search);

        Logger.getInstance().info("Click the 'Participants Name' of any Individual Employment Plan shown in the Search Results");
        searchPage.openFirstSearchResult();
    }
}
