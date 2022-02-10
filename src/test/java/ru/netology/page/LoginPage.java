package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;


import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");


    public VerificationPage login(String user, String password) {
        loginField.setValue(user);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }

    public LoginPage errorLogin(String user, String password) {
        loginField.setValue(user);
        passwordField.setValue(password);
        loginButton.click();
        return new LoginPage();
    }

    public void clearFields() {

        loginField.sendKeys(Keys.CONTROL + "A");
        loginField.sendKeys(Keys.BACK_SPACE);

        passwordField.sendKeys(Keys.CONTROL + "A");
        passwordField.sendKeys(Keys.BACK_SPACE);
    }

}