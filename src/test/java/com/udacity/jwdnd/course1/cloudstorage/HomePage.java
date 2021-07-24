package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
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

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTabButton;

    @FindBy(id = "credential-create")
    private WebElement credentialCreateButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "credential-submit")
    private WebElement credentialSubmitButton;

    @FindBy(id = "credential-edit")
    private WebElement credentialEditButton;

    @FindBy(id = "credential-delete")
    private WebElement credentialDeleteButton;

    @FindBy(id = "credential-url-display")
    private WebElement credentialUrlDisplay;

    @FindBy(id = "credential-username-display")
    private WebElement credentialUsernameDisplay;

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
        List<WebElement> notesList = driver.findElements(By.id("note-title-display"));
        if (!notesList.isEmpty()) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(this.noteDeleteButton));
            this.noteDeleteButton.click();
        }
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
//      Help with creating list in Selenium: https://www.browserstack.com/guide/findelement-in-selenium
        List<WebElement> notesList = driver.findElements(By.id("note-title-display"));
        return notesList.size() == 0;
    }

    public void createCredential(String url, String username, String password, WebDriver driver) {
        this.credentialsTabButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialCreateButton));
        this.credentialCreateButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialUrlField));
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialSubmitButton));
        this.credentialSubmitButton.click();
    }

    public void updateCredential(String url, String username, String password, WebDriver driver) {
        this.credentialsTabButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialEditButton));
        this.credentialEditButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialUrlField));
    //  Help with clearing fields in Selenium: https://stackoverflow.com/a/5746299
        this.credentialUrlField.clear();
        this.credentialUsernameField.clear();
        this.credentialPasswordField.clear();
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialSubmitButton));
        this.credentialSubmitButton.click();
    }

    public void deleteCredential(WebDriver driver) {
        this.credentialsTabButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(this.credentialDeleteButton));
        this.credentialDeleteButton.click();
    }

    public Credential readFirstCredential(WebDriver driver) {
        this.credentialsTabButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(this.credentialUrlDisplay));
        return new Credential(
                0,
                this.credentialUrlDisplay.getText(),
                this.credentialUsernameDisplay.getText(),
                "",
                "",
                0
        );
    }

    public Boolean checkCredentialsEmpty(WebDriver driver) {
        this.credentialsTabButton.click();
        List<WebElement> credentialsList = driver.findElements(By.id("credential-url-display"));
        return credentialsList.size() == 0;
    }
}
