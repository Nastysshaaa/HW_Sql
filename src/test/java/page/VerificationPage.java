package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification");

    public void verifyVerificationPageVisiblity() {
        codeField.shouldBe(visible);
    }

    public void verifyErrorNotificationVisiblity() {
        errorNotification.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }

    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }

    public void shouldReturnAnErrorMessage(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        errorNotification
                .shouldBe(visible)
                .shouldHave(Condition.text("Ошибка"))
                .shouldHave(Condition.text("Превышено количество попыток ввода кода!"));
    }
}