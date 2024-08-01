package churilla.mark.toolrental.exception;

/**
 * An exception class that is thrown when an invalid rental duration is provided.
 * The acceptable duration is a number that is greater than zero.
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
