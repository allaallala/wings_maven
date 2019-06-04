package edu.msstate.nsparc.wings.integration.enums;

public enum Database {
    MDES("mdes."),
    REAUI_MDES("reaui.");

    private String prefix;

    Database(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return prefix;
    }
}
