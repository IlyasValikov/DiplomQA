package ru.netology.tests;



import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.datas.DataHelper;
import ru.netology.datas.SQLHelper;
import ru.netology.gates.PaymentPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyPaymentTest {
    @BeforeEach
    public void start() {
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }



    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }



    @Test
    void buyPositiveAllFieldValidApproved() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getApprovedCard();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());

    }

    @Test
    void buyNegativeAllFieldEmpty() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getEmptyCard();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }
    @Test
    void creditNegativeMonth00OverThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear00();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear1Symbol();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolder1Word();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCyrillic() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderCyrillic();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var orderCardPage = startPage.orderCardPage();
        orderCardPage.insertCardData(cardInfo);
        orderCardPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}