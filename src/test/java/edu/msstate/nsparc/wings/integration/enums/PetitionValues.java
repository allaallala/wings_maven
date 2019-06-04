package edu.msstate.nsparc.wings.integration.enums;

public enum PetitionValues {

    PETITION_NUMBER(8),
    ATAA_LOW_MIN(50000),
    ATAA_LOW_RANDOM(20000),
    ATAA_HIGH_MIN(70000),
    ATAA_HIGH_RANDOM(10000),
    RTAA_MIN(80000),
    RTAA_RANDOM(5000),
    TAA_2014_MIN(85000),
    TAA_2014_RANDOM(30000);

    private int value;

    PetitionValues(int value) {
        this.value = value;
    }

    public int getPetitionValue() {
        return value;
    }
}
