package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Big Rocks -> Participant Report -> Create
*/
public class BigRocksParticipantReportCreationForm extends BigRocksReportBaseForm {


    /**
     * Default constructor
     */
    public BigRocksParticipantReportCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Participant Report Parameters')]"), "Participant Report Parameters");
    }


}
