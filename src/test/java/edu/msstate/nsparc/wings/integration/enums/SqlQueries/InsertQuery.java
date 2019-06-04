package edu.msstate.nsparc.wings.integration.enums.SqlQueries;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum InsertQuery implements ISqlQuery{
    INSERT_ADDRESS("insertAddress.sql"),
    INSERT_CLAIMANT("insertClaimant.sql"),
    INSERT_CLAIMANT_PAYMENT("insertClaimantPayment.sql"),
    INSERT_CONTACT_INFO("insertContactInfo.sql"),
    INSERT_DRIVER_LICENSE("insertDriverLicense.sql"),
    INSERT_EDUCATION_RECORD("insertEducationRecord.sql"),
    INSERT_EMPLOYER("insertEmployer.sql"),
    INSERT_EMPLOYER_ACCOUNT("insertEmployerAccount.sql"),
    INSERT_EMPLOYER_QUERY("insertEmployerQuery.sql"),
    INSERT_EMPLOYER_USER("insertEmployerUser.sql"),
    INSERT_JOB_ORDER("insertJobOrder.sql"),
    INSERT_MILITARY_RECORD("insertMilitaryRecord.sql"),
    INSERT_MISSISSIPPI_EMPLOYER_QUERY("insertMississippiEmployerQuery.sql"),
    INSERT_NEW_PDO("insertNewPdo.sql"),
    INSERT_PARTICIPANT("insertParticipant.sql"),
    INSERT_PARTICIPANT_ACTIVITY("insertParticipantActivity.sql"),
    INSERT_PARTICIPANT_INTERESTS("insertParticipantInterests.sql"),
    INSERT_PARTICIPANT_JOB_INTERESTS("insertParticipantJobInterests.sql"),
    INSERT_PARTICIPANT_PROGRAM("insertParticipantProgram.sql"),
    INSERT_PARTICIPANT_REGISTRATION_COMPLETED("insertParticipantRegistrationCompleted.sql"),
    INSERT_PARTICIPANT_SUPPLEMENTAL("insertParticipantSupplemental.sql"),
    INSERT_PARTICIPANT_USER("insertParticipantUser.sql"),
    INSERT_PETITION_QUERY("insertPetitionQuery.sql"),
    INSERT_PREVIOUS_JOB("insertPreviousJob.sql"),
    INSERT_PREVIOUS_JOB_X_OSOC("insertPreviousJobXOsoc.sql"),
    INSERT_SERVICE_ENROLLMENT_JOB_ORDER("insertServiceEnrollmentJobOrder.sql"),
    INSERT_SERVICE_ENROLLMENT_PARTICIPANT("insertServiceEnrollmentParticipant.sql"),
    INSERT_TRIAGE_PARTICIPANT_RULES("insertTriageParticipantRules.sql"),
    INSERT_VETERAN_DETAILS("insertVeteranDetails.sql");

    private Path path;

    InsertQuery(String fileName) {
        this.path = Paths.get("insert", fileName);
    }

    public String getQuery() {
        return getQuery(path);
    }
}
