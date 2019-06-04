package edu.msstate.nsparc.wings.integration.enums;

/**
 * Enum for buttons.
 * Created by a.vnuchko on 14.12.2015.
 */
public enum Buttons {
    Edit("Edit"), Create("Create"), Save("SaveChanges"), Done("Done"), Cancel("Cancel"),
    Search("Search"), Clear("Clear"), Add("Add"), CreateAnother("CreateAnother"), Continue("Continue"), Delete("Delete"), DeleteYes("DeleteYes");
    private String value;

    /**
     * Default constructor
     * @param value - value to set
     */
    Buttons(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
