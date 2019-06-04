package edu.msstate.nsparc.wings.integration.forms.callIn;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Wagner-Peyser -> Call-Ins -> Search -> click the any records.
 * Created by a.vnuchko on 30.06.2016.
 */
public class CallInDetailForm extends CallInBaseForm {

    //Web elements
    private String xpath = "//td[contains(.,'%1$s')]/following-sibling::td";
    private TableCell tbcParticipant = new TableCell(By.xpath(String.format(xpath, "Participant:")),"Participant");
    private TableCell tbcJobOrder = new TableCell(By.xpath(String.format(xpath, "Job Order:")),"Job Order");
    private TableCell tbcCreationDate = new TableCell(By.xpath(String.format(xpath, "Creation Date:")),"Creation Date");
    private TableCell tbcResultDate = new TableCell(By.xpath(String.format(xpath, "Result Date:")),"Result Date");
    private TableCell tbcType = new TableCell(By.xpath(String.format(xpath, "Type:")),"Type");
    private TableCell tbcResult = new TableCell(By.xpath(String.format(xpath, "Result:")),"Result");

    /**
     * Constructor of the form with specified locator
     */
    public CallInDetailForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Call In Detail')]"), "Call In Detail");
    }

    /**
     * Validate data on the participantSSDetails form
     * @param participant - participant
     * @param jobOrder - job order.
     * @param result - process result
     */
    public void validateCallInData(Participant participant, JobOrder jobOrder, String result) {
        CustomAssertion.softAssertContains(tbcParticipant.getText(), participant.getFirstName(), "Incorrect participant name");
        CustomAssertion.softAssertContains(tbcJobOrder.getText(), jobOrder.getJobTitle(), "Incorrect job order name");
        CustomAssertion.softAssertContains(tbcCreationDate.getText(), CommonFunctions.getCurrentDate(), "Incorrect creation date");
        CustomAssertion.softAssertContains(tbcResultDate.getText(), CommonFunctions.getCurrentDate(), "Incorrect result date");
        CustomAssertion.softAssertContains(tbcType.getText(), "Phone Call", "Incorrect type");
        CustomAssertion.softAssertContains(tbcResult.getText(), result, "Incorrect result");
    }
}
