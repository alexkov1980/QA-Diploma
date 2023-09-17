package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private SelenideElement buyByDebitCardButton = $(byText("Купить"));
    private SelenideElement buyByCreditCardButton = $(byText("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);
    }

    public PaymentFormPageDebit payWithDebitCard() {
        buyByDebitCardButton.click();
        return new PaymentFormPageDebit();
    }

    public PaymentFormPageCredit payWithCreditCard() {
        buyByCreditCardButton.click();
        return new PaymentFormPageCredit();
    }
}
