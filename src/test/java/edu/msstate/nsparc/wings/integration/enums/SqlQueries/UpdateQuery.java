package edu.msstate.nsparc.wings.integration.enums.SqlQueries;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum UpdateQuery implements ISqlQuery{
    UPDATE_APPLICATION("updateApplication.sql"),
    UPDATE_ASSESSMENTS_SCORE("updateAssessmentsScore.sql"),
    UPDATE_COURSE("updateCourse.sql"),
    UPDATE_JOB_ORDER("updateJobOrder.sql"),
    UPDATE_JOB_ORDER_RELEASE("updateJobOrderRelease.sql"),
    UPDATE_PARTICIPANT_GRADE("updateParticipantGrade.sql"),
    UPDATE_PARTICIPANT_SSN("updateParticipantSsn.sql"),
    UPDATE_REFERRAL("updateReferralToIncorrect.sql"),
    UPDATE_RESET_DETERMINATION("updateResetDetermination.sql"),
    UPDATE_RESET_TRE_ELIGIBLE("updateResetTreEligible.sql"),
    UPDATE_SERVICE_ENROLLMENT_EMPLOYER_POSTED("updateServiceEnrollmentEmployerPosted.sql"),
    UPDATE_SERVICE_ENROLLMENT_EMPLOYER_RESULT("updateServiceEnrollmentEmployerResult.sql"),
    UPDATE_SERVICE_ENROLLMENT_POSTED("updateServiceEnrollmentPosted.sql"),
    UPDATE_SERVICE_ENROLLMENT_RESULT("updateServiceEnrollmentResult.sql"),
    UPDATE_TRAINING_PROVIDER("updateTrainingProvider.sql"),
    UPDATE_WIA_ENROLLMENT("updateWiaEnrollment.sql"),
    UPDATE_DATE_APPLIED("updateDateApplied.sql"),
    UPDATE_APPLICATION_USER("updateApplicationUser.sql"),
    UPDATE_PARTICIPANT("updateParticipant.sql");

    private Path path;

    UpdateQuery(String fileName) {
        this.path = Paths.get("update", fileName);
    }

    public String getQuery() {
        return getQuery(path);
    }
}
