package ru.netology.patterns;

import com.codeborne.selenide.Condition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class FormCardTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(firstMeetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(firstMeetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(".button").click();
        $("[data-test-id=replan-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать?\n" +
                        "\n" +
                        "Перепланировать"));
        $("[role=button]").click();
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(secondMeetingDate);
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @Test
    @DisplayName("Should Fail Validation For Date (+ 0 Days) With Error Message")
    void shouldFailValidationForDateWithErrorMessage0() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 0;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("Should Fail Validation For Date (+ 1 Day) With Error Message")
    void shouldFailValidationForDateWithErrorMessage1() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 1;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("Should Fail Validation For Date(+2 Days) With Error Message")
    void shouldFailValidationForDateWithErrorMessage2() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 2;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName("Should Fail Validation For City With Error Message 1")
    void shouldFailValidationForCityWithErrorMessage1() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 2;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue("Лангепас");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should Fail Validation For City With Error Message 2")
    void shouldFailValidationForCityWithErrorMessage2() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue("");
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should Fail Validation For Name With Error Message 1")
    void shouldFailValidationForNameWithErrorMessage1() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var invalidUser = DataGenerator.Registration.generateUser("en");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(invalidUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Should Fail Validation For Name With Error Message 2")
    void shouldFailValidationForNameWithErrorMessage2() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue("57657665/*/-*?*?::?&&^^*%$%#");
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Should Fail Validation For Name With Error Message 3")
    void shouldFailValidationForNameWithErrorMessage3() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue("");
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should Fail Validation For Phone With Error Message 1")
    void shouldFailValidationForPhoneWithErrorMessage1() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue("+7901234567");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    @DisplayName("Should Fail Validation For Phone With Error Message 2")
    void shouldFailValidationForPhoneWithErrorMessage2() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue("");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Should Fail Validation For All Data With Error Message")
    void shouldFailValidationForAllDataWithErrorMessage() {
        var validUser = DataGenerator.Registration.generateUser("en");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue("+44444");
        $(".checkbox").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Should Fail Validation For Click Checkbox With Error Message")
    void shouldFailValidationForClickCheckboxWithErrorMessage() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var meetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] .input__control").setValue(meetingDate);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue("+9130002211");
        $(".button").click();
        $(".checkbox.input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}


