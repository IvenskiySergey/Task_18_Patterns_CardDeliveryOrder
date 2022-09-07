package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryCardTest {

    @Test
    void relapDateMeeting() {

        String planningDate1 = DataGenerator.generateDate(4);
        String planningDate2 = DataGenerator.generateDate(6);
        String city = DataGenerator.generateCity("ru");
        String name = DataGenerator.generateName("ru");
        String phone = DataGenerator.generatePhone("ru");

        open("http://localhost:9999/");
        $("span[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("span[data-test-id='date'] input[placeholder]").setValue(planningDate1);
        $("span[data-test-id='name'] input").setValue(name);
        $("span[data-test-id='phone'] input").setValue(phone);
        $("span[class='checkbox__box']").click();
        $(".button_view_extra.button_size_m").click();
        $("div[data-test-id=success-notification] div[class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate1)).shouldBe(Condition.visible);
        open("http://localhost:9999/");
        $("span[data-test-id='city'] input").setValue(city);
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