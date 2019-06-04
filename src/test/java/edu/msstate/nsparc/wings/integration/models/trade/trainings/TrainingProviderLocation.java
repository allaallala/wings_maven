package edu.msstate.nsparc.wings.integration.models.trade.trainings;

import framework.CommonFunctions;

public class TrainingProviderLocation {
    private boolean wiaLocation;
    private boolean tradeLocation;
    private String name;
    private String code;
    private String lineOne;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    private String contactName;
    private String contactTitle;
    private String phoneNumber;

    private static final Integer LOCATION_LENGTH = 5;

    /**
     * Make location WIA or Trade and generade location data.
     * @param isWia - true/false
     * @param isTrade - true/false
     */
    public TrainingProviderLocation(boolean isWia, boolean isTrade) {
        wiaLocation = isWia;
        tradeLocation = isTrade;
        generateRandomData();
    }

    /**
     * Make default location data
     */
    public TrainingProviderLocation() {
        wiaLocation = true;
        tradeLocation = false;
        generateRandomData();
    }

    /**
     * Generates random data
     */
    private void generateRandomData() {
        name = CommonFunctions.getRandomLiteralCode(LOCATION_LENGTH);
        code = "1";
        lineOne = CommonFunctions.getRandomLiteralCode(LOCATION_LENGTH);
        city = CommonFunctions.getRandomLiteralCode(LOCATION_LENGTH);
        state = "Mississippi";
        zipCode = "12345";
        county = "Adams";
        contactName = CommonFunctions.getRandomLiteralCode(LOCATION_LENGTH);
        contactTitle = CommonFunctions.getRandomLiteralCode(LOCATION_LENGTH);
        phoneNumber = "1234567890";
    }

    public boolean isWiaLocation() {
        return wiaLocation;
    }

    public String getName() {
        return name;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCounty() {
        return county;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCode(String codeNumber) {
        this.code = codeNumber;
    }
}
