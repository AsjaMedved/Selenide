package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.nio.channels.Selector;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class BankTest {
    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void successfulSendingOfData() { //успешная отправка данных
        String planingDate = generateDate(3, "dd.MM.yyyy");

        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $$("[data-test-id='date'] input").find(Condition.visible).press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE).setValue(planingDate).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button").click();
      //  $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[data-test-id='notification']").find(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на"+ planingDate));

    }

    }
