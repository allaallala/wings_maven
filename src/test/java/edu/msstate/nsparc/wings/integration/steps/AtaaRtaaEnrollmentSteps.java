package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment.AtaaRtaaEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.denials.AppealDenialsCreationForm;
import edu.msstate.nsparc.wings.integration.forms.denials.DenialSectionForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import framework.Logger;

/**
 * Base steps for ATAA/RTAA Enrollment
 */
public class AtaaRtaaEnrollmentSteps extends BaseWingsSteps {

    /**
     * Perform common steps:
     *  1. log in to the system;
     *  2. navigate to the Ataa/Rtaa Enrollment search page;
     *  3. fill out search criteria fields;
     *  4. perform search;
     *  5. open detail page of found Ataa/Rtaa Enrollment.
     * @param ataaRtaaEnrollment Ataa/Rtaa Enrollment object to be found and opened
     * @param role User role
     */
    public static void openAtaaRtaaEnrollmentDetailPage(AtaaRtaaEnrollment ataaRtaaEnrollment, Roles role) {
        openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Search);

        Logger.getInstance().info("Search for 'Ataa/Rtaa Enrollment' and open it");
        AtaaRtaaEnrollmentSearchForm enrollmentSearchForm = new AtaaRtaaEnrollmentSearchForm();
        enrollmentSearchForm.performSearchAndOpen(ataaRtaaEnrollment);
    }

    /**
     * Open creation page and add Previous job
     * @param ataaRtaaEnrollment AtaaRtaaEnrollment object
     */
    public static void openAtaaRtaaEnrollmentCreationAndAddJob(AtaaRtaaEnrollment ataaRtaaEnrollment) {
        openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_ATAA_RTAA_ENROLLMENTS, Popup.Create);

        Logger.getInstance().info("Select Participant and choose an Employment Record");
        AtaaRtaaEnrollmentCreationForm enrollmentCreationForm = new AtaaRtaaEnrollmentCreationForm();
        enrollmentCreationForm.selectParticipant(ataaRtaaEnrollment.getParticipant());
        enrollmentCreationForm.addPreviousJob();
    }


    /**
     * Open Appealing enrollment page
     * @param ineligibleEnrollment AtaaRtaaEnrollment object (should be with ineligible status)
     * @param role User role in system
     */
    public static void openAppealAtaaRtaaEnrollmentPage(AtaaRtaaEnrollment ineligibleEnrollment, Roles role) {
        openAtaaRtaaEnrollmentDetailPage(ineligibleEnrollment, role);

        Logger.getInstance().info("Click the [Appeal] button");
        DenialSectionForm denialSectionForm = new DenialSectionForm();
        denialSectionForm.openDenialAppealCreationForm();
        new AppealDenialsCreationForm();
    }

    /**
     * Open edit enrollment page
     * @param ataaRtaaEnrollment AtaaRtaaEnrollment object
     * @param roles User role in system
     */
    public static void openAtaaRtaaEditPage(AtaaRtaaEnrollment ataaRtaaEnrollment, Roles roles) {
        openAtaaRtaaEnrollmentDetailPage(ataaRtaaEnrollment, roles);

        Logger.getInstance().info("Click the [Edit] button");
        new AtaaRtaaEnrollmentDetailsForm().clickButton(Buttons.Edit);
        new AtaaRtaaEnrollmentEditForm();
    }
}
