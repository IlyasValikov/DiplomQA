package ru.netology.gates;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;



import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;


public class PaymentPage {
    private SelenideElement heading = $$("h2").find(Condition.text("Путешествие дня"));
    private SelenideElement buyButton = $$("button").find(exactText("Купить"));
    private SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));

    public PaymentPage() {
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
