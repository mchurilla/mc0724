package churilla.mark.toolrental.model;

import churilla.mark.toolrental.exception.DiscountPercentageRangeException;
import churilla.mark.toolrental.exception.InvalidRentalDurationException;
import churilla.mark.toolrental.exception.NegativeChargeableDaysException;
import churilla.mark.toolrental.utility.ValidationUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The RentalAgreement class is an immutable class that encapsulates the details of a rental agreement for a tool.
 * It includes information about the rented tool, the duration of the rental, discount rate and the number of
 * chargeable days. This class calculates other important values such as the rental due date,
 * the total chargeable amount before and after discounts, and the amount discounted.
 */
public class RentalAgreement {

    private final RentableTool tool;
    private final int rentalDuration;
    private final LocalDate checkoutDate;
    private final LocalDate rentalDueDate;
    private final int chargeableDays;
    private final int discount;
    private final BigDecimal discountAmount;
    private final BigDecimal preDiscountPrice;
    private final BigDecimal finalPrice;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new java.util.Locale("en", "us"));
    private static final int MIN_DISCOUNT = 0;
    private static final int MAX_DISCOUNT = 100;
    private static final int MIN_RENTAL_DURATION = 1;
    private static final int MIN_CHARGEABLE_DAYS = 0;

    /**
     * Constructor for the RentalAgreement class. This class is immutable and all properties of this class
     * are passed into the constructor.
     *
     * @param tool  The {@link RentableTool} that is being rented.
     * @param rentalDuration The duration in days of the rental.
     * @param checkoutDate  The date that the rental was checked out. The rental charges start the day after.
     * @param chargeableDays  The number of days that the customer will be charged. This number could differ
     *                        from the rental duration since the tool could be free on certain days.
     * @param discount The discount rate (whole number percent) to apply to the final price.
     */
    public RentalAgreement(final RentableTool tool,
                           final int rentalDuration,
                           final LocalDate checkoutDate,
                           final int chargeableDays,
                           final int discount) {
        this.tool = ValidationUtils.requireNonNull(tool, "tool");
        this.checkoutDate = ValidationUtils.requireNonNull(checkoutDate, "checkoutDate");

        if (rentalDuration < MIN_RENTAL_DURATION) {
            throw new InvalidRentalDurationException(String.format("The rental duration %s is invalid. Please re-enter a value of 1 or greater.", rentalDuration));
        }

        if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT) {
            throw new DiscountPercentageRangeException(String.format("The value %s for the discount percentage is invalid. Please provide a number between 0 and 100.", discount));
        }

        if (chargeableDays < MIN_CHARGEABLE_DAYS) {
            throw new NegativeChargeableDaysException(String.format("Invalid calculation of chargeable days: %s. Must be 0 or greater", chargeableDays));
        }

        this.rentalDuration = rentalDuration;
        this.chargeableDays = chargeableDays;
        this.discount = discount;

        BigDecimal discountPct = BigDecimal.valueOf(discount)
                                           .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Calculate the pre-discount price, the discount amount, and the final price.
        preDiscountPrice = tool.getToolType()
                .getDailyCharge()
                .multiply(BigDecimal.valueOf(chargeableDays))
                .setScale(2, RoundingMode.HALF_UP);

        discountAmount = preDiscountPrice.multiply(discountPct)
                                         .setScale(2, RoundingMode.HALF_UP);

        finalPrice = preDiscountPrice.subtract(discountAmount)
                                     .setScale(2, RoundingMode.HALF_UP);

        rentalDueDate = checkoutDate.plusDays(rentalDuration);
    }

    /**
     * Returns the number of chargeable days.
     *
     * @return The number of days to charge the customer.
     */
    public int getChargeableDays() {
        return chargeableDays;
    }

    /**
     * Returns the precalculated discount amount.
     *
     * @return The amount that is subtracted to give the final price.
     */
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Returns the precalculated pre-discount price.
     *
     * @return The total price of the rental prior to having the discount applied.
     */
    public BigDecimal getPreDiscountPrice() {
        return preDiscountPrice;
    }

    /**
     * Returns the precalculated final price of the rental.
     *
     * @return The final price of the rental after the discount has been applied.
     */
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    /**
     * Returns the rental due date, which is calculated based on the rental duration.
     *
     * @return The due date for returning the rented tool.
     */
    public LocalDate getRentalDueDate() {
        return rentalDueDate;
    }

    /**
     * Overridden toString() method. Prints out the details of the rental agreement in a formatted fashion.
     *
     * @return A formatted, human-readable string of the rental agreement.
     */
    @Override
    public String toString() {
        return """
        Tool code: %s
        Tool type: %s
        Brand: %s
        Checkout date: %s
        Rental duration: %s days
        Due date: %s
        Daily rental charge: %s
        Charged days: %s days
        Charge before discount: %s
        Discount rate: %s%%
        Total discount: %s
        Final charge: %s
        """
        .formatted(tool.getToolCode(),
                tool.getToolType().getName(),
                tool.getBrandName(),
                checkoutDate.format(DATE_TIME_FORMATTER),
                rentalDuration,
                rentalDueDate.format(DATE_TIME_FORMATTER),
                CURRENCY_FORMAT.format(tool.getToolType().getDailyCharge()),
                chargeableDays,
                CURRENCY_FORMAT.format(preDiscountPrice),
                discount,
                CURRENCY_FORMAT.format(discountAmount),
                CURRENCY_FORMAT.format(finalPrice));
    }
}
