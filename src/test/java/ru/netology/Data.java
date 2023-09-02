        package ru.netology;
        import com.github.javafaker.Faker;

        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.Locale;

public class Data {
    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    private Data() {
    }

    public static String getFirstCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getSecondCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getFirstCardStatus() {
        return "APPROVED";
    }

    public static String getSecondCardStatus() {
        return "DECLINED";
    }

    public static String getEmptyCardNumber() {
        return "";
    }

    public static String getWrongCardNumber() {
        return "1111 1111 1111 1111";
    }

    public static String getCardNumberWith15Digits() {
        return "1111 1111 1111 111";
    }

    public static String getCardNumberWith1Digit() {
        return "1";
    }

    public static String getCardNumberWithText() {
        return "раз два";
    }
    public static String getCardNumberWithChars() {
        return "!?";
    }

    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }

    public static String getEmptyMonth() {
        return "";
    }

    public static String getMonthOver12() {
        return "13";
    }

    public static String getZeroMonth() {
        return "00";
    }

    public static String getInvalidFormatMonth() {
        return "1";
    }

    public static String getMonthWithText() {
        return "октябрь";
    }

    public static String getValidYear() {
        String validYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return validYear;
    }

    public static String getEmptyYear() {
        return "";
    }

    public static String getPastYear() {
        return "13";
    }

    public static String getInvalidFormatYear() {
        return "3";
    }

    public static String getYearWithText() {
        return "девяносто";
    }

    public static String getValidOwner() {
        return fakerEn.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getEmptyOwner() {
        return "";
    }

    public static String getOnlyNameOwner() {
        return "Ivan";
    }

    public static String getLowercaseLettersOwner() {
        return "ivan ivanov";
    }

     public static String getRedundantDataOwner() {
        return "Ivanov Ivan Ivanovich";
    }

    public static String getCyrillicDataOwner() {
        return "Иван Иванов";
    }

    public static String getTwoAlphabetsDataOwner() {
        return "Иван Ivanov";
    }

    public static String getOwnerWithDigits() {
        return "12345";
    }

    public static String getOwnerWithSpecialChars() {
        return "!?*";
    }

    public static String getValidCode() {
        return fakerEn.number().digits(3);
    }

    public static String getEmptyCode() {
        return "";
    }

    public static String getInvalidFormatCode() {
        return "11";
    }

    public static String getCodeWithText() {
        return "код";
    }
}