package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections;

import org.openqa.selenium.By;

public class DocumentStaffSection extends BaseParticipantDetailsForm {

    public DocumentStaffSection() {
        super(By.xpath("//a[contains(.,'Documents')]"), "Documents Section");
    }




}
