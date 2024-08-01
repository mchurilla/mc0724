package churilla.mark.toolrental.exception;

/**
 * An exception class that is thrown when an invalid number is provided for the discount.
 * This number is a whole number that represents the discount percentage (e.g., 20) and has
 * a valid range of between 0 and 100 inclusive.
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
     * @param ex The source exception.
     */
    public DiscountPercentageRangeException(final String message, final Exception ex) {
        super(message, ex);
    }
}
