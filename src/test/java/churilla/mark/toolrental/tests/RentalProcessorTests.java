package churilla.mark.toolrental.tests;

import churilla.mark.toolrental.exception.*;
import churilla.mark.toolrental.logic.RentalProcessor;
import churilla.mark.toolrental.model.RentableTool;
import churilla.mark.toolrental.model.RentalAgreement;
import churilla.mark.toolrental.model.ToolType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RentalProcessorTests {
    private RentalProcessor rentalProcessor;

    @BeforeAll
    void setup() {
        try {
            rentalProcessor = new RentalProcessor();
        } catch (FatalException ex) {
            throw new RuntimeException("An error occurred when initializing RentalProcessor object", ex);
        }
    }

    //
    // Project proof tests.
    //

    /**
     *  Test #1 in the specification.
     *
     *  Tool code: JAKR
     *  Checkout date: 9/3/2015
     *  Rental days: 5
     *  Discount: 101%
     *
     *  This test expects the checkout() method to throw a {@link DiscountPercentageRangeException}.
     */
    @Test
    void test1_givenToolCodeJAKR_whenInvalidDiscount_thenExpectException() {
        assertThrows(
            DiscountPercentageRangeException.class,
            () -> rentalProcessor.checkout("JAKR", LocalDate.of(2015, 9, 3), 5, 101),
            "Expected checkout() to throw DiscountPercentageRangeException, but it did not."
        );
    }

    /**
     *  Test #2 in the specification.
     *
     *  Tool code: LADW
     *  Checkout date: 7/2/2020
     *  Rental days: 3
     *  Discount: 10%
     *
     *  This rental will occur during the July 4th holiday which is on a Saturday making the holiday on July 3rd.
     *  This rental should have two (2) charged rental days at $1.99 per day for a total of $3.98.
     *  The discount is set at 10%, or $0.40 giving a final charge of $3.58.
     */
    @Test
    void test2_givenToolCodeLADW_whenOccurringDuringJuly4th_thenExpectCorrectAmount() {
        RentalAgreement ra = rentalProcessor.checkout("LADW", LocalDate.of(2020, 7, 2), 3, 10);

        assertEquals(2, ra.getChargeableDays());
        assertEquals(BigDecimal.valueOf(0.40).setScale(2, RoundingMode.HALF_UP), ra.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.98).setScale(2, RoundingMode.HALF_UP), ra.getPreDiscountPrice());
        assertEquals(BigDecimal.valueOf(3.58).setScale(2, RoundingMode.HALF_UP), ra.getFinalPrice());
        assertEquals(LocalDate.of(2020, 7, 5), ra.getRentalDueDate());

        System.out.println(ra);
    }

    /**
     *  Test #3 in the specification.
     *
     *  Tool code: CHNS
     *  Checkout date: 7/2/15
     *  Rental days: 5
     *  Discount: 25%
     *
     *  This rental will occur during the July 4th holiday which is on a Saturday making the holiday on July 3rd.
     *  The rental will charge for the holiday but not the weekend, giving it three (3) charged rental days at $1.49
     *  per day. The pre-discount price is $4.47. Applying a discount rate of 25% gives a discount of $1.12 for a
     *  final price of $3.35.
     */
    @Test
    void test3_givenToolCodeCHNS_whenOccurringDuringJuly4th_thenExpectCorrectAmount() {
        RentalAgreement ra = rentalProcessor.checkout("CHNS", LocalDate.of(2015, 7, 2), 5, 25);

        assertEquals(3, ra.getChargeableDays());
        assertEquals(BigDecimal.valueOf(1.12).setScale(2, RoundingMode.HALF_UP), ra.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(4.47).setScale(2, RoundingMode.HALF_UP), ra.getPreDiscountPrice());
        assertEquals(BigDecimal.valueOf(3.35).setScale(2, RoundingMode.HALF_UP), ra.getFinalPrice());
        assertEquals(LocalDate.of(2015, 7, 7), ra.getRentalDueDate());

        System.out.println(ra);
    }

    /**
     *  Test #4 in the specification.
     *
     *  Tool code: JAKD
     *  Checkout date: 9/3/2015
     *  Rental days: 6
     *  Discount: 0%
     *
     *  This rental occurs during the Labor Day holiday, which occurs on Sept. 7th.
     *  Jackhammers have no holiday charge or weekend charge, giving it three (3) charged rental days at $2.99 per day.
     *  The pre-discount price is $8.97 with no discount being applied therefore $8.97 is the final price.
     */
    @Test
    void test4_giveToolCodeJAKD_whenOccurringOnLaborDay_thenExpectCorrectAmount() {
        RentalAgreement ra = rentalProcessor.checkout("JAKD", LocalDate.of(2015, 9, 3), 6, 0);

        assertEquals(3, ra.getChargeableDays());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), ra.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), ra.getPreDiscountPrice());
        assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), ra.getFinalPrice());
        assertEquals(LocalDate.of(2015, 9, 9), ra.getRentalDueDate());

        System.out.println(ra);
    }

    /**
     *  Test #5 in the specification.
     *
     *  Tool code: JAKR
     *  Checkout date: 7/2/2015
     *  Rental days: 9
     *  Discount: 0%
     *
     *  This rental occurs during the July 4th holiday which occurs on Saturday, making it July 3rd.
     *  Jackhammers are not charged on holidays or weekends, giving it five (5) charged days at a rate of $2.99 per day.
     *  The pre-discount price is $14.95, with no discount being applied making $14.95 the final price.
     */
    @Test
    void test5_givenToolCodeJAKR_whenOccurringDuringJuly4th_thenExpectCorrectAmount() {
        RentalAgreement ra = rentalProcessor.checkout("JAKR", LocalDate.of(2015, 7, 2), 9, 0);

        assertEquals(5, ra.getChargeableDays());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), ra.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(14.95).setScale(2, RoundingMode.HALF_UP), ra.getPreDiscountPrice());
        assertEquals(BigDecimal.valueOf(14.95).setScale(2, RoundingMode.HALF_UP), ra.getFinalPrice());
        assertEquals(LocalDate.of(2015, 7, 11), ra.getRentalDueDate());

        System.out.println(ra);
    }

    /**
     *  Test #6 in the specification.
     *
     *  Tool code: JAKR
     *  Checkout date: 7/2/2020
     *  Rental days: 4
     *  Discount: 50%
     *
     *  This rental occurs during the July 4th holiday which occurs on Saturday, making it July 3rd.
     *  Jackhammers are not charged on holidays or weekends, giving it one (1) charged day at a rate of $2.99 per day.
     *  The pre-discount price is $2.99 with a 50% discount being applied. The discount amount is $1.50.
     *  The final price after discount is $1.49.
     */
    @Test
    void test6_givenToolCodeJAKR_whenOccurringDuringJuly4th_thenExpectCorrectAmount() {
        RentalAgreement ra = rentalProcessor.checkout("JAKR", LocalDate.of(2020, 7, 2), 4, 50);

        assertEquals(1, ra.getChargeableDays());
        assertEquals(BigDecimal.valueOf(1.50).setScale(2, RoundingMode.HALF_UP), ra.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(2.99).setScale(2, RoundingMode.HALF_UP), ra.getPreDiscountPrice());
        assertEquals(BigDecimal.valueOf(1.49).setScale(2, RoundingMode.HALF_UP), ra.getFinalPrice());
        assertEquals(LocalDate.of(2020, 7, 6), ra.getRentalDueDate());

        System.out.println(ra);
    }

    //
    // Additional Tests
    //

    /**
     * Tests that when a rental duration less than zero is entered,
     * then a {@link InvalidRentalDurationException} is thrown.
     */
    @Test
    void giveRentalDurationLessThanZero_whenCheckingOut_thenExpectToThrow_InvalidRentalDurationException() {
       assertThrows(
               InvalidRentalDurationException.class,
               () -> rentalProcessor.checkout("CHNS", LocalDate.of(2020, 7, 2), -1, 25),
               "Expected InvalidRentalDurationException to be thrown but it wasn't."
       );
    }

    /**
     * Tests that when a rental duration of zero is entered, then a {@link InvalidRentalDurationException} is thrown.
     */
    @Test
    void givenRentalDurationIsZero_whenCheckingOut_thenExpectToThrow_InvalidRentalDurationException() {
        assertThrows(
                InvalidRentalDurationException.class,
                () -> rentalProcessor.checkout("CHNS", LocalDate.of(2020, 7, 2), 0, 25),
                "Expected InvalidRentalDurationException to be thrown but it wasn't."
        );
    }

    /**
     * Tests that when an unknown tool code is entered, then a {@link UnknownToolCodeException} is thrown.
     */
    @Test
    void givenUnknownToolCodeEntered_whenCheckingOut_thenExpectToThrow_UnknownToolCodeException() {
        assertThrows(
                UnknownToolCodeException.class,
                () -> rentalProcessor.checkout("BOBC", LocalDate.of(2020, 7, 2), 15, 25),
                "Expected UnknownToolCodeException to be thrown but it wasn't."
        );
    }

    /**
     * Tests that when the toolCode passed into checkout() is null, then a {@link RequiredFieldNullException} is thrown.
     */
    @Test
    void givenNullToolCode_whenCheckingOut_thenExpectToThrow_RequiredFieldNullException() {
        assertThrows(
                RequiredFieldNullException.class,
                () -> rentalProcessor.checkout(null, LocalDate.of(2020,1, 1), 0, 0)
        );
    }

    /**
     * Tests that when the checkoutDate passed into checkout() is null, then a {@link RequiredFieldNullException}
     * is thrown.
     */
    @Test
    void givenNullCheckoutDate_whenCheckingOut_thenExpectToThrow_RequiredFieldNullException() {
        assertThrows(
                RequiredFieldNullException.class,
                () -> rentalProcessor.checkout("FAKE", null, 0, 0)
        );
    }

    /**
     * Tests that when either the name or daily charge values passed to the {@link ToolType} constructor are null,
     * then a {@link RequiredFieldNullException} is thrown.
     */
    @Test
    void givenNullRequiredValues_whenPassedToToolTypeConstructor_thenExpectToThrow_RequiredFieldNullException() {
        assertThrows(
                RequiredFieldNullException.class,
                () -> new ToolType(null, BigDecimal.valueOf(9.99), true, true, true),
                "Expected RequiredFieldNullException to be thrown when tool type name is null but it wasn't."
        );

        assertThrows(
                RequiredFieldNullException.class,
                () -> new ToolType("FAKE", null, true, true, true),
                "Expected RequiredFieldNullException to be thrown when daily charge is null but it wasn't."
        );
    }

    /**
     * Tests that when any of the values passed to the {@link RentableTool} constructor are null,
     * then a {@link RequiredFieldNullException} is thrown.
     */
    @Test
    void givenNullRequiredValues_whenPassedToRentableToolConstructor_thenExpectToThrow_RequiredFieldNullException() {
        ToolType fakeToolType = new ToolType("FAKE", BigDecimal.valueOf(9.99), true, true, true);

        assertThrows(
                RequiredFieldNullException.class,
                () -> new RentableTool(null, fakeToolType, "FAKE"),
                "Expected RequiredFieldNullException to be thrown when tool code is null, but it wasn't."
        );

        assertThrows(
                RequiredFieldNullException.class,
                () -> new RentableTool("FAKE", null, "FAKE"),
                "Expected RequiredFieldNullException to be thrown when tool type is null, but it wasn't."
        );

        assertThrows(
                RequiredFieldNullException.class,
                () -> new RentableTool("FAKE", fakeToolType, null),
                "Expected RequiredFieldNullException to be thrown when brand name is null, but it wasn't."
        );
    }

    /**
     * Tests that when a null value is passed to the RentalAgreement constructor for either the tool or checkout date
     * fields, that a {@link RequiredFieldNullException} is thrown.
     */
    @Test
    void givenNullRequiredValues_whenPassedToRentalAgreementConstructor_thenExpectToThrow_RequiredFieldNullException() {
        assertThrows(
                RequiredFieldNullException.class,
                () -> new RentalAgreement(null, 5, LocalDate.of(2020, 7, 2), 0, 0),
                "Expected RequiredFieldNullException to be thrown when tool is null, but it wasn't."
        );

        ToolType type = new ToolType("FAKE", BigDecimal.valueOf(9.99), true, true, true);
        RentableTool tool = new RentableTool("FAKE", type, "FAKE");
        assertThrows(
                RequiredFieldNullException.class,
                () -> new RentalAgreement(tool, 5, null, 0, 0),
                "Expected RequiredFieldNullException to be thrown when checkout date is null, but it wasn't."
        );
    }
}
