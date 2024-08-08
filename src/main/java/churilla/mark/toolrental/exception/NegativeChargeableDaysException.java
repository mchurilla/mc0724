package churilla.mark.toolrental.exception;

/**
 * Exception thrown when the calculated number of chargeable days is less than zero.
 * <p>
 * This exception is thrown when the calculation that determines the chargeable days for a specific tool within a given
 * timeframe results in a value less than zero, indicating an error in the computation.
 * </p>
 */
public class NegativeChargeableDaysException extends RuntimeException {
    /**
     * Constructor that takes a message.
     *
     * @param message A message detailing the problem that has occurred.
     */
    public NegativeChargeableDaysException(final String message) {
        super(message);
    }

    /**
     * Constructor that takes a message and source exception.
     *
     * @param message A Message detailing the problem that has occurred.
     * @param cause The source exception.
     */
    public NegativeChargeableDaysException(final String message, final Exception cause) {
        super(message, cause);
    }
}
