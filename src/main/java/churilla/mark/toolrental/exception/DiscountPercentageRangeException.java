package churilla.mark.toolrental.exception;

/**
 * Exception thrown when a discount percentage provided is out of the valid range.
 * <p>
 * This exception is used to indicate that a discount percentage value does not meet
 * the expected range of 0 to 100 inclusive. The discount percentage is a whole number
 * representing the discount to be applied. Valid values are between 0 and 100.
 * </p>
 */
public class DiscountPercentageRangeException extends RuntimeException {

    /**
     * Constructor that takes a message.
     *
     * @param message A message detailing the problem that has occurred.
     */
    public DiscountPercentageRangeException(final String message) {
        super(message);
    }

    /**
     * Constructor that takes a message and source exception.
     *
     * @param message A Message detailing the problem that has occurred.
     * @param cause The source exception.
     */
    public DiscountPercentageRangeException(final String message, final Exception cause) {
        super(message, cause);
    }
}
