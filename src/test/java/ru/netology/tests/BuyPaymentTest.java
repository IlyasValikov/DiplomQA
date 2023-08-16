package ru.netology.tests;



import org.junit.jupiter.api.*;
import ru.netology.datas.DataHelper;
import ru.netology.datas.SQLHelper;
import ru.netology.gates.BuyPayment;
import ru.netology.gates.PaymentPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyPaymentTest {
    PaymentPage startPage = open("http://localhost:8080/", PaymentPage.class);



    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }



    @Test
    void buyPositiveAllFieldValidApproved() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getApprovedCard();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());

    }

    @Test
    void buyNegativeAllFieldEmpty() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getEmptyCard();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }
    @Test
    void creditNegativeMonth00OverThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear00();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYear1Symbol();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolder1Word();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCyrillic() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderCirillic();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        startPage.orderCardPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var orderCardPage = new BuyPayment();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}