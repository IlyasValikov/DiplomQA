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
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getApprovedCard());
        payment.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyPositiveAllFieldValidDeclined() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getDeclinedCard());
        payment.waitNotificationFailure();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    void buyNegativeNumberCard15Symbols() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getFifteenNumberCardNumber());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCardNotInDatabase() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getFakerNumberCardNumber());
        payment.waitNotificationFailure();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth1Symbol() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getOneNumberMonth());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonthOver12() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getThirteenMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00ThisYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getZeroMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeMonth00OverThisYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getPreviousMonthInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getEmptyYear());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }


    @Test
    void buyNegativeYearUnderThisYear() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getPreviousYearInField());
        payment.waitNotificationExpiredError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeYearOverThisYearOn6() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getPlusSixYearInField());
        payment.waitNotificationExpirationDateError();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv1Symbol() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getOneNumberInFieldCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeCvv2Symbols() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getOTwoNumberInFieldCVV());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwner1Word() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getOnlySurnameInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerCyrillic() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getRusName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNumberInName() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getNumberInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }

    @Test
    void buyNegativeOwnerSpecialSymbols() {
        val startPage = new PaymentPage();
        val payment = startPage.goToBuyPage();
        payment.inputData(DataHelper.getSpecialSymbolInFieldName());
        payment.waitNotificationWrongFormat();
        assertEquals("0", SQLHelper.getOrderCount());
    }
}
