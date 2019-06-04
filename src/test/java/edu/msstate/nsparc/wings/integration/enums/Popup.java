package edu.msstate.nsparc.wings.integration.enums;

/**
 * Enum for popup buttons
 * Created by a.vnuchko on 14.12.2015.
 */
public enum Popup {
    /**
     * Enum buttons
     */
    Search("search"), Create("create"), Yes("Yes"), No("No");
    private String value;

    /**
     * Default constructor
     * @param value - value to set
     */
    Popup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
