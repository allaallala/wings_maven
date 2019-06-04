package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This page is displayed while previewing Assessment record
 */
public class PreviewAssessment extends BaseWingsForm {
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private String xpathPreview = "//div[@id='previewPage']" + detailPath;
    private TableCell tbcParticipant = new TableCell(By.xpath(String.format(xpathPreview, "Participant")), "Participant");
    private TableCell tbcScore = new TableCell(By.xpath(String.format(xpathPreview, "Score")), "Score");
    private TableCell tbcFunctionalArea = new TableCell(By.xpath(String.format(xpathPreview, "Functional Area")), "Functional Area");
    public static final Button BTN_CLOSE_PREVIEW = new Button(By.xpath("//td[@id='closePreview']"), "Close Preview");

    /**
     * Default constructor
     */
    public PreviewAssessment() {
        super(By.xpath("//h1[contains(.,'Assessment')]"), "Preview Assessment");
    }

    /**
     * Validating displayed Assessment information
     *
     * @param assessment Object with expected data
     */
    public void validatePreviousJobInformation(Assessment assessment) {
        CustomAssertion.softAssertContains(tbcParticipant.getText(), assessment.getParticipant().getFullName(), "Incorrect participant full name");
        CustomAssertion.softAssertContains(tbcScore.getText(), assessment.getScore(), "Incorrect score");
        CustomAssertion.softAssertContains(tbcFunctionalArea.getText(), assessment.getFunctionalArea(), "Incorrect functional area");
    }
}
