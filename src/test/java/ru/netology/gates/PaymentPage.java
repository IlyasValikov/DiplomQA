package ru.netology.gates;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class PaymentPage {
    private SelenideElement heading = $(byText("Путешествие дня"));
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public BuyPayment orderCardPage() {
        buyButton.click();
        return new BuyPayment();
    }

    public CreditBuyPayment creditPage() {
        creditButton.click();
        return new CreditBuyPayment();
    }
}