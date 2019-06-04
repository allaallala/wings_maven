package edu.msstate.nsparc.wings.integration.models.trade.trainings;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents Training Provider entity
 */
public class TrainingProvider {
    private String name;
    private String code;
    private String dfa;
    private String fein;
    private String courseName;
    private String status;
    private boolean wiaProvider;
    private boolean tradeProvider;
    private List<TrainingProviderLocation> trainingProviderLocations;

    //User permissions
    private Boolean tpCreate;
    private Boolean tpView;
    private Boolean tpViewEditButton;
    private Boolean tpViewAuditButton;
    private Boolean tpEdit;
    private static final String TP_CREATE = "tpCreate";
    private static final String TP_VIEW = "tpView";
    private static final String TP_VIEW_EDIT = "tpViewEditButton";
    private static final String TP_VIEW_AUDIT = "tpViewAuditButton";
    private static final String TP_EDIT = "tpEdit";

    public enum TrainingProviderCriteria {
        TRAINING_PROVIDER_CODE, FEIN_LENGTH, DFA_NUMBER, WIA_APPROVED_NO, TRADE_APPROVED_YES
    }

    /**
     * Data for training provider
     */
    public TrainingProvider() {
        fein = CommonFunctions.getRandomIntegerNumber(Constants.FEIN_LENGTH);
        dfa = "V" + CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        name = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        code = CommonFunctions.getRandomAlphanumericalCode(Constants.CODE_LENGTH);
        wiaProvider = true;
        tradeProvider = false;
        courseName = "Course28102015";
        status = "Active";
        trainingProviderLocations = new ArrayList<>();
        trainingProviderLocations.add(new TrainingProviderLocation(wiaProvider, tradeProvider));
    }

    /**
     * User permissions for training provider
     * @param role  - user role.
     */
    public TrainingProvider(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        tpCreate = Boolean.valueOf(prop.getProperty(TP_CREATE));
        tpView = Boolean.valueOf(prop.getProperty(TP_VIEW));
        tpViewEditButton = Boolean.valueOf(prop.getProperty(TP_VIEW_EDIT));
        tpViewAuditButton = Boolean.valueOf(prop.getProperty(TP_VIEW_AUDIT));
        tpEdit = Boolean.valueOf(prop.getProperty(TP_EDIT));
    }

    /**
     * Converts training provider from WIA to TRADE.
     */
    public void convertToTrade() {
        wiaProvider = false;
        tradeProvider = true;
        trainingProviderLocations = new ArrayList<>();
        trainingProviderLocations.add(new TrainingProviderLocation(wiaProvider, tradeProvider));
    }


    /**
     * make both parameters false and return Location.
     * @return Location
     */
    public TrainingProviderLocation convertToFalse() {
        wiaProvider = false;
        tradeProvider = false;
        trainingProviderLocations = new ArrayList<>();
        trainingProviderLocations.add(new TrainingProviderLocation(wiaProvider, tradeProvider));
        return trainingProviderLocations.get(0);
    }

    //User permissions block.

    public Boolean getProviderCreate() {
        return tpCreate;
    }

    public Boolean getProviderView() {
        return tpView;
    }

    public Boolean getProviderViewEdit() {
        return tpViewEditButton;
    }

    public Boolean getProviderViewAudit() {
        return tpViewAuditButton;
    }

    public Boolean getProviderEdit() {
        return tpEdit;
    }

    public void setFein(String feinNumber) {
        this.fein = feinNumber;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDfa(String newDfa) {
        this.dfa = newDfa;
    }

    public boolean isWiaProvider() {
        return wiaProvider;
    }

    public boolean isTradeProvider() {
        return tradeProvider;
    }

    public List<TrainingProviderLocation> getLocations() {
        return trainingProviderLocations;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDfa() {
        return dfa;
    }

    public String getFein() {
        return fein;
    }

    public String getStatus() {
        return status;
    }

    public String getCourseName() {
        return courseName;
    }
}
