package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    @FindBy(id = "nav-files-tab")
    private WebElement fileNavText;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(id = "note-create")
    private WebElement noteCreateButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "note-submit")
    private WebElement noteSubmitButton;

    @FindBy(id = "note-edit")
    private WebElement noteEditButton;

    @FindBy(id = "note-delete")
    private WebElement noteDeleteButton;

    @FindBy(id = "note-title-display")
    private WebElement noteTitleDisplay;

    @FindBy(id = "note-description-display")
    private WebElement noteDescriptionDisplay;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public String checkAccess() {
        return fileNavText.getText();
    }

    public void logout() {
        logoutButton.click();
    }

    public void createNote(String title, String description, WebDriver driver) {
        this.notesTabButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.noteCreateButton));
        this.noteCreateButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(this.noteTitleField));
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);

        wait.until(ExpectedConditions.elementToBeClickable(this.noteSubmitButton));
        this.noteSubmitButton.click();
    }

    public void updateNote(String title, String description, WebDriver driver) {
        this.notesTabButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.noteEditButton));
        this.noteEditButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(this.noteTitleField));
        // https://stackoverflow.com/a/5746299
        this.noteTitleField.clear();
        this.noteDescriptionField.clear();
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);

        wait.until(ExpectedConditions.elementToBeClickable(this.noteSubmitButton));
        this.noteSubmitButton.click();
    }

    public void deleteNote(WebDriver driver) {
        this.notesTabButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.noteDeleteButton));
        this.noteDeleteButton.click();
    }

    public Note readFirstNote(WebDriver driver) {
        this.notesTabButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(this.noteTitleDisplay));
        return new Note(
                0,
                this.noteTitleDisplay.getText(),
                this.noteDescriptionDisplay.getText(),
                0
        );
    }

    public Boolean checkNotesEmpty(WebDriver driver) {
        this.notesTabButton.click();
//        https://www.browserstack.com/guide/findelement-in-selenium
        List<WebElement> notesList = driver.findElements(By.id("note-title-display"));
        return notesList.size() == 0;
    }
}
