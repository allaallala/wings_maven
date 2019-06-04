package edu.msstate.nsparc.wings.integration.forms.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingCourse.TrainingCourseSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened from Training Provider creation form by clicking on 'Add' button in Location section
 */
public class TrainingProviderLocationForm extends BaseWingsForm {

    private TextBox txbProviderLocationName = new TextBox("id=tmpLocation.locationName", "Provider Location Name");
    private TextBox txbProviderLocationCode = new TextBox("id=tmpLocation.locationCode", "Provider Location Code");
    private TextBox txbLineOne = new TextBox("id=tmpLocation.contactInformation.addressByResidenceAddr.lineOne", "Line One");
    private TextBox txbCity = new TextBox("id=tmpLocation.contactInformation.addressByResidenceAddr.city", "City");
    private ComboBox cmbState = new ComboBox("id=tmpLocation.contactInformation.addressByResidenceAddr.state", "State");
    private TextBox txbZipCode = new TextBox("id=tmpLocation.contactInformation.addressByResidenceAddr.zipcode", "Zip Code");
    private ComboBox cmbCounty = new ComboBox("id=tmpLocation.contactInformation.addressByResidenceAddr.county", "County");
    private TextBox txbContactName = new TextBox("id=tmpLocation.contactName", "Contact name");
    private TextBox txbContactTitle = new TextBox("id=tmpLocation.contactTitle", "Contact title");
    private TextBox txbPhoneNumber = new TextBox("id=tmpLocation.contactInformation.primaryPhone", "Phone Number");

    private Button btnWiaCoursesLookUp = new Button("css=div[id='wiaCourseLookup'] button", "WIA Courses Lookup");
    private Button btnTradeCoursesLookUp = new Button("css=div[id='tradeCourseLookup'] button", "TRADE Courses Lookup");
    private Button btnAdd = new Button("id=add", "Add");

    /**
     * Default constructor
     */
    public TrainingProviderLocationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Provider Creation')]"), "Training Provider Create Location");
    }

    public TrainingProviderLocationForm(String type) {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Provider Edit')]"), "Training Provider Location for " + type);
    }

    /**
     * This method is used for selecting random course from WIA look-up
     */
    public void selectRandomWiaCourse() {
        btnWiaCoursesLookUp.clickAndWait();
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.performSearchAndReturnFirst();
    }

    /**
     * This method is used for selecting random course from TRADE look-up
     */
    public void selectRandomTradeCourse() {
        btnTradeCoursesLookUp.clickAndWait();
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.performSearchAndReturnFirst();
    }

    /**
     * This method is used for selecting random course with expected Active status from look-up
     *
     * @param active Indicates if course should be active
     */
    public void selectRandomWiaCourse(boolean active) {
        btnWiaCoursesLookUp.clickAndWait();
        TrainingCourseSearchForm trainingCourseSearchForm = new TrainingCourseSearchForm();
        trainingCourseSearchForm.performSearchAndReturnFirst(active);
    }

    /**
     * This method is used for standard filling form fields and clicking on add button
     *
     * @param location - location
     */
    public void addLocation(TrainingProviderLocation location) {
        if (location.isWiaLocation()) {
            selectRandomWiaCourse();
        }
        if (btnTradeCoursesLookUp.isPresent()) {
            selectRandomTradeCourse();
        }

        fillOtherInformation(location);
        clickAdd();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }

    /**
     * This method is used for filling all information on form except Course
     *
     * @param location Provider Location Code
     */
    public void fillOtherInformation(TrainingProviderLocation location) {
        inputLocationName(location.getName());
        if (txbProviderLocationCode.isPresent()) {
            txbProviderLocationCode.type(location.getCode());
        }
        txbLineOne.type(location.getLineOne());
        txbCity.type(location.getCity());
        selectState(location.getState());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbZipCode.type(location.getZipCode());
        cmbCounty.select(location.getCounty());
        txbContactName.type(location.getContactName());
        txbContactTitle.type(location.getContactTitle());
        txbPhoneNumber.type(location.getPhoneNumber());
    }

    /**
     * This method is used for filling all information on form except Course
     */
    public void fillOtherInformation() {
        TrainingProviderLocation location = new TrainingProviderLocation();
        fillOtherInformation(location);
    }

    /**
     * Input location name
     *
     * @param locationName - location name
     */
    public void inputLocationName(String locationName) {
        txbProviderLocationName.type(locationName);
    }

    /**
     * Select state
     *
     * @param value - state value
     */
    public void selectState(String value) {
        cmbState.select(value);
    }

    /**
     * Select county
     *
     * @param present - true, if county combo box present on page
     */
    public void checkCountyPresent(Boolean present) {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (present) {
            cmbCounty.isPresent();
        } else {
            cmbCounty.assertIsNotPresent();
        }
    }

    /**
     * Click [Add] button.
     */
    public void clickAdd() {
        btnAdd.clickAndWait();
    }

    /**
     * Click WIA course look up button.
     */
    public void clickCourseLookup() {
        btnWiaCoursesLookUp.clickAndWait();
    }
}
