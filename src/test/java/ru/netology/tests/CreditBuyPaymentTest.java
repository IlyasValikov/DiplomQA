package ru.netology.tests;



import org.junit.jupiter.api.*;

import ru.netology.datas.DataHelper;
import ru.netology.datas.SQLHelper;
import ru.netology.gates.CreditBuyPayment;
import ru.netology.gates.PaymentPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditBuyPaymentTest {
    PaymentPage startPage = open("http://localhost:8080/", PaymentPage.class);


    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }


    @Test
    void creditPositiveAllFieldValidApproved() {
        startPage.creditPage();
        var cardInfo = DataHelper.getApprovedCard();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditPositiveAllFieldValidDeclined() {
        startPage.creditPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditNegativeAllFieldEmpty() {
        startPage.creditPage();
        var cardInfo = DataHelper.getEmptyCard();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat4Fields();

        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void creditNegativeNumberCard15Symbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCardNotInDatabase() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth1Symbol() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonthOver12() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00ThisYear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00OverThisYear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYear00();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear1Symbol() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYear1Symbol();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearUnderThisYear() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearOverThisYearOn6() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv1Symbol() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv2Symbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwner1Word() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolder1Word();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerCyrillic() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderCyrillic();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerNumeric() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerSpecialSymbols() {
        startPage.creditPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var creditPage = new CreditBuyPayment();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
