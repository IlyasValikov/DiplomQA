package ru.netology.tests;


import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.datas.DataHelper;
import ru.netology.datas.SQLHelper;
import ru.netology.gates.Payment;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyPaymentTest {
    public static String url = System.getProperty("sut.url");

    @BeforeEach
    public void openPage() {
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.clearDB();
    }

    @Test
    void buyPositiveAllFieldValidApproved() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyNegativeAllFieldEmpty() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getNumberCard15Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00OverThisYear() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear00() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear1Symbol() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearUnderThisYear() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCyrillic() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getOwnerByCyrillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerNumeric() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        val startPage = new Payment();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
