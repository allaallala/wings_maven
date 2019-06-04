package edu.msstate.nsparc.wings.integration.enums;

/**
 * Participant enum type
 */
public enum ParticipantType {
    YOUTH, ADULT, DISLOCATED, ATAA;

    @Override
    public String toString() {
        //only capitalize the first letter
        String s = super.toString();
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
}
