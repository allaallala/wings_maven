package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverCreationForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverEditForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import framework.Logger;

/**
 * Base steps for training waiver
 */
public class TrainingWaiverSteps extends BaseWingsSteps {

    /**
     * Perform common steps:
     *  1. navigate to the Training Waivers search page;
     *  2. fill out search criteria fields;
     *  3. perform search;
     *  4. open detail page of found Training Waiver.
     * @param trainingWaiver Training Waiver object to be found and opened
     */
    public static void openTrainingWaiverDetailPage(TrainingWaiver trainingWaiver) {
        Logger.getInstance().info("Navigate to Participants -> Trade -> Training Waivers -> Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS);
        BaseWingsSteps.popClick(Popup.Search);

        Logger.getInstance().info("Search for 'Training Waiver' and try to open form");
        TrainingWaiverSearchForm trainingWaiverSearchForm = new TrainingWaiverSearchForm();
        trainingWaiverSearchForm.performSearchAndOpen(trainingWaiver);
    }

    /**
     * Perform common steps:
     *  1. log in to the system;
     *  2. navigate to the Training Waivers search page;
     *  3. fill out search criteria fields;
     *  4. perform search;
     *  5. open detail page of found Training Waiver.
     * @param trainingWaiver Training Waiver object to be found and opened
     * @param role User role
     */
    public static void openTrainingWaiverDetailPage(TrainingWaiver trainingWaiver, Roles role) {
        openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);

        Logger.getInstance().info("Search for 'Training Waiver' and open it");
        TrainingWaiverSearchForm trainingWaiverSearchForm = new TrainingWaiverSearchForm();
        trainingWaiverSearchForm.performSearchAndOpen(trainingWaiver);
    }

    /**
     * Fill out creation page and then validate it
     * @param trainingWaiver Training Waiver object
     */
    public static void completeTrainingWaiverCreationAndValidate(TrainingWaiver trainingWaiver) {
        Logger.getInstance().info("Fill out the creation form");
        TrainingWaiverCreationForm creationForm = new TrainingWaiverCreationForm();
        creationForm.fillOutCreationForm(trainingWaiver);

        Logger.getInstance().info("Press Create button");
        creationForm.completeCreation();

        Logger.getInstance().info("Make Sure the record was created");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.validateInformation(trainingWaiver);
    }

    /**
     * Edit training waiver and validate changes
     * @param trainingWaiver - training waiver
     */
    public static void editTrainingWaiverAndValidate(TrainingWaiver trainingWaiver) {
        Logger.getInstance().info("Press edit button");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.openEditForm();

        Logger.getInstance().info("Change a few of Training Waiver fields");
        TrainingWaiverEditForm editForm = new TrainingWaiverEditForm();

        editForm.fillOutEditForm(trainingWaiver);

        Logger.getInstance().info("Save Changes");
        editForm.finishEditing();

        Logger.getInstance().info("Validate that the status was saved");
        detailsForm.validateInformation(trainingWaiver);
    }
}
