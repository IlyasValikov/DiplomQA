package ru.netology.tests;



import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import ru.netology.datas.DataHelper;
import ru.netology.datas.SQLHelper;
import ru.netology.gates.PaymentPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreditBuyPaymentTest {
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
    void creditPositiveAllFieldValidApproved() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getApprovedCard();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditPositiveAllFieldValidDeclined() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getDeclinedCard();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditNegativeAllFieldEmpty() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getEmptyCard();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat4Fields();

        assertEquals("0", SQLHelper.getOrderCount());

    }

    @Test
    void creditNegativeNumberCard15Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getNumberCard15Symbols();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCardNotInDatabase() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardNotInDatabase();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth1Symbol();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonthOver12() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonthOver12();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00ThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00ThisYear();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00OverThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardMonth00OverThisYear();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear00();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYear1Symbol();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearUnderThisYear() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearUnderThisYear();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearOverThisYearOn6() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardYearOverThisYearOn6();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv1Symbol() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv1Symbol();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv2Symbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardCvv2Symbols();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwner1Word() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolder1Word();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerCyrillic() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderCyrillic();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerNumeric() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardHolderNumeric();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerSpecialSymbols() {
        var startPage = new PaymentPage();
        var cardInfo = DataHelper.getCardSpecialSymbols();
        var creditPage = startPage.creditPage();
        creditPage.insertCardData(cardInfo);
        creditPage.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
