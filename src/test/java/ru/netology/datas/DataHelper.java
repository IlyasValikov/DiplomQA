package ru.netology.datas;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    private static String approvedCard = "4444 4444 4444 4441";
    private static String declinedCard = "4444 4444 4444 4442";

    private DataHelper() {
    }

    public static String getStatusApprovedCard() {
        return "APPROVED";
    }

    public static String getStatusDeclinedCard() {
        return "DECLINED";
    }

    public static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getPreviousMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getZeroMonth() {
        return "00";
    }

    private static String getThirteenMonth() {
        return "13";
    }

    public static String getValidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getValidYearPlusOne() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getPreviousYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getPlusSixYear() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getOwner() {
        return faker.name().fullName();
    }

    public static String getNameRus() {
        return fakerRu.name().lastName() + " " + fakerRu.name().firstName();
    }

    private static String getNameEn() {
        return faker.name().lastName();
    }

    public static String getCVC() {
        return faker.numerify("###");
    }

    private static String getTwoNumber() {
        return faker.numerify("##");
    }

    private static String getOneNumber() {
        return faker.numerify("#");
    }

    private static String getFakerNumberCard() {
        return faker.business().creditCardNumber();
    }

    private static String getFifteenNumber() {
        return approvedCard.substring(0, 18);
    }

    private static String getEmptyField() {
        return " ";
    }

    private static String getSpecialSymbol() {
        return "@#$%^&*()~-+/*?><|";
    }

    public static Card getApprovedCard() {
        return new Card(approvedCard, getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getDeclinedCard() {
        return new Card(declinedCard, getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getEmptyCardNumber() {
        return new Card(getEmptyField(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getOneNumberCardNumber() {
        return new Card(getOneNumber(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getFifteenNumberCardNumber() {
        return new Card(getFifteenNumber(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getFakerNumberCardNumber() {
        return new Card(getFakerNumberCard(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getOneNumberMonth() {
        return new Card(approvedCard, getOneNumber(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getPreviousMonthInField() {
        return new Card(approvedCard, getPreviousMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getZeroMonthInField() {
        return new Card(approvedCard, getZeroMonth(), getValidYearPlusOne(), getOwner(), getCVC());
    }

    public static Card getThirteenMonthInField() {
        return new Card(approvedCard, getThirteenMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static Card getEmptyYear() {
        return new Card(approvedCard, getValidMonth(), getEmptyField(), getOwner(), getCVC());
    }

    public static Card getPreviousYearInField() {
        return new Card(approvedCard, getValidMonth(), getPreviousYear(), getOwner(), getCVC());
    }

    public static Card getPlusSixYearInField() {
        return new Card(approvedCard, getValidMonth(), getPlusSixYear(), getOwner(), getCVC());
    }


    public static Card getSpecialSymbolInFieldName() {
        return new Card(approvedCard, getValidMonth(), getValidYear(), getSpecialSymbol(), getCVC());
    }

    public static Card getNumberInFieldName() {
        return new Card(approvedCard, getValidMonth(), getValidYear(), getTwoNumber(), getCVC());
    }

    public static Card getOnlySurnameInFieldName() {
        return new Card(approvedCard, getValidMonth(), getValidYear(), getNameEn(), getCVC());
    }


    public static Card getOneNumberInFieldCVV() {
        return new Card(approvedCard, getValidMonth(), getValidYear(), getOwner(), getOneNumber());
    }

    public static Card getOTwoNumberInFieldCVV() {
        return new Card(approvedCard, getValidMonth(), getValidYear(), getOwner(), getTwoNumber());
    }

    public static Card getRusName() {
        return new Card(approvedCard, getValidMonth(), getValidYear(), getNameRus(), getCVC());
    }
}
