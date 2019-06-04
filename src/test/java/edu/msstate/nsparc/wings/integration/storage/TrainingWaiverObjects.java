package edu.msstate.nsparc.wings.integration.storage;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;


/**
 * Class contains created Training Waiver objects with different parameters.
 * Object will be created using UI at the first call of "get" methods.
 */
public class TrainingWaiverObjects {

    public static TrainingWaiver getCreatedTrainingWaiver() {
        return DefaultTrainingWaiver.DEFAULT_TRAINING_WAIVER;
    }

    public static TrainingWaiver getCreatedIneligibleTrainingWaiver() {
        return IneligibleTrainingWaiver.INELIGIBLE_TRAINING_WAIVER;
    }

    public static TrainingWaiver getCreatedExpiredTrainingWaiver() {
        return ExpiredTrainingWaiver.EXPIRED_TRAINING_WAIVER;
    }

    public static TrainingWaiver getCreatedTrainingWaiverWithRenewal() {
        return TrainingWaiverWithRenewal.TRAINING_WAIVER_WITH_RENEWAL;
    }

    public static TrainingWaiver getCreatedTrainingWaiverWithRevocation() {
        return TrainingWaiverWithRevocation.TRAINING_WAIVER_WITH_REVOCATION;
    }

    /**
     * Contains created Training Waiver with default parameters.
     */
    private static class DefaultTrainingWaiver {
        private static final TrainingWaiver DEFAULT_TRAINING_WAIVER = createTrainingWaiver();

        /**
         * Create training waiver
         * @return default training waiver
         */

        private static TrainingWaiver createTrainingWaiver() {
            TrainingWaiver singleWaiver = new TrainingWaiver();
            TrainingSteps.createTrainingWaiver(singleWaiver);
            return singleWaiver;
        }
    }

    /**
     * Contains created ineligible Training Waiver.
     */
    private static class IneligibleTrainingWaiver {
        private static final TrainingWaiver INELIGIBLE_TRAINING_WAIVER = createTrainingWaiver();

        /**
         * Create ineligible training waiver
         * @return ineligible training waiver
         */
        private static TrainingWaiver createTrainingWaiver() {
            TrainingWaiver singleIneligibleWaiver = new TrainingWaiver();
            singleIneligibleWaiver.setEligible(Constants.FALSE);
            TrainingSteps.createTrainingWaiver(singleIneligibleWaiver);
            return singleIneligibleWaiver;
        }
    }

    /**
     * Contains created expired Training Waiver.
     */
    private static class ExpiredTrainingWaiver {
        private static final TrainingWaiver EXPIRED_TRAINING_WAIVER = createTrainingWaiver();

        /**
         * Create expired training waiver
         * @return expired training waiver
         */
        private static TrainingWaiver createTrainingWaiver() {
            TrainingWaiver singleExpiredWaiver = new TrainingWaiver();
            singleExpiredWaiver.initializeExpired();
            TrainingSteps.createTrainingWaiver(singleExpiredWaiver);
            return singleExpiredWaiver;
        }
    }

    /**
     * Contains created Training Waiver with default parameters and with added Renewal.
     */
    private static class TrainingWaiverWithRenewal {
        private static final TrainingWaiver TRAINING_WAIVER_WITH_RENEWAL = createTrainingWaiver();

        /**
         * Create training waiver with renewal
         * @return training waiver with renewal
         */
        private static TrainingWaiver createTrainingWaiver() {
            TrainingWaiver singleExpiredWaiver = new TrainingWaiver();
            singleExpiredWaiver.initializeExpired(); // Training Waiver with renewals should have "Expired" status
            TrainingSteps.createTrainingWaiverWithRenewal(singleExpiredWaiver);
            return singleExpiredWaiver;
        }
    }

    /**
     * Contains created Training Waiver with default parameters and with added Revocation.
     */
    private static class TrainingWaiverWithRevocation {
        private static final TrainingWaiver TRAINING_WAIVER_WITH_REVOCATION = createTrainingWaiver();

        /**
         * Create training waiver with revocation
         * @return training waiver with revocation
         */
        private static TrainingWaiver createTrainingWaiver() {
            TrainingWaiver singleExpiredWaiver = new TrainingWaiver();
            singleExpiredWaiver.initializeExpired();
            TrainingSteps.createTrainingWaiverWithRevocation(singleExpiredWaiver);
            singleExpiredWaiver.setWaiverStatus(Constants.REVOKED); // Training Waiver with revocation should have "Revoked" status
            return singleExpiredWaiver;
        }
    }
}
