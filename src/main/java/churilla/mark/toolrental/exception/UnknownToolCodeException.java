package churilla.mark.toolrental.exception;

/**
 * An exception class that is thrown when an unknown or invalid tool code is entered during checkout.
 * <p>
 * This exception indicates that the tool code provided during the checkout process does not
 * correspond to any known or valid tool in the system.
 * </p>
 */
public class UnknownToolCodeException extends RuntimeException {
    /**
     * Constructor that takes a message.
     *
     * @param message A message detailing the problem that has occurred.
     */
    public UnknownToolCodeException(final String message) {
        super(message);
    }

    /**
     * Constructor that takes a message and source exception.
     *
     * @param message A Message detailing the problem that has occurred.
     * @param cause The source exception.
     */
    public UnknownToolCodeException(final String message, final Exception cause) {
        super(message, cause);
    }
}
