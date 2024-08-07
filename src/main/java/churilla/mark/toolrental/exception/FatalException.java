package churilla.mark.toolrental.exception;

/**
 * The FatalException class represents an unrecoverable error that indicates a critical failure in the program.
 * <p>
 * This exception is used to signal situations where the application cannot continue running due to a severe
 * problem, such as the inability to read a critical file or data source that is essential for the proper
 * functioning of the application. In such cases, the application is unable to proceed and may need to terminate.
 * </p>
 * <p>
 * This is a checked exception that must be explicitly handled or declared in the method signature.
 * </p>
 */
public class FatalException extends Exception {

    /**
     * Constructor that takes a message.
     *
     * @param message A message detailing the problem that has occurred.
     */
    public FatalException(final String message) {
        super(message);
    }

    /**
     * Constructor that takes a message and source exception.
     *
     * @param message A Message detailing the problem that has occurred.
     * @param cause The source exception.
     */
    public FatalException(final String message, final Exception cause) {
        super(message, cause);
    }
}
