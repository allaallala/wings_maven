package edu.msstate.nsparc.wings.integration.forms.careerReadinessCertification;

import edu.msstate.nsparc.wings.integration.models.participant.CareerReadinessCertification;
import framework.elements.Span;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Career Readiness Certifications -> Create
 */
public class CareerReadinessCertificationCreationForm extends CareerReadinessCertificationBaseForm {

    //Fields
    private String fieldPath = "//select[@id='%1$s']/option[@selected='']";
    private final Span spnSelectedMathematics = new Span(By.xpath(String.format(fieldPath, "selectedMathematicsAssessmentId")),"Selected mathematics");
    private final Span spnSelectedLocating = new Span(By.xpath(String.format(fieldPath, "selectedInformationAssessmentId")),"Selected locating");
    private final Span spnSelectedReading = new Span(By.xpath(String.format(fieldPath, "selectedReadingAssessmentId")),"Selected reading");

    /**
     * Constructor
     */
    public CareerReadinessCertificationCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Career Readiness Certification Creation')]"),
                    "Career Readiness Certification Creation");
    }

    /**
     * Filling all fields for CRC creation
     * @param crc Object with data for filling
     */
    public void fillCRCInformation(CareerReadinessCertification crc) {
        selectParticipant(crc.getParticipant());
        inputDataAdministered(crc.getDateAdministired());
        selectAssessments(crc.getAppliedMathematicsString(), AssessmentTypes.MATH);
        selectAssessments(crc.getLocatingInformationString(), AssessmentTypes.LOC);
        selectAssessments(crc.getReadingForInformationString(), AssessmentTypes.READ);
    }

    /**
     * Finishing creating assessment
     */
    public void finishCreating() {
        clickAndWait(BaseButton.CREATE);
        passParticipationRecalculationPage();
        //new CareerReadinessCertificationDetailsForm().assertIsOpen();
    }

    /**
     * Get text of chosen math assessment
     * @return - text of chosen math assessment
     */
    public String getSelectedMath() {
       return spnSelectedMathematics.getText();
    }
    /**
     * Get text of chosen locating assessment
     * @return - text of chosen locating assessment
     */
    public String getSelectedLoc() {
        return spnSelectedLocating.getText();
    }
    /**
     * Get text of chosen reading assessment
     * @return - text of chosen reading assessment
     */
    public String getSelectedRead() {
        return spnSelectedReading.getText();
    }
}
