package tests;

import data.DataHelper;
import data.SQLHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanDatabase;

public class BankTest {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithRandomAddingToBase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisiblity();
    }

    @Test
    @DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldBeErrorNotificationIfLoginWithExistUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisiblity();

    }


    @Test
    @DisplayName("Should block page after tree incorrect Password")
    @SneakyThrows
    void shouldBlockPageAfterTreeIncorrectPassword() {
        String name = DataHelper.getIncorrectPassword().getLogin();

        for (int count = 1; count <= 4; count++) {
            open("http://localhost:9999");
            var loginPage = new LoginPage();
            var authInfo = DataHelper.getIncorrectPassword();
            var verificationPage = loginPage.validLogin(authInfo);
            var verificationCode = SQLHelper.getVerificationCode();
            if (count < 4) {
                verificationPage.validVerify(verificationCode.getCode());
            } else if (count == 4) {
                verificationPage.shouldReturnAnErrorMessage(verificationCode.getCode());
            }
        }
    }
}