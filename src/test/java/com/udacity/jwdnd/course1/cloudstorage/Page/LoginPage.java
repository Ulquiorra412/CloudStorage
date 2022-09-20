package com.udacity.jwdnd.course1.cloudstorage.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    @FindBy(id = "signupSuccess")
    private WebElement alertSignupSuccess;

    @FindBy(id = "alertError")
    private WebElement alertError;

    @FindBy(id="alertLogout")
    private WebElement alertLogout;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonLogin")
    private WebElement buttonLogin;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        buttonLogin.submit();
    }

    public String getSignupSuccessAlert() {
        if (driver.findElements(By.id("alertSignupSuccess")).size() > 0) {
            return alertSignupSuccess.getText();
        }
        return null;
    }

    public String getErrorAlert() {
        if (driver.findElements(By.id("alertError")).size() > 0) {
            return alertError.getText();
        }
        return null;
    }

    public String getLogoutAlert() {
        if (driver.findElements(By.id("alertLogout")).size() > 0) {
            return alertLogout.getText();
        }
        return null;
    }
}
