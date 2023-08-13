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

public class CreditBuyPaymentTest {
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
    void creditPositiveAllFieldValidApproved() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditPositiveAllFieldValidDeclined() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditNegativeAllFieldEmpty() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getEmptyCard());
        payment.waitNotificationWrongFormat4Fields();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeNumberCard15Symbols() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getNumberCard15Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCardNotInDatabase() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardNotInDatabase());
        payment.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth1Symbol() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonthOver12() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonthOver12());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00ThisYear() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00ThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00OverThisYear() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardMonth00OverThisYear());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear00() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYear00());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear1Symbol() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYear1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearUnderThisYear() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYearUnderThisYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearOverThisYearOn6() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardYearOverThisYearOn6());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv1Symbol() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardCvv1Symbol());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv2Symbols() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardCvv2Symbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwner1Word() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolder1Word());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerCirillic() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getOwnerByCyrillic());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerNumeric() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardHolderNumeric());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerSpecialSymbols() {
        val startPage = new Payment();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getCardSpecialSymbols());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
