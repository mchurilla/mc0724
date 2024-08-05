package churilla.mark.toolrental.model;

import churilla.mark.toolrental.exception.DiscountPercentageRangeException;
import churilla.mark.toolrental.exception.InvalidRentalDurationException;
import churilla.mark.toolrental.exception.RequiredFieldNullException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The RentalAgreement class is an immutable class that encapsulates the details of a rental agreement for a tool.
 * It includes information about the rented tool, the duration of the rental, discount rate and the number of
 * chargeable days. This class provides methods to calculate other important values such as the rental due date,
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

    /**
     * Constructor for the RentalAgreement class. This class is immutable and all properties of this class
     * are passed into the constructor.
     *
     * @param tool  The {@link RentableTool} that is being rented.
     * @param rentalDuration The duration in days of the rental.
     * @param checkoutDate  The date that the rental was checked out. The rental starts the day after.
     * @param chargeableDays  The number of days that the customer will be charged. This number could differ
     *                        from the rental duration since the tool could be free on certain days.
     * @param discount The discount rate (whole number percent) to apply to the final price.
     */
    public RentalAgreement(final RentableTool tool,
                           final int rentalDuration,
                           final LocalDate checkoutDate,
                           final int chargeableDays,
                           final int discount) {
        this.tool = tool;
        this.rentalDuration = rentalDuration;
        this.checkoutDate = checkoutDate;
        this.chargeableDays = chargeableDays;
        this.discount = discount;

        validateInput();

        BigDecimal discountPct = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));

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
     * Validates the information critical to creating a rental agreement. If any of the input is invalid,
     * this makes the entire agreement invalid.
     */
    private void validateInput() {
        if (tool == null) {
            throw new RequiredFieldNullException("tool");
        }
        if (checkoutDate == null) {
            throw new RequiredFieldNullException("checkoutDate");
        }

        if (rentalDuration < 1) {
            throw new InvalidRentalDurationException("The duration of the rental is invalid. Please re-enter a value of 1 or greater.");
        }

        if (discount < 0 || discount > 100) {
            throw new DiscountPercentageRangeException("The discount that was entered is invalid. Please re-enter a value from 0 to 100.");
        }

        if (chargeableDays < 0) {
            throw new IllegalArgumentException("Chargeable days must be zero (0) or greater.");
        }
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
        Daily rental charge: $%s
        charged days: %s days
        Charge before discount: $%s
        Discount rate: %s%%
        Total discount: $%s
        Final charge: $%s
        """
        .formatted(tool.getToolCode(), tool.getToolType().getName(), tool.getBrandName(),
        checkoutDate.format(DATE_TIME_FORMATTER), rentalDuration, rentalDueDate.format(DATE_TIME_FORMATTER),
        tool.getToolType().getDailyCharge(), chargeableDays, preDiscountPrice, discount, discountAmount, finalPrice);
    }
}
