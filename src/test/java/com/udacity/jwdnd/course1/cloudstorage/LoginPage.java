package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submitButton")
    private WebElement submitButton;

    @FindBy(id = "loginTitle")
    private WebElement loginTitle;

    @FindBy(id = "logoutMessage")
    private WebElement logoutMessage;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }

    public String checkAccess() {
        return loginTitle.getText();
    }

    public String checkError() {
        return loginTitle.getText();
    }

    public String checkLogout() {
        return logoutMessage.getText();
    }
}
