package churilla.mark.toolrental.exception;

/**
 * Exception thrown when the calculated number of chargeable days is less than zero.
 * <p>
 * This exception will occur if the calculation that computes the number of days to charge for a patricular tool
 * during a particular timeframe is incorrect and results in a number less than zero.
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
