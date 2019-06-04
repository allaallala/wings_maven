package edu.msstate.nsparc.wings.integration.storage;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;


/**
 * Class contains created Trade Enrollment objects with different parameters.
 * Object will be created using UI at the first call of "get" methods.
 */
public class TradeEnrollmentObjects {

    public static TradeEnrollment getCreatedTradeEnrollment() {
        return DefaultTradeEnrollment.DEFAULT_TRADE_ENROLLMENT;
    }

    public static TradeEnrollment getCreatedDeniedTradeEnrollment() {
        return DeniedTradeEnrollment.DENIED_TRADE_ENROLLMENT;
    }

    public static TradeTraining getCreatedTradeTraining() {
        return DefaultTradeTraining.DEFAULT_TRADE_TRAINING;
    }

    /**
     * Contains created Trade Enrollment with default parameters.
     */
    private static class DefaultTradeEnrollment {
        private static final TradeEnrollment DEFAULT_TRADE_ENROLLMENT = createTradeEnrollment();

        /**
         * Test
         * @return Trade enrollment
         */
        private static TradeEnrollment createTradeEnrollment() {
            TradeEnrollment singleEnrollment = new TradeEnrollment();
            TradeEnrollmentSteps.createTradeEnrollment(singleEnrollment, Roles.ADMIN);
            return singleEnrollment;
        }
    }

    /**
     * Contains created denied Trade Enrollment.
     */
    private static class DeniedTradeEnrollment {
        private static final TradeEnrollment DENIED_TRADE_ENROLLMENT = createDeniedTradeEnrollment();

        /**
         * Test
         * @return denied trade enrollment
         */

        private static TradeEnrollment createDeniedTradeEnrollment() {
            TradeEnrollment deniedTradeEnrollment = new TradeEnrollment();
            deniedTradeEnrollment.setEligible(false);
            TradeEnrollmentSteps.createTradeEnrollment(deniedTradeEnrollment, Roles.ADMIN);
            return deniedTradeEnrollment;
        }
    }

    /**
     * Contains created Trade Training with default parameters.
     */
    private static class DefaultTradeTraining {
        private static final TradeTraining DEFAULT_TRADE_TRAINING = createDefaultTradeTraining();

        /**
         * Test
         * @return trade training
         */

        private static TradeTraining createDefaultTradeTraining() {
            TradeTraining singleTradeTraining = new TradeTraining();
            TrainingSteps.createTradeTraining(singleTradeTraining, Roles.STAFF, Roles.ADMIN);
            return singleTradeTraining;
        }
    }
}
