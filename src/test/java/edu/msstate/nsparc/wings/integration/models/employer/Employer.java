package edu.msstate.nsparc.wings.integration.models.employer;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.Address;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
This class represents Employer entity
 */
public class Employer {

    private Address address, everifyAddress;
    private String employerAccount;
    private String companyName;
    private String telephoneNumber;
    private String naics;
    private String fein;
    private String ean;
    private String ID;
    String lineOne;
    private boolean outOfStateEmployer;

    //User permission fields
    private Boolean employerCreate;
    private Boolean employerView;
    private Boolean employerEdit;
    private Boolean employerCheckNeutral;
    private Boolean employerCheckTide;
    private Boolean employerCheckNotTide;
    private Boolean employerEditContactInformation;
    private Boolean employerEditCompanyInformation;
    private Boolean employerEditEverifyInformation;
    private Boolean employerEditCreateAccount;
    private Boolean employerEditRemoveUsername;
    private static final String EMPLOYER_CREATE = "employerCreate";
    private static final String EMPLOYER_VIEW = "employerView";
    private static final String EMPLOYER_EDIT = "employerEdit";
    private static final String EMPLOYER_CHECK_NEUTRAL = "employerCheckNeutral";
    private static final String EMPLOYER_CHECK_TIDE = "employerCheckTide";
    private static final String EMPLOYER_CHECK_NOT_TIDE = "employerCheckNotTide";
    private static final String EMPLOYER_EDIT_CONTACT_INFORMATION = "employerEditContactInformation";
    private static final String EMPLOYER_EDIT_COMPANY_INFORMATION = "employerEditCompanyInformation";
    private static final String EMPLOYER_EDIT_EVERIFY_INFORMATION = "employerEditEverifyInformation";
    private static final String EMPLOYER_EDIT_CREATE_ACCOUNT = "employerEditCreateAccount";
    private static final String EMPLOYER_EDIT_REMOVE_USERNAME = "employerEditRemoveUsername";

    /**
     * Constructor with employeer data
     * @param account - employeer account
     */
    public Employer(String account) {
        employerAccount = account;
        generateRandomData();
    }

    /**
     * Constructor with employeer data
     */
    public Employer() {
        generateRandomData();
    }

    /**
     * Constructor for checking user permissions
     * @param role - current user role
     */
    public Employer(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        employerCreate = Boolean.valueOf(prop.getProperty(EMPLOYER_CREATE));
        employerView = Boolean.valueOf(prop.getProperty(EMPLOYER_VIEW));
        employerEdit = Boolean.valueOf(prop.getProperty(EMPLOYER_EDIT));
        employerCheckNeutral = Boolean.valueOf(prop.getProperty(EMPLOYER_CHECK_NEUTRAL));
        employerCheckTide = Boolean.valueOf(prop.getProperty(EMPLOYER_CHECK_TIDE));
        employerCheckNotTide = Boolean.valueOf(prop.getProperty(EMPLOYER_CHECK_NOT_TIDE));
        employerEditContactInformation = Boolean.valueOf(prop.getProperty(EMPLOYER_EDIT_CONTACT_INFORMATION));
        employerEditCompanyInformation = Boolean.valueOf(prop.getProperty(EMPLOYER_EDIT_COMPANY_INFORMATION));
        employerEditEverifyInformation = Boolean.valueOf(prop.getProperty(EMPLOYER_EDIT_EVERIFY_INFORMATION));
        employerEditCreateAccount = Boolean.valueOf(prop.getProperty(EMPLOYER_EDIT_CREATE_ACCOUNT));
        employerEditRemoveUsername = Boolean.valueOf(prop.getProperty(EMPLOYER_EDIT_REMOVE_USERNAME));
    }

    public void setEmployerID(String id) {
        this.ID = id;
    }

    public String getEmployerID() {
        return ID;
    }

    /**
     * Generate random data for employeer
     */
    private void generateRandomData() {
        companyName = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        lineOne = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        address = new Address(lineOne);
        everifyAddress = new Address(lineOne);
        telephoneNumber = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        naics = "Executive Offices";
        fein = CommonFunctions.getRandomIntegerNumber(Constants.FEIN_LENGTH);
        ean = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        outOfStateEmployer = false;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEan() {
        return ean;
    }

    public String getEmployerAccount() {
        return employerAccount;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getNaics() {
        return naics;
    }

    public void setCompanyName(String newCompanyName) {
        this.companyName = newCompanyName;
    }

    public void setFein(String newFein) {
        this.fein = newFein;
    }

    public void setEan(String newEan) {
        this.ean = newEan;
    }

    public String getFein() {
        return fein;
    }


    public boolean isOutOfStateEmployer() {
        return outOfStateEmployer;
    }

    public void setOutOfStateEmployer(boolean employerOutOfState) {
        this.outOfStateEmployer = employerOutOfState;
    }

    public Address getAddress() {
        return address;
    }

    public Address getEverifyAddress() {
        return everifyAddress;
    }

    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }

    public void setEmployerAccount(String newEmployerAcc) {
        this.employerAccount = newEmployerAcc;
    }

    //User permissions

    public Boolean getEmployerCreate() {
        return employerCreate;
    }

    public Boolean getEmployerView() {
        return employerView;
    }

    public Boolean getEmployerEdit() {
        return employerEdit;
    }

    public Boolean getEmployerCheckNeutral() {
        return employerCheckNeutral;
    }

    public Boolean getEmployerCheckTide() {
        return employerCheckTide;
    }

    public Boolean getEmployerCheckNotTide() {
        return employerCheckNotTide;
    }

    public Boolean getEmployerEditContactInformation() {
        return employerEditContactInformation;
    }

    public Boolean getEmployerEditCompanyInformation() {
        return employerEditCompanyInformation;
    }

    public Boolean getEmployerEditEverifyInformation() {
        return employerEditEverifyInformation;
    }

    public Boolean getEmployerEditCreateAccount() {
        return employerEditCreateAccount;
    }

    public Boolean getEmployerEditRemoveUsername() {
        return employerEditRemoveUsername;
    }
}
