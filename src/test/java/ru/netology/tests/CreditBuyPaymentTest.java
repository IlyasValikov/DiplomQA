package ru.netology.tests;


import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.netology.datas.DataHelper;
import ru.netology.datas.SQLHelper;
import ru.netology.gates.PaymentPage;



import static com.codeborne.selenide.Selenide.*;
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
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditPositiveAllFieldValidDeclined() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    void creditNegativeNumberCard15Symbols() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getFifteenNumberCardNumber());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCardNotInDatabase() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getFakerNumberCardNumber());
        payment.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth1Symbol() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getOneNumberMonth());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonthOver12() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getThirteenMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00ThisYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getZeroMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeMonth00OverThisYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getPreviousMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getEmptyYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }


    @Test
    void creditNegativeYearUnderThisYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getPreviousYearInField());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeYearOverThisYearOn6() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getPlusSixYearInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv1Symbol() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getOneNumberInFieldCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeCvv2Symbols() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getOTwoNumberInFieldCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwner1Word() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getOTwoNumberInFieldCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerCyrillic() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getRusName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNumberInName() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getNumberInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void creditNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentPage();
        val payment = startPage.goToCreditPage();
        payment.inputData(DataHelper.getSpecialSymbolInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
