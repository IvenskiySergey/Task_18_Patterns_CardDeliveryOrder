package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTest {

    private static Faker faker;

    @BeforeAll
    static void setUPAll() {
        faker = new Faker(new Locale("ru"));
    }

    String name = faker.name().name();
    String phone = faker.phoneNumber().phoneNumber();

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void relapDateMeeting() {

        String planningDate1 = generateDate(4);
        String planningDate2 = generateDate(6);

        open("http://localhost:9999/");
        $("span[data-test-id='city'] input").setValue("Севастополь");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("span[data-test-id='date'] input[placeholder]").setValue(planningDate1);
        $("span[data-test-id='name'] input").setValue(name);
        $("span[data-test-id='phone'] input").setValue(phone);
        $("span[class='checkbox__box']").click();
        $(".button_view_extra.button_size_m").click();
        $("div[data-test-id=success-notification] div[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate1)).shouldBe(Condition.visible);
        $("span[data-test-id='city'] input").setValue("Севастополь");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("span[data-test-id='date'] input[placeholder]").setValue(planningDate2);
        $("span[data-test-id='name'] input").setValue(name);
        $("span[data-test-id='phone'] input").setValue(phone);
        $("span[class='checkbox__box']").click();
        $(".button_view_extra.button_size_m").click();
        $("div[data-test-id=replan-notification] div[class='notification__content']").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible);
        $("div[data-test-id=replan-notification] button").click();
        $("div[data-test-id=success-notification] div[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate2)).shouldBe(Condition.visible);
    }
}