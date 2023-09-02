package ru.netology;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.Data;
import ru.netology.MainPage;
import ru.netology.PaymentFormPageCredit;
import ru.netology.SqlRequest;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreditBuy {
    private MainPage mainPage;
    private PaymentFormPageCredit paymentFormPageCredit;

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
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForSuccessedNotification();
        val expected = Data.getFirstCardStatus();
        val actual = SqlRequest.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithEmptyFields() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getEmptyCardNumber();
        val month = Data.getEmptyMonth();
        val year = Data.getEmptyYear();
        val cardOwner = Data.getEmptyOwner();
        val code = Data.getEmptyCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyFieldCardNumber() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getEmptyCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithDeclinedCard() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getSecondCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForFailedNotification();
        val expected = Data.getSecondCardStatus();
        val actual = SqlRequest.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDenyPurchaseWithAnotherCard() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getWrongCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForFailedNotification();
    }

    @Test
    void shouldDenyPurchaseCardNumberWith15Digits() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getCardNumberWith15Digits();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseCardNumberWith1Digit() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getCardNumberWith1Digit();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseCardNumberWithText() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getCardNumberWithText();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }
    @Test
    void shouldDenyPurchaseCardNumberWithChars() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getCardNumberWithChars();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }
    @Test
    void shouldDenyPurchaseWithEmptyFieldMonth() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getEmptyMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithMonthOver12() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getMonthOver12();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithZeroMonth() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getZeroMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test
    void shouldDenyPurchaseWithWrongFormatMonth() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getInvalidFormatMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInMonthField() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getMonthWithText();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyYearField() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getEmptyYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithPastYear() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getPastYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForCardExpiredMessage();
    }

    @Test
    void shouldDenyPurchaseWithWrongFormatYear() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getInvalidFormatYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }


    @Test
    void shouldDenyPurchaseWithTextInYearField() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getYearWithText();
        val cardOwner = Data.getValidOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCardOwnerField() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getEmptyOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithoutSecondName() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getOnlyNameOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithLowercaseCardOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getLowercaseLettersOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }


    @Test
    void shouldDenyPurchaseWithRedundantDataCardOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getRedundantDataOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithCyrillicDataCardOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getCyrillicDataOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTwoLanguagesCardOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getTwoAlphabetsDataOwner();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithDigitsCardOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getOwnerWithDigits();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithSpecialCharsCardOwner() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getOwnerWithSpecialChars();
        val code = Data.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
    void shouldDenyPurchaseWithEmptyCodeField() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getEmptyCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
    void shouldDenyPurchaseWithWrongFormatCode() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getInvalidFormatCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
    void shouldDenyPurchaseWithTextInCodeField() {
        paymentFormPageCredit = mainPage.payWithCreditCard()
                .clear();
        val cardNumber = Data.getFirstCardNumber();
        val month = Data.getValidMonth();
        val year = Data.getValidYear();
        val cardOwner = Data.getValidOwner();
        val code = Data.getCodeWithText();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }
}