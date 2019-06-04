package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This form can be opened from any entity participantSSDetails form by clicking on 'Notes' button
 */
public class NotesForm extends BaseWingsForm {

    private Link lnkCloseNotes = new Link("css=span.float-right > a", "Close Notes");
    private Div dvNotesList = new Div("css=div.note-scroller", "List of notes");
    private Link lnkNotes = new Link("css=span#LinkNotesDiv a", "Notes");
    private TextBox txbNote = new TextBox("id=noteEntry", "Note text");
    private Button btnPost = new Button("css=input[value='Post']", "Post");
    private Button btnPostNewNote = new Button(By.xpath("//input[@value='Post']"), "Post button");
    private Link lnkPrintable = new Link(By.xpath("//a[@title='Printable Version']"), "Printable Version");
    private Span spNoteValue = new Span(By.xpath("//div[@class='note-header']/following-sibling::span"), "Note");
    private TextArea taEditedEntry = new TextArea("id=editedText", "Edited text");
    private RadioButton rbFirstNote = new RadioButton("//input[@type='radio']", "The First Note");
    private Div dvNotesHeader = new Div(By.xpath("//div[@id='innerNotePane']//td[contains(.,'NOTES FOUND')]"), "Notes header");
    private String addedDivPathText = "//div[@class='note']/span[contains(.,'%1$s')]";

    private String validateXpath = "//table[@id='arresults-table']//tbody/tr[1]/td[%1$d]";
    private TableCell tbcPostName = new TableCell(By.xpath(String.format(validateXpath, 2)), "Post name");
    private TableCell tbcPostInfo = new TableCell(By.xpath(String.format(validateXpath, 3)), "Post information");

    /**
     * Default constructor
     */
    public NotesForm() {
        super(By.xpath("//h1[contains(.,'Notes')]"), "Notes");
    }

    /**
     * This method is used for adding note with provided text
     *
     * @param text Note text
     */
    public void addNote(String text) {
        clickNotesLink();
        txbNote.waitForIsElementPresent();
        inputNote(text);
        btnPost.click();
        closeNotes();

    }

    /**
     * Add note without clicking [Notes] link
     *
     * @param text - text to type
     */
    public void addNoteWithoutClosing(String text) {
        txbNote.waitForIsElementPresent();
        inputNote(text);
        btnPost.click();
    }

    /**
     * This method is used for check if Note was added
     *
     * @param text Note text that should be present
     * @return True if note was added
     */
    public boolean checkNote(String text) {
        clickNotesLink();
        txbNote.waitForIsElementPresent();
        return checkAddedText(text);
    }

    /**
     * Check added note text
     *
     * @param text - text to check
     * @return true if text is present on the page.
     */
    public Boolean checkAddedText(String text) {
        Div addedTextDiv = new Div(By.xpath(String.format(addedDivPathText, text)), "Added text for notes");
        return addedTextDiv.isPresent();
    }

    /**
     * Edit note value
     *
     * @param text - text to type
     */
    public void editNote(String text) {
        spNoteValue.doubleClick();
        inputNote(text);
    }

    /**
     * Edit is impossible
     */
    public void editImpossible() {
        spNoteValue.doubleClick();
        taEditedEntry.waitForNotVisible();
    }

    /**
     * Click [Notes] link
     */
    public void clickNotesLink() {
        lnkNotes.click();
    }

    /**
     * Click [Printable] link
     */
    public void clickPrintable() {
        lnkPrintable.clickAndWait();
    }

    /**
     * Click [Close] link
     */
    public void closeNotes() {
        if (lnkCloseNotes.isPresent()) {
            lnkCloseNotes.click();
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
            lnkNotes.waitForIsElementPresent();
        }
    }

    /**
     * Click radio button on the first note
     */
    public void clickFirstNoteRb() {
        rbFirstNote.click();
    }

    /**
     * Check, that first note is not present.
     */
    public void checkFirstNoteNotPresent() {
        rbFirstNote.assertIsNotPresent();
    }

    /**
     * Input note data
     *
     * @param note - note
     */
    public void inputNote(String note) {
        txbNote.waitForIsElementPresent();
        txbNote.type(note);
    }

    /**
     * Input new value for a note
     *
     * @param newValue - new value
     */
    public void inputEditedNote(String newValue) {
        taEditedEntry.type(newValue);
    }

    /**
     * Get notes text
     *
     * @return notes text.
     */
    public String getNotesText() {
        return dvNotesList.getText();
    }

    /**
     * Click [Post new note] button.
     */
    public void postNewNote() {
        btnPostNewNote.clickAndWait();
    }

    /**
     * Get notes count on page.
     *
     * @return notes count.
     */
    public String getNotesCount() {
        info(dvNotesHeader.getText());
        return CommonFunctions.regexGetMatch(dvNotesHeader.getText(), "\\d{1,}");
    }

    /**
     * Check post button present.
     *
     * @param present - [Post] button is present or not on the page.
     */
    public void checkPostButtonPresent(Boolean present) {
        if (present) {
            btnPost.assertIsPresentAndVisible();
            btnPost.clickAndWait();
        } else {
            btnPost.assertIsNotPresent();
        }
    }

    /**
     * validate notes data
     *
     * @param postName - post name
     * @param date     - post date
     */
    public void validateNoteData(String postName, String date) {
        CustomAssertion.softTrue("Incorrect post name", tbcPostName.getText().contains(postName));
        CustomAssertion.softTrue("Incorrect post information", tbcPostInfo.getText().contains(date));
    }
}
