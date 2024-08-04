package churilla.mark.toolrental.model;

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

    private final int chargeableDays;

    private final int discount;

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
    }

    /**
     * Getter for the chargeableDays property.
     *
     * @return The number of days to charge the customer.
     */
    public int getChargeableDays() {
        return chargeableDays;
    }

    /**
     * Calculates the rental due date by adding the rental duration to the checkout date.
     *
     * @return The date the rental is due to be returned.
     */
    public LocalDate calculateRentalDueDate() {
        return checkoutDate.plusDays(rentalDuration);
    }

    /**
     * Calculates the total price of the rental before taking the discount into account.
     *
     * @return The price of the rental without the discount.
     */
    public BigDecimal calculatePreDiscountPrice() {
            return tool.getToolType()
                    .getDailyCharge()
                    .multiply(BigDecimal.valueOf(chargeableDays))
                    .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the total amount of discount to apply to the total.
     *
     * @return The total amount being discounted from the price.
     */
    public BigDecimal calculateDiscountAmount() {
        BigDecimal discountPct = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));

        // The total discount is the pre-discount price times the discount percent.
        return calculatePreDiscountPrice().
                multiply(discountPct).
                setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the final price of the rental with the discount applied.
     *
     * @return The final price, discount included.
     */
    public BigDecimal calculateFinalPrice() {
        return calculatePreDiscountPrice()
                .subtract(calculateDiscountAmount())
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Overridden toString() method. Prints out the details of the rental agreement in a formatted fashion.
     *
     * @return A formatted, human-readable string of the rental agreement.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Tool information.
        sb.append(String.format("Tool code: %s\n", tool.getToolCode()));
        sb.append(String.format("Tool type: %s\n", tool.getToolType().getName()));
        sb.append(String.format("Brand: %s\n", tool.getBrandName()));

        // Rental and checkout info.
        sb.append(String.format("Checkout date: %s\n", checkoutDate.format(DateTimeFormatter.ofPattern("MM/dd/yy"))));
        sb.append(String.format("Rental duration: %s days\n", rentalDuration));
        sb.append(String.format("Due date: %s\n", calculateRentalDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy"))));
        sb.append(String.format("Daily rental charge: $%s\n", tool.getToolType().getDailyCharge()));
        sb.append(String.format("Charged days: %s days\n", chargeableDays));

        // Pricing details.
        sb.append(String.format("Charge before discount: $%s\n", calculatePreDiscountPrice()));
        sb.append(String.format("Discount rate: %1$s%2$s\n", discount, "%"));
        sb.append(String.format("Total discount: $%s\n", calculateDiscountAmount()));
        sb.append(String.format("Final charge: $%s\n", calculateFinalPrice()));

        return sb.toString();
    }
}
