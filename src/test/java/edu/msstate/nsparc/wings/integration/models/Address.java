package edu.msstate.nsparc.wings.integration.models;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import framework.CommonFunctions;

/**
 * This class is used for storing Address information
 */
public class Address {
    private String lineOne;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    private String country;

    /**
     * Constructor
     */
    public Address() {
        generateRandomData();
    }

    /**
     * Constructor with specified line one.
     * @param line - line one
     */
    public Address(String line) {
        lineOne = line;
        city = "Navagradak";
        state = "Mississippi";
        zipCode = "39759";
        county = "Adams";
        country = "United States";
    }

    /**
     * Generate random data for address
     */
    private void generateRandomData() {
        lineOne = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        city = "Starkville";
        state = "Mississippi";
        zipCode = "39759";
        county = "Adams";
        country = "United States";
    }

    public Address(Integer newZipCode) {
        lineOne = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        city = "Easton";
        state = "New York";
        zipCode = newZipCode.toString();
        county = "";
    }

    public String getCity() {
        return city;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getState() {
        return state;
    }

    public String getCounty() {
        return county;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setLineOne(String lineFirst) {
        this.lineOne = lineFirst;
    }

    public void setCity(String newCity) {
        this.city = newCity;
    }

    public void setZipCode(String newZipCode) {
        this.zipCode = newZipCode;
    }

    public void setState(String newState) {
        this.state = newState;
    }

    public void setCounty(String newCounty) {
        this.county = newCounty;
    }
}
