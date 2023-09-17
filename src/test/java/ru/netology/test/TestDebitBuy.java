package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.Data;
import ru.netology.data.SqlRequest;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentFormPageDebit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDebitBuy {
    private MainPage mainPage;
    private PaymentFormPageDebit paymentFormPageDebit;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        mainPage = open("http://localhost:8080/", MainPage.class);
    }

    @AfterEach
    void cleanDB() {
        SqlRequest.clearDB();
    }


    @Test
    void shouldAllowPurchaseWithApprovedCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForSuccessedNotification();
        val expected = Data.getFirstCardStatus();
        val actual = SqlRequest.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithEmptyFields() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getEmptyCardNumber();
        val month = Data.getEmptyMonth();
        val year = Data.getEmptyYear();
        val cardOwner = Data.getEmptyOwner();
        val code = Data.getEmptyCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCardNumberField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getEmptyCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithDeclinedCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getSecondCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForFailedNotification();
        val expected = Data.getSecondCardStatus();
        val actual = SqlRequest.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithAnotherCard() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getWrongCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForFailedNotification();
    }

    @Test
    void shouldDenyPurchaseCardNumberWith15Digits() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getCardNumberWith15Digits();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseCardNumberWith1Digit() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getCardNumberWith1Digit();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseCardNumberWithText() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getCardNumberWithText();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseCardNumberWithChars() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getCardNumberWithChars();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyMonthField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getEmptyMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithMonthOver12() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getMonthOver12();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithZeroMonth() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getZeroMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithWrongFormatMonth() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getInvalidFormatMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInMonthField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getMonthWithText();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyYearField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getEmptyYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithPastYear() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getPastYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForCardExpiredMessage();
    }

    @Test
    void shouldDenyPurchaseWithWrongFormatYear() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getInvalidFormatYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }


    @Test
    void shouldDenyPurchaseWithTextInYearField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getYearWithText();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCardOwnerField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getEmptyOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithoutSecondName() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getOnlyNameOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithLowercaseCardOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getLowercaseLettersOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }


    @Test
    void shouldDenyPurchaseWithRedundantDataCardOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getRedundantDataOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithCyrillicDataCardOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getCyrillicDataOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTwoLanguagesCardOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getTwoAlphabetsDataOwner();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithDigitsCardOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getOwnerWithDigits();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithSpecialCharsCardOwner() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getOwnerWithSpecialChars();
        val code = Data.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCodeField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getEmptyCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithWrongFormatCode() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getInvalidFormatCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInCodeField() {
        paymentFormPageDebit = mainPage.payWithDebitCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getCodeWithText();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForInvalidCharactersMessage();
    }
}