package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Span;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * To open this form you need to login as Employer or Participant and click on
 * 'Resources and Information' link
 */
public class ResourcesAndInformationForm extends BaseWingsForm {
    public static final TableCell TBC_HELP_INFORMATION = new TableCell("//address/../p", "Help Information");
    private String btnXpath = "//a[@href[contains(.,'%1$s')]]";
    private Button btnLmEmployer = new Button(By.xpath(String.format(btnXpath, "employee-training")), "Learn more employee training");
    private Button btnLmTaxAppeals = new Button(By.xpath(String.format(btnXpath, "tax-appeals")), "Learn more tax appeals");
    private Button btnLmBenefit = new Button(By.xpath(String.format(btnXpath, "benefit-information/appeals")), "Learn more benefit");
    private Button btnLmRre = new Button(By.xpath(String.format(btnXpath, "rapid-response-services")), "Learn more rapid response service");
    private Button btnLmWotc = new Button(By.xpath(String.format(btnXpath, "work-opportunity-tax-credit-program")), "Learn more WOTC");

    private Span spEmployer = new Span(By.xpath("//p[contains(.,'On-the-Job Training')]"), "Employee training section text");
    private Span spTaxAppeals = new Span(By.xpath("//p[contains(.,'Whenever a determination')]"), "Tax appeals section text");
    private Span spBenefit = new Span(By.xpath("//p[contains(.,'You have the right to appeal')]"), "Benefit section text");
    private Span spRre = new Span(By.xpath("//p[contains(.,'Rapid Response')]"), "Rapid response service section text");
    private Span spWotc = new Span(By.xpath("//p[contains(.,'The Work Opportunity')]"), "WOTC section text");

    //TEXT
    private static final String employeeTraining =
            "A special On-the-Job Training program is funded by a National Emergency Grant and is specifically for "
                    + "new workers who have been unemployed at least 19 weeks. The Mississippi Department of Employment Security "
                    + "may pay up to half their wages while they train.";
    private static final String taxAppeals = "Whenever a determination is made that affects your tax liability, a written notice will be sent to you."
            + " The determination will provide appeal rights if you should disagree with a decision.";
    private static final String benefits =
            "You have the right to appeal the Notice of Nonmonetary Determination which advises whether or not your "
                    + "former employee is eligible for unemployment benefits.";
    private static final String rre =
            "Rapid Response is an early intervention service that assists both employers and employees affected by "
                    + "layoffs or plant closures. It provides access to user-friendly resources and information to help transition"
                    + " affected workers into reemployment.";
    private static final String wotc =
            "The Work Opportunity Tax Credit (WOTC) Program provides businesses with substantial tax savings for "
                    + "each new hire from nine targeted groups of job candidates.";

    /**
     * Default constructor
     */
    public ResourcesAndInformationForm() {
        super(By.xpath("//h2[contains(.,'Resources and Information')]"), "Resources and Information");
    }

    /**
     * Check [Learn more] buttons on the page.
     */
    public void checkButtonsPresent() {
        CustomAssertion.softTrue("Learn more employee training is not present", btnLmEmployer.isPresent());
        CustomAssertion.softTrue("Learn more tax appeals is not present", btnLmTaxAppeals.isPresent());
        CustomAssertion.softTrue("Learn more benefit is not present", btnLmBenefit.isPresent());
        CustomAssertion.softTrue("Learn more rapid response service is not present", btnLmRre.isPresent());
        CustomAssertion.softTrue("Learn more WOTC is not present", btnLmWotc.isPresent());
    }

    /**
     * Check text in the sections
     */
    public void checkTextSections() {
        info(spEmployer.getText());
        info(employeeTraining);
        CustomAssertion.softTrue("Incorrect text in the employer training section", spEmployer.getText().contains(employeeTraining));
        CustomAssertion.softTrue("Incorrect text in the tax appeals section", spTaxAppeals.getText().contains(taxAppeals));
        CustomAssertion.softTrue("Incorrect text in the benefit section", spBenefit.getText().contains(benefits));
        CustomAssertion.softTrue("Incorrect text in the rre section", spRre.getText().contains(rre));
        CustomAssertion.softTrue("Incorrect text in the WOTC section", spWotc.getText().contains(wotc));
    }
}
