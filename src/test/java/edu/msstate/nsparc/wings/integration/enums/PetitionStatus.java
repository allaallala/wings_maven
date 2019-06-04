package edu.msstate.nsparc.wings.integration.enums;

/**
 * Petition status enum
 */
public enum PetitionStatus {
    CERTIFIED, DENIED, TERMINATED;

    @Override
    public String toString() {
        //only capitalize the first letter
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
}
