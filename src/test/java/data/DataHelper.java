package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {

    }

    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo("vasya", "qwerty123");
    }

    private static String generateRandomLogin() {
        return faker.internet().username();
    }

    private static String generateRandomPassword() {
        return faker.internet().password();
    }

    private static String generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateRendomVerificationCode() {
        return new VerificationCode(faker.nummerify("######"));
    }

    @Value
    public static class AuthInfo {

    }
}
