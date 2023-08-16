package ru.netology.datas;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private String generateDate(int addDays, int addMonths, int addYears, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo("4444444444444441", getShiftedMonth(2), getShiftedYear(0), "Ivan Vasilev", "888");
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444444444444442", getShiftedMonth(3), getShiftedYear(1), "Ivan Vasilev", "777");
    }

    public static CardInfo getEmptyCard() {
        return new CardInfo("", "", "", "", "");
    }

    public static String getShiftedMonth(int monthCount) {
        return LocalDate.now().plusMonths(monthCount).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getShiftedYear(int yearCount) {
        return LocalDate.now().plusYears(yearCount).format(DateTimeFormatter.ofPattern("YY"));
    }

    public static CardInfo getNumberCard15Symbols() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        var number = faker.number().digits(15);
        return new CardInfo(number, month, year, holder, cvv);
    }

    public static CardInfo getCardNotInDatabase() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("1444444444444444", month, year, holder, cvv);
    }

    public static CardInfo getCardMonth1Symbol() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = faker.number().digit();
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardMonthOver12() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", "13", year, holder, cvv);
    }

    public static CardInfo getCardMonth00ThisYear() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var year = getShiftedYear(0);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", "00", year, holder, cvv);
    }

    public static CardInfo getCardMonth00OverThisYear() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", "00", year, holder, cvv);
    }

    public static CardInfo getCardYear1Symbol() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = faker.number().digit();
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardYearOverThisYearOn6() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(6);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardYearUnderThisYear() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(-1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardYear00() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, "00", holder, cvv);
    }

    public static CardInfo getCardCvv1Symbol() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(1);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardCvv2Symbols() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(2);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardHolder1Word() {
        var faker = new Faker();
        var holder = faker.name().firstName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardHolderCirillic() {
        var faker = new Faker(new Locale("ru"));
        var holder = faker.name().firstName() + " " + faker.name().lastName();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardHolderNumeric() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " " + faker.number().digit();
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    public static CardInfo getCardSpecialSymbols() {
        var faker = new Faker();
        var holder = faker.name().firstName() + " %$ * &";
        var month = getShiftedMonth(1);
        var year = getShiftedYear(1);
        var cvv = faker.number().digits(3);
        return new CardInfo("4444444444444441", month, year, holder, cvv);
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String cardHolder;
        String cvc;
    }
}