package ru.netology.test;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.netology.data.DataBase;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataHelperRegistrationTest {

    DataBase dataBase = new DataBase("jdbc:mysql://localhost:3306/mySqldb", "dbUser", "ps34df");

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @AfterAll
    void clearData(){
        dataBase.clearData();
    }

    @Test
    void shouldEnterInPersonalAccount() {

        var user = DataHelper.getUser();

        var loginPage = new LoginPage();
        var verificationPage = loginPage.login(user.getLogin(), user.getPassword());
        var dashboardPage = verificationPage.validVerify(dataBase.getAuthCode(user.getLogin()));
        assertEquals("Личный кабинет", dashboardPage.getAccount());
    }

    @Test
    void shouldBlockAccountAfterWrongPass() {

        var user = DataHelper.getUser();
        var loginPage = new LoginPage();

        for (int i = 0; i < 3; i++) {
            loginPage.clearFields();
            loginPage.errorLogin(user.getLogin(), "wrong_password_123");
        }

        String accStatus = dataBase.getAccountStatus(user.getLogin());

        loginPage.assertNotificationText();
        assertEquals("blocked", accStatus);

    }

}
