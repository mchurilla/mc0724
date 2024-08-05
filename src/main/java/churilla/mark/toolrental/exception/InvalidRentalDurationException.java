package churilla.mark.toolrental.exception;

/**
 * Exception thrown when an invalid rental duration is provided.
 * <p>
 * This exception is used to signal that the rental duration provided is not valid.
 * A valid rental duration is a positive number greater than zero. If a non-positive
 * number is provided (i.e., zero or a negative number), this exception will be thrown
 * to indicate that the duration is invalid.
 * </p>
 */
public class InvalidRentalDurationException extends RuntimeException {

    /**
     * Constructor that takes a message.
     *
     * @param message A message detailing the problem that has occurred.
     */
    public InvalidRentalDurationException(final String message) {
        super(message);
    }

    /**
     * Constructor that takes a message and source exception.
     *
     * @param message A Message detailing the problem that has occurred.
     * @param ex The source exception.
     */
    public InvalidRentalDurationException(final String message, final Exception ex) {
        super(message, ex);
    }
}
