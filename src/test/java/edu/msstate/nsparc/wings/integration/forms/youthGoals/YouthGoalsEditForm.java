package edu.msstate.nsparc.wings.integration.forms.youthGoals;

import org.openqa.selenium.By;

/**
This form is opened via Participants -> WIA -> Youth Goals -> Search for record -> Open record -> Click on Edit
 */
public class YouthGoalsEditForm extends YouthGoalsBaseForm {

    /**
     * Default constructor
     */
    public YouthGoalsEditForm() {
            super(By.xpath("//span[@id='breadCrumb'][contains(.,'Youth Goal Edit')]"), "Youth Goal Edit");
    }
}
