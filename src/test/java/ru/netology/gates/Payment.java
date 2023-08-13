package ru.netology.gates;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class Payment {
    private SelenideElement heading = $("h2").shouldHave(text("Путешествие дня"));
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public Payment() {
        heading.shouldBe(visible);
    }

    public BuyPayment goToBuyPage() {
        buyButton.click();
        return new BuyPayment();
    }

    public CreditBuyPayment goToCreditPage() {
        creditButton.click();
        return new CreditBuyPayment();
    }
}
